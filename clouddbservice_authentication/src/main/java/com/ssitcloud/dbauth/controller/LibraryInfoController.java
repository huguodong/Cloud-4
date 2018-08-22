package com.ssitcloud.dbauth.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.common.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.LibraryInfoEntity;
import com.ssitcloud.dbauth.service.LibraryInfoService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * 图书馆信息处理类
 * <p>2016年4月5日 上午11:18:30
 * @author hjc
 *
 */
@Controller
@RequestMapping("/library")
public class LibraryInfoController {
	@Resource
	private LibraryInfoService libraryInfoService;
	
	/**
	 * 新增一条图书馆参数信息，如电话，地址之类
	 * 
	 * 注：
	 * 	1.如果管类型是 子馆，需要判断 子馆数是否超过限定的子馆数限制。
	 * 
	 * 
	 * <p>2016年4月5日 下午5:51:03
	 * <p>create by hjc
	 * @param libInfo 一条数据参数，如{"library_idx":"1",infotype_idx:"1",info_value:"phone"}
	 * @param request
	 * @return 结果集 ResultEntity 
	 */
	@RequestMapping("/addLibraryInfo")
	@ResponseBody
	public ResultEntity addLibraryInfo(String libInfo, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		LibraryInfoEntity infoEntity = new LibraryInfoEntity();
		try {
			infoEntity = mapper.readValue(libInfo, LibraryInfoEntity.class);
			int ret = libraryInfoService.addLibraryInfo(infoEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success", "", infoEntity);
			}else {
				resultEntity.setValue(false, "failed");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增图书馆参数列表
	 *
	 * <p>2016年4月21日 下午4:22:28
	 * <p>create by hjc
	 * @param libInfo 一个图书馆参数list
	 * 如
	 * {
	 * 	{"library_idx":"1",infotype_idx:"1",info_value:"123456"}，
	 * 	{"library_idx":"1",infotype_idx:"2",info_value:"golden street"}，
	 * 	{"library_idx":"1",infotype_idx:"3",info_value:"mark mark"}
	 * }
	 * @param request
	 * @return
	 */
	@RequestMapping("/addLibraryInfoList")
	@ResponseBody
	public ResultEntity addLibraryInfoList(String libInfo, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		List<LibraryInfoEntity> list = null;
		try {
			list = JsonUtils.fromJson(libInfo, new TypeReference<List<LibraryInfoEntity>>() {});
			Boolean b = libraryInfoService.addLibraryInfoList(list);
			if (b) {
				resultEntity.setValue(true, "success", "", list);
			}else {
				resultEntity.setValue(false, "failed");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}

    /**
     * 查询图书馆LibraryInfoEntity
     * @param req LibraryInfoEntity，可以不传
     * @return
     */
    @RequestMapping("/selectLibraryInfoByParam")
    @ResponseBody
    public ResultEntity selectLibraryInfoByParam(String req){
        try {
            LibraryInfoEntity param;
            if(!StringUtil.isEmpty(req)){
                param = JsonUtils.fromJson(req,LibraryInfoEntity.class);
            }else{
                param = new LibraryInfoEntity();
            }
            List<LibraryInfoEntity> libraryInfoEntities = libraryInfoService.selectByParam(param);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setState(true);
            resultEntity.setResult(libraryInfoEntities);
            return resultEntity;
        } catch (Exception e) {
            ResultEntity resultEntity = new ResultEntity();
            //获取当前方法名称
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
            LogUtils.error(methodName+"()异常", e);
            return resultEntity;
        }
    }
}
