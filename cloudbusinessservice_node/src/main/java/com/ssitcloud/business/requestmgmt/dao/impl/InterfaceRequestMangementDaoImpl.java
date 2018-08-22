package com.ssitcloud.business.requestmgmt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.requestmgmt.dao.InterfaceRequestMangementDao;
import com.ssitcloud.request.entity.InterfaceRequestDto;

@Repository
public class InterfaceRequestMangementDaoImpl extends CommonDaoImpl implements InterfaceRequestMangementDao {

	@Override
	public int addRequest(InterfaceRequestDto param) {
		return this.sqlSessionTemplate.insert("interfaceRequest.addRequest", param);
		
	}


	@Override
	public int updateRequest(InterfaceRequestDto param) {
		return this.sqlSessionTemplate.update("interfaceRequest.updateRequest", param);
	}


	@Override
	public int deleteRequest(InterfaceRequestDto param) {
		return this.sqlSessionTemplate.delete("interfaceRequest.deleteRequest", param);
	}


	@Override
	public List<InterfaceRequestDto> queryRequest(
			InterfaceRequestDto param) {
		return this.sqlSessionTemplate.selectList("interfaceRequest.queryRequest", param);
	}
}
