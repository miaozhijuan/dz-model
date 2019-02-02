package hd.service.impl;

import java.util.concurrent.TimeUnit;

import hd.service.StringRedisTemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * redis工具类接口实现
 * 
 * @author mao
 *
 */
@Service
public class StringRedisTemplateServiceImpl implements StringRedisTemplateService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void set(String key, String value, long time, TimeUnit timeUnit) {
		stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
	}

	@Override
	public boolean delete(String key) {
		return stringRedisTemplate.delete(key);
	}

	@Override
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	@Override
	public long getExpire(String key, TimeUnit timeUnit) {
		return stringRedisTemplate.getExpire(key, timeUnit);
	}

	@Override
	public boolean hasKey(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	@Override
	public Double increment(String key, double num) {
		return stringRedisTemplate.boundValueOps(key).increment(num);
	}

	@Override
	public Long increment(String key, long num) {
		return stringRedisTemplate.boundValueOps(key).increment(num);
	}

	@Override
	public boolean expire(String key, long time, TimeUnit timeUnit) {
		return stringRedisTemplate.expire(key, time, timeUnit);
	}
}