package com.ssitcloud.dbauth.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.SoxTempPageEntity;

/** 
 *  
 * 2016年3月24日 下午5:44:23 
 * @author hjc 
 *
 */
public interface SoxTemplateService {

	/**
	 * 获取
	 *
	 * <p>2016年3月31日 下午2:16:49 
	 * <p>create by hjc
	 * @param soxTemplateEntity
	 * @return @see com.ketu.entity.SoxTemplateEntity
	 */
	public abstract SoxTemplateEntity getSoxTemplateEntity(SoxTemplateEntity soxTemplateEntity);
	
	/**
	 * 新增操作员模板
	 *
	 * <p>2016年3月31日 下午4:40:38 
	 * <p>create by hjc
	 * @param soxTemplateEntity 新增的模板对象，成功之后可以读取自增ID，若此ID为空，则新增失败
	 * @return 返回操作行数@see com.ketu.entity.SoxTemplateEntity
	 */
	public abstract int addSoxTemplateEntity(SoxTemplateEntity soxTemplateEntity);
	
	/**
	 * 根据模板id删除一条模板信息
	 * 
	 * <p>2016年4月6日 上午11:25:25
	 * <p>create by hjc
	 * @param soxTemplateEntity 模板实体类
	 * @return 数据库操作结果
	 */
	public abstract int delSoxTemplateById(SoxTemplateEntity soxTemplateEntity);
	
	/**
	 * 查询所有的操作员模板
	 *
	 * <p>2016年6月13日 下午7:33:33 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<SoxTemplateEntity> queryAllSoxTemp();
	
	/**
	 * 根据参数获取鉴权模板的分页信息
	 *
	 * <p>2016年6月21日 下午6:17:01 
	 * <p>create by hjc
	 * @param soxTempPageEntity
	 * @return
	 */
	public abstract SoxTempPageEntity getSoxTempListByParam(SoxTempPageEntity soxTempPageEntity);
	
	/**
	 * 新增模板
	 *
	 * <p>2016年6月22日 下午4:06:24 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addSoxTemp(String req);
	
	/**
	 * 更新模板
	 *
	 * <p>2016年6月22日 下午4:06:29 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateSoxTemp(String req);
	
	/**
	 * 删除模板
	 *
	 * <p>2016年6月22日 下午5:11:47 
	 * <p>create by hjc
	 * @param sox_id
	 * @return
	 */
	public abstract ResultEntity delSoxTemp(String req);
	
	/**
	 * 批量删除模板
	 *
	 * <p>2016年6月22日 下午5:11:50 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiSoxTemp(String req);
	
	//start author by lxp
	/**
	 * 根据id获取模板
	 * @param sox_tpl_id 模板id
	 * @return
	 */
	SoxTemplateEntity getSoxTemplateEntityById(Integer sox_tpl_id);
	//end author by lxp
}
