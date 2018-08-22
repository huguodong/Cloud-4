package com.ssitcloud.view.statistics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.MasterLibAndSlaveLibsEntity;
import com.ssitcloud.common.entity.OperatorType;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.controller.BasicController;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.common.util.JsonUtils;
import com.ssitcloud.view.statistics.service.MainPageService;
import com.ssitcloud.view.statistics.service.RegionService;

@Controller
@RequestMapping(value = { "mainPage" })
public class MainPageController extends BasicController{
	@Resource
	private MainPageService mainPageService;
	@Resource
    RegionService regionService;
	
	/**
	 * 统计办证信息
	 */
	@RequestMapping("/countCardissueLog")
    @ResponseBody
	public ResultEntity countCardissueLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
            result = mainPageService.countCardissueLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}

	/**
	 * 统计借还信息
	 * @return
	 */
	@RequestMapping("/countLoanLog")
    @ResponseBody
	public ResultEntity countLoanLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
            result = mainPageService.countLoanLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}

	/**
	 * 统计财经信息
	 * @return
	 */
	@RequestMapping("/countFinanceLog")
    @ResponseBody
	public ResultEntity countFinanceLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
            result = mainPageService.countFinanceLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}
	
	/**
	 * 人流量信息
	 * @return
	 */
	@RequestMapping("/countVisitorLog")
    @ResponseBody
	public ResultEntity countVisitorLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
            result = mainPageService.countVisitorLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}
	
	/**
     * 根据当前登陆用户获取相应的图书馆信息。和分馆信息
     * 如果是系统管理员 ，则 可以获取全部图书馆信息。
     * @comment
     * @param request
     * @param json
     * @return
     * @data 2017年9月13日
     * @author
     */
	@RequestMapping("/querylibInfoByCurUserEXT1")
    @ResponseBody
    public ResultEntity querylibInfoByCurUserEXT1(HttpServletRequest request,String json){
        ResultEntity result = new ResultEntity();
        try {
        	Map<String,String> param = JsonUtils.fromJson(json, Map.class);
        	String operator_type = null;
        	String library_idx = null;
        	if(param.containsKey("library_idx")){
        		library_idx = param.get("library_idx");
        	}
        	if(param.containsKey("operator_type")){
        		operator_type = param.get("operator_type");
        	}
            if(OperatorType.CLOUD_ADMIN.equals(operator_type)||
					OperatorType.MAINTRINER.equals(operator_type)){
            	Map<String, String> map = new HashMap<>();
                map.put("req", json);
                String resps = regionService.querylibInfoByCurUserEXT1(map);
                result = JsonUtils.fromJson(resps, ResultEntity.class);
            	return result;
            }else{
            	List<LibraryEntity> sssss = new ArrayList<>();
        		List<LibraryEntity> libs = new ArrayList<>();
        		List<LibraryEntity> list = new ArrayList<>();
        		LibraryEntity library = new LibraryEntity();
        		library.setLibrary_idx(Integer.parseInt(library_idx));
        		libs.add(library);
        		while(true){
        			if(libs.size() == 0){
        				break;
        			}
        			list = libs;
        			libs = new ArrayList<>();
        			for(LibraryEntity lib : list){
        				Map<String, String> map = new HashMap<>();
        				param.put("library_idx", String.valueOf(lib.getLibrary_idx()));
        				map.put("req", JsonUtils.toJson(param));
        				String resps = regionService.querylibInfoByCurUserEXT1(map);
        				ResultEntity rest = JsonUtils.fromJson(resps, ResultEntity.class);
    					if(rest.getState()){
    						if("_MASTER_SLAVE_".equals(rest.getMessage())){
    							MasterLibAndSlaveLibsEntity masterLibAndSlaveLibs = JsonUtils.fromJson(JsonUtils.toJson(rest.getResult()), MasterLibAndSlaveLibsEntity.class);
                    			List<LibraryEntity> slaveLibs= masterLibAndSlaveLibs.getSlaveLibrary();
                    			if(slaveLibs != null){
                    				libs.addAll(slaveLibs);
                    				sssss.addAll(slaveLibs);
                    			}
    						}else if("_SLAVE_".equals(rest.getMessage())){
    							LibraryEntity lib_ = JsonUtils.fromJson(JsonUtils.toJson(rest.getResult()), LibraryEntity.class);
    							if(!sssss.contains(lib_)) sssss.add(lib_);
    	            		}
    					}
        			}
        		}
            	result.setState(true);
            	result.setResult(sssss);
            }
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(result, methodName, e);
        }
        return result;
    }
}
