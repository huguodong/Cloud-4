package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.entity.LibSelfcheckProtocolConfigEntity;
import com.ssitcloud.entity.MetadataProtocolEntity;
import com.ssitcloud.entity.ProtocolConfigTemplateEntity;
import com.ssitcloud.entity.page.LibSelfcheckProtocolPageEntity;
import com.ssitcloud.service.AcsConfigService;
import com.ssitcloud.service.DeviceAcsLoginInfoService;

@Controller
@RequestMapping(value={"acsconfig"})
public class AcsConfigController {
	
	@Resource
	private AcsConfigService acsConfigService;
	@Resource
	private DeviceAcsLoginInfoService deviceAcsLoginInfoService;
	
	/**
	 * 分页参数查询
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAcsConfigByparam"})
	@ResponseBody
	public ResultEntityF<LibSelfcheckProtocolPageEntity> queryAcsConfigByparam(HttpServletRequest request,String req){
		ResultEntityF<LibSelfcheckProtocolPageEntity> result=new ResultEntityF<>();
		try {
			LibSelfcheckProtocolPageEntity libSelfcheckProtocolPage=acsConfigService.queryAcsConfigByparam(req);
			if(libSelfcheckProtocolPage!=null){
				result.setState(true);
				result.setResult(libSelfcheckProtocolPage);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * ACS 配置管理 新函数
	 * 2016年9月19日 13:51:09
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAcsConfigByparamEX1"})
	@ResponseBody
	public ResultEntity queryAcsConfigByparamEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			ProtocolConfigTemplateEntity protocolConfigTemplates=acsConfigService.queryAcsConfigByparamEX1(req);
			result.setResult(protocolConfigTemplates);
			result.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 使用queryMetadataProtocol
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAllProtocolConfig"})
	@ResponseBody
	public ResultEntity queryAllProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			 List<LibSelfcheckProtocolConfigEntity> libSelfcheckProtocolConfigs=acsConfigService.queryAllProtocolConfig(req);
			 if(libSelfcheckProtocolConfigs!=null){
				 result.setState(true);
				 result.setResult(libSelfcheckProtocolConfigs);
			 }
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 2016年9月19日 17:04:11
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"queryMetadataProtocol"})
	@ResponseBody
	public ResultEntity queryMetadataProtocol(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			List<MetadataProtocolEntity> MetadataProtocols=acsConfigService.queryMetadataProtocol(req);
			if(MetadataProtocols!=null){
				 result.setState(true);
				 result.setResult(MetadataProtocols);
			 }
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 增加 （ 使用 addProtocolConfigEX1 代替 ）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addProtocolConfig"})
	@ResponseBody
	public ResultEntity addProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.addProtocolConfig(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 增加
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
	 * 删除操作<p>废弃，
	 * 使用 delProtocolConfigTemplate 代替
	 * @param request
	 * @param req
	 * @return
	 */
	@Deprecated
	@RequestMapping(value={"delProtocolConfig"})
	@ResponseBody
	public ResultEntity delProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.delProtocolConfig(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 修改操作(使用updProtocolConfigEX1代替 )
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updProtocolConfig"})
	@ResponseBody
	public ResultEntity updProtocolConfig(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.updProtocolConfig(req);
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
			result=acsConfigService.queryProtocolConfigByTplIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 2016年9月19日 17:28:14
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
	 * 使用 delProtocolConfigTemplateEX1代替
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delProtocolConfigTemplate"})
	@ResponseBody
	public ResultEntity delProtocolConfigTemplate(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		
		try {
			result=acsConfigService.delProtocolConfigTemplate(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				//删除不能成功，可能不存在或者还存在外键关联的原因 ,这个感觉会跑出异常而不会进到这里面来
				result.setRetMessage("");
			}
		}
		return result;
	}
	@RequestMapping(value={"delProtocolConfigTemplateEX1"})
	@ResponseBody
	public ResultEntity delProtocolConfigTemplateEX1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=acsConfigService.delProtocolConfigTemplateEX1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				//删除不能成功，可能不存在或者还存在外键关联的原因 ,这个感觉会跑出异常而不会进到这里面来
				result.setRetMessage("");
			}
		}
		return result;
	}
	/**
	 * 获取ACS模板数据
	 *
	 * <p>2016年6月30日 上午10:07:03 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAscTempList")
	@ResponseBody
	public ResultEntity getAscTempList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if(StringUtils.isBlank(json) || json.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
			}
			resultEntity = acsConfigService.getAscTempList(json);
		} catch (Exception e) {
			//获取当前方法名称
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据deviceIdx获取acs logininfo信息
	 * req={deviceIdx:".."}
	 * @return
	 */
	@RequestMapping(value={"loadAcsLogininfo"})
	@ResponseBody
	public ResultEntity loadAcsLogininfo(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceAcsLoginInfoService.loadAcsLogininfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
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
