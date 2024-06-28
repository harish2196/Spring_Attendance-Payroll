package com.chainsys.payrollapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayrollApplicationController {

	@RequestMapping("/register")
	public String saveEmp() {
		return "hello.jsp";

	}	
	
}
