package com.ssitcloud.dbauth.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.SoxTempPageEntity;

/** 
 *  
 * <p>2016年3月24日 下午5:42:49 
 * @author hjc 
 *
 */
public interface SoxTemplateDao extends CommonDao{

	/**
	 * 获取操作员模板信息
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
	 * @return 返回操作行数
	 */
	public abstract int addSoxTemplateEntity(SoxTemplateEntity soxTemplateEntity);
	
	/**
	 * 根据模板id更新模板信息
	 *
	 * <p>2016年6月22日 下午4:26:06 
	 * <p>create by hjc
	 * @param soxTemplateEntity
	 * @return
	 */
	public abstract int updSoxTemplateEntityById(SoxTemplateEntity soxTemplateEntity);
	
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


	public abstract SoxTemplateEntity selectOneByMap(Map<String, Object> m);


	public abstract int addSoxTemplateEntityFully(SoxTemplateEntity row);
}
