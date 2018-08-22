package com.ssitcloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MaintenanceCardEntity;
import com.ssitcloud.entity.page.MaintenanceCardPageEntity;
import com.ssitcloud.service.MaintenanceCardService;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceCardController {
	
	
	@Resource
	private MaintenanceCardService maintenanceCardService;
	
	
	/**
	 * 新增维护卡信息
	 *
	 * <p>2017年3月30日 下午7:53:07 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "insertMaintenanceCard" })
	@ResponseBody
	public ResultEntity insertMaintenanceCard(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			MaintenanceCardEntity maintenanceCardEntity = JsonUtils.fromJson(json, MaintenanceCardEntity.class);
			if(maintenanceCardEntity!=null){
				String typeId = maintenanceCardEntity.getCard_id();
				String msg="";
				if(!Pattern.matches("[a-zA-Z0-9]+", typeId)){
					msg="维护卡号只能输入英文或者数字";
					result.setRetMessage(msg);
					result.setMessage(Const.CHECK_FALSE);
					result.setResult("ID_RULE_INVALID");
					return result;
				}
				int count = maintenanceCardService.countCardByCardIdAndLibIdx(maintenanceCardEntity);
				
				if(count > 0){
					result.setRetMessage("维护卡号已经占用，请修改");
					result.setMessage(Const.CHECK_FALSE);
					result.setResult("ID_EXISTS");
					return result;
				}
				int re =0;
				try {
					re = maintenanceCardService.insertMaintenanceCard(maintenanceCardEntity);
				} catch (Exception e) {
					if(e instanceof org.springframework.dao.DuplicateKeyException){
						msg=e.getCause().getMessage();
						com.ssitcloud.common.util.LogUtils.error(msg, e);
						if(StringUtils.contains(msg, "Duplicate entry") && StringUtils.contains(msg, "type_name")){
							result.setMessage(Const.CHECK_FALSE);
							result.setRetMessage("维护卡名已经占用，请修改");
						}
					}else{
						result.setRetMessage(e.getCause().getMessage());
					}
					return result;
				}
				result.setState(re >= 1 ? true : false);
				result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			}
			//
			result.setResult("SYS_CARD_TYPE");
			result.setRetMessage("馆IDX:"+maintenanceCardEntity.getLib_idx()+"|IDX:"+maintenanceCardEntity.getMaintenance_idx()+"|设备维护卡名:"+maintenanceCardEntity.getCard_id()+"||");
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 更新维护卡信息
	 *
	 * <p>2017年3月30日 下午7:53:07 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "updateMaintenanceCard" })
	@ResponseBody
	public ResultEntity updateMaintenanceCard(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			MaintenanceCardEntity maintenanceCardEntity = JsonUtils.fromJson(json, MaintenanceCardEntity.class);
			if(maintenanceCardEntity!=null){
				String typeId = maintenanceCardEntity.getCard_id();
				String msg="";
				if(!Pattern.matches("[a-zA-Z0-9]+", typeId)){
					msg="维护卡号只能输入英文或者数字";
					result.setRetMessage(msg);
					result.setMessage(Const.CHECK_FALSE);
					result.setResult("ID_RULE_INVALID");
					return result;
				}
				int count = maintenanceCardService.countCardByCardIdAndLibIdx(maintenanceCardEntity);
				
				if(count > 0){
					result.setRetMessage("维护卡号已经占用，请修改");
					result.setMessage(Const.CHECK_FALSE);
					result.setResult("ID_EXISTS");
					return result;
				}
				int re =0;
				try {
					re = maintenanceCardService.updateMaintenanceCard(maintenanceCardEntity);
				} catch (Exception e) {
					if(e instanceof org.springframework.dao.DuplicateKeyException){
						msg=e.getCause().getMessage();
						com.ssitcloud.common.util.LogUtils.error(msg, e);
						if(StringUtils.contains(msg, "Duplicate entry") && StringUtils.contains(msg, "type_name")){
							result.setMessage(Const.CHECK_FALSE);
							result.setRetMessage("维护卡名已经占用，请修改");
						}
					}else{
						result.setRetMessage(e.getCause().getMessage());
					}
					return result;
				}
				result.setState(re >= 1 ? true : false);
				result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			}
			//
			result.setResult("SYS_CARD_TYPE");
			result.setRetMessage("馆IDX:"+maintenanceCardEntity.getLib_idx()+"|IDX:"+maintenanceCardEntity.getMaintenance_idx()+"|设备维护卡名:"+maintenanceCardEntity.getCard_id()+"||");
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除维护卡信息
	 *
	 * <p>2017年3月30日 下午7:53:07 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "deleteMaintenanceCard" })
	@ResponseBody
	public ResultEntity deleteMaintenanceCard(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		StringBuilder idxSB=new StringBuilder();
		StringBuilder deleteFailIdxs=new StringBuilder();
		int re=0;
		try {
			String json = request.getParameter("json");
			List<MaintenanceCardEntity> list= JsonUtils.fromJson(json,new TypeReference<List<MaintenanceCardEntity>>() {});
			for (MaintenanceCardEntity maintenanceCardEntity : list) {
				try {
					int num = maintenanceCardService.deleteMaintenanceCard(maintenanceCardEntity);
					if(num != 1){
						result.setMessage(Const.CHECK_FALSE);
						result.setRetMessage(Const.FAILED);
						return result;
					}
					re += num;
					idxSB.append(maintenanceCardEntity.getMaintenance_idx()).append(",");
				} catch (Exception e) {
					if(e instanceof  org.springframework.dao.DataIntegrityViolationException){
						String msg=((org.springframework.dao.DataIntegrityViolationException) e).getRootCause().getMessage();
						com.ssitcloud.common.util.LogUtils.error(msg, e);
						if(StringUtils.contains(msg, "Cannot delete or update a parent row: a foreign key constraint fails")){
							if(list.size()==1){//单个删除的直接抛出异常
								throw new RuntimeException("使用中的数据不能删除");
							}else{
								deleteFailIdxs.append(maintenanceCardEntity.getMaintenance_idx()).append(",");
							}
						}else{
							throw new RuntimeException(msg);
						}
					}else{
						throw new RuntimeException(e);
					}
				}
				if(idxSB.length()>0&&deleteFailIdxs.length()>0){
					result.setRetMessage("删除成功的IDX:"+idxSB.toString().substring(0,idxSB.length()-1)+"|删除失败的IDX:"+deleteFailIdxs.toString().substring(0,deleteFailIdxs.length()-1));
				}else if(idxSB.length()>0&&deleteFailIdxs.length()==0){
					result.setRetMessage("删除成功的IDX:"+idxSB.toString().substring(0,idxSB.length()-1));
					result.setMessage("ONE");
				}else if(deleteFailIdxs.length()>0){
					result.setRetMessage("删除失败的IDX:"+deleteFailIdxs.toString().substring(0,deleteFailIdxs.length()-1));
				}
			}
			result.setState(re >= 1 ? true : false);
			//result.setRetMessage(idxSB.toString());
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 分页模糊查询维护卡信息
	 *
	 * <p>2017年3月30日 下午8:39:59 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "queryMaintenanceCardByFuzzy" })
	@ResponseBody
	public ResultEntity queryMaintenanceCardByFuzzy(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		MaintenanceCardPageEntity pageEntity = new MaintenanceCardPageEntity();
		int page = pageEntity.getPage();
		int pageSize = pageEntity.getPageSize();
		String idxs = "";
		String card_id = "";
		try {
//			map.put("library_idx", request.getParameter("library_idx"));
//			map.put("type_id", request.getParameter("type_id"));
//			map.put("type_distinction", request.getParameter("type_distinction"));
//			map.put("page", request.getParameter("page"));
			String pageMapStr = request.getParameter("page");
			try {
				if(StringUtils.isNotBlank(pageMapStr)){
					Map<String, String> pageMap = JsonUtils.fromJson(pageMapStr, Map.class);
					page = Integer.valueOf(pageMap.get("page"));
					pageSize = Integer.valueOf(pageMap.get("pageSize"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			idxs = request.getParameter("lib_idx");
			card_id = request.getParameter("card_id");
			
			if(JSONUtils.mayBeJSON(idxs)){
				List<Object> objList = JsonUtils.fromJson(idxs, List.class);
				List<String> lib_idxs = new ArrayList<>();
				for (Object obj : objList) {
					lib_idxs.add(String.valueOf(obj));
				}
				pageEntity.setLib_idxs(lib_idxs);
			}
			pageEntity.setPageSize(pageSize);
			pageEntity.setPage(page);
			pageEntity.setCard_id(card_id);
			
			pageEntity = maintenanceCardService.queryMaintenanceCardByFuzzy(pageEntity);	
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(pageEntity);
			
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMaintenanceCard")
	@ResponseBody
	public ResultEntity queryMaintenanceCard(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = maintenanceCardService.queryMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 修改操作员的维护卡信息
	 *
	 * <p>2016年7月14日 下午4:57:20 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOperatorMaintenanceCard")
	@ResponseBody
	public ResultEntity updateOperatorMaintenanceCard(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = maintenanceCardService.updateOperatorMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	

}
