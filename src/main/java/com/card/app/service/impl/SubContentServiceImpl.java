package com.card.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.card.app.model.SubContent;
import com.card.app.service.SubContentService;

@Service
@Transactional
public class SubContentServiceImpl extends AbstractSubContentService<SubContent> implements SubContentService {

	@Override
	public List<SubContent> findSubContentList(int id) {
		// TODO Auto-generated method stub
		return subContentDao.findSubContentList(id);
	}

}
