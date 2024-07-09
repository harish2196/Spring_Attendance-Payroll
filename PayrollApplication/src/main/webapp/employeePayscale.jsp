<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.chainsys.payrollapplication.model.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Employee Pay Scales</title>
 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
   
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
         
        }

        h2 {
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #e0e0e0;
        }

        .button-container {
            display: flex;
            justify-content: center;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
         .custom-nav {
            background-color: lightgray;
            padding: 10px 5px;
            width: 39%;
          margin-left: 27%;
          margin-top: 3%;
            border-radius: 20px;
            overflow: hidden;
            border: none;
            box-shadow: 1px 7px 10px rgba(0, 0, 0, 0.2);
        }

        .custom-nav a {
            float: left;
            display: block;
            color: #333;
           
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        .custom-nav a:hover {
            background-color: #ddd;
            color: black;
        }

        .custom-dropdown {
            float: left;
            overflow: hidden;
        }

        .custom-dropdown .custom-dropbtn {
            font-size: 17px;
            border: none;
            outline: none;
            color: #333;
            padding: 14px 16px;
            background-color: inherit;
            margin: 0;
        }

        .custom-dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
            z-index: 1;
        }

        .custom-dropdown-content a {
            float: none;
            color: #333;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            text-align: left;
        }

        .custom-dropdown-content a:hover {
            background-color: #ddd;
            color: black;
        }

        .custom-dropdown:hover .custom-dropdown-content {
            display: block;
        }

        .custom-dropdown:after {
            content: "";
            clear: both;
            display: table;
        }


.custom-dropdown-content .a1 button {
    border: none; 
    background: none;
    padding: 0; 
    font: inherit; 
    cursor: pointer; 
}

.custom-dropdown-content .a1 button:hover {
     background-color: lightgray; 
}
   .logout input[type="submit"] {
    background-color: transparent; 
    border: none;
    color: inherit;
    cursor: pointer; 
     color: lightgray;
     margin-top:10%;
   
}
.form-group{
margin-left:38%;
}

.btn-primary:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

    </style>



  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <a class="navbar-brand" href="#">I N N O W E L L</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
  
   <div>
<%--     <p style="color: white;margin-top:6.7%;bottom:0;margin-left=-60%">Welcome, <%= session.getAttribute("name") %></p>
 --%></div>

        <li class="nav-item">
            <a class="nav-link" href="adminDashboard.jsp">Home</a>
          </li>                   
          <li class="nav-item">
            <a class="nav-link" href="joinUs.jsp">Join Us</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="contact.jsp">Contact</a>
          </li>
           <li class="nav-item">
          <form action="/adminCheckOut" class="logout" method="get">
    <a href="login.jsp">     
        <input type="submit" value="Logout">
    </a>
</form>

          </li>
        </ul>
      </div>
    </div>
  </nav>
  <body>
    <h2 style="margin-left:38%;margin-top:2%;">Employee PayScales</h2>
    <form action="/searchPayscale" method="post" class="form-inline">
    <div class="form-group">
        <input type="text" name="empcode" class="form-control" placeholder="Search EmpCode">
    </div>
    <button type="submit" class="btn btn-primary ml-2">Search</button>
</form>
    
    <table border="1">
        <tr>
            <th>Employee Code</th>
            <th>Name</th>
            <th>Email</th>
            <th>Permission Count</th>
            <th>Sick Leave Days</th>
            <th>Casual Leave Days</th>
            <th>Working Days</th>
            <th>Working Hours</th>
            <th>Salary</th>
            <th>Credit Status</th>
          <!--   <th>Gross Pay</th> -->
            <th>Action</th>
        </tr>
        <%
            List<EmployeePayScale> employeePayScales = (List<EmployeePayScale>) request.getAttribute("employeePayScale");
            if (employeePayScales != null) {
                for (EmployeePayScale employeePayScale : employeePayScales) {
        %>
                    <tr>
                        <td><%= employeePayScale.getEmpCode() %></td>
                        <td><%= employeePayScale.getUsername() %></td>
                        <td><%= employeePayScale.getUserEmail() %></td>
                        <td><%= employeePayScale.getPayrollPermission() %></td>
                        <td><%= employeePayScale.getSickLeaveDays() %></td>
                        <td><%= employeePayScale.getCasualLeaveDays() %></td>
                        <td><%= employeePayScale.getWorkingDays() %></td>
                         <td><%= employeePayScale.getWorkingHours() %></td>
                        <td><%= employeePayScale.getSalary() %></td>
                          <td><%= employeePayScale.getSalaryStatus() %></td>
<%--                     <td><%= String.format("%.2f", employeePayScale.getGrossPay()) %></td>
 --%>
                        <td>
                            <div class="button-container">
                                <form action="/payslip" method="post">
                                    <input type="hidden" name="empCode" value="<%= employeePayScale.getEmpCode() %>">
                                     <input type="hidden" name="allocatedSalary" value="<%= employeePayScale.getSalary() %>">
                                    <button type="submit">Credit Salary</button>
                                </form>
                            </div>
                        </td>
                    </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
