package com.ssitcloud.business.devmgmt.service;

import java.util.List;

import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;

public interface TableChangeTriSerive {

	ResultEntityF<List<TableChangeTriEntity>> queryAllTableChanges(String req);

	ResultEntityF<List<TableChangeTriEntity>> selTableChangeTriPatchInfo(String req);

	ResultEntityF<Object> setRequestTimeByTriIdxs(String req);

	ResultEntity updateRequestTimeByTriTableName(String req);

}
