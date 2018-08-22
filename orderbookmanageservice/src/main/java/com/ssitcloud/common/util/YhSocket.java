package com.ssitcloud.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

@SuppressWarnings("unused")
public class YhSocket {
	private String ip;
	private int port;
	private Socket sc;	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * 初始化网络配置
	 * @param ip 远程ip地址
	 * @param port  远程端口号
	 * @return
	 */
	public boolean NetSet(String ip, int port){
		this.ip = ip;
		this.port = port;
		return true;
	}
	/**
	 * 创建网络连接
	 * @return
	 */
	public boolean NetConnect(){
		boolean flg = false;
		try {
			sc = new Socket(ip, port);
			flg = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			;
		}
		return flg;
	}
	/**
	 * 关闭网络连接
	 * @return
	 */
	public boolean NetClose(){
		boolean flg = false;
		try {
			sc.close();
			flg = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			;
		}
		return flg;
	}
	/**
	 * 打开网络连接
	 * @param qNumber 区号
	 * @return
	 */
	public boolean NetConnectOn(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x02,			// 设备id		
				0x00,			// 连接命令		
				0x00,			// 访问方式允许其他用户直接访问
				0x00,			// 设置新密码
				0x01,
				0x02,		
				0x03,		
				0x04,
				0x05,
				0x06,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 关闭网络连接
	 * @param qNumber  区号
	 * @return
	 */
	public boolean NetDconnectOn(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x02,			// 设备id		
				(byte) 0xFF,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 打开电源
	 * @param qNumber  区号
	 * @return
	 */
	public boolean NetPowerOn(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x03,			// 设备id		
				(byte) 0x01,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 关闭电源
	 * @param qNumber 区号
	 * @return
	 */
	public boolean NetPowerOff(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x03,			// 设备id		
				(byte) 0x02,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 停止移动所有的列
	 * @param qNumber  区号
	 * @return
	 */
	public boolean NetStopAllL(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x02,			// 设备id		
				(byte) 0x05,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 禁止移动所有的列
	 * @param qNumber 区号
	 * @return
	 */
	public boolean NetFobiddenAllL(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x02,			// 设备id		
				(byte) 0x06,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 解消禁止
	 * @param qNumber  区号
	 * @return
	 */
	public boolean NetClearFobidden(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x02,			// 设备id		
				(byte) 0x07,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 通风
	 * @param qNumber  区号
	 * @return
	 */
	public boolean NetVentilation(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x02,			// 设备id		
				(byte)0x0A,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 开始或正在关闭列
	 * @param qNumber  区号
	 * @return
	 */
	public boolean NetCloseL(int qNumber){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x03,			// 设备id		
				(byte)0x03,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}	
	/**
	 * 打开指定列
	 * @param qNumber 区号
	 * @param lNumber 列号
	 * @param lName   列别名
	 * @return
	 */
	public boolean NetOpenL(int qNumber, int lNumber, int lName){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x03,			// 设备id
				0x11,
				(byte) lNumber,
				(byte) lName,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 打开记录
	 * @param qNumber 区号
	 * @param lNumber 列号
	 * @param jNumber 节号
	 * @param cNumber 层号
	 * @param ce 侧号
	 * @param ben 本号
	 * @param docName  档案题名
	 * @param lName  列别名
	 * @return
	 */
	public boolean NetOpenRecord(int qNumber, int lNumber, int jNumber, int cNumber, int ce, int ben, String docName, int lName){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[50];
		send_buffer[0] = (byte)0xC0;
		send_buffer[1] = 0x01;
		send_buffer[2] = (byte)qNumber;		// 区号
		send_buffer[3] = 0x02;
		send_buffer[4] = 0x13;
		send_buffer[5] = (byte)lNumber;	// 列号
		send_buffer[6] = (byte)jNumber;	// 节号
		send_buffer[7] = (byte)cNumber;	// 层号
		send_buffer[8] = (byte)ce;	// 1 左侧 0 右侧
		send_buffer[9] = (byte)ben;	// 本
		
		byte[] docBytes = docName.getBytes();
		int _ilen = docBytes.length;
		send_buffer[10] = (byte) _ilen;
		for (int i = 0; i < docBytes.length; i++) {
			send_buffer[i + 10] = docBytes[i];
		}
		send_buffer[11 + _ilen] = (byte)lName;
		send_buffer[12 + _ilen] = (byte)0xC0;
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[1024];
	        int ilen = is.read(outbytes);
	        NetClose();
	        if (outbytes[4] == 0){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	/**
	 * 状态查看
	 * @param qNumber 区号
	 * @param outBytes  状态反馈
	 * @return
	 */
	
	public boolean NetStatusChk(int qNumber, byte[] outBytes){
		if (!NetConnect()){
			return false;
		}
		byte[] send_buffer = new byte[]{
				(byte)0xC0,
				0x01,
				(byte)qNumber,		// 区号
				0x02,			// 设备id		
				(byte) 0xEE,
				(byte)0xC0
		}; 
		// 创建写
		try {
			OutputStream os = sc.getOutputStream();
			os.write(send_buffer);
			//打开输入流
	        InputStream is = sc.getInputStream();
	        byte[] outbytes = new byte[50];
	        int ilen = is.read(outbytes);
	        NetClose();
	        for(int i = 0; i < 50; i ++){
	        	outBytes[i] = outbytes[i];
	    	}
	        return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NetClose();
			return false;
		}
	}
	public boolean MyTest(byte[] bytes){
		bytes[0] = (byte)0xC0;
		bytes[1] = 0x11;
		return true;
	}
}
