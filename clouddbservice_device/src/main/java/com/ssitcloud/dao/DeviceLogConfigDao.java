/**
 * 
 */
package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.DeviceLogConfigEntity;

/**
 * @comment
 * 
 * @author hwl
 * @data 2016年4月11日
 */
public interface DeviceLogConfigDao extends CommonDao {

	public abstract int insert(DeviceLogConfigEntity deviceLogConfigEntity);

	public abstract int update(DeviceLogConfigEntity deviceLogConfigEntity);

	public abstract int delete(DeviceLogConfigEntity deviceLogConfigEntity);

	public abstract List<DeviceLogConfigEntity> select(DeviceLogConfigEntity deviceLogConfigEntity);

}
