package com.ssitcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.EmailParamEntity;
import com.ssitcloud.service.EmailParamService;

@Controller
@RequestMapping(value = { "emailParam" })
public class EmailParamController {
	@Resource
	private EmailParamService emailParamService;

	/**
	 * 新增邮件服务器配置EmailParamEntity 格式 json = { "email_idx":"",//邮件服务器配置主键，自增
	 * "lib_idx":"", "email_smtp":"", "email_port":"", "email_account":"",
	 * "email_password":"", "email_code":"", "email_use_flg":"" } author
	 * huanghuang 2017年2月9日 下午1:42:14
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "addEmailParam" })
	@ResponseBody
	public ResultEntity addEmailParam(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				EmailParamEntity emailParamEntity = JsonUtils.fromJson(json,
						EmailParamEntity.class);
				int ret = emailParamService.insertEmailParam(emailParamEntity);
				String message = "馆idx："+emailParamEntity.getLib_idx()+"|邮件参数idx："+emailParamEntity.getEmail_idx()+"||";
				if (ret > 0) {
					resultEntity
							.setValue(true, "success", "", emailParamEntity);
				} else {
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(message);
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 修改邮件服务器配置EmailParamEntity 格式 json = { "email_idx":"",//邮件服务器配置主键，自增
	 * "lib_idx":"", "email_smtp":"", "email_port":"", "email_account":"",
	 * "email_password":"", "email_code":"", "email_use_flg":"" } author
	 * huanghuang 2017年2月9日 下午1:42:33
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "updateEmailParam" })
	@ResponseBody
	public ResultEntity updateEmailParam(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				EmailParamEntity emailParamEntity = JsonUtils.fromJson(json,
						EmailParamEntity.class);
				int ret = emailParamService.updateEmailParam(emailParamEntity);
				String message = "馆idx："+emailParamEntity.getLib_idx()+"|邮件参数idx："+emailParamEntity.getEmail_idx()+"||";
				resultEntity.setState(ret >= 1 ? true : false);
				resultEntity.setRetMessage(message);
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 删除邮件服务器配置EmailParamEntity 格式 json = { "email_idx":"",//邮件服务器配置主键，自增
	 * "lib_idx":"", "email_smtp":"", "email_port":"", "email_account":"",
	 * "email_password":"", "email_code":"", "email_use_flg":"" } author
	 * huanghuang 2017年2月9日 下午1:43:32
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "deleteEmailParam" })
	@ResponseBody
	public ResultEntity deleteEmailParam(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		int ret = 0;
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				JSONArray jarr = JSONArray.fromObject(json);
				//String libidxs = "";
				String idxs="";
				for (int i = 0,z=jarr.size(); i < z; i++) {
					EmailParamEntity emailParamEntity = new EmailParamEntity();
					emailParamEntity.setEmail_idx(Integer.parseInt(jarr.getJSONObject(i).getString("email_idx")));
					//emailParamEntity.setLib_idx(Integer.parseInt(jarr.getJSONObject(i).getString("jlibidx")));
					ret = emailParamService.deleteEmailParam(emailParamEntity);
					idxs+=emailParamEntity.getEmail_idx()+",";
					//libidxs+=emailParamEntity.getLib_idx()+",";
					if (ret > 0) {
						resultEntity.setValue(true, "success", "",emailParamEntity);
					} else {
						resultEntity.setValue(false, "failed");
					}
				}
				if(idxs.length() > 0){
					idxs = idxs.substring(0, idxs.length()-1);
				}
				//if(libidxs.length() > 0){
				//	libidxs = libidxs.substring(0, libidxs.length()-1);
				//}
				String message = "邮件参数idx："+idxs+"||";
				resultEntity.setRetMessage(message);
				resultEntity.setMessage("ONE");
			} else {
				resultEntity.setRetMessage("删除失败!");
			}
		} catch (Exception e) {
			resultEntity.setResult(ret);
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 查询一条邮件服务器配置记录EmailParamEntity 格式 json = { "email_idx":"",//邮件服务器配置主键，自增
	 * "lib_idx":"", "email_smtp":"", "email_port":"", "email_account":"",
	 * "email_password":"", "email_code":"", "email_use_flg":"" } author
	 * huanghuang 2017年2月9日 下午1:43:47
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "selectEmailParam" })
	@ResponseBody
	public ResultEntity selectEmailParam(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				EmailParamEntity emailParamEntity = JsonUtils.fromJson(json,
						EmailParamEntity.class);
				emailParamEntity = emailParamService
						.queryOneEmailParam(emailParamEntity);
				resultEntity.setValue(true, "success", "", emailParamEntity);
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 查询多条邮件服务器配置记录EmailParamEntity 格式 json = { "email_idx":"",//邮件服务器配置主键，自增
	 * "lib_idx":"", "email_smtp":"", "email_port":"", "email_account":"",
	 * "email_password":"", "email_code":"", "email_use_flg":"" } author
	 * huanghuang 2017年2月9日 下午1:44:03
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "selectEmailParams" })
	@ResponseBody
	public ResultEntity selectEmailParams(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			List<EmailParamEntity> list = new ArrayList<EmailParamEntity>();
			JSONObject condition = JSONObject.fromObject(json);
			Map<String, Object> params = new HashMap<String, Object>();
			if (condition.has("libIdxs")) {// 如果是in查询，则将查询条件按，拆分，重新拼接
				String[] idxStr = condition.getString("libIdxs").split(",");
				List<Integer> idxList = new ArrayList<Integer>();
				for (String idx : idxStr) {
					idxList.add(Integer.parseInt(idx));
				}
				params.put("libIdxs", idxList);
				list = emailParamService.queryEmailParams(params);
			}
			list = emailParamService.queryEmailParams(params);
			resultEntity.setValue(true, "success", "", list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 分页查询邮件服务器配置记录EmailParamEntity 格式 json = { "email_idx":"",//邮件服务器配置主键，自增
	 * "lib_idx":"", "email_smtp":"", "email_port":"", "email_account":"",
	 * "email_password":"", "email_code":"", "email_use_flg":"" } author
	 * shuangjunjie 2017年2月22日 上午9:45
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "selectEmailParamByPage" })
	@ResponseBody
	public ResultEntity selectEmailParamByPage(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = emailParamService.selectEmailParamByPage(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

}
