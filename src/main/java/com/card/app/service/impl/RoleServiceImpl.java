package com.card.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.card.app.model.Role;
import com.card.app.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends AbstractRoleService<Role> implements RoleService{

}
