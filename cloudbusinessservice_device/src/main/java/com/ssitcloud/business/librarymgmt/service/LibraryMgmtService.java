package com.ssitcloud.business.librarymgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface LibraryMgmtService {

	ResultEntity selLibraryServiceTemplateByIdx(String req);

	ResultEntity queryAllMasterLibAndSlaveLib(String req);

	ResultEntity getLibIdAndLibIdx(String req);
	
	ResultEntity querylibInfoByCurUserEXT1(String req);

	ResultEntity saveLibPosition(String req);
}
