package com.card.app.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.card.app.dao.TopicContentDao;
import com.card.app.model.TopicContent;
import com.card.app.model.Tutorial;

@Repository
public class TopicContentDaoImpl extends AbstractGenericDao<TopicContent> implements TopicContentDao{

	@Override
	public List<TopicContent> findTopicList(int id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(TopicContent.class).add(Restrictions.like("topic.id", id)).list();
	}

	@Override
	public List<TopicContent> findTopicContentBySubTopic(int id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(TopicContent.class).add(Restrictions.like("subContent.id", id)).list();
	}

/*	@Override
	public List<TopicContent> findTopicContentByTopicId(int id) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
