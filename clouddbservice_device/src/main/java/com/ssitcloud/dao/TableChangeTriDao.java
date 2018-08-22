package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.TableChangeTriEntity;

public interface TableChangeTriDao {
	public int insertOne(TableChangeTriEntity changerTri);
	public int updateOne(TableChangeTriEntity changerTri);
	public List<TableChangeTriEntity> selectList(TableChangeTriEntity changerTri);
	public TableChangeTriEntity selectOne(TableChangeTriEntity changerTri);
	public int deleteOne(TableChangeTriEntity changerTri);
	public int deleteDateWhereisOutof(int day);
	
	/**
	 * 查出每个表最后变化时间
	 * @methodName: selectBycreatTimeDesc
	 * @return
	 * @returnType: TableChangeTriEntity
	 * @author: liuBh
	 */
	public List<TableChangeTriEntity> selectBycreatTimeDescByIdx(TableChangeTriEntity changerTri);
	List<TableChangeTriEntity> selectPatchInfoChangesOrderByCreatimeDescByIdx(
			TableChangeTriEntity changerTri);
	
	/**
	 * params 参数 {requestTime："" ,table_name:"",device_id:""}
	 * @param params
	 * @return
	 */
	public int updataRequestTime(Map<String, Object> params);
	
	
	/**
	 * 
	 * @param idxList
	 * @return
	 */
	public int updateInTriIdx(List<Integer> idxList);
	
	public int updateBytableNameList(List<String> tableNameList);

	
}
