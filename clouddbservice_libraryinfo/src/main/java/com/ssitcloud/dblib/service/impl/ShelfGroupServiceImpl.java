package com.ssitcloud.dblib.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.dao.ShelfGroupDao;
import com.ssitcloud.dblib.entity.ShelfGroupEntity;
import com.ssitcloud.dblib.service.ShelfGroupService;


@Service
public class ShelfGroupServiceImpl implements ShelfGroupService {

	@Resource
	private ShelfGroupDao shelfGroupdao;
	
	@Override
	public PageEntity queryAllShelfGroup(Map<String, String> map) {
		// TODO Auto-generated method stub
		return shelfGroupdao.queryAllShelfGroup(map);
	}

	@Override
	public int updateShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		// TODO Auto-generated method stub
		int re = shelfGroupdao.updateShelfGroup(shelfGroupEntity);
		return re;
	}

	@Override
	public int deleteShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		// TODO Auto-generated method stub
		return shelfGroupdao.deleteShelfGroup(shelfGroupEntity);
	}

	@Override
	public int addShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		int re = shelfGroupdao.addShelfGroup(shelfGroupEntity);
		return re;
	}

}
