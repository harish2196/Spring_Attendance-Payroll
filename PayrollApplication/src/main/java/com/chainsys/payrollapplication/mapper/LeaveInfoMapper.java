package com.chainsys.payrollapplication.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.LeaveReport;

public class LeaveInfoMapper implements RowMapper<LeaveReport> {

	@Override
	public LeaveReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		LeaveReport leaveReport = new LeaveReport();
		leaveReport.setEmpCode(rs.getInt("emp_code"));
		leaveReport.setName(rs.getString("name"));
		leaveReport.setFromdate(rs.getString("from_date"));
		leaveReport.setTodate(rs.getString("to_date"));
		leaveReport.setLeaveType(rs.getString("leave_type"));
		leaveReport.setLeaveCount(rs.getString("leave_count"));
		leaveReport.setLeaveStatus(rs.getString("status"));

		return leaveReport;
	}
}
