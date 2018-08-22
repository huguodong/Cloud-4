package com.ssitcloud.dbauth.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.IpWhiteDao;
import com.ssitcloud.dbauth.entity.IpWhiteEntity;

/**
 * <p>2016年4月5日 上午11:46:03
 * @author hjc
 *
 */
@Repository
public class IpWhiteDaoImpl extends CommonDaoImpl implements IpWhiteDao{

	@Override
	public int addIpWhite(IpWhiteEntity ipWhiteEntity) {
		return this.sqlSessionTemplate.insert("ipwhite.addIpWhite", ipWhiteEntity);
	}

	@Override
	public int delIpWhiteByOperIdx(IpWhiteEntity ipWhiteEntity) {
		return this.sqlSessionTemplate.delete("ipwhite.delIpWhiteByOperIdx", ipWhiteEntity);
	}
	
	@Override
	public IpWhiteEntity selIpWhiteByIdx(IpWhiteEntity ipWhiteEntity) {
		return this.sqlSessionTemplate.selectOne("ipwhite.selIpWhiteByIdx",ipWhiteEntity);
	}

	@Override
	public IpWhiteEntity selIpWhiteEntity(String operator_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operator_id", operator_id);
		return this.sqlSessionTemplate.selectOne("ipwhite.selIpWhiteEntity",map);
	}
	
	@Override
	public int updIpWhite(IpWhiteEntity ipWhiteEntity) {
		return this.sqlSessionTemplate.update("ipwhite.updIpWhite", ipWhiteEntity);
	}

	@Override
	public IpWhiteEntity selIpWhiteByOperatorId(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("ipwhite.selIpWhiteByOperatorId", param);
	}
	
	
}
