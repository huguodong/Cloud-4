package com.ssitcloud.businessauth.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface BakupService {

	ResultEntity bakupOnlyByLiraryIdxSsitAuth(String req);

	ResultEntity restoreDataByLibraryIdx(String req);

}
