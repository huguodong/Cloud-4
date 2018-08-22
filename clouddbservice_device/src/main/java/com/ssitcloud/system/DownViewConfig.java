package com.ssitcloud.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.devmgmt.entity.DataEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.interfaceconfig.dao.JsonDataDao;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="view_config")
public class DownViewConfig implements TableCommand{
		
	@Resource
	private JsonDataDao configDao;
	
	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			
			List<DataEntity> devconfs = configDao.queryViewConfigData();
			//one
			if(devconfs!=null&&devconfs.size()>0){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(devconfs,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return result;
	}

}
