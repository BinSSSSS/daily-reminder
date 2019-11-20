package cn.tblack.reminder.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController 
{
	@RequestMapping("/error/403")
	public String mail() {
		return "/error/403";
	}
	
	@RequestMapping("/add-reminder")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public String newReminder() {
		
		return "add-reminder";
	}
	
	@RequestMapping("/reminder-list")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public String reminderList() {
		return "/user/reminder/reminder-list";
	}
	
	@RequestMapping("/")
	public String index() {
		
		return "index";
	}
	
	@RequestMapping("user-list")
	@PreAuthorize("hasRole('ADMIN')")
	public String userList() {
		return "/bs/user-list";
	}
}
