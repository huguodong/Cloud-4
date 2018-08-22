package com.ssitcloud.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.ReaderTypeDao;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.entity.ReaderTypeEntity;
import com.ssitcloud.system.entity.ReaderTypeRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;
/**
 * 下载读者信息
 * @author lbh
 *
 */
@Component("reader_type")
public class DownReaderType implements TableCommand{
	@Resource
	private ReaderTypeDao readerTypeDao;
	
	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			//downcfgSync.getdBName();
			//downcfgSync.getKeyName();
			//查询模板信息
			ReaderTypeEntity readerTypeEntity=new ReaderTypeEntity();
			readerTypeEntity.setLibrary_idx(Integer.parseInt(library_idx));
			readerTypeEntity.setType_distinction("1");
			List<ReaderTypeEntity> readerTypes=readerTypeDao.select(readerTypeEntity);
			List<ReaderTypeRemoteEntity> readerTypeRemotes=new ArrayList<>();
			if(CollectionUtils.isNotEmpty(readerTypes)){
				for(ReaderTypeEntity readerType:readerTypes){
					ReaderTypeRemoteEntity readerTypeRemote=new ReaderTypeRemoteEntity();
					readerTypeRemote.setCard_fee(readerType.getCard_fee());
					readerTypeRemote.setType_deposit(readerType.getType_deposit());
					readerTypeRemote.setType_id(readerType.getType_id());
					readerTypeRemote.setType_name(readerType.getType_name());
					readerTypeRemote.setVerification_fee(readerType.getVerification_fee());
					readerTypeRemote.setType_idx(readerType.getType_idx());
					readerTypeRemotes.add(readerTypeRemote);
				}
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(readerTypeRemotes,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
