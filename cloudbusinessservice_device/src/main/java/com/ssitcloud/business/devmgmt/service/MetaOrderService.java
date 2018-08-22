package com.ssitcloud.business.devmgmt.service;

import java.util.List;

import com.ssitcloud.devmgmt.entity.MetadataOrderEntity;
import com.ssitcloud.common.entity.ResultEntityF;

public interface MetaOrderService {

	ResultEntityF<List<MetadataOrderEntity>> queryMetaOrder();

}
