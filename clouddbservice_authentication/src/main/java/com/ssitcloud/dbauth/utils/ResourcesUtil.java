package com.ssitcloud.dbauth.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

import com.ssitcloud.dbauth.common.CommonException;




public class ResourcesUtil {
	
	/**
	 * 获取ClassPath文件的输入流对象
	 * @methodName: getInputStream
	 * @param path
	 * @return
	 * @returnType: InputStream
	 * @author: liuBh
	 */
	public static InputStream getInputStream(String path){
		try {
			return new ClassPathResource(path).getInputStream();
		} catch (IOException e) {
			throw new CommonException(e);
		}
	}
	/**
	 * 获取ClassPath文件的文件流对象
	 * @methodName: getFile
	 * @param path
	 * @return
	 * @returnType: File
	 * @author: liuBh
	 */
	public static File getFile(String path){
		try {
			return new ClassPathResource(path).getFile();
		} catch (IOException e) {
			throw new CommonException(e);
		}
	}
}
