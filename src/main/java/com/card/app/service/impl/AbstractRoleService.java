package com.card.app.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.dao.RoleDao;
import com.card.app.model.Permission;
import com.card.app.model.Role;
import com.card.app.service.GenericService;

@Transactional
public abstract class AbstractRoleService<E> implements GenericService<Role>{

	@Autowired
	RoleDao  roleDao;
	
	@Override
	@Transactional
	public void delete(Role entity) {
		// TODO Auto-generated method stub
		roleDao.delete(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
		roleDao.deleteAll();
	}
	
	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleDao.findAll();
	}
	
	@Override
	public List<Role> findAllByExample(Role entity) {
		// TODO Auto-generated method stub
		return roleDao.findAllByExample(entity);
	}
	
	@Override
	public Role findById(Serializable id) {
		// TODO Auto-generated method stub
		return roleDao.findById(id);
	}
	
	@Override
	@Transactional
	public Serializable save(Role entity) {
		// TODO Auto-generated method stub
		return roleDao.save(entity);
	}

	
	
	@Override
	@Transactional
	public void saveOrUpdate(Role entity) {
		// TODO Auto-generated method stub
		roleDao.saveOrUpdate(entity);
	}
}
