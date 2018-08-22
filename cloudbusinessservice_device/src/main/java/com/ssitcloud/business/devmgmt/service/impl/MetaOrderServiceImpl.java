package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.MetadataOrderEntity;
import com.ssitcloud.business.devmgmt.service.MetaOrderService;
import com.ssitcloud.common.entity.ResultEntityF;

@Service
public class MetaOrderServiceImpl extends BasicServiceImpl implements MetaOrderService{

	public static final String SEL_METADATA_ORDER="SelMetadataOrder";
	@Override
	public ResultEntityF<List<MetadataOrderEntity>> queryMetaOrder(){
		String respJson=HttpClientUtil.doPost(requestURL.getRequestURL(SEL_METADATA_ORDER), new HashMap<String, String>(), charset);
		if(respJson!=null){
			return JsonUtils.fromJson(respJson, new TypeReference<ResultEntityF<List<MetadataOrderEntity>>>() {});
		}
		return new ResultEntityF<List<MetadataOrderEntity>>();
	}
}
