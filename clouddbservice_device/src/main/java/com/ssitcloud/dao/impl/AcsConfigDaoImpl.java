package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.AcsConfigDao;
import com.ssitcloud.entity.ACSProtocolEntity;
import com.ssitcloud.entity.LibSelfcheckProtocolConfigEntity;
import com.ssitcloud.entity.MetadataProtocolEntity;
import com.ssitcloud.entity.ProtocolConfigTemplateEntity;
import com.ssitcloud.entity.SelfCheckProtocolEntity;
import com.ssitcloud.entity.page.LibSelfcheckProtocolPageEntity;

@Repository
public class AcsConfigDaoImpl extends CommonDaoImpl implements AcsConfigDao{

	@Override
	public LibSelfcheckProtocolPageEntity queryAcsConfigByparam(
			LibSelfcheckProtocolPageEntity libSelfcheckProtocolPage) {
		LibSelfcheckProtocolPageEntity total=getSqlSession().selectOne("metadataLibrarySelfcheckProtocol.queryAcsConfigByparam",libSelfcheckProtocolPage);
		libSelfcheckProtocolPage.setTotal(total.getTotal());
		libSelfcheckProtocolPage.setDoAount(false);
		List<LibSelfcheckProtocolPageEntity> libSelfcheckProtocols=getSqlSession().selectList("metadataLibrarySelfcheckProtocol.queryAcsConfigByparam", libSelfcheckProtocolPage);
		libSelfcheckProtocolPage.setRows(libSelfcheckProtocols);
		return libSelfcheckProtocolPage;
	}
	
	@Override
	public ProtocolConfigTemplateEntity queryAcsConfigByparamEX1(ProtocolConfigTemplateEntity protocolConfigTemplate) {
		ProtocolConfigTemplateEntity total=getSqlSession().selectOne("deviceAcsConfig.selectPage", protocolConfigTemplate);
		protocolConfigTemplate.setTotal(total.getTotal());
		protocolConfigTemplate.setDoAount(false);
		List<ProtocolConfigTemplateEntity> lists=getSqlSession().selectList("deviceAcsConfig.selectPage", protocolConfigTemplate);
		protocolConfigTemplate.setRows(lists);
		return protocolConfigTemplate;
	}
	@Override
	public List<LibSelfcheckProtocolConfigEntity> queryAllProtocolConfig() {
		return getSqlSession().selectList("metadataLibrarySelfcheckProtocol.queryAllProtocolConfig");
	}

	@Override
	public int addProtocolConfig(SelfCheckProtocolEntity selfCheckProtocol) {
		return getSqlSession().insert("metadataLibrarySelfcheckProtocol.addProtocolConfig", selfCheckProtocol);
	}

	@Override
	public SelfCheckProtocolEntity selProtocolConfigByIdx(
			SelfCheckProtocolEntity selfCheckProtocol) {
		return getSqlSession().selectOne("metadataLibrarySelfcheckProtocol.selProtocolConfigByIdx", selfCheckProtocol);
	}

	@Override
	public int delProtocolConfig(SelfCheckProtocolEntity selfCheckProtocol) {
		return getSqlSession().delete("metadataLibrarySelfcheckProtocol.delProtocolConfig", selfCheckProtocol);
	}
	@Override
	public int delProtocolConfigEx1(ACSProtocolEntity selfCheckProtocol) {
		return getSqlSession().delete("deviceAcsConfig.deleteByIdx", selfCheckProtocol);
	}
	@Override
	public int updProtocolConfig(SelfCheckProtocolEntity selfCheckProtocol) {
		return getSqlSession().update("metadataLibrarySelfcheckProtocol.updProtocolConfig", selfCheckProtocol);
	}

	@Override
	public int addProtocolConfigTemplate(
			ProtocolConfigTemplateEntity protocolConfigTemplate) {
		return getSqlSession().insert("metadataLibrarySelfcheckProtocol.addProtocolConfigTemplate", protocolConfigTemplate);
	}

	@Override
	public int addProtocolConfigBatch(
			List<SelfCheckProtocolEntity> selfCheckProtocols) {
		return getSqlSession().insert("metadataLibrarySelfcheckProtocol.addProtocolConfigBatch", selfCheckProtocols);
	}
	
	@Override
	public int addProtocolConfigEx1Batch(
			List<ACSProtocolEntity> selfCheckProtocols) {
		return getSqlSession().insert("deviceAcsConfig.addProtocolConfigEx1Batch", selfCheckProtocols);
	}

	@Override
	public List<SelfCheckProtocolEntity> queryProtocolConfigByTplIdx(
			SelfCheckProtocolEntity selfCheckProtocol) {
		return getSqlSession().selectList("metadataLibrarySelfcheckProtocol.queryProtocolConfigByTplIdx", selfCheckProtocol);
	}

	@Override
	public int updProtocolConfigTemplate(
			ProtocolConfigTemplateEntity protocolConfigTemplate) {
		return getSqlSession().update("metadataLibrarySelfcheckProtocol.updProtocolConfigTemplate", protocolConfigTemplate);
	}

	@Override
	public int delProtocolConfigTemplate(
			ProtocolConfigTemplateEntity protocolConfigTemplate) {
		return getSqlSession().delete("metadataLibrarySelfcheckProtocol.delProtocolConfigTemplate", protocolConfigTemplate);
	}

	@Override
	public List<ProtocolConfigTemplateEntity> getAscTempList(
			ProtocolConfigTemplateEntity templateEntity) {
		return getSqlSession().selectList("metadataLibrarySelfcheckProtocol.getAscTempList",templateEntity);
	}

	@Override
	public int delSelfcheckProtocolConfigByIdx(Integer protocol_tpl_idx) {
		return getSqlSession().delete("deviceAcsConfig.deleteByIdx", protocol_tpl_idx);
	}

	@Override
	public List<MetadataProtocolEntity> queryMetadataProtocol(
			MetadataProtocolEntity metadataProtocolEntity) {
		return getSqlSession().selectList("deviceAcsConfig.selectMetadataProtocol", metadataProtocolEntity);
	}
	//queryProtocolConfigByTplIdxEX1
	//
	//
	@Override
	public List<ACSProtocolEntity> queryProtocolConfigByTplIdxEX1(
			ACSProtocolEntity aCSProtocolEntity) {
		return getSqlSession().selectList("deviceAcsConfig.queryProtocolConfigByTplIdxEX1", aCSProtocolEntity)
				;
	}

	@Override
	public List<ProtocolConfigTemplateEntity> queryProtocolConfigTemplateExactly(
			ProtocolConfigTemplateEntity pcte) {
		 return getSqlSession().selectList("deviceAcsConfig.queryProtocolConfigTemplateExactly", pcte)
				;
	}


}
