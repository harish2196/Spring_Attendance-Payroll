package com.chainsys.payrollapplication.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.Employees;

public class LoginMapper  implements RowMapper<Employees> {

	@Override
	public Employees mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Employees employeeDetails = new Employees();
	        employeeDetails.setUserName(rs.getString("username"));
	        employeeDetails.setUserPassword(rs.getString("userpassword"));
			return employeeDetails;
	}

}
