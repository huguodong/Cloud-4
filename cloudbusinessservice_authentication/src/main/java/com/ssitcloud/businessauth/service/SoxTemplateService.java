package com.ssitcloud.businessauth.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * <p>2016年4月15日 下午1:32:36
 * @author hjc
 */
public interface SoxTemplateService {
	
	
	/**
	 * 查询所有的操作员模板
	 *
	 * <p>2016年6月13日 下午7:25:19 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryAllSoxTemp(String req);
	
	/**
	 * 根据参数获取鉴权模板分类信息
	 *
	 * <p>2016年6月21日 下午5:30:38 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity getSoxTempListByParam(String req);
	
	/**
	 * 新增模板数据
	 *
	 * <p>2016年6月22日 下午3:43:06 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addSoxTemp(String req);
	
	/**
	 * 更新模板数据
	 *
	 * <p>2016年6月22日 下午3:43:13 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateSoxTemp(String req);
	
	/**
	 * 删除模板
	 *
	 * <p>2016年6月22日 下午5:01:08 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delSoxTemp(String req);
	
	/**
	 * 批量删除模板
	 *
	 * <p>2016年6月22日 下午5:01:13 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiSoxTemp(String req);
}
