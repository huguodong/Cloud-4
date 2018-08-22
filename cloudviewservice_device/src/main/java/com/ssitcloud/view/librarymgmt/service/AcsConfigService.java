package com.ssitcloud.view.librarymgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface AcsConfigService {
	
	ResultEntity queryAcsConfigByparam(String req);
	
	ResultEntity queryAcsConfigByparamEx1(String req);

	ResultEntity queryAllProtocolConfig(String req);
	
	ResultEntity queryMetadataProtocol(String req);
	
	ResultEntity querylibInfoByCurUser(String oper);

	ResultEntity addProtocolConfig(String req);
	
	ResultEntity addProtocolConfigEX1(String req);
	
	ResultEntity delProtocolConfig(String req);

	ResultEntity updProtocolConfig(String req);
	
	ResultEntity updProtocolConfigEX1(String req);

	ResultEntity queryProtocolConfigByTplIdx(String req);

	ResultEntity delProtocolConfigTemplate(String req);

	ResultEntity delProtocolConfigTemplateEX1(String req);

	ResultEntity queryProtocolConfigByTplIdxEX1(String req);

	ResultEntity querylibInfoByCurUserEXT1(String oper);
	
	ResultEntity queryProtocolConfigTemplate(String req);

}
