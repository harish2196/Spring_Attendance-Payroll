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
		adminReport.setProjectTitle(rs.getString("project_title"));
		adminReport.setProjectFeatures(rs.getString("project_features"));
		adminReport.setTimeDurations(rs.getInt("time_duration"));
		adminReport.setStatus(rs.getString("status"));
		adminReport.setReason(rs.getString("reason"));
		return adminReport;
		
	}

}
