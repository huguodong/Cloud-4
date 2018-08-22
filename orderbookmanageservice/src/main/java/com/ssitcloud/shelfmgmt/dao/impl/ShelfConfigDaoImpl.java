package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.ShelfConfigDao;
import com.ssitcloud.shelfmgmt.entity.ShelfConfigEntity;

@Repository
public class ShelfConfigDaoImpl extends CommonDaoImpl implements ShelfConfigDao {

	
	@Override
	public PageEntity queryAllShelfConfig(Map<String, String> map) {
		// TODO Auto-generated method stub
		ShelfConfigEntity shelfConfigEntity = JsonUtils.fromJson(map.get("json"), ShelfConfigEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("shelfConfig.count", shelfConfigEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("shelfConfig.select", shelfConfigEntity, rowBounds));
		return pageEntity;
	}

	@Override
	public Integer addShelfConfig(ShelfConfigEntity shelfConfigEntity) {
		return this.sqlSessionTemplate.insert("shelfConfig.add", shelfConfigEntity);
	}

	@Override
	public Integer updateShelfConfig(ShelfConfigEntity shelfConfigEntity) {
		return this.sqlSessionTemplate.insert("shelfConfig.update", shelfConfigEntity);
	}
	
	@Override
	public int deleteShelfConfig(List<ShelfConfigEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("shelfConfig.delete", list);
	}
	

}
