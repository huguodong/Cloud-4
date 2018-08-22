package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.entity.page.StatisticsTemplatePageEntity;

/**
 * 统计查询模板配置
 *
 * <p>2017年2月10日 下午2:22:45  
 * @author hjc 
 *
 */
public interface StatisticsTemplateDao {
	/**
	 * 统计查询模板配置StatisticsTemplateEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract int insertStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract int updateStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract int deleteStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract StatisticsTemplateEntity queryOneStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract List<StatisticsTemplateEntity> queryStatisticsTemplates(StatisticsTemplateEntity statisticsTemplateEntity);
	/**
	 * 统计查询模板配置StatisticsTemplateEntity分页查询
	 * author lqw
	 * 2017年3月31日 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract StatisticsTemplatePageEntity selectStatisticsTemplatePage(StatisticsTemplatePageEntity statisticsTemplatePageEntity);

    /**
     *根据登录用户获取模板
     * lqw 2017/09/11
     * @param id
     * @return
     */
    List<StatisticsTemplateEntity> selectTemplateMenuByOperidx(int id);
}
