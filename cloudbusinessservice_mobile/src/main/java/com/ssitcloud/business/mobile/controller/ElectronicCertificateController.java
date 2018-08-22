package com.ssitcloud.business.mobile.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.service.ElectronicCertificateServiceI;
import com.ssitcloud.common.entity.ResultEntity;

import net.sf.json.util.JSONUtils;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月28日 上午10:57:11
 */
@Controller
@RequestMapping("/electronicCertificate")
public class ElectronicCertificateController {
	
	@Autowired
	private ElectronicCertificateServiceI electronicCertificateService;
//	
//	/**
//	 * 查询多个电子凭证 
//	 * @param request json = (ElectronicCertificatePageEntity)
//	 * @return
//	 */
//	@RequestMapping("/selectElectronicCertificates")
//	@ResponseBody
//	public ResultEntity selectElectronicCertificates(HttpServletRequest request){
//		String json = request.getParameter("json");
//		return electronicCertificateService.selectElectronicCertificateS(json);
//	}
//	
//	/**
//	 * 统计符合条件的电子凭证 
//	 * @param request json = (ElectronicCertificateEntity)
//	 * @return
//	 */
//	@RequestMapping("/countElectronicCertificate")
//	@ResponseBody
//	public ResultEntity countElectronicCertificate(HttpServletRequest request){
//		String json = request.getParameter("json");
//		return electronicCertificateService.countElectronicCertificate(json); 
//	}
	
	/**
	 * 根据读者id和其他条件查询电子凭证
	 * 例如order和control_time
	 * control_time条件为control_time>{you submit control_time}
	 * order == 0 desc
	 * order == 1 asc
	 * @return
	 */
	@RequestMapping("/selectByReaderIdx")
	@ResponseBody
	public ResultEntity selectByReaderIdx(HttpServletRequest request){
		String json = request.getParameter("json");
		if(!JSONUtils.mayBeJSON(json)){
			return new ResultEntity();
		}
		Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
		return electronicCertificateService.selectByReaderIdx(map);
	}
	
	@RequestMapping("/setRead")
	@ResponseBody
	public ResultEntity setRead(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if(json != null){
			try{
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				List<Integer> ids = (List<Integer>) map.get("ids");
				return electronicCertificateService.setReaderElectronicCertificate(ids, 1);
			}catch(Exception e){
				resultEntity.setMessage("submit args is error");
			}
		}
		return resultEntity;
	}
	
	@RequestMapping("/getRecommendList")
	@ResponseBody
	public ResultEntity getRecommendList(HttpServletRequest request){
		String json = request.getParameter("json");
		if(!JSONUtils.mayBeJSON(json)){
			return new ResultEntity();
		}
		Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
		return electronicCertificateService.getRecommendList(map);
	}
}
