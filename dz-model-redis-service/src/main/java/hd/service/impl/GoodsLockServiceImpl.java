package hd.service.impl;

import hd.lock.GoodsLock;
import hd.service.GoodsLockService;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 商品扣库存锁实现
 * 
 * @author mao
 *
 */
@Service
public class GoodsLockServiceImpl implements GoodsLockService {

	@Autowired
	private GoodsLock goodsLock;

	@Override
	public void openLock() {
		goodsLock.openLock();
	}

	@Override
	public boolean releaseLock() {
		return goodsLock.releaseLock();
	}
}