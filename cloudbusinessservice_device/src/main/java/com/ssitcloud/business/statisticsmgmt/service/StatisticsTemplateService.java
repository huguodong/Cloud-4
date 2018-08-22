package com.ssitcloud.business.statisticsmgmt.service;


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
	 */
	ResultEntity insertStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity修改
	 * <p>2017年3月30日  
     * @author lqw
	 */
	ResultEntity updateStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity删除
	 * <p>2017年3月30日  
     * @author lqw
	 */
	ResultEntity deleteStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity单个查询
	 * <p>2017年3月30日  
     * @author lqw
	 */
	ResultEntity  queryOneStatisticsTemplate(String req);
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity多个查询
	 * <p>2017年3月30日  
     * @author lqw
	 */
	ResultEntity queryStatisticsTemplates(String req);
	/**
	 * 统计查询模板配置StatisticsTemplateEntity分页查询
	 * author lqw
	 * 2017年3月31日
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
     *根据登录用户获取模板
     * lqw 2017/09/11
     */
    ResultEntity selectTemplateMenuByOperidx(String req);

}
