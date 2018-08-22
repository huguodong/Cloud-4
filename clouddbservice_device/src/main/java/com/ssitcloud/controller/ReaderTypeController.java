package com.ssitcloud.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.regexp.RegexpUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.ReaderTypeEntity;
import com.ssitcloud.service.ReaderTypeService;

/**
 * 
 * @package com.ssitcloud.controller
 * @comment 读者流通类型表Controller
 * @data 2016年4月23日
 * @author hwl
 */
@Controller
@RequestMapping("/readertype")
public class ReaderTypeController {

	@Resource
	ReaderTypeService readerTypeService;
	
	@RequestMapping(value = { "AddReaderType" })
	@ResponseBody
	public ResultEntity AddReaderType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			ReaderTypeEntity readerType=JsonUtils.fromJson(json,ReaderTypeEntity.class);
			if(readerType!=null){
				String typeId=readerType.getType_id();
				String distinction=readerType.getType_distinction();
				String msg="";
				if(!Pattern.matches("[a-zA-Z0-9]+", typeId)){
					if("1".equals(distinction)){
						msg="流通类型ID只能输入英文或者数字";
					}else if("2".equals(distinction)){
						msg="维护卡号只能输入英文或者数字";
					}
					result.setRetMessage(msg);
					result.setMessage(Const.CHECK_FALSE);
					result.setResult("ID_RULE_INVALID");
					return result;
				}
				List<ReaderTypeEntity> findReaderTypes=null;
				if("2".equals(readerType.getType_distinction())){
					 findReaderTypes=readerTypeService.selectCardByTypeIdAndLibIdx(readerType);
				}else{
					 findReaderTypes=readerTypeService.selectReaderTypeByTypeIdAndLibIdx(readerType);
				}
				if(CollectionUtils.isNotEmpty(findReaderTypes)){
					if("1".equals(distinction)){
						result.setRetMessage("ID已经占用，请修改");
					}else if("2".equals(distinction)){
						result.setRetMessage("维护卡号已经占用，请修改");
					}
					result.setMessage(Const.CHECK_FALSE);
					result.setResult("ID_EXISTS");
					return result;
				}
				int re =0;
				try {
					re=readerTypeService.insertReaderType(readerType);
				} catch (Exception e) {
					if(e instanceof org.springframework.dao.DuplicateKeyException){
						msg=e.getCause().getMessage();
						com.ssitcloud.common.util.LogUtils.error(msg, e);
						if(StringUtils.contains(msg, "Duplicate entry") && StringUtils.contains(msg, "type_name")){
							result.setMessage(Const.CHECK_FALSE);
							if("1".equals(distinction)){
								result.setRetMessage("类型名已经占用，请修改");
							}else if("2".equals(distinction)){
								result.setRetMessage("维护卡名已经占用，请修改");
							}
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
			if("2".equals(readerType.getType_distinction())){
				result.setResult("SYS_CARD_TYPE");
				result.setRetMessage("馆IDX:"+readerType.getLibrary_idx()+"|IDX:"+readerType.getType_idx()+"|设备维护卡名:"+readerType.getType_name()+"||");
			}else{
				result.setRetMessage("馆IDX:"+readerType.getLibrary_idx()+"|流通类型IDX:"+readerType.getType_idx()+"|流通类型名:"+readerType.getType_name()+"||");
			}
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "UpdReaderType" })
	@ResponseBody
	public ResultEntity UpdReaderType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			ReaderTypeEntity readerType=JsonUtils.fromJson(json,ReaderTypeEntity.class);
			String distinction=null;
			if(readerType!=null){
				String typeId=readerType.getType_id();
				distinction=readerType.getType_distinction();
				String msg="";
				if("1".equals(distinction)){
					msg="流通类型ID只能输入英文或者数字";
				}else if("2".equals(distinction)){
					msg="维护卡号只能输入英文或者数字";
				}
				if(!Pattern.matches("[a-zA-Z0-9]+", typeId)){
					result.setRetMessage(msg);
					result.setMessage(Const.CHECK_FALSE);
					return result;
				}
				List<ReaderTypeEntity> findReaderTypes=null;
				if("2".equals(readerType.getType_distinction())){
					 findReaderTypes=readerTypeService.selectCardByTypeIdAndLibIdx(readerType);
				}else{
					 findReaderTypes=readerTypeService.selectReaderTypeByTypeIdAndLibIdx(readerType);
				}
				
				if(CollectionUtils.isNotEmpty(findReaderTypes)){
					for(ReaderTypeEntity readerTypeEntity:findReaderTypes){
						Integer typeidx=readerTypeEntity.getType_idx();
						if(!typeidx.equals(readerType.getType_idx())){
							if("2".equals(distinction)){
								result.setRetMessage("维护卡号已经占用，请修改");
							}else{
								result.setRetMessage("流通类型ID已经占用，请修改");
							}
							result.setMessage(Const.CHECK_FALSE);
							return result;
						}
					}
				}
			}
			int re = 0;
			try {
				re=readerTypeService.updateReaderType(readerType);
				if(re != 1){
					result.setMessage(Const.CHECK_FALSE);
					result.setRetMessage("当前编辑的数据不是最新数据，请刷新后再做编辑");
				}
			} catch (Exception e) {
				if(e instanceof org.springframework.dao.DuplicateKeyException){
					String msg=e.getCause().getMessage();
					com.ssitcloud.common.util.LogUtils.error(msg, e);
					if(StringUtils.contains(msg, "Duplicate entry") && StringUtils.contains(msg, "type_name")){
						result.setMessage(Const.CHECK_FALSE);
						if("1".equals(distinction)){
							result.setRetMessage("类型名已经占用，请修改");
						}else if("2".equals(distinction)){
							result.setRetMessage("维护卡名已经占用，请修改");
						}
					}
				}else{
					result.setRetMessage(e.getCause().getMessage());
				}
				return result;
			}
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "DeleteReaderType" })
	@ResponseBody
	public ResultEntity DeleteReaderType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		StringBuilder idxSB=new StringBuilder();
		StringBuilder deleteFailIdxs=new StringBuilder();
		int re=0;
		try {
			String json = request.getParameter("json");
			List<ReaderTypeEntity> list= JsonUtils.fromJson(json,new TypeReference<List<ReaderTypeEntity>>() {});
			for (Iterator<ReaderTypeEntity> iterator = list.iterator(); iterator.hasNext();) {
				ReaderTypeEntity rEntity = (ReaderTypeEntity)iterator.next();
				try {
					int num = readerTypeService.deleteReaderType(rEntity);
					if(num != 1){
						result.setMessage(Const.CHECK_FALSE);
						result.setRetMessage(Const.FAILED);
						return result;
					}
					re += num;
					idxSB.append(rEntity.getType_idx()).append(",");
				} catch (Exception e) {
					if(e instanceof  org.springframework.dao.DataIntegrityViolationException){
						String msg=((org.springframework.dao.DataIntegrityViolationException) e).getRootCause().getMessage();
						com.ssitcloud.common.util.LogUtils.error(msg, e);
						if(StringUtils.contains(msg, "Cannot delete or update a parent row: a foreign key constraint fails")){
							if(list.size()==1){//单个删除的直接抛出异常
								throw new RuntimeException("使用中的数据不能删除");
							}else{
								deleteFailIdxs.append(rEntity.getType_idx()).append(",");
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
	
	@RequestMapping(value = { "SelectReaderType" })
	@ResponseBody
	public ResultEntity SelectReaderType(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			PageEntity pageEntity = readerTypeService.selectReaderTypeByPage(map);
			result.setResult(pageEntity);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	
	@RequestMapping(value = { "SelectReadertypeByFuzzy" })
	@ResponseBody
	public ResultEntity SelectReadertypeByFuzzy(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("library_idx", request.getParameter("library_idx"));
			map.put("type_id", request.getParameter("type_id"));
			map.put("type_distinction", request.getParameter("type_distinction"));
			map.put("page", request.getParameter("page"));
			PageEntity pageEntity = readerTypeService.selectReaderTypeByFuzzy(map);
			result.setResult(pageEntity);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
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
			resultEntity = readerTypeService.queryMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据图书馆操作员idx 查询操作员的维护卡信息
	 *
	 * <p>2016年7月14日 下午2:53:20 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOperatorMaintenanceCard")
	@ResponseBody
	public ResultEntity queryOperatorMaintenanceCard(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = readerTypeService.queryOperatorMaintenanceCard(req);
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
			resultEntity = readerTypeService.updateOperatorMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条读者流通类型记录EmailParamEntity
	 * 格式
	 * json = {
	 * 		"library_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectByLibIdx"})
	@ResponseBody
	public ResultEntity selectByLibIdx (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			ReaderTypeEntity readerTypeEntity = JsonUtils.fromJson(json, ReaderTypeEntity.class);
			List<ReaderTypeEntity> list = readerTypeService.selectByLibIdx(readerTypeEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
