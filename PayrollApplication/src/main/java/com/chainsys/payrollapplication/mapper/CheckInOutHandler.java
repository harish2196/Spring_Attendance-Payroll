package com.chainsys.payrollapplication.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.CheckInsAndCheckOuts;

public class CheckInOutHandler implements RowMapper<CheckInsAndCheckOuts> {

    public CheckInsAndCheckOuts mapRow(ResultSet rs, int rowNum) throws SQLException {
        CheckInsAndCheckOuts checkInsAndCheckOuts = new CheckInsAndCheckOuts();
        checkInsAndCheckOuts.setEmpCode(rs.getInt("emp_code"));
        checkInsAndCheckOuts.setName(rs.getString("name"));
        checkInsAndCheckOuts.setCheckIn(rs.getString("checkin_time"));
        checkInsAndCheckOuts.setCheckOut(rs.getString("checkout_time"));
        return checkInsAndCheckOuts;
    }
	
}