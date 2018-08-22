package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.ShelfGroupDao;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;
import com.ssitcloud.shelfmgmt.entity.RelShelfConfigEntity;
import com.ssitcloud.shelfmgmt.entity.ShelfGroupEntity;

@Repository
public class ShelfGroupDaoImpl extends CommonDaoImpl implements ShelfGroupDao {

	
	@Override
	public PageEntity queryAllShelfGroup(Map<String, String> map) {
		// TODO Auto-generated method stub
		ShelfGroupEntity shelfGroupEntity = JsonUtils.fromJson(map.get("json"), ShelfGroupEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("shelfGroup.count", shelfGroupEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("shelfGroup.select", shelfGroupEntity, rowBounds));
		return pageEntity;
	}

	@Override
	public Integer addShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		return this.sqlSessionTemplate.insert("shelfGroup.add", shelfGroupEntity);
	}

	@Override
	public Integer updateShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		return this.sqlSessionTemplate.insert("shelfGroup.update", shelfGroupEntity);
	}
	
	@Override
	public int deleteShelfGroup(List<ShelfGroupEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("shelfGroup.delete", list);
	}

	@Override
	public RelShelfConfigEntity queryBookshelfRel(RelShelfConfigEntity relShelfConfig) {
		return this.sqlSessionTemplate.selectOne("shelfGroup.selectRelConfigById", relShelfConfig);
	}

	@Override
	public int addBookshelfRel(RelShelfConfigEntity relShelfConfig) {
		return this.sqlSessionTemplate.insert("shelfGroup.addRelConfig", relShelfConfig);
	}
	
	public int updateBookshelf(RelShelfConfigEntity relShelfConfig){
		return this.sqlSessionTemplate.update("shelfGroup.updateRelConfig", relShelfConfig);
	}

	@Override
	public ShelfGroupEntity queryShelGroupfById(ShelfGroupEntity shelfGroupEntity) {
		return this.sqlSessionTemplate.selectOne("shelfGroup.selectById", shelfGroupEntity);
	}
}
