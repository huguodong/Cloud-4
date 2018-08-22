package com.ssitcloud.dblib.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BibliosDao;
import com.ssitcloud.dblib.dao.BookItemDao;
import com.ssitcloud.dblib.dao.CollegeInfoDao;
import com.ssitcloud.dblib.dao.ReaderCardDao;
import com.ssitcloud.dblib.entity.BibliosAndBook;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;
import com.ssitcloud.readermgmt.entity.UploadReaderFieldTempEntity;

public class UploadBibliosUtils {
	public int lib_idx = 0;
	private int successNum = 0;//插入成功的数据
	private int failNum = 0;//失败的数据
	Map<String, String> errorLogs = null;
	
	private BookItemDao bookItemDao =null;
	
	private BibliosDao bibliosDao = null;
	
	public UploadBibliosUtils(BookItemDao bookItemDao,BibliosDao bibliosDao){
		this.bookItemDao = bookItemDao;
		this.bibliosDao = bibliosDao;
		errorLogs = new HashMap<>();
	}
	/**
	 * 解析上传文件
	 * @param file
	 * @param cardEnitties
	 * @param collegeInfoEntities
	 * @throws Exception
	 */
	public void parseUploadFile(CommonsMultipartFile file,String req){
		try{
			//获取文件名称
			String fileName = file.getOriginalFilename();
			//获取后缀
			String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
			//获取上传流
			InputStream inputStream = file.getInputStream();
			
			//将读者类的字段封装到map集合中,key:字段名称 ,value:字段对象
			Map<String,Field> bibliosFieldMap = convertBeanToMap(BibliosAndBook.class);
	
			//获取模板配置
			List<UploadReaderFieldTempEntity> fieldTempEntities = queryTempConfig(req.toString());
			int size = fieldTempEntities.size();
			if("txt".equalsIgnoreCase(prefix)){
				//当txt文本上传数据时，只能定义一行
				if(size<=1){
					parseTxtFile(inputStream,fieldTempEntities,bibliosFieldMap);
				}else{
					errorLogs.put("字段定义不正确:","当txt文本上传数据时，只能定义一行");
					failNum=1;
				}
			}else{
				parseXlsxFile(inputStream, fieldTempEntities, bibliosFieldMap,prefix);
			}
		}catch(Exception e){
			e.printStackTrace();
			//throw new RuntimeException(e);
		}
		
	}
	
	/***
	 * 将类成员变量封装到map集合
	 * @param clazz
	 * @return
	 */
	public <T> Map<String,Field> convertBeanToMap(Class<T> clazz){
		Map<String, Field> map = new HashMap<>();
		Field[] readerFields = clazz.getDeclaredFields();
		for(Field field : readerFields){
			map.put(field.getName(),field);
		}
		return map;
	}
	
	/***
	 * 查询模板
	 * @return
	 */
	public List<UploadReaderFieldTempEntity> queryTempConfig(String req){
		ResultEntity resultEntity = new ResultEntity();
		List<UploadReaderFieldTempEntity> list = new ArrayList<>();
		if(req != null){
			resultEntity = JsonUtils.fromJson(req, ResultEntity.class);
		}
		if(resultEntity.getState()){
			//获取图书馆 TODO 
			Map<Object,Object> configEntity = (Map<Object, Object>) resultEntity.getResult();
			Object uploadReaderStr = configEntity.get("import_tpl_value");
			lib_idx = Integer.parseInt(configEntity.get("library_idx").toString());
			JSONObject jsonObject = JSONObject.fromObject((uploadReaderStr));
			JSONArray jsonArr =  jsonObject.optJSONArray("searchinfo");
			for(int i = 0;i<jsonArr.size();i++){
				UploadReaderFieldTempEntity entity = new UploadReaderFieldTempEntity();
				JSONObject object = jsonArr.optJSONObject(i);
				entity.setData_source_select(object.optString("data_source_select"));
				entity.setDataFilter(object.optString("dataFilter"));
				entity.setcOptionType(object.optString("cOptionType"));
				entity.setColumnRank(object.optInt("columnRank"));
				list.add(entity);
			}
		}
		return list;
	}
	
	/**
	 * 解析txt文档
	 * @param inputStream
	 * @param fieldTempEntities
	 * @param cardEnitties
	 * @param collegeInfoEntities
	 * @param readerFieldMap
	 * @param collegeFieldMap
	 * @throws Exception
	 */
	private void parseTxtFile(InputStream inputStream,List<UploadReaderFieldTempEntity> fieldTempEntities
		,Map<String, Field> bibliosFieldMap){
		//获取txt文档的编码
		/*String charset = getFileEncode(inputStream);
		if(charset.equals("asci")){
			charset = "GBK";
		}*/
		try {
			//txt格式文件只有一列,只有一个规则
			UploadReaderFieldTempEntity fieldTempEntity = fieldTempEntities.get(0);
			String optionType = fieldTempEntity.getcOptionType();
			String splitFilter = "";
			//判断是单行还是多行合并
			//获取字段。数据库中“字段”包含：英文、中文回显两部分（code|name 编码|名称）。获取英文部分。
			String fields = fieldTempEntity.getData_source_select().split(" ")[0];
			if("multiple-text".equals(optionType)){
				//如果是多行合并，获取行分隔符
				splitFilter = translationSplitFilter(fieldTempEntity.getDataFilter());
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			String tempReadLine = "";
			
			while((tempReadLine = bufferedReader.readLine()) != null){
				BibliosAndBook bibBookEnitty = new BibliosAndBook();
				boolean flag = false;
				if(!StringUtils.isBlank(splitFilter)){
					String[] valueArr = tempReadLine.split(splitFilter);
					String[] keyArr = fields.split(splitFilter);
					List<String> listA = Arrays.asList(keyArr);
		            List<String> listB = new ArrayList<String>(listA);
		            List<String> listC = Arrays.asList(valueArr);
		            List<String> listD = new ArrayList<String>(listC);
		            //判断是否存在regdate,如果不存在添加这个key
					for(String key:keyArr){
						if("regdate".equals(key.trim())){
							flag = true;
							break;
						}
					}
					if(flag==false){
						listB.add("regdate");
						listD.add("");
					}
					keyArr = (String[])listB.toArray(new String[listB.size()]);
					valueArr = (String[])listD.toArray(new String[listD.size()]);
					for(int i = 0;i<keyArr.length;i++){
						String key = keyArr[i];
						String value = "";
						if(valueArr.length > i){
							value = valueArr[i];
						}						
						setFieldValue(bibliosFieldMap,key,value,bibBookEnitty);						
					}
				}else{
					
					setFieldValue(bibliosFieldMap,fields,tempReadLine,bibBookEnitty);
					
				}
				try{
					//插入数据
					inseriBookItem(bibBookEnitty);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
		
			bufferedReader.close();
		} catch (Exception e) {
			//throw new RuntimeException(e);
		}
	}
	
	/**
	 * 解析excel文档
	 * @param inputStream
	 * @param fieldTempEntities
	 * @param cardEnitties
	 * @param collegeInfoEntities
	 * @param readerFieldMap
	 * @param collegeFieldMap
	 * @throws Exception
	 */
	private void parseXlsxFile(InputStream inputStream,List<UploadReaderFieldTempEntity> fieldTempEntities
			,Map<String, Field> bibliosFieldMap,String prefix) throws Exception{
		Workbook workbook = null;
		boolean flag = false;
		if("xlsx".equals(prefix)){
			workbook = new XSSFWorkbook(inputStream);
		}else{
			workbook = new HSSFWorkbook(inputStream);
		}
		
		for(UploadReaderFieldTempEntity fieldTempEntity : fieldTempEntities){
			String[] fieldArr = fieldTempEntity.getData_source_select().split(" ");
			//判断是否存在regdate,如果不存在添加这个key
			if("regdate".equals(fieldArr[0])){
				flag = true;
				break;
			}
		}
		if(flag==false){
			//如果不存在regdate字段，添加一个
			UploadReaderFieldTempEntity entity = new UploadReaderFieldTempEntity();
			entity.setColumnRank(fieldTempEntities.size()+1);
			entity.setcOptionType("text");
			entity.setData_source_select("regdate ");
			entity.setDataFilter(" ");
			fieldTempEntities.add(entity);
		}
		//获取工作簿
		int sheets = workbook.getNumberOfSheets();
		for(int m =0;m<sheets;m++){
			//获取excel工作表对象
			Sheet sheet = workbook.getSheetAt(m);
			if(sheet == null) return;
			//获取数据的总行数
			int totalRows = sheet.getLastRowNum();
			for(int i = sheet.getFirstRowNum()+1;i<totalRows+1;i++){
				Row row = sheet.getRow(i);
				//当行中的内容为空，则直接跳过
				if(row == null) continue;
				BibliosAndBook bibBookEntity = new BibliosAndBook();
				//BookItemEntity bookItem = new BookItemEntity();
				for(UploadReaderFieldTempEntity fieldTempEntity : fieldTempEntities){
					//取出对应的excel的值，由于对应的excell是从0开始读，但配置中使用的是从1开始，所以要减去-1处理
					Cell cell = row.getCell(fieldTempEntity.getColumnRank()-1);
					String[] fieldArr = fieldTempEntity.getData_source_select().split(" ");
					if("text".equals(fieldTempEntity.getcOptionType())){
						//当单元格中的内容为空，则直接跳过
						if((!"regdate".equals(fieldArr[0])&&cell == null)&&(!"createtime".equals(fieldArr[0])&&cell == null)){
							continue;
						}else if("regdate".equals(fieldArr[0])||"createtime".equals(fieldArr[0])){
							if (cell == null || cell.getCellType()==Cell.CELL_TYPE_BLANK) {  
								cell  =row.createCell(fieldTempEntity.getColumnRank()-1);
								//当cell中regdate为空时，赋当前时间
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
								Date date = new Date();
								String a  =df.format(date);
								cell.setCellValue(a);
								//cell.setCellType(0);
								//cell = newCell;  
							}  
						}
						switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC:
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							    if(HSSFDateUtil.isCellDateFormatted(cell)){ 
							    	//如果为时间格式，时间格式进行转换
							    	String date = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
									//如果是单行，直接赋值
									setFieldValue(bibliosFieldMap,fieldArr[0],date,bibBookEntity);
									//setFieldValue(bookItemFieldMap, fieldArr[0], cell.toString(),bookItem);
							    }else{
							    	//其他格式默认为数字格式进行处理
							    	DecimalFormat df = new DecimalFormat("0");  
			                        String cellValue = df.format(cell.getNumericCellValue());
			                        setFieldValue(bibliosFieldMap,fieldArr[0],cellValue,bibBookEntity);
							    }
							    break;
						   default:
							   setFieldValue(bibliosFieldMap,fieldArr[0],cell.toString(),bibBookEntity);
							
						}
					}/*else if("multiple-text".equalsIgnoreCase(fieldTempEntity.getcOptionType())){
						//如果是多行
						String splitFilter = "";
						splitFilter = translationSplitFilter(fieldTempEntity.getDataFilter());//转译分割符
						
						//TODO 需要考虑excel单元格为空的情况
						String[] valueArr = cell.toString().split(splitFilter);
						String[] keyArr = fieldArr[0].split(splitFilter);
						for(int j = 0;j<keyArr.length;j++){
							setFieldValue(bibliosFieldMap,keyArr[j],valueArr[j],bibBookEntity);
							//setFieldValue(bookItemFieldMap,keyArr[j],valueArr[j],bookItem);
						}
						for(int j = 0;j<1;j++){
							setFieldValue(bibliosFieldMap,keyArr[j],cell.toString(),bibBookEntity);
							//setFieldValue(bookItemFieldMap,keyArr[j],valueArr[j],bookItem);
						}
					}*/
				}
				try{
					//插入数据
					inseriBookItem(bibBookEntity);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	/**插入读者卡信息*/
	private void inseriBookItem(BibliosAndBook bibBookEntity){
		int flag = 0;
		//try{
			bibBookEntity.setLib_idx(lib_idx);
			BibliosEntity biblios = new BibliosEntity();
			String isbn = bibBookEntity.getISBN();
			String title = bibBookEntity.getTitle();
			String author = bibBookEntity.getAuthor();
			String regdate = bibBookEntity.getRegdate();
			biblios.setISBN(isbn);
			biblios.setAuthor(author);
			biblios.setTitle(title);
			BibliosEntity bibliosExt = null;
			BibliosEntity bibliosExt2 = null;
			BibliosAndBook bibliosAndBook2=null;
			String key = null;
        	Integer bibliosIdx = 0;
        	
			if(!StringUtils.isEmpty(isbn)){
        		bibliosExt = bibliosDao.queryBibliosByIsbnMultiCondition(biblios);
        	}else{
        		if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(author))
        			bibliosExt = bibliosDao.queryBibliosByTitleAndAuthor(biblios);
        	}
        	if(bibliosExt == null){
        		//flag =1;
        		try{
        			bibliosAndBook2 = bibliosDao.insertBiblios2(bibBookEntity);
        			//successNum ++;
        		}catch(Exception e){
        			//failNum ++;
        			//Throwable throwable = e.getCause();
        			if(failNum <=3000){
        				key = bibBookEntity.getBook_barcode()==null?failNum+" 条码号未知  ":failNum+" 条码号"+bibBookEntity.getBook_barcode()+" ";
        				errorLogs.put(key,"biblios表插入出错"+e.getCause());
        			}else{
        				errorLogs.clear();
        			}
        			//e.printStackTrace();
        			//throw new RuntimeException(e);
        		}
        		//flag =2;
        		bibliosAndBook2.setRegdate(regdate);
        		try{
        			bookItemDao.insertBookItem2(bibliosAndBook2);
        			successNum ++;
        		}catch(Exception e){
        			failNum ++;
        			if(failNum <=3000){
        				key = bibBookEntity.getBook_barcode()==null?failNum+" 条码号未知  ":failNum+" 条码号"+bibBookEntity.getBook_barcode()+" ";
        				errorLogs.put(key,"bookitem表插入出错"+e.getCause());
        			}else{
        				errorLogs.clear();
        			}
        			//e.printStackTrace();
        			//throw new RuntimeException(e);
        		}
        	}else{  //如果存在isbn或者author和title存在，则直接插入bookitem,对biblios不操作
        		bibliosExt2 = bibliosDao.queryBibliosByISBN(biblios);
        		if(bibliosExt2==null){
        			if((!"".equals(title)||title!=null)&&(!"".equals(author)||author!=null)){
        				bibliosExt2 = bibliosDao.queryBibliosByTitleAndAuthor(biblios);
        			}
        			if(bibliosExt2!=null){
        				bibBookEntity.setBib_idx(bibliosExt2.getBib_idx());
        				bibBookEntity.setRegdate(regdate);
                		try{
                			bookItemDao.insertBookItem2(bibBookEntity);
                			successNum ++;
                		}catch(Exception e){
                			failNum ++;
                			if(failNum <=3000){
                				key = bibBookEntity.getBook_barcode()==null?failNum+" 条码号未知  ":failNum+" 条码号"+bibBookEntity.getBook_barcode()+" ";
                				errorLogs.put(key,"bookitem表插入出错"+e.getCause());
                			}else{
                				errorLogs.clear();
                			}
                			//e.printStackTrace();
                			//throw new RuntimeException(e);
                		}
        			}
        		}
        		/*
        		failNum ++;
        		key = bibBookEntity.getBook_barcode()==null?failNum+" 条码号未知  ":failNum+" 条码号"+bibBookEntity.getBook_barcode()+" ";
        		//如果失败条数太多，直接返回导入成功、失败条数，防止占用太多内存
        		errorLogs.put(key,"bookitem/biblios表插入数据已经存在！");*/
        	}
        	/*}catch(Exception e){
			failNum ++;
			Throwable throwable = e.getCause();
			if(failNum <=3000){
				String methodName = "";
				if(flag == 1){
					methodName = "biblios表插入出错";
				}else if(flag == 2){
					methodName = "bookitem表插入出错 ";
				}
				//如果失败条数太多，直接返回导入成功、失败条数，防止占用太多内存
				errorLogs.put(bibBookEntity.getBook_barcode(),methodName+throwable.toString());
			}else{
				errorLogs.clear();
			}
			//e.printStackTrace();
		}*/
		
	}
	
	/**设置值*/
	public <T> void setFieldValue(Map<String, Field> fieldMap,String key,Object value,T t) throws Exception{
		if("unkown".equals(key)) return;
		if(fieldMap.containsKey(key)){
			Field field = fieldMap.get(key);
			field.setAccessible(true);
			String str = field.getGenericType().toString();
			//System.out.print(str);
			if(field.getType() == Integer.class) {
				if(value.toString() != null ){
					field.set(t, Integer.parseInt(value.toString().trim()));
				}else{
					field.set(t, "");
				}
			}else{
				if(value.toString() != null && !"".equals(value)){
					field.set(t, value);
				}else{
					//System.out.println("***********"+field.getName());
					if("regdate".equals(field.getName().trim())){
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						field.set(t,df.format(new Date()));
					}else{
						field.set(t, value);
					}
				}
			}
		}
	}
	
	/***
	 * 转译分割符
	 * | -----> \\|
	 * @param filter
	 * @return
	 */
	public String translationSplitFilter(String filter){
		if("|".equals(filter)){
			return "\\|";
		}
		return filter;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	public Map<String, String> getErrorLogs() {
		return errorLogs;
	}
	public void setErrorLogs(Map<String, String> errorLogs) {
		this.errorLogs = errorLogs;
	}
	
	
}
