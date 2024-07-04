package com.chainsys.payrollapplication.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.AdminReport;

public class AdminReportMapper implements RowMapper<AdminReport> {

	@Override
	public AdminReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminReport adminReport=new AdminReport();
		adminReport.setEmpCode(rs.getInt("emp_code"));
		adminReport.setName(rs.getString("name"));
		adminReport.setText(rs.getString("report_text"));
		return adminReport;
		
	}

}
