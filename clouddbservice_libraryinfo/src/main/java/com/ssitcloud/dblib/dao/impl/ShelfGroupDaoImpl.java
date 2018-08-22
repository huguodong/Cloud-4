package com.ssitcloud.dblib.dao.impl;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.ShelfGroupDao;
import com.ssitcloud.dblib.entity.ShelfGroupEntity;

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
	public ShelfGroupEntity queryShelfGroupById(ShelfGroupEntity shelfGroupEntity){
		return this.sqlSessionTemplate.selectOne("shelfGroup.selectbyId", shelfGroupEntity);
	}
	
	@Override
	public int updateShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		return this.sqlSessionTemplate.update("shelfGroup.update", shelfGroupEntity);
	}

	@Override
	public int deleteShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		try{
			if(shelfGroupEntity.getLib_id() != null && shelfGroupEntity.getShelf_group_id() != null)
			sqlSessionTemplate.insert("libraryInfoCommon.superInsert", "insert into delete_rec(lib_id,table_name,key1,updatetime) values ('"+shelfGroupEntity.getLib_id()+"','shelf_group','"+shelfGroupEntity.getShelf_group_id()+"',SYSDATE())");
		}catch(Exception e){
			try{
				sqlSessionTemplate.update("libraryInfoCommon.superUpdate", "update delete_rec set updatetime = SYSDATE() where lib_id = '"+shelfGroupEntity.getLib_id()+"' AND table_name = 'shelf_group' AND key1 = '"+shelfGroupEntity.getShelf_group_id()+"'");
			}catch(Exception ex){
				//nothing
			}
		}
		return this.sqlSessionTemplate.delete("shelfGroup.delete", shelfGroupEntity);
	}

	@Override
	public int addShelfGroup(ShelfGroupEntity shelfGroupEntity) {
		return this.sqlSessionTemplate.insert("shelfGroup.add", shelfGroupEntity);
	}

}
