package hd.service;

import hd.common.base.BaseService;
import hd.entity.Order;
import hd.entity.OrderDetails;

/**
 * 订单接口
 * 
 * @author mao
 *
 */
public interface OrderService extends BaseService<Order> {

	/**
	 * 创建单商品订单
	 * 
	 * @param order
	 * @param orderDetails
	 */
	void addOrder(Order order, OrderDetails orderDetails);
}
