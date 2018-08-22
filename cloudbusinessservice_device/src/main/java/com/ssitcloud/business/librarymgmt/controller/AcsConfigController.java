package com.ssitcloud.business.librarymgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.librarymgmt.service.AcsConfigService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.devmgmt.entity.LibSelfcheckProtocolPageEntity;

@Controller
@RequestMapping(value={"acsconfig"})
public class AcsConfigController {
	
	@Resource
	private AcsConfigService acsConfigService;
	
	/**
	 * 分页参数查询
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAcsConfigByparam"})
	@ResponseBody
	public ResultEntityF<LibSelfcheckProtocolPageEntity> queryAcsConfigByparam(HttpServletRequest request,String req){
		ResultEntityF<LibSelfcheckProtocolPageEntity> result=new ResultEntityF<LibSelfcheckProtocolPageEntity>();
		try {
			result=acsConfigService.queryAcsConfigByparam_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAcsConfigByparamEX1"})
	@ResponseBody
	public ResultEntity queryAcsConfigByparamEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.queryAcsConfigByparamEX1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 所有ProtocolConfig ,使用 queryMetadataProtocol
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAllProtocolConfig"})
	@ResponseBody
	public ResultEntity queryAllProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.queryAllProtocolConfig_bus(req);
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
			result=acsConfigService.queryMetadataProtocol(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * metadataProtocolConfig
	 *  <br>
	 *  使用 addProtocolConfigEX1 代替
	 * 新增操作
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addProtocolConfig"})
	@ResponseBody
	public ResultEntity addProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.addProtocolConfig_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addProtocolConfigEX1"})
	@ResponseBody
	public ResultEntity addProtocolConfigEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.addProtocolConfigEX1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 删除一个记录
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delProtocolConfig"})
	@ResponseBody
	public ResultEntity delProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.delProtocolConfig_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 更新操作(使用 updProtocolConfigEX1代替)
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updProtocolConfig"})
	@ResponseBody
	public ResultEntity updProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.updProtocolConfig_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	@RequestMapping(value={"updProtocolConfigEX1"})
	@ResponseBody
	public ResultEntity updProtocolConfigEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.updProtocolConfigEX1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 使用 queryProtocolConfigByTplIdxEX1
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryProtocolConfigByTplIdx"})
	@ResponseBody
	public ResultEntity queryProtocolConfigByTplIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.queryProtocolConfigByTplIdx_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 2016年9月19日 17:26:26
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryProtocolConfigByTplIdxEX1"})
	@ResponseBody
	public ResultEntity queryProtocolConfigByTplIdxEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.queryProtocolConfigByTplIdxEX1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 通过模板IDX删除模板  delProtocolConfigTemplateEX1代替
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
			result=acsConfigService.delProtocolConfigTemplate_bus(req);
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
			result=acsConfigService.delProtocolConfigTemplateEX1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value={"queryProtocolConfigTemplate"})
	@ResponseBody
	public ResultEntity queryProtocolConfigTemplate(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.queryProtocolConfigTemplate(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
