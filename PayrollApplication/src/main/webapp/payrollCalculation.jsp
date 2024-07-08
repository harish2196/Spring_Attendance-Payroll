<%@ page import="java.util.*" %>
<%@ page import="com.chainsys.payrollapplication.model.*" %>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Payroll Information</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>      
        body {
            padding: 20px;
        }
        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        table th {
            background-color: #f2f2f2;
        }
        table tr:hover {
            background-color: #f5f5f5;
        }
        .back-button{
        margin-left:5.7%;
        color:firebrick;
        font-size:18px;
        }
    </style>
</head>
<body>

    <a href="home.jsp" class="back-button">
        <span>&lt;-</span> Back
    </a>

    <div class="container">
        <h2 class="text-center" style="color:dodgerblue">Employee Payroll Information</h2>
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>Employee Code</th>
                        <th>Employee Name</th>
                        <th>Employee Email</th>
                        <th>Permission Count</th>
                        <th>Sick Leave</th>
                        <th>Casual Leave</th>
                        <th>Working Days</th>
                        <th>Working Hours</th>
                        <th>Allocate Salary</th>
                    </tr>
                </thead>
                <tbody>
                    <% PayrollList payrollList = (PayrollList) request.getAttribute("payrollList");
                       if (payrollList != null) { %>
                        <tr>
                            <td><%= payrollList.getEmpCode() %></td>
                            <td><%= payrollList.getEmpName() %></td>
                            <td><%= payrollList.getEmpEmail() %></td>
                            <td><%= payrollList.getPermissionCount() %></td>
                            <td><%= payrollList.getSickLeaveDays() %></td>
                            <td><%= payrollList.getCasualLeaveDays() %></td>
                            <td><%= payrollList.getTotalCheckinCount() %></td>
                             <td><%= payrollList.getWorkingHours() %></td>
                            <td><%= payrollList.getSalary() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
