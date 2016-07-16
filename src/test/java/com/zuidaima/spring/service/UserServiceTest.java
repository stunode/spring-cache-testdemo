package com.zuidaima.spring.service;

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
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private UserService userService;

	@Autowired
	private CacheManager cacheManager;

	private Cache userCache;

	@Before
	public void setUp() {
		userCache = cacheManager.getCache("user");
	}

	@Test
	public void testCache() {
		Long id = 1L;
		User user = new User(id, "zhang", "zhang@gmail.com");
		userService.save(user);
		Assert.assertNotNull(userCache.get(id).get());
		userService.findById(id);
		userService.findById(id);
	}
}
