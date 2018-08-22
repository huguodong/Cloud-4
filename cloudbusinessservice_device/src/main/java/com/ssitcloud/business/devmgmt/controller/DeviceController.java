package com.ssitcloud.business.devmgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceGroupService;
import com.ssitcloud.business.devmgmt.service.DeviceService;
import com.ssitcloud.business.devregister.service.DeviceRegisterService;
import com.ssitcloud.business.devregister.service.FtpBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;

@Controller
@RequestMapping(value="device")
public class DeviceController {
	
	@Resource
	private DeviceService deviceService;
	@Resource
	private DeviceGroupService deviceGroupService;
	
	@Resource
	private DeviceRegisterService deviceRegisterService;
	
	@Resource
	private FtpBusinessService ftpBusinessService;
	
	
	@RequestMapping(value="SelectDeviceByPage")
	@ResponseBody
	public ResultEntity queryDeviceByPage(HttpServletRequest request,String req){
		return deviceService.queryDeviceByPage(req);
	}
	
	/**
	 * 带参数查询设备（机器监控页面）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceByParam"})
	@ResponseBody
	public ResultEntity queryDeviceByParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.queryDeviceByParam(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 带参数查询服务设备（机器服务监控页面）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryServiceDeviceByParam"})
	@ResponseBody
	public ResultEntity queryServiceDeviceByParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceService.queryServiceDeviceByParam(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"QueryDevIdxByDevId"})
	@ResponseBody
	public ResultEntity QueryDevIdxByDevId (HttpServletRequest request,String req){
		return deviceService.QueryDevIdxByDevId(req);
	}
	
	@RequestMapping(value= {"SelectDevice"})
	@ResponseBody
	public ResultEntity SelectDevice(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		Map<String, String> map = new HashMap<>();
		map.put("json", request.getParameter("json"));
		map.put("page", request.getParameter("page"));
		String resps = deviceService.SelectDevice(map);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		return result ;
	}
	
	@RequestMapping(value= {"DeleteDevice"})
	@ResponseBody
	public ResultEntity DeleteDevice(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		String resps = deviceService.DeleteDevice(json);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		if(result.getState()){
			ftpBusinessService.delFtpUser(json);
		}
		return result ;
	}
	


	@RequestMapping(value= {"SelectInfo"})
	@ResponseBody
	public ResultEntity SelectInfo(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceService.SelectInfo(json);
			result  = JsonUtils.fromJson(resps, ResultEntity.class);
			ObjectMapper mapper = new ObjectMapper();
			DeviceEntity device = mapper.readValue(JsonUtils.toJson(result.getResult()), DeviceEntity[].class)[0];
			LibraryEntity libraryEntity = new LibraryEntity();
			libraryEntity.setLibrary_idx(device.getLibrary_idx());
			String lib = deviceService.SelLibrary(JsonUtils.toJson(libraryEntity));
			ResultEntity libEntity = JsonUtils.fromJson(lib, ResultEntity.class);
			ObjectMapper mapper1 = new ObjectMapper();
			LibraryEntity  libidEntity  = mapper1.readValue(JsonUtils.toJson(libEntity.getResult()), LibraryEntity[].class)[0];
			device.setLib_id(libidEntity.getLib_id());
			device.setLib_name(libidEntity.getLib_name());
			result .setResult(device);	
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	@RequestMapping(value= {"UpdDevice"})
	@ResponseBody
	public ResultEntity UpdDevice(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.UpdDevice(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"UpdDeviceMgmtPage"})
	@ResponseBody
	public ResultEntity UpdDeviceMgmtPage(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.UpdDeviceMgmtPage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"UpdHyDeviceMgmtPage"})
	@ResponseBody
	public ResultEntity UpdHyDeviceMgmtPage(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.UpdHyDeviceMgmtPage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
		查询设备有没有自定义参数配置
	 * @param request
	 * @param req={"device_idx":deviceIdx,"configName":configName}
	 * @return
	 */
	@RequestMapping(value={"checkAllConfigDataByDevIdx"})
	@ResponseBody
	public ResultEntity checkAllConfigDataByDevIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.checkAllConfigDataByDevIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
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
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.compareMonitorConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 根据deviceIdx获取ACS logininfo数据
	 * 
	 * @param request
	 * @param req={deviceIdx:"..."}
	 * @return
	 */
	@RequestMapping(value={"loadAcsLogininfo"})
	@ResponseBody
	public ResultEntity loadAcsLogininfo(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.loadAcsLogininfo(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 根据 所有图书馆IDX获取所有的对应的device_id
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceIdbyLibIdx"})
	@ResponseBody
	public ResultEntity queryDeviceIdbyLibIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.queryDeviceIdbyLibIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
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
	 * 根据设备id查询设备ip
	 *
	 * <p>2016年9月21日 下午6:54:16 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDeviceIps")
	@ResponseBody
	public ResultEntity queryDeviceIps(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = deviceService.queryDeviceIps(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 将图书馆的设备转移到另一个图书馆
	 *
	 * <p>2016年9月21日 上午10:40:23 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/transferToNewLib")
	@ResponseBody
	public ResultEntity transferToNewLib(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			//library_id   deviceType  deviceId deviegroup
			System.out.println(req);
			if(StringUtils.hasText(req)){
				Map<String, Object> transMap = JsonUtils.fromJson(req, Map.class);
				
				//获取新图书馆idx之后，从鉴权表中获取其最大设备注册数，然后比较库中已有的设备。 --->start
				String library_idx = transMap.get("newLibidx").toString();
				Integer count = Integer.valueOf(transMap.get("count").toString());
				String libidx = "{\"library_idx\":\""+library_idx+"\"}";
				String libidxResp = deviceRegisterService.selDeviceUserByLibraryIdx(libidx);
				if (libidxResp!=null) {
					ResultEntity regDevice = JsonUtils.fromJson(libidxResp, ResultEntity.class);
					if(regDevice.getState()){
						if(regDevice.getResult() instanceof Map){
							Map<String, Object> map = (Map<String, Object>) regDevice.getResult();
							if (map.get("libraryTemp")!=null) {
								Map<String, Object> tempMap = (Map<String, Object>) map.get("libraryTemp");
								List<String> list = null;
								if (map.get("deviceIdList")!=null && !map.get("deviceIdList").toString().equals("")) {
									list = (List<String>) map.get("deviceIdList");
								}
								int maxDeviceCount = Integer.valueOf( tempMap.get("max_device_count").toString());//最大设备数
								
								if (count >= maxDeviceCount) {
									//超出最大数
									result.setState(false);
									result.setMessage("超过最大设备数");
									return result;
								}else if (list!=null && (list.size()+count)>maxDeviceCount) {
									//如果该图书馆的设备用户不为空，且原来的设备用户加上此次新增的数量比最大数量大的话，去查设备库，查看设备库中的设备有多少，  设备用户不一定等于设备库中的设备数
									
									String deviceresp = deviceRegisterService.selDeviceCountByIds(map.get("deviceIdList").toString());
									if (deviceresp!=null) {
										ResultEntity devResp = JsonUtils.fromJson(deviceresp, ResultEntity.class);
										String devResult = JsonUtils.toJson(devResp.getResult());
										if((Integer.valueOf(devResult)+count) >= maxDeviceCount){
											result.setState(false);
											result.setMessage("超过最大设备数");
											return result;
										}
									}else{
										result.setState(false);
										result.setMessage("无法获取该图书馆已注册设备信息，请稍后再试！");
										return result;
									}
								}
							}else{
								result.setState(false);
								result.setMessage("获取新馆的最大设备数失败");
								return result;
							}
						}else{
							result.setState(false);
							result.setMessage("获取新馆的最大设备数失败");
							return result;
						}
					}else{
						result.setState(false);
						result.setMessage(regDevice.getMessage());
						return result;
					}
				}else{
					result.setState(false);
					result.setMessage("获取新馆的最大设备数失败");
				}
				/////检测图书馆最大设备数完成，运行到此，证明没有超过最大设备数 ---> end
				
				//发送请求，先修改鉴权层的设备用户的所属馆，以及修改对应的设备ip --->start
				String authTrans = deviceService.authTransferToLibrary(req);
				if (!StringUtils.isEmpty(authTrans)) {
					ResultEntity authResultEntity = JsonUtils.fromJson(authTrans, ResultEntity.class);
					if (authResultEntity.getState()) {
						//发送请求，修改设备层的设备所属馆信息，并且删除设备的设备组关联 --->start
						
						System.out.println("发送请求，修改设备层的设备所属馆信息，并且删除设备的设备组关联 --->start");
						String devTrans = deviceService.devTransferToLibrary(req);
						System.out.println("device:"+devTrans);
						if(StringUtils.hasText(devTrans)){
							ResultEntity devResultEntity = JsonUtils.fromJson(devTrans, ResultEntity.class);
							if (devResultEntity.getState()) {
								result.setState(true);
								result.setMessage("success");
								transMap.put("result", true);
								//发送信息记录日志
								deviceService.devTransferToLibraryLog(JsonUtils.toJson(transMap));
								return result;
							}else{
								result.setState(false);
								result.setMessage("failed");
								transMap.put("result", false);
								//发送信息记录日志
								deviceService.devTransferToLibraryLog(JsonUtils.toJson(transMap));
								return result;
							}
						}else{
							result.setState(false);
							result.setMessage("修改设备层设备的馆信息失败，服务没有响应");
						}
						//发送请求，修改设备层的设备所属馆信息，并且删除设备的设备组关联 --->end
					}else{
						return authResultEntity;
					}
				}else{
					result.setState(false);
					result.setMessage("修改鉴权层设备用户的馆信息失败，服务没有响应");
				}
				
				//发送请求，先修改鉴权层的设备用户的所属馆，以及修改对应的设备ip --->end
				
			}else{
				result.setState(false);
				result.setMessage("参数不能为空");
				return result;
			}
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 通过指定条件查找设备
	 * 格式
	 * req = {
	 * 	"device_idx":XX,
	 * 	"device_model_idx":XX,
	 * 	"device_id":XX,
	 * 	"device_code":XX,
	 * 	"device_desc":XX,
	 * 	"device_name":XX,
	 * 	"library_idx":XX
	 * }
	 * author huanghuang
	 * 2017年3月17日 下午2:23:25
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value= {"selectDeviceByCondition"})
	@ResponseBody
	public ResultEntity selectDeviceByCondition(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			String resps = deviceService.SelectInfo(req);
			result  = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	

	/**
	 * 获取所有的地区信息
	 *
	 * <p>2017年9月7日 上午10:28:51 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllRegion")
	@ResponseBody
	public ResultEntity queryAllRegion(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			String response = deviceService.queryAllRegion(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 根据设备idx 查询设备的地点信息
	 *
	 * <p>2017年9月8日 下午3:43:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDeviceRegion")
	@ResponseBody
	public ResultEntity queryDeviceRegion(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.queryDeviceRegion(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * <p>根据图书馆位置信息
	 * <p>create by liuwei
	 * @param request
	 * @return 
	 */
	@RequestMapping("/GetLibPosition")
	@ResponseBody
	public ResultEntity GetLibPosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.GetLibPosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * <p>根据图书馆id来获取查询设备的位置信息
	 * <p>create by liuwei
	 * @param request
	 * @return 
	 */
	@RequestMapping("/GetDevicePosition")
	@ResponseBody
	public ResultEntity GetDevicePosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.GetDevicePosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 保存设备的地理位置
	 * create by liuwei 
	 * * @param request
	 * @return 
	 */
	@RequestMapping("/saveDevicePosition")
	@ResponseBody
	public ResultEntity saveDevicePosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.saveDevicePosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		}catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 保存设备的地理位置
	 * create by liuwei 
	 * * @param request
	 * @return 
	 */
	@RequestMapping("/deleteLibraryPosition")
	@ResponseBody
	public ResultEntity deleteLibraryPosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.deleteLibraryPosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		}catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * <p>根据图书馆id来获取查询设备的位置信息
	 * <p>create by liuwei
	 * @param request
	 * @return 
	 */
	@RequestMapping("/getLibPosition")
	@ResponseBody
	public ResultEntity getLibPosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.GetLibPosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 保存Ncip压缩文件更新时间到数据库 create by liuwei * @param request
	 * 
	 * @return
	 */
	@RequestMapping("/insertFileUploadFlag")
	@ResponseBody
	public ResultEntity insertFlagUploadFlag(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.insertFlagUploadFlag(json);
			if (response != null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			} else {
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
