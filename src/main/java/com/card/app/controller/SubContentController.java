package com.card.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/subcontent")
public class SubContentController {

	private static final Logger log = Logger.getLogger(SubContentController.class);
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
		model.addAttribute("subContentList", this.subContentService.findAll());
		return "subcontent-index";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	public String addPermission(Model model,HttpServletRequest  request){
		
		
		model.addAttribute("tutorialList",tutorialService.findAll());
		model.addAttribute("topicList", topicService.findAll());
		model.addAttribute("subContent", new SubContent());
		
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "subcontent-add";
		
	}
	
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String addPermission(@ModelAttribute("subContent") SubContent subContent, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			//log.info("permission Name : "+permission.getName()+"  "+permission.getId());
		}
		
		if(subContent.getId() == 0){
			//new person, add it
			this.subContentService.save(subContent);
		}else{
			//existing person, call update
			this.subContentService.saveOrUpdate(subContent);;
		}
		
		return "redirect:/subcontent/index";
		
	}
	
	@RequestMapping(value= "/update", method = RequestMethod.GET)
	public String updateSubcontent(@RequestParam(value="subContentId",required=true) int subContentId,Model model,HttpServletRequest  request){
		
		
		model.addAttribute("tutorialList",tutorialService.findAll());
		model.addAttribute("topicList", topicService.findAll());
		model.addAttribute("subContent", subContentService.findById(subContentId));
		
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "subcontent-update";
		
	}
	
	@RequestMapping(value= "/update", method = RequestMethod.POST)
	public String updateSubcontent(@ModelAttribute("subContent") SubContent subContent, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			//log.info("permission Name : "+permission.getName()+"  "+permission.getId());
		}
		
		if(subContent.getId() == 0){
			//new person, add it
			this.subContentService.save(subContent);
		}else{
			//existing person, call update
			this.subContentService.saveOrUpdate(subContent);;
		}
		
		return "redirect:/subcontent/index";
		
	}
	
	@RequestMapping(value= "/subcontentlistbyid", method = RequestMethod.GET)
	public  String getTopicContentList(@PathParam("topicId") int topicId,Model model){
		
		model.addAttribute("subContentList",subContentService.findSubContentList(topicId));
		model.addAttribute("topicName",((Topic)topicService.findById(topicId)).getTopic());
		//return topicContentService.findTopicList(topicId);
		return "subcontent-index";
	}
	
	@RequestMapping(value= "/subcontentlist", method = RequestMethod.GET)
	public @ResponseBody HashMap<Integer,String> subContentListByTopicId(@PathParam("topicId") int topicId){
		HashMap<Integer,String>  topiclistmap=new HashMap<Integer,String>();
		List<SubContent>  topiclist=this.subContentService.findSubContentList(topicId);
		for(SubContent t:topiclist)
		{
			if(t.getTopic().getId()==topicId)
			{
				topiclistmap.put(t.getId(), t.getSubContent());
			}
			
		}
		
		return topiclistmap;
		
	
		
	}
}
