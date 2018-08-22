package com.ssitcloud.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.order.dao.OrderDao;
import com.ssitcloud.order.entity.OrderEntity;
import com.ssitcloud.order.entity.page.OrderPageEntity;

@Repository
public class OrderDaoImpl extends CommonDaoImpl implements OrderDao {

	@Override
	public List<OrderEntity> queryAllOrder(OrderEntity order) {
		return this.sqlSessionTemplate.selectList("order.queryAllOrder", order);
	}

	@Override
	public OrderPageEntity queryOrderByParam(OrderPageEntity order) {
		OrderPageEntity total = this.sqlSessionTemplate.selectOne("order.queryOrderByParam", order);
		order.setTotal(total.getTotal());
		order.setDoAount(false);
		order.setRows(this.sqlSessionTemplate.selectList("order.queryOrderByParam", order));
		return order;
	}

	@Override
	public OrderEntity queryOrderById(int idx) {
		return this.sqlSessionTemplate.selectOne("order.queryOrderById", idx);
	}

	@Override
	public int updateOrder(OrderEntity order) {
		return this.sqlSessionTemplate.update("order.updateOrder", order);
	}

	@Override
	public int addOrder(OrderEntity order) {
		return this.sqlSessionTemplate.insert("order.addOrder", order);
	}

}
