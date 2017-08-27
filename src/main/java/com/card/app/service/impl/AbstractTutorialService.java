package com.card.app.service.impl;


import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.dao.TutorialDao;
import com.card.app.model.Permission;
import com.card.app.model.Tutorial;
import com.card.app.service.GenericService;

@Transactional
public abstract class AbstractTutorialService<E> implements GenericService<Tutorial> {

	@Autowired
	TutorialDao   tutorialDao;
	
	@Override
	@Transactional
	public void delete(Tutorial entity) {
		// TODO Auto-generated method stub
		tutorialDao.delete(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		tutorialDao.deleteAll();
	}
	
	@Override
	public List<Tutorial> findAll() {
		// TODO Auto-generated method stub
		return tutorialDao.findAll();
	}
	
	@Override
	public List<Tutorial> findAllByExample(Tutorial entity) {
		// TODO Auto-generated method stub
		return tutorialDao.findAllByExample(entity);
	}
	
	@Override
	public Tutorial findById(Serializable id) {
		// TODO Auto-generated method stub
		return tutorialDao.findById(id);
	}
	
	@Override
	@Transactional
	public Serializable save(Tutorial entity) {
		// TODO Auto-generated method stub
		return tutorialDao.save(entity);
	}

	
	
	@Override
	@Transactional
	public void saveOrUpdate(Tutorial entity) {
		// TODO Auto-generated method stub
		tutorialDao.saveOrUpdate(entity);
	}

	
	
}
