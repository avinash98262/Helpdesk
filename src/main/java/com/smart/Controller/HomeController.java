package com.smart.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.userRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller // ******here i am using the controller instead of restcontroller
//  ************so controller means it will view the html page ***
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public userRepository userrepository;

	@RequestMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "Home- smart contact manager");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "About- smart contact manager");
		return "about";
	}

	@RequestMapping("/signup")
	public String signUp(Model model) {

		model.addAttribute("title", "Register-smart contat manager");

		model.addAttribute("user", new User()); // *********this imp. to contain the name and object which will fillupsb
		return "signup";

	}

	// handler for registering user;

	@RequestMapping(value = "/do_register", method = RequestMethod.POST) // in model attribute variable all value has
																			// injected which had get in data
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {
												//--your entity name must match with the name in the form,xs
		try {
                      
			if (!agreement) {
				System.out.println("Please Accept the terms and Condition");
				throw new Exception("Please Accept the terms and Condition");
			}

			if (result1.hasErrors()) {
				System.out.println("ERROR " + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("Role_User");
			user.setEnabled(true);
			user.setImageUrl("default.png");

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("Agreement " + agreement);
			System.out.println("USER " + user);
			System.out.println("USER " + user.getName());

//			this is the most important line (mind it the both the line)
			User result = this.userrepository.save(user); // it will save to the database
			model.addAttribute("user", new User()); // it will clear the form after saving and preparing form for
													// another student

			session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));

			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Somthing went Wrong" + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}

	@RequestMapping(value = "/signin")
	public String customLogin(Model model) {

		model.addAttribute("title", "Login page");
		return "login";
	}

}
