package com.card.app.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.dao.TopicContentDao;
import com.card.app.model.Permission;
import com.card.app.model.Topic;
import com.card.app.model.TopicContent;
import com.card.app.service.GenericService;

@Transactional
public abstract class AbstractTopicContentService<E> implements GenericService<TopicContent> {

	@Autowired
	TopicContentDao topicContentDao;
	
	@Override
	@Transactional
	public void delete(TopicContent entity) {
		// TODO Auto-generated method stub
		topicContentDao.delete(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		topicContentDao.deleteAll();
	}
	
	@Override
	public List<TopicContent> findAll() {
		// TODO Auto-generated method stub
		return topicContentDao.findAll();
	}
	
	@Override
	public List<TopicContent> findAllByExample(TopicContent entity) {
		// TODO Auto-generated method stub
		return topicContentDao.findAllByExample(entity);
	}
	
	@Override
	public TopicContent findById(Serializable id) {
		// TODO Auto-generated method stub
		return topicContentDao.findById(id);
	}
	
	@Override
	@Transactional
	public Serializable save(TopicContent entity) {
		// TODO Auto-generated method stub
		return topicContentDao.save(entity);
	}

	
	
	@Override
	@Transactional
	public void saveOrUpdate(TopicContent entity) {
		// TODO Auto-generated method stub
		topicContentDao.saveOrUpdate(entity);
	}

}
