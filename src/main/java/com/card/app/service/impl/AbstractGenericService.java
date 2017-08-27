package com.card.app.service.impl;


import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.dao.CardDao;
import com.card.app.model.User;
import com.card.app.service.GenericService;
@Transactional
public abstract class AbstractGenericService<E> implements GenericService<User>{


	@Autowired
	CardDao  cardDao;
	
	@Override
	@Transactional
	public void delete(User entity) {
		// TODO Auto-generated method stub
		cardDao.delete(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		cardDao.deleteAll();
	}
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return cardDao.findAll();
	}
	
	@Override
	public List<User> findAllByExample(User entity) {
		// TODO Auto-generated method stub
		return cardDao.findAllByExample(entity);
	}
	
	@Override
	public User findById(Serializable id) {
		// TODO Auto-generated method stub
		return cardDao.findById(id);
	}
	
	@Override
	@Transactional
	public Serializable save(User entity) {
		// TODO Auto-generated method stub
		return cardDao.save(entity);
	}

	
	
	@Override
	@Transactional
	public void saveOrUpdate(User entity) {
		// TODO Auto-generated method stub
		cardDao.saveOrUpdate(entity);
	}
}
