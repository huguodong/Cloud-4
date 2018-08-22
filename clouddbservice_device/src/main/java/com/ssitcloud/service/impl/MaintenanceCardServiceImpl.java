package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.MaintenanceCardDao;
import com.ssitcloud.dao.OperatorMaintenanceInfoDao;
import com.ssitcloud.entity.MaintenanceCardEntity;
import com.ssitcloud.entity.OperatorMaintenanceInfoEntity;
import com.ssitcloud.entity.page.MaintenanceCardPageEntity;
import com.ssitcloud.service.MaintenanceCardService;

@Service
public class MaintenanceCardServiceImpl implements MaintenanceCardService{

	@Resource
	private MaintenanceCardDao maintenanceCardDao;
	
	@Resource
	OperatorMaintenanceInfoDao maintenanceInfoDao;

	@Override
	public int countCardByCardIdAndLibIdx(
			MaintenanceCardEntity maintenanceCardEntity) {
		return maintenanceCardDao.countCardByCardIdAndLibIdx(maintenanceCardEntity);
	}

	@Override
	public int insertMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity) {
		return maintenanceCardDao.insertMaintenanceCard(maintenanceCardEntity);
	}
	
	@Override
	public int updateMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity) {
		return maintenanceCardDao.updateMaintenanceCard(maintenanceCardEntity);
	}
	@Override
	public int deleteMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity) {
		return maintenanceCardDao.deleteMaintenanceCard(maintenanceCardEntity);
	}
	
	@Override
	public MaintenanceCardPageEntity queryMaintenanceCardByFuzzy(MaintenanceCardPageEntity pageEntity) {
		MaintenanceCardPageEntity maintenanceCardPageEntity = new MaintenanceCardPageEntity();
		maintenanceCardPageEntity = maintenanceCardDao.queryMaintenanceCardByFuzzy(pageEntity);
		return maintenanceCardPageEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryMaintenanceCard(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			Map<String , Object> map = JsonUtils.fromJson(req, Map.class);
			if(map!=null && map.get("library_idx")!=null){
				List<MaintenanceCardEntity> list = maintenanceCardDao.selMaintenaceCard(map);
				resultEntity.setValue(true, "", "", list);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity updateOperatorMaintenanceCard(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			Map<String, Object> operator = (Map<String, Object>) map.get("operator");
			if (operator==null) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			
			if(operator.get("maintenanceCard")!=null && operator.get("operator_idx")!=null
					&& !operator.get("maintenanceCard").toString().equals("") 
					&& !operator.get("operator_idx").toString().equals("")){
				String maintenanceCard = operator.get("maintenanceCard").toString();
				String operator_idx = operator.get("operator_idx").toString();
				
				OperatorMaintenanceInfoEntity maintenanceInfoEntity = new OperatorMaintenanceInfoEntity();
				maintenanceInfoEntity.setMaintenance_idx(Integer.valueOf(maintenanceCard));
				maintenanceInfoEntity.setOperator_idx(Integer.valueOf(operator_idx));
				
				maintenanceInfoDao.deleteMaintenanceByOperatorIdx(maintenanceInfoEntity);
				maintenanceInfoDao.addMaintenance(maintenanceInfoEntity);
				
				resultEntity.setValue(true, "success");
			}else{
				resultEntity.setValue(false, "参数不足！");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	
}
