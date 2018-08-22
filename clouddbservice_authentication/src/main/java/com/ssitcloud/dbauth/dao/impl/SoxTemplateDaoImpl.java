package com.ssitcloud.dbauth.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.SoxTemplateDao;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.SoxTempPageEntity;

/** 
 *  
 * <p>2016年3月24日 下午5:43:53 
 * @author hjc 
 *
 */
@Repository
public class SoxTemplateDaoImpl extends CommonDaoImpl implements SoxTemplateDao {

	@Override
	public SoxTemplateEntity getSoxTemplateEntity(SoxTemplateEntity soxTemplateEntity) {
		return this.sqlSessionTemplate.selectOne("soxTemplate.getSoxTemplateEntity",soxTemplateEntity);
	}

	@Override
	public int addSoxTemplateEntity(SoxTemplateEntity soxTemplateEntity) {
		return this.sqlSessionTemplate.insert("soxTemplate.addSoxTemplateEntity", soxTemplateEntity);
	}
	
	@Override
	public int updSoxTemplateEntityById(SoxTemplateEntity soxTemplateEntity) {
		return this.sqlSessionTemplate.update("soxTemplate.updSoxTemplateEntityById", soxTemplateEntity);
	}

	@Override
	public int delSoxTemplateById(SoxTemplateEntity soxTemplateEntity) {
		return this.sqlSessionTemplate.delete("soxTemplate.delSoxTemplateById",soxTemplateEntity);
	}


	@Override
	public List<SoxTemplateEntity> queryAllSoxTemp() {
		return this.sqlSessionTemplate.selectList("soxTemplate.queryAllSoxTemp");
	}

	@Override
	public SoxTempPageEntity getSoxTempListByParam(SoxTempPageEntity soxTempPageEntity) {
		SoxTempPageEntity total = this.sqlSessionTemplate.selectOne("soxTemplate.getSoxTempListByParam",soxTempPageEntity);
		soxTempPageEntity.setDoAount(false);
		List<SoxTempPageEntity> list = this.sqlSessionTemplate.selectList("soxTemplate.getSoxTempListByParam",soxTempPageEntity);
		soxTempPageEntity.setTotal(total.getTotal());
		soxTempPageEntity.setRows(list);
		return soxTempPageEntity;
	}

	@Override
	public SoxTemplateEntity selectOneByMap(Map<String, Object> m) {
		return sqlSessionTemplate.selectOne("soxTemplate.selectOneByMap", m);
	}

	@Override
	public int addSoxTemplateEntityFully(SoxTemplateEntity row) {
		return sqlSessionTemplate.insert("soxTemplate.addSoxTemplateEntityFully", row);
	}

	
	
}
