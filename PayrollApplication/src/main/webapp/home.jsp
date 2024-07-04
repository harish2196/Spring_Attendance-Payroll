<%

if(session == null){
	response.sendRedirect("home.jsp");
}

response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
response.setHeader("Pragma", "no-cache"); 
response.setHeader("Expires", "0");

if (session.getAttribute("username") == null) {
    response.sendRedirect("login.jsp");
}
%>

<%@page import="java.util.ArrayList"%>
<%-- <%@ page import="java.util.Base64" %> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Innowell</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            background-color: #f4f4f4;
        }

        .container1 {
            text-align: center;
            background-color: white;
            background-image: url("images/a21.avif");
            
            height:80vh;
            background-size: cover;
            width: 100%;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .logo {
            height: 80vh;
            display: flex;
            font-weight: bolder;
      letter-spacing: 20px;
        color: white;
            text-shadow: 2px 3px 5px rgba(2, 0, 1, 1.3);
            justify-content: center;
            align-items: center;
            font-size: 120px;
        }


        .para p {
            width: 35%;
            text-align: right;
            margin-top: 8%;
            right: 0;
            font-size: 20px;
            margin-left: 63%;
            font-weight: bold;
            font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
        }

        .para1 p {
            width: 50%;
            font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
            font-weight: bolder;
            font-size: 40px;
            right: 0;
            margin-left: 6%;
        }

        .box1 img {
            width: 400px;
            height: 500px;
            border-radius: 50px;
            padding: 30px;
        }

        .box {
            display: flex;
            margin-left: 17.5%;
        }

        .box2 img {
            width: 450px;
            height: 300px;
            margin-top: 2%;
            border-radius: 50px;
        }

        .box2 p {
            font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
            width: 69%;
            font-weight: bold;
            font-size: 22px;
            word-spacing: 0.3em;
        }

        footer {
            background-color: #0a75b8;
            color: #fff;
            margin-top: 5%;
            padding: 20px 0;
        }

        .footer-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .footer-logo img {
            width: 150px;
            margin-bottom: 20px;
        }

        .footer-content {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .footer-section {
            flex: 1;
            margin-right: 20px;
        }

        .footer-section h3 {
            font-size: 18px;
            margin-bottom: 10px;
        }

        .footer-section ul {
            list-style-type: none;
            padding: 0;
        }

        .footer-section ul li {
            margin-bottom: 8px;
        }

        .footer-section ul li a {
            color: #fff;
            font-family: Arial, Helvetica, sans-serif;
            text-decoration: none;
        }

        .footer-section ul li a:hover {
            text-decoration: underline;
            color: darkred;
            margin-left: 15px;
        }

        .footer-section ul li a:hover {
            text-decoration: underline;
        }

        .footer-bottom {
            text-align: center;
            border-top: 1px solid #fff;
            padding-top: 10px;
        }

        .footer-locations {
            margin-bottom: 10px;
        }

        .footer-locations a {
            color: #fff;
            margin: 0 10px;
            text-decoration: none;
        }

        .footer-locations a:hover {
            text-decoration: underline;
            color: #ffd700;
            margin-left: 15px;
        }

        .custom-nav {
            background-color: lightgray;
            padding: 10px 5px;
            width: 39%;
          margin-left: 31%;
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
.custom-dropdown button {
    background-color: transparent;
    border: none;
    bottom:0;
    margin-top:12.5%;
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
  
   <div>
    <p style="color: white;margin-top:6.7%;bottom:0;margin-left=-60%">Welcome, <%= session.getAttribute("username") %></p>
</div>

        <li class="nav-item">
            <a class="nav-link" href="Home.jsp">Home</a>
          </li>                   
          <li class="nav-item">
            <a class="nav-link" href="JoinUs.jsp">Join Us</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="contact.jsp">Contact</a>
          </li>
           <li class="nav-item">
      <form action="/checkout" method="post" class="logout">
    <input type="submit" value="Logout">
</form>


          </li>
        </ul>
      </div>
    </div>
  </nav>
  
  
    <div class="container1">
        <div class="logo">InnoWell</div>
    </div>
    
    
    <nav class="custom-nav">
          
        <div class="custom-dropdown">
            <button class="custom-dropbtn">Leave Application
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content">
                <a href="viewPermission.jsp">Apply Permission</a>
                <a href="leave.jsp">Apply Leave</a>
            </div>
        </div>
        
        <div class="custom-dropdown">
     
        <button type="button" onclick="window.location.href='adminReport.jsp'">Admin Report</button>
    </a>          
         

</div>
         <div class="custom-dropdown">
            <button class="custom-dropbtn">Status
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content">
           <form action="UpdateAndDelete" class="a1" method="get">
    <button type="submit" value="submit">Check Permission</button>            
</form>
                 <form action="EmployeeLeave"  class="a1" method="get">
               <button type="submit" value="submit">Check Leave</button> 
                </form>
                
            </div>
        </div>   
        
         <div class="custom-dropdown">
          <form action="PayrollCalculations" class="a1" method="post">
              <button class="custom-dropbtn">Timesheet
                <i class="fa fa-caret-down"></i>
            </button>
            </form>

            

    </a>
</div>     
    </nav>
    
    
    <div class="para">
        <p>Managing attendance and zpayroll efficiently is crucial for any organization to ensure accurate compensation
            and regulatory compliance. By leveraging advanced software solutions, companies can automate attendance
            tracking, leave management, and payroll processing, thereby reducing errors and saving time.</p>
    </div>
    <div class="para1">
        <p>
            Allow your employees to mark attendance easily on the web. All they need is an internet connection and our
            URL to log in and log out for the day.</p>
    </div>
    <div class="box">
        <div class="box1">
            <img src="images/a5.avif" alt="unkn">
        </div>
        <div class="box2">
            <img src="images/a4.webp" alt="">
            <p>
           
                "Payroll management ensures timely employee compensation, fostering morale and maintaining organizational efficiency, thereby <span  style="color: darkmagenta;"> contributing to sustained success and employee satisfaction."</span></p></span>
        </div>
    </div>
    <footer>
        <div class="footer-container">
            <div class="footer-logo">
                <img src="images/a6.png" alt="Innowell Logo">
            </div>
            <div class="footer-content">
                <div class="footer-section">
                    <h3>About US</h3>
                    <ul>
                        <li><a href="#">Who are we</a></li>
                        <li><a href="#">Leadership</a></li>
                        <li><a href="#">Branches</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h3>Services</h3>
                    <ul>
                        <li><a href="#">MEP</a></li>
                        <li><a href="#">Sustainability</a></li>
                        <li><a href="#">BIM</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h3>Corporate</h3>
                    <ul>
                        <li><a href="#">Corporate facility</a></li>
                        <li><a href="#">Corporate governance</a></li>
                        <li><a href="#">CSR</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h3>Links</h3>
                    <ul>
                        <li><a href="#">Projects</a></li>
                        <li><a href="#">Careers</a></li>
                        <li><a href="#">Innovation</a></li>
                    </ul>
                </div>
            </div>
            <div class="footer-bottom">
                <div class="footer-locations">
                    <a href="#">Sivakasi</a>
                    <a href="#">Chennai</a>
                    <a href="#">Bangalore</a>
                    <a href="#">Hyderabad</a>
                    <a href="#">Bangladesh</a>
                </div>
                <p>&copy; 2024 Innowell Engineering International Pvt. Ltd.</p>
            </div>
        </div>
    </footer>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>