<%@ page import="java.util.*" %>
<%@ page import="com.chainsys.payrollapplication.model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Permission Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/dataTables.bootstrap4.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            margin-top: 20px;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            cursor: pointer; 
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        form {
            text-align: center;
            margin-top: 20px;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            margin-left: 44%;
            cursor: pointer;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .custom-button {
            border: none;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        .custom-button:hover {
            background-color: #45a049;
        }

        .reject-button {
            background-color: #dc3545; 
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        .reject-button:hover {
            background-color: #c82333; 
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
            box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
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

        input[type="button"] {
            padding: 4px 10px;
            border: none;
            border-radius: 4px;
            background-color: #28a745;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="button"]:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">I N N O W E L L</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="adminDashboard.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="joinUs.jsp">Join Us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="contact.jsp">Contact</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<h1 style="text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);font-weight:bolder;">Employee Permission Details</h1>

<!-- <form action="/searchPermission" method="post">
    <input style="margin-left:40%;margin-top:0.3%;margin-bottom:1%" type="text" name="empcode" placeholder="Search EmpCode">
    <input type="submit" value="Search">
</form> -->
<div class="table-responsive">
    <table id="permissionDetailsTable" class="table table-striped table-hover">
        <thead class="thead-dark">
            <tr>
                <th>EmpCode</th>
                <th>Name</th>
                <th>Date</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Status</th>
                <th>Permission Count</th>
                <th>Message</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
            ArrayList<PermissionCount> permissionCount = (ArrayList<PermissionCount>) request.getAttribute("permissionCount");
            if (permissionCount != null && !permissionCount.isEmpty()) {
                for (PermissionCount permission : permissionCount) {
            %>
            <tr>
                <td><%= permission.getEmpCode() %></td>
                <td><%= permission.getName() %></td>
                <td><%= permission.getDate() %></td>
                <td><%= permission.getStartTime() %></td>
                <td><%= permission.getEndTime() %></td>
                <td><%= permission.getStatus() %></td>
                <td><%= permission.getPermissionCount() %></td>
                  <td><%= permission.getInfoText() %></td>
                <td>
                    <form action="/grantPermission" method="post" style="display:inline;">
                        <input type="hidden" name="empCode" value="<%= permission.getEmpCode() %>">
                        <input type="hidden" name="action" value="Accepted">
                        <input type="hidden" name="startTime" value="<%= permission.getStartTime() %>">
                        <button class="custom-button" type="submit">Accept</button>
                    </form>
                    <form action="/grantPermission" method="post" style="display:inline;">
                        <input type="hidden" name="empCode" value="<%= permission.getEmpCode() %>">
                        <input type="hidden" name="action" value="Rejected">
                        <input type="hidden" name="startTime" value="<%= permission.getStartTime() %>">
                        <button class="reject-button" type="submit">Reject</button>
                    </form>
                </td>
            </tr>
            <% }
            } else { %>
            <tr>
                <td colspan="8">No permission records found.</td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
<script>
    $(document).ready(function() {
        $('#permissionDetailsTable').DataTable();
    });
</script>

</body>
</html>
