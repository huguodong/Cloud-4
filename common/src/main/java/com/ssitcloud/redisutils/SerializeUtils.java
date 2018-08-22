package com.ssitcloud.redisutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.ssitcloud.common.util.JsonUtils;

public class SerializeUtils {
	
	/***
	 * 序列化，该类必须继承serialze接口
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object){
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("序列化出错了");
		}
		return null;
	}
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	public static <T>T unserialze(byte[] bytes,Class<T> t){
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		T entity = null;
		if(bytes == null || bytes.length <=0 )
			return entity;
		try{
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			Object obejct = ois.readObject();
			if(obejct != null){
				entity = (T)ois.readObject();
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("反序列化失败");
		}
		return entity;
		
	}
			

}
