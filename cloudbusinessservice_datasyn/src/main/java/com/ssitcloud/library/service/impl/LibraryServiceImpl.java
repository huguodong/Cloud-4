package com.ssitcloud.library.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.library.service.LibraryService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

import net.sf.json.JSONObject;

@Service
public class LibraryServiceImpl implements LibraryService {

	private static final String URL_selLibraryByIdxOrId = "queryLibraryByIdxOrId";
	public static final String charset = "UTF-8";
	@Resource(name = "requestURLListEntity")
	protected RequestURLListEntity requestURLListEntity;
	public LibraryEntity selLibraryByIdxOrId(String json) {
		
		Map<String, String> param = new HashMap<>();
		param.put("json",json);
		String retstr = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_selLibraryByIdxOrId), param, charset);
		if(!StringUtils.isEmpty(retstr)){
			ResultEntity resultEntity = JsonUtils.fromJson(retstr, ResultEntity.class);
			if(resultEntity.getState()){
				LibraryEntity libraryEntity = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), LibraryEntity.class);
				return libraryEntity;
			}
		}
		return null;
	}
	
	/**
	 * 从redis中获取lib_idx.如果缓存中不存在，则查询mysql数据库
	 * @param lib_id
	 * @return
	 */
	public int getLibraryIdxById(String lib_id) {
		//从redis缓存中获取图书馆
		LibraryEntity libraryEntity= JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,lib_id,LibraryEntity.class);
		if(libraryEntity != null){
			return libraryEntity.getLibrary_idx();
		}else{
			//如果缓存中没有数据，查询mysql
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lib_id", lib_id);
			libraryEntity = selLibraryByIdxOrId(jsonObject.toString());
			if(libraryEntity != null){
				JedisUtils.getInstance().hset(RedisConstant.LIBRARY_KEY,lib_id,JsonUtils.toJson(libraryEntity));
				JedisUtils.getInstance().set(RedisConstant.LIBRARY_KEYS+libraryEntity.getLibrary_idx(), lib_id);
				return libraryEntity.getLibrary_idx();
			}
		}
		return -1;
	}
	
	
	

}
