<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Sign In</title>
<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<style>
   .login-options {
        text-align: center;
        margin-top: 20px;
       
    }
    
    .login-option {
        display: inline-block;
        padding: 10px 20px;
        border: 2px solid transparent;
        background-color: transparent;
        color: black;
      
        text-decoration: none;
        font-weight: bold;
        transition: border-color 0.3s ease-in-out;
    }
    
    .login-option:hover {
        border-color: yellow;
    }
</style>

    <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
    <div class="main">   
        <section class="sign-in">
            <div class="container">
                <div class="signin-content">
                    <div class="signin-image">
                        <figure>
                            <img src="images/signin-image.jpg" alt="sign up image">
                        </figure>
                        <a href="registration.jsp" class="signup-image-link">Create an account</a>
                    </div>
                    <div class="signin-form">
                      <div class="login-options">
         <a href="login.jsp" class="login-option">Employee Login</a>
        <a href="adminLogin.jsp" class="login-option">Admin Login</a>
    </div>
                        <h2 class="form-title">Employee Login</h2>
                        <form action="/login" method="post" class="register-form" id="login-form">
                            <div class="form-group">
                                <label for="username"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input type="text" name="username" id="username" placeholder="Your Name" required="required" />
                            </div>
                            <div class="form-group">
                                <label for="password"><i class="zmdi zmdi-lock"></i></label>
                                <input type="password" name="password" id="password" placeholder="Password" required="required" />
                            </div>
                            <div class="form-group">
                                <input type="checkbox" name="remember-me" id="remember-me" class="agree-term" required="required" />
                                <label for="remember-me" class="label-agree-term"><span><span></span></span>Remember me</label>
                            </div>
                            <div class="form-group form-button">
                                <input type="submit" name="signin" id="signin" class="form-submit" value="Log in" />
                            </div>
                        </form>
                        <div class="social-login">
                            <span class="social-label">Or login with</span>
                            <ul class="socials">
                                <li><a href="#"><i class="display-flex-center zmdi zmdi-facebook"></i></a></li>
                                <li><a href="#"><i class="display-flex-center zmdi zmdi-twitter"></i></a></li>
                                <li><a href="#"><i class="display-flex-center zmdi zmdi-google"></i></a></li>
                            </ul>
                        </div>
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
        if(status === "failed") {
            Swal.fire({
                icon: 'error',
                title: 'Wrong username or password!',
                text: 'Your login failed!'
            });
        }
    </script>
</body>
</html>
