package com.ssitcloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.OperatorMaintenanceInfoDao;
import com.ssitcloud.dao.ReaderTypeDao;
import com.ssitcloud.entity.OperatorMaintenanceInfoEntity;
import com.ssitcloud.entity.ReaderTypeEntity;
import com.ssitcloud.service.ReaderTypeService;


@Service
public class ReaderTypeServiceImpl implements ReaderTypeService {

	@Resource
	ReaderTypeDao readerTypeDao;
	
	@Resource
	OperatorMaintenanceInfoDao maintenanceInfoDao;
	
	@Override
	public int insertReaderType(ReaderTypeEntity readerTypeEntity) {
		// TODO Auto-generated method stub
		return readerTypeDao.insert(readerTypeEntity);
	}

	@Override
	public int deleteReaderType(ReaderTypeEntity readerTypeEntity) {
		// TODO Auto-generated method stub
		return readerTypeDao.delete(readerTypeEntity);
	}

	@Override
	public int updateReaderType(ReaderTypeEntity readerTypeEntity) {
		// TODO Auto-generated method stub
		return readerTypeDao.update(readerTypeEntity);
	}

	@Override
	public List<ReaderTypeEntity> selectReaderType(ReaderTypeEntity readerTypeEntity) {
		// TODO Auto-generated method stub
		return readerTypeDao.select(readerTypeEntity);
	}

	@Override
	public PageEntity selectReaderTypeByPage(Map<String, String> map) {
		// TODO Auto-generated method stub
		return readerTypeDao.selectByPage(map);
	}

	@Override
	public PageEntity selectReaderTypeByFuzzy(Map<String, String> map) {
		// TODO Auto-generated method stub
		return readerTypeDao.selectByFuzzy(map);
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
				List<ReaderTypeEntity> list = readerTypeDao.selMaintenaceCard(map);
				resultEntity.setValue(true, "", "", list);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryOperatorMaintenanceCard(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			Map<String , Object> map = JsonUtils.fromJson(req, Map.class);
			if(map!=null && map.get("idx")!=null){
				String operator_idx = map.get("idx").toString();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("operator_idx", operator_idx);
				OperatorMaintenanceInfoEntity infoEntity = maintenanceInfoDao.queryOperatorMaintenanceCard(param);
				resultEntity.setValue(true, "", "", infoEntity);
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

	@Override
	public List<ReaderTypeEntity> selectReaderTypeByTypeId(
			ReaderTypeEntity readerType) {
		return readerTypeDao.selectReaderTypeByTypeId(readerType);
	}
	
	@Override
	public List<ReaderTypeEntity> selectCardByTypeId(
			ReaderTypeEntity readerType) {
		return readerTypeDao.selectCardByTypeId(readerType);
	}

	@Override
	public List<ReaderTypeEntity> selectCardByTypeIdAndLibIdx(
			ReaderTypeEntity readerType) {
		return readerTypeDao.selectCardByTypeIdAndLibIdx(readerType);
	}
	@Override
	public List<ReaderTypeEntity> selectReaderTypeByTypeIdAndLibIdx(
			ReaderTypeEntity readerType) {
		return readerTypeDao.selectReaderTypeByTypeIdAndLibIdx(readerType);
	}
	
	@Override
	public List<ReaderTypeEntity> selectByLibIdx(
			ReaderTypeEntity readerType) {
		return readerTypeDao.selectByLibIdx(readerType);
	}
	
	
}
