package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.EmailParamDao;
import com.ssitcloud.entity.EmailParamEntity;
import com.ssitcloud.entity.page.EmailParamMgmtPageEntity;

@Repository
public class EmailParamDaoImpl extends CommonDaoImpl implements
		EmailParamDao {

	@Override
	public int insertEmailParam(EmailParamEntity emailParamEntity) {
		return this.sqlSessionTemplate.insert("email_param.insertEmailParam", emailParamEntity);
	}

	@Override
	public int updateEmailParam(EmailParamEntity emailParamEntity) {
		return this.sqlSessionTemplate.update("email_param.updateEmailParam", emailParamEntity);
	}

	@Override
	public int deleteEmailParam(EmailParamEntity emailParamEntity) {
		return this.sqlSessionTemplate.delete("email_param.deleteEmailParam", emailParamEntity);
	}

	@Override
	public EmailParamEntity queryOneEmailParam(
			EmailParamEntity emailParamEntity) {
		return this.select("email_param.selectEmailParam", emailParamEntity);
	}

	@Override
	public List<EmailParamEntity> queryEmailParams(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("email_param.selectEmailParams", params);
	}

	@Override
	public EmailParamMgmtPageEntity queryEmailParamByPage(
			EmailParamMgmtPageEntity emailParamMgmtPageEntity) {
		if(null == emailParamMgmtPageEntity) emailParamMgmtPageEntity =new EmailParamMgmtPageEntity();
		EmailParamMgmtPageEntity total = getSqlSession().selectOne("email_param.selectEmailParamByPage", emailParamMgmtPageEntity);
		emailParamMgmtPageEntity.setDoAount(false);
		emailParamMgmtPageEntity.setTotal(total.getTotal());
		emailParamMgmtPageEntity.setRows(getSqlSession().selectList("email_param.selectEmailParamByPage", emailParamMgmtPageEntity));
		return emailParamMgmtPageEntity;
	}

}
