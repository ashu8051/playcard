package com.card.app.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.card.app.dao.SubContentDao;
import com.card.app.model.SubContent;
import com.card.app.model.Topic;

@Repository
public class SubContentDaoImpl extends AbstractGenericDao<SubContent> implements SubContentDao{

	@Override
	public List<SubContent> findSubContentList(int id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SubContent.class).add(Restrictions.like("topic.id", id)).list();
	}

}
