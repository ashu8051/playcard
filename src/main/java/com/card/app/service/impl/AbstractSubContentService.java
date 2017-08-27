package com.card.app.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.dao.SubContentDao;
import com.card.app.model.SubContent;
import com.card.app.model.TopicContent;
import com.card.app.service.GenericService;

public abstract class AbstractSubContentService<E> implements GenericService<SubContent> {

	@Autowired
	SubContentDao subContentDao;
	
	@Override
	@Transactional
	public void delete(SubContent entity) {
		// TODO Auto-generated method stub
		subContentDao.delete(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		subContentDao.deleteAll();
	}
	
	@Override
	public List<SubContent> findAll() {
		// TODO Auto-generated method stub
		return subContentDao.findAll();
	}
	
	@Override
	public List<SubContent> findAllByExample(SubContent entity) {
		// TODO Auto-generated method stub
		return subContentDao.findAllByExample(entity);
	}
	
	@Override
	public SubContent findById(Serializable id) {
		// TODO Auto-generated method stub
		return subContentDao.findById(id);
	}
	
	@Override
	@Transactional
	public Serializable save(SubContent entity) {
		// TODO Auto-generated method stub
		return subContentDao.save(entity);
	}

	
	
	@Override
	@Transactional
	public void saveOrUpdate(SubContent entity) {
		// TODO Auto-generated method stub
		subContentDao.saveOrUpdate(entity);
	}

	
}
