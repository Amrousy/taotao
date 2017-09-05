package com.taotao.jedis;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {

	@Before
	public void setValue() {
		Jedis jedis = new Jedis("192.168.25.158", 6379);
		jedis.auth("admin");
		jedis.set("hello", "world");
		jedis.close();
	}

	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("192.168.25.158", 6379);
		jedis.auth("admin");
		System.out.println(jedis.get("hello"));
		jedis.close();
	}
}
