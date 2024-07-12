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

	@RequestMapping("/approvedpayslip")
	public String employeePayslip() {
		return "employeePayslip.jsp"; 
	}  

	@RequestMapping("/admin")
	public String adminLog() {
		return "adminLogin.jsp"; 
	}  
	@RequestMapping("/home")
	public String homePage() {
		return "home.jsp"; 
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
			@RequestParam("reason") String message,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		Validations validations=new Validations();

		validations.validateString(name);
		PermissionCount permissionCount = new PermissionCount();
		permissionCount.setName(name);
		permissionCount.setDate(date);
		permissionCount.setStartTime(startTime);
		permissionCount.setEndTime(endTime);
		permissionCount.setInfoText(message);
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

	@PostMapping("/timeSheet")
	public String timeSheetValidation(HttpSession session,Model model) {
		int empCode = (Integer) session.getAttribute("emp_code");

		String empName=payrollDAO.getEmployeeName(empCode);//name
		int hour=payrollDAO.getTotalWorkingHours(empCode);//hour
		String empEmail= payrollDAO.getEmployeeEmail(empCode);//email
		int permissionCount = payrollDAO.countPermissionsPayroll(empCode);//permission
		int sickLeaveDays = payrollDAO.countSickLeavePayroll(empCode);//sick
		int casualLeaveDays = payrollDAO.countCasualLeavePayroll(empCode);//casual
		int totalCheckinCount = payrollDAO.getTotalCheckinCount(empCode);//workingDays
		int salary= payrollDAO.getEmployeeSalary(empCode);//salary
		payrollDAO.salaryPending(empCode); 

		PayrollList payrollList=new PayrollList();
		payrollList.setEmpCode(empCode);
		payrollList.setEmpName(empName);
		payrollList.setEmpEmail(empEmail);
		payrollList.setPermissionCount(permissionCount);
		payrollList.setSickLeaveDays(sickLeaveDays);
		payrollList.setCasualLeaveDays(casualLeaveDays);
		payrollList.setTotalCheckinCount(totalCheckinCount);
		payrollList.setWorkingHours(hour);
		payrollList.setSalary(salary);

		payrollDAO.insertOrUpdateLeavePermission(payrollList);
		model.addAttribute("payrollList", payrollList);
		return "payrollCalculation.jsp";
	}


	@PostMapping("/employeePayslip")
	public String viewEmployeepayslip(RedirectAttributes redirectAttributes, HttpSession session, Model model) {
		int empCode = (Integer) session.getAttribute("emp_code");

		String name = payrollDAO.getEmployeeName(empCode);
		String email = payrollDAO.getEmployeeEmail(empCode);
		int totalCheckinCount = payrollDAO.getTotalCheckinCount(empCode);
		int sickLeaveDays = payrollDAO.countSickLeavePayroll(empCode);
		int casualLeaveDays = payrollDAO.countCasualLeavePayroll(empCode);
		int permissionCount = payrollDAO.countPermissionsPayroll(empCode);
		int allocateSalary = payrollDAO.getEmployeePayscaleSalary(empCode);

		boolean isCredited = payrollDAO.isSalaryCredited(empCode);
		if (!isCredited) {
			redirectAttributes.addFlashAttribute("status", "failed");
			System.out.println("Salary status is not 'Credited' for empCode: " + empCode);
			return "redirect:/home";
		}

		EmployeePayScale employeePayScale = new EmployeePayScale();
		employeePayScale.setEmpCode(empCode);
		employeePayScale.setUsername(name);
		employeePayScale.setUserEmail(email);
		employeePayScale.setWorkingDays(totalCheckinCount);
		employeePayScale.setSickLeaveDays(sickLeaveDays);
		employeePayScale.setCasualLeaveDays(casualLeaveDays);
		employeePayScale.setSalary(allocateSalary);
		employeePayScale.setPayrollPermission(String.valueOf(permissionCount));


		double dailySalary = allocateSalary / 22.0;
		double checkinsSalary = dailySalary * totalCheckinCount;
		double halfDayDeduction = 0.0, fullDayDeduction = 0.0;

		if (permissionCount > 2) {
			halfDayDeduction = (permissionCount - 2) * (dailySalary / 2);
			checkinsSalary -= halfDayDeduction;
		}

		if (sickLeaveDays >= 3) {
			fullDayDeduction = (sickLeaveDays - 2) * dailySalary;
			checkinsSalary -= fullDayDeduction;
		}

		if (casualLeaveDays >= 2) {
			fullDayDeduction = (casualLeaveDays - 1) * dailySalary;
			checkinsSalary -= fullDayDeduction;
		}

		employeePayScale.setPermissionPayscale(halfDayDeduction);
		employeePayScale.setSickLeavePayscale(fullDayDeduction);
		employeePayScale.setCasualLeavePayscale(fullDayDeduction);

		double pfDeduction = checkinsSalary * 0.12;
		double netPay = checkinsSalary - pfDeduction;

		employeePayScale.setGrossPay(checkinsSalary);
		employeePayScale.setPf((int) pfDeduction);
		employeePayScale.setNetPay((int) netPay);

		payrollDAO.payrollPays(employeePayScale, empCode);

		model.addAttribute("employeePayScale", employeePayScale);
		model.addAttribute("dailySalary", dailySalary);
		System.err.println("--->"+dailySalary);
		model.addAttribute("grossSalary", (int) checkinsSalary);
		model.addAttribute("pfDeduction", pfDeduction);
		model.addAttribute("netPay", netPay);
		model.addAttribute("salary", allocateSalary);

		return "employeePayslip.jsp";
	}

	@PostMapping("/absence")
	public String getEmployeeAbsence(RedirectAttributes redirectAttributes,HttpSession session,Model model) {
		int permissionCount,sickLeaveDays,casualLeaveDays,totalCheckinCount;
		String name;
		EmployeePayScale employeePayScale = new EmployeePayScale();
		int empCode = (Integer) session.getAttribute("emp_code");
		permissionCount = payrollDAO.countPermissionsPayroll(empCode);
		sickLeaveDays = payrollDAO.countSickLeavePayroll(empCode);
		casualLeaveDays = payrollDAO.countCasualLeavePayroll(empCode);
		name = payrollDAO.getEmployeeName(empCode);
		totalCheckinCount = payrollDAO.getTotalCheckinCount(empCode);

		int remainPermissionCount = 2 - permissionCount;
		int remainSickLeaveDays = 2 - sickLeaveDays;
		int remainCasualLeaveDays = 1 - casualLeaveDays;

		employeePayScale.setEmpCode(empCode);
		employeePayScale.setUsername(name); 
		employeePayScale.setWorkingDays(totalCheckinCount);
		employeePayScale.setSickLeaveDays(remainSickLeaveDays);
		employeePayScale.setCasualLeaveDays(remainCasualLeaveDays);
		employeePayScale.setPayrollPermission(String.valueOf(remainPermissionCount));

		model.addAttribute("employeePayScale", employeePayScale);


		return "leaveBalance.jsp";

	}




}