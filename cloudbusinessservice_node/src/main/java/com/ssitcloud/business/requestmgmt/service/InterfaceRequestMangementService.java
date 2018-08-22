package com.ssitcloud.business.requestmgmt.service;
import java.util.List;

import com.ssitcloud.request.entity.InterfaceRequestDto;


public interface InterfaceRequestMangementService {
	
	public int addRequest(String req);

	public int updateRequest(String req);

	public int  deleteRequest(String req);

	public List<InterfaceRequestDto> queryRequest(String req);
}
