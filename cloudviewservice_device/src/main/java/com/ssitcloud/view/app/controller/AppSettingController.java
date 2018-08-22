package com.ssitcloud.view.app.controller;


import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.app.service.AppSettingServiceI;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.SystemLogUtil;


/**
 * 负责服务层的app_setting的增删查改，没有验证用户权限
 * @author lqw
 *2017/3/18
 */
@Controller
@RequestMapping("/appSetting")
public class AppSettingController extends BasicController{
	@Autowired
	private AppSettingServiceI appsettingService;
	
	/**
	 * 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"adminmain"})
	public String main(HttpServletRequest request){
		return "/page/app/adminappsetting";
	}
	/**
	 * 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"readersmain"})
	public String readersmain(HttpServletRequest request){
		return "/page/app/readersappsetting";
	}
	
	/**
	 * 根据主键查询AppSettingEntity
	 * @param request，需要提供查询参数“setting_idx”
	 * @return ResultEntity,state中表示是否成功，result中存放查询实体AppSettingEntity
	 */
	@RequestMapping("queryAppSettingByPk")
	@ResponseBody
	public ResultEntity queryAppSetting(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		String setting_idxStr = request.getParameter("setting_idx");
		String json = "{'setting_idx':'"+setting_idxStr+"'}";
		try{
			resultEnity = appsettingService.queryOneAppSetting(json);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		
		return resultEnity;
	}
	
	/**
	 * 查询 多个 AppSettingPageEntity
	 */
	@RequestMapping("queryAppSettings")
	@ResponseBody
	public ResultEntity queryAppSettings(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		String json = request.getParameter("json");
		try{
			resultEnity = appsettingService.queryAppSettingS(json);
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
		String req = request.getParameter("req");
		String user_type=null;
		Integer lib_idx=null;
		int opertype=-1;
		try{
			JSONObject jb = JSONObject.fromObject(req);
			if(jb.containsKey("user_type")){
				user_type = jb.getString("user_type");
			}
	        if(jb.containsKey("lib_idx")){
	        	lib_idx = jb.getInt("lib_idx");
			}
	        if(jb.containsKey("opertype")){
	        	opertype = jb.getInt("opertype");
			}
	        String str = "[{\"user_type\":\""+user_type+"\",\"lib_idx\":\""+lib_idx+"\"}]";
			appsettingService.deleteAppSettingBylibidx(str);
			JSONArray json = getReqToJson(req);
			for(int i=0,z=json.size();i<z;i++){
				resultEnity = appsettingService.insertAppSetting(json.getJSONObject(i).toString());
			}
			String num = "";
			if(opertype == 1){
				if(user_type.equals("1")){
					num = Const.PERMESSION_ADD_ADAPP;
				}else{
					num = Const.PERMESSION_ADD_READAPP;
				}
			}else{
				if(user_type.equals("1")){
					num = Const.PERMESSION_UPD_ADAPP;
				}else{
					num = Const.PERMESSION_UPD_READAPP;
				}
			}
			
			SystemLogUtil.LogOperation(resultEnity, getCurrentUser(), request, num);
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
		String setting_idxStr = request.getParameter("setting_idx");
		String json = "{'setting_idx':'"+setting_idxStr+"'}";
		try{
			resultEnity = appsettingService.deleteAppSetting(json);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		
		return resultEnity;
	}
	
	/**
	 * 插入AppSettingEntity
	 * @param request，需要提供插入参数“json={AppSettingEntity实体}”
	 * @return ResultEntity ，state值表示是否插入成功
	 */
	@RequestMapping("/insertAppSetting")
	@ResponseBody
	public ResultEntity insertAppSetting(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		try{
			String req = request.getParameter("req");
			JSONArray json = getReqToJson(req);
			JSONObject jb = json.getJSONObject(0);
			String user_type = "";
			if(jb.containsKey("user_type")){
				user_type = jb.getString("user_type");
			}
			for(int i=0,z=json.size();i<z;i++){
				resultEnity = appsettingService.insertAppSetting(json.getJSONObject(i).toString());
			}
			String num = "";
			if(user_type.equals("1")){
				num = Const.PERMESSION_ADD_ADAPP;
			}else{
				num = Const.PERMESSION_ADD_READAPP;
			}
			
			SystemLogUtil.LogOperation(resultEnity, getCurrentUser(), request, num);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		
		return resultEnity;
	}
	public JSONArray getReqToJson(String req){
		String user_type=null;
		String image_url=null;
		Integer lib_idx=null;
		String setting_desc=null;
		String str = null;
		JSONArray json = new JSONArray();
		JSONArray jidx = new JSONArray();
		JSONObject jb = JSONObject.fromObject(req);
		if(jb.containsKey("user_type")){
			user_type = jb.getString("user_type");
		}
        if(jb.containsKey("image_url")){
        	image_url = jb.getString("image_url");
		}
        if(jb.containsKey("lib_idx")){
        	lib_idx = jb.getInt("lib_idx");
		}
        if(jb.containsKey("setting_desc")){
        	setting_desc = jb.getString("setting_desc");
		}
        if(jb.containsKey("service_id")){
        	jidx = jb.getJSONArray("service_id");
        	for(int i=0,z=jidx.size();i<z;i++){
        		Integer setting_sort=null;
        		String service_id=null;
    			JSONObject js = jidx.getJSONObject(i);
    			if(js !=null && !js.toString().equals("null")){
	    			if(js.containsKey("setting_sort")){
	                	setting_sort = js.getInt("setting_sort");
	    			}
	        		if(js.containsKey("service_id")){
	        			service_id = js.getString("service_id");
	    			}
	        		str = "{\"user_type\":\""+user_type+"\",\"image_url\":\""+image_url+"\",\"lib_idx\":"+lib_idx+",\"setting_desc\":\""+setting_desc+"\",\"setting_sort\":"+setting_sort+",\"service_id\":\""+service_id+"\"}";
	        		json.add(JSONObject.fromObject(str));
        		}
        	}
        }
        return json;
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
	public ResultEntity selectAppSettingByPage(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = appsettingService.selectAppSettingByPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	
	/**
	 * 根据馆idx删除AppSettingEntity
	 * lqw 2017/3/20
	 * @param request，需要提供lib_idx
	 * @return ResultEntity ，state值表示是否删除成功
	 */
	@RequestMapping("deleteAppSettingBylibidx")
	@ResponseBody
	public ResultEntity deleteAppSettingBylibidx(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			JSONArray json = JSONArray.fromObject(req);
			JSONObject jb = json.getJSONObject(0);
			String user_type = "";
			if(jb.containsKey("user_type")){
				user_type = jb.getString("user_type");
			}
			result = appsettingService.deleteAppSettingBylibidx(req);
			String num = "";
			if(user_type.equals("1")){
				num = Const.PERMESSION_DEL_ADAPP;
			}else{
				num = Const.PERMESSION_DEL_READAPP;
			}
			
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, num);
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
	@RequestMapping("/selectByCode")
	@ResponseBody
	public ResultEntity selectByCode(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
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
	public ResultEntity selLibraryByNameORLibId(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			result=appsettingService.selLibraryByNameORLibId(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
