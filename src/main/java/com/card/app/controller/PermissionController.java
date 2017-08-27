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

import com.card.app.model.Permission;
import com.card.app.model.Role;
import com.card.app.model.User;
import com.card.app.service.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	private static final Logger log = Logger.getLogger(PermissionController.class);
	@Autowired
	private PermissionService  permissionService;
	

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listPersons(Model model) {
		//model.addAttribute("permission", new Permission());
		model.addAttribute("listPermissions", this.permissionService.findAll());
		return "permission-index";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	public String addPermission(Model model){
		
		model.addAttribute("permission", new Permission());
		/*if(p.getId() == 0){
			//new person, add it
			this.permissionService.save(p);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(p);;
		}*/
		
		return "permission-add";
		
	}
	
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String addPermission(@ModelAttribute("permission") Permission permission, BindingResult result, Model model, HttpSession session){
		if(log.isDebugEnabled())
		{
			log.info("permission Name : "+permission.getName()+"  "+permission.getId());
		}
		if(permission.getId() == 0){
			//new person, add it
			this.permissionService.save(permission);
		}else{
			//existing person, call update
			this.permissionService.saveOrUpdate(permission);;
		}
		
		return "redirect:/permission/index";
		
	}
}
