package com.chainsys.payrollapplication.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.PermissionCount;

public class PermissionMapper implements RowMapper<PermissionCount> {

    @Override
    public PermissionCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        PermissionCount permissionCount = new PermissionCount();
        permissionCount.setEmpCode(rs.getInt("emp_code"));
        permissionCount.setName(rs.getString("name"));
        permissionCount.setDate(rs.getString("date"));
        permissionCount.setStartTime(rs.getString("start_time"));
        permissionCount.setEndTime(rs.getString("end_time"));
        permissionCount.setStatus(rs.getString("status"));
        permissionCount.setPermissionCount(rs.getString("permission"));
        permissionCount.setInfoText(rs.getString("message_text"));
        return permissionCount;
    }
}
