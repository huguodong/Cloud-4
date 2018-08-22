package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.LibSelfcheckProtocolConfigEntity;
import com.ssitcloud.entity.MetadataProtocolEntity;
import com.ssitcloud.entity.ProtocolConfigTemplateEntity;
import com.ssitcloud.entity.page.LibSelfcheckProtocolPageEntity;

public interface AcsConfigService {

	LibSelfcheckProtocolPageEntity queryAcsConfigByparam(String req);

	List<LibSelfcheckProtocolConfigEntity> queryAllProtocolConfig(String req);

	ResultEntity addProtocolConfig(String req);

	ResultEntity delProtocolConfig(String req);

	ResultEntity updProtocolConfig(String req);

	ResultEntity queryProtocolConfigByTplIdx(String req);

	ResultEntity delProtocolConfigTemplate(String req);
	
	/**
	 * 根据图书馆idx查询acs模板
	 *
	 * <p>2016年6月30日 下午2:09:27 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity getAscTempList(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ProtocolConfigTemplateEntity queryAcsConfigByparamEX1(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity delProtocolConfigTemplateEX1(String req);
	/**
	 * 查询全部
	 * @param req
	 * @return
	 */
	List<MetadataProtocolEntity> queryMetadataProtocol(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity queryProtocolConfigByTplIdxEX1(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity addProtocolConfigEX1(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity updProtocolConfigEX1(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity queryProtocolConfigTemplate(String req);

}
