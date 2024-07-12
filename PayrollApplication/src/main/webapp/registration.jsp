<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Sign Up</title>

<link rel="stylesheet"
	href="fonts/material-icon/css/material-design-iconic-font.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

	<div class="main">
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<div class="signup-form">
						<h2 class="form-title">Sign up</h2>

						<form action="/register" method="post" class="register-form"
							id="register-form" enctype="multipart/form-data">

							<div class="form-group">
								<label for="name"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="name" id="name" placeholder="Emp Name"
									pattern="[A-Za-z]{2,20}" required="required" />
							</div>

							<div class="form-group">
								<label for="role"><i class="zmdi zmdi-account-circle"></i></label>
								<input type="text" name="role" id="role"
									placeholder="Designation" pattern="[A-Za-z]{2,20}"
									required="required" />
							</div>

							<div class="form-group">
								<label for="email"><i class="zmdi zmdi-email"></i></label> <input
									type="email" name="email" id="mailInput" oninput="validateEmailInput()"  placeholder="Your Email"
									
									required="required" />
									<span id="emailFeedback" style="color: red;"></span>
							</div>
							        				

							<div class="form-group">
								<label for="contact"><i class="zmdi zmdi-phone"></i></label> <input
									type="text" name="contact" id="contact"
									placeholder="Contact no" pattern="[0-9]{10}"
									required="required"/>
							</div>				

							<div class="form-group">
								<label for="image"><i class="zmdi zmdi-image"></i></label> <input
									type="file" name="image" id="image" accept="image/*"
									required="required" />
							</div>

	<div class="form-group">
								<label for="pass"><i class="zmdi zmdi-email"></i></label> <input
									type="password" name="pass" id="passInput" oninput="validatePassInput()"  placeholder="Password"
									
									required="required" />
									<span id="passFeedback" style="color: red;"></span>
							</div>

							<div class="form-group">
								<label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
								<input type="password" name="re_pass" id="re_pass"
									placeholder="Repeat your password"
									oninput=""
									required="required" />
							</div>

							<div class="form-group">
								<input type="checkbox" name="agree-term" id="agree-term"
									class="agree-term" /> <label for="agree-term"
									class="label-agree-term"> <span><span></span></span>I
									agree all statements in <a href="#" class="term-service">Terms
										of service</a>
								</label>
							</div>
							<div class="form-group form-button">
								<input type="submit" name="signup" id="signup"
									class="form-submit" value="Register" required="required" />
							</div>
						</form>
					</div>
					<div class="signup-image">
						<figure>
							<img src="images/signup-image.jpg" alt="sing up image">
						</figure>
						<a href="login.jsp" class="signup-image-link">I am already
							member</a>
					</div>
				</div>
			</div>
		</section>
	</div>


	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

	<script type="text/javascript">
    var status = document.getElementById("status").value;
    if (status === "success") {
        var empCode = <%= request.getAttribute("randomNumber") %>;
        Swal.fire({
            icon: 'success',
            title: 'Congrats!',
            text: 'Your registration was successful! Your Emp Code is: ' + empCode
        });
    }   
    
    
    function validateEmailInput() {
        const emailInput = document.getElementById('mailInput').value;
        const feedbackElement = document.getElementById('emailFeedback');
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (emailRegex.test(emailInput)) {
            feedbackElement.textContent = ""; 
        } else {
            if (emailInput == "") {
                feedbackElement.textContent = "";
            } else {
                feedbackElement.textContent = "Please enter a valid email address.";
            }
        }
    } 
 
     function validatePassInput() {
        const passInput = document.getElementById('passInput').value;
        const feedbackElement = document.getElementById('passFeedback');
        const passRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

        if (passRegex.test(passInput)) {
            feedbackElement.textContent = ""; 
            contentElement.textContent = "";
        } else {
            if (passInput == "") {
                feedbackElement.textContent = "";
            } else {
                feedbackElement.textContent = "Please enter a valid Password.";
            }
        }
    } 
</script>

</body>

</html>