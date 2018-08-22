package com.ssitcloud.business.common.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SSH2工具类
 * 
 * @author dell
 * 
 */
public class JSchUtils implements Closeable {
	private static long interval = 1000L;
	private static int timeout = 30000;
	private static Session session = null;

	public static Session newInstance(String ip, String user, String pwd) throws Exception {
		return newInstance(ip, user, pwd, 0, null, null);
	}

	/**
	 * @param ip
	 *            主机IP
	 * @param user
	 *            主机登陆用户名
	 * @param psw
	 *            主机登陆密码
	 * @param port
	 *            主机ssh2登陆端口，如果取默认值，传入小于等于0的整数
	 * @param privateKey
	 *            密钥文件路径
	 * @param passphrase
	 *            密钥的密码
	 * @return
	 * @throws Exception
	 */
	public static Session newInstance(String ip, String user, String pwd, int port, String privateKey, String passphrase) throws Exception {
		if (session != null && session.isConnected())
			return session;
		JSch jsch = new JSch();

		// 设置密钥和密码
		if (privateKey != null && !"".equals(privateKey)) {
			if (passphrase != null && "".equals(passphrase)) {
				// 设置带口令的密钥
				jsch.addIdentity(privateKey, passphrase);
			} else {
				// 设置不带口令的密钥
				jsch.addIdentity(privateKey);
			}
		}

		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}

		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}

		// 设置登陆主机的密码
		session.setPassword(pwd);// 设置密码
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		// 设置登陆超时时间
		session.connect(timeout);

		return session;
	}

	/**
	 * 利用JSch包实现远程主机SHELL命令执行
	 * 
	 * @param cmd
	 * @param outputFileName
	 * @return
	 * @throws Exception
	 */
	public static void shell(String cmd, String outputFileName) throws Exception {
		ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
		PipedInputStream pipeIn = new PipedInputStream();
		PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
		FileOutputStream fileOut = new FileOutputStream(outputFileName);
		channelShell.setInputStream(pipeIn);
		channelShell.setOutputStream(fileOut);
		channelShell.connect(timeout);
		pipeOut.write(cmd.getBytes());
		Thread.sleep(interval);
		pipeOut.close();
		pipeIn.close();
		fileOut.close();
		channelShell.disconnect();
	}

	/**
	 * 利用JSch包实现远程主机SHELL命令执行
	 * 
	 * @param shellCommand
	 *            发送需要执行的SHELL命令，需要用\n结尾，表示回车
	 * @return
	 * @throws Exception 
	 */
	public static String shell(String shellCommand) throws Exception {
		ChannelShell channel = null;
		InputStream instream = null;
		OutputStream outstream = null;
		try {
			// 创建sftp通信通道
			channel = (ChannelShell) session.openChannel("shell");
			channel.connect(timeout);
			// 获取输入流和输出流
			instream = channel.getInputStream();
			outstream = channel.getOutputStream();
			outstream.write(shellCommand.getBytes());
			outstream.flush();
			Thread.sleep(interval);
			// 获取命令执行的结果
			if (instream.available() > 0) {
				byte[] data = new byte[instream.available()];
				int nLen = instream.read(data);
				if (nLen < 0) {
					throw new Exception("network error.");
				}
				// 转换输出结果并打印出来
				String temp = new String(data, 0, nLen, "utf-8");
				System.out.println(temp);
				return temp;
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (outstream != null) {
				try {
					outstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			channel.disconnect();
		}
		return null;
	}

	/**
	 * 远程执行命令
	 * 
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	public static int exec(String cmd) throws Exception {
		ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
		channelExec.setCommand(cmd);
		channelExec.setInputStream(null);
		channelExec.setErrStream(System.err);
		InputStream in = channelExec.getInputStream();
		channelExec.connect();
		Thread.sleep(interval);
		int res = -1;
		StringBuffer buf = new StringBuffer(1024);
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				buf.append(new String(tmp, 0, i));
			}
			if (channelExec.isClosed()) {
				res = channelExec.getExitStatus();
				break;
			}
		}
		System.out.println(buf.toString());
		channelExec.disconnect();
		return res;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 * @throws JSchException
	 * @throws SftpException
	 * @throws FileNotFoundException
	 */
	public static void upload(String directory, String uploadFile) throws JSchException, FileNotFoundException, SftpException {
		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect(5000);
		channelSftp.cd(directory);
		File file = new File(uploadFile);
		channelSftp.put(new FileInputStream(file), file.getName());
		System.out.println("Upload Success!");
		channelSftp.quit();
	}

	
	/**
	 * 下载文件
	 * 
	 * @param src
	 *            linux服务器文件地址
	 * @param dst
	 *            本地存放地址
	 * @throws JSchException
	 * @throws SftpException
	 */
	public static void download(String src, String dst) throws JSchException, SftpException {
		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();
		channelSftp.get(src, dst);
		channelSftp.quit();
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 * @throws JSchException
	 */
	public static Vector<?> listFiles(String directory) throws JSchException, SftpException {
		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		return channelSftp.ls(directory);
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public static Session getSession() {
		return session;
	}

	/**
	 * 关闭Session
	 */
	@Override
	public void close() throws IOException {
		getSession().disconnect();
	}

	public static void main(String[] args) {
		String ip = "172.16.0.112";
		String user = "root";
		String pwd = "Ssit123456!";
		try {
			// 连接
			JSchUtils.newInstance(ip, user, pwd);
			// JSchUtils.shell("ls -lrt /usr/tomcat-view \n");
			// JSchUtils.download("/usr/mongodb-linux-x86_64-3.2.8.tgz", "D:\\apache-tomcat-7.0.70\\");
			// 停止tomcat
			// JSchUtils.shell("ps -ef|grep 'tomcat1'|grep -v grep|awk '{print \"kill -9 \" $2}'|sh \n ps -ef|grep tomcat1 \n");
			// 启动tomcat
			// JSchUtils.shell("cd /usr/ \n pwd \n ps -ef|grep tomcat1 \n cd tomcat1/bin/ \n ./startup.sh \n");
			// 替换回去
			//JSchUtils.shell("cd /usr/log/ \n sed -i 's/bye/bye1/g' 1.txt \n");
			//useradd abc;echo 1234|passwd --stdin abc
			//JSchUtils.exec("chmod 777 /usr/ftpsh/TEST666.sh");
			JSchUtils.shell("/usr/ftpsh/TEST555.sh \n");
			//JSchUtils.shell("rm -rf /usr/ftpsh/TEST127.sh");
			// JSchUtils.exec("ls -lrt \n");
			getSession().disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
