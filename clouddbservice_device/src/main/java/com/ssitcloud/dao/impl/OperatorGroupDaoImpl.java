package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.OperatorGroupDao;
import com.ssitcloud.entity.OperatorGroupEntity;
import com.ssitcloud.entity.page.OperGroupMgmtPageEntity;

/**
 * @comment 实现操作员组表的增删改查。表：service_group
 * @author hwl
 * 
 * @data 2016年4月5日
 * 
 */
@Repository
public class OperatorGroupDaoImpl extends CommonDaoImpl implements
		OperatorGroupDao {

	@Override
	public int insert(OperatorGroupEntity operatorgroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("operatorgroup.insert",
				operatorgroupEntity);
	}

	@Override
	public int delete(OperatorGroupEntity operatorgroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("operatorgroup.delete",
				operatorgroupEntity);
	}

	@Override
	public int update(OperatorGroupEntity operatorgroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("operatorgroup.update",
				operatorgroupEntity);
	}

	@Override
	public List<OperatorGroupEntity> selectByidx(
			OperatorGroupEntity operatorgroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("operatorgroup.selectByidx",
				operatorgroupEntity);
	}

	@Override
	public List<OperatorGroupEntity> selectAll() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("operatorgroup.selectAll");
	}

	@Override
	public OperGroupMgmtPageEntity queryOperGroupByparam(OperGroupMgmtPageEntity operGroupMgmtPage) {
		OperGroupMgmtPageEntity total=getSqlSession().selectOne("operatorgroup.queryOperGroupByparam", operGroupMgmtPage);
		operGroupMgmtPage.setDoAount(false);
		operGroupMgmtPage.setTotal(total.getTotal());
		operGroupMgmtPage.setRows(getSqlSession().selectList("operatorgroup.queryOperGroupByparam", operGroupMgmtPage));
		return operGroupMgmtPage;
	}

}
