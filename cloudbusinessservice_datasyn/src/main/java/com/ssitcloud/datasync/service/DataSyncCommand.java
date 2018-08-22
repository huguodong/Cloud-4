package com.ssitcloud.datasync.service;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;

public interface DataSyncCommand {
	/**
	 * 接收设备客户端数据同步请求<br/>
	 * 如果有新的同步方法需要实现该接口
	 * 
	 * @methodName: execute
	 * @param request
	 * @return
	 * @returnType: RespEntity
	 * @author: liuBh
	 */
	RespEntity execute(RequestEntity requestEntity);

}
