package hd.service.impl;

import hd.common.finals.FinalUtil;
import hd.common.finals.MqUtil;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.config.ToSearchResult;
import hd.entity.Goods;
import hd.entity.GoodsWater;
import hd.mapper.GoodsMapper;
import hd.mapper.GoodsWaterMapper;
import hd.service.GoodsService;

import java.util.Date;
import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

/**
 * PC商品端接口实现层
 * 
 * @author mao
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private GoodsWaterMapper goodsWaterMapper;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public int insertSelective(Goods record) {
		return goodsMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return goodsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Goods record) {
		return goodsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Goods selectByPrimaryKey(String id) {
		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public SearchResult<Goods> getSearchResult(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return new ToSearchResult<Goods>().getSearchResult(goodsMapper.list(scs));
	}

	@Override
	public List<Goods> list(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return goodsMapper.list(scs);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void addGoods(Goods goods, GoodsWater goodsWater) {
		goodsMapper.insertSelective(goods);
		goodsWaterMapper.insertSelective(goodsWater);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void buckleWithholdAmount(String message) {
		// 解析队列数据
		JSONObject obj = JSONObject.parseObject(message);
		String messageId = obj.getString("messageId");
		String goodsWaterId = obj.getString("goodsWaterId");
		String goodsId = obj.getString("goodsId");
		int amount = obj.getIntValue("amount");
		// 判断该数据是否已经处理
		GoodsWater goodsWater = goodsWaterMapper.selectByPrimaryKey(goodsWaterId);
		if (null == goodsWater) {
			// 添加流水
			goodsWater = new GoodsWater();
			goodsWater.setId(goodsWaterId);
			goodsWater.setGoodsId(goodsId);
			goodsWater.setAmount(amount);
			goodsWater.setStatus(FinalUtil.BYTE_0);
			goodsWater.setCreateTime(new Date());
			goodsWaterMapper.insertSelective(goodsWater);
			// 预扣库存
			goodsMapper.buckleWithholdAmount(goodsId, amount);
		}
		// 确认MQ
		jmsTemplate.convertAndSend(new ActiveMQQueue(MqUtil.ADD_ORDER_CHECK_MQ), messageId);
	}
}
