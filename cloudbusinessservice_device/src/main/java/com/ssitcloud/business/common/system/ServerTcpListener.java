package com.ssitcloud.business.common.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTcpListener implements Runnable {

	private ServerSocket server;
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(33456);
			while (true) {
				try {
					System.out.println("开始监听.....");
					Socket socket = server.accept();
					System.out.println("有链接......");
					sendFile(socket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * 传输文件
	 * @param socket  监听的socket
	 * @param fileDir 文件夹路径
	 */
	private static void sendFile(Socket socket) {
		BufferedOutputStream dos = null;
		BufferedInputStream bis = null; // What can BufferedInputStream help ?
		DataInputStream dis=null;
		byte[] bytes = new byte[1024*1024];
		try {
			/*
			 * new a File with the filePath new a FileInputStream with the File
			 * to read the file by byte new a BufferedInputStream with the
			 * FileInputStream to use buffered stream
			 */
			dos = new BufferedOutputStream(socket.getOutputStream());
			// 读取客户端数据
			dis = new DataInputStream(socket.getInputStream());
			if(dis.available()<=0){
				return;
			}
			String filepath = dis.readUTF();//bus服务器，真实文件路径
			System.out.println("要传输的filepath："+filepath);
			bis = new BufferedInputStream(new FileInputStream(new File(filepath)));
			// 首先发送文件名 客户端发送使用writeUTF方法，服务器端应该使用readUTF方法
			// dos.writeUTF(filepath);
			// 之后再发送文件的内容
			int length = 0;
			while ((length = bis.read(bytes, 0, bytes.length)) > 0) {
				dos.write(bytes, 0, length);
			}
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 使用完毕后，应关闭输入、输出流和socket
				if (bis != null)
					bis.close();
				if (dos != null)
					dos.close();
				if (socket != null)
					socket.close();
				if(dis!=null)
					dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}