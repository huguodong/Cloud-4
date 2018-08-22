package com.ssitcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceTroubleEntity;
import com.ssitcloud.entity.page.DeviceTroublePageEntity;
import com.ssitcloud.service.DeviceTroubleService;

@Controller
@RequestMapping(value={"deviceTrouble"})
public class DeviceTroubleController {
	@Resource
	private DeviceTroubleService deviceTroubleService;
	
	/**
	 * 新增故障通知DeviceTroubleEntity
	 * 格式
	 * json = {
	 * 		"trouble_idx":"",//故障通知主键，自增
	 * 		"lib_idx":"",
	 * 		"device_idx":"",
	 * 		"trouble_info":"",
	 * 		"trouble_name":"",
	 * 		"create_time":"",
	 * 		"control_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addDeviceTrouble"})
	@ResponseBody
	public ResultEntity addDeviceTrouble (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				DeviceTroubleEntity deviceTroubleEntity = JsonUtils.fromJson(json, DeviceTroubleEntity.class);
				int ret = deviceTroubleService.insertDeviceTrouble(deviceTroubleEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",deviceTroubleEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 修改故障通知DeviceTroubleEntity
	  * 格式
	 * json = {
	 * 		"trouble_idx":"",//故障通知主键，自增
	 * 		"lib_idx":"",
	 * 		"device_idx":"",
	 * 		"trouble_info":"",
	 * 		"trouble_name":"",
	 * 		"create_time":"",
	 * 		"control_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateDeviceTrouble"})
	@ResponseBody
	public ResultEntity updateDeviceTrouble (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				DeviceTroubleEntity deviceTroubleEntity = JsonUtils.fromJson(json, DeviceTroubleEntity.class);
				int ret = deviceTroubleService.updateDeviceTrouble(deviceTroubleEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",deviceTroubleEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除故障通知DeviceTroubleEntity
	 * 格式
	 * json = {
	 * 		"trouble_idx":"",//故障通知主键，自增
	 * 		"lib_idx":"",
	 * 		"device_idx":"",
	 * 		"trouble_info":"",
	 * 		"trouble_name":"",
	 * 		"create_time":"",
	 * 		"control_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteDeviceTrouble"})
	@ResponseBody
	public ResultEntity deleteDeviceTrouble (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				DeviceTroubleEntity deviceTroubleEntity = JsonUtils.fromJson(json, DeviceTroubleEntity.class);
				int ret = deviceTroubleService.deleteDeviceTrouble(deviceTroubleEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",deviceTroubleEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询一条故障通知记录DeviceTroubleEntity
	 * 格式
	 * json = {
	 * 		"trouble_idx":"",//故障通知主键，自增
	 * 		"lib_idx":"",
	 * 		"device_idx":"",
	 * 		"trouble_info":"",
	 * 		"trouble_name":"",
	 * 		"create_time":"",
	 * 		"control_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectDeviceTrouble"})
	@ResponseBody
	public ResultEntity selectDeviceTrouble (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				DeviceTroubleEntity deviceTroubleEntity = JsonUtils.fromJson(json, DeviceTroubleEntity.class);
				deviceTroubleEntity = deviceTroubleService.queryOneDeviceTrouble(deviceTroubleEntity);
				resultEntity.setValue(true, "success","",deviceTroubleEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条故障通知记录DeviceTroubleEntity
	 * author shuangjunjie
	 * 2017年4月19日 下午5:37
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectDeviceTroublesByLibIdxs"})
	@ResponseBody
	public ResultEntity selectDeviceTroublesByLibIdxs (HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		DeviceTroublePageEntity pageEntity = new DeviceTroublePageEntity();
		try {
			String req = request.getParameter("req");
			try {
				pageEntity = JsonUtils.fromJson(req, DeviceTroublePageEntity.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			pageEntity = deviceTroubleService.selectDeviceTroublesByLibIdxs(pageEntity);
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
	 * 更新多条故障通知记录
	 * author shuangjunjie
	 * 2017年4月19日 下午5:37
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateDeviceTroubles"})
	@ResponseBody
	public ResultEntity updateDeviceTroubles (HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map reqMap = JsonUtils.fromJson(req, Map.class);
			List<Integer> troubleIdxsList = new ArrayList();
			String trouble_idxs = reqMap.get("trouble_idxs").toString();
			if(JSONUtils.mayBeJSON(trouble_idxs)){
				troubleIdxsList = JsonUtils.fromJson(trouble_idxs, List.class);
			}
			Map param = new HashMap();
			param.put("troubleIdxs", troubleIdxsList);
			int ret = deviceTroubleService.updateDeviceTroubles(param);
			if (ret>0) {
				result.setValue(true, "success");
			}else{
				result.setValue(false, "failed");
			}
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
