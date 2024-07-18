<%@ page
	import="com.chainsys.payrollapplication.model.CheckInsAndCheckOuts"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Check-Ins</title>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/dataTables.bootstrap4.min.css">
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
  .back-button{
        margin-left:4.7%;
        margin-top:2%;
        color:firebrick;
        font-size:18px;
        }
</style>
</head>
  <a href="adminDashboard.jsp" class="back-button">
        <span><.Back</span> 
    </a>
<body>

	<div class="container">
	
	 
	<!-- <div class="contain">
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
 -->
		<h2 style="text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);">Employee Check-Ins & Check-Outs</h2>
    <table id="leaveDetailsTable" class="table table-striped table-hover">

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
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
<script>
    $(document).ready(function() {
        $('#leaveDetailsTable').DataTable();
    });
</script>
</body>
</html>
