package com.taotao.jedis;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.jedis.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterTest {

	@Test
	public void testJedisCluster() throws IOException {
		HashSet<HostAndPort> set = new HashSet<>();
		set.add(new HostAndPort("192.168.25.158", 7001));
		set.add(new HostAndPort("192.168.25.158", 7002));
		set.add(new HostAndPort("192.168.25.158", 7003));
		set.add(new HostAndPort("192.168.25.158", 7004));
		set.add(new HostAndPort("192.168.25.158", 7005));
		set.add(new HostAndPort("192.168.25.158", 7006));
		JedisCluster cluster = new JedisCluster(set);

		cluster.set("hello", "100");
		System.out.println(cluster.get("hello"));
		cluster.close();
	}

	@Test
	public void testJedisClient() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = context.getBean(JedisClient.class);
		jedisClient.set("ceshi", "测试");
		System.out.println(jedisClient.get("ceshi"));
		context.close();
	}
}
