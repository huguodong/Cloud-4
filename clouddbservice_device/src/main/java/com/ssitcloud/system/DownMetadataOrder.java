package com.ssitcloud.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.MetadataOrderDao;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.entity.MetadataOrderEntity;
import com.ssitcloud.system.entity.MetadataOrderRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="metadata_order")
public class DownMetadataOrder implements TableCommand{
	
	@Resource
	private MetadataOrderDao metadataOrderDao;
	
	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			//根据实际情况，这里需要获取到系统 类型，但是现在定义的接口没有
			String tableName=downcfgSync.getTable();
			MetadataOrderEntity metadataOrderEntity=new MetadataOrderEntity();
			List<MetadataOrderRemoteEntity> metadataOrders=metadataOrderDao.selectByDown(metadataOrderEntity);
			
			if(metadataOrders!=null){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(metadataOrders,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
