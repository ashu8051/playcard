package com.card.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.card.app.model.SubContent;
import com.card.app.model.Topic;
import com.card.app.model.TopicContent;
import com.card.app.model.Tutorial;
import com.card.app.service.SubContentService;
import com.card.app.service.TopicContentService;
import com.card.app.service.TopicService;
import com.card.app.service.TutorialService;

@Controller
@RequestMapping("/topiccontent")
public class TopicContentController {

	private static final Logger log = Logger.getLogger(TopicContentController.class);
	@Autowired
	private TopicContentService  topicContentService;
	@Autowired
	private TutorialService  tutorialService;
	@Autowired
	private TopicService  topicService;
	@Autowired
	private SubContentService  subContentService;
	

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listPersons(Model model) {
		//model.addAttribute("permission", new Permission());
		model.addAttribute("topicContentList", this.topicContentService.findAll());
		return "topicContent-index";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	public String addPermission(Model model,HttpServletRequest  request){
		
		ArrayList<String>  contentType=new ArrayList<String>();
		contentType.add("H1");
		contentType.add("H2");
		contentType.add("P");
		contentType.add("Program");
		model.addAttribute("contentType",contentType);
		model.addAttribute("tutorialList",tutorialService.findAll());
		model.addAttribute("subContentList",subContentService.findAll());
		model.addAttribute("topicList", topicService.findAll());
		model.addAttribute("topicContent", new TopicContent());
		
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "topicContent-add";
		
	}
	
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String addPermission(@ModelAttribute("topicContent") TopicContent topicContent, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			//log.info("permission Name : "+permission.getName()+"  "+permission.getId());
		}
		Tutorial tutorial=tutorialService.findById(topicContent.getTutorial().getId());
		Topic topic=topicService.findById(topicContent.getTopic().getId());
		
		topicContent.setTutorial(tutorial);
		topicContent.setTopic(topic);
		if(topicContent.getId() == 0){
			//new person, add it
			this.topicContentService.save(topicContent);
		}else{
			//existing person, call update
			this.topicContentService.saveOrUpdate(topicContent);;
		}
		
		model.addAttribute("contentList",topicContentService.findTopicContentBySubTopic(topicContent.getSubContent().getId()));
		model.addAttribute("topicName",topic.getTopic());
		
		return "topicContent-topic";
		
	} 
	@RequestMapping(value= "/update", method = RequestMethod.GET)
	public String updateTopicContent(@RequestParam("contentId") int contentId,Model model,HttpServletRequest  request){
		
		ArrayList<String>  contentType=new ArrayList<String>();
		contentType.add("H1");
		contentType.add("H2");
		contentType.add("P");
		contentType.add("Program");
		model.addAttribute("contentType",contentType);
		model.addAttribute("tutorialList",tutorialService.findAll());
		model.addAttribute("subContentList",subContentService.findAll());
		model.addAttribute("topicList", topicService.findAll());
		model.addAttribute("topicContent", topicContentService.findById(contentId));
		
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "topicContent-update";
		
	}
	
	@RequestMapping(value= "/update", method = RequestMethod.POST)
	public String updateTopicContent(@ModelAttribute("topicContent") TopicContent topicContent, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			//log.info("permission Name : "+permission.getName()+"  "+permission.getId());
		}
		Tutorial tutorial=tutorialService.findById(topicContent.getTutorial().getId());
		Topic topic=topicService.findById(topicContent.getTopic().getId());
		
		topicContent.setTutorial(tutorial);
		topicContent.setTopic(topic);
		if(topicContent.getId() == 0){
			//new person, add it
			this.topicContentService.save(topicContent);
		}else{
			//existing person, call update
			this.topicContentService.saveOrUpdate(topicContent);;
		}
		
		model.addAttribute("contentList",topicContentService.findTopicContentBySubTopic(topicContent.getSubContent().getId()));
		model.addAttribute("topicName",topic.getTopic());
		
		return "topicContent-topic";
		
	}
	
	
	
	@RequestMapping(value= "/topiccontentlist", method = RequestMethod.GET)
	public @ResponseBody List<Topic> topicListByTopicId(@PathParam("topicId") int topicId){
		
		
		return topicService.findAllTopicList(topicId);
		
	}
	

	
	@RequestMapping(value= "/contentlist", method = RequestMethod.GET)
	public  String getTopicContentList(@PathParam("subContentId") int subContentId,Model model){
		
		model.addAttribute("contentList",topicContentService.findTopicContentBySubTopic(subContentId));
		model.addAttribute("topicName",subContentService.findById(subContentId).getSubContent());
		//return topicContentService.findTopicList(topicId);
		return "topicContent-topic";
	}
	
	@RequestMapping(value= "/contentDelete", method = RequestMethod.GET)
	public  String getTopicContentDelete(@RequestParam("contentId") int contentId,@RequestParam("topicId") int topicId,Model model){
		TopicContent topicContent=topicContentService.findById(contentId);
		topicContentService.delete(topicContent);
		model.addAttribute("contentList",topicContentService.findTopicContentBySubTopic(topicContent.getSubContent().getId()));
		model.addAttribute("topicName",((Topic)topicService.findById(topicId)).getTopic());
		//return topicContentService.findTopicList(topicId);
		return "topicContent-topic";
	}

}
