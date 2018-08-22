package com.ssitcloud.dblib.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.ElectronicCertificateEntity;
import com.ssitcloud.dblib.entity.page.ElectronicCertificatePageEntity;
import com.ssitcloud.dblib.service.ElectronicCertificateServiceI;

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
	

	/**
	 * 插入电子凭证
	 * @param request json = (ElectronicCertificateEntity)
	 * @return
	 */
	@RequestMapping("/addElectronicCertificate")
	@ResponseBody
	public ResultEntity addElectronicCertificate(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ElectronicCertificateEntity certificateEntity = JsonUtils.fromJson(json, ElectronicCertificateEntity.class);
				int ret = electronicCertificateService.insertElectronicCertificate(certificateEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",certificateEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多个电子凭证 
	 * @param request json = (ElectronicCertificatePageEntity)
	 * @return
	 */
	@RequestMapping("/selectElectronicCertificates")
	@ResponseBody
	public ResultEntity selectElectronicCertificates(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ElectronicCertificatePageEntity certificateEntity = JsonUtils.fromJson(json, ElectronicCertificatePageEntity.class);
				List<ElectronicCertificateEntity> ret = electronicCertificateService.selectElectronicCertificateS(certificateEntity);
				resultEntity.setValue(true, "success","",ret);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 统计符合条件的电子凭证 
	 * @param request json = (ElectronicCertificateEntity)
	 * @return
	 */
	@RequestMapping("/countElectronicCertificate")
	@ResponseBody
	public ResultEntity countElectronicCertificate(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ElectronicCertificateEntity certificateEntity = JsonUtils.fromJson(json, ElectronicCertificateEntity.class);
				int ret = electronicCertificateService.countElectronicCertificate(certificateEntity);
				resultEntity.setValue(true, "success","",ret);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 插入电子凭证，插入前先检查是否有一样的数据，如果有则不插入
	 *
	 * <p>2017年3月4日 下午4:52:48 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/addElectronicCertificateWithoutSame")
	@ResponseBody
	public ResultEntity addElectronicCertificateWithoutSame(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ElectronicCertificateEntity certificateEntity = JsonUtils.fromJson(json, ElectronicCertificateEntity.class);
				if(certificateEntity!=null){
					String time = certificateEntity.getControl_time_str();
					if (StringUtils.isNotBlank(time)) {
						Timestamp timestamp = Timestamp.valueOf(time);
						certificateEntity.setControl_time(timestamp);
					}
				}
				int count = electronicCertificateService.checkSameElectronicCertificate(certificateEntity);
				if(count>0){
					resultEntity.setValue(true, "already has the same data");
					return resultEntity;
				}
				int ret = electronicCertificateService.insertElectronicCertificate(certificateEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",certificateEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/selectByReaderIdx")
	@ResponseBody
	public ResultEntity selectByReaderIdx(HttpServletRequest request){
		String json = request.getParameter("json");
		if(json == null){
			return new ResultEntity();
		}
		Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
		return electronicCertificateService.selectByReaderIdx(map);
	}
	
	@RequestMapping("/updateState")
	@ResponseBody
	public ResultEntity updateState(HttpServletRequest request){
		String json = request.getParameter("json");
		if(json == null){
			return new ResultEntity();
		}
		Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
		return electronicCertificateService.updateState(map);
	}
}
