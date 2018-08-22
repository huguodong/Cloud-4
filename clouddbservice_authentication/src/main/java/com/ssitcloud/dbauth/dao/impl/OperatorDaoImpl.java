package com.ssitcloud.dbauth.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;














import org.springframework.stereotype.Repository;

import com.ssitcloud.common.entity.SqlParam;
import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.OperatorDao;
import com.ssitcloud.dbauth.entity.OperatorAppEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.OperatorPageEntity;
import com.ssitcloud.dbauth.param.GetVaildTimeParam;
import com.ssitcloud.dbauth.param.LoginCheckParam;

/** 
 *
 * <p> 2016年3月24日 下午3:36:45 
 * @author hjc 
 *
 */
@Repository
public class OperatorDaoImpl extends CommonDaoImpl implements OperatorDao {


	@Override
	public OperatorEntity getVaildTimeByParam(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectOne("operator.getVaildTimeByParam", operatorEntity);
	}

	@Override
	public GetVaildTimeParam getVaildTime(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectOne("operator.getVaildTime",operatorEntity);
	}

	@Override
	public int addOperator(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.insert("operator.addOperator", operatorEntity);
	}

	@Override
	public int delOperatorByIdx(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.delete("operator.delOperatorByIdx", operatorEntity);
	}

	@Override
	public int updOperatorByIdx(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.update("operator.updOperatorByIdx",operatorEntity);
	}
	
	@Override
	public int updateOperatorByidx(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.update("operator.updateOperatorByidx",operatorEntity);
	}

	@Override
	public int updOperatorByParam(Map<String, Object> uParam,
			Map<String, Object> wParam) {
		SqlParam sqlParam = new SqlParam();
		sqlParam.setUpdParam(uParam);
		sqlParam.setWhereParam(wParam);
		return this.sqlSessionTemplate.update("operator.updOperatorByParam",sqlParam);
	}

	@Override
	public OperatorEntity selOperatorByOperIdOrIdx(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectOne("operator.selOperatorByOperIdOrIdx", operatorEntity);
	}

	@Override
	public LoginCheckParam getLoginCheckInfo(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectOne("operator.getLoginCheckInfo",operatorEntity);
	}

	@Override
	public int changePwd(Integer operator_idx, String pwd) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("operator_idx", operator_idx);
		param.put("operator_pwd", pwd);
		return this.sqlSessionTemplate.update("operator.changePwd",param);
	}

	@Override
	public List<OperatorEntity> selDeviceUserByLibraryIdx(String library_idx) {
		Map<String, Object> map = new HashMap<>();
		map.put("library_idx", library_idx);
		return this.sqlSessionTemplate.selectList("operator.selDeviceUserByLibraryIdx",map);
	}


	@Override
	public List<OperatorEntity> queryOperatorNameByoperIdxArr(
			List<Integer> operIdxs) {
		return this.sqlSessionTemplate.selectList("operator.queryOperatorNameByoperIdxArr",operIdxs);
	}

	@Override
	public OperatorPageEntity queryOperatorByParam(OperatorPageEntity operatorPageEntity) {
		OperatorPageEntity o = this.sqlSessionTemplate.selectOne("operator.queryOperatorByParam",operatorPageEntity);
		operatorPageEntity.setTotal(o.getTotal());
		operatorPageEntity.setDoAount(false);
		List<OperatorPageEntity> list = this.sqlSessionTemplate.selectList("operator.queryOperatorByParam",operatorPageEntity);
		operatorPageEntity.setRows(list);
		return operatorPageEntity;
	}

	@Override
	public List<Map<String, Object>> queryOperatorInfoList(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectList("operator.queryOperatorInfoList",operatorEntity);
	}

	@Override
	public List<Map<String, Object>> queryOperatorAddInfoList(String[] arr) {
		return this.sqlSessionTemplate.selectList("operator.queryOperatorAddInfoList",arr);
	}

	@Override
	public int selCountOperatorBySoxId(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectOne("operator.selCountOperatorBySoxId",operatorEntity);
	}

	@Override
	public int setOperatorActive(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.update("operator.setOperatorActive", operatorEntity);
	}
	@Override
	public int setOperatorLock(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.update("operator.setOperatorLock", operatorEntity);
	}

	@Override
	public int deleteDevOperatorInfoByOperId(String deleteDeviceId) {
		return this.sqlSessionTemplate.delete("operator.deleteDevOperatorInfoByOperId",deleteDeviceId);
	}

	@Override
	public List<Map<String, Object>> queryDeviceIps(List<String> list) {
		return this.sqlSessionTemplate.selectList("operator.queryDeviceIps",list);
	}

	@Override
	public int authTransferToLibrary (Map<String, Object> param) {
		return this.sqlSessionTemplate.update("operator.authTransferToLibrary",param);
	}

	@Override
	public int updateOperatorLockTimes(SoxTemplateEntity soxTemplateEntity) {
		return this.sqlSessionTemplate.update("operator.updateOperatorLockTimes",soxTemplateEntity);
	}

	@Override
	public int updateOperatorLogin(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.update("operator.updateOperatorLogin",operatorEntity);
	}

	@Override
	public int addOperatorFully(OperatorEntity row) {
		return sqlSessionTemplate.insert("operator.addOperatorFully", row);
	}

	@Override
	public OperatorAppEntity selectOperAppByIdOrIdx(
			OperatorAppEntity operatorAppEntity) {
		return this.sqlSessionTemplate.selectOne("operator.selectOperAppByIdOrIdx", operatorAppEntity);
	}

	@Override
	public int updateOperAppByIdx(OperatorAppEntity operatorAppEntity) {
		return this.sqlSessionTemplate.update("operator.updateOperAppByIdx",operatorAppEntity);
	}

	@Override
	public int updateOperAppPwdByIdx(OperatorAppEntity operatorAppEntity) {
		return this.sqlSessionTemplate.update("operator.updateOperAppPwdByIdx", operatorAppEntity);
	}

	@Override
	public OperatorAppEntity checkOperIdentity(OperatorAppEntity operatorAppEntity) {
		return this.sqlSessionTemplate.selectOne("operator.checkOperIdentity",operatorAppEntity);
	}

	@Override
	public OperatorAppEntity selectOperatorIdByParam(
			OperatorAppEntity operatorAppEntity) {
		return this.sqlSessionTemplate.selectOne("operator.selectOperatorIdByParam", operatorAppEntity);
	}
	
	@Override
	public List<OperatorAppEntity> selectByParam(OperatorAppEntity operatorEntity) {
		return this.sqlSessionTemplate.selectList("operator.selectByParam", operatorEntity);
	}
}
