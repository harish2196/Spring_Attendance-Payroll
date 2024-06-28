package com.chainsys.payrollapplication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.chainsys.payrollapplication.model.EmployeeDetails;

@Repository
public class PayrollDAOImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertEmployee(EmployeeDetails employeeDetails) {
    	String insertQuery = "INSERT INTO employee (emp_code, username, designation, useremail, userpassword, usermobile, image, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = { employeeDetails.getEmp_code(), employeeDetails.getUsername(), 
                            employeeDetails.getDesignation(), employeeDetails.getUseremail(), 
                            employeeDetails.getUserpassword(), employeeDetails.getUsermobile(), 
                            employeeDetails.getImageData(), employeeDetails.getSalary() };

        int rowsAffected = jdbcTemplate.update(insertQuery, params);
        System.out.println("Rows Affected: " + rowsAffected);
    }
}
