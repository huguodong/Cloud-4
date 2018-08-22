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
import com.ssitcloud.order.dao.OrderDao;
import com.ssitcloud.shelfmgmt.dao.ShelfConfigDao;
import com.ssitcloud.shelfmgmt.entity.ShelfConfigEntity;
import com.ssitcloud.shelfmgmt.service.ShelfConfigService;


@Service
public class ShelfConfigServiceImpl implements ShelfConfigService {

	@Resource
	private ShelfConfigDao shelfConfigDao;
	
	@Override
	public ResultEntity queryAllShelfConfig(String req) {
		ResultEntity result = new ResultEntity();
		Map<String, String> map = new HashMap<>();
		JSONObject jsonObject =JSONObject.fromObject(req);
		map.put("json", jsonObject.getString("json"));
		map.put("page", jsonObject.getString("page"));
		PageEntity shelfGroup = shelfConfigDao.queryAllShelfConfig(map);
		result.setResult(shelfGroup);
		result.setMessage(Const.SUCCESS);
		result.setState(true);
		return result;
	}

	@Override
	public ResultEntity addShelfConfig(ShelfConfigEntity shelfConfigEntity) {
		ResultEntity result = new ResultEntity();
		int re = shelfConfigDao.addShelfConfig(shelfConfigEntity);
		if(re<=0){
			result.setState(false);
			result.setRetMessage("新增失败！");
			return result;
		}else{
			result.setState(true);
			return result;
		}
	}

	@Override
	public ResultEntity updateShelfConfig(ShelfConfigEntity shelfConfigEntity) {
		ResultEntity result = new ResultEntity();
		int re = shelfConfigDao.updateShelfConfig(shelfConfigEntity);
		if(re<=0){
			result.setState(false);
			result.setRetMessage("编辑失败！");
			return result;
		}else{
			result.setState(true);
			return result;
		}
	}

	@Override
	public ResultEntity deleteShelfConfig(List<ShelfConfigEntity> list) {
		ResultEntity result = new ResultEntity();
		int re = shelfConfigDao.deleteShelfConfig(list);
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
