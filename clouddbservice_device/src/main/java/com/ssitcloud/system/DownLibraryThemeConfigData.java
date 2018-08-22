package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.devmgmt.entity.ConfigDataEntityF;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.interfaceconfig.dao.JsonDataDao;
import com.ssitcloud.utils.MBeanUtil;

@Component("library_theme_config")
public class DownLibraryThemeConfigData implements TableCommand{

	@Resource
	private JsonDataDao configDataDao;

	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("deviceIdx", device_id);
			params.put("libId", Integer.parseInt(library_idx));
			List<ConfigDataEntityF> list = configDataDao.queryConfigDataByDevIdAndLibIdx(params);
			if(list!=null&&list.size()>0){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(list,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return result;
	}

}
