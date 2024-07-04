<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.chainsys.payrollapplication.model.AdminReport" %>
<!DOCTYPE html>
<html>
<head>
    <title>Report List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        h1 {
            font-family: 'Arial', sans-serif;
            font-weight: bold;
            text-align: center;
            margin-bottom: 2%;
            color: darkmagenta;
        }
        .table-hover tbody tr:hover {
            background-color: lightgrey;
        }
        .hover-effect:hover {
            color: #ff6347;
        }
        .btn-back {
            background-color: firebrick;
            border-color: firebrick;
            color: white;
        }
        .btn-back:hover {
            background-color: darkred;
            border-color: darkred;
        }
        .btn-search {
            background-color: green;
            border-color: green;
            color: white;
            padding: 5px 10px;
            margin-left: 10px;
        }
        .btn-search:hover {
            background-color: darkgreen;
            border-color: darkgreen;
        }
      .btn{
      margin-left:43%;
      }
    </style>
</head>
<body>
    <div class="container my-5">
        <h1>Report List</h1>
       <!--  <form action="SearchByCode" method="post" class="mb-3">
            <div class="form-group">
                <input type="text" name="empcode" class="form-control" placeholder="Search by Employee Code">
            </div>
            <button type="submit" class="btn btn-search">Search</button>
        </form> -->
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Employee Code</th>
                    <th>Name</th>
                    <th>Report Text</th>
                </tr>
            </thead>
            <tbody>
            
                <% List<AdminReport> adminReport = (List<AdminReport>) request.getAttribute("adminReport"); %>
                <% if (adminReport != null) { %>
                    <% for (AdminReport report : adminReport) { %>
                        <tr class="hover-effect">
                            <td><%= report.getEmpCode() %></td>
                            <td><%= report.getName() %></td>
                            <td><%= report.getText() %></td>
                        </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="3" class="text-center">No reports found</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
       <div class="btn">
        <button onclick="window.location.href='adminDashboard.jsp'" type="button" class="btn btn-primary btn-back">Back</button>
    </div>
      </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
