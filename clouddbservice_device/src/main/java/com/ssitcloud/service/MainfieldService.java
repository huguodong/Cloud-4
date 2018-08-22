package com.ssitcloud.service;


import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.MainfieldEntity;

/**
 * 信息主字段表
 *
 * <p>2017年2月10日 下午2:16:39  
 * @author hjc 
 *
 */
public interface MainfieldService {
	/**
	 * 新增记录
	 *
	 * <p>2017年2月10日 下午4:34:33 
	 * <p>create by hjc
	 * @param mainfieldEntity
	 * @return
	 */
	public abstract int addMainField(MainfieldEntity mainfieldEntity);
	
	/**
	 * 删除记录
	 *
	 * <p>2017年2月10日 下午4:34:33 
	 * <p>create by hjc
	 * @param mainfieldEntity
	 * @return
	 */
	public abstract int delMainField(MainfieldEntity mainfieldEntity);
	
	/**
	 * 更新记录
	 *
	 * <p>2017年2月10日 下午4:34:33 
	 * <p>create by hjc
	 * @param mainfieldEntity
	 * @return
	 */
	public abstract int updMainField(MainfieldEntity mainfieldEntity);
	
	/**
	 * 查询一条记录
	 *
	 * <p>2017年2月10日 下午4:34:33 
	 * <p>create by hjc
	 * @param mainfieldEntity
	 * @return
	 */
	public abstract MainfieldEntity selMainfieldByIdx(MainfieldEntity mainfieldEntity);
	
	/**
	 * 分页查询
	 * 
	 * <p>2017年2月24日上午10:51 
	 * <p>create by shuangjunjie
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selectMainFieldByPage(String req);
	
	/**
	 * 获取mainfield表中根据表名group by 之后的字段集合
	 *
	 * <p>2017年3月3日 下午4:49:19 
	 * <p>create by hjc
	 * @return
	 */
	public abstract ResultEntity listMainfieldCollection();
	
	/**
	 * 根据传过来的字段列表添加字段到mainfield 表中，
	 * 保存之前还要查询一遍是否有这个字段
	 * <p>2017年3月6日 上午11:15:40 
	 * <p>create by hjc
	 * @param mainfield_table 表名，
	 * @param mainfield_field 字段列表的字符串形式 ["1","2","3"]
	 * @return
	 */
	public abstract ResultEntity additionalMainfieldList(String mainfield_table,String mainfield_field);
	/**
	 * 查询全部
	 * <p>2017年4月14日
	 * <p>create by lqw
	 * @param mainFieldEntity
	 * @return
	 */
	public abstract ResultEntity selectMainFieldList(MainfieldEntity mainfieldEntity);
	
	/**将mainfield表中的数据加载到redis缓存中*/
	void loadMainFieldToRedis();

}
