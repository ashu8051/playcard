package com.card.app.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.dao.PermissionDao;
import com.card.app.model.Permission;
import com.card.app.service.GenericService;

@Transactional
public abstract class AbstractPermissionService<E> implements GenericService<Permission>{
	@Autowired
	PermissionDao  permissionDao;
	
	@Override
	@Transactional
	public void delete(Permission entity) {
		// TODO Auto-generated method stub
		permissionDao.delete(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		permissionDao.deleteAll();
	}
	
	@Override
	public List<Permission> findAll() {
		// TODO Auto-generated method stub
		return permissionDao.findAll();
	}
	
	@Override
	public List<Permission> findAllByExample(Permission entity) {
		// TODO Auto-generated method stub
		return permissionDao.findAllByExample(entity);
	}
	
	@Override
	public Permission findById(Serializable id) {
		// TODO Auto-generated method stub
		return permissionDao.findById(id);
	}
	
	@Override
	@Transactional
	public Serializable save(Permission entity) {
		// TODO Auto-generated method stub
		return permissionDao.save(entity);
	}

	
	
	@Override
	@Transactional
	public void saveOrUpdate(Permission entity) {
		// TODO Auto-generated method stub
		permissionDao.saveOrUpdate(entity);
	}
	
}
