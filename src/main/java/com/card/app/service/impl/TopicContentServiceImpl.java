package com.card.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.card.app.model.TopicContent;
import com.card.app.service.TopicContentService;

@Service
@Transactional
public class TopicContentServiceImpl extends AbstractTopicContentService<TopicContent> implements TopicContentService {

	@Override
	public List<TopicContent> findTopicList(int id) {
		// TODO Auto-generated method stub
		return topicContentDao.findTopicList(id);
	}

	@Override
	public List<TopicContent> findTopicContentBySubTopic(int id) {
		// TODO Auto-generated method stub
		return topicContentDao.findTopicContentBySubTopic(id);
	}

}
