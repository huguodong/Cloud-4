package com.ssitcloud.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.MainfieldDao;
import com.ssitcloud.entity.MainfieldEntity;
import com.ssitcloud.entity.page.MainFieldPageEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.service.MainfieldService;

/**
 * 信息主字段表
 *
 * <p>2017年2月10日 下午2:16:39  
 * @author hjc 
 *
 */
@Service
public class MainfieldServiceImpl implements MainfieldService{
	@Resource
	private MainfieldDao mainfieldDao;
	

	@Override
	public int addMainField(MainfieldEntity mainfieldEntity) {
		return mainfieldDao.addMainField(mainfieldEntity);
	}

	@Override
	public int delMainField(MainfieldEntity mainfieldEntity) {
		return mainfieldDao.delMainField(mainfieldEntity);
	}

	@Override
	public int updMainField(MainfieldEntity mainfieldEntity) {
		return mainfieldDao.updMainField(mainfieldEntity);
	}

	@Override
	public MainfieldEntity selMainfieldByIdx(MainfieldEntity mainfieldEntity) {
		return mainfieldDao.selMainfieldByIdx(mainfieldEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity selectMainFieldByPage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				MainFieldPageEntity mainFieldPageEntity = new MainFieldPageEntity();
				Integer page = map.get("page") == null?1:Integer.valueOf(map.get("page").toString());
				Integer pageSize = map.get("pageSize") == null?10:Integer.valueOf(map.get("pageSize").toString());
				
				mainFieldPageEntity.setPage(page);
				mainFieldPageEntity.setPageSize(pageSize);
				mainFieldPageEntity = mainfieldDao.selectMainFieldByPage(mainFieldPageEntity);
				
				result.setValue(true, "", "", mainFieldPageEntity);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}

	@Override
	public ResultEntity listMainfieldCollection() {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<Map<String, Object>> list = mainfieldDao.listMainfieldCollection();
			resultEntity.setValue(true, "", "", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity additionalMainfieldList(String table,String field) {
		ResultEntity result = new ResultEntity();
		try {
			List<String> mList = JsonUtils.fromJson(field,List.class);
			for (String string : mList) {
				MainfieldEntity mainfieldEntity = new MainfieldEntity();
				mainfieldEntity.setMainfield_table(table);
				mainfieldEntity.setMainfield_field(string);
				mainfieldEntity.setMainfield_field_desc(string);
				int ret = mainfieldDao.countMainfieldByTableAndField(mainfieldEntity);
				if(ret==0){
					//如果数据库中没有数据，则新增
					mainfieldDao.addMainField(mainfieldEntity);
				}
			}
			result.setValue(true, "success");
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public ResultEntity selectMainFieldList(MainfieldEntity mainfieldEntity) {
		ResultEntity result = new ResultEntity();
		try{
			List<MainfieldEntity> list = mainfieldDao.selectMainFieldList(mainfieldEntity);
			result.setValue(true, "", "", list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
	public void loadMainFieldToRedis(){
		List<Map<String, Object>> list = mainfieldDao.listMainfieldCollection();
		if(list != null){
			for(Map<String, Object> map : list){
				String table = (String) map.get("mainfield_table");//对应mongodb的表名
				String field = (String) map.get("field");
				String[] fields = field.split("#");
				JedisUtils.getInstance().sadd(RedisConstant.MAINFIELD+table,fields);
			}
		}
	}
	
	

	
}
