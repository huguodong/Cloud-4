package com.ssitcloud.datasync.service.impl;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.datasync.service.DataSyncCommand;

@Component(value="connection")
public class Connection implements DataSyncCommand {

	@Override
	public RespEntity execute(RequestEntity requestEntity) {
		RespEntity resp = new RespEntity(requestEntity);
		resp.getData().setState(true);
		return resp;
	}
	

}
