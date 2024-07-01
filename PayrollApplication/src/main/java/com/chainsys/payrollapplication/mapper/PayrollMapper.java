package com.chainsys.payrollapplication.mapper;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.EmployeeDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PayrollMapper implements RowMapper<EmployeeDetails> {

    @Override
    public EmployeeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmp_code(rs.getInt("emp_code"));
        employeeDetails.setUsername(rs.getString("username"));
        employeeDetails.setDesignation(rs.getString("designation"));
        employeeDetails.setUseremail(rs.getString("useremail"));
        employeeDetails.setUserpassword(rs.getString("userpassword"));
        employeeDetails.setUsermobile(rs.getString("usermobile"));
        employeeDetails.setImageData(rs.getBytes("image"));
        employeeDetails.setSalary(rs.getInt("salary"));
        return employeeDetails;
    }
   
}
