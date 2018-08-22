package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceExtTempEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.entity.page.DeviceExtLogicPageEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.DeviceExtConfService;
import com.ssitcloud.service.DeviceService;
import com.ssitcloud.service.MetadataLogicObjService;
import com.ssitcloud.system.entity.DeviceExtConfigRemoteEntity;

@Controller
@RequestMapping(value={"extconf"})
public class DeviceExtConfController {

	@Resource
	private DeviceExtConfService deviceExtConfService;
	@Resource
	private DeviceConfigService deviceConfigService;
	@Resource
	private MetadataLogicObjService metadataLogicObjService;
	@Resource
	private DeviceService deviceService;
	
	/**
	 * 新增外设模板device_ext_template
	 * 格式
	 * {
	 * 		"device_tpl_ID":""
	 * 		"device_tpl_desc":""
	 * 		"device_type":""
	 * }
	 * @methodName: AddNewDeviceExtConfTemp
	 * @param req
	 * @param resp
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddNewDeviceExtTemp"})
	@ResponseBody
	public ResultEntity AddNewDeviceExtTemp(HttpServletRequest req,HttpServletResponse resp){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			DeviceExtTempEntity extTemp=JsonUtils.fromJson(json, DeviceExtTempEntity.class);
			int re=deviceExtConfService.insertDeviceExtTemp(extTemp);
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 更新外设模板
	 * json={
	 * 	"device_tpl_ID":""
	 * 	"device_tpl_desc":""
	 * 	"device_type":""
	 * }
	 */
	@RequestMapping(value={"UpdDeviceExtTemp"})
	@ResponseBody
	public ResultEntity UpdDeviceExtTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			DeviceExtTempEntity extTemp=JsonUtils.fromJson(json, DeviceExtTempEntity.class);
			int re=deviceExtConfService.updateDeviceExtTemp(extTemp);
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除外设模板 
	 * 传入参数id
	 * @methodName: DelDeviceExtConfTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DelDeviceExtTemp"})
	@ResponseBody
	public ResultEntity DelDeviceExtTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			int re=deviceExtConfService.deleteDeviceExtTemp(JsonUtils.fromJson(json, DeviceExtTempEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查询设备外设模板
	 * * json={
	 * 	"device_tpl_ID":""
	 * 	"device_tpl_desc":""
	 * 	"device_type":""
	 * }
	 * @methodName: SelDeviceExtTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelDeviceExtTemp"})
	@ResponseBody
	public ResultEntity SelDeviceExtTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceExtTempEntity> extTemps=deviceExtConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), DeviceExtTempEntity.class));
			result.setResult(extTemps);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	
	/**
	 * 新增外设硬件配置device_ext_config
	 * 格式
	 * {
	 * 		"library_idx":""
	 * 		"device_tpl_type":""
	 * 		"device_ext_id":""
	 * 		"ext_comm_conf":""
	 * 		"ext_extend_conf":""
	 * 		"logic_obj_idx":""
	 * 		"ext_model_idx":""
	 * 		"updatetime":""
	 * }
	 * @methodName:  
	 * @param req
	 * @param resp
	 * @author: hwl
	 * @returnType: ResultEntity
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"AddNewDeviceExtConfig"})
	@ResponseBody
	public ResultEntity AddNewDeviceExtConfig(HttpServletRequest req,HttpServletResponse resp){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			int re=0;
			//List<DeviceExtConfigEntity> list = JsonUtils.fromJson(json, new TypeReference<List<DeviceExtConfigEntity>>() {});
			List<Map<String, Object>> list=(List<Map<String,Object>>)JsonUtils.fromJson(json, List.class);
			if(CollectionUtils.isNotEmpty(list)){
				for(Map<String,Object> map:list){
					DeviceExtConfigEntity deviceExtConfig=new DeviceExtConfigEntity();
					if(map.get("library_idx")!=null){
						deviceExtConfig.setLibrary_idx(Integer.parseInt(map.get("library_idx").toString()));
					}
					if(map.get("device_tpl_type")!=null){
						deviceExtConfig.setDevice_tpl_type(Integer.parseInt(map.get("device_tpl_type").toString()));
					}
					if(map.get("device_ext_id")!=null){
						deviceExtConfig.setDevice_ext_id(Integer.parseInt(map.get("device_ext_id").toString()));
					}
					if(map.get("ext_comm_conf")!=null&&!map.get("ext_comm_conf").toString().equals("{}")){
						deviceExtConfig.setExt_comm_conf(JsonUtils.toJson(map.get("ext_comm_conf")));
					}
					if(map.get("ext_extend_conf")!=null&&!map.get("ext_extend_conf").toString().equals("{}")){
						deviceExtConfig.setExt_extend_conf(JsonUtils.toJson(map.get("ext_extend_conf")));
					}
					if(map.get("logic_obj_idx")!=null){
						deviceExtConfig.setLogic_obj_idx(Integer.parseInt(map.get("logic_obj_idx").toString()));
					}
					if(map.get("ext_model_idx")!=null){
						deviceExtConfig.setExt_model_idx(Integer.parseInt(map.get("ext_model_idx").toString()));
					}
					re+=deviceExtConfService.insertDeviceExtConfig(deviceExtConfig);
				}
			}
			/*for (DeviceExtConfigEntity deviceExtConfigEntity : list) {
				re+=deviceExtConfService.insertDeviceExtConfig(deviceExtConfigEntity);
			}*/
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			
			if(result.getState()){
				if(list != null && !list.isEmpty()){
					//下发数据到设备端
					Map<String, Object> map = list.get(0);
					DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
					
					if(map.get("library_idx")!=null){
						deviceConfigEntity.setLibrary_idx(Integer.parseInt(map.get("library_idx").toString()));
					}
					if(map.get("device_ext_id")!=null){
						deviceConfigEntity.setDevice_idx(Integer.parseInt(map.get("device_ext_id").toString()));
					} 
					if(map.get("device_tpl_type")!=null){
						int type = Integer.parseInt(map.get("device_tpl_type").toString());
						type = type == DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL ? 1: 0;
						deviceConfigEntity.setDevice_ext_tpl_flg(type);
					}
					
					if(map.get("ext_model_idx")!=null){
						deviceConfigEntity.setDevice_ext_tpl_idx(Integer.parseInt(map.get("ext_model_idx").toString()));
					}
					
					deviceService.pushMessage(deviceConfigEntity, deviceConfigEntity.getLibrary_idx(), deviceConfigEntity.getDevice_idx());
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新外设硬件配置
	 * 格式
	 * {
	 * 		"library_idx":""
	 * 		"device_tpl_type":""
	 * 		"device_ext_id":""
	 * 		"ext_comm_conf":""
	 * 		"ext_extend_conf":""
	 * 		"logic_obj_idx":""
	 * 		"ext_model_idx":""
	 * 		"updatetime":""
	 * }
	 * @param req
	 * @return
	 * @author: hwl
	 */
	@RequestMapping(value={"UpdDeviceExtConfig"})
	@ResponseBody
	public ResultEntity UpdDeviceExtConfig(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			DeviceExtConfigEntity extTemp=JsonUtils.fromJson(json, DeviceExtConfigEntity.class);
			int re=deviceExtConfService.updateDeviceExtConfig(extTemp);
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 
	 * @comment
	 * @param req
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"DelAndAddExtConfig"})
	@ResponseBody
	public ResultEntity DelAndAddExtConfig(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			
			String json=req.getParameter("json");
			
			List<Map<String,Object>> listMap=JsonUtils.fromJson(json,List.class);
			Map<String,Object> m=(Map<String,Object>)listMap.get(0);
		
			if(m.get("device_ext_id")==null || m.get("device_tpl_type")==null){
				return result;
			}
			
			Integer device_ext_id= Integer.parseInt(m.get("device_ext_id").toString());
			int library_idx = Integer.parseInt(m.get("library_idx").toString());
			Integer device_tpl_type= Integer.parseInt(m.get("device_tpl_type").toString());
			int ret = deviceExtConfService.deleteDeviceExtConfig(new DeviceExtConfigEntity(device_ext_id,device_tpl_type));
			int re = 0;
			for(Object o:listMap){
					Map<String,Object> em=(Map<String,Object>)o;
					DeviceExtConfigEntity dExtConfigEntity=new DeviceExtConfigEntity();
					if(em.get("device_ext_id")!=null){
						dExtConfigEntity.setDevice_ext_id(Integer.parseInt(em.get("device_ext_id").toString()));
					}
					if(em.get("device_tpl_type")!=null){
						dExtConfigEntity.setDevice_tpl_type(Integer.parseInt(em.get("device_tpl_type").toString()));
					}
					if(em.get("library_idx")!=null){
						dExtConfigEntity.setLibrary_idx(Integer.parseInt(em.get("library_idx").toString()));
					}
					if(em.get("ext_comm_conf")!=null&&!"{}".equals(em.get("ext_comm_conf").toString())){//map
						dExtConfigEntity.setExt_comm_conf(JsonUtils.toJson(em.get("ext_comm_conf")));
					}
					if(em.get("ext_extend_conf")!=null&&!"{}".equals(em.get("ext_extend_conf").toString())){//map
						dExtConfigEntity.setExt_extend_conf(JsonUtils.toJson(em.get("ext_extend_conf")));
					}
					if(em.get("logic_obj_idx")!=null){
						dExtConfigEntity.setLogic_obj_idx(Integer.parseInt(em.get("logic_obj_idx").toString()));
					}
					if(em.get("ext_model_idx")!=null){
						dExtConfigEntity.setExt_model_idx(Integer.parseInt(em.get("ext_model_idx").toString()));
					}
					re+=deviceExtConfService.insertDeviceExtConfig(dExtConfigEntity);
			}
			//需要更新 device_config flg
			DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
			deviceConfig.setDevice_idx(device_ext_id);
			DeviceConfigEntity queryDeviceConfig=deviceConfigService.queryDeviceConfigByDeviceIdx(deviceConfig);
			if(queryDeviceConfig==null){
				throw new RuntimeException("device_idx:"+device_ext_id+" 查询不到device_config信息,更新失败");
			}
			if(queryDeviceConfig!=null&&queryDeviceConfig.getDevice_ext_tpl_flg()==DeviceConfigEntity.IS_MODEL){
				//flg==模板 则需要更新为非模板
				deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
				int updNum=deviceConfigService.updateDeviceConfig(deviceConfig);
				if(updNum<=0){ 
					throw new RuntimeException("更新device_config表失败 device_ext_flg 为数据失败");
				}
			}
			
			result.setState(re==listMap.size()?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			//下发数据到设备端
			if(result.getState()){
				deviceConfig.setLibrary_idx(library_idx);
				deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
				deviceService.pushMessage(deviceConfig, library_idx, deviceConfig.getDevice_idx());
			}

		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除外设硬件配置
	 * 
	 * @methodName: DelDeviceExtConfig
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value={"DelDeviceExtConfig"})
	@ResponseBody
	public ResultEntity DelDeviceExtConfig(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			int re=deviceExtConfService.deleteDeviceExtConfig(JsonUtils.fromJson(json, DeviceExtConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 查询外设硬件配置device_ext_config
	 * 格式
	 * {
	 * 		"library_idx":""
	 * 		"device_tpl_type":""
	 * 		"device_ext_id":""
	 * 		"ext_comm_conf":""
	 * 		"ext_extend_conf":""
	 * 		"logic_obj_idx":""
	 * 		"ext_model_idx":""
	 * 		"updatetime":""
	 * }
	 * @methodName: SelDeviceExtConfig
	 * @param req
	 * @param resp
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value={"SelDeviceExtConfig"})
	@ResponseBody
	public ResultEntity SelDeviceExtConfig(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			//List<DeviceExtConfigEntity> extTemps=deviceExtConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), DeviceExtConfigEntity.class));
			List<Map<String, Object>> extTemps=deviceExtConfService.SelExtData(req.getParameter("json"));
			result.setResult(extTemps);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 新增外设元数据 metadata_ext_model
	 * 格式
	 * {
	 * 		"meta_ext_idx":自增长id
	 * 		"ext_model":""
	 * 		"ext_model_desc":""
	 * 		"ext_model_version":""
	 * 		"ext_type":""
	 * }
	 * @methodName: AddNewMetadataExtModel
	 * @param req
	 * @param resp
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"AddNewMetadataExtModel"})
	@ResponseBody
	public ResultEntity AddNewMetadataExtModel(HttpServletRequest req,HttpServletResponse resp){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			MetadataExtModelEntity extTemp=JsonUtils.fromJson(json, MetadataExtModelEntity.class);
			int re=deviceExtConfService.insertMetadataExtModel(extTemp);
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新外设元数据
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"UpdMetadataExtModel"})
	@ResponseBody
	public ResultEntity UpdMetadataExtModel(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			MetadataExtModelEntity extTemp=JsonUtils.fromJson(json, MetadataExtModelEntity.class);
			int re=deviceExtConfService.updateMetadataExtModel(extTemp);
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除外设元数据
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"DelMetadataExtModel"})
	@ResponseBody
	public ResultEntity DelMetadataExtModel(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			int re=deviceExtConfService.deleteMetadataExtModel(JsonUtils.fromJson(json, MetadataExtModelEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 条件查询外设元数据
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"SelMetadataExtModel"})
	@ResponseBody
	public ResultEntity SelMetadataExtModel(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<MetadataExtModelEntity> extTemps=deviceExtConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), MetadataExtModelEntity.class));
			result.setResult(extTemps);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 获取所有的硬件与逻辑配置模板
	 *
	 * <p>2016年4月25日 下午6:53:27
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SelAllExtTempList")
	@ResponseBody
	public ResultEntity selAllExtTempList(HttpServletRequest request) {
		ResultEntity result=new ResultEntity();
		try {
			String json = request.getParameter("json");
			Map<String, String> param = new HashMap<String, String>();
			if (!StringUtils.isBlank(json)) {
				param = JsonUtils.fromJson(json, Map.class);
			}
			List<Map<String, Object>> extTempsList=deviceExtConfService.selAllExtTempList(param);
			result.setResult(extTempsList);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 根据类型参数查询
	 *
	 * <p>2016年5月19日 上午9:58:35 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SelExtTempListByParam")
	@ResponseBody
	public ResultEntity SelExtTempListByParam(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		Map<String, Object> param = new HashMap<>();
		DeviceExtLogicPageEntity  pageEntity = new DeviceExtLogicPageEntity();
		int page = pageEntity.getPage();
		int pageSize = pageEntity.getPageSize();
		String deviceType = "";
		String keyword = "";
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json)) {
				param = JsonUtils.fromJson(json, Map.class);
			}
			if (param!=null && !param.isEmpty()) {
				if (param.get("pageSize") != null && !"".equals(param.get("pageSize").toString())) {
					pageSize = Integer.valueOf(param.get("pageSize").toString());
				}
				if (param.get("page") != null && !"".equals(param.get("page").toString())) {
					page = Integer.valueOf(param.get("page").toString());
				}
				keyword = param.get("keyword") == null ? keyword :  param.get("keyword").toString();
				deviceType = param.get("deviceType") == null ? deviceType :  param.get("deviceType").toString();
			}
			pageEntity.setPageSize(pageSize);
			pageEntity.setPage(page);
			pageEntity.setDevice_type(deviceType);
			pageEntity.setDevice_tpl_desc(keyword);
			pageEntity.setDevice_tpl_ID(keyword);
			
			pageEntity = deviceExtConfService.SelExtTempListByParam(pageEntity);	
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(pageEntity);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 获取所有的metadata_ext_model还有metadata_logic_obj的数据
	 *
	 * <p>2016年5月14日 下午1:51:42 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/selAllExtModelAndLogicObj")
	@ResponseBody
	public ResultEntity selAllExtModelAndLogicObj(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			Map<String, Object> map = new HashMap<>();
//			Map<String, String> param = new HashMap<String, String>();
//			if (!StringUtils.isBlank(json)) {
//				param = JsonUtils.fromJson(json, Map.class);
//			}
			List<MetadataLogicObjEntity> logicObjList = metadataLogicObjService
					.selectMetadataLogicObj(JsonUtils.fromJson(json,
							MetadataLogicObjEntity.class));
			
			List<MetadataExtModelEntity> extModelList = deviceExtConfService
					.selectList(JsonUtils.fromJson(json,
							MetadataExtModelEntity.class));
			
			map.put("logicObjList", logicObjList);
			map.put("extModelList", extModelList);
			result.setResult(map);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 
	 * @comment
	 * @param request
	 * @return
	 * @data 2016年6月15日
	 * @author hwl
	 */
	@RequestMapping("/SelDevExtData")
	@ResponseBody
	public ResultEntity SelDevExtData(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			List<Map<String , Object>> list= deviceExtConfService.SelExtData(json);
			
			result.setResult(list);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 新增外设模板信息
	 *
	 * <p>2016年6月15日 下午5:13:24 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/addExtTemp")
	@ResponseBody
	public ResultEntity addExtTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtConfService.addExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新外设逻辑模板信息
	 *
	 * <p>2016年6月15日 下午5:12:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/updateExtTemp")
	@ResponseBody
	public ResultEntity updateExtTemp(HttpServletRequest request ){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtConfService.updateExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除外设逻辑模板信息
	 *
	 * <p>2016年6月15日 下午5:12:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delExtTemp")
	@ResponseBody
	public ResultEntity delExtTemp(HttpServletRequest request ){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtConfService.delExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 批量删除外设逻辑模板信息
	 *
	 * <p>2016年6月15日 下午5:12:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delMultiExtTemp")
	@ResponseBody
	public ResultEntity delMultiExtTemp(HttpServletRequest request ){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}") || req.equals("[]")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtConfService.delMultiExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * req,一个数组device_ids
	 * 获取设备对应的外设信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "GetDevExtModel" })
	@ResponseBody
	public ResultEntity GetDevExtModel(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceExtConfService.GetDevExtModel(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
