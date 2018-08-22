package com.ssitcloud.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface LibraryService {

	ResultEntity querySlaveLibraryByMasterIdx(String req);

	ResultEntity queryAllMasterLibAndSlaveLib(String req);

	ResultEntity getLibIdAndLibIdx(String req);

}
