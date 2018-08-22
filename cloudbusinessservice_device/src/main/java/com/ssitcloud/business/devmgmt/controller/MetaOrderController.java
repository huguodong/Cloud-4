package com.ssitcloud.business.devmgmt.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.devmgmt.entity.MetadataOrderEntity;
import com.ssitcloud.business.devmgmt.service.MetaOrderService;
import com.ssitcloud.common.entity.ResultEntityF;

@Controller
@RequestMapping(value={"metaorder"})
public class MetaOrderController {

	@Resource
	private MetaOrderService metaOrderService;
	/**
	 * 查询metadata_order所有数据
	 * @methodName: SelMetadataOrder
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntityF<List<MetadataOrderEntity>>
	 * @author: liuBh
	 */
	@RequestMapping(value="SelMetadataOrder")
	@ResponseBody
	public ResultEntityF<List<MetadataOrderEntity>> SelMetadataOrder(HttpServletRequest request,String req){
		ResultEntityF<List<MetadataOrderEntity>> result =new ResultEntityF<>();
		try {
			return metaOrderService.queryMetaOrder();
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
