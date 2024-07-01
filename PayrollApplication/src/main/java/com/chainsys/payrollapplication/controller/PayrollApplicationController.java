package com.chainsys.payrollapplication.controller;

import java.io.IOException;
import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chainsys.payrollapplication.dao.PayrollDAO;
import com.chainsys.payrollapplication.dao.PayrollDAOImpl;
import com.chainsys.payrollapplication.model.AdminReport;
import com.chainsys.payrollapplication.model.EmployeeDetails;
import com.chainsys.payrollapplication.model.LeaveReport;
import com.chainsys.payrollapplication.model.PermissionCount;

import jakarta.servlet.http.HttpSession;


@Controller
public class PayrollApplicationController {

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
			//            @RequestParam("image") MultipartFile imageFile,
			RedirectAttributes redirectAttributes) {

		EmployeeDetails employeeDetails = new EmployeeDetails();
		employeeDetails.setUsername(name);
		employeeDetails.setDesignation(role);
		employeeDetails.setUseremail(email);
		employeeDetails.setUsermobile(contact);
		employeeDetails.setUserpassword(password);

		//            if (!imageFile.isEmpty()) {
		//                employeeDetails.setImageData(imageFile.getBytes());
		//            }

		int randomNumber = 1000 + (int) (Math.random() * 9000);
		employeeDetails.setEmp_code(randomNumber);

		payrollDAO.insertEmployee(employeeDetails);

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

	@PostMapping("/adminLogin")
	public String adminLogin(@RequestParam("username") String name,
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
			return "redirect:/admin";
		}

		if (success) {
			session.setAttribute("username", name);
			session.setAttribute("emp_code", empCode); 
			payrollDAO.insertCheckInTime(empCode, name);
			return "redirect:/adminDashboard.jsp"; 
		} else {

			redirectAttributes.addFlashAttribute("status", "failed");
			return "redirect:/admin";
		}
	}

  
	
	
	
	@PostMapping("/registerPermission")
	public String applyPermission(
			@RequestParam("name") String name,
			@RequestParam("date") String date,
			@RequestParam("start_time") String startTime,
			@RequestParam("end_time") String endTime,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		PermissionCount permissionCount = new PermissionCount();
		permissionCount.setName(name);
		permissionCount.setDate(date);
		permissionCount.setStart_time(startTime);
		permissionCount.setEnd_time(endTime);
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
		LeaveReport leaveReport=new LeaveReport();
		leaveReport.setName(name);
		leaveReport.setFromdate(fromDate);
		leaveReport.setToDate(toDate);
		leaveReport.setLeaveType(leaveType);
	    int empCode = (int) session.getAttribute("emp_code");
	   
	    payrollDAO.insertLeaveReport(leaveReport,empCode);

		return "leave.jsp"; 
	}
	
	@PostMapping("/adminReport")
	public String adminReport(
			@RequestParam("name") String name,
			@RequestParam("comments") String comments,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		AdminReport adminReport=new AdminReport();
		adminReport.setName(name);
		adminReport.setText(comments);
		
	    int empCode = (int) session.getAttribute("emp_code");	   
	    payrollDAO.insertAdminReport(adminReport,empCode);

		return "adminReport.jsp"; 
	}

}