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
import com.ssitcloud.entity.PersonalSettingEntity;
import com.ssitcloud.service.PersonalSettingService;

@Controller
@RequestMapping("/personalsetting")
public class PersonalSettingController {
	@Resource
	private PersonalSettingService personalSettingService;
	
	/**
	 * 新增个人菜单设置
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPersonalSetting")
	@ResponseBody
	public ResultEntity addPersonalSetting(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				PersonalSettingEntity personalSettingEntity = JsonUtils.fromJson(json, PersonalSettingEntity.class);
				String retMessage = "常用菜单idx："+personalSettingEntity.getSetting_idx()+"|常用菜单名："+personalSettingEntity.getSetting_desc()+"||";
				
				boolean isExists = personalSettingService.isExists(personalSettingEntity);
				if(isExists){
					resultEntity.setValue(false, "已经存在，请勿重复添加!");
				}else{
					int ret = personalSettingService.addPersonalSetting(personalSettingEntity);
					if (ret>0) {
						resultEntity.setValue(true, "success","",personalSettingEntity);
					}else{
						resultEntity.setValue(false, "添加失败");
					}
				}
				resultEntity.setRetMessage(retMessage);
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
	 * 根据idx删除个人菜单设置
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delPersonalSetting")
	@ResponseBody
	public ResultEntity delPersonalSetting(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				JSONArray jarr = JSONArray.fromObject(json);
				String idxs="";
				//String setNames="";
				for (int i = 0,z=jarr.size(); i < z; i++) {
					PersonalSettingEntity personalSettingEntity = new PersonalSettingEntity();
					personalSettingEntity.setSetting_idx(Integer.parseInt(jarr.getJSONObject(i).getString("setting_idx")));
					int ret = personalSettingService.delPersonalSetting(personalSettingEntity);
					idxs = personalSettingEntity.getSetting_idx()+",";
					//setNames = personalSettingEntity.getSetting_desc()+",";
					if (ret>=0) {
						resultEntity.setValue(true, "success","",personalSettingEntity);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}
				if(idxs.length() >0){
					idxs = idxs.substring(0, idxs.length()-1);
				}
				//if(setNames.length() >0){
				//	setNames = setNames.substring(0, setNames.length()-1);
				//}
				String retMessage = "常用菜单idx："+idxs+"||";
				resultEntity.setRetMessage(retMessage);
				resultEntity.setMessage("ONE");
			}else {
				resultEntity.setRetMessage("删除失败!");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据idx更新个人菜单设置
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updPersonalSetting")
	@ResponseBody
	public ResultEntity updPersonalSetting(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				PersonalSettingEntity personalSettingEntity = JsonUtils.fromJson(json, PersonalSettingEntity.class);
				int ret = personalSettingService.updPersonalSetting(personalSettingEntity);
				String retMessage = "常用菜单idx："+personalSettingEntity.getSetting_idx()+"|常用菜单名："+personalSettingEntity.getSetting_desc()+"||";
				if (ret>0) {
					resultEntity.setValue(true, "success","",personalSettingEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(retMessage);
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
	 * 根据IDX查询单条个人菜单设置
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/selPersonalSettingByIdx")
	@ResponseBody
	public ResultEntity selPersonalSettingByIdx(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				PersonalSettingEntity personalSettingEntity = JsonUtils.fromJson(json, PersonalSettingEntity.class);
				personalSettingEntity = personalSettingService.selPersonalSettingByIdx(personalSettingEntity);
				resultEntity.setValue(true, "success","",personalSettingEntity);
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
	 * 分页查询 个人菜单设置
	 * <p>2017年2月23日
	 * <p>create by shuangjunjie
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectPersonalSettingByPage")
	@ResponseBody
	public ResultEntity selectPersonalSettingByPage(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isNotBlank(req)){
				result = personalSettingService.selectPersonalSettingByPage(req);
			}else{
				result.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询多条个人菜单设置
	 * author huanghuang
	 * 2017年4月5日 上午10:31:46
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "selPersonalSettingList" })
	@ResponseBody
	public ResultEntity selPersonalSettingList(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			PersonalSettingEntity personalSettingEntity = JsonUtils.fromJson(json, PersonalSettingEntity.class);
			List<PersonalSettingEntity> list = personalSettingService.selPersonalSettingList(personalSettingEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
}
