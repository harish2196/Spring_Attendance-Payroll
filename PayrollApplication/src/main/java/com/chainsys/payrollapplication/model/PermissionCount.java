package com.chainsys.payrollapplication.model;

public class PermissionCount {

    public int emp_code;
    public String name;
    public String date;
    public String start_time;
    public String end_time;
    public String status;
    public String permissionCount;

    public PermissionCount() {
    }

    public PermissionCount(int emp_code, String name, String date, String start_time, String end_time, String status, String permissionCount) {
        this.emp_code = emp_code;
        this.name = name;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
        this.permissionCount = permissionCount;
    }

    public int getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(int emp_code) {
        this.emp_code = emp_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPermissionCount() {
        return permissionCount;
    }

    public void setPermissionCount(String permissionCount) {
        this.permissionCount = permissionCount;
    }


    
}

