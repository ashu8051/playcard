package com.card.app.service;


import java.util.List;

import com.card.app.model.TopicContent;

public interface TopicContentService extends GenericService<TopicContent> {

	public List<TopicContent>  findTopicList(int id);
	public List<TopicContent> findTopicContentBySubTopic(int id);
}
