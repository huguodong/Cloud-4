package com.ssitcloud.view.devmgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.devmgmt.service.RecommendService;

@Controller
@RequestMapping(value = { "recommend" })
public class RecommendController {
	@Resource
	RecommendService recommendService;
	
	/**
	 * 跳到设置页面
	 */
	@RequestMapping("main")
	public String configDeviceView(){
		return "/page/deviceservice/recommend";
	}

	/**
	 * 编辑
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/editRankRole")
	@ResponseBody
	public ResultEntity editRankRole(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.editRankRole(map);
	}

	/**
	 * 删除
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteRankRole")
	@ResponseBody
	public ResultEntity deleteRankRole(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.deleteRankRole(map);
	}

	/**
	 * 根据id查询
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/findRankRoleByIdx")
	@ResponseBody
	public ResultEntity findRankRoleByIdx(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.findRankRoleByIdx(map);
	}

	/**
	 * 查询列表
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/findRankRoleList")
	@ResponseBody
	public ResultEntity findRankRoleList(HttpServletRequest request, String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		return recommendService.findRankRoleList(map);
	}
}
