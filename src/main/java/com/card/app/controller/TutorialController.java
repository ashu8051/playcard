package com.card.app.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.card.app.model.Permission;
import com.card.app.model.Tutorial;
import com.card.app.service.PermissionService;
import com.card.app.service.TutorialService;

@Controller
@RequestMapping("/tutorial")
public class TutorialController {

	private static final Logger log = Logger.getLogger(TopicContentController.class);
	@Autowired
	private TutorialService  tutorialService;
	

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listPersons(Model model) {
		//model.addAttribute("permission", new Permission());
		model.addAttribute("tutorialList", this.tutorialService.findAll());
		return "tutorial-index";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	public String addPermission(Model model){
		
		model.addAttribute("tutorial", new Tutorial());
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "tutorial-add";
		
	}
	
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String addPermission(@ModelAttribute("tutorial") Tutorial tutorial, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			
		}
		if(tutorial.getId() == 0){
			//new person, add it
			this.tutorialService.save(tutorial);
		}else{
			//existing person, call update
			this.tutorialService.saveOrUpdate(tutorial);;
		}
		
		return "redirect:/tutorial/index";
		
	}
	
	@RequestMapping(value= "/update", method = RequestMethod.GET)
	public String updateTutorial(@RequestParam(value="tutorialId",required=true) int tutorialId,Model model){
		
		model.addAttribute("tutorial", tutorialService.findById(tutorialId));
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "tutorial-update";
		
	}
	
	@RequestMapping(value= "/update", method = RequestMethod.POST)
	public String updateTutorial(@ModelAttribute("tutorial") Tutorial tutorial, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			
		}
		if(tutorial.getId() == 0){
			//new person, add it
			this.tutorialService.save(tutorial);
		}else{
			//existing person, call update
			this.tutorialService.saveOrUpdate(tutorial);;
		}
		
		return "redirect:/tutorial/index";
		
	}


}
