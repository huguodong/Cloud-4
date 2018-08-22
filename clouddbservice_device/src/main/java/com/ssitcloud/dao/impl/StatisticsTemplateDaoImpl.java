package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.StatisticsTemplateDao;
import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.entity.page.EmailParamMgmtPageEntity;
import com.ssitcloud.entity.page.StatisticsTemplatePageEntity;

/**
 * 统计查询模板配置
 *
 * <p>2017年2月10日 下午2:22:45  
 * @author hjc 
 *
 */
@Repository
public class StatisticsTemplateDaoImpl extends CommonDaoImpl implements StatisticsTemplateDao{
	@Override
	public int insertStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity) {
		return this.sqlSessionTemplate.insert("statistics_template.insertStatisticsTemplate", statisticsTemplateEntity);
	}

	@Override
	public int updateStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity) {
		return this.sqlSessionTemplate.update("statistics_template.updateStatisticsTemplate", statisticsTemplateEntity);
	}

	@Override
	public int deleteStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity) {
		return this.sqlSessionTemplate.delete("statistics_template.deleteStatisticsTemplate", statisticsTemplateEntity);
	}

	@Override
	public StatisticsTemplateEntity queryOneStatisticsTemplate(
			StatisticsTemplateEntity statisticsTemplateEntity) {
		return this.select("statistics_template.selectStatisticsTemplate", statisticsTemplateEntity);
	}

	@Override
	public List<StatisticsTemplateEntity> queryStatisticsTemplates(
			StatisticsTemplateEntity statisticsTemplateEntity) {
		return this.selectAll("statistics_template.selectStatisticsTemplates", statisticsTemplateEntity);
	}

	@Override
	public StatisticsTemplatePageEntity selectStatisticsTemplatePage(
			StatisticsTemplatePageEntity statisticsTemplatePageEntity) {
		if(null == statisticsTemplatePageEntity) statisticsTemplatePageEntity =new StatisticsTemplatePageEntity();
		if(statisticsTemplatePageEntity.isWhetherPaging()){
			StatisticsTemplatePageEntity total = getSqlSession().selectOne("statistics_template.selectStatisticsTemplatePage", statisticsTemplatePageEntity);
			statisticsTemplatePageEntity.setDoAount(false);
			statisticsTemplatePageEntity.setTotal(total.getTotal());
		}
		statisticsTemplatePageEntity.setRows(getSqlSession().selectList("statistics_template.selectStatisticsTemplatePage", statisticsTemplatePageEntity));
		return statisticsTemplatePageEntity;
	}

    @Override
    public List<StatisticsTemplateEntity> selectTemplateMenuByOperidx(int id) {
        return this.sqlSessionTemplate.selectList("statistics_template.selectTemplateMenuByOperidx",id);
    }
}
