<%@ page
	import="com.chainsys.payrollapplication.model.CheckInsAndCheckOuts"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Check-Ins</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f2f2f2;
}

.container {
	width: 80%;
	margin: 20px auto;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	padding: 20px;
}

h2 {
	text-align: center;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

th {
	background-color: #f2f2f2;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

tr:hover {
	background-color: #ddd;
}

input[type="text"], input[type="submit"] {
	padding: 4px 10px;
	border: none;
	border-radius: 4px;
	transition: background-color 0.3s ease;
}

input[type="submit"] {
	background-color: #28a745;
	color: white;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #218838;
}

a.back-btn input[type="submit"] {
	margin-left: 0.8%;
	background-color: firebrick;
}
.contain{
display:flex;
}
</style>
</head>
<body>

	<div class="container">
	<div class="contain">
	<div class="container1">
		<form action="/search" method="post">
			<input type="number" name="empcode" placeholder="Search EmpCode" required="required" >
			<input type="submit" value="Search">		
			</form>
			</div>
			<div class="container2">
					<a href="adminDashboard.jsp">
			<input type="submit" value="Back" style="margin-left:26%;background-color: firebrick;">
		</a></div>
</div>

		<h2>Employee Check-Ins & Check-Outs</h2>

		<table>
			<thead>
				<tr>
					<th>Employee Code</th>
					<th>Name</th>
					<th>Check-In Time</th>
					<th>Check-Out Time</th>
				</tr>
			</thead>
			<tbody>
				<% 
            List<CheckInsAndCheckOuts> checkInsAndCheckOuts = (List<CheckInsAndCheckOuts>) session.getAttribute("checkInsAndCheckOuts");
            if (checkInsAndCheckOuts != null) {
                for (CheckInsAndCheckOuts checkInOut : checkInsAndCheckOuts) { 
        %>
				<tr>
					<td><%= checkInOut.getEmpCode() %></td>
					<td><%= checkInOut.getName() %></td>
					<td><%= checkInOut.getCheckIn() %></td>
					<td><%= checkInOut.getCheckOut() %></td>
				</tr>

				<% 
                }			
            } else {
        %>
				<tr>
					<td colspan="4">No check-ins and check-outs found.</td>
				</tr>
				<% } %>
			</tbody>
		</table>
	</div>

</body>
</html>
