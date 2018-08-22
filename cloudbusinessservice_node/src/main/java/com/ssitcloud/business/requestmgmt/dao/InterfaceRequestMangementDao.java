package com.ssitcloud.business.requestmgmt.dao;

import java.util.List;

import com.ssitcloud.request.entity.InterfaceRequestDto;


public interface InterfaceRequestMangementDao {
	
	public int  addRequest(InterfaceRequestDto entity);

	public int updateRequest(InterfaceRequestDto interfacePageEntity);

	public int  deleteRequest(InterfaceRequestDto interfacePageEntity);

	public List<InterfaceRequestDto> queryRequest(InterfaceRequestDto interfacePageEntity);
	
}
