package com.zuidaima.spring.service;

import java.util.List;

import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zuidaima.spring.entity.User;

@ContextConfiguration(value = { "classpath:applicationContext.xml" })
public class UserService2Test extends AbstractJUnit4SpringContextTests {

	@Autowired
	private UserService2 userService2;
	@Autowired
	private UserService userService;

	@Autowired
	private CacheManager cacheManager;

	private Cache userCache;

	@Before
	public void setUp() {
//		userCache = cacheManager.getCache("user");
	}

	@Test
	public void testCache() {
		Long id = 1L;
		User user = new User(id, "最代码", "zuidaima@gmail.com");
		userService.save(user);
		Assert.assertNotNull(userCache.get(id).get());
		userService.findById(id);
		userService.findById(id);
	}

	@Test
	public void testCache2() {
		int i = 1;
		String name="javaniu";
		printStat(""+(i++));
		Long id = 1L;
		User user = new User(id, name, "javaniu@gmail.com");
		userService2.save(user);
		printStat(" save "+(i++));

		userService2.findById(id);
		printStat(" findById1 "+(i++));
		userService2.findById(id);
		printStat(" findById2 "+(i++));
		userService2.findByUsername(name);
		printStat(" findByUsername1 "+(i++));
		userService2.findByUsername(name);
		printStat(" findByUsername2 "+(i++));
		userService2.findByUsername(name);
		printStat(" findByUsername3 "+(i++));
		userService2.findByUsername(name);
		printStat(" findByUsername4 "+(i++));

	}

	@Test
	public void testCache3() {
		Long id = 1L;
		User user = new User(id, "javaniu", "javaniu@gmail.com");
		userService2.save(user);
		userService2.findById(id);
		userService2.findById(id);
	}

	@After
	public void after() {
	}

	public void printStat(String i) {
		System.out.println("\n>>>>>>>>Start" + i + "<<<<<<<<<<<<<<<<<<<<");
		Cache cache = cacheManager.getCache("user");
		net.sf.ehcache.Cache c = (net.sf.ehcache.Cache) cache.getNativeCache();
		Statistics statistics = c.getStatistics();
		System.out.println("hits:" + statistics.getCacheHits());
		System.out.println("misses:" + statistics.getCacheMisses());
		List<Element> keys = c.getKeys();
		for (int j = 0; j < keys.size(); j++) {
			Element e = c.get(keys.get(j));
			System.out.println(e.getKey() + "->" + e.getValue());
		}
		System.out.println(">>>>>>>>Finish" + i + "<<<<<<<<<<<<<<<<<<<<\n");
	}
}
