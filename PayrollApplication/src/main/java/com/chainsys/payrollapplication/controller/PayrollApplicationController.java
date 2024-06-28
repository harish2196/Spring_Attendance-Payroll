package com.chainsys.payrollapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chainsys.payrollapplication.dao.PayrollDAOImpl;
import com.chainsys.payrollapplication.model.EmployeeDetails;

@Controller
public class PayrollApplicationController {

    @Autowired
    private PayrollDAOImpl payrollDAOImpl;
    
    @RequestMapping("/reg")
    public String showRegistrationForm() {
        return "registration.jsp"; 
    }   
    
    @RequestMapping("/register")
    public String handleFormSubmission(
            @RequestParam("name") String name, @RequestParam("role") String role,
            @RequestParam("email") String email, @RequestParam("contact") String contact,
            @RequestParam("pass") String password, @RequestParam("image") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        try {
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setUsername(name);
            employeeDetails.setDesignation(role);
            employeeDetails.setUseremail(email);
            employeeDetails.setUsermobile(contact);
            employeeDetails.setUserpassword(password);

            if (imageFile != null && !imageFile.isEmpty()) {
                employeeDetails.setImageData(imageFile.getBytes());
            }

            int randomNumber = 1000 + (int) (Math.random() * 9000);
            employeeDetails.setEmp_code(randomNumber);

            payrollDAOImpl.insertEmployee(employeeDetails);

            redirectAttributes.addFlashAttribute("status", "success");
            redirectAttributes.addFlashAttribute("randomNumber", randomNumber);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
            e.printStackTrace();
            return "redirect:/"; 
        }
     
        return "redirect:/success"; 
    }
}
