package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.StatisticsGroupTemplateDao;
import com.ssitcloud.entity.StatisticsGroupTemplateEntity;
import com.ssitcloud.service.StatisticsGroupTemplateService;

@Service
public class StatisticsGroupTemplateServiceImpl implements
		StatisticsGroupTemplateService {
	@Resource
	private StatisticsGroupTemplateDao statisticsGroupTemplateDao;

	@Override
	public List<StatisticsGroupTemplateEntity> findById(
			StatisticsGroupTemplateEntity sgte) {
		return statisticsGroupTemplateDao.findById(sgte);
	}

	@Override
	public List<StatisticsGroupTemplateEntity> findAll(
			StatisticsGroupTemplateEntity sgte) {
		return statisticsGroupTemplateDao.findAll(sgte);
	}

}
