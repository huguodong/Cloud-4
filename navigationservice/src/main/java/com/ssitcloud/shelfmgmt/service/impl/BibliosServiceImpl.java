package com.ssitcloud.shelfmgmt.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.shelfmgmt.dao.BibliosDao;
import com.ssitcloud.shelfmgmt.entity.BibliosBean;
import com.ssitcloud.shelfmgmt.service.BibliosService;

@Service
public class BibliosServiceImpl implements BibliosService {

	@Resource
	private BibliosDao bibliosDao;
	
	private ByteArrayOutputStream tempArrayOutputStream = new ByteArrayOutputStream();//用来缓存剩余的不完整的数据
	private ByteArrayOutputStream dataLengthArr = new ByteArrayOutputStream(5);//用来保存
	private byte[] buffArr = new byte[2048*2000];//缓冲
	private int startIndex = 0;
	private int endIndex = 0;//读到缓存中的字节数量
	private int cacheMarcLength = 0;//被缓存的marc完整长度
	private int count = 0;//记录数
	private Map<String, String> datas = new HashMap<>();
	
	@Override
	public ResultEntity importMarcData(String req) {
		ResultEntity result = new ResultEntity();
		int re = 0;
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
            File file=new File(jsonObject.getString("file"));
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStream inputStream = readFile(file);//读取文件
                while ((endIndex = inputStream.read(buffArr)) != -1){
					startIndex = 0;
					dealData();
				}
                inputStream.close();
                re = 1;
            }else{
            	System.out.println("找不到指定的文件");
            	re = 0;
            }
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}finally{
			try{
				if(tempArrayOutputStream != null){
					tempArrayOutputStream.close();
				}
				if(dataLengthArr != null){
					dataLengthArr.close();
				}
			}catch(Exception e){
				/////
			}
		}
		return result;
	}

	@Override
	public ResultEntity queryMarcDataByItemCtrlNum(String req) {
		ResultEntity result = new ResultEntity();
		try {
			BibliosBean biblios = bibliosDao.queryMarcDataByItemCtrlNum(JsonUtils.fromJson(req, BibliosBean.class));
			if(biblios != null){
				result.setState(true);
				result.setResult(biblios);
			}else{
				result.setState(false);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**处理数据*/
	public void dealData() throws Exception{
		//处理缓存
		dealCacheData();
		//正常读取数据,如果读取的数据小于5，直接存放到缓存数组中
		if((endIndex - startIndex) < 5){
			tempArrayOutputStream.write(buffArr, startIndex, endIndex - startIndex);
			//标识缓存中的字节小于5，无法计算一条完整数据的长度
			return;
		}
		
		dataLengthArr.reset();
		for(int i =0;i<5;i++){
			dataLengthArr.write(buffArr[i+startIndex]);
		}

		cacheMarcLength = Integer.parseInt(new String(dataLengthArr.toByteArray(),"UTF-8"));
		int length = Integer.parseInt(new String(dataLengthArr.toByteArray(),"utf-8"));//数据长度
		//如果数据不完整，存入到缓存中去
		if((endIndex - startIndex) < length ){
			tempArrayOutputStream.write(buffArr, startIndex,endIndex - startIndex );
			return;
		}
		//正常读取
		tempArrayOutputStream.write(buffArr, startIndex, length);
		//解析
		readMarcDataField(tempArrayOutputStream.toByteArray());
		//清空缓存
		tempArrayOutputStream.reset();
		startIndex = startIndex + length;
		//递归调用
		dealData();
	}
	
	
	/**处理缓存*/
	public void dealCacheData() throws Exception{
		dataLengthArr.reset();
		//如果有缓存
		if(tempArrayOutputStream.size() > 0){
			//计算缓存中的数据长度，如果小于5,先计算一条完整的数据长度,一段数据的前五位
			if(tempArrayOutputStream.size() < 5){
				dataLengthArr.write(tempArrayOutputStream.toByteArray());
				for(int i = 0;i<5-tempArrayOutputStream.size();i++){
					dataLengthArr.write(buffArr[i]);
				}
				//计算长度
				cacheMarcLength = Integer.parseInt(new String(dataLengthArr.toByteArray(),"utf-8"));
			}
			
			//如果再次加载还是不够凑成一条完整的数据，仍然将它加载到字节缓存流里面
			if(cacheMarcLength > (tempArrayOutputStream.size()+buffArr.length)){
				tempArrayOutputStream.write(buffArr, startIndex, endIndex);
				return;
			}
			//计算还需要向tempArrayOutputStream添加多少的字节
			int remainderLength = cacheMarcLength-tempArrayOutputStream.size();
			tempArrayOutputStream.write(buffArr, startIndex, remainderLength);
			readMarcDataField(tempArrayOutputStream.toByteArray());
			//读完之后，设置下一次的开始读取起始点
			startIndex = startIndex + remainderLength;//数据读了remainLength个，在数组中下标是remainLength -1
			//清空缓存
			tempArrayOutputStream.reset();
		}
		
	}
	
	
	
	/**读取文件*/
	public InputStream readFile(File file){
		try{
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedInputStream bufInputSteam = new BufferedInputStream(fileInputStream);
			return bufInputSteam;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	/**解析*/
	public void readMarcDataField(byte[] marcData) throws Exception{
		
		//获取数据的总长度
		byte[] marcLengthArr = getMarcLength(marcData);
		int marcLength = Integer.parseInt(new String(marcLengthArr,"utf-8"));
		//获取数据基地址
		byte[] marcAddressArr = getMarcAddressdir(marcData);
		//数据基地址 的长度
		int marcAddressLength = Integer.parseInt(new String(marcAddressArr,"utf-8"));
		//获取目次区数据
		byte[] directoryAreaArr = getDirectoryArea(marcData, marcAddressLength);
		//将目次区的数据进行分割 xxx xxxx(长度) xxxxx（起始位置）每 12位进行分割
		String directoryAreaString = new String(directoryAreaArr);
		int n = directoryAreaArr.length / 12;
		String directoryAreaData[] = new String[n];
		for (int i = 0; i < n; i++) {
			directoryAreaData[i] = directoryAreaString.substring(i * 12, (i + 1) * 12);
		}
		//获取数据字段区的数据
		String marcFieldData[][] = new String[n][2];
		datas.clear();
		for (int i = 0; i < n; i++) {
			marcFieldData[i][0] = directoryAreaData[i].substring(0, 3);//字段名
			int length = Integer.parseInt(directoryAreaData[i].substring(3, 7));//长度
			int start = Integer.parseInt(directoryAreaData[i].substring(7));//起始位置
			byte temp[] = new byte[length-1];
			for (int j = start; j < start +length-1; j++) {
				if(marcData[j+marcAddressLength]==31){//分隔符，替换成分割线
					temp[j - start] = 124;
				}else{
					temp[j - start] = marcData[j+marcAddressLength];
				}
			}
			marcFieldData[i][1] = new String(temp,"utf-8");
			System.out.print(marcFieldData[i][0]);
			System.out.print(marcFieldData[i][1]);
			if(!datas.containsKey(marcFieldData[i][0].trim())){
				datas.put(marcFieldData[i][0].trim(),marcFieldData[i][1].trim());
			}
			System.out.println();
		}
		data2Bean(datas);
		count++;
		if(count == 16308){
			System.out.println(count);
		}
		System.out.println("数量："+count+"-------------------------------------------------------------");
		
	}
	
	/**数据注入到bean*/
	public void data2Bean(Map<String,String> datas) throws IOException{
		BibliosBean bean = new BibliosBean();
		
		String field_010 = datas.get("010");
		String field_020 = datas.get("020");
		if( !isNull(field_010)){
			bean.setISBN(getString("|a", field_010));
			bean.setPrice(getString("|d", field_010));
		}else if(!isNull(field_020)){
			bean.setISBN(getString("|a", field_020));
			bean.setPrice(getString("|c", field_020));	
		}
		
		String field_200 = datas.get("200");
		String field_245 = datas.get("245");
		if(!isNull(field_200)){
			bean.setTitle(getString("|a", field_200));
			bean.setTitleen(getString("|A", field_200));
			bean.setAuthor(getString("|f", field_200));
			bean.setTranslator(getString("|g", field_200));
		}else if(!isNull(field_245)){
			bean.setTitle(getString("|a", field_245));
			int len = bean.getTitle().lastIndexOf(":");
			int len2 = bean.getTitle().lastIndexOf("/");
			int len3 = bean.getTitle().lastIndexOf("=");
			if(len>0){
				bean.setTitle(bean.getTitle().substring(0, len).trim());
			}else if(len2>0){
				bean.setTitle(bean.getTitle().substring(0, len2).trim());
			}else if(len3>0){
				bean.setTitle(bean.getTitle().substring(0, len3).trim());
			}
			
			if("7-5059-0409-4".equals(bean.getISBN())){
				System.out.println(bean.getISBN());
				System.out.println("ssa");
			}
			
			bean.setAuthor(getString("|c", field_245));
			if(bean.getAuthor().indexOf("=")>0){
				int len4 = bean.getAuthor().indexOf("/");
				if(len4 > 0){
					bean.setAuthor(bean.getAuthor().substring(len4+1));
				}
			}
			
			if(bean.getAuthor().length() > 200 ){
				bean.setAuthor(bean.getAuthor().substring(190)+"...");
			}
		}
		
		String field_035 = datas.get("035");
		if(!isNull(field_035)){
			bean.setItemCtrlNum(getString("|a", field_035));
			int len = bean.getItemCtrlNum().lastIndexOf(")");
			bean.setItemCtrlNum(bean.getItemCtrlNum().substring(len+1).trim());
		}
		
		String field_101 = datas.get("101");
		String field_041 = datas.get("041");
		if(!isNull(field_101)){
			bean.setLang(getString("|a", field_101));
			if(bean.getLang().length() > 20){
				String tempStr = bean.getLang().substring(0,17);
				bean.setLang(tempStr+"...");
			}
		}else if(field_041!= null && field_041.length() > 0){
			bean.setLang(getString("|a", field_041));
			if(bean.getLang().length() > 20){
				String tempStr = bean.getLang().substring(0,17);
				bean.setLang(tempStr+"...");
			}
		}
		
		String field_690 = datas.get("690");
		if(!isNull(field_690)){
			bean.setCallNo(getString("|a", field_690));
		}
		
		String field_210 = datas.get("210");
		String field_260 = datas.get("260");
		if(!isNull(field_210)){
			bean.setPubAddress(getString("|a", field_210));
			bean.setPublish(getString("|c", field_210));
			bean.setPubDate(getString("|d", field_210));
			
		}else if(!isNull(field_260)){
			bean.setPubAddress(getString("|a", field_260));
			bean.setPublish(getString("|b", field_260));
		}
		
		String field_215 = datas.get("215");
		if(!isNull(field_215)){
			bean.setBook_page(getString("|a", field_215));
			bean.setBook_size(getString("|d", field_215));
			if(bean.getBook_size().length() > 30){
				bean.setBook_size(bean.getBook_size().substring(0, 31));
			}
		}
		try{
			bibliosDao.addBiblios(bean);
		}catch(Exception e){
			LogUtils.error(e.getMessage()+bean.toString());
		}
		System.out.println("添加成功！");
	}
	
	
	
	public String getString(String tag,String resource){
		
		if(resource.contains(tag)){
			int len = resource.indexOf(tag);
			String tempString = resource.substring(len+2);
			int index = tempString.indexOf("|");
			int index_1 = tempString.indexOf(":|");
			int index_2 = tempString.indexOf(";|");
			if(index_2 > 0)
				index = index_1 < index?index_1:index;
			if(index_2 > 0)
				index = index_2 < index?index_2:index;
			if(index > 0){
				return tempString.substring(0, index);
			}else{
				return tempString;
			}
		}
		return "";
	}
	
	
	
	/***
	 * 获取目次区的数据
	 * 目次区的数据：数据基址长度-记录头标（24）-分隔符（1）
	 * @param marcData
	 * @param marcAddressLength= 目次区+记录头标
	 * @return
	 */
	public byte[] getDirectoryArea(byte[] marcData,int marcAddressLength){
		
		int length = marcAddressLength - 24 - 1;
		byte arr[] = new byte[length];
		for (int i = 0; i < length; i++) {
			arr[i] = marcData[i + 24];
		}
		return arr;
		
	}
	
	/***
	 * 获取数据总长度，起始：记录头标：0,5个字节
	 * @param marcData
	 * @return
	 */
	public byte[] getMarcLength(byte[] marcData){
		byte arr[] = new byte[5];
		for (int i = 0; i < 5; i++) {
			arr[i] = marcData[i];
		}
		return arr;
	}
	
	/***
	 * 获取数据基地址,起始：记录头标的12，5个字节
	 * 该地址存放的是数据字段区前面的数据长度
	 * @param marcData
	 * @return
	 */
	public byte[] getMarcAddressdir(byte[] marcData){
		byte arr[] = new byte[5];//，从12位开始
		for (int i = 0; i < 5; i++) {
			arr[i] = marcData[i + 12];
		}
		return arr;
	}
	
	
	public boolean isNull(String data){
		if(data == null || data.length() <= 0){
			return true;
		}else{
			return false;
		}
		
	}

}
