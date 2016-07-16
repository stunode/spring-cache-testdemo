package com.zuidaima.spring.service;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.zuidaima.spring.entity.User;

public interface UserService2 {

	@Caching(put = { @CachePut(value = "user", key = "#user.id"),
			@CachePut(value = "user", key = "#user.username"),
			@CachePut(value = "user", key = "#user.email") })
	public User save(User user);

	@Caching(put = { @CachePut(value = "user", key = "#user.id"),
			@CachePut(value = "user", key = "#user.username"),
			@CachePut(value = "user", key = "#user.email") })
	public User update(User user);

	@Caching(evict = { @CacheEvict(value = "user", key = "#user.id"),
			@CacheEvict(value = "user", key = "#user.username"),
			@CacheEvict(value = "user", key = "#user.email") })
	public User delete(User user);

	@CacheEvict(value = "user", allEntries = true)
	public void deleteAll();

	@Caching(cacheable = {
			@Cacheable(value = "user", key = "#id"),
			@Cacheable(value = "user", key = "#result.username", condition = "#result != null"),
			@Cacheable(value = "user", key = "#result.email", condition = "#result != null") })
	public User findById(final Long id);

	@Caching(cacheable = {
			@Cacheable(value = "user", key = "#username"),
			@Cacheable(value = "user", key = "#result.id", condition = "#result != null"),
			@Cacheable(value = "user", key = "#result.email", condition = "#result != null") })
	public User findByUsername(final String username);

	@Caching(cacheable = {
			@Cacheable(value = "user", key = "#email"),
			@Cacheable(value = "user", key = "#result.id", condition = "#result != null"),
			@Cacheable(value = "user", key = "#result.email", condition = "#result != null") })
	public User findByEmail(final String email);

	@Caching(evict = { @CacheEvict(value = "user", key = "#user.id", condition = "#root.target.canEvict(#root.caches[0], #user.id, #user.username)", beforeInvocation = true) })
	public void conditionUpdate(User user);

	public boolean canEvict(Cache userCache, Long id, String username);
}
