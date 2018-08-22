package com.ssitcloud.business.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.app.service.AppSettingServiceI;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.app.entity.AppSettingEntity;

import net.sf.json.util.JSONUtils;

/**
 * 负责服务层的app_setting的增删查改，没有验证用户权限
 * @author lxp
 *
 */
@Controller
@RequestMapping("/appSetting")
public class AppSettingController {
	@Autowired
	private AppSettingServiceI appsettingService;
	
	/**
	 * 根据主键查询AppSettingEntity
	 * @param request，需要提供查询参数“setting_idx”
	 * @return ResultEntity,state中表示是否成功，result中存放查询实体AppSettingEntity
	 */
	@RequestMapping("queryAppSettingByPk")
	@ResponseBody
	public ResultEntity queryAppSetting(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		Integer setting_idx = null;
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEnity.setValue(false, "没有参数或者实体不为合格的json");
			return resultEnity;
		}
		try{
			AppSettingEntity checkEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
			if(checkEntity.getSetting_idx() == null){
				throw new IllegalArgumentException("setting_idx为null");
			}
			setting_idx = checkEntity.getSetting_idx();
		}catch(IllegalArgumentException e){
			resultEnity.setValue(false, e.getMessage());
			return resultEnity;
		}catch(Exception e){
			resultEnity.setValue(false, "实体不为合格的AppSettingEntity json");
			return resultEnity;
		}
		final String appSettingEntityJson = "{\"setting_idx\":{idx}}";
		try{
			AppSettingEntity appSettingEntity = appsettingService
				.queryOneAppSetting(appSettingEntityJson.replace("{idx}", setting_idx.toString()));
			resultEnity.setState(appSettingEntity!=null);
			resultEnity.setResult(appSettingEntity);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		
		return resultEnity;
	}
	
	/**
	 * 模糊查询AppSettingPageEntity
	 * @param request，需要提供查询参数“json={AppSettingPageEntity查询实体}”
	 * @return ResultEntity,state中表示是否成功，result中存放模糊查询列表（List<AppSettingEntity>）
	 */
	@RequestMapping("queryAppSettings")
	@ResponseBody
	public ResultEntity queryAppSettings(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		String json = request.getParameter("req");
		if(json != null && !JSONUtils.mayBeJSON(json)){
			resultEnity.setValue(false, "查询实体不为合格的json");
			return resultEnity;
		}
		String queryJson = json!=null?json:"{}";
		try{
			List<AppSettingEntity> resultList = appsettingService.queryAppSettingS(queryJson);
			resultEnity.setState(resultList!=null);
			resultEnity.setResult(resultList);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		return resultEnity;
	}
	
	/**
	 * 根据主键更新AppSettingEntity
	 * @param request，需要提供查询参数“json={AppSettingEntity查询实体，实体需要设置主键}”
	 * @return ResultEntity ，state值表示是否删除成功
	 */
	@RequestMapping("updateAppSettingByPk")
	@ResponseBody
	public ResultEntity updatAppSetting(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		String json = request.getParameter("req");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEnity.setValue(false, "没有参数或者实体不为合格的json");
			return resultEnity;
		}
		//检查参数是否合格
		try{
			AppSettingEntity checkEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
			if(checkEntity.getSetting_idx() == null){
				throw new IllegalArgumentException("主键为null");
			}
			if(checkEntity.getLib_idx() == null){
				throw new IllegalArgumentException("lib_idx为null");
			}
		}catch(IllegalArgumentException e){
			resultEnity.setValue(false, e.getMessage());
			return resultEnity;
		}catch(Exception e){
			resultEnity.setValue(false, "实体不为合格的AppSettingEntity json");
			return resultEnity;
		}
		try{
			resultEnity = appsettingService.updateAppSetting(json);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		
		return resultEnity;
	}
	
	
	/**
	 * 根据主键删除AppSettingEntity
	 * @param request，需要提供setting_idx
	 * @return ResultEntity ，state值表示是否删除成功
	 */
	@RequestMapping("deleteAppSettingByPk")
	@ResponseBody
	public ResultEntity deleteAppSetting(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		Integer setting_idx = null;
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEnity.setValue(false, "没有参数或者实体不为合格的json");
			return resultEnity;
		}
		try{
			AppSettingEntity checkEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
			if(checkEntity.getSetting_idx() == null){
				throw new IllegalArgumentException("setting_idx为null");
			}
			setting_idx = checkEntity.getSetting_idx();
		}catch(IllegalArgumentException e){
			resultEnity.setValue(false, e.getMessage());
			return resultEnity;
		}catch(Exception e){
			resultEnity.setValue(false, "实体不为合格的AppSettingEntity json");
			return resultEnity;
		}
		final String appSettingEntityJson = "{\"setting_idx\":{idx}}";
		try{
			resultEnity = appsettingService.deleteAppSetting(appSettingEntityJson.replace("{idx}", setting_idx.toString()));
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		
		return resultEnity;
	}
	
	/**
	 * 根据馆idx删除AppSettingEntity
	 * lqw 2017/3/20
	 * @param request，需要提供lib_idx
	 * @return ResultEntity ，state值表示是否删除成功
	 */
	@RequestMapping("deleteAppSettingBylibidx")
	@ResponseBody
	public ResultEntity deleteAppSettingBylibidx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appsettingService.deleteAppSettingBylibidx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	
	/**
	 * 根据主键更新AppSettingEntity
	 * @param request，需要提供插入参数“json={AppSettingEntity实体}”
	 * @return ResultEntity ，state值表示是否插入成功
	 */
	@RequestMapping("/insertAppSetting")
	@ResponseBody
	public ResultEntity insertAppSetting(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		String json = request.getParameter("req");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEnity.setValue(false, "没有参数或者实体不为合格的json");
			return resultEnity;
		}
		//检查参数是否合格
		try{
			AppSettingEntity checkEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
			if(checkEntity.getLib_idx() == null){
				throw new IllegalArgumentException("lib_idx为null");
			}
		}catch(IllegalArgumentException e){
			resultEnity.setValue(false, e.getMessage());
			return resultEnity;
		}catch(Exception e){
			resultEnity.setValue(false, "实体不为合格的AppSettingEntity json");
			return resultEnity;
		}
		
		try{
			resultEnity = appsettingService.insertAppSetting(json);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		
		return resultEnity;
	}
	
	/**
	 * 查询个人菜单设置记录AppSettingPage2Entity 
	 *
	 * author lqw
	 * 2017年3月20日 
	 * @param request
	 * @return
	 */
	@RequestMapping("selectAppSettingByPage")
	@ResponseBody
	public ResultEntity selectAppSettingByPage(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appsettingService.selectAppSettingByPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	
	/**
	 * 根据用户main_menu_code查询其菜单
	 *lqw
	 * <p>2017年3月20日 
	 * @return
	 */
	@RequestMapping("selectByCode")
	@ResponseBody
	public ResultEntity selectByCode(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			if(req == null || !JSONUtils.mayBeJSON(req)){
				result.setValue(false, "没有参数或者实体不为合格的json");
				return result;
			}
			result = appsettingService.selectByCode(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	
	/**
	 * 通过图书馆ID或名称模糊查询
	 *lqw 2017/3/22
	 */
	@RequestMapping(value={"selLibraryByNameORLibId"})
	@ResponseBody
	public ResultEntity selLibraryByNameORLibId(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			if(req == null || !JSONUtils.mayBeJSON(req)){
				result.setValue(false, "没有参数或者实体不为合格的json");
				return result;
			}
			result=appsettingService.selLibraryByNameORLibId(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
