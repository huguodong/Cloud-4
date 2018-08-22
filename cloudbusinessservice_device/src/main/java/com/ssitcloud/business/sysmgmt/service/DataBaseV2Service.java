package com.ssitcloud.business.sysmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DataBaseV2Service {

	ResultEntity dataBaseBakupByLibraryIdxAndID(String req);

	ResultEntity restoreDataByLibraryIdx(String req);

	ResultEntity queryBakDataInfo(String req);

	ResultEntity checkBakUpFileIfExsit(String req);

	ResultEntity deleteLibBakup(String req);

	ResultEntity getLastLibBakUpTime(String req);

}
