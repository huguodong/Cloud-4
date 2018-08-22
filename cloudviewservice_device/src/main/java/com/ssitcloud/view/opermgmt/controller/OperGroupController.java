package com.ssitcloud.view.opermgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.opermgmt.service.OperGroupService;
import com.ssitcloud.view.opermgmt.service.ServGroupService;


/**
 * 
 * 操作员分组管理页面
 * 
 * @author lbh
 *
 */
@Controller
@RequestMapping(value={"opergroup"})
public class OperGroupController extends BasicController{
	@Resource
	private OperGroupService operGroupService;
	
	@Resource
	private ServGroupService servGroupService;
	
	@RequestMapping(value={"main"})
	public ModelAndView main(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		Operator operator=getCurrentUser();
		model.put("operator", operator);
		return new ModelAndView("/page/opermgmt/opergroup-manage", model);
	}
	@RequestMapping(value={"add"})
	public ModelAndView add(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		
		return new ModelAndView("/page/include/opermgmt/add_opergroup_div", model);
	}
	/**
	 * 分页参数查询
	 * 如果是管理员？
	 * 如果是
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"queryOperGroupByparam"})
	@ResponseBody
	public ResultEntity queryOperGroupByparam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {	
			Operator oper=getCurrentUser();
			if(oper!=null){
				String library_idx=oper.getLibrary_idx();
				String operator_type=oper.getOperator_type();
				String operator_idx=oper.getOperator_idx();
				
				if(JSONUtils.mayBeJSON(req)){
					Map<String, Object> m=JsonUtils.fromJson(req, Map.class);
					if(library_idx!=null) 
						m.put("library_idx", library_idx);
					if(operator_type!=null) 
						m.put("operator_type", operator_type);
					if(operator_idx!=null) 
						m.put("operator_idx", operator_idx);
					
					result=operGroupService.queryOperGroupByparam_view(JsonUtils.toJson(m));
				}
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	@RequestMapping(value={"queryServiceGroupAndCmd"})
	@ResponseBody
	public ResultEntity queryServiceGroupAndCmd(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operGroupService.queryServiceGroupAndCmd(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 增加操作员分组 操作
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"addOperGroup"})
	@ResponseBody
	public ResultEntity addOperGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			Operator oper=getCurrentUser();
			if(JSONUtils.mayBeJSON(req)){
				Map<String, Object> map=JsonUtils.fromJson(req, Map.class);
				map.put("operator_idx", oper.getOperator_idx());
				result=operGroupService.addOperGroup(JsonUtils.toJson(map));
				SystemLogUtil.LogOperation(result,oper, request, Const.OPERCMD_ADD_OPERGROUP);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 删除一行数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delOperGroup"})
	@ResponseBody
	public ResultEntity delOperGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		Operator oper=getCurrentUser();
		try {
			if(JSONUtils.mayBeJSON(req)){
				@SuppressWarnings("unchecked")
				Map<String, Object> map=JsonUtils.fromJson(req, Map.class);
				map.put("operator_idx", oper.getOperator_idx());
				result=operGroupService.delOperGroup(JsonUtils.toJson(map));
				SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_OPERGROUP);
			}else{
				LogUtils.error("delOperGroup:"+req);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 修改一行数据
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"updOperGroup"})
	@ResponseBody
	public ResultEntity updOperGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
				Operator oper=getCurrentUser();
				if(JSONUtils.mayBeJSON(req)){
					Map<String, Object> map=JsonUtils.fromJson(req, Map.class);
					map.put("operator_idx", oper.getOperator_idx());
					result=operGroupService.updOperGroup(JsonUtils.toJson(map));
					SystemLogUtil.LogOperation(result, oper, request, Const.OPERCMD_UPDATE_OPERGROUP);
				}
			} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 通过oeprator_idx查询operator_name
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryOperatorNameByoperIdxArr"})
	@ResponseBody
	public ResultEntity queryOperatorNameByoperIdxArr(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operGroupService.queryOperatorNameByoperIdxArr(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 查询所有的用户组信息，以及用户组对应的权限信息
	 *
	 * <p>2016年6月23日 下午7:19:45 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllServiceGroup")
	@ResponseBody
	public ResultEntity queryAllServiceGroup(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result=operGroupService.queryAllServiceGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 查询图书馆的用户组
	 *
	 * <p>2016年6月14日 下午1:51:30 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryLibraryServiceGroup")
	@ResponseBody
	public ResultEntity queryLibraryServiceGroup(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			Map<String, String> param = new HashMap<String, String>();
			if (!StringUtils.isBlank(req)) {
				param = JsonUtils.fromJson(req, Map.class);
			}
			param.put("oLib_idx", "0");//操作员所在馆
			param.put("oOper_idx", "1");//操作员idx
			param.put("oOper_type", "1");//操作员类型
			resultEntity = operGroupService.queryLibraryServiceGroup(JsonUtils.toJson(param));
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	
}
