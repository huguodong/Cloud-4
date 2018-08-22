package com.ssitcloud.dblib.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.MakeshelfDao;
import com.ssitcloud.dblib.entity.MakeshelfEntity;

@Repository
public class MakeshelfDaoImpl extends CommonDaoImpl implements MakeshelfDao {

	@Override
	public PageEntity queryAllMakeShelfRecord(Map<String, String> map) {
		// TODO Auto-generated method stub
		MakeshelfEntity makeshelfEntity = JsonUtils.fromJson(map.get("json"), MakeshelfEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("makeshelf.count", makeshelfEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("makeshelf.select", makeshelfEntity, rowBounds));
		return pageEntity;
	}

	@Override
	public int deleteMakeShelf(List<MakeshelfEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("makeshelf.delete", list);
	}

	@Override
	public int addMakeShelfRecord(MakeshelfEntity makeshelfEntity) {
		makeshelfEntity.setUpdatetime(new Date());
		return this.sqlSessionTemplate.insert("makeshelf.add", makeshelfEntity);
	}
	
	@Override
	public MakeshelfEntity queryMakeShelfByid(MakeshelfEntity makeshelfEntity){
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("makeshelf.selectById", makeshelfEntity);
	}
}
