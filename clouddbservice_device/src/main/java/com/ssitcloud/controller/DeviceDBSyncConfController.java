package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncTempEntity;
import com.ssitcloud.entity.page.DBSyncTempPageEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.DeviceDBSyncConfService;
import com.ssitcloud.service.DeviceService;

@Controller
@RequestMapping(value={"dbsynconf"})
public class DeviceDBSyncConfController {
	
	
	
	@Resource
	private DeviceDBSyncConfService deviceDBSyncConfService;
	
	@Resource
	DeviceConfigService deviceConfigService;
	
	@Resource
	private DeviceService deviceService;
	/**
	 * 新增同步设置模板（device_dbsync_template表）
	 * 格式
	 * json={
		device_tpl_ID:""
		device_tpl_desc:""
		device_type:""
	 * }
	 * @methodName: AddNewDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddNewDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity AddNewDeviceDbsyncTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceDBSyncConfService.insertDeviceDBSyncTemp(JsonUtils.fromJson(req.getParameter("json"), DeviceDBSyncTempEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 更新同步设置模板（device_dbsync_template表）
	 * json={
		device_tpl_ID:""
		device_tpl_desc:""
		device_type:""
	 * }
	 * @methodName: UpdDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"UpdDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity UpdDeviceDbsyncTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceDBSyncConfService.updateDeviceDBSyncTemp(JsonUtils.fromJson(req.getParameter("json"), DeviceDBSyncTempEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 删除同步设置模板device_dbsync_template表）
	 * 格式：
	 * json=id
	 * @methodName: DelDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DelDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity DelDeviceDbsyncTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
				String json=req.getParameter("json");
				int re=deviceDBSyncConfService.deleteDeviceDBSyncTemp(JsonUtils.fromJson(json, DeviceDBSyncTempEntity.class));
				result.setState(re>=1?true:false);
				result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询同步模板设置
	 * 
	 * @methodName: SelDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity SelDeviceDbsyncTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceDBSyncTempEntity> dbsyncTemps=deviceDBSyncConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), DeviceDBSyncTempEntity.class));
			result.setResult(dbsyncTemps);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 新建同步配置
	 * @methodName: AddNewDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddNewDeviceDbsync"})
	@ResponseBody
	public ResultEntity AddNewDeviceDbsync(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceDBSyncConfService.insertDeviceDBSyncConf(JsonUtils.fromJson(req.getParameter("json"), DeviceDBSyncConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 添加同步配置，修改设备配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"AddDbsyncAndUpdDevCfg"})
	@ResponseBody
	public ResultEntity AddDbsyncAndUpdDevCfg(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceDBSyncConfigEntity> list = JsonUtils.fromJson(json, new TypeReference<List<DeviceDBSyncConfigEntity>>() {});
			int re =0;
			DeviceDBSyncConfigEntity deviceDBSyncConfig = list.get(0);
			DeviceConfigEntity dEntity = new DeviceConfigEntity();
			dEntity.setDevice_idx(deviceDBSyncConfig.getDevice_dbsync_id());
			dEntity.setDevice_dbsync_tpl_flg(0);
			int ret = deviceConfigService.updateDeviceConfig(dEntity);
			if(ret>0){
				for (DeviceDBSyncConfigEntity dbSyncConfigEntity : list) {
					re += deviceDBSyncConfService.InsertDbsyncAndUpdDevCfg(dbSyncConfigEntity);
				}
			}
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			if(result.getState()){
				//下发数据到设备端
				DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
				deviceConfigEntity.setLibrary_idx(deviceDBSyncConfig.getLibrary_idx());
				deviceConfigEntity.setDevice_idx(deviceDBSyncConfig.getDevice_dbsync_id());
				deviceConfigEntity.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
				deviceService.pushMessage(deviceConfigEntity, deviceConfigEntity.getLibrary_idx(), deviceDBSyncConfig.getDevice_dbsync_id());
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 按设备更新同步配置List<p>
	 * 1.模板数据 更新 为数据<p>
	 * 2.数据 更新 为数据<p>
 	 * @methodName: UpdDevDbsyncList
	 * @param request
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value={"UpdDevDbsyncList"})
	@ResponseBody
	public ResultEntity UpdDevDbsyncList(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
	    String rexStr="^[0-9]+[a-zA-Z]{1}$";
		try {
			List<DeviceDBSyncConfigEntity> list = JsonUtils.fromJson(json, new TypeReference<List<DeviceDBSyncConfigEntity>>() {});
			DeviceDBSyncConfigEntity dEntity = list.get(0);
			DeviceDBSyncConfigEntity queryDBSyncEntity=new DeviceDBSyncConfigEntity();
			queryDBSyncEntity.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_NOT_MODEL);
			queryDBSyncEntity.setDevice_dbsync_id(dEntity.getDevice_dbsync_id());
			List<DeviceDBSyncConfigEntity> deviceDBSyncConfigs=deviceDBSyncConfService.selectDeviceDBSyncConfig(queryDBSyncEntity);
			if(CollectionUtils.isNotEmpty(deviceDBSyncConfigs)){
				int ret = deviceDBSyncConfService.deleteDeviceDBSyncConf(queryDBSyncEntity);
				if(ret!=deviceDBSyncConfigs.size()){
					throw new RuntimeException("更新删除数据失败！！！");
				}
			}
			if(CollectionUtils.isNotEmpty(list)){
				int re =0;
				int nullNum=0;
				for (DeviceDBSyncConfigEntity dbSyncConfigEntity : list) {
					String sync_cycle=dbSyncConfigEntity.getSync_cycle();
					if(sync_cycle!=null){
						if(org.springframework.util.StringUtils.hasText(sync_cycle)&&sync_cycle.matches(rexStr)){
							//如果同步周期不符合条件则 不插入数据库
							re +=deviceDBSyncConfService.insertDeviceDBSyncConf(dbSyncConfigEntity);
						}else if(dbSyncConfigEntity.getIssync()==0&&!sync_cycle.matches(rexStr)){
							//如果没有填写 同步周期的单位 则 sync_cycle=undefined-1 并且 is_sync==0 则表示没有填写该项内容,则不插入数据库
							nullNum++;
						}
					}else{
						nullNum++;
					}
				}
				if(re!=list.size()-nullNum){
					throw new RuntimeException("新增数据失败！！！");
				}
			}
			DeviceConfigEntity deviceConfigEntity=new DeviceConfigEntity();
			deviceConfigEntity.setDevice_idx(dEntity.getDevice_dbsync_id());
			DeviceConfigEntity deviceConfig=deviceConfigService.queryDeviceConfigByDeviceIdx(deviceConfigEntity);
			if(deviceConfig!=null&&deviceConfig.getDevice_dbsync_tpl_flg()==DeviceConfigEntity.IS_MODEL){
				//原本是使用模板 则
				DeviceConfigEntity updateEntity=new DeviceConfigEntity();
				updateEntity.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
				updateEntity.setDevice_idx(deviceConfig.getDevice_idx());
				int updNum=deviceConfigService.updateDeviceConfig(updateEntity);
				if(updNum<=0){
					throw new RuntimeException("更新device_config表失败！！！");
				}
			}
			
			//下发数据到设备端
			if(deviceConfig != null){
				deviceConfigEntity.setLibrary_idx(deviceConfig.getLibrary_idx());
				deviceConfigEntity.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
				deviceService.pushMessage(deviceConfigEntity, deviceConfigEntity.getLibrary_idx(), deviceConfig.getDevice_idx());
			}
			
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 更新同步配置
	 * @methodName: UpdDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"UpdDeviceDbsync"})
	@ResponseBody
	public ResultEntity UpdDeviceDbsync(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceDBSyncConfService.updateDeviceDBSyncConf(JsonUtils.fromJson(json, DeviceDBSyncConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除同步配置
	 * @methodName: DelDevDbsyncList
	 * @param request
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value={"DelDevDbsyncList"})
	@ResponseBody
	public ResultEntity DelDevDbsyncList(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceDBSyncConfigEntity> list = JsonUtils.fromJson(json, new TypeReference<List<DeviceDBSyncConfigEntity>>() {});
			int re =0;
			for (DeviceDBSyncConfigEntity dbSyncConfigEntity : list) {
				re +=deviceDBSyncConfService.deleteDeviceDBSyncConf(dbSyncConfigEntity);
			}
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除同步配置
	 * @methodName: DelDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DelDeviceDbsync"})
	@ResponseBody
	public ResultEntity DelDeviceDbsync(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceDBSyncConfService.deleteDeviceDBSyncConf(JsonUtils.fromJson(req.getParameter("json"), DeviceDBSyncConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询同步配置数据
	 * @methodName: SelDeviceDbsync
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelDeviceDbsync"})
	@ResponseBody
	public ResultEntity SelDeviceDbsync(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceDBSyncConfigEntity> syncConfs=deviceDBSyncConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), DeviceDBSyncConfigEntity.class));
			result.setResult(syncConfs);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"SelDeviceDbsyncBytype"})
	@ResponseBody
	public ResultEntity SelDeviceDbsyncBytype(HttpServletRequest req,String json){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceDBSyncConfigEntity> list=deviceDBSyncConfService.selectDeviceDBSyncConfig(JsonUtils.fromJson(json, DeviceDBSyncConfigEntity.class));
			result.setResult(list);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 获取所有数据同步配置模板
	 *
	 * <p>2016年4月25日 下午6:53:27
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SelAllDBSyncTempList")
	@ResponseBody
	public ResultEntity SelAllDBSyncTempList(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			Map<String, String> param = new HashMap<String, String>();
			if (!StringUtils.isBlank(json)) {
				param = JsonUtils.fromJson(json, Map.class);
			}
			List<Map<String, Object>> dbSyncTempsList=deviceDBSyncConfService.selAllDBSyncTempList(param);
			result.setResult(dbSyncTempsList);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 分页查询数据同步模板配置
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	@RequestMapping("/SelDbsyncTemp")
	@ResponseBody
	public ResultEntity SelDbsyncTemp(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			
			DBSyncTempPageEntity dbSyncTempsList=deviceDBSyncConfService.selDbsynctemp(json);
			result.setResult(dbSyncTempsList);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除数据同步配置模板
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	@RequestMapping("/DelDbsyncTemp")
	@ResponseBody
	public ResultEntity DelDbsyncTemp(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			result =deviceDBSyncConfService.DelDbsynctemp(json);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 更新数据同步配置模板
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	@RequestMapping("/UpdDbsyncTemp")
	@ResponseBody
	public ResultEntity UpdDbsyncTemp(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			result = deviceDBSyncConfService.UpdDbsynctemp(json);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 新增数据同步配置模板
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	@RequestMapping("/AddNewDbsyncTemp")
	@ResponseBody
	public ResultEntity AddNewDbsyncTemp(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			result = deviceDBSyncConfService.AddDbsynctemp(json);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
