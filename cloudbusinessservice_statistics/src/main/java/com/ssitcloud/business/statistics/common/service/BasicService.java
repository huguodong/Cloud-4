package com.ssitcloud.business.statistics.common.service;

import com.ssitcloud.common.entity.ResultEntity;



public interface BasicService {


	ResultEntity postURL(String URL, String req);

	ResultEntity postURLLongtime(String URL, String req);

}
