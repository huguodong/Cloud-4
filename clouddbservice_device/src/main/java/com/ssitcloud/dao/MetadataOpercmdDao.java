package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.MetadataOpercmdEntity;
import com.ssitcloud.entity.page.OperCmdMgmtPageEntity;
import com.ssitcloud.system.entity.MetaOpercmdRemoteEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月7日
 */
public interface MetadataOpercmdDao extends CommonDao {

	public abstract int insert(MetadataOpercmdEntity metadataOpercmdEntity);

	public abstract int update(MetadataOpercmdEntity metadataOpercmdEntity);

	public abstract int delete(MetadataOpercmdEntity metadataOpercmdEntity);

	public abstract List<MetadataOpercmdEntity> selectByid(MetadataOpercmdEntity metadataOpercmdEntity);

	public abstract List<MetadataOpercmdEntity> selectAll();

	List<MetaOpercmdRemoteEntity> selectListByRemoteDevice(MetadataOpercmdEntity metadataOpercmdEntity);

	public abstract List<MetadataOpercmdEntity> selectCmdType();
	public abstract List<MetadataOpercmdEntity> selectCardCmdType();

	public abstract OperCmdMgmtPageEntity queryServgroupByparam(OperCmdMgmtPageEntity operCmdMgmtPage);
	
	/**
	 * 根据主目录例系统管理参数查询权限
	 *
	 * <p>2016年7月14日 上午10:23:58 
	 * <p>create by hjc
	 * @param groupIdx
	 * @return
	 */
	public abstract List<Map<String, Object>> getBussinessTypeByGroupId(String groupIdx);
	
	/**
	 * 根据次目录ID号查询权限，前四位
	 *
	 * <p>2016年7月14日 上午10:24:22 
	 * <p>create by hjc
	 * @param opercmd
	 * @param operator_group_idx
	 * @return 
	 */
	public abstract List<Map<String, Object>> getOpercmdListByOpercmd(String opercmd,String operator_group_idx);
	/**
	 * 查询function 功能状态
	 * @return
	 */
	public abstract List<MetadataOpercmdEntity> selectDevAllFunction();
	
	/**
	 * 馆员App:查询馆员有没有设备监控权限
	 * @param req
	 * @return
	 */
	public abstract List<MetadataOpercmdEntity> checkPermission(Map<String,Object> param);
	
	public abstract List<MetadataOpercmdEntity> selectDeviceOperLogCmd();
}
