package com.card.app.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.dao.TopicDao;
import com.card.app.model.Permission;
import com.card.app.model.Topic;
import com.card.app.service.GenericService;

@Transactional
public abstract class AbstractTopicService<E> implements GenericService<Topic> {

	@Autowired
	TopicDao  topicDao;
	
	@Override
	@Transactional
	public void delete(Topic entity) {
		// TODO Auto-generated method stub
		topicDao.delete(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		topicDao.deleteAll();
	}
	
	@Override
	public List<Topic> findAll() {
		// TODO Auto-generated method stub
		return topicDao.findAll();
	}
	
	@Override
	public List<Topic> findAllByExample(Topic entity) {
		// TODO Auto-generated method stub
		return topicDao.findAllByExample(entity);
	}
	
	@Override
	public Topic findById(Serializable id) {
		// TODO Auto-generated method stub
		return topicDao.findById(id);
	}
	
	@Override
	@Transactional
	public Serializable save(Topic entity) {
		// TODO Auto-generated method stub
		return topicDao.save(entity);
	}

	
	
	@Override
	@Transactional
	public void saveOrUpdate(Topic entity) {
		// TODO Auto-generated method stub
		topicDao.saveOrUpdate(entity);
	}

}
