package com.card.app.dao;

import java.util.List;

import com.card.app.model.SubContent;

public interface SubContentDao extends GenericDao<SubContent>{

	public List<SubContent>  findSubContentList(int id);
	
}
