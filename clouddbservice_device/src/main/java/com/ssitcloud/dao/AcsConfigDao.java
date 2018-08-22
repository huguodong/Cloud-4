package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.ACSProtocolEntity;
import com.ssitcloud.entity.LibSelfcheckProtocolConfigEntity;
import com.ssitcloud.entity.MetadataProtocolEntity;
import com.ssitcloud.entity.ProtocolConfigTemplateEntity;
import com.ssitcloud.entity.SelfCheckProtocolEntity;
import com.ssitcloud.entity.page.LibSelfcheckProtocolPageEntity;

public interface AcsConfigDao {

	LibSelfcheckProtocolPageEntity queryAcsConfigByparam(
			LibSelfcheckProtocolPageEntity libSelfcheckProtocolPage);

	List<LibSelfcheckProtocolConfigEntity> queryAllProtocolConfig();

	int addProtocolConfig(SelfCheckProtocolEntity selfCheckProtocol);

	SelfCheckProtocolEntity selProtocolConfigByIdx(
			SelfCheckProtocolEntity selfCheckProtocol);

	int delProtocolConfig(SelfCheckProtocolEntity selfCheckProtocol);

	int updProtocolConfig(SelfCheckProtocolEntity selfCheckProtocol);

	int addProtocolConfigTemplate(
			ProtocolConfigTemplateEntity protocolConfigTemplate);

	int addProtocolConfigBatch(List<SelfCheckProtocolEntity> selfCheckProtocols);

	List<SelfCheckProtocolEntity> queryProtocolConfigByTplIdx(
			SelfCheckProtocolEntity selfCheckProtocol);

	int updProtocolConfigTemplate(
			ProtocolConfigTemplateEntity protocolConfigTemplate);

	int delProtocolConfigTemplate(
			ProtocolConfigTemplateEntity protocolConfigTemplate);

	/**
	 * 根据图书馆idx查询acx模板
	 *
	 * <p>2016年6月30日 下午2:17:37 
	 * <p>create by hjc
	 * @param templateEntity
	 * @return
	 */
	public abstract List<ProtocolConfigTemplateEntity> getAscTempList(ProtocolConfigTemplateEntity templateEntity);
	/**
	 * 
	 * @param protocolConfigTemplate
	 * @return
	 */
	ProtocolConfigTemplateEntity queryAcsConfigByparamEX1(ProtocolConfigTemplateEntity protocolConfigTemplate);

	int delSelfcheckProtocolConfigByIdx(Integer protocol_tpl_idx);
	/**
	 * 
	 * @param metadataProtocolEntity
	 * @return
	 */
	List<com.ssitcloud.entity.MetadataProtocolEntity> queryMetadataProtocol(MetadataProtocolEntity metadataProtocolEntity);
	/**
	 * 
	 * @param aCSProtocolEntity
	 * @return
	 */
	List<ACSProtocolEntity> queryProtocolConfigByTplIdxEX1(ACSProtocolEntity aCSProtocolEntity);

	int addProtocolConfigEx1Batch(List<ACSProtocolEntity> selfCheckProtocols);
	/**
	 * 
	 * @param selfCheckProtocol
	 * @return
	 */
	int delProtocolConfigEx1(ACSProtocolEntity selfCheckProtocol);
	/**
	 * 
	 * @param pcte
	 * @return
	 */
	List<ProtocolConfigTemplateEntity> queryProtocolConfigTemplateExactly(ProtocolConfigTemplateEntity pcte);
}
