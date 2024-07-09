package com.chainsys.payrollapplication.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.chainsys.payrollapplication.dao.*;
import com.chainsys.payrollapplication.dao.PayrollDAO;
import com.chainsys.payrollapplication.dao.PayrollDAOImpl;
import com.chainsys.payrollapplication.model.AdminReport;
import com.chainsys.payrollapplication.model.CheckInsAndCheckOuts;
import com.chainsys.payrollapplication.model.EmployeePayScale;
import com.chainsys.payrollapplication.model.Employees;
import com.chainsys.payrollapplication.model.LeaveReport;
import com.chainsys.payrollapplication.model.PayrollList;
import com.chainsys.payrollapplication.model.PermissionCount;

import jakarta.servlet.http.HttpSession;


@Controller
public class EmployeeController {

	@Autowired
	PayrollDAO payrollDAO;
	

	@RequestMapping("/reg")
	public String showRegistrationForm() {
		return "registration.jsp"; 
	}  

	@RequestMapping("/log")
	public String showLogIn() {
		return "login.jsp"; 
	}  

	@RequestMapping("/admin")
	public String adminLog() {
		return "adminLogin.jsp"; 
	}  

	@PostMapping("/register")
	public String registerFormSubmission(
			@RequestParam("name") String name,
			@RequestParam("role") String role,
			@RequestParam("email") String email,
			@RequestParam("contact") String contact,
			@RequestParam("pass") String password,
			@RequestParam("image") MultipartFile imageFile,
			RedirectAttributes redirectAttributes,
			Model model) {	
		
		byte[] image = null;
		if (!imageFile.isEmpty()) {
			try {
				image = imageFile.getBytes();
			} catch (IOException e) {
				e.printStackTrace();
				return "error"; 
			}
		}

		if (payrollDAO.isUserExist(email, contact)) {
			model.addAttribute("message", "User already exists");
			return "registration.jsp";
		}
		Validations validations=new Validations();
		validations.validateString(name);
		validations.validateString(role);
		validations.isEmailChecker(email);
		validations.isPhoneNumber(contact);
		validations.isPassword(password);
		
		
		Employees employees = new Employees();
		employees.setUserName(name);
		employees.setDesignation(role);
		employees.setUserEmail(email);
		employees.setUserMobile(contact);
		employees.setUserPassword(password);
		employees.setImageData(image);

		int randomNumber = 1000 + (int) (Math.random() * 9000);
		employees.setEmpCode(randomNumber);

		payrollDAO.insertEmployee(employees);

		redirectAttributes.addFlashAttribute("status", "success");
		redirectAttributes.addFlashAttribute("randomNumber", randomNumber);

		return "redirect:/reg";
	}


	@PostMapping("/login")
	public String loginForm(@RequestParam("username") String name,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes,
			HttpSession session) {

		boolean success = false;
		int empCode = 0; 
		try {
			success = payrollDAO.login(name, password);
			if (success==true) {
				empCode = payrollDAO.getEmployeeCode(name);
				System.out.println("Employee Code: " + empCode); 

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("status", "failed");
			return "redirect:/log";
		}

		if (success) {
			session.setAttribute("username", name);
			session.setAttribute("emp_code", empCode); 
			payrollDAO.insertCheckInTime(empCode, name);
			return "redirect:/home.jsp"; 
		} else {

			redirectAttributes.addFlashAttribute("status", "failed");
			return "redirect:/log";
		}
	}

	@PostMapping("/checkout")
	public String checkOut(HttpSession session) {
		Integer empCode = (Integer) session.getAttribute("emp_code");

		if (empCode != null) {
			payrollDAO.insertCheckOutTime(empCode);
			session.invalidate();

		} 
		return "redirect:/log";
	}



	@PostMapping("/registerPermission")
	public String applyPermission(
			@RequestParam("name") String name,
			@RequestParam("date") String date,
			@RequestParam("start_time") String startTime,
			@RequestParam("end_time") String endTime,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		Validations validations=new Validations();
		
		validations.validateString(name);
		PermissionCount permissionCount = new PermissionCount();
		permissionCount.setName(name);
		permissionCount.setDate(date);
		permissionCount.setStartTime(startTime);
		permissionCount.setEndTime(endTime);
		int empCode = (int) session.getAttribute("emp_code");

		payrollDAO.insertPermission(permissionCount,empCode);

		return "viewPermission.jsp"; 
	}

	@PostMapping("/leave")
	public String applyLeave(
			@RequestParam("name") String name,
			@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			@RequestParam("leaveType") String leaveType,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		Validations validations=new Validations();
		validations.validateString(name);
		LeaveReport leaveReport=new LeaveReport();
		leaveReport.setName(name);
		leaveReport.setFromdate(fromDate);
		leaveReport.setTodate(toDate);
		leaveReport.setLeaveType(leaveType);
		int empCode = (int) session.getAttribute("emp_code");

		payrollDAO.insertLeaveReport(leaveReport,empCode);

		return "leave.jsp"; 
	}
	
	@PostMapping("/viewPermission")
	public String permissionStatus(Model model,HttpSession session) {
		int empCode = (Integer) session.getAttribute("emp_code");
		ArrayList<PermissionCount> permissionCount = new ArrayList<>(payrollDAO.getPermissionStatus(empCode));
		model.addAttribute("permissionCount", permissionCount);
		return "permissionStatus.jsp"; 

	}

	@PostMapping("/viewLeave")
	public String leaveStatus(Model model,HttpSession session) {
		int empCode = (Integer) session.getAttribute("emp_code");
		ArrayList<LeaveReport> leaveReport = new ArrayList<>(payrollDAO.getAllLeaveStatus(empCode));
		model.addAttribute("leaveReport", leaveReport);
		return "leaveStatus.jsp"; 

	}

}