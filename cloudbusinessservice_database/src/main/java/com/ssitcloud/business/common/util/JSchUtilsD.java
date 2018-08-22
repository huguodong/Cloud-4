package com.ssitcloud.business.common.util;

import java.io.IOException;
import java.util.Vector;

import com.jcraft.jsch.Session;
import com.ssitcloud.business.common.exception.JschConnectException;
import com.ssitcloud.business.common.exception.JschDownloadException;
import com.ssitcloud.business.common.exception.JschExecException;
import com.ssitcloud.business.common.exception.JschListFilesException;
import com.ssitcloud.business.common.exception.JschShellException;
import com.ssitcloud.business.common.exception.JschUploadException;

/**
 * SSH2工具类
 * 
 * @author dell
 * 
 */
public class JSchUtilsD{

	public static Session newInstance(String ip, String user, String pwd) throws JschConnectException{
		try {
			return JSchUtils.newInstance(ip, user, pwd, 0, null, null);
		} catch (Exception e) {
			throw new JschConnectException();
		}
	}

	public static Session newInstance(String ip, String user, String pwd, int port, String privateKey, String passphrase) throws JschConnectException{
		try {
			return JSchUtils.newInstance(ip, user, pwd, port, privateKey, passphrase);
		} catch (Exception e) {
			throw new JschConnectException();
		}
	}

	public static void shell(String cmd, String outputFileName) throws JschShellException{
		try {
			JSchUtils.shell(cmd,outputFileName);
		} catch (Exception e) {
			throw new JschShellException();
		}
	}

	public static String shell(String shellCommand) throws JschShellException {
		try {
			return JSchUtils.shell(shellCommand);
		} catch (Exception e) {
			throw new JschShellException();
		}
	}

	public static int exec(String cmd) throws JschExecException{
		try {
			return JSchUtils.exec(cmd);
		} catch (Exception e) {
			throw new JschExecException();
		}
	}

	public static void upload(String directory, String uploadFile) throws JschUploadException{
		try {
			JSchUtils.upload(directory, uploadFile);
		} catch (Exception e) {
			throw new JschUploadException();
		} 
	}

	public static void download(String src, String dst) throws JschDownloadException{
		try {
			JSchUtils.download(src, dst);
		} catch (Exception e) {
			throw new JschDownloadException();
		}
	}

	public static Vector<?> listFiles(String directory) throws JschListFilesException {
		try {
			return JSchUtils.listFiles(directory);
		} catch (Exception e) {
			throw new JschListFilesException();
		} 
	}
	
	public static Session getSession() {
		return JSchUtils.getSession();
	}

}
