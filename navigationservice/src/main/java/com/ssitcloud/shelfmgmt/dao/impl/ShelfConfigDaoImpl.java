package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.Date;
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
	public int deleteShelfConfig(ShelfConfigEntity shelfConfigEntity) {
		try{
			if(shelfConfigEntity.getShelf_config_id() != null)
			sqlSessionTemplate.insert("libraryInfoCommon.superInsert", "insert into delete_rec(table_name,key1,updatetime) values ('shelf_config','"+shelfConfigEntity.getShelf_config_id()+"',SYSDATE())");
		}catch(Exception e){
			try{
				sqlSessionTemplate.update("libraryInfoCommon.superUpdate", "update delete_rec set updatetime = SYSDATE() where table_name = 'shelf_config' AND key1 = '"+shelfConfigEntity.getShelf_config_id()+"'");
			}catch(Exception ex){
				//nothing
			}
		}
		return this.sqlSessionTemplate.delete("shelfConfig.delete", shelfConfigEntity);
	}
	

}
