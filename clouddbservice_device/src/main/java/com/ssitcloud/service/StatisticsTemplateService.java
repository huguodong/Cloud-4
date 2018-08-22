package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.StatisticsTemplateEntity;

/**
 * 统计查询模板配置
 *
 * <p>2017年2月10日 下午2:22:45  
 * @author hjc 
 *
 */
public interface StatisticsTemplateService {
	/**
	 * 统计查询模板配置StatisticsTemplateEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract int insertStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract int updateStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract int deleteStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param statisticsTemplateEntity
	 * @return
	 */
	public abstract StatisticsTemplateEntity queryOneStatisticsTemplate(StatisticsTemplateEntity statisticsTemplateEntity);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
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
	public abstract ResultEntity selectStatisticsTemplatePage(String req);
	
	List<Map<String,Object>> selectBySql(String sql);

    /**
     *根据登录用户获取模板
     * lqw 2017/09/11
     * @param id
     * @return
     */
    List<StatisticsTemplateEntity> selectTemplateMenuByOperidx(int id);
}
