package com.card.app.controller;

import java.util.HashSet;

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
import com.card.app.service.PermissionService;
import com.card.app.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	private static final Logger log = Logger.getLogger(RoleController.class);
	@Autowired
	private RoleService  roleService;
	@Autowired
	private PermissionService  permissionService;
	

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listPersons(Model model) {
		//model.addAttribute("role", new Role());
		model.addAttribute("listroles", this.roleService.findAll());
		return "role-index";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	public String addRole(Model model){
		
		model.addAttribute("role", new Role());
		/*if(p.getId() == 0){
			//new person, add it
			this.roleService.save(p);
		}else{
			//existing person, call update
			this.roleService.saveOrUpdate(p);;
		}*/
		
		return "role-add";
		
	}
	
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String addRole(@ModelAttribute("role") Role role, BindingResult result, Model model, HttpSession session){
		
		if(role.getId() == 0){
			//new person, add it
			role.setPermissions((new HashSet<Permission>(this.permissionService.findAll())));
			this.roleService.save(role);
		}else{
			//existing person, call update
			this.roleService.saveOrUpdate(role);;
		}
		
		return "redirect:/role/index";
		
	}
}
