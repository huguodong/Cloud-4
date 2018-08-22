package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.OperatorGroupEntity;
import com.ssitcloud.entity.page.OperGroupMgmtPageEntity;

/**
 * @comment
 * @author hwl
 * 
 * @data 2016年4月5日
 * 
 */
public interface OperatorGroupDao extends CommonDao {

	public abstract int insert(OperatorGroupEntity operatorgroupEntity);

	public abstract int delete(OperatorGroupEntity operatorgroupEntity);

	public abstract int update(OperatorGroupEntity operatorgroupEntity);

	public abstract List<OperatorGroupEntity> selectByidx(
			OperatorGroupEntity operatorgroupEntity);

	public abstract List<OperatorGroupEntity> selectAll();

	public abstract OperGroupMgmtPageEntity queryOperGroupByparam(
			OperGroupMgmtPageEntity operGroupMgmtPage);
}
