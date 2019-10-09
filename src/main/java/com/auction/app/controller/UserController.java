package com.auction.app.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.auction.app.model.User;
import com.auction.app.repository.AuctionRepository;
import com.auction.app.repository.CompletedAuctionRepository;
import com.auction.app.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	CompletedAuctionRepository completedAuctionRepository;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/login")
	public String index( Model model, Principal principal, @CookieValue(name = "emailTry", required = false) String count ) {
		
		if(count!=null && count.equals("0"))
			return "Banned";
		
		model.addAttribute("myUser", new User());
		return principal==null ? "Login" : "/";
	}
	@ExceptionHandler(NotFoundException.class)
	public String errorPage() {
		return "Index";
	}
	
	@RequestMapping("/secret/index")
	@ResponseBody
	public String textIndex(Model model) {
		return "index";
	}
	
	@RequestMapping("/")
	public String mainIndex(Model model) {
		model.addAttribute("projects", auctionRepository.findByActive(1));
		model.addAttribute("completedProjects",completedAuctionRepository.findAll() );
		return "Index";
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	@ResponseBody
	public String createUser(@ModelAttribute("myUser") User user) {
		
//		String password=new BCryptPasswordEncoder().encode(user.getPassword());
//		user.setPassword(password);
		
		if(userRepository.findByEmail(user.getEmail()) != null) 
			return "Email Already Registered";
		
		else if(userRepository.findByUsername(user.getUsername())!=null)
			return "Username Taken";
		
		else { 
			User userCreated=userRepository.save(user);
			return userCreated!=null ? "done" : "Something went wrong";
		}
		
		
		
	}
	@RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
	@ResponseBody
	public String checkEmail(@ModelAttribute("myUser") User user,String ques, Model model, HttpServletRequest request, HttpServletResponse response) {
		User findUser=userRepository.findByEmail(user.getEmail());
		
		if(ques!=null) {
				if(user.getQuestion() == findUser.getQuestion() && findUser.getAnswer().equals(user.getAnswer())) {
					model.addAttribute("user", user);
					return "done";
				}
				
			return "Wrong Answer";
		}
		
		//Checking if email is valid  
		
		boolean isEmailValid=(findUser!=null);
		
		//Checking in try left for e-mail
		
		for(Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals("emailTry") && !isEmailValid) {
				int count=Integer.parseInt(cookie.getValue());
				count=(isEmailValid ? count : count-1);
				cookie.setValue(count+"");
				response.addCookie(cookie);
				return count!=0 ? "Email Not Found You Have "+count+" Left !!!!" : "Banned";
			}
		}
		
		//If Cookie is not created then create a cookie
		if(!isEmailValid) {
			Cookie cookie=new Cookie("emailTry", "3");
			response.addCookie(cookie);
			return "Email Not Found You Have "+3+" Left !!!!";
		}
		
		return ","+findUser.getEmail();
		
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public String checkEmail(@ModelAttribute("myUser") User user) {
		User findUser=userRepository.findByEmail(user.getEmail());
		
		if(user.getPassword().length()<9)
			return "Password Length Must Be Greater Than 8";
		
		
		else if(user.getQuestion() == findUser.getQuestion() && findUser.getAnswer().equals(user.getAnswer())) {
			findUser.setPassword(user.getPassword());
			userRepository.save(findUser);
			return "";
		}
		
		return "Something went wrong <br> Try refreshing page";
	}
	
}
