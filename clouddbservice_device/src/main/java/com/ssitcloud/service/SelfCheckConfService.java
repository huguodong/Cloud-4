package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.SelfCheckProtocolEntity;

public interface SelfCheckConfService {

	int insertSelfCheckProtocol(SelfCheckProtocolEntity selfCheckProtocol);

	int updateSelfCheckProtocol(SelfCheckProtocolEntity selfCheckProtocol);

	int DelSelfCheckProtocol(SelfCheckProtocolEntity selfCheckProtocol);

	List<SelfCheckProtocolEntity> selectList(SelfCheckProtocolEntity selfCheckProtocol);
	
	List<SelfCheckProtocolEntity> selectListExactly(SelfCheckProtocolEntity selfCheckProtocol);

}
