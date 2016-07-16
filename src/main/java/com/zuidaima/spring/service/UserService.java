package com.zuidaima.spring.service;

import com.zuidaima.spring.entity.User;

public interface UserService {

	public User save(User user);

	public User update(User user);

	public User delete(User user);

	public void deleteAll();

	public User findById(final Long id);

}
