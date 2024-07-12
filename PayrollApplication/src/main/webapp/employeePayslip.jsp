<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.chainsys.payrollapplication.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payslip</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            color: #333;
        }
        .payroll-table {
            width: 80%; 
            margin: auto; 
            border-collapse: collapse;
            margin-top: 20px;
        }
        .payroll-table th, .payroll-table td {
            border: 1px solid #ddd;
            padding: 8px; 
            text-align: left;
        }
        .payroll-table th {
            background-color: #4CAF50;
            color: white;
        }
        .payroll-table tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .payroll-table tr:hover {
            background-color: #ddd;
        }
        .header, .footer {
            text-align: center;
            margin: 20px 0;
        }
        .container {
            width: 80%;
            margin: auto;
            border: 1px solid #ddd;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 0px 10px #ddd;
        }
        .company-details {
            text-align: center;
            margin-bottom: 20px;
        }
        .company-details img {
            width: 150px;
            height: auto;
            margin-bottom: 1.5%;
        }
        .company-details p {
            margin: 0;
        }
        .header {
            font-size: 24px;
            color: #4CAF50;
        }
        .footer {
            margin-top: 20px;
            color: #777;
        }
        .back-button {
    display: flex;
    justify-content: center;
    margin-top: 20px; 
}

.back-button a {
    display: inline-block;
    padding: 10px 20px;
    background-color: firebrick;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.back-button a:hover {
    background-color: brown;
}
        
  
    </style>
</head>
<body>

<div class="container">
    <div class="company-details">
        <img src="images/a6.png" alt="Company Logo">
        <p>101, Test Tower A.R. Street,<br> Bengaluru-400896, Karnataka, India<br> Tel: +123-456-7890, email: innowell@xyz.com</p>
    </div>

    <h2 class="header">SALARY SLIP</h2>
    
    <table class="payroll-table">
        <tr>
            <th>Field</th>
            <th>Amount</th>
        </tr>
        <%
            EmployeePayScale employeePayScale = (EmployeePayScale) request.getAttribute("employeePayScale");
            double dailySalary = (Double) request.getAttribute("dailySalary");
            int grossSalary = (Integer) request.getAttribute("grossSalary");
            double pfDeduction = (Double) request.getAttribute("pfDeduction");
            double netPay = (Double) request.getAttribute("netPay");
        %>
        <tr>
        <td>EmpCode</td>
         <td><%= employeePayScale.getEmpCode()%></td>
        </tr>
        
         <tr>
        <td>Name</td>
         <td><%= employeePayScale.getUsername() %></td>
        </tr>
         <tr>
        <td>Email</td>
         <td><%= employeePayScale.getUserEmail() %></td>
        </tr>
        <tr>
            <td>Working Days</td>
            <td><%= employeePayScale.getWorkingDays()%></td>
        </tr>
        <tr>
            <td>Daily Payscale</td>
            <td><%= String.format("%.2f", dailySalary) %></td>
        </tr>
        <tr>
            <td>Permission Count</td>
            <td><%= employeePayScale.getPayrollPermission() %></td>
        </tr>
        <tr>
            <td>Sick Leave Days</td>
            <td><%= employeePayScale.getSickLeaveDays() %></td>
        </tr>
        <tr>
            <td>Casual Leave Days</td>
            <td><%= employeePayScale.getCasualLeaveDays() %></td>
        </tr>
        <tr>
    <td>Permission Deduction</td>
    <td><%= String.format("%.2f", employeePayScale.getPermissionPayscale()) %></td>
</tr>
<tr>
    <td>Sick Deduction</td>
    <td><%= String.format("%.2f", employeePayScale.getSickLeavePayscale()) %></td>
</tr>
<tr>
    <td>Casual Deduction</td>
    <td><%= String.format("%.2f", employeePayScale.getCasualLeavePayscale()) %></td>
</tr>

        <tr>
            <td>Gross Pay</td>
            <td><%= grossSalary %></td>
        </tr>
        <tr>
            <td>Pf Deduction</td>
            <td><%= String.format("%.2f", pfDeduction) %></td>
        </tr>
        <tr>
            <td>Net Pay</td>
            <td><%= String.format("%.2f", netPay) %></td>
        </tr>
     
    </table>
  <div class="back-button">
    <a href="home.jsp">Back</a>
</div>
    <p class="footer">&copy; 2024 Innowell Engineering International Pvt Ltd.</p>
</div>

</body>
</html>
