package com.card.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.card.app.model.Topic;
import com.card.app.service.TopicService;

@Service
@Transactional
public class TopicServiceImpl extends AbstractTopicService<Topic> implements TopicService {

	@Override
	public List<Topic> findAllTopicList(int id) {
		// TODO Auto-generated method stub
		return topicDao.findAllTopicList(id);
	}

}
