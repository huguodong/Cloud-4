package com.ssitcloud.view.librarymgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.librarymgmt.service.AcsConfigService;

/**
 * ACS服务模板配置 控制类
 * @author lbh
 *
 */
@Controller
@RequestMapping(value={"ascconfig"})
public class AcsConfigController extends BasicController{

	@Resource
	private AcsConfigService ascConfigService;
	
	@RequestMapping(value={"main"})
	public ModelAndView main(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		//return new ModelAndView("/page/librarymgmt/config-ACS", model);
		return new ModelAndView("/page/librarymgmt/config-ACS-EX1", model);
	}
	/**
	 * @author lbh
	 *  保留旧的ACS函数,占时弃用 和 queryAcsConfigByparamEx1 调用其中一个
	 * @param request
	 * @param req
	 * @return
	 */
	@Deprecated
	@RequestMapping(value={"queryAcsConfigByparam"})
	@ResponseBody
	public ResultEntity queryAcsConfigByparam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.queryAcsConfigByparam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 新的ACS 管理界面 <br>
	 * 2016年9月19日 11:14:30
	 * 主管管理员用户可以看到 分管的acs模板
	 * 
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAcsConfigByparamEx1"})
	@ResponseBody
	public ResultEntity queryAcsConfigByparamEx1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.queryAcsConfigByparamEx1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查询所有ProtocolConfig字典信息，使用 queryMetadataProtocol
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAllProtocolConfig"})
	@ResponseBody
	public ResultEntity queryAllProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.queryAllProtocolConfig(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	@RequestMapping(value={"queryMetadataProtocol"})
	@ResponseBody
	public ResultEntity queryMetadataProtocol(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.queryMetadataProtocol(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 根据当前登陆用户
	 * 查询图书馆信息
	 * 
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"querylibInfoByCurUser"})
	@ResponseBody
	public ResultEntity querylibInfoByCurUser(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			String oper=getCurrentUserJson();
			if(oper!=null){
				result=ascConfigService.querylibInfoByCurUser(oper);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据当前登陆用户
	 * 查询图书馆信息
	 * 
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"querylibInfoByCurUserEXT1"})
	@ResponseBody
	public ResultEntity querylibInfoByCurUserEXT1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			String oper=getCurrentUserJson();
			if(oper!=null){
				result=ascConfigService.querylibInfoByCurUserEXT1(oper);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 新增  (使用 addProtocolConfigEX1代替)
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addProtocolConfig"})
	@ResponseBody
	public ResultEntity addProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.addProtocolConfig(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_ADD_SELFCONF);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value={"addProtocolConfigEX1"})
	@ResponseBody
	public ResultEntity addProtocolConfigEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.addProtocolConfigEX1(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_ADD_SELFCONF);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 删除<p> 
	 * 废弃, 使用delProtocolConfigTemplate
	 * <p>
	 * req={"protocol_idx":"...."}
	 * @return
	 * @see delProtocolConfigTemplate
	 */
	@Deprecated
	@RequestMapping(value={"delProtocolConfig"})
	@ResponseBody
	public ResultEntity delProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.delProtocolConfig(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 修改操作 （使用updProtocolConfigEX1代替 ）
	 * req={
	 * 	实体 JSON string
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updProtocolConfig"})
	@ResponseBody
	public ResultEntity updProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.updProtocolConfig(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_SELFCONF);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value={"updProtocolConfigEX1"})
	@ResponseBody
	public ResultEntity updProtocolConfigEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.updProtocolConfigEX1(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_SELFCONF);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 通过模板idx查询 配置数据,使用queryProtocolConfigByTplIdxEX1
	 * req={
	 * 	protocol_idx:"xxxx"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryProtocolConfigByTplIdx"})
	@ResponseBody
	public ResultEntity queryProtocolConfigByTplIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.queryProtocolConfigByTplIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 通过模板idx查询 配置数据
	 * @return
	 */
	@RequestMapping(value={"queryProtocolConfigByTplIdxEX1"})
	@ResponseBody
	public ResultEntity queryProtocolConfigByTplIdxEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.queryProtocolConfigByTplIdxEX1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查询 模板实体
	 * ProtocolConfig
	 * @return
	 */
	@RequestMapping(value={"queryProtocolConfigTemplate"})
	@ResponseBody
	public ResultEntity queryProtocolConfigTemplate(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.queryProtocolConfigTemplate(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 通过模板IDX删除模板 ，暂时作废，使用delProtocolConfigTemplateEX1代替
	 * req={
	 * 	protocol_tpl_idx:"....."
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delProtocolConfigTemplate"})
	@ResponseBody
	public ResultEntity delProtocolConfigTemplate(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.delProtocolConfigTemplate(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_SELFCONF);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 通过模板IDX删除模板  
	 * req={
	 * 	protocol_tpl_idx:"....."
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delProtocolConfigTemplateEX1"})
	@ResponseBody
	public ResultEntity delProtocolConfigTemplateEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=ascConfigService.delProtocolConfigTemplateEX1(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_SELFCONF);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	
}
