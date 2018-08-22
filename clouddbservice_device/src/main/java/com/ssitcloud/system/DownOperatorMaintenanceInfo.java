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
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.MaintenanceCardDao;
import com.ssitcloud.dao.OperatorMaintenanceInfoDao;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.entity.MaintenanceCardEntity;
import com.ssitcloud.entity.OperatorMaintenanceInfoEntity;
import com.ssitcloud.entity.ReaderTypeEntity;
import com.ssitcloud.entity.UserRolePermessionEntity;
import com.ssitcloud.service.ReaderTypeService;
import com.ssitcloud.service.UserService;
import com.ssitcloud.system.entity.MaintenanceInfoRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="operator_maintenance_info")
public class DownOperatorMaintenanceInfo implements TableCommand{

	@Resource
	private UserService userService;
	@Resource
	private ReaderTypeService readerTypeService;
	@Resource
	private OperatorMaintenanceInfoDao maintenanceInfoDao;
	
	@Resource
	private MaintenanceCardDao maintenanceCardDao;
	/**
	 * {
		  "fields":{
		  	"":1,"":2...
		  }
		  "table": "device_run_config",
		  "records":{
		  	1:"",
		  	2:"",
		  	3:"opercmds"
		  },
		  "opercmds":[
			{"":"","":""},
			{"":"","":""}
		  ]
		}
	 */
	/**
	 * {
		    "servicetype":"ssitcloud",
		    "target":"ssitcloud",
		    "operation":"downloadCfgSync",
		    "data":{
		        "state":true,
		        "message":"",
		        "retMessage":"",
		        "result":{
		            "table":"operator_maintenance_info",
		            "fields":{
		                "card_type":"1",
		                "card_pwd":"3",
		                "opercmds":"4",
		                "card_id":"2"
		            },
		            "records":[
		                {
		                    "1":"1",
		                    "2":"1231222",
		                    "3":"3123123",
		                    "4":"00040101,00040102,00040103,00020101,00020102,00020103,00020104,00020105,00020201"
		                },
		                {
		                    "1":"1",
		                    "2":"13222",
		                    "3":"32",
		                    "4":"00040101,00040102,00040103,00020101,00020102,00020103,00020104,00020105,00020201"
		                }
		            ]
		        }
		    }
		}
	 */
	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			
			String tableName=downcfgSync.getTable();
			downcfgSync.getdBName();
			downcfgSync.getKeyName();
			//查询模板信息
			Map<String, Object> map = new HashMap<>();
//			map.put("device_id", device_id);
//			map.put("library_id", library_idx);
			map.put("lib_idx", library_idx);
			
//			ReaderTypeEntity readerType=new ReaderTypeEntity();
//			readerType.setLibrary_idx(Integer.parseInt(library_idx));
//			readerType.setType_distinction("2");//维护卡
//			List<ReaderTypeEntity> readerTypes=readerTypeService.selectReaderType(readerType);
			List<MaintenanceInfoRemoteEntity> maintenanceInfoRemotes=new ArrayList<>();
//			List<Integer> typeIdxList=new ArrayList<>();
			
			maintenanceInfoRemotes = maintenanceCardDao.queryMaintenanceCardInfo(map);
//			UserRolePermessionEntity userRolePermession=new UserRolePermessionEntity();
//			userRolePermession.setOperbusinesstype("2");
//			if(CollectionUtils.isNotEmpty(readerTypes)){
//				for(ReaderTypeEntity rType:readerTypes){
//					MaintenanceInfoRemoteEntity maintenanceInfoRemote=new MaintenanceInfoRemoteEntity();
//					Integer typeIdx=rType.getType_idx();
//					typeIdxList.add(typeIdx);
//					maintenanceInfoRemote.setMaintenance_card_id(rType.getType_id());
//					List<OperatorMaintenanceInfoEntity> maintenanceInfos=maintenanceInfoDao.queryOperatorMaintenanceCardByTypeIdxs(typeIdxList);
//					if(CollectionUtils.isNotEmpty(maintenanceInfos)){
//						for(OperatorMaintenanceInfoEntity operatorMaintenanceInfo:maintenanceInfos){
//							Integer operIdx=operatorMaintenanceInfo.getOperator_idx();
//							userRolePermession.setOperator_idx(operIdx);
//							List<UserRolePermessionEntity> userRolePermessions=userService.queryByUserRolePermessionEntity(userRolePermession);
//							//设备对应的维护卡和 维护卡对应operator_idx的的权限
//							if(CollectionUtils.isNotEmpty(userRolePermessions)){
//								List<String> cmdList=new ArrayList<>();
//								for(UserRolePermessionEntity permession:userRolePermessions){
//									//Map<String,String> cmdMap=new HashMap<>();
//									//cmdMap.put("opercmd", permession.getOpercmd());
//									//cmdMap.put("opercmddesc",permession.getOpercmd_desc());
//									cmdList.add(permession.getOpercmd());
//								}
//								maintenanceInfoRemote.setOpercmds(JsonUtils.toJson(cmdList));
//							}
//						}
//					}
//					maintenanceInfoRemotes.add(maintenanceInfoRemote);
//				}
//			}
			result.setResult(MBeanUtil.makeReturnResultEntityFromList(maintenanceInfoRemotes, tableName));
			result.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
