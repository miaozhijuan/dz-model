package hd.service;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类接口
 * 
 * @author mao
 *
 */
public interface StringRedisTemplateService {

	/**
	 * 插入string类型数据
	 * 
	 * @param key
	 * @param value
	 */
	void set(String key, String value);

	/**
	 * 插入时间限制string类型数据
	 * 
	 * @param key
	 * @param value
	 * @param time
	 * @param timeUnit
	 */
	void set(String key, String value, long time, TimeUnit timeUnit);

	/**
	 * 根据键删除数据
	 * 
	 * @param key
	 * @return
	 */
	boolean delete(String key);

	/**
	 * 根据键获取数据
	 * 
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 根据键获取过期时间
	 * 
	 * @param key
	 * @param timeUnit
	 * @return
	 */
	long getExpire(String key, TimeUnit timeUnit);

	/**
	 * 根据键判断是否检查是否存在
	 * 
	 * @param key
	 * @return
	 */
	boolean hasKey(String key);

	/**
	 * 根据键给该值做数值操作（Double）
	 * 
	 * @param key
	 * @param num
	 * @return
	 */
	Double increment(String key, double num);

	/**
	 * 根据键给该值做数值操作（Long）
	 * 
	 * @param key
	 * @param num
	 * @return
	 */
	Long increment(String key, long num);

	/**
	 * 根据键设置过期时间
	 * 
	 * @param key
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	boolean expire(String key, long time, TimeUnit timeUnit);
}
