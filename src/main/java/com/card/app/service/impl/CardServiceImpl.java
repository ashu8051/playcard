package com.card.app.service.impl;



import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.card.app.model.User;
import com.card.app.service.CardService;

@Service
@Transactional(readOnly = true)
public class CardServiceImpl  extends AbstractGenericService<User> implements CardService {

	
}
