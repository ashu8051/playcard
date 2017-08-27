package com.card.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.card.app.model.Permission;
import com.card.app.service.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl extends AbstractPermissionService<Permission> implements PermissionService{

}
