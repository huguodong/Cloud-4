/**
 * 
 */
package com.ssitcloud.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.AskVersionResultEntity;
import com.ssitcloud.entity.PatchInfoEntity;
import com.ssitcloud.entity.UpgradeStrategyEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.entity.page.PatchInfoPageEntity;
import com.ssitcloud.service.PatchInfoService;

/**
 * @comment 版本信息表Controller
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Controller
@RequestMapping("/patchinfo")
public class PatchInfoController {

	@Resource
	PatchInfoService patchInfoService;

	/**
	 * 新建版本信息
	 * 格式(patch_idx为自增id，create_time获取当前时间)
	 * { 
	 * "patch_idx":"",
	 * "patch_version":"" ,
	 * "patch_desc":"",
	 * "patch_type":"" ,
	 * "restrict_info":"",
	 * "patch_directory":"" ,
	 * "create_time":"" 
	 *  }
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddPatchInfo" })
	@ResponseBody
	public ResultEntity AddPatchInfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		StringBuilder sb=new StringBuilder("模板IDX：");
		try {
			String json = request.getParameter("req");
			PatchInfoEntity patchInfoEntity = JsonUtils.fromJson(json,PatchInfoEntity.class);
			patchInfoEntity.setPatch_directory("");
			Integer n=patchInfoService.selectPatchInfoCountByVersion(patchInfoEntity);
			int re =-1;
			if(n!=null){
				if(n>0){//Duplicate patch_version
					re=-1;
				}else{
					re = patchInfoService.addPatchInfo(patchInfoEntity);
					sb.append(patchInfoEntity.getPatch_idx());
				}
			}
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			if(re==-1){
				result.setMessage("版本号已经存在");
				result.setResult(-1);
			}else{
				result.setRetMessage(sb.toString());
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新版本信息
	 * 格式
	 * { 
	 * "patch_idx":"",
	 * "patch_version":"" ,
	 * "patch_desc":"",
	 * "patch_type":"" ,
	 * "restrict_info":"",
	 * "patch_directory":"",
	 *  }
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdPatchInfo" })
	@ResponseBody
	public ResultEntity UpdPatchInfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		StringBuilder sb=new StringBuilder("模板IDX：");
		try {
			String json = request.getParameter("req");
			PatchInfoEntity patchInfoEntity=JsonUtils.fromJson(json,PatchInfoEntity.class);
			Integer n=patchInfoService.selectPatchInfoCountByVersion(patchInfoEntity);
			int re =-1;
			if(n!=null){
				if(n>0){//Duplicate patch_version,出去本身的ID
					re=-1;
				}else{
					re = patchInfoService.updatePatchInfo(patchInfoEntity);
					sb.append(patchInfoEntity.getPatch_idx());
				}
			}
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : "optimistic");
			if(re==-1){
				result.setMessage("版本号已经存在");
				result.setResult(-1);
			}else{
				result.setRetMessage(sb.toString());
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除版本信息
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DelPatchInfo" })
	@ResponseBody
	public ResultEntity DeletePatchInfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		int re = 0;
		try {
			String json = request.getParameter("req");
			if (!StringUtils.hasText(json)) {
				result.setValue(false, "参数不能为空");
				return result;
			}
			result = patchInfoService.deleteSinglePatchInfo(json);
//			List<PatchInfoEntity> list = JsonUtils.fromJson(json, new TypeReference<List<PatchInfoEntity>>() {});
//			for (Iterator<PatchInfoEntity> iterator = list.iterator(); iterator.hasNext();) {
//				PatchInfoEntity pEntity = (PatchInfoEntity)iterator.next();
//				re += patchInfoService.deletePatchInfo(pEntity);
//			}
//			
//			result.setState(re >= 1 ? true : false);
//			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 批量删除
	 *
	 * <p>2016年7月30日 下午5:59:20 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "delMultiPatchInfo" })
	@ResponseBody
	public ResultEntity delMultiPatchInfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		int re = 0;
		try {
			String json = request.getParameter("req");
			if (!StringUtils.hasText(json)) {
				result.setValue(false, "参数不能为空");
				return result;
			}
			result = patchInfoService.delMultiPatchInfo(json);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 条件查询版本信息
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelPatchInfo" })
	@ResponseBody
	public ResultEntityF<PatchInfoPageEntity>  SelectPatchInfoByPage(HttpServletRequest request,String req) {
		ResultEntityF<PatchInfoPageEntity> result =new ResultEntityF<PatchInfoPageEntity>(); 
		PatchInfoPageEntity page=new PatchInfoPageEntity();
		try {
			if(StringUtils.hasText(req)){
				 page=JsonUtils.fromJson(req, PatchInfoPageEntity.class);
			}
			page.setDoAount(true);
			page=patchInfoService.selPatchInfoByPage(page);
			result.setResult(page);
			result.setState(true);
			return result;
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@RequestMapping(value = { "askVersion" })
	@ResponseBody
	public ResultEntity askVersion(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		Assert.hasText(req, "req has no text!!!!");
		try {
			AskVersionResultEntity patchInfoEntities = patchInfoService.askVersion(JsonUtils.fromJson(req, UpgradeStrategyEntity.class));
			
			result.setResult(patchInfoEntities);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据 PathInfoIdx查询对应的限制的图书馆的设备
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx")
	@ResponseBody
	public ResultEntity SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=patchInfoService.SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 根据 PathInfoIdx查询对应的限制的图书馆的设备
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx")
	@ResponseBody
	public ResultEntity SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=patchInfoService.SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
