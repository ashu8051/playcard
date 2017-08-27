package com.card.app.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.card.app.dao.TopicDao;
import com.card.app.model.Topic;

@Repository
public class TopicDaoImpl extends AbstractGenericDao<Topic> implements TopicDao{

	@Override
	public List<Topic> findAllTopicList(int id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(Topic.class).add(Restrictions.like("tutorial.id", id)).list();
		
	}

}
