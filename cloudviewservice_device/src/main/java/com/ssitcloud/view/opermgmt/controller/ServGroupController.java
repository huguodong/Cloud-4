package com.ssitcloud.view.opermgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.service.UserService;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.opermgmt.service.ServGroupService;

/**
 * 权限分组管理
 * @author lbh
 *
 */
@RequestMapping(value={"servgroup"})
@Controller
public class ServGroupController extends BasicController{
	@Resource
	private UserService userService;
	
	@Resource
	private ServGroupService servGroupService;
	
	@RequestMapping(value={"main"})
	public ModelAndView main(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		return new ModelAndView("/page/opermgmt/authority-group", model);
	}
	
	/**
	 * 查询权限组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryServgroupByparam"})
	@ResponseBody
	public ResultEntity queryServgroupByparam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.queryServgroupByparam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 取得登陆者的权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"queryOperPerm"})
	@ResponseBody
	public ResultEntity queryOperPerm(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
			    //需要取得用户的权限列表
				Subject currentUser = SecurityUtils.getSubject();
				//MyShiro
				if(currentUser.isAuthenticated()){
					String operStr=(String)currentUser.getPrincipal();
					if(JSONUtils.mayBeJSON(operStr)){
						Operator oper=JsonUtils.fromJson(operStr,Operator.class);
						if(oper!=null){
							result.setResult(oper.getUserRolePermessions());
							result.setState(true);
						}
						//根据idx获取用户的操作命令列表
						//String res=userService.SelPermissionByOperIdx(node.get("operator_idx").asText());
				    	//if(JSONUtils.mayBeJSON(res)){
							//List<UserRolePermessionEntity> userPermissions=JsonUtils.fromNode(JsonUtils.readTree(res).get("result"),new TypeReference<List<UserRolePermessionEntity>>() {});
				    		//model.put(key, value);
							
						//}
					}
				}
		return result;
	}
	/**
	 * 查询出一级权限（相当于菜单）
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectCmdType"})
	@ResponseBody
	public ResultEntity selectCmdType(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.selectCmdType();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询维护卡以及权限
	 *
	 * <p>2017年3月30日 上午9:55:11 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectCardCmdType"})
	@ResponseBody
	public ResultEntity selectCardCmdType(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.selectCardCmdType();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"selectDeviceOperLogCmd"})
	@ResponseBody
	public ResultEntity selectDeviceOperLogCmd(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.selectDeviceOperLogCmd();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * var servGroup={
			"service_group_id":service_group_id,
			"service_group_name":service_group_name,
			"service_group_desc":service_group_desc,
			"meta_opercmd_idx_str":idxArrStr,
			"library_idx":library_idx
		};
	 * 新增一个权限分组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addservgroup"})
	@ResponseBody
	public ResultEntity addservgroup(HttpServletRequest request,String req){
		Operator oper=getCurrentUser();
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.addservgroup(req);
			SystemLogUtil.LogOperation(result, oper, request, Const.OPERCMD_ADD_SERV_GROUP);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 删除单个权限分组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delservgroup"})
	@ResponseBody
	public ResultEntity delservgroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.delservgroup(req);
			if(result.getState())
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_SERV_GROUP);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/****
	 * 修改  权限的时候要 检查 是否影响到 设备的权限，删除权限组的不处理。
	 * 如果影响到的更新 缓存 或使其缓存失效
	 * 
	 * 修改更新权限分组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updservgroup"})
	@ResponseBody
	public ResultEntity updservgroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.updservgroup(req);
			if(result.getState())
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_SERV_GROUP);
			if(result.getResult()!=null){
				String res=result.getResult().toString();
				if("DEV_PERMESSION_HAS_CHANGED".equals(res)){//需要更新 同步程序缓存
					servGroupService.upadtePermessionCacheForAllDevice();
				}
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
