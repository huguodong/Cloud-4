package com.ssitcloud.devicelog.service;

import com.ssitcloud.common.entity.ResultEntityF;

public interface TableCommand {
	ResultEntityF<Object> execute(String req);
}
