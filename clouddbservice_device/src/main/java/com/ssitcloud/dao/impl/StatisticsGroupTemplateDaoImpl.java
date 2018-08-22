package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.StatisticsGroupTemplateDao;
import com.ssitcloud.entity.StatisticsGroupTemplateEntity;

@Repository
public class StatisticsGroupTemplateDaoImpl extends CommonDaoImpl implements
		StatisticsGroupTemplateDao {

	@Override
	public List<StatisticsGroupTemplateEntity> findById(StatisticsGroupTemplateEntity sgte) {
		return this.selectAll("statistics_group_template.selectStatisticsGroupTemplate", sgte);
	}

	@Override
	public List<StatisticsGroupTemplateEntity> findAll(StatisticsGroupTemplateEntity sgte) {
		return this.selectAll("statistics_group_template.selectStatisticsGroupTemplates",sgte);
	}

}
