package com.ssitcloud.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface BakupService {

	ResultEntity bakupOnlyByLiraryIdx(String req);

	ResultEntity bakupBySpecalTableDevice(String req);

	ResultEntity queryBakDataInfo(String req);

	ResultEntity queryBakDataInfoByIdx(String req);

	ResultEntity insertBakDataInfo(String req);

	ResultEntity updBakDataInfoByIdx(String req);

	ResultEntity getTplIdxByLibraryIdx(String req);

	ResultEntity restoreDataByLibraryIdx(String req);

	ResultEntity bakupBasicListSsit(String req);

	ResultEntity queryLibraryDbBakByparamExt(String req);

	ResultEntity checkBakUpFileIfExsit(String req);

	ResultEntity deleteLibBakup(String req);

	ResultEntity getLastLibBakUpTime(String req);
	
	

}
