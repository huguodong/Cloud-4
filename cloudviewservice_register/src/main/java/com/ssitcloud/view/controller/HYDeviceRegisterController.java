package com.ssitcloud.view.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.devregister.entity.DeviceRegisterParam;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.common.util.NetworkUtil;
import com.ssitcloud.view.service.DeviceRegisteService;
@Controller
@RequestMapping("/hydevice")
public class HYDeviceRegisterController extends BasicController{
	@Resource
	private DeviceRegisteService deviceRegisteService;
	
	@Value("${hhtest_lib_id}")
	private String HHTEST_LIB_ID;
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping("/hydeviceRegisterOpen")
	@ResponseBody
	public ResultEntity hydeviceRegisterOpen(HttpServletRequest request, String req) {
		DeviceRegisterParam registerParam= null;
		request.setAttribute("registerType", "TXT");
		try{
			registerParam = JsonUtils.fromJson(req, DeviceRegisterParam.class);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		if(registerParam==null) return new ResultEntity();
		return hydeviceRegister(request,registerParam);
	}
	
	
	/**
	 * 海洋设备注册
	 * 
	 * <p>
	 * 2018年1月9日 下午14:22:00
	 * <p>
	 * create by liuwei
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping("/hydeviceRegister")
	@ResponseBody
	public ResultEntity hydeviceRegister(HttpServletRequest request, DeviceRegisterParam registerParam) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String deviceTypeDesc = request.getParameter("deviceTypeDesc");
			String registerType = registerParam.getRegisterType();//request.getParameter("registerType");
			//String device_code = registerParam.getDeviceCode();
			
			
			/*if (StringUtils.isBlank(device_code)) {
				resultEntity.setValue(false, "设备特征码不能为空！");
				return resultEntity;
			}*/

			// #24小时自助借还-SS，#借还书机-SCH，#办证机-REG，#还书机-BDR，#馆员工作站-STA，#点检车-STA-C，#安全门-POR，#标签加工，STA-V
			String deviceUrl = "";// 设备类型id
			String deviceType = deviceTypeDesc;

			String req = "{\"meta_devicetype_idx\":\"\",\"device_type\":\"" + deviceType + "\"}";
			MetadataDeviceTypeEntity typeEntity = deviceRegisteService.queryDeviceTypeByName(req);
			if (typeEntity != null) {
				// 设备类型id
				String meta_devicetype_idx = typeEntity.getMeta_devicetype_idx().toString();
				String json = "{\"device_type_idx\":\"" + meta_devicetype_idx + "\"}";
				DeviceDisplayConfig entity = deviceRegisteService.getDisplayByTypeId(json);
				if (entity != null) {
					deviceUrl = entity.getDisplay_type_url();
				}
			}
			String device_id = "";
			String library_id = "";
			
			// 获取设备请求的ip、端口
			String ip = NetworkUtil.getIpAddress(request);
			String port = String.valueOf(request.getRemotePort());

			// 获取操作员的idx
			Operator operator = getCurrentUser();
			if (operator == null || operator.getOperator_idx() == null || "".equals(operator.getOperator_idx())) {
				if(StringUtils.isEmpty(registerType)){
					resultEntity.setValue(false, "获取操作员信息失败，请尝试重新登陆！");
					return resultEntity;
				}
			}

			registerParam.setIp(ip);
			registerParam.setPort(port);
			if(StringUtils.isEmpty(registerType)){
				registerParam.setAdmin_idx(operator.getOperator_idx());// 操作员的idx
			}else{
				registerParam.setAdmin_idx("1");//操作员的idx
			}
			// registerParam.setAdmin_idx("1");//操作员的idx
			// 根据图书馆id验证图书馆信息
			String libid = registerParam.getLibId();
			if (StringUtils.isBlank(libid)) {
				resultEntity.setValue(false, "请输入图书馆id");
				return resultEntity;
			}
			String libinfo = "{\"lib_id\":\"" + libid + "\"}";
			String libresp = deviceRegisteService.selLibraryByIdxOrId(libinfo);
			String library_idx = "";
			if (libresp != null) {
				ResultEntity libEntity = JsonUtils.fromJson(libresp, ResultEntity.class);
				if (libEntity.getState()) {
					if (libEntity.getResult() instanceof Map) {
						Map<String, Object> libMap = (Map<String, Object>) libEntity.getResult();
						library_idx = libMap.get("library_idx").toString();
					}
				} else {
					resultEntity.setValue(false, libEntity.getMessage());
					return resultEntity;
				}
			} else {// 如果没有返回，再发一次
				libresp = deviceRegisteService.selLibraryByIdxOrId(libinfo);
				if (libresp != null) {
					ResultEntity libEntity = JsonUtils.fromJson(libresp, ResultEntity.class);
					if (libEntity.getState()) {
						if (libEntity.getResult() instanceof Map) {
							Map<String, Object> libMap = (Map<String, Object>) libEntity.getResult();
							library_idx = libMap.get("library_idx").toString();
						}
					} else {
						resultEntity.setValue(false, libEntity.getMessage());
						return resultEntity;
					}
				} else {
					resultEntity.setValue(false, "查询图书馆信息连接失败，请重试！");
					return resultEntity;
				}
			}
			if (StringUtils.isBlank(library_idx) || library_idx.equals("null")) {
				resultEntity.setValue(false, "图书馆id不存在，请检查！");
				return resultEntity;
			}
			registerParam.setLibrary_idx(library_idx);//
			// 获取图书馆library_idx------end

			// 获取图书馆idx之后，从鉴权表中获取其最大设备注册数，然后比较库中已有的设备。
			String libidx = "{\"library_idx\":\"" + library_idx + "\"}";
			String libidxResp = deviceRegisteService.selDeviceUserByLibraryIdx(libidx);
			if (libidxResp != null) {
				ResultEntity regDevice = JsonUtils.fromJson(libidxResp, ResultEntity.class);
				if (regDevice.getState()) {
					if (regDevice.getResult() instanceof Map) {
						Map<String, Object> map = (Map<String, Object>) regDevice.getResult();
						if (map.get("libraryTemp") != null) {
							Map<String, Object> tempMap = (Map<String, Object>) map.get("libraryTemp");
							List<String> list = null;
							if (map.get("deviceIdList") != null && !map.get("deviceIdList").toString().equals("")) {
								list = (List<String>) map.get("deviceIdList");
							}
							int maxDeviceCount = Integer.valueOf(tempMap.get("max_device_count").toString());
							// 如果该图书馆的设备用户不为空， 且设备用户大于最大设备数的时候才去查，设备库中的设备有多少
							if (list != null && list.size() > maxDeviceCount) {
								String deviceresp = deviceRegisteService.selDeviceCountByIds(map.get("deviceIdList").toString());
								if (deviceresp != null) {
									ResultEntity devResp = JsonUtils.fromJson(deviceresp, ResultEntity.class);
									String result = JsonUtils.toJson(devResp.getResult());
									if (Integer.valueOf(result) >= maxDeviceCount) {
										resultEntity.setValue(false, "max");
										return resultEntity;
									}
								} else {
									resultEntity.setValue(false, "无法获取该图书馆已注册设备信息，请稍后再试！");
									return resultEntity;
								}
							}
						}

					}
				} else {
					resultEntity.setValue(false, regDevice.getMessage());
					return resultEntity;
				}
			}
			// 验证各类数据合法性
			// 检查设备id

			device_id = registerParam.getDeviceId();
			if (StringUtils.isBlank(device_id)) {
				resultEntity.setValue(false, "设备id不能为空！");
				return resultEntity;
			}

			String devInfo = "{\"device_id\":\"" + device_id + "\"}";
			String devResp = deviceRegisteService.isExistDeviceWithIdOrIdx(devInfo);
			if (devResp != null) {
				Object ret = JsonUtils.fromJson(devResp, ResultEntity.class).getResult();
				if (ret instanceof String) {
					if (ret.equals("1")) {
						resultEntity.setValue(false, "设备ID:" + device_id + "已经存在，请检查！");
						return resultEntity;
					}
				}
			} else {
				devResp = deviceRegisteService.isExistDeviceWithIdOrIdx(devInfo);
				if (devResp != null) {
					Object ret = JsonUtils.fromJson(devResp, ResultEntity.class).getResult();
					if (ret instanceof String) {
						if (ret.equals("1")) {
							resultEntity.setValue(false, "设备ID:" + device_id + "已经存在，请检查！");
							return resultEntity;
						}
					}
				} else {
					resultEntity.setValue(false, "校验设备id时连接失败，请重试！");
					return resultEntity;
				}
			}
			// 检查设备id end

			// 检查设备类型
			if (StringUtils.isBlank(registerParam.getDeviceType())) {
				resultEntity.setValue(false, "请选择设备类型！");
				return resultEntity;
			}
			// 检查硬件与逻辑模板
			/*if (StringUtils.isBlank(registerParam.getExtTempId())) {
				resultEntity.setValue(false, "请选择硬件与逻辑配置！");
				return resultEntity;
			}*/
			// 检查运行配置模板
			/*if (StringUtils.isBlank(registerParam.getRunTempId())) {
				resultEntity.setValue(false, "请选择运行配置模板！");
				return resultEntity;
			}*/
			// 检查设备监控配置
			if (StringUtils.isBlank(registerParam.getMonitorTempId())) {
				resultEntity.setValue(false, "请选择设备监控配置！");
				return resultEntity;
			}
			// 检查数据同步配置
			if (StringUtils.isBlank(registerParam.getDbSyncTempId())) {
				resultEntity.setValue(false, "请选择数据同步配置！");
				return resultEntity;
			}

			// //检测自定义模板数据的合法性
			//String extJson = registerParam.getExtTempSubmit();
			//String runJson = registerParam.getRunTempSubmit();

			// if(!StringUtils.isBlank(extJson)){
			// Map<String, Object> map1 = JsonUtils.fromJson(extJson, Map.class);
			// if(map1.get("extDetailList") instanceof List){
			// List<Map<String, Object>> list = (List<Map<String, Object>>) map1.get("extDetailList");
			// for (Map<String, Object> map : list) {
			// System.out.println(map.get("ext_comm_conf"));
			// System.out.println(map.get("ext_extend_conf"));
			// }
			// }
			// }

			// if(!StringUtils.isBlank(runJson)){
			// Map<String, Object> map2 = JsonUtils.fromJson(runJson, Map.class);
			// if(map2.get("runDetailList") instanceof List){
			// List<Map<String, Object>> list = (List<Map<String, Object>>) map2.get("runDetailList");
			// for (Map<String, Object> map : list) {
			// System.out.println(map.get("run_conf_type"));
			// System.out.println(map.get("run_conf_value"));
			// }
			// }
			// }

			// 验证通过之后，发送新增设备指令
			String response = deviceRegisteService.hydeviceRegister(registerParam);
			// ResultEntity r = new ResultEntity();
			// r.setState(true);
			// r.setMessage("success");
			// r.setResult("");
			// String response = JsonUtils.toJson(r);
			if (response == null) {
				resultEntity.setValue(false, "连接失败，请重试");
			} else {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
				if (resultEntity.getState() && resultEntity.getMessage().equals("success")) {
					// 注册成功之后
					library_id = registerParam.getLibId();
					device_id = registerParam.getDeviceId();

					//String url = "";
					
					// String url = PropertyPlaceholder.getProperty(type);
					
					// String url = "?device_id="+device_id+"&library_id="+library_id;
					//String type = deviceType;

					
					//if (type.equals("SSL")) {
						//deviceUrl = SSL + url;
					//} else if (type.equals("SCH")) {
						//deviceUrl = SCH + url;
					//} else if (type.equals("REG")) {
						//deviceUrl = REG + url;
					//} else if (type.equals("BDR")) {
						//deviceUrl = BDR + url;
					//} else if (type.equals("STA")) {
						//deviceUrl = STA + url;
					//} else if (type.equals("STA-C")) {
						//deviceUrl = STA_C + url;
					//} else if (type.equals("POR")) {
						//deviceUrl = POR + url;
					//} else if (type.equals("STA-V")) {
						//deviceUrl = STA_V + url;
					//}
					 

					/**
					 * 动态获取url
					 */
					// deviceUrl = PropertyPlaceholder.getProperty(type) + url;
					
					if (deviceUrl != null && !"".equals(deviceUrl)) {
						deviceUrl += "?lib=" + library_id + "&device=" + device_id + "&type=" + deviceType;
						System.out.println("跳转的url="+deviceUrl);
					}

					Map<String, Object> map = new HashMap<>();
					map.put("libraryId", library_id);
					map.put("deviceId", device_id);
					resultEntity.setResult(map);
					resultEntity.setRetMessage(deviceUrl);
				}
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		LogUtils.info("deviceUrl="+resultEntity.getRetMessage());
		return resultEntity;
	}
}
