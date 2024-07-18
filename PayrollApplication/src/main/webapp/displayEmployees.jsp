<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Base64"%>

<%@ page import="com.chainsys.payrollapplication.model.Employees"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Employee Personal Details</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.21/css/dataTables.bootstrap4.min.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
	text-align: center;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #f2f2f2;
	text-align: center;
	color: #333;
	text-decoration: none;
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

.btn-primary {
	background-color: green;
	border-color: green;
}

.btn-danger {
	background-color: #dc3545;
	border-color: #dc3545;
}

.btn-primary:hover, .btn-primary:focus {
	background-color: #32CD32;
	border-color: green;
}

.btn-danger:hover, .btn-danger:focus {
	background-color: #c82333;
	border-color: #bd2130;
}

.logout input[type="submit"] {
	background-color: transparent;
	border: none;
	color: inherit;
	cursor: pointer;
	color: lightgray;
	margin-top: 10%;
}

.btn-custom {
	background-color: #4F7942;
	color: white;
	border: none;
	padding: 3.4px 10px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

.btn-custom:hover {
	background-color: #808000;
}

h1 {
	text-align: center;
	margin-bottom: 20px;
	font-weight:bolder;
	color: #2c3e50;
	font-size: 2.5em;
	text-shadow: 2px 3px 4px rgba(0, 0, 0, 0.3);
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="#">I N N O W E L L</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNav" aria-controls="navbarNav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
						<p style="color: white; margin-top: 6.7%; bottom: 0;">
							Welcome,
							<%= session.getAttribute("username") %>
						</p>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="adminDashboard.jsp">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="joinUs.jsp">Join
							Us</a></li>
					<li class="nav-item"><a class="nav-link" href="Contact.jsp">Contact</a>
					</li>
					<li class="nav-item">
						<form action="/adminCheckOut" class="logout" method="get">
							<a href="login.jsp"> <input type="submit" value="Logout">
							</a>
						</form>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container" style="margin-top: 20px;">
		<h1>Employee Personal Details</h1>

		<!-- 	<form action="/employeeSearch" method="post">
			<input style="margin-left: 38.5%; margin-bottom: 2%" type="text"
				name="empcode" placeholder="Search EmpCode"> <input
				type="submit" value="Search" class="btn-custom">
		</form> -->

		<table id="leaveDetailsTable" class="table table-striped table-hover">
			<thead class="thead-dark">
				<tr>
					<th>Employee Code</th>
					<th>Name</th>
					<th>Designation</th>
					<th>Email</th>
					<th>Mobile</th>
					<th>Salary</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<% 
            List<Employees> employeeList = (List<Employees>) request.getAttribute("employee");
            if (employeeList != null && !employeeList.isEmpty()) {
                for (Employees employee : employeeList) {
                	
                	byte[] image=employee.getImageData();
            %>
				<tr>
					<td><%= employee.getEmpCode() %></td>
					<td><%= employee.getUserName() %></td>
					<td><%= employee.getDesignation() %></td>
					<td><%= employee.getUserEmail() %></td>
					<td><%= employee.getUserMobile() %></td>
					<td><%= employee.getSalary() %></td>
					<td><a class="btn btn-primary"
						href="updatePage.jsp?id=<%= employee.getEmpCode() %>&name=<%= employee.getUserName()%>&designation=<%= employee.getDesignation() %>&email=<%= employee.getUserEmail() %>&mobile=<%= employee.getUserMobile() %>&salary=<%= employee.getSalary() %>">Update</a>
						<form action="/delete" method="post"
							style="display: inline-block;">
							<input type="hidden" name="id"
								value="<%= employee.getEmpCode() %>">
							<button type="submit" class="btn btn-danger">Delete</button>
						</form></td>
				</tr>
				<% 
                }
            } else {
            %>
				<tr>
					<td colspan="7">No employees found.</td>
				</tr>
				<% } %>
			</tbody>
		</table>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
	<script>
    $(document).ready(function() {
        $('#leaveDetailsTable').DataTable();
    });
</script>
</body>
</html>
