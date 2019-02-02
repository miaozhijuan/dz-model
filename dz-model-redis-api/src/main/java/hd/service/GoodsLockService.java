package hd.service;

/**
 * 商品扣库存锁接口
 * 
 * @author mao
 *
 */
public interface GoodsLockService {

	/**
	 * 获取锁并开锁
	 */
	void openLock();

	/**
	 * 释放锁
	 * 
	 * @return
	 */
	boolean releaseLock();
}