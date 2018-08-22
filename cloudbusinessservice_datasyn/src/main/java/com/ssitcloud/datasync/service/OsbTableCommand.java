package com.ssitcloud.datasync.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface OsbTableCommand<T> {
	
	ResultEntity execute(T t) ;
}
