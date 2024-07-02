package com.chainsys.payrollapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chainsys.payrollapplication.dao.PayrollDAO;
import com.chainsys.payrollapplication.model.Employees;

import jakarta.servlet.http.HttpSession;

@Component
public class AdminController {

	@Autowired
	PayrollDAO payrollDAO;


	@PostMapping("/adminLogin")
	public String adminLogin(@RequestParam("username") String name,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes,
			HttpSession session) {

		boolean success = false;
		int empCode = 0;

		try {
			success = payrollDAO.login(name, password);
			if (success) {
				empCode = payrollDAO.getEmployeeCode(name);
				session.setAttribute("username", name);
				session.setAttribute("emp_code", empCode);
				payrollDAO.insertCheckInTime(empCode, name);

				if (password.equals("1234")) {
					redirectAttributes.addFlashAttribute("specialAccess", true);
					return "redirect:/adminDashboard.jsp"; 
				} else {
					redirectAttributes.addFlashAttribute("status", "failed");
					return "redirect:/admin"; 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("status", "failed");
		return "redirect:/admin"; 
	}


	@GetMapping("/adminCheckOut")
	public String adminCheckOut(HttpSession session) {
		Integer empCode = (Integer) session.getAttribute("emp_code");

		if (empCode != null) {
			payrollDAO.insertCheckOutTime(empCode);
			session.invalidate();

		} 
		return "redirect:/log";
	}

	@GetMapping("/getEmployeeDetails")
	public String getEmployee(Model model) {
		Employees employee = payrollDAO.selectEmployee(); 

		model.addAttribute("employee", employee); 
		return "displayEmployees.jsp"; 
	}

}
