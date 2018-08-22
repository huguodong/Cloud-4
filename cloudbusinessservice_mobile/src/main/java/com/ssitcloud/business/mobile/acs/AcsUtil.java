package com.ssitcloud.business.mobile.acs;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import com.ssitcloud.authentication.entity.LibraryAcsLogininfoEntity;
import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.common.entity.ACSConfigEntity;
import com.ssitcloud.mobile.entity.AcsBookInfoByReconEntity;
import com.ssitcloud.mobile.entity.AcsBookInfoEntity;
import com.ssitcloud.mobile.entity.AcsInReservationBookEntity;
import com.ssitcloud.mobile.entity.AcsQueryReservationEntity;
import com.ssitcloud.mobile.entity.AcsReaderCardEntity;
import com.ssitcloud.mobile.entity.AcsRenewEntity;
import com.ssitcloud.mobile.entity.AcsReservationBookEntity;

import net.sf.json.util.JSONUtils;

/**
 * acs操作类,使用后必须关闭此操作类
 * @author LXP
 * @version 创建时间：2017年3月2日 下午5:00:55
 */
public class AcsUtil implements Closeable{
	public static enum user_card_state{OTHER_ERROR,CARE_INVALID,CARE_PASSWORD_ERROR,CARE_SUCCESS}
	public static enum renew_sate{OTHER_ERROR,RENEW_SUCCESS,RENEW_FAIL}
	
	private ACSConfigEntity config;
	private AcsAdapter adapter;
	//
	private boolean isLogin = false;//是否登陆成功了
	
	
	private Socket socket;
	private int TIME_OUT = 1_800;

	private OutputStream outputStream;
	private BufferedReader br;

	/**
	 * 
	 * @param libraryAcsLogininfo 图书馆配置
	 * @param config 海恒中间件配置
	 */
	public AcsUtil(LibraryEntity libraryEntity,LibraryAcsLogininfoEntity libraryAcsLogininfo,ACSConfigEntity config) {
		this.config = config;
		this.adapter = new AcsAdapter(libraryEntity,libraryAcsLogininfo);
	}
	
	
	/**
	 * 登陆尝试
	 * @return
	 * @throws IOException 连接发生异常或者读取数据失败时抛出
	 */
	public boolean login() throws IOException{
		if(socket == null){
			socket = new Socket(config.getHost(), config.getPort());
			socket.setSoTimeout(TIME_OUT);
		}
		//检查socket是否正常
		if(!socket.isConnected() || socket.isClosed()){
			try{
				socket.close();
			}catch(IOException e){
				LogUtils.info("关闭socket出错");
			}
			socket = new Socket(config.getHost(), config.getPort());
			socket.setSoTimeout(TIME_OUT);
		}
		
		outputStream = socket.getOutputStream();
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		try{
			String data = adapter.getLoginJson();
			sendData(data);
		}catch(IOException e){
			LogUtils.error("登陆acs服务出错，远程计算机关闭的连接"+config);
			throw new SocketException("登陆acs服务出错，远程计算机关闭的连接");
		}
		
		try{
			String resultStr = getOutData();
			if (!JSONUtils.mayBeJSON(resultStr)) {
				LogUtils.error(config + " 返回登陆信息格式有误=》" + resultStr);
				throw new IOException("返回登陆信息格式有误");
			}

			Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
			Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
			String result = String.valueOf(returnData.get("OPERATIONRESULT"));
			if ("1".equals(result)
					|| "y".equalsIgnoreCase(result) 
					|| "true".equalsIgnoreCase(result)) {
				isLogin = true;
				return true;
			} else {
				LogUtils.info("登陆失败了，返回信息==>"+resultStr);
				isLogin = false;
				return false;
			}
		}catch (Exception e) {
			LogUtils.info(getClass()+"异常",e);
			isLogin = false;
			return false;
		}
	}
	
	/**
	 * 获取绑定卡的读者信息
	 * @param readercard
	 * @return
	 * @throws IOException 连接发生异常或者读取数据失败时抛出
	 */
	public Map<String, Object> obtainCardInfo(AcsReaderCardEntity readercard) throws IOException{
		if(!isLogin){
			login();
		}
		if(socket.isClosed()){
			throw new IOException("socket被关闭");
		}
		
		try{
			String data = adapter.getReaderInfoJson(readercard);
			sendData(data);
		}catch(IOException e){
			LogUtils.error("远程计算机关闭的连接"+config);
			throw new SocketException("远程计算机关闭了连接");
		}
		
		try{
			String resultStr = getOutData();
			if (!JSONUtils.mayBeJSON(resultStr)) {
				LogUtils.error(config + " 返回卡信息格式有误==>" + resultStr);
				throw new IOException("返回卡信息格式有误");
			}

			Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
			Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
			if(returnData == null || returnData.isEmpty()){
				throw new SocketException("服务没有返回数据");
			}
			return returnData;
		}catch (Exception e) {
			LogUtils.info(getClass()+"异常",e);
			throw new IOException("读取用户信息失败");
		}
		
	}
	
	/**
	 * 获取图书信息
	 * @param bookInfo
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> obtainBookInfo(AcsBookInfoEntity bookInfo) throws IOException{
		if(!isLogin){
			login();
		}
		if(socket.isClosed()){
			throw new IOException("socket被关闭");
		}
		
		try{
			String data = adapter.getBookInfoJson(bookInfo);
			sendData(data);
		}catch(IOException e){
			LogUtils.error("远程计算机关闭的连接"+config);
			throw new SocketException("远程计算机关闭了连接");
		}
		
		try{
			String resultStr = getOutData();
			if (!JSONUtils.mayBeJSON(resultStr)) {
				LogUtils.error(config + " 返回图书信息格式有误=》" + resultStr);
				throw new IOException("返回图书信息格式有误");
			}

			Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
			Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
			if(returnData == null || returnData.isEmpty()){
				throw new SocketException("服务没有返回数据");
			}
			return returnData;
		}catch (Exception e) {
			LogUtils.info(getClass()+"异常",e);
			throw new IOException("读取图书信息失败");
		}
	}
	
	/**
	 * 续借
	 * @param renewEntity
	 * @return 若使用服务失败，返回null<br/>
	 * 若使用服务成功，state{boolean}指示了续借是否成功，其他参数如下
	 * "data": {
        "BOOKSN": "001241",
        "BOOKTITLE": "",
        "LIBCODE": "044120",
        "OPERATIONRESULT": "N",
        "PATRONCARDSN": "999999999x",
        "PRINTLINE": "",
        "SCREENMESSAGE": "证号为[null]的读者不存在",
        "SHOULDRETURNDATE": ""
    }
	 */
	public Map<String, Object> renew(AcsRenewEntity renewEntity){
		try {
			if (!isLogin) {
				login();
			}
			if (socket.isClosed()) {
				throw new IOException("socket被关闭");
			}

			try {
				String data = adapter.getRenewJson(renewEntity);
				sendData(data);
			} catch (IOException e) {
				LogUtils.error("远程计算机关闭的连接" + config);
				throw new SocketException("远程计算机关闭了连接");
			}
			
			try{
				String resultStr = getOutData();
				if (!JSONUtils.mayBeJSON(resultStr)) {
					LogUtils.error(config + " 返回续借信息有误=》" + resultStr);
					throw new IOException("返回续借信息格式有误");
				}

				Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
				Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
				if(returnData == null || returnData.isEmpty()){
					throw new SocketException("服务没有返回数据");
				}
				
				Map<String, Object> d = new HashMap<String, Object>();
				String operationResult =String.valueOf(returnData.get("OPERATIONRESULT"));
				if ("true".equalsIgnoreCase(operationResult) 
						|| "y".equalsIgnoreCase(operationResult)
						|| "1".equals(operationResult)) {
					d.put("state", true);
					d.putAll(returnData);
				} else {
					d.put("state", false);
					if(returnData != null){
						d.putAll(returnData);
					}
				}
				return d;
			}catch (Exception e) {
				LogUtils.debug(e);
				throw new IOException("读取续借返回信息失败");
			}
			
		} catch (IOException e) {
			LogUtils.info(getClass()+"异常",e);
			return null;
		}
		
	}
	
	/**
	 * 预借图书
	 * @param entity
	 * @return 预借结果信息
	 */
	public Map<String, Object> reservationBook(AcsReservationBookEntity entity){
		try {
			if (!isLogin) {
				login();
			}
			if (socket.isClosed()) {
				throw new IOException("socket被关闭");
			}

			try {
				String data = adapter.getReservationBookJson(entity);
				sendData(data);
			} catch (IOException e) {
				LogUtils.error("远程计算机关闭的连接" + config);
				throw new SocketException("远程计算机关闭了连接");
			}
			
			try{
				String resultStr = getOutData();
				if (!JSONUtils.mayBeJSON(resultStr)) {
					LogUtils.error(config + " 返回预借信息有误=》" + resultStr);
					throw new IOException("返回续借信息格式有误");
				}

				Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
				Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
				if(returnData == null || returnData.isEmpty()){
					throw new SocketException("服务没有返回数据");
				}
				return returnData;
			}catch (Exception e) {
				LogUtils.debug(e);
				throw new IOException("读取预借返回信息失败");
			}
			
		} catch (IOException e) {
			LogUtils.info(getClass()+"异常",e);
			return null;
		}
	}
	
	/**
	 * 取消预借
	 * @param entity
	 * @return
	 */
	public Map<String, Object> inreservationBook(AcsInReservationBookEntity entity){
		try {
			if (!isLogin) {
				login();
			}
			if (socket.isClosed()) {
				throw new IOException("socket被关闭");
			}

			try {
				String data = adapter.getInReservationBookJson(entity);
				sendData(data);
			} catch (IOException e) {
				LogUtils.error("远程计算机关闭的连接" + config);
				throw new SocketException("远程计算机关闭了连接");
			}
			
			try{
				String resultStr = getOutData();
				if (!JSONUtils.mayBeJSON(resultStr)) {
					LogUtils.error(config + " 返回取消预借信息有误=》" + resultStr);
					throw new IOException("返回取消续借信息格式有误");
				}

				Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
				Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
				if(returnData == null || returnData.isEmpty()){
					throw new SocketException("服务没有返回数据");
				}
				return returnData;
			}catch (Exception e) {
				LogUtils.debug(e);
				throw new IOException("读取预借返回信息失败");
			}
			
		} catch (IOException e) {
			LogUtils.info(getClass()+"异常",e);
			return null;
		}
	}
	public Map<String,Object> obtainBookInfoByRecon(AcsBookInfoByReconEntity entity){
		try {
			if (!isLogin) {
				login();
			}
			if (socket.isClosed()) {
				throw new IOException("socket被关闭");
			}

			try {
				String data = adapter.getBookInfoByRecon(entity);
				sendData(data);
			} catch (IOException e) {
				LogUtils.error("远程计算机关闭的连接" + config);
				throw new SocketException("远程计算机关闭了连接");
			}
			
			try{
				String resultStr = getOutData();
				if (!JSONUtils.mayBeJSON(resultStr)) {
					LogUtils.error(config + " 返回查询图书（根据记录号）信息有误=》" + resultStr);
					throw new IOException("返回查询图书（根据记录号）信息格式有误");
				}

				Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
				Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
				if(returnData == null || returnData.isEmpty()){
					throw new SocketException("服务没有返回数据");
				}
				String result = String.valueOf(returnData.get("OPERATIONRESULT"));
				if(!"1".equals(result)
						&& !"y".equalsIgnoreCase(result)
						&& !"true".equalsIgnoreCase(result)){
					throw new IllegalArgumentException("查询图书（根据记录号），OPERATIONRESULT is "+result);	
				}
				return returnData;
			}catch (Exception e) {
				LogUtils.debug(e);
				throw new IOException("读取查询图书（根据记录号）返回信息失败");
			}
			
		} catch (IOException e) {
			LogUtils.info(getClass()+"异常",e);
			return null;
		}
	}
	
	/**
	 * 查询续借列表
	 * @param entity
	 * @return
	 */
	public Map<String, Object> reservationList(AcsQueryReservationEntity entity){
		try {
			if (!isLogin) {
				login();
			}
			if (socket.isClosed()) {
				throw new IOException("socket被关闭");
			}

			try {
				String data = adapter.getQueryReservation(entity);
				sendData(data);
			} catch (IOException e) {
				LogUtils.error("远程计算机关闭的连接" + config);
				throw new SocketException("远程计算机关闭了连接");
			}
			
			try{
				String resultStr = getOutData();
				if (!JSONUtils.mayBeJSON(resultStr)) {
					LogUtils.error(config + " 返回续借列表信息有误，目标数据不为json=》" + resultStr);
					throw new IOException("返回续借列表信息有误");
				}

				Map<String, Object> resultMap = JsonUtils.fromJson(resultStr, Map.class);
				Map<String, Object> returnData = (Map<String, Object>) resultMap.get("data");
				if(returnData == null || returnData.isEmpty()){
					throw new SocketException("查询续借列表时，服务没有返回数据");
				}
				return returnData;
			}catch (Exception e) {
				LogUtils.debug(e);
				throw new IOException("读取查询图书（根据记录号）返回信息失败");
			}
			
		} catch (IOException e) {
			LogUtils.info(getClass()+"异常",e);
			return null;
		}
	}
	
	/**
	 * 登出
	 */
	public void logout(){
		try{
			if (socket.isClosed()) {
				throw new IOException("socket被关闭");
			}
			try {
				String data = adapter.getLogoutJson();
				sendData(data);
			} catch (IOException e) {
				LogUtils.error("远程计算机关闭的连接" + config);
				throw new SocketException("远程计算机关闭了连接");
			}
			isLogin = false;
		}catch(IOException e){
			LogUtils.info(getClass()+"异常",e);
		}
	}
	
	/**
	 * 使用流发送数据
	 * @param data
	 * @throws IOException
	 */
	private void sendData(String data) throws IOException{
		LogUtils.debug(config+" 发送数据==>"+data);
		outputStream.write((data+'\n').getBytes());
		//outputStream.write('\n');//写入换行符
		outputStream.flush();
	}
	
	/**
	 * 从输入流获取数据
	 * @return
	 */
	private String getOutData(){
		StringBuilder resultStrB = new StringBuilder(128);
		String i = null;
		try{
			if((i=br.readLine())!=null){
				resultStrB.append(i);
			}
		}catch(IOException e){
			LogUtils.debug(config + " 传回数据发生异常",e);
		}
		LogUtils.debug(config + " 返回数据信息-->" + resultStrB.toString());
		
		return resultStrB.toString();
	}
	
//	/**
//	 * 从输入流获取数据，采用单独读取字符流方式，读取完一次json后结束读取
//	 * @return
//	 */
//	private String getOutData2(){
//		StringBuilder sb = new StringBuilder(128);
//		Stack<Character> stack = new Stack<>();
//		int ch;
//		try {
//			while((ch = br.read()) != -1){
//				if(Character.isWhitespace(ch)){
//					continue;
//				}
//				sb.append((char)ch);
//				if((char)ch == '{'){
//					stack.push('{');
//				}else if((char)ch == '}'){
//					stack.pop();
//				}
//				if(stack.isEmpty()){
//					break;
//				}
//			}
//		} catch (IOException e) {
//			LogUtils.debug(config + " 传回数据发生异常",e);
//		}
//		LogUtils.debug(config + " 返回数据信息=》" + sb.toString());
//		return sb.toString();
//	}


	@Override
	public void close() throws IOException {
		if(socket != null){
			socket.close();
		}
	}
	
}
