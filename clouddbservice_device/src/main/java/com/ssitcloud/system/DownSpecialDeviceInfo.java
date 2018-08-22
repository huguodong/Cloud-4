package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.SpecialDeviceDao;
import com.ssitcloud.devmgmt.entity.SpecialDeviceEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="special_device")
public class DownSpecialDeviceInfo implements TableCommand{

	@Resource
	private SpecialDeviceDao specialDeviceDao;

	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String service_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			//查询模板信息
			Map<String, String> map = new HashMap<>();
			map.put("service_id", service_id);
			map.put("library_idx", library_idx);
			
			List<SpecialDeviceEntity> specialDeviceEntityInfos=specialDeviceDao.querySpecialDeviceInfobyServiceIdxAndDeviceId(map);
			if(specialDeviceEntityInfos!=null){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(specialDeviceEntityInfos,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
