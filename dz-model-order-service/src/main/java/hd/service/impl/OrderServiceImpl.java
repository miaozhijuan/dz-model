package hd.service.impl;

import hd.common.finals.MqUtil;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.common.tools.IdUtil;
import hd.config.ToSearchResult;
import hd.entity.MqMessage;
import hd.entity.Order;
import hd.entity.OrderDetails;
import hd.mapper.MqMessageMapper;
import hd.mapper.OrderDetailsMapper;
import hd.mapper.OrderMapper;
import hd.service.OrderService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

/**
 * 订单接口实现
 * 
 * @author mao
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderDetailsMapper orderDetailsMapper;

	@Autowired
	private MqMessageMapper mqMessageMapper;

	@Override
	public int insertSelective(Order record) {
		return orderMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Order selectByPrimaryKey(String id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public SearchResult<Order> getSearchResult(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return new ToSearchResult<Order>().getSearchResult(orderMapper.list(scs));
	}

	@Override
	public List<Order> list(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return orderMapper.list(scs);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void addOrder(Order order, OrderDetails orderDetails) {
		// 保存订单
		orderMapper.insertSelective(order);
		orderDetailsMapper.insertSelective(orderDetails);
		// 发送mq扣库存
		String messageId = IdUtil.getUUID();
		Map<String, Object> senMap = new HashMap<String, Object>(4);
		senMap.put("messageId", messageId);
		senMap.put("goodsWaterId", IdUtil.getUUID());
		senMap.put("goodsId", orderDetails.getGoodsId());
		senMap.put("amount", orderDetails.getAmount());
		String body = JSONObject.toJSONString(senMap);
		// mq信息表
		MqMessage mqMessage = new MqMessage();
		mqMessage.setId(messageId);
		mqMessage.setMqName(MqUtil.ADD_ORDER_MQ);
		mqMessage.setBody(body);
		mqMessage.setCreateTime(new Date());
		mqMessageMapper.insertSelective(mqMessage);
		// 发送扣库存消息
		jmsTemplate.convertAndSend(new ActiveMQQueue(MqUtil.ADD_ORDER_MQ), body);
	}
}
