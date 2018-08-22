package com.ssitcloud.business.requestmgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.requestmgmt.dao.InterfaceRequestMangementDao;
import com.ssitcloud.business.requestmgmt.service.InterfaceRequestMangementService;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.request.entity.InterfaceRequestDto;

@Service
public class InterfaceRequestMangementServiceImpl implements InterfaceRequestMangementService {

	@Autowired
	private InterfaceRequestMangementDao interfaceRequestMangementDao;
	
	@Override
	public int  addRequest(String req) {
		
		int count = 0;
		
		InterfaceRequestDto interfaceRequestDTO = JsonUtils.fromJson(req, InterfaceRequestDto.class);
		
		if(StringUtils.isEmpty(interfaceRequestDTO.getRequestId())){
			return count;
		}
		List<InterfaceRequestDto> requests = interfaceRequestMangementDao.queryRequest(interfaceRequestDTO);
		
		if(CollectionUtils.isEmpty(requests)){
			
			count = interfaceRequestMangementDao.addRequest(interfaceRequestDTO);
			
		}else{
			
			count =interfaceRequestMangementDao.updateRequest(interfaceRequestDTO);
		}
		
		return count;
	}

	@Override
	public int  updateRequest(String req) {
		InterfaceRequestDto interfaceRequestDTO = JsonUtils.fromJson(req, InterfaceRequestDto.class);
		return interfaceRequestMangementDao.updateRequest(interfaceRequestDTO);
	}

	@Override
	public int  deleteRequest(String req) {
		InterfaceRequestDto interfaceRequestDTO = JsonUtils.fromJson(req, InterfaceRequestDto.class);
		return interfaceRequestMangementDao.deleteRequest(interfaceRequestDTO);
	}

	@Override
	public List<InterfaceRequestDto> queryRequest(String req) {
		InterfaceRequestDto interfaceRequestDTO = JsonUtils.fromJson(req, InterfaceRequestDto.class);
		
		return interfaceRequestMangementDao.queryRequest(interfaceRequestDTO);
	}
	
}
