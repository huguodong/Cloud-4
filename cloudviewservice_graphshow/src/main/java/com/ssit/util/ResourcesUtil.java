package com.ssit.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;


public class ResourcesUtil {

	/**
	 * 获取ClassPath文件的输入流对象
	 * 
	 * @methodName: getInputStream
	 * @param path
	 * @return
	 * @returnType: InputStream
	 * @author: liuBh
	 * @throws IOException 
	 */
	public static InputStream getInputStream(String path) throws IOException {
		try {
			return new ClassPathResource(path).getInputStream();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	/**
	 * 获取ClassPath文件的文件流对象
	 * 
	 * @methodName: getFile
	 * @param path
	 * @return
	 * @returnType: File
	 * @author: liuBh
	 * @throws IOException 
	 */
	public static File getFile(String path) throws IOException {
		try {
			return new ClassPathResource(path).getFile();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}
