package com.ssitcloud.business.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.LibraryServiceI;
import com.ssitcloud.business.mobile.service.PasswordServiceI;
import com.ssitcloud.business.mobile.service.ReaderCardServiceI;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;

/**
 * 图书馆信息对外接口
 * @author LXP
 * @version 创建时间：2017年3月1日 下午3:58:59
 */
@Controller
@RequestMapping("/library")
public class LibraryControler {
	@Autowired
	private ReaderCardServiceI readerCardService;
	
	@Autowired
	private LibraryServiceI libraryService;
	
	@Autowired
	private PasswordServiceI passwordService;
	
	/**
	 * 查询读者绑定的图书馆信息
	 * @param request
	 * @return result中lib键表示读者绑定图书馆信息，card键表示读者绑定图书卡信息，两者类型均为list
	 */
	@RequestMapping("/readerLib")
	@ResponseBody
	public ResultEntity readerLib(HttpServletRequest request){
		String json = request.getParameter("json");
		ResultEntity entity = new ResultEntity(); 
		try{
			if(json == null){
				entity.setValue(false, "提交参数有误");
				return entity;
			}
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			Integer reader_idx = (Integer) map.get("reader_idx");
			if(reader_idx == null){
				entity.setValue(false, "提交参数有误");
				return entity;
			}
			
			ReaderCardEntity readerCardEntity = new ReaderCardEntity();
			readerCardEntity.setReader_idx(reader_idx);
			List<Map<String, Object>> cardList = readerCardService.selectReaderCards(readerCardEntity);
			if(cardList == null){
				entity.setValue(false, "查询失败");
				return entity;
			}
			
			List<Integer> libIdList = new ArrayList<>(cardList.size());
			Iterator<Map<String, Object>> iterator = cardList.iterator();
			while(iterator.hasNext()){
				Map<String, Object> mapTemp = iterator.next();
				String pwd = passwordService.decrypt((String) mapTemp.get("card_password"));
				if(pwd != null){
					mapTemp.put("card_password", pwd);
				}else{
					iterator.remove();
				}
				libIdList.add((Integer) mapTemp.get("lib_idx"));
			}
			
			List<Map<String, Object>> libList = libraryService.selectLibrarys(libIdList);
			libList = removeNotNeedData(libList);
			
			Map<String, Object> cardAndLib = new HashMap<>(4);
			cardAndLib.put("card", cardList);
			cardAndLib.put("lib", libList);
			
			entity.setState(true);
			entity.setResult(cardAndLib);
			return entity;
		}catch(Exception e){
			LogUtils.info(e);
			entity.setValue(false, e.getMessage());
			return entity;
		}
		
	}
	
	@RequestMapping("/getLibList")
	@ResponseBody
	public ResultEntity lib(HttpServletRequest request){
		String json = request.getParameter("json");
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> map=null;
		try{
			map = JsonUtils.fromJson(json, Map.class);
		}catch(Exception e){
			LogUtils.info(e);
			resultEntity.setState(false);
			resultEntity.setMessage("请求参数有误");
			return resultEntity;
		}
		
		List<Map<String, Object>> result = libraryService.selectLibrarys(map);
		result = removeNotNeedData(result);
		resultEntity.setState(result != null);
		resultEntity.setResult(result);
		
		return resultEntity;
	}
	
	/**
	 * 根据主键获取图书馆
	 * @param request json={"lib_idx":图书馆主键}
	 * @return
	 */
	@RequestMapping("/selectlibrary")
	@ResponseBody
	public ResultEntity library(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if(json != null){
			try{
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				Integer lib_idx = (Integer)map.get("lib_idx");
				if(lib_idx != null){
					Map<String, Object> libraryMap = libraryService.selectLibraryByPk(lib_idx);
					removeNotNeedData(libraryMap);
					resultEntity.setState(true);
					resultEntity.setResult(libraryMap);
				}
			}catch(Exception e){
				LogUtils.info(getClass()+" library 异常",e);
			}
		}
		return resultEntity;
	}
	
	
	/**
	 * 清除客户端不需要的数据
	 * @param data
	 * @return
	 */
	private Map<String, Object> removeNotNeedData(Map<String, Object> data){
		if(data == null){
			return data;
		}
		
		data.remove("relLibs");
		data.remove("libInfo");
		data.remove("lib_service_tpl_id");
		data.remove("service_start_date");
		data.remove("service_expire_date");
		data.remove("createtime");
		data.remove("version_stamp");
		
		return data;
	}
	
	/**
	 * 清除客户端不需要的数据
	 * @param data
	 * @return
	 */
	private List<Map<String, Object>> removeNotNeedData(List<Map<String, Object>> data){
		if(data == null){
			return data;
		}
		for (Map<String, Object> map : data) {
			removeNotNeedData(map);
		}
		
		return data;
	}
	
	/**
	 * 根据library_idx查出library信息
	 * add by shuangjunjie
	 * 2017年4月8日
	 * @param reuqest
	 * @return
	 */
	@RequestMapping("/selectLibraryIdByIdx")
	@ResponseBody
	public ResultEntity selectLibraryIdByIdx(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("json");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = libraryService.selectLibraryIdByIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
		}
		return resultEntity;
	}
	
	@RequestMapping("/sendBarcode")
	@ResponseBody
	public ResultEntity sendBarcode(String json){
		if(json == null){
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
		return libraryService.sendBarcode(json,readerCardService);
	}
}
