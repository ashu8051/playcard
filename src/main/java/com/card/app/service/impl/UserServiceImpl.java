package com.card.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.card.app.dao.UserDao;
import com.card.app.model.User;
import com.card.app.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends AbstractGenericService<User> implements UserService{

	@Autowired
	UserDao  userDao;
	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return userDao.findByName(name);
	}

}
