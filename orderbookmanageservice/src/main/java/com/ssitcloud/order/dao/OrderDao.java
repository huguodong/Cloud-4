package com.ssitcloud.order.dao;

import java.util.List;

import com.ssitcloud.order.entity.OrderEntity;
import com.ssitcloud.order.entity.page.OrderPageEntity;

public interface OrderDao {
	public abstract List<OrderEntity> queryAllOrder(OrderEntity order);

	public abstract OrderPageEntity queryOrderByParam(OrderPageEntity order);

	public abstract OrderEntity queryOrderById(int idx);

	public abstract int updateOrder(OrderEntity order);

	public abstract int addOrder(OrderEntity order);
}