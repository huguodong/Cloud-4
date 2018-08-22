package com.ssitcloud.shelfmgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.ShelfGroupDao;
import com.ssitcloud.shelfmgmt.entity.ShelfGroupEntity;
import com.ssitcloud.shelfmgmt.service.ShelfGroupService;


@Service
public class ShelfGroupServiceImpl implements ShelfGroupService {

	@Resource
	private ShelfGroupDao shelfGroupDao;
	
	@Override
	public ResultEntity queryAllShelfGroup(String req) {
		ResultEntity result = new ResultEntity();
		Map<String, String> map = new HashMap<>();
		JSONObject jsonObject =JSONObject.fromObject(req);
		map.put("json", jsonObject.getString("json"));
		map.put("page", jsonObject.getString("page"));
		PageEntity shelfGroup = shelfGroupDao.queryAllShelfGroup(map);
		result.setResult(shelfGroup);
		result.setMessage(Const.SUCCESS);
		result.setState(true);
		return result;
	}
	
	@Override
	public ResultEntity queryshelfGroupById(String req) {
		ShelfGroupEntity shelfGroupEntity =JsonUtils.fromJson(req, ShelfGroupEntity.class);
		ShelfGroupEntity entity = shelfGroupDao.queryShelGroupfById(shelfGroupEntity);
		ResultEntity result = new ResultEntity();
		result.setResult(entity);
		result.setState(true);
		result.setMessage(Const.SUCCESS);
		return result;
	}

	@Override
	public ResultEntity addShelfGroup(String req) {
		ResultEntity result = new ResultEntity();
		
		
		JSONObject jsonObject =JSONObject.fromObject(req);
		ShelfGroupEntity shelfGroupEntity = JsonUtils.fromJson(jsonObject.getString("json1"), ShelfGroupEntity.class);
		int re = shelfGroupDao.addShelfGroup(shelfGroupEntity);
		
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}

	@Override
	public ResultEntity updateShelfGroup(String req) {
		ResultEntity result = new ResultEntity();
		
		JSONObject jsonObject =JSONObject.fromObject(req);
		ShelfGroupEntity shelfGroupEntity = JsonUtils.fromJson(jsonObject.getString("json1"), ShelfGroupEntity.class);
		int re = shelfGroupDao.updateShelfGroup(shelfGroupEntity);
		result.setState(re >= 1 ? true : false);
		result.setRetMessage(re >= 1 ? Const.SUCCESS : "optimistic");
		return result;
	}

	@Override
	public ResultEntity deleteShelfGroup(List<ShelfGroupEntity> list) {
		ResultEntity result = new ResultEntity();
		int re = 0;
		for(ShelfGroupEntity entity:list){
			re = shelfGroupDao.deleteShelfGroup(entity);
		}
		if(re<=0){
			result.setState(false);
			result.setRetMessage("删除失败！");
			return result;
		}else{
			result.setState(true);
			return result;
		}
	}

}
