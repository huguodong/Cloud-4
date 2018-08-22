package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.MaintenanceCardDao;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.system.entity.MaintenanceInfoRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;
/**
 * 下载读者信息
 * @author lbh
 *
 */
@Component("maintenance_card")
public class DownMaintenanceCard implements TableCommand{
	@Resource
	private MaintenanceCardDao maintenanceCardDao;
	
	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			//downcfgSync.getdBName();
			//downcfgSync.getKeyName();
			
			Map<String,Object> map = new HashMap<>();
			map.put("lib_idx", Integer.parseInt(library_idx));
			List<MaintenanceInfoRemoteEntity> maintenanceInfoRemotes=maintenanceCardDao.queryMaintenanceCardInfo(map);
			if(CollectionUtils.isNotEmpty(maintenanceInfoRemotes)){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(maintenanceInfoRemotes,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
