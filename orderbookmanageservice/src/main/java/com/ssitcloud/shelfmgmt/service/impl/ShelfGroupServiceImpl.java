package com.ssitcloud.shelfmgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.order.dao.OrderDao;
import com.ssitcloud.shelfmgmt.dao.ShelfGroupDao;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;
import com.ssitcloud.shelfmgmt.entity.RelShelfConfigEntity;
import com.ssitcloud.shelfmgmt.entity.RelShelfGroupEntity;
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
		RelShelfConfigEntity relShelfConfigEntity = JsonUtils.fromJson(jsonObject.getString("json4"), RelShelfConfigEntity.class);
		
		
		int re = shelfGroupDao.addShelfGroup(shelfGroupEntity);
		if(re == 1){
			System.out.println(shelfGroupEntity.getShelf_group_idx());
			if(shelfGroupEntity.getShelf_group_idx() >0){
				relShelfConfigEntity.setShelf_idx(shelfGroupEntity.getShelf_group_idx());
				shelfGroupDao.addBookshelfRel(relShelfConfigEntity);
			}
		}
		
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}

	@Override
	public ResultEntity updateShelfGroup(String req) {
		ResultEntity result = new ResultEntity();
		
		JSONObject jsonObject =JSONObject.fromObject(req);
		ShelfGroupEntity shelfGroupEntity = JsonUtils.fromJson(jsonObject.getString("json1"), ShelfGroupEntity.class);
		RelShelfConfigEntity relShelfConfigEntity = JsonUtils.fromJson(jsonObject.getString("json4"), RelShelfConfigEntity.class);
		int re = shelfGroupDao.updateShelfGroup(shelfGroupEntity);
		if(re == 1){
			re = shelfGroupDao.updateBookshelf(relShelfConfigEntity);
		}
		result.setState(re >= 1 ? true : false);
		result.setRetMessage(re >= 1 ? Const.SUCCESS : "optimistic");
		return result;
	}

	@Override
	public ResultEntity deleteShelfGroup(List<ShelfGroupEntity> list) {
		ResultEntity result = new ResultEntity();
		int re = shelfGroupDao.deleteShelfGroup(list);
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
