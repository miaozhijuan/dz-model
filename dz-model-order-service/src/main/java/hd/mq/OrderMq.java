package hd.mq;

import hd.common.finals.MqUtil;
import hd.mapper.MqMessageMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 订单MQ
 * @author mao
 *
 */
@Component
public class OrderMq {
	
	@Autowired
	private MqMessageMapper mqMessageMapper;
	
	/**
	 * 扣库存成功确认队列
	 * @param messageId
	 */
	@JmsListener(destination = MqUtil.ADD_ORDER_CHECK_MQ)
	public void addOderCheckMq(String messageId) {
		if (StringUtils.isNotBlank(messageId)) {
			mqMessageMapper.deleteByPrimaryKey(messageId);
		}
	}
}
