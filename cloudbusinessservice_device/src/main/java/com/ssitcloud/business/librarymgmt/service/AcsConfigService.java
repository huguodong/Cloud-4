package com.ssitcloud.business.librarymgmt.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.devmgmt.entity.LibSelfcheckProtocolPageEntity;

public interface AcsConfigService {

	ResultEntityF<LibSelfcheckProtocolPageEntity> queryAcsConfigByparam_bus(String req);
	
	ResultEntity queryAcsConfigByparamEX1(String req);

	ResultEntity queryAllProtocolConfig_bus(String req);

	ResultEntity addProtocolConfig_bus(String req);
	
	ResultEntity addProtocolConfigEX1(String req);

	ResultEntity delProtocolConfig_bus(String req);

	ResultEntity updProtocolConfig_bus(String req);
	
	ResultEntity updProtocolConfigEX1(String req);

	ResultEntity queryProtocolConfigByTplIdx_bus(String req);

	ResultEntity delProtocolConfigTemplate_bus(String req);

	ResultEntity delProtocolConfigTemplateEX1(String req);

	ResultEntity queryMetadataProtocol(String req);

	ResultEntity queryProtocolConfigByTplIdxEX1(String req);

	ResultEntity queryProtocolConfigTemplate(String req);



	

}
