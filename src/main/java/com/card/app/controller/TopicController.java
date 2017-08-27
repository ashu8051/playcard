package com.card.app.controller;

import java.util.HashMap;
import java.util.List;

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

import com.card.app.model.Topic;
import com.card.app.service.TopicService;
import com.card.app.service.TutorialService;

@Controller
@RequestMapping("/topic")
public class TopicController {

	private static final Logger log = Logger.getLogger(TopicController.class);
	@Autowired
	private TopicService  topicService;
	@Autowired
	private TutorialService  tutorialService;
	//@Autowired
	//private TopicService  topicService;
	

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listPersons(Model model) {
		//model.addAttribute("permission", new Permission());
		model.addAttribute("topicList", this.topicService.findAll());
		return "topic-index";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	public String addPermission(Model model){
		
		model.addAttribute("tutorialList",tutorialService.findAll());
		model.addAttribute("topic", new Topic());
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "topic-add";
		
	}
	
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String addPermission(@ModelAttribute("topic") Topic topic, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			//log.info("permission Name : "+permission.getName()+"  "+permission.getId());
		}
		
		if(result.hasErrors())
		{
			model.addAttribute("error", "Please fill form");
		}
		//Tutorial tutorial=tutorialService.findById(topic.getTutorial().getId());
		//topic.setTutorial(tutorial);
		
			if(topic.getId() == 0){
				//new person, add it
				this.topicService.save(topic);
			}else{
				//existing person, call update
				this.topicService.saveOrUpdate(topic);;
			}
	
		
		
		return "redirect:/topic/index";
		
	}
	
	@RequestMapping(value= "/update", method = RequestMethod.GET)
	public String updateTopic(@RequestParam(value="topicId",required=true) int topicId,Model model){
		
		model.addAttribute("tutorialList",tutorialService.findAll());
		model.addAttribute("topic", topicService.findById(topicId));
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "topic-update";
		
	}
	
	@RequestMapping(value= "/update", method = RequestMethod.POST)
	public String updateTopic(@ModelAttribute("topic") Topic topic, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			//log.info("permission Name : "+permission.getName()+"  "+permission.getId());
		}
		
		if(result.hasErrors())
		{
			model.addAttribute("error", "Please fill form");
		}
		//Tutorial tutorial=tutorialService.findById(topic.getTutorial().getId());
		//topic.setTutorial(tutorial);
		
			if(topic.getId() == 0){
				//new person, add it
				this.topicService.save(topic);
			}else{
				//existing person, call update
				this.topicService.saveOrUpdate(topic);;
			}
	
		
		
		return "redirect:/topic/index";
		
	}
	
	@RequestMapping(value= "/topiclistmap", method = RequestMethod.GET)
	public @ResponseBody HashMap<Integer,String> addTopicListByIdMap(@PathParam("tutorialId") int tutorialId,Model model){
		
		HashMap<Integer,String>  topiclistmap=new HashMap<Integer,String>();
		List<Topic>  topiclist=this.topicService.findAllTopicList(tutorialId);
		for(Topic t:topiclist)
		{
			if(t.getTutorial().getId()==tutorialId)
			{
				topiclistmap.put(t.getId(), t.getTopic());
			}
			
		}
		
		return topiclistmap;
	}
	
	@RequestMapping(value= "/topiclist", method = RequestMethod.GET)
	public String addTopicListById(@PathParam("tutorialId") int tutorialId,Model model){
		
		/*HashMap<Integer,String>  topiclistmap=new HashMap<Integer,String>();
		List<Topic>  topiclist=this.topicService.findAllTopicList(tutorialId);
		for(Topic t:topiclist)
		{
			if(t.getTutorial().getId()==1)
			{
				topiclistmap.put(t.getId(), t.getTopic());
			}
			
		}*/
		
		model.addAttribute("topicList", this.topicService.findAllTopicList(tutorialId));
		
		return "topic-index";
		
	}

}
