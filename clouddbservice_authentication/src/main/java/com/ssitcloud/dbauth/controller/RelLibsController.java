package com.ssitcloud.dbauth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.RelLibsEntity;
import com.ssitcloud.dbauth.service.RelLibsService;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * <p>2016年4月5日 下午1:53:11
 * @author hjc
 *
 */
@Controller
@RequestMapping("/rellibs")
public class RelLibsController {
	@Resource
	private RelLibsService relLibsService;
	@RequestMapping("/selectRelLibsByidx")
	@ResponseBody
	public ResultEntity selectRelLibsByidx(HttpServletRequest request,String json) {
		ResultEntity resultEntity = new ResultEntity();
		if(StringUtils.isBlank(json)){//传过来的json为空时，则取req里的数据
			json = request.getParameter("req");
			if(StringUtils.isBlank(json)){
				resultEntity.setValue(false, "查询子馆时，传的参数为空");
				return resultEntity;
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(!jsonObject.isEmpty()){
			Integer lib_idx = 0;
			if(jsonObject.get("library_idx")!=null){
				lib_idx = jsonObject.getInt("library_idx");
			}
			try{
				List<RelLibsEntity> relLibsEntitys = relLibsService.selectRelLibsByidx(lib_idx);
				if(!relLibsEntitys.isEmpty()){
					resultEntity.setState(true);
					resultEntity.setResult(relLibsEntitys);
				}
			}catch(Exception e){
				LogUtils.error("查找子馆数据时出错", e);
				resultEntity.setValue(false, "查找子馆数据时出错");
				return resultEntity;
			}
		}else{
			resultEntity.setValue(false, "查询子馆时，传的参数为空");
		}
		
		return resultEntity;
	}

	@RequestMapping(value = {"/selectByIdx"})
    @ResponseBody
    public ResultEntity selectByIdx(HttpServletRequest request,String req){
        ResultEntity resultEntity = new ResultEntity();
        JSONObject jsonObject = JSONObject.fromObject(req);
        RelLibsEntity re = new RelLibsEntity();
        if(!jsonObject.isEmpty()) {
            if (jsonObject.get("library_idx") != null) {
                Integer lib_idx = jsonObject.getInt("library_idx");
                re.setMaster_lib_id(lib_idx);
            }
        }
        try{
            List<RelLibsEntity> relLibsEntitys = relLibsService.selectByIdx(re);
            if(!relLibsEntitys.isEmpty()){
                resultEntity.setState(true);
                resultEntity.setResult(relLibsEntitys);
            }
        }catch(Exception e){
            LogUtils.error("查找子馆数据时出错", e);
            resultEntity.setValue(false, "查找子馆数据时出错");
            return resultEntity;
        }
        return resultEntity;
    }
	
	@RequestMapping("/selmasterLibsByIdx")
	@ResponseBody
	public ResultEntity selmasterLibsByIdx(HttpServletRequest request,String json) {
		ResultEntity resultEntity = new ResultEntity();
		if(StringUtils.isBlank(json)){//传过来的json为空时，则取req里的数据
			json = request.getParameter("req");
			if(StringUtils.isBlank(json)){
				resultEntity.setValue(false, "查询子馆时，传的参数为空");
				return resultEntity;
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(!jsonObject.isEmpty()){
			String libIdxsStr = jsonObject.optString("library_idx");
			List<String> lib_idx = new ArrayList<>();
			if (libIdxsStr!=null && !libIdxsStr.equals("")) {
				String[] idxs = libIdxsStr.split(",");
				for (int i = 0; i < idxs.length; i++) {
					lib_idx.add(idxs[i]);
				}
			}
			Map<String,Object> map = new HashMap<>();
			map.put("lib_idx", lib_idx);
			try{
				List<RelLibsEntity> relLibsEntitys = relLibsService.selmasterLibsByIdx(map);
				if(!relLibsEntitys.isEmpty()){
					resultEntity.setState(true);
					resultEntity.setResult(relLibsEntitys);
				}
			}catch(Exception e){
				LogUtils.error("查找子馆数据时出错", e);
				resultEntity.setValue(false, "查找子馆数据时出错");
				return resultEntity;
			}
		}else{
			resultEntity.setValue(false, "查询子馆时，传的参数为空");
		}
		
		return resultEntity;
	}
}
