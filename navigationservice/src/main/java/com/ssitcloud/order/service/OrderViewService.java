package com.ssitcloud.order.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.order.entity.OrderEntity;
import com.ssitcloud.order.entity.page.OrderPageEntity;

public interface OrderViewService {
	List<OrderEntity> queryAllOrder(String req);

	OrderPageEntity queryOrderByParam(String req);

	OrderEntity queryOrderById(String req);

	ResultEntity updateOrder(String req);

	ResultEntity addOrder(String req);
	
	ResultEntity getLocations(String req);
	
	ResultEntity controlShelf(String req);
	
	ResultEntity shelfReset(String req);
}
