package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.TableChangeTriEntity;

public interface TableChangeTriService {
	
	public List<TableChangeTriEntity> queryAllgourpByTableOrderByCreatimeDesc();

	public List<TableChangeTriEntity> selTableChangeTriPatchInfo();
	
	public int deleteDateWhereisOutof(int day);

	public ResultEntity setRequestTimeByTriIdxs(String req);

	public ResultEntity updateRequestTimeByTriTableName(String req);

}
