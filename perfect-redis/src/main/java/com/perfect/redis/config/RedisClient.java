package com.perfect.redis.config; /**
 * 所属产品: 互联网供应链金融平台
 * 版权信息: © 中金财金服
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ***************************************************************************
 *创建时间 : 2016年12月21日
 *实现功能 : Redis缓存辅助类
 *作者 : yanping.shi
 *版本 : v0.0.1
-----------------------------------------------------------------------------
 *修改记录:
 *日 期 版本 修改人 修改内容
 *2016年12月21日 v0.0.1 yanping.shi 创建
 ****************************************************************************
 */
@Component
public class RedisClient {
	private Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	/** 
	 * 批量删除对应的value 
	 *  
	 * @param keys 
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/** 
	 * 批量删除key 
	 *  
	 * @param pattern 
	 */
	public void removePattern(final String pattern) {
		Set<Object> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/** 
	 * 删除对应的value 
	 *  
	 * @param key 
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/** 
	 * 判断缓存中是否有对应的value 
	 *  
	 * @param key 
	 * @return 
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/** 
	 * 读取缓存 
	 *  
	 * @param key 
	 * @return 
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/** 
	 * 写入缓存 
	 *  
	 * @param key 
	 * @param value 
	 * @return 
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	/** 
	 * 写入缓存 
	 *  
	 * @param key 
	 * @param value 
	 * @return 
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

}
