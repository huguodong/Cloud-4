package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.ShelfGroupDao;
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
	public int deleteShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		try{
			if(shelfGroupEntity.getShelf_group_id() != null)
			sqlSessionTemplate.insert("libraryInfoCommon.superInsert", "insert into delete_rec(table_name,key1,updatetime) values ('shelf_group','"+shelfGroupEntity.getShelf_group_id()+"',SYSDATE())");
		}catch(Exception e){
			try{
				sqlSessionTemplate.update("libraryInfoCommon.superUpdate", "update delete_rec set updatetime = SYSDATE() where table_name = 'shelf_group' AND key1 = '"+shelfGroupEntity.getShelf_group_id()+"'");
			}catch(Exception ex){
				//nothing
			}
		}
		return this.sqlSessionTemplate.delete("shelfGroup.delete", shelfGroupEntity);
	}


	@Override
	public ShelfGroupEntity queryShelGroupfById(ShelfGroupEntity shelfGroupEntity) {
		return this.sqlSessionTemplate.selectOne("shelfGroup.selectById", shelfGroupEntity);
	}
}
