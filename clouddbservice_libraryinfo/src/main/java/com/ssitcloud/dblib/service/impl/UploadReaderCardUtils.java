package com.ssitcloud.dblib.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.CollegeInfoDao;
import com.ssitcloud.dblib.dao.ReaderCardDao;
import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;
import com.ssitcloud.readermgmt.entity.UploadReaderFieldTempEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UploadReaderCardUtils {
	
	private  int lib_idx = 0;
	private String lib_id = "";
	private ReaderCardDao cardDao = null;
	private CollegeInfoDao collegeInfoDao = null;
	Map<String, String> errorLogs = null;
	private int successNum = 0;//插入成功的数据
	private int failNum = 0;//失败的数据
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public UploadReaderCardUtils(ReaderCardDao cardDao,CollegeInfoDao collegeInfoDao){
		this.cardDao = cardDao;
		this.collegeInfoDao = collegeInfoDao;
		errorLogs = new HashMap<>();
	}
	
	/**
	 * 解析上传文件
	 * @param file
	 * @param cardEnitties
	 * @param collegeInfoEntities
	 * @throws Exception
	 */
	public void parseUploadFile(CommonsMultipartFile file,String req) throws Exception{
		//获取文件名称
		String fileName = file.getOriginalFilename();
		//获取后缀
		String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
		//获取上传流
		InputStream inputStream = file.getInputStream();
		//将读者类的字段封装到map集合中,key:字段名称 ,value:字段对象
		Map<String,Field> readerFieldMap = convertBeanToMap(ReaderCardEntity.class);
		//将学校信息的字段封装到map集合中,key:字段名称 ,value:字段对象
		Map<String,Field> collegeFieldMap = convertBeanToMap(CollegeInfoEntity.class);
		//获取模板配置
		List<UploadReaderFieldTempEntity> fieldTempEntities = queryTempConfig(req.toString());
		if("txt".equalsIgnoreCase(prefix)){
			//获取编码
			String charset = getFileEncode(file.getInputStream());
			if("asci".equals(charset)){
				charset = "GBK";
			}
			parseTxtFile(inputStream,fieldTempEntities,readerFieldMap,collegeFieldMap,charset);
		}
		else{
			parseXlsxFile(inputStream, fieldTempEntities, readerFieldMap, collegeFieldMap,prefix);
		}
		
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
		,Map<String, Field> readerFieldMap,Map<String, Field> collegeFieldMap,String charset)throws Exception{
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
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,charset));
		String tempReadLine = "";
		
		while((tempReadLine = bufferedReader.readLine()) != null){
			ReaderCardEntity cardEnitty = new ReaderCardEntity();
			CollegeInfoEntity collegeInfoEntity = new CollegeInfoEntity();
			if(!StringUtils.isBlank(splitFilter)){
				String[] valueArr = tempReadLine.split(splitFilter);
				String[] keyArr = fields.split(splitFilter);
				for(int i = 0;i<keyArr.length;i++){
					String key = keyArr[i];
					String value = "";
					if(valueArr.length > i){
						value = valueArr[i];
					}
					
					setFieldValue(readerFieldMap,key,value,cardEnitty);
					setFieldValue(collegeFieldMap,key,value,collegeInfoEntity);
				}
			}else{
				setFieldValue(readerFieldMap,fields,tempReadLine,cardEnitty);
				setFieldValue(collegeFieldMap,fields,tempReadLine,collegeInfoEntity);
			}
			//插入数据
			inseriReaderCard(cardEnitty,collegeInfoEntity);
		}
		bufferedReader.close();
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
			,Map<String, Field> readerFieldMap,Map<String, Field> collegeFieldMap,String prefix) throws Exception{
		
		Workbook workbook = null;
		if("xlsx".equals(prefix)){
			workbook = new XSSFWorkbook(inputStream);
		}else{
			workbook = new HSSFWorkbook(inputStream);
		}
		//获取工作簿
		int sheets = workbook.getNumberOfSheets();
		for(int m =0;m<sheets;m++){
			//获取excel工作表对象
			Sheet sheet = workbook.getSheetAt(m);
			if(sheet == null) return;
			//获取数据的总行数
			int totalRows = sheet.getLastRowNum();
			for(int i = sheet.getFirstRowNum()+1;i<=totalRows;i++){
				Row row = sheet.getRow(i);
				if(row == null) continue;
				ReaderCardEntity cardEntity = new ReaderCardEntity();
				CollegeInfoEntity collegeInfoEntity = new CollegeInfoEntity();
				for(UploadReaderFieldTempEntity fieldTempEntity : fieldTempEntities){
					//取出对应的excel的值
					Cell cell = row.getCell(fieldTempEntity.getColumnRank()-1);
					if(cell == null) continue;
					String[] fieldArr = fieldTempEntity.getData_source_select().split(" ");
					
					if("text".equals(fieldTempEntity.getcOptionType())){
						Object cellValue = null;
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								cellValue = cell.getDateCellValue();
							}else{
								DecimalFormat df = new DecimalFormat("0");  
		                        cellValue = df.format(cell.getNumericCellValue());
							}
						}else{
							cellValue = cell.toString();
						}
						//如果是单行，直接赋值
						setFieldValue(readerFieldMap, fieldArr[0],cellValue,cardEntity);
						setFieldValue(collegeFieldMap, fieldArr[0],cellValue,collegeInfoEntity);
					}else if("multiple-text".equalsIgnoreCase(fieldTempEntity.getcOptionType())){
						//如果是多行
						String splitFilter = "";
						splitFilter = translationSplitFilter(fieldTempEntity.getDataFilter());//转译分割符
						
						String[] valueArr = cell.toString().split(splitFilter);
						String[] keyArr = fieldArr[0].split(splitFilter);
						for(int j = 0;j<keyArr.length;j++){
							String value = "";
							if(valueArr.length > j){
								value = valueArr[j];
							}
							setFieldValue(readerFieldMap,keyArr[j],value,cardEntity);
							setFieldValue(collegeFieldMap,keyArr[j],value,collegeInfoEntity);
						}
					}
				}
				inseriReaderCard(cardEntity, collegeInfoEntity);
			}
		}
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
			Map<Object,Object> configEntity = (Map<Object, Object>) resultEntity.getResult();
			Object uploadReaderStr = configEntity.get("import_tpl_value");
			lib_idx = Integer.parseInt(configEntity.get("library_idx").toString());
			lib_id = configEntity.get("lib_id").toString();
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
	
	/**插入读者卡信息*/
	private void inseriReaderCard(ReaderCardEntity cardEntity,CollegeInfoEntity collegeInfoEntity){
		try{
			cardEntity.setActual_card_no(cardEntity.getCard_no());
			cardEntity.setLib_idx(lib_idx);
			collegeInfoEntity.setActual_card_no(cardEntity.getCard_no());
			collegeInfoEntity.setLib_idx(lib_idx);
			//处理兰州大学的读者卡
			if(dealLZULIB(cardEntity, collegeInfoEntity)){
				cardDao.insertReaderCard(cardEntity);
				collegeInfoDao.addCollegeInfo(collegeInfoEntity);
				successNum ++;
			}
			
		}catch(Exception e){
			failNum ++;
			Throwable throwable = e.getCause();
			if(failNum <=3000){
				//如果失败条数太多，直接返回导入成功、失败条数，防止占用太多内存
				errorLogs.put(cardEntity.getCard_no(),throwable.toString());
			}else{
				errorLogs.clear();
			}
			e.printStackTrace();
		}
	}
	
	/**处理兰州大学的读者卡*/
	private boolean dealLZULIB(ReaderCardEntity cardEntity,CollegeInfoEntity collegeInfoEntity){
		if("LZULIB".equalsIgnoreCase(lib_id)){
			String actual_card_no = cardEntity.getCard_no().substring(0, cardEntity.getCard_no().length()-1);
			cardEntity.setActual_card_no(actual_card_no);
			collegeInfoEntity.setActual_card_no(actual_card_no);
			
			//读者证不满13位不插入
			if(cardEntity.getCard_no() == null || cardEntity.getCard_no().length() <13){
				return false;
			}
			//入学年份：从第二位开始到第五位
			String grade = cardEntity.getCard_no().substring(1, 5);
			collegeInfoEntity.setGrade(grade);
			//入学年份
			String admissionDate = grade+"-09-01";
			try{
				collegeInfoEntity.setAdmission_date(dateFormat.parse(admissionDate));
			}catch(Exception e){
				collegeInfoEntity.setAdmission_date(null);
			}
			//以8开头的是教职工
			if(collegeInfoEntity.getActual_card_no().startsWith("8")){
				collegeInfoEntity.setReader_type("教师");
			}
		}
		return true;
		
	}
	
	
	/**设置值*/
	public <T> void setFieldValue(Map<String, Field> fieldMap,String key,Object value,T t) throws Exception{
 		if("unkown".equals(key)) return;
		if(value == null || value.toString().length() <= 0) return;
 		if(fieldMap.containsKey(key)){
			Field field = fieldMap.get(key);
			
			if(field.getType() == Date.class && value instanceof String){
				value = dateFormat.parse(value.toString());
			}
			field.setAccessible(true);
			field.set(t, value);
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

	public static String getFileEncode(InputStream inputStream) {
		String charset ="asci";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(inputStream);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "Unicode";//UTF-16LE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "Unicode";//UTF-16BE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int len = 0;
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) //单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) 
                        //双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) { //也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
                //TextLogger.getLogger().info(loc + " " + Integer.toHexString(read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) {
                }
            }
        }
        return charset;
	}
	
	
	
	

}
