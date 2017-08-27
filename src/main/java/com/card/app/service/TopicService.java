package com.card.app.service;

import java.util.List;

import com.card.app.model.Topic;

public interface TopicService extends GenericService<Topic>{

	public List<Topic> findAllTopicList(int id);
}
