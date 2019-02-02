package hd.service;

import hd.common.base.BaseService;
import hd.entity.Goods;
import hd.entity.GoodsWater;

/**
 * PC商品端接口
 * 
 * @author mao
 *
 */
public interface GoodsService extends BaseService<Goods> {
	/**
	 * 添加商品信息入库
	 * 
	 * @param goods
	 * @param goodsWater
	 */
	void addGoods(Goods goods, GoodsWater goodsWater);

	/**
	 * 扣库存
	 * 
	 * @param message
	 */
	void buckleWithholdAmount(String message);
}
