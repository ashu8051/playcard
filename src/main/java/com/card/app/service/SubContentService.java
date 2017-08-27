package com.card.app.service;

import java.util.List;

import com.card.app.model.SubContent;

public interface SubContentService extends GenericService<SubContent> {

	public List<SubContent> findSubContentList(int id);
}
