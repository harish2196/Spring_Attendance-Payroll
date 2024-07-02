<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chainsys.payrollapplication.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
     body {
            font-family: Arial, sans-serif;
           
            background-color: linen;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            transition: background-color 0.3s ease;
        }
        th, td {
            padding: 12px;
            text-align: left;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            text-align: center;
            color: #333;
            text-decoration: underline;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .button-container {
            text-align: center;
        }
        .button-container button {
            margin: 5px;
            padding: 5px 10px;
            cursor: pointer;
        }
      .delete-btn {
            background-color: darkgreen;
            color: white;
            border: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .delete-btn:hover {
            background-color: #45a049;
        }
        .delete-button {
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .delete-button:hover {
            background-color: firebrick;
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
   .btn-search {
            background-color: green;
            border-color: green;
            color: white;
            padding: 3.3px 7px;
            margin-left:0.2%;
            margin-top:-0.2%;
        }
        .btn-search:hover {
            background-color: darkgreen;
            border-color: darkgreen;
        }
     
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">I N N O W E L L</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
             
                <li class="nav-item">
                    <p style="color: white; margin-top: 6.7%; bottom: 0;">
                        Welcome, <%= session.getAttribute("username") %>
                    </p>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="adminDashboard.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="JoinUs.jsp">Join Us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Contact.jsp">Contact</a>
                </li>
                <li class="nav-item">
                    <form action="Login" class="logout" method="get">
                        <a href="http://localhost:8080/ProductServlet/">
                            <input type="submit" value="Logout">
                        </a>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container" style="margin-top: 20px;">
    <h1 style="text-align: center; margin-bottom: 20px;">Employee Personal Details</h1>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th>Employee Code</th>
                <th>Name</th>
                <th>Designation</th>
                <th>Email</th>
                <th>Mobile</th>
                <th>Salary</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                Employees employee = (Employees) request.getAttribute("employee");
                if (employee != null) {
            %>
                <tr>
                  <td><%= employee.getEmpCode() %></td>
    <td><%= employee.getUserName() %></td>
    <td><%= employee.getDesignation() %></td>
    <td><%= employee.getUserEmail() %></td>
    <td><%= employee.getUserMobile() %></td>
    <td><%= employee.getSalary() %></td>
                    <%-- <td>
                        <img src="<%= employee.getImageUrl() %>" alt="Employee Image" width="100" height="100"/>
                    </td> --%>
                    <td>
                       
                        <a class="btn btn-primary" href="update.jsp?id=<%= employee.getEmpCode() %>&name=<%= employee.getUserName()%> ">Update</a>
                        <form action="UpdateAndDelete" method="post" style="display: inline-block;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= employee.getEmpCode() %>">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            <% } else { %>
                <tr>
                    <td colspan="8">No employee found.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

</body>
</html>
