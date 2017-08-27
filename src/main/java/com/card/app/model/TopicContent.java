package com.card.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TOPIC_CONTENT")
public class TopicContent {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="TOPIC_CONTENT_ID",updatable=false, unique=true, nullable=false)
    private int id;
	
	//@Column(name="TUTORIAL_ID")
	//private int tutorialId;
	//@Column(name="TOPIC_ID")
	//private int topicId;
	
	@Column(name="CONTENT",length=1000)
	private String content;
	
	@Column(name="CONTENT_TYPE")
	private String contentType;
	
	public String getContentType() {
		
		
		return contentType;
	}

	public void setContentType(String contentType) {
		
		this.contentType = contentType;
	}

	@ManyToOne( fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="TUTORIAL_ID")
	private Tutorial tutorial;
	
	@ManyToOne( fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="TOPIC_ID")
	private Topic topic;
	
	@ManyToOne( fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="SUB_CONTENT_ID")
	private SubContent subContent;

	public SubContent getSubContent() {
		return subContent;
	}

	public void setSubContent(SubContent subContent) {
		this.subContent = subContent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getContent() {
		/*if(getContentType().equals("H1"))
		{
			return  "<h1>"+content+"</h1>";
		}
		else if(getContentType().equals("H2"))
		{
			return  "<h2>"+content+"</h2>";
		}
		else if(getContentType().equals("P"))
		{
			return  "<h3>"+content+"</h3>";
		}*/
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
