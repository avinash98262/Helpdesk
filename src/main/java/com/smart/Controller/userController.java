package com.smart.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class userController {
	
	
	@RequestMapping("/index")
	public String dashboard(Model model) {
		model.addAttribute("title", "User DashBoard");
		
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add_contact")
	public String openAddContactForm(Model model) {
		
		model.addAttribute("title","add_contact_page");
		return "normal/add_contact_form";
	}
}
