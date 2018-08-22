package com.ssitcloud.datasync.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.Log;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.AskCfgSyncResultEntity;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.DeviceConfigEntity;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;

@Component(value="askCfgSync")
public class AskCfgSync extends BasicServiceImpl implements DataSyncCommand{
	
	private static final String URL_queryDeviceConfigByDeviceIdx = "queryDeviceConfigByDeviceIdx";
	private static final String URL_queryDeviceConfigNewAndOldByDeviceIdx = "queryDeviceConfigNewAndOldByDeviceIdx";
	
	@Resource(name="heartBeatChangeTableData")
	private HeartBeatChangeTableData heartBeatChangeTableData;
	
	/**
	 * 在正式使用的是否，返回数据后要把heartBeatChangeTableData 对应的device_id 的队列的数据清除掉
	 * 
	 * 测试的暂时可以不清空队列
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		String lib_id = map.get(LIB_ID) + "";
		String dev_id = map.get(DEV_ID) + "";
		//String tables="";
		List<String> tables=new ArrayList<>();
		List<Integer> triIdxList=new ArrayList<>();
		List<String> triTabNameList=new ArrayList<>();
		getChangeTables(tables,triIdxList,triTabNameList,dev_id);
		
		AskCfgSyncResultEntity askCfgSyncResult=new AskCfgSyncResultEntity(dev_id,lib_id,AskCfgSyncResultEntity.RequestResult_allow,"",	StringUtils.collectionToCommaDelimitedString(tables));
		resp.getData().setResult(askCfgSyncResult);
		resp.getData().setState(true);
		
		Log.DebugOnScr(JsonUtils.toJson(resp));
		return resp;
	}
	/**
	 * 获取有变化的配置表
	 * @param tables
	 * @param triIdxList
	 * @param triTabNameList
	 * @param dev_id
	 */
	@SuppressWarnings("unchecked")
	private void  getChangeTables(List<String> tables,
			List<Integer> triIdxList, List<String> triTabNameList,String dev_id) {
		if(!"null".equals(dev_id)&&heartBeatChangeTableData.containsKey(dev_id)){//deivce_id,如果存在
			Queue<TableChangeTriEntity> changes=heartBeatChangeTableData.get(dev_id);
			if(changes!=null&&changes.size()>0){
				for(TableChangeTriEntity change:changes){
					String tableName=change.getTable_name();
					Integer triIdx=change.getTri_idx();//如果涉及到有多个设备都使用的话，只要有一个设备返回了（表示内存中已经存在），则不直接设置requestTime
					Integer dataIdx=change.getData_idx();
					//新注册或者 更换模板的时候出现 tableName== device_config的情况
					if("device_config".equals(tableName)&&"I".equals(change.getChange_state())){
						/**
						 * 1.这种情况是新注册设备 、新注册设备自定义数据
						 */
						tableName="";//清空tableName
						//dataIdx ==device_idx
						//需要远程查询出device_config的数据
						ResultEntity result=queryDeviceConfigByDeviceIdx(dataIdx);
						if(result!=null&&result.getResult()!=null){
							List<Map<String,Object>> obj=(List<Map<String,Object>>)result.getResult();
							if(CollectionUtils.isNotEmpty(obj)){
								DeviceConfigEntity deviceConf=JsonUtils.convertMapToObject(obj.get(0), DeviceConfigEntity.class);
								if(deviceConf!=null){
									Integer dbSyncTplFlg=deviceConf.getDevice_dbsync_tpl_flg();
									Integer extTplFlg=deviceConf.getDevice_ext_tpl_flg();
									Integer runTplFlg=deviceConf.getDevice_run_tpl_flg();
									if(DeviceConfigEntity.IS_MODEL==dbSyncTplFlg){
										//同步数据模板、如果是模板,则需要同步，是数据则不管，其他的已经操作
										tables.add("device_dbsync_config");
									}
									if(DeviceConfigEntity.IS_MODEL==extTplFlg){
										tables.add("device_ext_config");
									}
									if(DeviceConfigEntity.IS_MODEL==runTplFlg){
										tables.add("device_run_config");
									}
									//如果是插入的话 表示注册设备，需要同步原始数据表的数据
									tables.add("metadata_order");
									tables.add("metadata_opercmd");
									tables.add("metadata_run_config");
									tables.add("metadata_logic_obj");
									tables.add("metadata_ext_model");
									tables.add("view_config");
									tables.add("library_theme_config");
									tables.add("device_theme_config");
									tables.add("reader_type");
									tables.add("maintenance_card");
									/*tables.add("device_service");
									tables.add("device_service_queue");
									tables.add("special_device");*/
								}
							}else{
								LogUtils.error("askCfgSync result为空: "+obj);
							}
						}else{
							if(result!=null){
								LogUtils.error("askCfgSync 出现错误："+result.getRetMessage());
							}else{
								LogUtils.error("askCfgSync queryDeviceConfigByDeviceIdx 出现错误：result is null");
							}
							
						}
					}else if("device_config".equals(tableName)&&"U".equals(change.getChange_state())){
						/**
						 * 这种情况是
						 * 2.设备管理 选择了别的模板 
						 * 3.由模板改成自定义数据
						 */
						//1.得到device_idx 查询 device_config_old、device_config表数据
						//2.比较两个表的数据，看看哪个字段不一致，不一致的模板字段取出来，得到对应变更的表名
						//3.后台API直接传过来需要同步的List<String>
						//这里面执行了删除操作 false 不删除 等到下载了才删除
						ResultEntity result= queryDeviceConfigNewAndOldByDeviceIdx(dataIdx);
						if(result!=null){
							List<String> tableNameList=(List<String>)result.getResult();
							if(tableNameList!=null&&tableNameList.size()>0){
								if(CollectionUtils.isNotEmpty(tableNameList)){
									for(String table:tableNameList){
										if(!tables.contains(table)){
											tables.add(table);
										}
									}
								}
							}
						}
					}else if("device_config".equals(tableName)&&"D".equals(change.getChange_state())){
						//这种情况是删除设备的情况下，不需要同步device_config表  但是需要更新操作RquestTime 操作
						LogUtils.info("dataIdx："+dataIdx+" 删除设备操作");
						triIdxList.add(triIdx);
					}else if(tableName!=null&&!tables.contains(tableName)){
						tables.add(tableName);
					}
					if("metadata_opercmd".equals(tableName)||"metadata_order".equals(tableName)){
						if(!triTabNameList.contains(tableName)){
							triTabNameList.add(tableName);
						}
					}else if(triIdx!=null){
						triIdxList.add(triIdx);
					}
				}
			}
		}
	}
	public ResultEntity queryDeviceConfigByDeviceIdx(Integer deviceIdx){
		ResultEntity result=new ResultEntity();
		Map<String, String> params=new HashMap<String, String>();
		params.put("req", "{\"device_idx\":\""+deviceIdx+"\"}");
		String resDeviceConfig=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_queryDeviceConfigByDeviceIdx), params, charset);
		if(JSONUtils.mayBeJSON(resDeviceConfig)){
			result=JsonUtils.fromJson(resDeviceConfig, ResultEntity.class);
		}else{
			System.out.println(resDeviceConfig);
			result.setRetMessage(resDeviceConfig);
		}
		return result;
	}
	/**
	 * 直接返回来需要同步的表名 逗号分隔
	 * @param deviceIdx
	 * @return
	 */
	public ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(Integer deviceIdx){
		Map<String, String> params=new HashMap<String, String>();
		params.put("req", "{\"device_idx\":\""+deviceIdx+"\",\"delete\":\"false\"}");
		String resDeviceConfig=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_queryDeviceConfigNewAndOldByDeviceIdx), params, charset);
		if(JSONUtils.mayBeJSON(resDeviceConfig)){
			return JsonUtils.fromJson(resDeviceConfig, ResultEntity.class);
		}
		return null;
	}
	
	/*@Async
	public void updateRequestTimeByTriIdxs(List<Integer> triIdxList){
		String res=postRetStr(URL_setRequestTimeByTriIdxs, JsonUtils.toJson(triIdxList));
		if(JSONUtils.mayBeJSON(res)){
			LogUtils.info("update requestTime tri_change_table数据返回结果:"+res);
		}
	}
	@Async
	public void updateRequestTimeByTriTableName(List<String> triTabNameList){
		String res=postRetStr(URL_updateRequestTimeByTriTableName, JsonUtils.toJson(triTabNameList));
		if(JSONUtils.mayBeJSON(res)){
			LogUtils.info("update requestTime tri_change_table数据返回结果:"+res);
		}
	}*/
}
