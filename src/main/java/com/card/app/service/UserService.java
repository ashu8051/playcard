package com.card.app.service;

import com.card.app.model.User;

public interface UserService extends GenericService<User>{

	public User findByName(String name);
}
