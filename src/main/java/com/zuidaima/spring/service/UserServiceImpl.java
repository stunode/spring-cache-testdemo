package com.zuidaima.spring.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zuidaima.spring.entity.User;

@Service
public class UserServiceImpl implements UserService {

	Set<User> users = new HashSet<User>();

	@CachePut(value = "user", key = "#user.id")
	public User save(User user) {
		users.add(user);
		return user;
	}

	@CachePut(value = "user", key = "#user.id")
	public User update(User user) {
		users.remove(user);
		users.add(user);
		return user;
	}

	@CacheEvict(value = "user", key = "#user.id")
	public User delete(User user) {
		users.remove(user);
		return user;
	}

	@CacheEvict(value = "user", allEntries = true)
	public void deleteAll() {
		users.clear();
	}

	@Cacheable(value = "user", key = "#id")
	public User findById(final Long id) {
		System.out.println("cache miss, invoke find by id, id:" + id);
		for (User user : users) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

}
