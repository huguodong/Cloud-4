package com.ssitcloud.dblib.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.LogUtils;
import com.ssitcloud.dblib.dao.ElectronicCertificateDao;
import com.ssitcloud.dblib.entity.ElectronicCertificateEntity;
import com.ssitcloud.dblib.entity.page.ElectronicCertificatePageEntity;
import com.ssitcloud.dblib.service.ElectronicCertificateServiceI;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月28日 上午10:57:00
 */
@Service
public class ElectronicCertificateServiceImpl implements ElectronicCertificateServiceI {
	
	@Autowired
	private ElectronicCertificateDao dao;
	
	@Override
	public int insertElectronicCertificate(ElectronicCertificateEntity certificateEntity) {
		return dao.insertElectronicCertificate(certificateEntity);
	}

	@Override
	public ElectronicCertificateEntity selectElectronicCertificate(ElectronicCertificateEntity certificateEntity) {
		return dao.selectElectronicCertificate(certificateEntity);
	}

	@Override
	public List<ElectronicCertificateEntity> selectElectronicCertificateS(
			ElectronicCertificatePageEntity certificateEntity) {
		return dao.selectElectronicCertificateS(certificateEntity);
	}

	@Override
	public int countElectronicCertificate(ElectronicCertificateEntity certificateEntity) {
		return dao.countElectronicCertificate(certificateEntity);
	}

	@Override
	public int checkSameElectronicCertificate(
			ElectronicCertificateEntity certificateEntity) {
		return dao.checkSameElectronicCertificate(certificateEntity);
	}

	@Override
	public ResultEntity selectByReaderIdx(Map<String, Object> data) {
		ResultEntity result = new ResultEntity();
		try{
			if(data.get("pageSize") != null /*每页多少条*/
					&& data.get("pageCurrent") != null /*第几页*/){
				Integer ps = (Integer)data.get("pageSize");
				ps = ps>0?ps:Integer.valueOf(20);
				Integer pc = (Integer) data.get("pageCurrent");
				pc = pc>0?pc:Integer.valueOf(1);
				
				data.remove("pageCurrent");
				data.remove("pageSize");
				data.put("limit_s", (pc-1)*ps);
				data.put("limit_e", ps);
			}
			
			List<ElectronicCertificateEntity> elecList = dao.selectByReaderIdx(data);
			result.setResult(elecList);
			result.setState(true);
		}catch(Exception e){
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
			LogUtils.info(e);
		}
		return result;
	}

	@Override
	public ResultEntity updateState(Map<String, Object> data){
		ResultEntity resEntity = new ResultEntity();
		if(data.get("state") == null 
				|| data.get("id_list") == null){
			return resEntity;
		}
		try{
			int r = dao.updateElectronicState(data);
			resEntity.setState(true);
			return resEntity;
		}catch(Exception e){
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resEntity, methodName, e);
			LogUtils.info(e);
		}
		return resEntity;
	}
}
