package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.entity.MetadataOpercmdEntity;
import com.ssitcloud.entity.UserRolePermessionEntity;
import com.ssitcloud.entity.page.OperCmdMgmtPageEntity;
import com.ssitcloud.service.MetadataOpercmdService;
import com.ssitcloud.service.UserService;

/**
 * 
 * @comment 操作指令元数据表Controller
 * 
 * @author hwl
 * @data 2016年4月7日
 */
@Controller
@RequestMapping("/metaopercmd")
public class MetadataOpercmdController {

	@Resource
	MetadataOpercmdService metadataOpercmdService;
	@Resource
	private UserService userService;
	/**
	 * 添加操作指令元数据表数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddMetaOperCmd" })
	@ResponseBody
	public ResultEntity AddMetaOperCmd(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = metadataOpercmdService.addMetadataOpercmd(JsonUtils
					.fromJson(request.getParameter("json"),
							MetadataOpercmdEntity.class));
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
	 * 更新操作指令元数据表数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdMetaOperCmd" })
	@ResponseBody
	public ResultEntity UpdMetaOperCmd(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = metadataOpercmdService.updMetadataOpercmd(JsonUtils
					.fromJson(request.getParameter("json"),
							MetadataOpercmdEntity.class));
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
	 * 删除操作指令元数据表数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteMetaOperCmd" })
	@ResponseBody
	public ResultEntity DeleteMetaOperCmd(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = metadataOpercmdService.delMetadataOpercmd(JsonUtils
					.fromJson(request.getParameter("json"),
							MetadataOpercmdEntity.class));
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
	 * 根据条件查询操作指令元数据表数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectMetaOperCmd" })
	@ResponseBody
	public ResultEntity SelectMetaOperCmd(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataOpercmdEntity> metadataOpercmdEntities = metadataOpercmdService
					.selbyidMetadataOpercmd(JsonUtils.fromJson(
							request.getParameter("json"),
							MetadataOpercmdEntity.class));
			try {
				 LogUtils.info("DB层 SelectMetaOperCmd");
				 LogUtils.info(JsonUtils.toJson(metadataOpercmdEntities));
			} catch (Exception e) {
			}
		   
			result.setResult(metadataOpercmdEntities);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 获取用户的业务指令集
	 * @methodName: SelUserCmds
	 * @param request
	 * @param json
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "SelUserCmdsByIdx" })
	@ResponseBody
	public ResultEntity SelUserCmdsByIdx(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			@SuppressWarnings("unchecked")
			Map<String,Object> map=JsonUtils.fromJson(json, Map.class);
			if(map!=null&&map.containsKey("idx")){
				String idx=map.get("idx").toString();
				List<UserRolePermessionEntity> userCmds=userService.queryById(idx);
				if(userCmds!=null){
					result.setResult(userCmds);
					result.setMessage(Const.SUCCESS);
					result.setState(true);
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 获取所有权限
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelPermissionBySsitCloudAdmin"})
	@ResponseBody
	public ResultEntity SelPermissionBySsitCloudAdmin(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			List<UserRolePermessionEntity> userCmds=userService.queryAll();
			if(userCmds!=null){
				result.setResult(userCmds);
				result.setState(true);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 获取设备权限 (datasync)
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "SelUserCmdsByIdxAndRestriDevice" })
	@ResponseBody
	public ResultEntity SelUserCmdsByIdxAndRestriDevice(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			@SuppressWarnings("unchecked")
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			if(map!=null&&map.containsKey("operator_idx")){
				String idx=map.get("operator_idx").toString();
				UserRolePermessionEntity userRolePermession=new UserRolePermessionEntity(Integer.parseInt(idx));
				userRolePermession.setOpercmd("0104");//设备权限
				List<UserRolePermessionEntity> userCmds=userService.queryByIdAndDeviceUser(userRolePermession);
				if(userCmds!=null){
					result.setResult(userCmds);
					result.setMessage(Const.SUCCESS);
					result.setState(true);
				}
			}else{
				result.setMessage("缺少 operator_idx："+req);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "selectCmdType"})
	@ResponseBody
	public ResultEntity selectCmdType(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataOpercmdEntity> metadataOpercmd=metadataOpercmdService.selectCmdType();
			if(metadataOpercmd!=null){
				result.setResult(metadataOpercmd);
				result.setState(true);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询维护卡权限
	 *
	 * <p>2017年3月30日 上午10:36:01 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "selectCardCmdType"})
	@ResponseBody
	public ResultEntity selectCardCmdType(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataOpercmdEntity> metadataOpercmd=metadataOpercmdService.selectCardCmdType();
			System.out.println(metadataOpercmd.size());
			if(metadataOpercmd!=null){
				result.setResult(metadataOpercmd);
				result.setState(true);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "selectDeviceOperLogCmd"})
	@ResponseBody
	public ResultEntity selectDeviceOperLogCmd(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataOpercmdEntity> metadataOpercmd=metadataOpercmdService.selectDeviceOperLogCmd();
			System.out.println(metadataOpercmd.size());
			if(metadataOpercmd!=null){
				result.setResult(metadataOpercmd);
				result.setState(true);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 分页查询
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryServgroupByparam"})
	@ResponseBody
	public ResultEntity queryServgroupByparam(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			OperCmdMgmtPageEntity OperCmdMgmtPage=metadataOpercmdService.queryServgroupByparam(req);
			if(OperCmdMgmtPage!=null){
				result.setResult(OperCmdMgmtPage);
				result.setState(true);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 馆员App:查询馆员对应的权限
	 * json = {
	 * 		"operator_idx":""
	 * 
	 * }
	 * 
	 * author shuangjunjie
	 * 2017/3/28
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"checkPermission"})
	@ResponseBody
	public ResultEntity checkPermission(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if(StringUtils.isBlank(json) || json.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			Map<String,Object> param = JsonUtils.fromJson(json, Map.class);
			param.put("opercmd", "010301");			//仅具有 设备监控权限
			List<MetadataOpercmdEntity> l = metadataOpercmdService.checkPermission(param);
			param.put("opercmd", "010302");			//既具有 设备监控，也具有环境监控权限
			List<MetadataOpercmdEntity> l2 = metadataOpercmdService.checkPermission(param);

			Map<String,Object> map = new HashMap<String,Object>();
			if(l != null && l.size()>0){
				map.put("010301",true);
			}else{
				map.put("010301",false);
			}
			if(l2 != null && l2.size()>0){
				map.put("010302",true);
			}else{
				map.put("010302",false);
			}
			resultEntity.setValue(true, "", "", map);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "检查权限出错", methodName+"()异常"+e.getMessage(), "");
			e.printStackTrace();
		}

		return resultEntity;
	}
	
	
	
}
