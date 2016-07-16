package com.zuidaima.spring.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import com.zuidaima.spring.entity.User;

@Service
public class UserService2Impl implements UserService2 {

	Set<User> users = new HashSet<User>();

	public User save(User user) {
		users.add(user);
		return user;
	}

	public User update(User user) {
		users.remove(user);
		users.add(user);
		return user;
	}

	public User delete(User user) {
		users.remove(user);
		return user;
	}

	public void deleteAll() {
		users.clear();
	}

	public User findById(final Long id) {
		System.out.println("cache miss, invoke find by id, id:" + id);
		for (User user : users) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	public User findByUsername(final String username) {
		System.out.println("cache miss, invoke find by username, username:"
				+ username);
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public User findByEmail(final String email) {
		System.out.println("cache miss, invoke find by email, email:" + email);
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	public void conditionUpdate(User user) {
		users.remove(user);
		users.add(user);
	}

	public boolean canEvict(Cache userCache, Long id, String username) {
		User cacheUser = userCache.get(id, User.class);
		if (cacheUser == null) {
			return false;
		}
		return !cacheUser.getUsername().equals(username);
	}

}
