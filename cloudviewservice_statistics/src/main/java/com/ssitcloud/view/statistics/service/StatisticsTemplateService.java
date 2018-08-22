package com.ssitcloud.view.statistics.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface StatisticsTemplateService {
	
	/**
	 * 查询出统计模板的数据源
	 * author huanghuang
	 * 2017年3月16日 上午9:18:53
	 * @param req
	 * @return
	 */
	ResultEntity takeDataSource(String req);
	
	/**
	 * 查询出统计类型的子类型
	 * author huanghuang
	 * 2017年3月17日 上午9:10:44
	 * @param req
	 * @return
	 */
	ResultEntity selectStaticsType(String req);
	/**
	 * 查询出统计模板的图书馆藏地
	 * author huanghuang
	 * 2017年3月17日 上午11:30:36
	 * @param req
	 * @return
	 */
	ResultEntity selectBookLocations(String req);
	/**
	 * 查询出统计模板的图书流通类型
	 * author huanghuang
	 * 2017年3月17日 上午11:30:41
	 * @param req
	 * @return
	 */
	ResultEntity selectBookCirtypes(String req);
	/**
	 * 查询出统计模板的图书载体类型
	 * author huanghuang
	 * 2017年3月17日 上午11:30:46
	 * @param req
	 * @return
	 */
	ResultEntity selectBookMediatypes(String req);
	/**
	 * 查询出统计模板的读者类型
	 * author huanghuang
	 * 2017年3月17日 上午11:30:51
	 * @param req
	 * @return
	 */
	ResultEntity selectReadertype(String req);
	/**
	 * 获取所有的设备类型
	 * author huanghuang
	 * 2017年3月17日 下午2:11:11
	 * @param req
	 * @return
	 */
	ResultEntity selectAllDeviceType(String req);
	/**
	 * 通过条件查找设备
	 * author huanghuang
	 * 2017年3月17日 下午2:35:40
	 * @param req
	 * @return
	 */
	ResultEntity selectDeviceByCondition(String req);
	
	/**
	 * 查询出统计类型的主类型
	 * author huanghuang
	 * 2017年3月17日 上午9:10:44
	 * @param req
	 * @return
	 */
	ResultEntity queryStatisticsMaintypeList(String req);
	/**
	 * 查询出统计模板的主函数
	 * author huanghuang
	 * 2017年3月17日 上午11:30:51
	 * @param req
	 * @return
	 */
	ResultEntity selectFunMaindatas(String req);
	/**
	 * 查询出统计模板的子函数
	 * author huanghuang
	 * 2017年3月17日 上午11:30:51
	 * @param req
	 * @return
	 */
	ResultEntity selectFunSubdatas(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity插入
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	ResultEntity insertStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity修改
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	ResultEntity updateStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity删除
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	ResultEntity deleteStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity单个查询
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	ResultEntity  queryOneStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity多个查询
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	ResultEntity queryStatisticsTemplates(String req);
	/**
	 * 统计查询模板配置StatisticsTemplateEntity分页查询
	 * author lqw
	 * 2017年3月31日 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	ResultEntity selectStatisticsTemplatePage(String req);
	
	/**
	 * 动态拼接sql查询
	 * author huanghuang
	 * 2017年4月6日 上午10:55:23
	 * @param req
	 * @return
	 */
	ResultEntity selectBySql(String req);
    /**
     * 动态拼接sql查询鉴权库
     * author lqw
     * 2017年8月29日 上午10:55:23
     * @param req
     * @return
     */
    ResultEntity selectAutBySql(String req);
	/**
	 * 查询出一条动态记录
	 * author huanghuang
	 * 2017年4月6日 上午11:14:18
	 * @param req
	 * @return
	 */
	ResultEntity queryReltype(String req);
	/**
	 * 查询出多条动态记录
	 * author huanghuang
	 * 2017年4月6日 上午11:14:22
	 * @param req
	 * @return
	 */
	ResultEntity queryReltypeList(String req);
    /**
     *根据登录用户获取模板
     * lqw 2017/09/11
     */
    ResultEntity selectTemplateMenuByOperidx(String req);
}
