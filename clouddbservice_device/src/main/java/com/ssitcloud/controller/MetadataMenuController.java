package com.ssitcloud.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MetadataMenuEntity;
import com.ssitcloud.service.MetadataMenuService;
import com.ssitcloud.service.UserService;

/**
 * 用户菜单
 *
 * <p>2017年2月14日 下午3:27:02  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/metamenu")
public class MetadataMenuController {

	@Resource
	private MetadataMenuService menuService;
	
	@Resource
	private UserService userService;


	/**
	 * 
	 *
	 * <p>2017年2月14日 下午3:37:16 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelMenuBySsitCloudAdmin"})
	@ResponseBody
	public ResultEntity SelPermissionBySsitCloudAdmin(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String flags = "";
			@SuppressWarnings("unchecked")
			Map<String,Object> map=JsonUtils.fromJson(json, Map.class);
			if(map!=null&&map.containsKey("flags")){
				flags=map.get("flags")==null?"":map.get("flags").toString();
			}
			List<MetadataMenuEntity> list = menuService.queryAll(flags);
			if(list!=null){
				result.setResult(list);
				result.setState(true);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据用户权限查询其菜单
	 *
	 * <p>2017年2月15日 下午6:26:11 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelMenuByOperIdx"})
	@ResponseBody
	public ResultEntity SelMenuByOperIdx(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			@SuppressWarnings("unchecked")
			Map<String,Object> map=JsonUtils.fromJson(json, Map.class);
			if(map!=null&&map.containsKey("idx")){
				String idx=map.get("idx").toString();
				String flags=map.get("flags")==null?"":map.get("flags").toString();
				List<MetadataMenuEntity> list = menuService.SelMenuByOperIdx(idx,flags);
				if(list!=null){
					result.setResult(list);
					result.setState(true);
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 根据用户main_menu_code查询其菜单
	 *lqw
	 * <p>2017年3月20日 
	 * @return
	 */
	@RequestMapping(value={"selectByCode"})
	@ResponseBody
	public ResultEntity selectByCode(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			if (StringUtils.isNotBlank(req)) {
				MetadataMenuEntity metadataMenuEntity = JsonUtils.fromJson(req,
						MetadataMenuEntity.class);
				List<MetadataMenuEntity> list = menuService.selectByCode(metadataMenuEntity);
				result.setValue(true, "success", "", list);
			} else {
				result.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
