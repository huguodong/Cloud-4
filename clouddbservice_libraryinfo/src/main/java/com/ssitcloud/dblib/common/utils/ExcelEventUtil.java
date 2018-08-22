package com.ssitcloud.dblib.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 读取excel大量数据的工具
 * 仅限2007以后的excel文件， 即文件格式为xlsx的excel文件
 *
 * <p>2017年5月23日 下午5:02:13  
 * @author hjc 
 *
 */
public class ExcelEventUtil {
	
	/**
	 * The type of the data value is indicated by an attribute on the cell. The
	 * value is usually in a "v" element within the cell.
	 * 单元格类型
	 */
	enum xssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
	}
	
	
	
	/**
	 * 读取第一个sheet
	 *
	 * <p>2017年5月23日 下午5:16:22 
	 * <p>create by hjc
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public List<String[]> processOneSheet(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		StylesTable styleTable = r.getStylesTable();
		// To look up the Sheet Name / Sheet Order / rID,
		//  you need to process the core Workbook stream.
		// Normally it's of the form rId# or rSheet#
		InputStream sheet2 = r.getSheet("rId1");//默认读取第一个sheet
		List<String[]> list = fetchSheetParser(sst, styleTable, sheet2);
		return list;
	}
	
	
	/**
	 * 读取并且处理所有的sheet
	 *
	 * <p>2017年5月23日 下午5:16:00 
	 * <p>create by hjc
	 * @param filename
	 * @throws Exception
	 */
	public void processAllSheets(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		StylesTable stylesTable = r.getStylesTable();


		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			System.out.println("Processing new sheet:\n");
			InputStream sheet = sheets.next();
			List<String[]> list = fetchSheetParser(sst, stylesTable, sheet);
			
			int count = 0;
			for (String[] strings : list) {
				System.out.println(strings.length);
				for (String string : strings) {
					System.out.print(string + "\t|");
				}
				System.out.println();
				count ++;
				if (count >=10) {
					break;
				}
			}
		}
	}
	
	
	public List<String[]> fetchSheetParser(SharedStringsTable sst,
			StylesTable stylesTable, InputStream sheetInputStream) throws SAXException,
			ParserConfigurationException, IOException {
		
		InputSource source = new InputSource(sheetInputStream);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		XMLReader parser = saxParser.getXMLReader();
		SheetHandler handler = new SheetHandler(sst, stylesTable);//初始化
		parser.setContentHandler(handler);
		
		parser.parse(source);
		return handler.getRows();
	}
	
	/**
	 * See org.xml.sax.helpers.DefaultHandler javadocs
	 */
	private static class SheetHandler extends DefaultHandler {
		private SharedStringsTable sst;
		private boolean vIsOpen;
		private StringBuffer value;//保存单元格的值

		private List<String[]> rows = new ArrayList<String[]>();
		
		private List<String> cellList = new ArrayList<String>(); //保存每一行的list
		boolean cellListEmpty = true; //当前行的单元格list是否为空

		/**
		 * Table with styles
		 */
		private StylesTable stylesTable;//excel所有的style数据，处理日期类型时用到

		// used when cell close element is seen.
		private xssfDataType nextDataType;//单元格类型，枚举类型
		// Used to format numeric cell values.
		private short formatIndex;
		private String formatString;
		private final DataFormatter formatter;
		
		// The last column printed to the output stream
		private int lastColumnNumber = -1;
		
		private boolean isCellNull = false;

		private int thisColumn = -1;
		
		private String[] record;
		
		private boolean handleC = false;
		
		
		/**
		 * 初始化
		 * @param sst
		 * @param stylesTable
		 */
		private SheetHandler(SharedStringsTable sst, StylesTable stylesTable) {
			this.sst = sst;
			this.stylesTable = stylesTable;
			this.value = new StringBuffer();
			this.nextDataType = xssfDataType.NUMBER;
			this.formatter = new DataFormatter();
			rows.clear();// 每次读取都清空行集合
			cellList.clear();//保存一行单元格的内容
		}

		
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			if ("inlineStr".equals(name) || "v".equals(name)) {
				vIsOpen = true;
				// Clear contents cache
				value.setLength(0);
			} else if (name.equals("c")) {// c => cell
				handleC = true;//处理到C起始标签了
				// Get the cell reference
				String r = attributes.getValue("r");
				int firstDigit = -1;
				for (int c = 0; c < r.length(); ++c) {
					if (Character.isDigit(r.charAt(c))) {
						firstDigit = c;
						break;
					}
				}
				thisColumn = nameToColumn(r.substring(0, firstDigit));//

				// Set up defaults.
				this.nextDataType = xssfDataType.NUMBER;
				this.formatIndex = -1;
				this.formatString = null;
				String cellType = attributes.getValue("t");
				String cellStyleStr = attributes.getValue("s");
				if ("b".equals(cellType))
					nextDataType = xssfDataType.BOOL;
				else if ("e".equals(cellType))
					nextDataType = xssfDataType.ERROR;
				else if ("inlineStr".equals(cellType))
					nextDataType = xssfDataType.INLINESTR;
				else if ("s".equals(cellType))
					nextDataType = xssfDataType.SSTINDEX;
				else if ("str".equals(cellType))
					nextDataType = xssfDataType.FORMULA;
				else if (cellStyleStr != null) {
					// It's a number, but almost certainly one
					// with a special style or format
					int styleIndex = Integer.parseInt(cellStyleStr);
					XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
					this.formatIndex = style.getDataFormat();
					this.formatString = style.getDataFormatString();
					if (this.formatString == null)
						this.formatString = BuiltinFormats
								.getBuiltinFormat(this.formatIndex);
				}
			}
		}

		public void endElement(String uri, String localName, String name)
				throws SAXException {

			String thisStr = null;

			if ("v".equals(name)) {
				switch (nextDataType) {
				case BOOL:
					char first = value.charAt(0);
					thisStr = first == '0' ? "FALSE" : "TRUE";
					break;
				case ERROR:
					thisStr = "ERROR:" + value.toString() ;
					break;

				case FORMULA:
					// A formula could result in a string value,
					// so always add double-quote characters.
					thisStr =  value.toString();
					break;

				case INLINESTR:
					XSSFRichTextString rtsi = new XSSFRichTextString(
							value.toString());
					thisStr = rtsi.toString();
					break;

				case SSTINDEX:
					String sstIndex = value.toString();
					try {
						int idx = Integer.parseInt(sstIndex);
						XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx)); //获取单元格的值
						thisStr = rtss.toString();
					} catch (NumberFormatException ex) {
						System.out.println("Failed to parse SST index '"
								+ sstIndex + "': " + ex.toString());
					}
					break;

				case NUMBER:
					String n = value.toString();
					// 判断是否是日期格式
					if (HSSFDateUtil.isADateFormat(this.formatIndex, n)) {
						Double d = Double.parseDouble(n);
						Date date = HSSFDateUtil.getJavaDate(d);
						thisStr = formateDateToString(date);
					} else if (this.formatString != null){
						thisStr = formatter.formatRawCellContents(
								Double.parseDouble(n), this.formatIndex,
								this.formatString);
					} else {
						thisStr = n;
					}
					break;

				default:
					thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
					break;
				}
				

				// Output after we've seen the string contents
				// Emit commas for any fields that were missing on this row
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
				// 判断单元格的值是否为空
				if (thisStr == null || "".equals(isCellNull)) {
					isCellNull = true;// 设置单元格是否为空值
				}
//				record[thisColumn] = thisStr;
				cellList.add(thisStr);
				// Update column
				if (thisColumn > -1)
					lastColumnNumber = thisColumn;
				handleC = false;
			}else if ("row".equals(name)) {
				
				// Print out any missing commas if needed
				// Columns are 0 based
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
				// 判断是否空行
				//判断cellList是不是都为空
				
				if (cellList!=null && cellList.size() >0) {
					cellListEmpty = true;
					for (String string : cellList) {
						if (string!=null && !"".equals(string)) {
							cellListEmpty = false;
							break;
						}
					}
					
					if (!cellListEmpty) {
						record = new String[cellList.size()];
						cellList.toArray(record);
						if (isCellNull == false ) {
							rows.add(record.clone());
							isCellNull = false;
							for (int i = 0; i < record.length; i++) {
								record[i] = null;
							}
						}
					}
					cellList.clear();
				}
				lastColumnNumber = -1;
			}else{
				//如果处理了单元格c的起始标签，并且马上遇到结束标签，则为空值
				if (handleC) {
					cellList.add("");
					handleC = false;
				}
			}
		}

		/**
		 * 获取单元格的值
		 */
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (vIsOpen)
				value.append(ch, start, length);
		}

		/**
		 * Converts an Excel column name like "C" to a zero-based index.
		 * 获取单元格在当前行的位置，从0开始
		 * @param name
		 * @return Index corresponding to the specified name
		 */
		private int nameToColumn(String name) {
			int column = -1;
			for (int i = 0; i < name.length(); ++i) {
				int c = name.charAt(i);
				column = (column + 1) * 26 + c - 'A';
			}
			return column;
		}

		/**
		 * 格式化日期
		 * 
		 * <p>
		 * 2017年5月22日 下午3:10:17
		 * 
		 * @param date
		 * @return
		 */
		private String formateDateToString(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期
			return sdf.format(date);

		}

		/**
		 * 返回读取到的所有数据
		 *
		 * <p>2017年5月23日 下午5:19:11 
		 * <p>create by hjc
		 * @return
		 */
		public List<String[]> getRows() {
			return rows;
		}
	}
	
	
	public static void main(String[] args) {
		ExcelEventUtil eventUtil = new ExcelEventUtil();
		try {
			List<String[]> list = eventUtil.processOneSheet("d:/1.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
