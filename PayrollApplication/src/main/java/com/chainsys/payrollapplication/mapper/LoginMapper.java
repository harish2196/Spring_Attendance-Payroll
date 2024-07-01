package com.chainsys.payrollapplication.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.EmployeeDetails;

public class LoginMapper  implements RowMapper<EmployeeDetails> {

	@Override
	public EmployeeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {		
		 EmployeeDetails employeeDetails = new EmployeeDetails();
	        employeeDetails.setUsername(rs.getString("username"));
	        employeeDetails.setUserpassword(rs.getString("userpassword"));
			return employeeDetails;
	}

}
