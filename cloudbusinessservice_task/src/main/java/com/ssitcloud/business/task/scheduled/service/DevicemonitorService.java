package com.ssitcloud.business.task.scheduled.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DevicemonitorService {
	ResultEntity queryLoanLog(String req);
	ResultEntity queryFinLog(String req);
	ResultEntity queryCardLog(String req);
}
