package com.ssitcloud.dbauth.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface BakupService {

	ResultEntity bakupOnlyByLiraryIdxSsitAuth(String req);

	ResultEntity restoreDataByLibraryIdx(String req);

	ResultEntity GetChangedIDXByIdxNameAndLibraryInfo(String req);

}
