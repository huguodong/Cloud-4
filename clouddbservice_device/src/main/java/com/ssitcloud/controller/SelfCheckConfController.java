package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.SelfCheckProtocolEntity;
import com.ssitcloud.service.SelfCheckConfService;

/**
 * 自助借还模板配置控制类配置
 * （metadata_library_selfcheck_protocol表）
 * @package: com.ssitcloud.controller
 * @classFile: SelfCheckConfController
 * @author: liuBh
 * @description: TODO
 */
@Controller
@RequestMapping(value={"selfcheckconf"})
public class SelfCheckConfController {
	
	@Resource
	private SelfCheckConfService selfCheckConfService;
	
	/**
	 * 新增自助借还模板配置
	 * @methodName: AddSelfCheckConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddSelfCheckConf"})
	@ResponseBody
	public ResultEntity AddSelfCheckConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=selfCheckConfService.insertSelfCheckProtocol(JsonUtils.fromJson(req.getParameter("json"), SelfCheckProtocolEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 更新自助借还模板配置
	 * @methodName: UpdSelfCheckConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"UpdSelfCheckConf"})
	@ResponseBody
	public ResultEntity UpdSelfCheckConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=selfCheckConfService.updateSelfCheckProtocol(JsonUtils.fromJson(req.getParameter("json"), SelfCheckProtocolEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 删除自助借还模板配置
	 * @methodName: DelSelfCheckConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DelSelfCheckConf"})
	@ResponseBody
	public ResultEntity DelSelfCheckConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=selfCheckConfService.DelSelfCheckProtocol(JsonUtils.fromJson(req.getParameter("json"), SelfCheckProtocolEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 模糊查询配置
	 * @methodName: SelSelfCheckConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelSelfCheckConf"})
	@ResponseBody
	public ResultEntity SelSelfCheckConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<SelfCheckProtocolEntity> re=selfCheckConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), SelfCheckProtocolEntity.class));
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(re);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 精确查询配置
	 * @methodName: SelSelfCheckConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelSelfCheckConfExactly"})
	@ResponseBody
	public ResultEntity SelSelfCheckConfExactly(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<SelfCheckProtocolEntity> re=selfCheckConfService.selectListExactly(JsonUtils.fromJson(req.getParameter("json"), SelfCheckProtocolEntity.class));
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(re);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
}
