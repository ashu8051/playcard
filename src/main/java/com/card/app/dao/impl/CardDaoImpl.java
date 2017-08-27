package com.card.app.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.card.app.dao.CardDao;
import com.card.app.model.User;

@Repository
public class CardDaoImpl extends AbstractGenericDao<User> implements CardDao{

	
}
