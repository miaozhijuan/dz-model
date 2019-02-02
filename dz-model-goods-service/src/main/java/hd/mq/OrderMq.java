package hd.mq;

import hd.common.finals.MqUtil;
import hd.service.GoodsService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 处理订单MQ
 * 
 * @author mao
 *
 */
@Component
public class OrderMq {

	@Autowired
	private GoodsService goodsService;

	/**
	 * 预扣库存mq
	 * 
	 * @param messages
	 */
	@JmsListener(destination = MqUtil.ADD_ORDER_MQ)
	public void addOrderMq(String messages) {
		if (StringUtils.isNotBlank(messages)) {
			goodsService.buckleWithholdAmount(messages);
		}
	}
}
