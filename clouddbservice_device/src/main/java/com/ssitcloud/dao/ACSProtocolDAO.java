package com.ssitcloud.dao;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.ACSProtocolEntity;

public interface ACSProtocolDAO extends CommonDao{

	int UpdOneByIdx(ACSProtocolEntity acsProtocolEntity);

	int DelOneByIdx(ACSProtocolEntity acsProtocolEntity);

	int IncOne(ACSProtocolEntity acsProtocolEntity);


}
