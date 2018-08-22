package com.ssitcloud.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.MetadataOpercmdDao;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.entity.MetadataOpercmdEntity;
import com.ssitcloud.system.entity.MetaOpercmdRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;
/**
 * 设备端 同步 metadata_opercmd数据
 * <p>2016年6月24日 下午2:59:07
 * @author lbh
 *
 */
@Component(value="metadata_opercmd")
public class DownMetadataOpercmd implements TableCommand{

	@Resource
	private MetadataOpercmdDao opercmdDao;
	
	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			//String library_idx=downcfgSync.getLibrary_id();
			//String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			downcfgSync.getdBName();
			downcfgSync.getKeyName();
			/***
			 * 同步权限的话需要获取的该设备的权限，现在实际操作中并没有可以控制设备端的权限，所以 下发所有设备端权限
			 */
			
				MetadataOpercmdEntity metadataOpercmd=new MetadataOpercmdEntity();
				metadataOpercmd.setOpercmd_typeinfo(4);//设备端权限
				List<MetaOpercmdRemoteEntity> metaOpercmds=opercmdDao.selectListByRemoteDevice(metadataOpercmd);
				if(metaOpercmds!=null){
					result.setResult(MBeanUtil.makeReturnResultEntityFromList(metaOpercmds,tableName));
					result.setState(true);
				}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
