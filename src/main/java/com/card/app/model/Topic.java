package com.card.app.model;

import java.util.HashSet;
import java.util.Set;

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

import javax.persistence.CascadeType;





@Entity
@Table(name="TOPIC")
public class Topic {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="TOPIC_ID",updatable=false, unique=true, nullable=false)
    private int id;
	
	@Column(name="TOPIC_NAME")
	private String topic;
	
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="TUTORIAL_ID")
	//@Cascade(value = {CascadeType.ALL })
	private Tutorial tutorial;
	
	@OneToMany(mappedBy="topic", fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	private Set<SubContent>  subContent;
	
	@OneToMany(mappedBy="topic", fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	private Set<TopicContent>  topicContent;

	public Set<TopicContent> getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(HashSet<TopicContent> topicContent) {
		this.topicContent = topicContent;
	}

	public Tutorial getTutorial() {
		return tutorial;
	}

	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
}
