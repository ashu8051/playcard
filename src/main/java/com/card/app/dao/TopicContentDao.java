package com.card.app.dao;

import java.util.List;

import com.card.app.model.TopicContent;

public interface TopicContentDao extends GenericDao<TopicContent> {

	public List<TopicContent>  findTopicList(int id);
	public List<TopicContent>  findTopicContentBySubTopic(int id);
	//public List<TopicContent>  findTopicContentByTopicId(int id);
}
