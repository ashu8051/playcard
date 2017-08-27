package com.card.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SUB_CONTENT")
public class SubContent {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="SUB_CONTENT_ID",updatable=false, unique=true, nullable=false)
    private int id;
	
	@Column(name="SUB_CONTENT_NAME")
	private String subContent;
	
	@ManyToOne( fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinColumn(name="TUTORIAL_ID")
	//@Cascade(value = {CascadeType.ALL })
	private Tutorial tutorial;
	
	@ManyToOne( fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="TOPIC_ID")
	//@Cascade(value = {CascadeType.ALL })
	private Topic topic;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubContent() {
		return subContent;
	}

	public void setSubContent(String subContent) {
		this.subContent = subContent;
	}

	public Tutorial getTutorial() {
		return tutorial;
	}

	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Set<TopicContent> getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(Set<TopicContent> topicContent) {
		this.topicContent = topicContent;
	}

	@OneToMany(mappedBy="subContent", fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	private Set<TopicContent>  topicContent;
	
	
}
