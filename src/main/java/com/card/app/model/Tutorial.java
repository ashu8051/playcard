package com.card.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TUTORIAL")
public class Tutorial {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="TUTORIAL_ID",updatable=false, unique=true, nullable=false)
    private int id;
	
	@Column(name="TUTORIAL_NAME")
	private String tutorialName;
	
	@JsonIgnore
	@OneToMany(mappedBy="tutorial", fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	//@Fetch(FetchMode.JOIN)
	private Set<Topic>  topicList;
	
	@JsonIgnore
	@OneToMany(mappedBy="tutorial", fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	//@Fetch(FetchMode.JOIN)
	private Set<SubContent>  subContentList;
	
	public Set<SubContent> getSubContentList() {
		return subContentList;
	}

	public void setSubContentList(Set<SubContent> subContentList) {
		this.subContentList = subContentList;
	}

	@JsonIgnore
	@OneToMany(mappedBy="tutorial", fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	private Set<TopicContent>  topicContentList;
	
	

	public Set<TopicContent> getTopicContentList() {
		return topicContentList;
	}

	public void setTopicContentList(Set<TopicContent> topicContentList) {
		this.topicContentList = topicContentList;
	}

	public Set<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(Set<Topic> topicList) {
		this.topicList = topicList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTutorialName() {
		return tutorialName;
	}

	public void setTutorialName(String tutorialName) {
		this.tutorialName = tutorialName;
	}
}
