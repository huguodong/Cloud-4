package com.ssitcloud.view.sysmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.BasicService;

public interface DataBaseService extends BasicService{

	ResultEntity backUp(String req);

	ResultEntity queryDbBakByparam(String req);

	ResultEntity deleteBakup(String req);

	ResultEntity getLastBakUpTime(String req);

	ResultEntity restoreBakup(String req);

	ResultEntity getMongodbNames(String req);

	ResultEntity bakupByLibraryIdx(String req);

	ResultEntity restoreDataByLibraryIdx(String req);

	ResultEntity queryLibraryDbBakByparamExt(String req);

	ResultEntity checkBakUpFileIfExsit(String req);

	ResultEntity deleteLibBakup(String req);

	ResultEntity getLastLibBakUpTime(String req);

}
