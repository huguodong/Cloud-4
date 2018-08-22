package com.ssitcloud.view.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DbSyncTempParam;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.devmgmt.entity.ExtTempParam;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.devmgmt.entity.MonitorTempParam;
import com.ssitcloud.devmgmt.entity.RunTempParam;
import com.ssitcloud.devregister.entity.DeviceRegisterParam;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.common.util.NetworkUtil;
import com.ssitcloud.view.common.util.WebUtil;
import com.ssitcloud.view.service.DeviceRegisteService;

/**
 * <p>
 * 2016年4月25日 下午1:45:33
 * 
 * @author hjc
 */
@Controller
@RequestMapping("/device")
public class DeviceRegisterController extends BasicController{
	@Resource
	private DeviceRegisteService deviceRegisteService;
	
	@Value("${hhtest_lib_id}")
	private String HHTEST_LIB_ID;

	/*
	 * 
	 * //24小时自助借还
	 * 
	 * @Value("${SSL}") private String SSL;
	 * 
	 * //借还书机
	 * 
	 * @Value("${SCH}") private String SCH;
	 * 
	 * //办证机
	 * 
	 * @Value("${REG}") private String REG;
	 * 
	 * //还书机
	 * 
	 * @Value("${BDR}") private String BDR;
	 * 
	 * //馆员工作站
	 * 
	 * @Value("${STA}") private String STA;
	 * 
	 * //点检车
	 * 
	 * @Value("${STA-C}") private String STA_C;
	 * 
	 * //安全门
	 * 
	 * @Value("${POR}") private String POR;
	 * 
	 * //标签加工
	 * 
	 * @Value("${STA-V}") private String STA_V;
	 */

	/**
	 * 设备连接，检查特征码
	 * 
	 * <p>
	 * 2016年4月27日 下午6:23:34
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @param deviceCode
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping("/connect")
	public ModelAndView deviceConnect(HttpServletRequest request,String deviceCode,boolean isNotSwitch) {
		ModelAndView mav = new ModelAndView();
		try {
			if (StringUtils.isBlank(deviceCode)) {
				mav.addObject("json", "deviceCode is null");
				mav.setViewName("redirect:/device/connect/error");
				return mav;
			} else {
				String device_idx = "";
				String device_id = "";
				String device_ip = "";
				String library_id = "";
				String library_name = "";

				// 判断deviceCode，是否存在库中
				String response = deviceRegisteService.queryDeviceByDeviceCode(deviceCode);
				if (response != null) {
					ResultEntity resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
					if (resultEntity.getState()) {
						String message = resultEntity.getMessage();
						//JSONObject obj = JSONObject.fromObject(resultEntity.getResult());
						//DeviceTempParam device = (DeviceTempParam)JSONObject.toBean(obj, DeviceTempParam.class);
						// 如果存在，根据类型进行跳转
						if (message.equals("1")) {
							Map<String, Object> param = (Map<String, Object>) resultEntity.getResult();
							device_idx = param.get("device_idx").toString();
							String type = param.get("device_type").toString();
							device_id = param.get("device_id").toString();
							List<String> list=new ArrayList<String>();
							list.add(device_id);
							String ipEntity = deviceRegisteService.queryDeviceIps(JSONArray.fromObject(list).toString());
							if(ipEntity!=null){
								device_ip = ipEntity;
							}
							// 查询图书馆id
							String libIdx = param.get("library_idx").toString();
							String libinfo = "{\"library_idx\":\"" + libIdx + "\"}";
							String libresp = deviceRegisteService.selLibraryByIdxOrId(libinfo);
							if (libresp != null) {
								ResultEntity libEntity = JsonUtils.fromJson(libresp, ResultEntity.class);
								if (libEntity.getState()) {
									if (libEntity.getResult() instanceof Map) {
										Map<String, Object> libMap = (Map<String, Object>) libEntity.getResult();
										library_id = libMap.get("lib_id").toString();
										library_name = libMap.get("lib_name").toString();
									}
								} else {
									// 未知原因错误
									mav.addObject("json", "sorry,can not find the library_id");
									mav.setViewName("redirect:/device/connect/error");
								}
							} else {
								// 未知原因错误
								mav.addObject("json", "sorry,can not find the library_id");
								mav.setViewName("redirect:/device/connect/error");
							}
							//判断是否是新出厂设备，如果是跳转到切换设备页面
							if (HHTEST_LIB_ID == null) HHTEST_LIB_ID = "HHTEST";
							//查询是否有主馆id为HHTEST
							boolean isHHMaster = deviceRegisteService.isHHMaster(libinfo);
							//判断图书馆id是HHTEST或者父馆是HHTEST
							if ((HHTEST_LIB_ID.equals(library_id) || isHHMaster) && !isNotSwitch) {
								/*Map<String, Object> modelMap = new HashMap<String, Object>();
								modelMap.put("device_idx", device_idx);
								modelMap.put("device_id", device_id);
								modelMap.put("device_ip", device_ip);
								modelMap.put("deviceCode", deviceCode);
								
								modelMap.put("old_library_idx", libIdx);
								modelMap.put("old_library_id", library_id);
								modelMap.put("old_library_name", library_name);
								mav.addAllObjects(modelMap);
								System.out.println("跳转的url=/page/libraryswitch.jsp");*/
								/**
								 * 如果遇到白屏情况有可能是libraryswitch.jsp没有授权
								 */
								//mav.setViewName("redirect:/page/libraryswitch.jsp");
								
								
								mav.addObject("device_idx", device_idx);
								mav.addObject("device_id", device_id);
								mav.addObject("device_ip", device_ip);
								mav.addObject("deviceCode", deviceCode);
								
								mav.addObject("old_library_idx", libIdx);
								mav.addObject("old_library_id", library_id);
								mav.addObject("old_library_name", library_name);
								
								mav.addObject("redirectType", 2);
								mav.setViewName("redirect:/device/login");
								
							}else{
								// 设备类型id
								String meta_devicetype_idx = param.get("meta_devicetype_idx").toString();
								/**
								 * 动态获取url
								 */
								String req = "{\"device_type_idx\":\"" + meta_devicetype_idx + "\"}";
								DeviceDisplayConfig entity = deviceRegisteService.getDisplayByTypeId(req);
								String url = null;
								if (entity != null) {
									url = entity.getDisplay_type_url();
								}
								// String url = PropertyPlaceholder.getProperty(type);
								if (url != null && !"".equals(url)) {
									url = url.replaceAll("#/", "");
									url += "?lib=" + library_id + "&device=" + device_id + "&type=" + type;
									System.out.println("跳转的url="+url);
									mav.setViewName("redirect:" + url);
								}else{
									mav.addObject("json", "redirect url is null");
									mav.setViewName("redirect:/device/connect/error");
									return mav;
								}

								/*
								 * String url = ""; // String url = "?device_id="+device_id+"&library_id="+library_id;
								 * 
								 * if (type.equals("SSL")) { mav.setViewName("redirect:"+SSL+url);
								 * 
								 * }else if (type.equals("SCH")) { mav.setViewName("redirect:"+SCH+url);
								 * 
								 * }else if (type.equals("REG")) { mav.setViewName("redirect:"+REG+url);
								 * 
								 * }else if (type.equals("BDR")) { mav.setViewName("redirect:"+BDR+url);
								 * 
								 * }else if (type.equals("STA")) { mav.setViewName("redirect:"+STA+url);
								 * 
								 * }else if (type.equals("STA-C")) { mav.setViewName("redirect:"+STA_C+url);
								 * 
								 * }else if (type.equals("POR")) { mav.setViewName("redirect:"+POR+url);
								 * 
								 * }else if (type.equals("STA-V")) { mav.setViewName("redirect:"+STA_V+url); }
								 */
							}
							

						} else if (message.equals("0")) {
							mav.addObject("deviceCode", deviceCode);
							mav.addObject("redirectType", 1);
							mav.setViewName("redirect:/device/login");
						} else {
							// 未知原因错误
							mav.addObject("json", "sorry,something is wrong");
							mav.setViewName("redirect:/device/connect/error");
						}
					} else {
						mav.addObject("json", response);
						mav.setViewName("redirect:/device/connect/error");
						return mav;
					}
				} else {
					mav.addObject("json", "connect time out");
					mav.setViewName("redirect:/device/connect/error");
					return mav;
				}
			}
		} catch (Exception e) {
			LogUtils.error("设备连接失败", e);
			mav.addObject("json", "sorry,something is wrong");
			mav.setViewName("redirect:/device/connect/error");
			return mav;
		}
		return mav;
	}

	/**
	 * 设备连接错误提示页面
	 * 
	 * <p>
	 * 2016年4月28日 下午2:30:39
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/connect/error")
	public String connectError(HttpServletRequest request, HttpServletResponse response) {
		String requeString = request.getParameter("json");
		return requeString;
	}

	/**
	 * 跳转到登陆界面
	 * 
	 * <p>
	 * 2016年4月28日 下午3:30:42
	 * <p>
	 * create by hjc
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String gotoLogin(String device_idx,String device_id,String device_ip,String deviceCode,String old_library_idx,String old_library_id,String old_library_name,String redirectType,HttpServletRequest request) {
		request.setAttribute("device_idx", device_idx);
		request.setAttribute("device_id", device_id);
		request.setAttribute("device_ip", device_ip);
		request.setAttribute("deviceCode", deviceCode);
		request.setAttribute("old_library_idx", old_library_idx);
		request.setAttribute("old_library_id", old_library_id);
		request.setAttribute("old_library_name", old_library_name);
		request.setAttribute("redirectType", redirectType);
		return "/index";
	}

	/**
	 * 跳转到设备注册页面
	 * 
	 * <p>
	 * 2016年4月28日 下午3:48:50
	 * <p>
	 * create by hjc
	 * 
	 * @return
	 */
	@RequestMapping("/register")
	public String gotoRegister(String deviceCode, HttpServletRequest request) {
		request.setAttribute("deviceCode", deviceCode);
		//判断一下，如果没有登录的话，跳转到connect链接去判断
		String url = request.getRequestURL().toString()+"?";
		for (Entry<String, String[]> map : request.getParameterMap().entrySet()) {
			url += map.getKey()+"="+request.getParameter(map.getKey())+"&";
		}
		url = url.replace("/register", "/connect");
		try {
			Operator operator = getCurrentUser();
			if (operator==null) {
				return "redirect:"+url;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/device/connect/error?json="+e.getMessage();
		}
		return "/page/deviceregister";
	}

	/**
	 * 跳转到设备注册页面
	 * 
	 * <p>
	 * 2018年1月8日 下午18:56:45
	 * <p>
	 * create by liuwei
	 * 
	 * @return
	 */
	@RequestMapping("/hyregister")
	public String gotoHYDevRegister(HttpServletRequest request) {
		//判断一下，如果没有登录的话，跳转到connect链接去判断
		String url = request.getRequestURL().toString()+"?";
		for (Entry<String, String[]> map : request.getParameterMap().entrySet()) {
			url += map.getKey()+"="+request.getParameter(map.getKey())+"&";
		}
		url = url.replace("/register", "/connect");
		try {
			Operator operator = getCurrentUser();
			if (operator==null) {
				return "redirect:"+url;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/device/connect/error?json="+e.getMessage();
		}
		return "/page/hydeviceregister";
	}
	
	/**
	 * 获取设备的类型
	 * 
	 * <p>
	 * 2016年4月25日 下午2:42:56
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeviceTypes")
	@ResponseBody
	public ResultEntity getDeviceTypes(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = deviceRegisteService.SelAllMetadataDeviceType("");
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 查询图书馆的libname
	 * 
	 * <p>
	 * 2016年6月2日 下午3:57:30
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLibName")
	@ResponseBody
	public ResultEntity getLibName(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json)) {
				String libinfo = "{\"lib_id\":\"" + json + "\"}";
				String libresp = deviceRegisteService.selLibraryByIdxOrId(libinfo);
				if (libresp != null) {
					ResultEntity libEntity = JsonUtils.fromJson(libresp, ResultEntity.class);
					if (libEntity.getState()) {
						if (libEntity.getResult() instanceof Map) {
							Map<String, Object> libMap = (Map<String, Object>) libEntity.getResult();
							resultEntity.setValue(true, libMap.get("lib_id").toString(), libMap.get("library_idx").toString(), libMap.get("lib_name"));
						} else if (resultEntity.getResult() == null) {
							resultEntity.setValue(true, "", "", "无此图书馆");
						}
					} else {
						resultEntity.setValue(false, libEntity.getMessage());
						return resultEntity;
					}
				} else {
					resultEntity.setValue(false, "", "", "获取名称超时");
					return resultEntity;
				}
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 获取所有的ext模板数据
	 * 
	 * <p>
	 * 2016年4月25日 下午6:42:31
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllExtTempList")
	@ResponseBody
	public ResultEntity getAllExtTempList(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isBlank(json)) {
				String id = json;
				json = "{\"device_type\":\"" + id + "\"}";
			} else {
				json = "";
			}
			String response = deviceRegisteService.getAllExtTempList(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
				List<ExtTempParam> extConfList = new ArrayList<>();
				if (resultEntity.getState()) {
					String res = JsonUtils.toJson(resultEntity.getResult());
					extConfList = JsonUtils.fromJson(res, new TypeReference<List<ExtTempParam>>() {
					});
				}
				resultEntity.setResult(extConfList);
			} else {
				resultEntity.setValue(false, "连接失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 获取所有的运行配置模板信息
	 * 
	 * <p>
	 * 2016年4月26日 下午5:34:12
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/getAllRunTempList")
	@ResponseBody
	public ResultEntity getAllRunTempList(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isBlank(json)) {
				String id = json;
				json = "{\"device_type\":\"" + id + "\"}";
			} else {
				json = "";
			}
			String response = deviceRegisteService.getAllRunTempList(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
				List<RunTempParam> runTempParams = new ArrayList<>();
				if (resultEntity.getState()) {
					String res = JsonUtils.toJson(resultEntity.getResult());
					runTempParams = JsonUtils.fromJson(res, new TypeReference<List<RunTempParam>>() {
					});
				}
				resultEntity.setResult(runTempParams);
			} else {
				resultEntity.setValue(false, "连接失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 获取所有的运行配置模板信息
	 * 
	 * <p>
	 * 2016年4月26日 下午5:34:12
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/getAllMonitorTempList")
	@ResponseBody
	public ResultEntity getAllMonitorTempList(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isBlank(json)) {
				String id = json;
				json = "{\"device_type\":\"" + id + "\"}";
			} else {
				json = "";
			}
			String response = deviceRegisteService.getAllMonitorTempList(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
				List<MonitorTempParam> monitorTempParams = new ArrayList<>();
				if (resultEntity.getState()) {
					String res = JsonUtils.toJson(resultEntity.getResult());
					monitorTempParams = JsonUtils.fromJson(res, new TypeReference<List<MonitorTempParam>>() {
					});
				}
				resultEntity.setResult(monitorTempParams);
			} else {
				resultEntity.setValue(false, "连接失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 获取所有的运行配置模板信息
	 * 
	 * <p>
	 * 2016年4月26日 下午5:34:12
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/getAllDbSyncTempList")
	@ResponseBody
	public ResultEntity getAllDbSyncTempList(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isBlank(json)) {
				String id = json;
				json = "{\"device_type\":\"" + id + "\"}";
			} else {
				json = "";
			}
			String response = deviceRegisteService.getAllDbSyncTempList(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
				List<DbSyncTempParam> dbSyncTempParams = new ArrayList<>();
				if (resultEntity.getState()) {
					String res = JsonUtils.toJson(resultEntity.getResult());
					dbSyncTempParams = JsonUtils.fromJson(res, new TypeReference<List<DbSyncTempParam>>() {
					});
				}
				resultEntity.setResult(dbSyncTempParams);
			} else {
				resultEntity.setValue(false, "连接失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping("/deviceRegisterOpen")
	@ResponseBody
	public ResultEntity deviceRegisterOpen(HttpServletRequest request, String req) {
		DeviceRegisterParam registerParam= null;
		try{
			registerParam = JsonUtils.fromJson(req, DeviceRegisterParam.class);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		if(registerParam==null) return new ResultEntity();
		return deviceRegister(request,registerParam);
	}
	/**
	 * 设备注册
	 * 
	 * <p>
	 * 2016年4月27日 下午5:46:00
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping("/deviceRegister")
	@ResponseBody
	public ResultEntity deviceRegister(HttpServletRequest request, DeviceRegisterParam registerParam) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String deviceTypeDesc = request.getParameter("deviceTypeDesc");
			String registerType = request.getParameter("registerType");
			String device_code = registerParam.getDeviceCode();
			
			
			if (StringUtils.isBlank(device_code)) {
				resultEntity.setValue(false, "设备特征码不能为空！");
				return resultEntity;
			}

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
			// 判断deviceCode，是否存在库中,如果存在，查询device_id以及library_id
			String coderesp = deviceRegisteService.queryDeviceByDeviceCode(device_code);
			if (coderesp != null) {
				ResultEntity codeResp = JsonUtils.fromJson(coderesp, ResultEntity.class);
				if (codeResp.getState()) {
					String message = codeResp.getMessage();
					// 如果存在，根据类型进行跳转
					if (message.equals("1")) {
						Map<String, Object> param = (Map<String, Object>) codeResp.getResult();
						String type = param.get("device_type").toString();
						device_id = param.get("device_id").toString();
						// 查询图书馆id
						String libIdx = param.get("library_idx").toString();
						String libinfo = "{\"library_idx\":\"" + libIdx + "\"}";
						String libresp = deviceRegisteService.selLibraryByIdxOrId(libinfo);
						if (libresp != null) {
							ResultEntity libEntity = JsonUtils.fromJson(libresp, ResultEntity.class);
							if (libEntity.getState()) {
								if (libEntity.getResult() instanceof Map) {
									Map<String, Object> libMap = (Map<String, Object>) libEntity.getResult();
									library_id = libMap.get("lib_id").toString();
								}
							}
						}

						/*
						 * // String url = "?device_id="+device_id+"&library_id="+library_id; String url = "";
						 * 
						 * if (type.equals("SSL")) { deviceUrl = SSL + url; }else if (type.equals("SCH")) { deviceUrl = SCH + url; }else if (type.equals("REG")) { deviceUrl = REG + url; }else if
						 * (type.equals("BDR")) { deviceUrl = BDR + url; }else if (type.equals("STA")) { deviceUrl = STA + url; }else if (type.equals("STA-C")) { deviceUrl = STA_C + url; }else if
						 * (type.equals("POR")) { deviceUrl = POR + url; }else if (type.equals("STA-V")) { deviceUrl = STA_V + url; }
						 */
						// 设备类型id
						String meta_devicetype_idx = param.get("meta_devicetype_idx").toString();
						/**
						 * 动态获取url
						 */
						String json = "{\"device_type_idx\":\"" + meta_devicetype_idx + "\"}";
						DeviceDisplayConfig entity = deviceRegisteService.getDisplayByTypeId(json);
						if (entity != null) {
							deviceUrl = entity.getDisplay_type_url();
						}
						/**
						 * 动态获取url
						 */
						// deviceUrl = PropertyPlaceholder.getProperty(type);
						if (deviceUrl != null && !"".equals(deviceUrl)) {
							deviceUrl += "?lib=" + library_id + "&device=" + device_id + "&type=" + deviceType;
							System.out.println("跳转的url="+deviceUrl);
						}
						resultEntity.setValue(false, "code", deviceUrl, "");
						return resultEntity;
					}
				}
			}

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
			if (StringUtils.isBlank(registerParam.getExtTempId())) {
				resultEntity.setValue(false, "请选择硬件与逻辑配置！");
				return resultEntity;
			}
			// 检查运行配置模板
			if (StringUtils.isBlank(registerParam.getRunTempId())) {
				resultEntity.setValue(false, "请选择运行配置模板！");
				return resultEntity;
			}
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
			// String extJson = registerParam.getExtTempSubmit();
			// String runJson = registerParam.getRunTempSubmit();

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
			String response = deviceRegisteService.deviceRegister(registerParam);
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

	/**
	 * 获取逻辑外设部件
	 * 
	 * <p>
	 * 2016年5月7日 下午2:34:25
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllLogicObj")
	@ResponseBody
	public ResultEntity getAllLogicObj(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json)) {
				json = "";
			}
			String response = deviceRegisteService.SelectMetadataLogicObj(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			} else {
				resultEntity.setValue(false, "连接服务器失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 获取所有的extmodel
	 * 
	 * <p>
	 * 2016年5月11日 上午9:34:25
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllExtModel")
	@ResponseBody
	public ResultEntity getAllExtModel(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json)) {
				json = "";
			}
			String response = deviceRegisteService.SelMetadataExtModel(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			} else {
				resultEntity.setValue(false, "连接服务器失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 获取所有的logic_obj和ext_model的元数据
	 * 
	 * <p>
	 * 2016年5月14日 下午2:16:15
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/getAllLogicObjAndExtModel")
	@ResponseBody
	public ResultEntity getAllLogicObjAndExtModel(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json)) {
				json = "";
			}
			String response = deviceRegisteService.selAllExtModelAndLogicObj(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			} else {
				resultEntity.setValue(false, "连接服务器失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 获取ACS模板数据
	 * 
	 * <p>
	 * 2016年6月30日 上午10:07:03
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAscTempList")
	@ResponseBody
	public ResultEntity getAscTempList(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
			}
			String response = deviceRegisteService.getAscTempList(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			} else {
				resultEntity.setValue(false, "连接失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 将图书馆的设备转移到另一个图书馆
	 *
	 * <p>2016年9月21日 上午10:30:31 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/transferToNewLib")
	@ResponseBody
	public ResultEntity transferToNewLib(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			Operator operator = getCurrentUser();
			if (operator!=null ) {
				if(Operator.SSITCLOUD_ADMIN.equals(operator.getOperator_type()) 
						|| Operator.SSITCLOUD_MANAGER.equals(operator.getOperator_type())) {
					
					Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
					map.put("operator_idx", operator.getOperator_idx());
					map.put("client_ip", WebUtil.getIpAddr(request));
					map.put("client_port", request.getRemotePort());
					
					result = deviceRegisteService.transferToNewLib(JsonUtils.toJson(map));
				}else{
					result.setValue(false, "权限不足", "", "");
					return result;
				}
			}else{
				result.setValue(false, "权限不足", "", "");
				return result;
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	
	/**
	 * 测试设备注册成功之后系统初始化
	 * 
	 * <p>
	 * 2016年6月30日 上午10:04:07
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/test")
	public Map<String, Object> testcontroller(HttpServletRequest request) {
		/*
		 * "servicetype":"sys", "target":"sys_config", "operation":"sys_init_set_id", "data":"", "result":"0"
		 */
		System.out.println(JsonUtils.toJson(request.getParameterMap()));
		System.out.println(request.getParameter("data"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("servicetype", "sys");
		map.put("target", "sys_config");
		map.put("operation", "sys_init_set_id");
		map.put("data", "");
		map.put("result", "0");
		return map;
	}
	
	/**
	 * 查询所有的地区信息
	 *
	 * <p>2017年9月7日 上午10:18:14 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllRegion")
	@ResponseBody
	public ResultEntity queryAllRegion(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		resultEntity = deviceRegisteService.queryAllRegion("");
		return resultEntity;
	}

}
