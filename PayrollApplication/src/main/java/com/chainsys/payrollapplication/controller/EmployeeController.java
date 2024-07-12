package com.chainsys.payrollapplication.controller;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.chainsys.payrollapplication.dao.*;
import com.chainsys.payrollapplication.dao.PayrollDAO;
import com.chainsys.payrollapplication.model.EmployeePayScale;
import com.chainsys.payrollapplication.model.Employees;
import com.chainsys.payrollapplication.model.LeaveReport;
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

	@RequestMapping("/permissionPage")
	public String Permission() {
		return "viewPermission.jsp"; 
	} 

	@RequestMapping("/leavePage")
	public String leave() {
		return "leave.jsp"; 
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
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/permissionPage";
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
		validations.isValidDate(fromDate);
		validations.isValidDate(toDate);


		LeaveReport leaveReport=new LeaveReport();
		leaveReport.setName(name);
		leaveReport.setFromdate(fromDate);
		leaveReport.setTodate(toDate);
		leaveReport.setLeaveType(leaveType);
		int empCode = (int) session.getAttribute("emp_code");

		payrollDAO.insertLeaveReport(leaveReport,empCode);
		redirectAttributes.addFlashAttribute("status", "success");

		return "redirect:/leavePage";
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

	@PostMapping("/employeePayslip")
	public String viewEmployeepayslip(RedirectAttributes redirectAttributes,HttpSession session,
			Model model) {

		int empCode = (Integer) session.getAttribute("emp_code");	

		int totalCheckinCount, sickLeaveDays, casualLeaveDays, permissionCount,allocateSalary;
		String name, email;

		EmployeePayScale employeePayScale = new EmployeePayScale();
		employeePayScale.setEmpCode(empCode);
		name = payrollDAO.getEmployeeName(empCode);
		employeePayScale.setUsername(name); 
		email = payrollDAO.getEmployeeEmail(empCode);
		employeePayScale.setUserEmail(email); 

		totalCheckinCount = payrollDAO.getTotalCheckinCount(empCode);
		sickLeaveDays = payrollDAO.countSickLeavePayroll(empCode);
		casualLeaveDays = payrollDAO.countCasualLeavePayroll(empCode);
		permissionCount = payrollDAO.countPermissionsPayroll(empCode);
		allocateSalary=payrollDAO.getEmployeePayscaleSalary(empCode);

		employeePayScale.setWorkingDays(totalCheckinCount);
		employeePayScale.setSickLeaveDays(sickLeaveDays);
		employeePayScale.setCasualLeaveDays(casualLeaveDays);
		employeePayScale.setSalary(allocateSalary);
		employeePayScale.setPayrollPermission(String.valueOf(permissionCount));
		double halfDayDeduction = 0,fullDayDeduction = 0;
		double salary = allocateSalary;
		double dailySalary = salary / 22;
		double checkinsSalary = dailySalary * totalCheckinCount;

		if (permissionCount > 2) {
			halfDayDeduction = (permissionCount - 2) * (dailySalary / 2);
			checkinsSalary -= halfDayDeduction;
		}		
		employeePayScale.setPermissionPayscale(halfDayDeduction);


		if(sickLeaveDays >= 2 && sickLeaveDays < 3) {
			checkinsSalary=checkinsSalary;
			employeePayScale.setSickLeavePayscale(0.0);

		}else if (sickLeaveDays >= 3 ) {
			fullDayDeduction =(sickLeaveDays-2) * (dailySalary);
			checkinsSalary -= fullDayDeduction;
			employeePayScale.setSickLeavePayscale(fullDayDeduction);
		}



		if (casualLeaveDays >= 1 && casualLeaveDays < 2 ) {
			checkinsSalary=checkinsSalary;
			employeePayScale.setCasualLeavePayscale(0.0);
		}else if(casualLeaveDays >= 2) {
			fullDayDeduction = (casualLeaveDays - 1) * (dailySalary);
			checkinsSalary -= fullDayDeduction;
			employeePayScale.setCasualLeavePayscale(fullDayDeduction);
		}


		employeePayScale.setSalary((int)salary);		
		employeePayScale.setGrossPay(checkinsSalary);
		double pfDeduction = checkinsSalary * 0.12;
		employeePayScale.setPf((int) pfDeduction);
		double netPay = checkinsSalary - pfDeduction;
		employeePayScale.setNetPay((int) netPay);

		payrollDAO.payrollPays(employeePayScale, empCode);

		model.addAttribute("employeePayScale", employeePayScale);
		model.addAttribute("dailySalary", dailySalary);
		model.addAttribute("grossSalary", (int) checkinsSalary);
		model.addAttribute("pfDeduction", pfDeduction);
		model.addAttribute("netPay", netPay);
		model.addAttribute("salary", salary);
		return "employeePayslip.jsp";
	}

	@PostMapping("/absence")
	public String getEmployeeAbsence(RedirectAttributes redirectAttributes,HttpSession session,Model model) {
		int permissionCount,sickLeaveDays,casualLeaveDays;
		EmployeePayScale employeePayScale = new EmployeePayScale();
		int empCode = (Integer) session.getAttribute("emp_code");
		permissionCount = payrollDAO.countPermissionsPayroll(empCode);
		sickLeaveDays = payrollDAO.countSickLeavePayroll(empCode);
		casualLeaveDays = payrollDAO.countCasualLeavePayroll(empCode);
	
		int remainPermissionCount = permissionCount - 2;
		int remainSickLeaveDays = sickLeaveDays - 2;
		int remainCasualLeaveDays = casualLeaveDays - 1;
		
		employeePayScale.setSickLeaveDays(remainSickLeaveDays);
		employeePayScale.setCasualLeaveDays(remainCasualLeaveDays);
		employeePayScale.setPayrollPermission(String.valueOf(remainPermissionCount));
		model.addAttribute("employeePayScale", employeePayScale);

		
		return "leaveBalance.jsp";

	}




}