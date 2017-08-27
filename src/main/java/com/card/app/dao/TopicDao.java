package com.card.app.dao;

import java.util.List;

import com.card.app.model.Topic;

public interface TopicDao extends GenericDao<Topic> {

	public List<Topic> findAllTopicList(int id);
}
