package com.ssitcloud.view.librarymgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.librarymgmt.service.AcsConfigService;

@Service
public class AcsConfigServiceImpl extends BasicServiceImpl implements AcsConfigService{

	
	
	private static final String URL_queryAcsConfigByparam = "queryAcsConfigByparam";
	private static final String URL_queryAcsConfigByparamEX1 = "queryAcsConfigByparamEX1";
	
	private static final String URL_queryAllProtocolConfig = "queryAllProtocolConfig";
	private static final String URL_querylibInfoByCurUser = "querylibInfoByCurUser";
	private static final String URL_addProtocolConfig = "addProtocolConfig";
	private static final String URL_delProtocolConfig = "delProtocolConfig";
	private static final String URL_updProtocolConfig = "updProtocolConfig";
	private static final String URL_queryProtocolConfigByTplIdx = "queryProtocolConfigByTplIdx";
	private static final String URL_delProtocolConfigTemplate = "delProtocolConfigTemplate";
	private static final String URL_delProtocolConfigTemplateEX1 = "delProtocolConfigTemplateEX1";
	private static final String URL_queryMetadataProtocol = "queryMetadataProtocol";
	private static final String URL_queryProtocolConfigByTplIdxEX1 = "queryProtocolConfigByTplIdxEX1";
	private static final String URL_addProtocolConfigEX1 = "addProtocolConfigEX1";
	private static final String URL_updProtocolConfigEX1 = "updProtocolConfigEX1";
	private static final String URL_querylibInfoByCurUserEXT1 = "querylibInfoByCurUserEXT1";
	private static final String URL_queryProtocolConfigTemplate = "queryProtocolConfigTemplate";
	

	@Override
	public ResultEntity queryAcsConfigByparam(String req) {
		return postUrl(URL_queryAcsConfigByparam, req);
	}

	@Override
	public ResultEntity queryAllProtocolConfig(String req) {
		return postUrl(URL_queryAllProtocolConfig, "");
	}

	@Override
	public ResultEntity querylibInfoByCurUser(String oper) {
		return postUrl(URL_querylibInfoByCurUser, oper);
	}

	@Override
	public ResultEntity addProtocolConfig(String req) {
		return postUrl(URL_addProtocolConfig, req);
	}
	
	@Override
	public ResultEntity addProtocolConfigEX1(String req) {
		return postUrl(URL_addProtocolConfigEX1, req);
	}

	@Override
	public ResultEntity delProtocolConfig(String req) {
		return postUrl(URL_delProtocolConfig, req);
	}

	@Override
	public ResultEntity updProtocolConfig(String req) {
		return postUrl(URL_updProtocolConfig, req);
	}

	@Override
	public ResultEntity updProtocolConfigEX1(String req) {
		return postUrl(URL_updProtocolConfigEX1, req);
	}

	@Override
	public ResultEntity queryProtocolConfigByTplIdx(String req) {
		return postUrl(URL_queryProtocolConfigByTplIdx, req);
	}

	@Override
	public ResultEntity delProtocolConfigTemplate(String req) {
		return postUrl(URL_delProtocolConfigTemplate, req);
	}

	@Override
	public ResultEntity queryAcsConfigByparamEx1(String req) {
		return postUrl(URL_queryAcsConfigByparamEX1, req);
	}

	@Override
	public ResultEntity delProtocolConfigTemplateEX1(String req) {
		return postUrl(URL_delProtocolConfigTemplateEX1, req);
	}

	@Override
	public ResultEntity queryMetadataProtocol(String req) {
		return postUrl(URL_queryMetadataProtocol, req);
	}

	@Override
	public ResultEntity queryProtocolConfigByTplIdxEX1(String req) {
		return postUrl(URL_queryProtocolConfigByTplIdxEX1, req);
	}

	@Override
	public ResultEntity querylibInfoByCurUserEXT1(String req) {
		return postUrl(URL_querylibInfoByCurUserEXT1, req);
	}

	@Override
	public ResultEntity queryProtocolConfigTemplate(String req) {
		return postUrl(URL_queryProtocolConfigTemplate, req);
	}

}
