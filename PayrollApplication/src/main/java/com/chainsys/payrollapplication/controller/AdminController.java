package com.chainsys.payrollapplication.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chainsys.payrollapplication.dao.MailRespond;
import com.chainsys.payrollapplication.dao.PayrollDAO;
import com.chainsys.payrollapplication.dao.Validations;
import com.chainsys.payrollapplication.model.AdminReport;
import com.chainsys.payrollapplication.model.CheckInsAndCheckOuts;
import com.chainsys.payrollapplication.model.EmployeePayScale;
import com.chainsys.payrollapplication.model.Employees;
import com.chainsys.payrollapplication.model.LeaveReport;
import com.chainsys.payrollapplication.model.PayrollList;
import com.chainsys.payrollapplication.model.PermissionCount;

import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController {

	@Autowired
	PayrollDAO payrollDAO;

	@RequestMapping("/admin")
	public String adminLog() {
		return "adminLogin"; 
	}  

	@RequestMapping("/homePageAdmin")
	public String adminHome() {
		return "adminDashboard"; 
	}  

	@RequestMapping("/viewEmployees")
	public String getAllEmp() {
		return "viewEmployees"; 
	} 

	@RequestMapping("/updatePage")
	public String updateEmployee() {
		return "updateEmployee"; 
	} 

	@RequestMapping("/checkInsOuts")
	public String employeeCheckInsouts() {
		return "checkInsOuts"; 
	} 

	@RequestMapping("/adminChoice")
	public String adminSwitch() {
		return "adminChoice"; 
	}

	@RequestMapping("/employeePayslip")
	public String payslip() {
		return "employeePayscale"; 
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
			if (success) {
				empCode = payrollDAO.getEmployeeCode(name);
				session.setAttribute("username", name);
				session.setAttribute("emp_code", empCode);
				payrollDAO.insertCheckInTime(empCode, name);

				if (password.equals("1234")) {
					redirectAttributes.addFlashAttribute("specialAccess", true);
					return "redirect:/homePageAdmin"; 
				} else {
					redirectAttributes.addFlashAttribute("status", "failed");
					//return "redirect:/admin"; 
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
	public String getEmployeeDetails(Model model) {
		List<Employees> employees = payrollDAO.viewEmployeeDetails();
		model.addAttribute("employeeList", employees); 
		return "viewEmployees"; 
	}


	@PostMapping("/update")
	public String updateEmployee(
			@RequestParam("id") int id,@RequestParam("name") String name,@RequestParam("role") String designation,@RequestParam("email") String email,
			@RequestParam("contact") String phoneNumber,@RequestParam("salary") int salary,Model model) {
		Validations validations=new  Validations();
		validations.validateString(name);
		validations.validateString(designation);
		validations.isEmailChecker(email);
		validations.isPhoneNumber(phoneNumber);
		validations.isNumerics(salary);

		Employees employee = new Employees();
		employee.setEmpCode(id);
		employee.setUserName(name);
		employee.setDesignation(designation);
		employee.setUserEmail(email);
		employee.setUserMobile(phoneNumber);
		employee.setSalary(salary);
		payrollDAO.updateEmployeeDetails(employee);
		List<Employees> updatedEmployeeList = payrollDAO.viewEmployeeDetails();
		model.addAttribute("employeeList", updatedEmployeeList);
		return "viewEmployees"; 
	}

	@PostMapping("/delete")
	public String deleteEmployee(@RequestParam("id") int id, Model model) {

		payrollDAO.deleteEmployeeById(id);
		List<Employees> updatedEmployeeList = payrollDAO.viewEmployeeDetails(); 
		model.addAttribute("employeeList", updatedEmployeeList);
		return "viewEmployees"; 

	}

	@PostMapping("/reportSearch")  
	public String getEmployeeReports(@RequestParam("empcode") int empCode,Model model) {
		List<AdminReport> adminReport= payrollDAO.getReport(empCode);	
		model.addAttribute("adminReport", adminReport); 
		return "comment.jsp"; 
	}

	@PostMapping("/switch")
	public String permissionAndLeave(@RequestParam("action") String action, @RequestParam("option") String option,
			Model model) {
		if ("thisorthat".equals(action)) {
			if ("permission".equalsIgnoreCase(option)) {
				ArrayList<PermissionCount> permissionCount = new ArrayList<>(payrollDAO.getPermissionInfo());
				model.addAttribute("permissionCount", permissionCount);
				return "permissionInfo"; 
			} else if ("Leave".equalsIgnoreCase(option)) {
				ArrayList<LeaveReport> leaveReport = new ArrayList<>(payrollDAO.getAllLeaveReports());
				model.addAttribute("leaveReport", leaveReport);
				return "leaveInfo"; 
			} else {
				return "hello.jsp"; 
			}
		} else {
			return "hello.jsp"; 
		}
	}


	@PostMapping("/grantPermission")
	public String grantPermission(@RequestParam("empCode") int empCode,
			@RequestParam("action") String status,
			Model model) {

		if (status != null) {
			try {
				boolean isSuccess = payrollDAO.updatePermissionStatus(empCode, status);			
			} catch (Exception e) {
				System.out.println(e.getMessage());				
			}
		}

		List<PermissionCount> permissionCount = payrollDAO.getPermissionInfo(); 
		model.addAttribute("permissionCount", permissionCount);
		return "permissionInfo"; 
	}

	@PostMapping("/leaveCount")
	public String leaveStatus(@RequestParam("empCode") int empCode,
			@RequestParam("action") String action,@RequestParam("id") int id,@RequestParam("toDate") String toDate,@RequestParam("fromDate") String fromDate,
			Model model) {
		if (action != null) {
			if (action.equalsIgnoreCase("rejected")) {
				payrollDAO.getTotalLeaveDays(id, toDate, fromDate);
				payrollDAO.updateLeaveStatus(id,"Rejected");	  
			} else if (action.equalsIgnoreCase("accepted")) {    	 
				int totalLeaveDays = payrollDAO.getTotalLeaveDays(id,toDate,fromDate);
				payrollDAO.insertTotalLeaveDays(id, totalLeaveDays);
				payrollDAO.updateLeaveStatus(id, "Accepted");
			}
		}

		ArrayList<LeaveReport> leaveReport = new ArrayList<>(payrollDAO.getAllLeaveReports());
		model.addAttribute("leaveReport", leaveReport);
		return "leaveInfo"; 		
	}

	@PostMapping("/search")   
	public String retrieveAllEmployees(@RequestParam("empcode") int empCode,HttpSession session) {
		ArrayList<CheckInsAndCheckOuts> checkInsAndCheckOuts= (ArrayList<CheckInsAndCheckOuts>) payrollDAO.getEmployeeCheckInOut(empCode);	
		session.setAttribute("checkInsAndCheckOuts", checkInsAndCheckOuts); 
		return "checkInOuts.jsp"; 
	}

	@GetMapping("/payscale")
	public String employeePayScale(HttpSession session,Model model) {
		List<EmployeePayScale> employeePayScale = payrollDAO.getAllEmployeePayScales();
		model.addAttribute("employeePayScale", employeePayScale);
		return "employeePayscale";
	}


	@PostMapping("/payslip")
	public String employeepayslip(@RequestParam("empCode") int empCode,@RequestParam("allocatedSalary") String allocatedSalary,
			RedirectAttributes redirectAttributes,HttpSession session,
			Model model) {

		payrollDAO.salaryCredited(empCode);		
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
		return "payslipcalculation";
	}

	@PostMapping("/SalarySlip")
	public String sendSalarySlipEmail(
			@RequestParam("empCode") String empCode,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("totalCheckinCount") int totalCheckinCount,
			@RequestParam("allocatedSalary") double allocatedSalary,
			@RequestParam("permissionCount") int permissionCount,
			@RequestParam("sickLeaveDays") int sickLeaveDays,
			@RequestParam("casualLeaveDays") int casualLeaveDays,
			@RequestParam("permissionPayroll") double permissionPayroll,
			@RequestParam("casualPayroll") double casualPayroll,
			@RequestParam("sickPayroll") double sickPayroll,
			@RequestParam("grossSalary") int grossSalary,
			@RequestParam("pfDeduction") String pfDeduction,
			@RequestParam("netPay") String netPay,
			Model model) {

		model.addAttribute("empCode", empCode);
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("totalCheckinCount", totalCheckinCount);
		model.addAttribute("permissionCount", permissionCount);
		model.addAttribute("sickLeaveDays", sickLeaveDays);
		model.addAttribute("casualLeaveDays", casualLeaveDays);		
		model.addAttribute("permissionPayroll", permissionPayroll);
		model.addAttribute("casualPayroll", casualPayroll);
		model.addAttribute("sickPayroll", sickPayroll);
		model.addAttribute("grossSalary", grossSalary);
		model.addAttribute("pfDeduction", pfDeduction);
		model.addAttribute("netPay", netPay);
		model.addAttribute("allocatedSalary", allocatedSalary);

		String mailBody = "Dear " + name + ",\n" +
				"Your payslip details for the current month are as follows:\n\n" +
				"EmpCode: " + empCode + "\n" +
				"Working days: " + totalCheckinCount + "\n" +
				"Allocated Salary: Rs." + allocatedSalary + "\n" +
				"Permission Count: " + permissionCount + "\n" +
				"Sick Leave Days: " + sickLeaveDays + "\n" +
				"Casual Leave Days: " + casualLeaveDays + "\n" +			
				"Permission Deduction: " + String.format("%.2f", permissionPayroll)  + "\n" +
				"Casual Leave Deduction: " + String.format("%.2f", casualPayroll) + "\n" +
				"sickLeave Deduction: " + String.format("%.2f", sickPayroll )+ "\n" +
				"Gross Salary: Rs." + grossSalary + "\n" +
				"PF Deduction: Rs." + pfDeduction + "\n" +
				"Net Pay: Rs." + netPay + "\n\n" +
				"Thank you for your hard work.\n\n" +
				"Regards,\n" +
				"HR Department";

		try {
			MailRespond.Properties();
			MailRespond.MailBody(email,mailBody);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		List<EmployeePayScale> employeePayScale = payrollDAO.getAllEmployeePayScales();
		return "redirect:/payscale"; 
	}

	@PostMapping("/employeeSearch")  
	public String getAllEmployees(@RequestParam("empcode") int empCode,HttpSession session,Model model) {
		List<Employees> employee= payrollDAO.getEmployeeDeatils(empCode);	
		model.addAttribute("employee", employee); 
		return "displayEmployees.jsp"; 
	}

	@PostMapping("/searchPayscale")
	public String searchEmployeePayscale(@RequestParam("empcode") int empCode,Model model) {
		List<EmployeePayScale> employeePayScale = payrollDAO.searchEmployeePayScales(empCode);
		model.addAttribute("employeePayScale", employeePayScale);
		return "employeePayscale.jsp";
	}

	@PostMapping("/searchPermission")
	public String searchEmployeePermission(@RequestParam("empcode") int empCode,Model model) {
		ArrayList<PermissionCount> permissionCount = new ArrayList<>(payrollDAO.searchPermission(empCode));
		model.addAttribute("permissionCount", permissionCount);
		return "permissionInfo.jsp"; 
	}

	@PostMapping("/searchLeave")
	public String searchEmployeeLeave(@RequestParam("empcode") int empCode,Model model) {
		ArrayList<LeaveReport> leaveReport = new ArrayList<>(payrollDAO.searchLeaveReports(empCode));
		model.addAttribute("leaveReport", leaveReport);
		return "leaveInfo.jsp"; 
	}


	//	@GetMapping("/employeeAbsence")
	//    public void employeeAbsence(Model model) {
	//      
	//        List<Integer> allEmployeeCodes = payrollDAO.getExistingEmployeeCodes();
	//        List<CheckInsAndCheckOuts> existingEmployeeCodes = payrollDAO.getEmployeeCodes("2024-07-22");
	//  
	//        List<Integer> existingEmpCodes = existingEmployeeCodes.stream()
	//                .map(CheckInsAndCheckOuts::getEmpCode)
	//                .collect(Collectors.toList());
	//
	//        List<Integer> missingEmployeeCodes = allEmployeeCodes.stream()
	//                .filter(empCode -> !existingEmpCodes.contains(empCode))
	//                .collect(Collectors.toList());
	//
	//        model.addAttribute("employeeCodes", missingEmployeeCodes);
	//       
	//    }

	@PostMapping("/adminReport")
	public String adminReport(
			@RequestParam("employeeCode") int employeeCode,@RequestParam("name") String name,@RequestParam("projectTitle") String projectTitle,@RequestParam("projectFeatures") String projectFeatures,
			@RequestParam("timeDuration") int timeDuration,HttpSession session,RedirectAttributes redirectAttributes) {
		AdminReport adminReport=new AdminReport();
		Validations validations=new  Validations();
		validations.validateString(name);

		adminReport.setEmpCode(employeeCode);
		adminReport.setName(name);
		adminReport.setProjectTitle(projectTitle);
		adminReport.setProjectFeatures(projectFeatures);
		adminReport.setTimeDurations(timeDuration);

		payrollDAO.insertAdminReport(adminReport);
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/report"; 
	}

	@GetMapping("/adminViewTask")
	public String adminReport(Model model) {
		List<AdminReport> adminReport = payrollDAO.getAdminTaskList();
		model.addAttribute("adminReport", adminReport); 
		return "adminTask";   
	}

	@GetMapping("/checkInOut")
	public String checkInOut(HttpSession session,Model model) {
		List<CheckInsAndCheckOuts> checkInsAndCheckOuts = payrollDAO.viewCheckInsAndOuts(); 
		List<Employees> allEmployeeCodes = payrollDAO.getExistingEmployeeCodes();
		List<CheckInsAndCheckOuts> existingEmployeeCodes = payrollDAO.getEmployeeCodes("2024-07-22");

		List<Integer> existingEmpCodes = existingEmployeeCodes.stream()
				.map(CheckInsAndCheckOuts::getEmpCode )
				.collect(Collectors.toList());
		
		List<CheckInsAndCheckOuts> missingEmployeeCodes = allEmployeeCodes.stream()
				.filter(empCode -> !existingEmpCodes.contains(empCode.getEmpCode()))
				.map(e -> new CheckInsAndCheckOuts(e.getEmpCode(),e.getUserName()))
				.collect(Collectors.toList());

		session.setAttribute("employeeCodes", missingEmployeeCodes);
		session.setAttribute("checkInsAndCheckOuts", checkInsAndCheckOuts); 
		return  "redirect:/checkInsOuts";
	}
	
	
	@GetMapping("/filterAbsentees")
	public String filterByDateCheckIns(@RequestParam("filterDate")String filterDate,HttpSession session,Model model) {
		List<CheckInsAndCheckOuts> checkInsAndCheckOuts = payrollDAO.viewCheckInsAndOuts(); 
		List<Employees> allEmployeeCodes = payrollDAO.getExistingEmployeeCodes();
		List<CheckInsAndCheckOuts> existingEmployeeCodes = payrollDAO.getEmployeeCodes(filterDate);

		List<Integer> existingEmpCodes = existingEmployeeCodes.stream()
				.map(CheckInsAndCheckOuts::getEmpCode )
				.collect(Collectors.toList());
		
		List<CheckInsAndCheckOuts> missingEmployeeCodes = allEmployeeCodes.stream()
				.filter(empCode -> !existingEmpCodes.contains(empCode.getEmpCode()))
				.map(e -> new CheckInsAndCheckOuts(e.getEmpCode(),e.getUserName()))
				.collect(Collectors.toList());

		session.setAttribute("employeeCodes", missingEmployeeCodes);
		session.setAttribute("checkInsAndCheckOuts", checkInsAndCheckOuts); 
		return  "redirect:/checkInsOuts";
		
	}


}
