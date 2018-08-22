package com.ssitcloud.interfaceconfig.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.ConfigDataEntity;
import com.ssitcloud.devmgmt.entity.ConfigEntity;
import com.ssitcloud.devmgmt.entity.DataEntity;
import com.ssitcloud.devmgmt.entity.DeviceType;
import com.ssitcloud.devmgmt.entity.InitConfigEntity;
import com.ssitcloud.devmgmt.entity.QueryDevice;
import com.ssitcloud.interfaceconfig.service.JsonDataService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

@Controller
@RequestMapping(value = { "json" })
public class ConfigDbController {
	@Resource
	private JsonDataService jsonDataService;

	@RequestMapping(value = { "getDevTypeData" })
	@ResponseBody
	public ResultEntity getDevTypeData(HttpServletRequest request, String req) {

		JSONObject jsonObj = JSONObject.fromObject(req);
		String library_idx = jsonObj.getString("library_idx");

		List<DeviceType> list = jsonDataService.getDevTypeData(library_idx);
		ResultEntity entity = new ResultEntity();
		entity.setResult(list);
		return entity;
	}

	@RequestMapping(value = { "getLibDevData" })
	@ResponseBody
	public ResultEntity getLibDevData(HttpServletRequest request, String req) {

		JSONObject jsonObj = JSONObject.fromObject(req);
		// String library_idx = jsonObj.getString("library_idx");
		String device_id = jsonObj.getString("device_id");

		HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("library_idx", Integer.valueOf(library_idx));
		map.put("device_id", device_id);
		List<QueryDevice> list = jsonDataService.queryAllLibDev(map);
		ResultEntity entity = new ResultEntity();
		entity.setResult(list);
		return entity;
	}

	@RequestMapping(value = { "getData" })
	@ResponseBody
	public ResultEntity getData(HttpServletRequest request, String req) {
		HashMap<String,Object> map3 = new HashMap<String, Object>();
		JSONObject obj;
		ConfigEntity jb;
		InitConfigEntity jb2;
		String initStr;
		
		JSONObject jsonObj = JSONObject.fromObject(req);
		String lib_id = jsonObj.getString("lib_id");
		String flag = jsonObj.getString("flag");
		//如果是设备级配置
		if("device".equals(flag)){
			//获取图书馆级的default默认项
			List<InitConfigEntity> list2 = new ArrayList<InitConfigEntity>();
			HashMap<String,Object> map2 = new HashMap<String, Object>();
			
			String value[];
			// 如果获取到的initStr为空，则读取图书馆级别的配置
			map2.put("libId", lib_id);
			map2.put("deviceIdx", null);
			initStr = jsonDataService.getInitData(map2);
			if (!"".equals(initStr) && initStr != null) {
				JSONArray json = JSONArray.fromObject(initStr);
				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {
						obj = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成
						// json 对象
						jb2 = (InitConfigEntity) JSONObject.toBean(obj, InitConfigEntity.class);
						value = jb2.getValue();
						if("$S".equals(value[0])||"$P".equals(value[0])){
							System.out.println("key2:"+jb2.getName()+" value2:"+jb2.getFieldset());
							if(jb2.getFieldset()==null||jb2.getFieldset()== ""){
								jb2.setFieldset(jb2.getName());
							}
							System.out.println("key2:"+jb2.getName()+" value2:"+jb2.getFieldset());
							map3.put(jb2.getName()+jb2.getFieldset(),value);
						}
						list2.add(jb2);
					}
				}
			}
		}
		

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("libId", lib_id);
		ResultEntity entity = new ResultEntity();
		List<DataEntity> list = jsonDataService.getJsonData(map);
		List<ConfigEntity> list1 = new ArrayList<ConfigEntity>();
		String s;
		for (int i = 0; i < list.size(); i++) {
			s = list.get(i).getOperation();
			obj = new JSONObject().fromObject(s);
			System.out.println(obj);
			jb = (ConfigEntity) JSONObject.toBean(obj, ConfigEntity.class);
			//如果flag为device设备级配置，则将library级配置的默认值赋给device设备级
			if("device".equals(flag)){
				if(jb.getFieldset()==null||jb.getFieldset()== ""){
					jb.setFieldset(jb.getName());
				}
				String keyValue[]  =(String[]) map3.get(jb.getName()+jb.getFieldset());
				System.out.println("key:"+jb.getName()+" value:"+jb.getFieldset());
				
				System.out.println("key:"+jb.getName()+" value:"+jb.getFieldset());
				if(keyValue!=null&&jb.getDefaultValue()!=keyValue[0]){
					jb.setDefaultValue(keyValue[1]);
				}
			}
			list1.add(jb);
		}
		
		
		
		entity.setResult(list1);
		return entity;
	}

	@RequestMapping(value = "getInitData")
	@ResponseBody
	public ResultEntity getInitData(HttpServletRequest request, String req) {
		JSONObject obj;
		InitConfigEntity jb;
		List<InitConfigEntity> list = new ArrayList<InitConfigEntity>();

		// 获取传递参数中的lib_id和deviceId
		JSONObject jsonObj = JSONObject.fromObject(req);
		Map<String, Object> map = JsonUtils.fromJson(req, Map.class);

		// HashMap<String, Object> map = new HashMap<String, Object>();
		/*
		 * System.out.println(req.toString()); req.
		 */
		/*
		 * String lib_id = jsonObj.getString("lib_id"); int deviceIdx =
		 * jsonObj.getInt("deviceIdx"); if (lib_id != null) { map.put("libId",
		 * Integer.valueOf(lib_id)); } else { map.put("libId", null); } if
		 * (deviceIdx > 0) { map.put("deviceIdx", Integer.valueOf(deviceIdx)); }
		 * else { map.put("deviceIdx", null); }
		 */
		/*
		 * map.put("deviceType", jsonObj.getString("deviceType"));
		 */
		String initStr = jsonDataService.getInitData(map); // [{"name":"passwordOptions","fieldset":"pwdMaxLenght","value":"{$P:8}"},{"name":"passwordOptions","fieldset":"pwdInputTimes","value":"{$P:4}"},{"name":"passwordOptions","fieldset":"pwdErrorDo","value":"{$P:0}"},{"name":"waitTime","fieldset":"AccountDetailInfoForRenew","value":"{$S:44}"}]

		if (!"".equals(initStr) && initStr != null) {
			JSONArray json = JSONArray.fromObject(initStr);

			// ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map2 = new HashMap<String, Object>();
			if (json.size() > 0) {
				for (int i = 0; i < json.size(); i++) {
					obj = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成
					// json 对象
					jb = (InitConfigEntity) JSONObject.toBean(obj, InitConfigEntity.class);
					list.add(jb);
				}
			}
		} else {
			// 如果获取到的initStr为空，则读取图书馆级别的配置
			map.put("deviceIdx", null);
			initStr = jsonDataService.getInitData(map); // [{"name":"passwordOptions","fieldset":"pwdMaxLenght","value":"{$P:8}"},{"name":"passwordOptions","fieldset":"pwdInputTimes","value":"{$P:4}"},{"name":"passwordOptions","fieldset":"pwdErrorDo","value":"{$P:0}"},{"name":"waitTime","fieldset":"AccountDetailInfoForRenew","value":"{$S:44}"}]
			if (initStr != "" || initStr != null) {
				JSONArray json = JSONArray.fromObject(initStr);

				// ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> map2 = new HashMap<String, Object>();
				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {
						obj = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成
														// json 对象
						jb = (InitConfigEntity) JSONObject.toBean(obj, InitConfigEntity.class);
						list.add(jb);
					}
				}
			} else {
				list = null;
			}
		}
		ResultEntity entity = new ResultEntity();
		entity.setResult(list);
		return entity;
	}

	/**
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "saveData")
	@ResponseBody
	public ResultEntity saveData(HttpServletRequest request, String req) {
		JSONObject obj;
		int count = 0;

		ConfigDataEntity configEntity = new ConfigDataEntity();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		configEntity.setCreatetime(df.format(new Date()));

		JSONObject jsonObj = JSONObject.fromObject(req);
		Map<String, Object> map = JsonUtils.fromJson(req, Map.class);

		String valLib = String.valueOf(map.get("lib_id"));
		String valdev = String.valueOf(map.get("deviceIdx"));
		String json = String.valueOf(map.get("json"));
		String operator_type = String.valueOf(map.get("operator_type"));

		int deviceIdx = 0;

		// 对String类型的libId和deviceIdx进行整形转换
		if (!"".equals(valLib.trim()) && !("null".equals(valLib.trim()))) {
			configEntity.setLibId(Integer.valueOf(valLib));
		}

		if (!"".equals(valdev.trim()) && !("null".equals(valdev.trim()))) {
			configEntity.setDeviceIdx(Integer.parseInt(valdev));// (int)
			// map.put("deviceIdx", null);
		}

		if (!"".equals(json.trim()) && !("null".equals(json.trim()))) {
			configEntity.setOperation(json);// json);
		}

		if (!"".equals(operator_type.trim()) && !("null".equals(operator_type.trim()))) {
			configEntity.setOperator_type(operator_type);
		}

		System.out.println(configEntity.toString());

		// 如果lib_id和deviceId存在，则只需要进行更新操作；否则进行插入操作
		// HashMap<String, Object> map1 = new HashMap<String, Object>();
		// 对String类型的libId和deviceIdx进行整形转换与非空判断
		/*
		 * if (map.get("lib_id") != null) { map1.put("libId",
		 * Integer.valueOf(lib_id));
		 * configEntity.setLibId(Integer.valueOf(lib_id)); } else {
		 * map1.put("libId", null); } if (map.get("deviceIdx") != null) {
		 * map1.put("deviceIdx", Integer.valueOf(deviceIdx)); } else {
		 * map1.put("deviceIdx", null); }
		 */

		// map.put("deviceType", deviceType);

		/*
		 * if (deviceId.equals("") || deviceId == null || deviceType.equals("")
		 * || deviceType == null) count = jsonDataService.queryLibExsit(map);
		 * 
		 * else {
		 */
		count = jsonDataService.queryExsit(map);
		// 如果是设备登录，则查询数据lib_id和deviceId是否都存在，不存在插入操作，存在则更新操作
		if (count > 0)
			jsonDataService.updateJsonData(configEntity);
		else
			jsonDataService.saveJsonData(configEntity);
		/* } */
		String device_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+configEntity.getDeviceIdx());
		jsonDataService.pushThemeToDevice(device_id, configEntity.getLibId());
		ResultEntity entity = new ResultEntity();

		return entity;
	}
	
	
}
