package com.card.app.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.card.app.aop.LogExecutionTime;
import com.card.app.model.User;
import com.card.app.service.RoleService;
import com.card.app.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserService  userService;
	@Autowired
	private RoleService  roleService;
	

	@Secured("ROLE_MANAGE_USER")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@LogExecutionTime(name="listPersons",level="Info", message="ListPersons Data")
	public String listPersons(Model model) {

		if(log.isDebugEnabled())
		{
			log.info("userService "+this.userService.findAll().toString());
		}
		model.addAttribute("listUsers", this.userService.findAll());
		return "user-index";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	public String addPerson(Model model){
		
		model.addAttribute("user", new User());
		/*if(p.getId() == 0){
			//new person, add it
			this.userService.save(p);
		}else{
			//existing person, call update
			this.userService.saveOrUpdate(p);;
		}*/
		
		return "user-add";
		
	}
	
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session){
		
		if(user.getId() == 0){
			user.setRole(this.roleService.findById(1));
			this.userService.save(user);
		}else{
			//existing person, call update
			this.userService.saveOrUpdate(user);;
		}
		
		return "redirect:/user/index";
		
	}
}
