create database payroll_Application;
use payroll_Application;
drop database payroll_Application;

CREATE TABLE Employee_details (
  emp_code INT NOT NULL ,
  username VARCHAR(255),
  designation varchar(50),
  useremail VARCHAR(255) unique,
  userpassword VARCHAR(255),
  usermobile VARCHAR(255) unique,
 image longblob,
 salary int,
  PRIMARY KEY (emp_code),
  UNIQUE (useremail) 
);

select * from Employee_details;
CREATE TABLE checkins_checkouts(
    emp_code INT NOT NULL,
    name VARCHAR(255),
    checkin_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    checkout_time TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (emp_code) REFERENCES Employee_details(emp_code)
);
select * from checkins_checkouts;
truncate checkins_checkouts;

CREATE TABLE permission_count (
    emp_code INT NOT NULL,
    name VARCHAR(255),
    date VARCHAR(20),
    start_time VARCHAR(20), 
    end_time VARCHAR(20),   
    status VARCHAR(50) DEFAULT 'waiting', 
    permission VARCHAR(50) NOT NULL DEFAULT 'default_value',
    FOREIGN KEY (emp_code) REFERENCES Employee_details(emp_code)
);
select * from permission_count;
truncate permission_count;

CREATE TABLE Leave_report (
    emp_code INT NOT NULL,
    name VARCHAR(255),
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    leave_type VARCHAR(255),
    leave_Count INT,
     status VARCHAR(50) DEFAULT 'pending', 
    FOREIGN KEY (emp_code) REFERENCES Employee_details(emp_code)
);
select * from Leave_report;
drop table Leave_report;
CREATE TABLE admin_report (
  emp_code INT NOT NULL,
  name VARCHAR(255),
   report_text TEXT,
  FOREIGN KEY (emp_code) REFERENCES Employee_details(emp_code)
);
select * from admin_report;
truncate admin_report;

CREATE TABLE employee_payscale(
    id INT AUTO_INCREMENT PRIMARY KEY,
    emp_code INT NOT NULL,
    username VARCHAR(255)  DEFAULT 'none',
    useremail VARCHAR(255)  DEFAULT 'none',
    payroll_permission VARCHAR(50) DEFAULT 'none',
    sick_leaveDays INT NOT NULL default 0,
    casual_leaveDays INT NOT NULL default 0,
    working_days INT NOT NULL default 0,
    working_hours INT NOT NULL default 0,
     salary INT NOT NULL default 0,
     salary_status VARCHAR(20) DEFAULT 'pending',
    gross_pay INT NOT NULL default 0,
    Pf INT NOT NULL default 0,
    netpay INT NOT NULL default 0,
    FOREIGN KEY (emp_code) REFERENCES Employee_details(emp_code)
);

select * from employee_payscale;
drop table employee_payscale;