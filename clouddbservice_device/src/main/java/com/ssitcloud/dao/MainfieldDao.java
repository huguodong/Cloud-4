package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.MainfieldEntity;
import com.ssitcloud.entity.page.MainFieldPageEntity;

/**
 * 信息主字段表
 *
 * <p>2017年2月10日 下午2:16:39  
 * @author hjc 
 *
 */
public interface MainfieldDao {
	
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
	 * <p>2017年2月24日上午10:41
	 * <p>create by shuangjunjie
	 * @param mainFieldPageEntity
	 * @return
	 */
	public abstract MainFieldPageEntity selectMainFieldByPage(MainFieldPageEntity mainFieldPageEntity);
	
	
	/**
	 * 获取mainfield表中根据表名group by 之后的字段集合
	 *
	 * <p>2017年3月3日 下午4:49:19 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<Map<String, Object>> listMainfieldCollection();
	
	/**
	 * 根据mainfield_table 和mainfidle_field查询有多少相同数量
	 *
	 * <p>2017年3月6日 上午11:21:16 
	 * <p>create by hjc
	 * @param mainfieldEntity
	 */
	public abstract int countMainfieldByTableAndField(MainfieldEntity mainfieldEntity);
	/**
	 * 查询全部
	 * <p>2017年4月14日
	 * <p>create by lqw
	 * @param mainFieldEntity
	 * @return
	 */
	public abstract List<MainfieldEntity> selectMainFieldList(MainfieldEntity mainfieldEntity);

}
