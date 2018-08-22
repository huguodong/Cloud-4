package com.ssitcloud.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.exception.DeleteDeviceErrorExeception;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.entity.DeivceIdxAndIDEntity;
import com.ssitcloud.entity.DeleteDeviceResultEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceMgmtEntity;
import com.ssitcloud.entity.page.DeviceMgmtAppPageEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.param.DeviceRegisterParam;
import com.ssitcloud.service.DeviceRegsterService;
import com.ssitcloud.service.DeviceService;
import com.ssitcloud.service.DeviceServiceQueueService;
import com.ssitcloud.service.DeviceServiceService;
import com.ssitcloud.service.SpecialDeviceService;

/**
 * 
 * @comment 设备表Controller
 * 
 * @author hwl
 * @data 2016年4月7日
 */
@Controller
@RequestMapping("/device")
public class DeviceController {

	@Resource
	DeviceService deviceService;
	@Resource
	private DeviceRegsterService deviceRegsterService;
	
	@Resource
	private DeviceServiceService deviceServiceService;
	
	@Resource
	private DeviceServiceQueueService deviceServiceQueueService;
	
	@Resource
	private SpecialDeviceService specialDeviceService;
	/**
	 * 添加设备数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddNewDevice" })
	@ResponseBody
	public ResultEntity AddNewDevice(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = deviceService.addDevice(JsonUtils.fromJson(
					request.getParameter("json"), DeviceEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新设备数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdDevice" })
	@ResponseBody
	public ResultEntity UpdDevice(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = deviceService.updDevice(JsonUtils.fromJson(
					request.getParameter("json"), DeviceEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除设备数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteDevice" })
	@ResponseBody
	public ResultEntity DeleteDevice(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = deviceService.delDevice(JsonUtils.fromJson(
					request.getParameter("json"), DeviceEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 根据条件查询设备数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectDevice" })
	@ResponseBody
	public ResultEntity SelectDevice(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<DeviceEntity> deviceEntities = deviceService
					.selbyidDevice(JsonUtils.fromJson(
							request.getParameter("json"), DeviceEntity.class));
			result.setResult(deviceEntities);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 获取设备信息
	 * @methodName: SelectDeviceByPage
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntityF<DatagridPageEntity<DeviceEntity>>
	 * @author: liuBh
	 */
	@RequestMapping(value="SelectDeviceByPage")
	@ResponseBody
	public ResultEntityF<DevicePageEntity> SelectDeviceByPage(HttpServletRequest request,String req){
		ResultEntityF<DevicePageEntity> result =new ResultEntityF<DevicePageEntity>(); 
		DevicePageEntity page=new DevicePageEntity();
		
		if(StringUtils.hasText(req)){
			 page=JsonUtils.fromJson(req, DevicePageEntity.class);
		}
		page.setDoAount(true);
		Integer operator_idx_Limit=page.getOperator_idx_Limit();
		boolean devGroupLimit=false;
		if(operator_idx_Limit!=null){
			devGroupLimit=true;
		}
		page=deviceService.selbyDevicePage(page, operator_idx_Limit, devGroupLimit);
		result.setResult(page);
		result.setState(true);
		return result;
	}
	/**
	 * 
	 * 通过设备ID查询设备IDX
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value="QueryDevIdxByDevId")
	@ResponseBody
	public ResultEntity QueryDevIdxByDevId(HttpServletRequest request,String req){
		ResultEntity result =new ResultEntity(); 
		List<DeivceIdxAndIDEntity> list=deviceService.selectDeviceIdAndIdx();
		if(list==null){
			result.setState(false);
			return result;
		}
		Map<Integer,String> map=new HashMap<Integer, String>(100);
		for(DeivceIdxAndIDEntity deivceIdxAndID:list){
			String device_id=deivceIdxAndID.getDevice_id();
			Integer deivce_idx=deivceIdxAndID.getDevice_idx();
			if(StringUtils.hasText(device_id)&&deivce_idx!=null){
				map.put(deivce_idx,device_id);
			}
		}
		result.setResult(map);
		result.setState(true);
		return result;
	}
	
	
	/**
	 * 注册信息
	 * 
	 *     |馆ID[library_idx] 
	 *     |设备ID[device_id]
	 *  1--|设备名[device_name]
	 *     |设备类型[device_model_idx]
	 *     |设备特征码[device_code]
	 * 
	 *  2--硬件配置模板[DeviceExtTempEntity类]
	 * 
	 *  3--设备监控模板[DeviceMonTempEntity类]
	 * 
	 *  4--运行模板[DeviceRunTempEntity类]
	 * 
	 *  5--数据同步模板[DeviceDBSyncTempEntity类]
	 *  
	 *  
	 *  
	 * 
	 * @methodName: AddRegisterInfo
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddRegisterInfo"})
	@ResponseBody
	public ResultEntity AddRegisterInfo(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceRegsterService.doDeviceRegister(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	@RequestMapping(value={"SelectDeviceMgmt"})
	@ResponseBody
	public ResultEntity SelectDeviceMgmt(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			PageEntity p = deviceService.SelectDeviceMgmt(map);
			result.setResult(p);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
	
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	
	/**
	 * 删除设备相关信息
	 * @methodName 
	 * @returnType ResultEntity
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"DeleteDeviceMgmt"})
	@ResponseBody
	public ResultEntity DeleteDeviceMgmt(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		DeleteDeviceResultEntity deleteDeviceResult=new DeleteDeviceResultEntity();
		List<String> deleteSuccessDeviceIds=new ArrayList<>();
	    List<String> deleteFailDeviceIds=new ArrayList<>();
		int re = 0;
		try {
			List<DeviceMgmtEntity> deviceMgmtEntities = JsonUtils.fromJson(json,new TypeReference<List<DeviceMgmtEntity>>() {});
			for (Iterator<DeviceMgmtEntity> iterator = deviceMgmtEntities.iterator(); iterator.hasNext();) {
				DeviceMgmtEntity deviceMgmt = (DeviceMgmtEntity)iterator.next();
				try {
					re+=deviceService.DeleteDeviceMgmt(deviceMgmt);
					deleteSuccessDeviceIds.add(deviceMgmt.getDevice_id());
				} catch (DeleteDeviceErrorExeception e) {
					deleteFailDeviceIds.add(deviceMgmt.getDevice_id());
				}
			}
			deleteDeviceResult.setDeleteFailDeviceIds(deleteFailDeviceIds);
			deleteDeviceResult.setDeleteSuccessDeviceIds(deleteSuccessDeviceIds);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			result.setResult(deleteDeviceResult);
			result.setRetMessage("删除成功IDX:"+StringUtils.collectionToCommaDelimitedString(deleteSuccessDeviceIds));
			if(deviceMgmtEntities.size()==1||deleteFailDeviceIds.size()==0){
				result.setMessage("ONE");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 检查系统中是否有deviceCode的设备
	 *
	 * <p>2016年4月28日 下午2:55:11 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"hasDeviceCode"})
	@ResponseBody
	public ResultEntity hasDeviceCode(HttpServletRequest req,String json){
		ResultEntity result=new ResultEntity();
		try {
			if (json!=null && !json.equals("")) {
				Integer ret = deviceService.hasDeviceCode(json);
				result.setResult(ret);
				result.setState(true);
			}else {
				result.setState(false);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据设备号查询设备信息，包括设备metadata_devicetype中的设备类型
	 *
	 * <p>2016年4月28日 下午2:55:11 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/queryDeviceByDeviceCode"})
	@ResponseBody
	public ResultEntity queryDeviceByDeviceCode(HttpServletRequest req,String json){
		ResultEntity result=new ResultEntity();
		try {
			Map<String, Object> map = new HashMap<>();	
			if (json!=null && !json.equals("")) {
				map = deviceService.queryDeviceByDeviceCode(json);
				if(map==null){
					result.setValue(true,"0");
				}else{
					result.setValue(true,"1","",map);
				}
			}else {
				result.setState(false);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 设备注册
	 *
	 * <p>2016年4月28日 下午5:13:26 
	 * <p>create by hjc
	 * @param req
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"deviceRegister"})
	@ResponseBody
	public ResultEntity deviceRegister(HttpServletRequest req,String json){
		ResultEntity result=new ResultEntity();
		DeviceRegisterParam registerParam = new DeviceRegisterParam();
		try {
			if (json!=null && !json.equals("")) {
				registerParam = JsonUtils.fromJson(json, DeviceRegisterParam.class);
				return deviceService.deviceRegister(registerParam);
			}else {
				result.setState(false);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 设备注册
	 *
	 * <p>2018年1月9日 下午15:18:29 
	 * <p>create by liuwei
	 * @param req
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"hydeviceRegister"})
	@ResponseBody
	public ResultEntity hydeviceRegister(HttpServletRequest req,String json){
		ResultEntity result=new ResultEntity();
		DeviceRegisterParam registerParam = new DeviceRegisterParam();
		try {
			if (json!=null && !json.equals("")) {
				registerParam = JsonUtils.fromJson(json, DeviceRegisterParam.class);
				return deviceService.hydeviceRegister(registerParam);
			}else {
				result.setState(false);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 带参数查询设备（设备监控页面）
	 * @param request
	 * @author lbh
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceByParam"})
	@ResponseBody
	public ResultEntity queryDeviceByParam(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.queryDeviceByParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询device_id 或者 device_idx的设备是否存在
	 * {device_id:"1"} or {device_idx:"1"}
	 * 
	 * <p>2016年6月1日 下午1:55:59 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return 存在返回1 不存在返回0
	 */
	@RequestMapping(value={"isExistDeviceWithIdOrIdx"})
	@ResponseBody
	public ResultEntity isExistDeviceWithIdOrIdx(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(json)){
				DeviceEntity deviceEntity = JsonUtils.fromJson(json, DeviceEntity.class);
				int ret = deviceService.isExistDeviceWithIdOrIdx(deviceEntity);
				if (ret >= 1) {
					result.setValue(true, "","","1");
				}else{
					result.setValue(true, "","","0");
				}
			}else{
				result.setValue(false, "请输入设备id或者idx");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据传过来的设备id数组，查询设备库有中有多少设备
	 *
	 * <p>2016年6月2日 下午6:52:28 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"selDeviceCountByIds"})
	@ResponseBody
	public ResultEntity selDeviceCountByIds(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(json)){
				json = json.replace("[", "").replace("]", "").replace(" ", "");
				String[] ids = json.split(",");
				List<String> list = Arrays.asList(ids);
				if (list!=null && list.size()>0) {
					Map<String, Object> param = new HashMap<>();
					param.put("ids", list);
					int ret = deviceService.selDeviceCountByIds(param);
					result.setValue(true, "","",ret);
				}else {
					result.setValue(true, "","","0");
				}
			}else{
				result.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 设备管理点击保存按钮 保存数据
	 * 
	 * 需要修改到的表有
		device_config[变更模板（不是模板改数据或者数据该模板）]
		device[设备类型/图书馆ID/设备物流编号/设备流通地点/设备描述/设备名称/设备ID/ACS信息]
		device_group[设备分组]
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"UpdDeviceMgmtPage"})
	@ResponseBody
	public ResultEntity UpdDeviceMgmtPage(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.UpdDeviceMgmtPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"UpdHyDeviceMgmtPage"})
	@ResponseBody
	public ResultEntity UpdHyDeviceMgmtPage(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.UpdHyDeviceMgmtPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询设备有没有自定义参数配置
	 * @param request
	 * @param req={"device_idx":deviceIdx,"configName":configName}
	 * @return
	 */
	@RequestMapping(value={"checkAllConfigDataByDevIdx"})
	@ResponseBody
	public ResultEntity checkAllConfigDataByDevIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.checkAllConfigDataByDevIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 比较 监控配置模板数据 和 修改后的数据是否一致
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"compareMonitorConfig"})
	@ResponseBody
	public ResultEntity compareMonitorConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.compareMonitorConfig(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 根据 所有图书馆IDX获取所有的对应的device_id
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceIdbyLibIdx"})
	@ResponseBody
	public ResultEntity queryDeviceIdbyLibIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.queryDeviceIdbyLibIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 获取图书馆的设备信息，进行批处理操作， 分页显示， 可以根据参数查询
	 *
	 * <p>2016年9月20日 上午11:55:16 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLibraryDevicesByPage")
	@ResponseBody
	public ResultEntity getLibraryDevicesByPage(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			//library_id   deviceType  deviceId deviegroup
			
			result = deviceService.getLibraryDevicesByPage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 发送请求，修改设备层的设备所属馆信息，并且删除设备的设备组关联
	 *
	 * <p>2016年9月21日 下午6:34:09 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/devTransferToLibrary")
	@ResponseBody
	public ResultEntity devTransferToLibrary(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			//library_id   deviceType  deviceId deviegroup
			
			result = deviceService.devTransferToLibrary(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"selectDeviceCode"})
	@ResponseBody
	public ResultEntity selectDeviceCode(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.selectDeviceCode(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 通过device_id 查出设备类型
	 * 
	 * <p>2017年3月6日 下午1:40 
	 * <p>create by shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectDevicTypeByDeviceId")
	@ResponseBody
	public ResultEntity selectDevicTypeByDeviceId(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.selectDevicTypeByDeviceId(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 通过device_idx查出device_id
	 * 
	 * <p>2017年3月6日 下午3:51
	 * <p>create by shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectDevIdByIdx")
	@ResponseBody
	public ResultEntity selectDevIdByIdx(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.selectDevIdByIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	
	/**
	 * 通过device_type查设备
	 * <p>create by LXP
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectByDevTypeNameList")
	@ResponseBody
	public ResultEntity selectByDevTypeNameList(String req){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			return deviceService.selectByDevTypeNameList(map);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	
	/**
	 * 查询图书馆中所有的设备信息
	 *
	 * <p>2017年3月21日 下午7:30:22 
	 * <p>create by hjc
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllDeviceByLibidx")
	@ResponseBody
	public ResultEntity getAllDeviceByLibidx(String req, HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.getAllDeviceByLibidx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据设备id获取信息
	 *
	 * <p>2017年4月12日 上午10:22:44 
	 * <p>create by hjc
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/selDeviceById")
	@ResponseBody
	public ResultEntity selDeviceById(String req, HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.selDeviceById(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping("/selDeviceIdx")
	@ResponseBody
	public ResultEntity selDeviceIdx(String req, HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.selDeviceIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 通过 图书馆idxs查出对应设备信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"SelectDeviceMgmtByLibraryIdxs"})
	@ResponseBody
	public ResultEntity SelectDeviceMgmtByLibraryIdxs(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		DeviceMgmtAppPageEntity pageEntity = new DeviceMgmtAppPageEntity();
		int page = pageEntity.getPage();
		int pageSize = pageEntity.getPageSize();
		String idxs = "";
		String device_id = "";
		Integer region_idx = 0;
		try {
			String pageMapStr = request.getParameter("page");
			try {
				if(null != pageMapStr && !"".equals(pageMapStr)){
					pageEntity = JsonUtils.fromJson(pageMapStr, DeviceMgmtAppPageEntity.class);
				}else{
					pageEntity.setPageSize(pageSize);
					pageEntity.setPage(page);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			idxs = request.getParameter("libIdxs");
			device_id = request.getParameter("device_id");
			if(org.apache.commons.lang.StringUtils.isNotBlank(request.getParameter("region_idx"))){
				region_idx = Integer.parseInt(request.getParameter("region_idx"));
			}
			
			if(JSONUtils.mayBeJSON(idxs)){
				List<Integer> lib_idxs = JsonUtils.fromJson(idxs, List.class);
				pageEntity.setLibIdxs(lib_idxs);
			}
			if(!StringUtils.isEmpty(device_id)){
				pageEntity.setDevice_id(device_id);
			}
			if(null != region_idx && region_idx != 0){
				pageEntity.setRegion_idx(region_idx);
			}
			
			
			pageEntity = deviceService.SelectDeviceMgmtByLibraryIdxs(pageEntity);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(pageEntity);
	
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据图书馆idx查询设备idx和地区码，若没有设置地区码则地区码为null
	 * @param library_idx
	 */
	@RequestMapping("/selectDeviceRegionByLibidx")
	@ResponseBody
	public ResultEntity selectDeviceRegionByLibidx(String req, HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			if(map.containsKey("library_idx")){
				 List<Map<String,Object>> d = deviceService.selectDeviceRegionByLibidx((Integer) map.get("library_idx"));
				 result.setState(true);
				 result.setResult(d);
			}else{
				result.setMessage("library_idx is null");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 查询设备的地点信息
	 *
	 * <p>2017年9月8日 下午3:10:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryDeviceRegion")
	@ResponseBody
	public ResultEntity queryDeviceRegion(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (!StringUtils.hasText(req)) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			Map<String, Object> map = new HashMap<>();
			map = JsonUtils.fromJson(req, Map.class);
			String devidx = map.get("device_idx")+"";
			resultEntity = deviceService.queryDeviceRegion(devidx);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	/**
	 * 根据 所有图书馆IDX获取所有的对应的获取特殊设备（3D导航、人流量、智能家具）的service_id
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryServiceIdbyLibIdx"})
	@ResponseBody
	public ResultEntity queryServiceIdbyLibIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceServiceService.queryServiceIdbyLibIdx();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryServiceIdbyLibId"})
	@ResponseBody
	public ResultEntity queryServiceIdbyLibId(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceServiceService.queryServiceIdbyLibId();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx获取服务名称 分组serviceid
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryServiceIdbyServiceIdx"})
	@ResponseBody
	public ResultEntity queryServiceIdbyServiceIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceServiceService.queryServiceIdbyServiceIdx();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryDeviceServiceByParams"})
	@ResponseBody
	public ResultEntity queryDeviceServiceByParams(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceServiceService.queryDeviceServiceByParams(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 查询图书馆的地理位置信息
	* @param request
	 * @param req
	 * by liuwei 
	 */
	@RequestMapping(value=("getLibPosition"))
	@ResponseBody
	public ResultEntity getLibPosition(HttpServletRequest request){
		ResultEntity result= new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			
			result=deviceService.getLibPosition(map);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询设备的地理位置信息
	* @param request
	 * @param req
	 * by liuwei 
	 */
	@RequestMapping(value=("getDevicePosition"))
	@ResponseBody
	public ResultEntity getDevicePosition(HttpServletRequest request){
		ResultEntity result= new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			
			result=deviceService.getDevicePosition(map);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询服务设备信息
	* @param request
	 * @param req
	 * by liuwei 
	 */
	@RequestMapping(value=("queryServiceDeviceByParam"))
	@ResponseBody
	public ResultEntity queryServiceDeviceByParam(HttpServletRequest request){
		ResultEntity result= new ResultEntity();
		try {
			String req = request.getParameter("req");
			//Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			
			result=deviceService.queryServiceDeviceByParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 保存设备的位置信息
	 * @param request
	 * by liuwei 
	 */
	@RequestMapping(value=("saveDevicePosition"))
	@ResponseBody
	public ResultEntity saveDevicePosition(HttpServletRequest request){
		ResultEntity result= new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			
			String device_id = (String) map.get("deviceId");
			
			int count = deviceService.queryDeviceById(device_id);
			if(count==0){
				deviceService.saveDevicePosition(map);
			}else{
				deviceService.updateDevicePosition(map);
			}
			
			
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 保存图书馆的位置信息
	 * @param request
	 * by liuwei 
	 */
	@RequestMapping(value=("saveLibPosition"))
	@ResponseBody
	public ResultEntity saveLibPosition(HttpServletRequest request){
		ResultEntity result= new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			
			String lib_id = (String) map.get("lib_id");
			
			int count = deviceService.queryLibById(lib_id);
			if(count==0){
				deviceService.saveLibPosition(map);
			}else{
				deviceService.updateLibPosition(map);
			}
			
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 删除图书馆的位置信息
	 * @param request
	 * by liuwei 
	 */
	@RequestMapping(value=("deleteLibraryPosition"))
	@ResponseBody
	public ResultEntity deleteLibraryPosition(HttpServletRequest request){
		ResultEntity result= new ResultEntity();
		try {
			String req = request.getParameter("req");
			
			deviceService.deleteLibraryPosition(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 保存文件上传更新信息
	 * 
	 * @param request
	 *            by liuwei
	 */
	@RequestMapping(value = ("insertFileUploadFlag"))
	@ResponseBody
	public ResultEntity insertFlagUploadFlag(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);

			int count = deviceService.queryFileUploadFlag(map);
			if (count == 0) {
				deviceService.saveFileUploadFlag(map);
			} else {
				deviceService.updateFileUploadFlag(map);
			}

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 检测Ncip文件上传是否有更新
	 * 
	 * @param request
	 *            by liuwei
	 */
	@RequestMapping(value = ("checkFileUploadFlag"))
	@ResponseBody
	public ResultEntityF<List<SyncConfigEntity>> checkFlagUploadFlag(HttpServletRequest request) {
		ResultEntityF<List<SyncConfigEntity>> result = new ResultEntityF<List<SyncConfigEntity>>();
		List<SyncConfigEntity> changes = deviceService.SelSyncConfig();
		if (changes != null) {
			result.setResult(changes);
			result.setState(true);
		}
		return result;
	}
	
	@RequestMapping("selectDeviceCountByLibraryIdx")
	@ResponseBody
	public ResultEntity selectDeviceCountByLibraryIdx(String req){
		return deviceService.selectDeviceCountByLibraryIdx(req);
	}
	
	/**
	 * 获取设备部件监控状态错误码
	 */
	@RequestMapping(value = ("queryDevStatusCode"))
	@ResponseBody
	public ResultEntity queryDevStatusCode(){
		return deviceService.queryDevStatusCode();
	}
}
