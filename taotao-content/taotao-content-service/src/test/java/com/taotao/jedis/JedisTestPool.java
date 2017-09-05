package com.taotao.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTestPool {

	@Test
	public void testJedisPool() {
		JedisPool jedisPool = new JedisPool("192.168.25.158", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.auth("admin");
		jedis.set("jedis", "test");
		System.out.println(jedis.get("jedis"));
		jedis.close();
		jedisPool.close();
	}
}
