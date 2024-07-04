<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="ISO-8859-1">
<title>Contact Us</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<style>
body{
padding:0;
margin:0;
}

header {
    background-color: #ffcc00;
    padding: 26px;
    text-align: center;
}

header h1 {
    margin: 0;
    color: #333;
}

main {
    padding: 20px;
}

.contact {
    max-width: 800px;
    margin: 0 auto;
}

form {
    margin-top: 20px;
}

label {
    display: block;
    margin-bottom: 5px;
}

input[type="text"],
input[type="email"],
textarea {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

button {
    padding: 10px 20px;
    background-color: #333;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #555;
}

footer {
    background-color: #333;
    color: #fff;
    padding: 20px;
    text-align: center;
}

.social-icons {
    display: flex;
    justify-content: center;
}

.social-icons a {
    color: white;
    text-decoration: none;
    font-size: 24px;
    margin: 0 10px;
}

.social-icons a:hover {
    color: #f8b400; 
}
.foot{
    display: flex;
  }
  .foot1{
    margin-left: 4%;
    right: 0;
    margin-right: 0;
  }
  .foot2{
    margin-left: 50%;
  }
</style>
 <header>
       <marquee behavior="scroll" direction="left">
            <h1 style="font-family: Verdana, Geneva, Tahoma, sans-serif; font-size: 42px; color: darkmagenta; text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);">I N N O W E L L</h1>
        </marquee>
    </header>
    <main>
        <section class="contact">
            <h2>Contact Us</h2>
            <p>If you have any questions or inquiries about Innowell , feel free to get in touch with us using the form below or through the contact information provided.</p>
            <form action="#" method="POST">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
                <label for="message">Message:</label>
                <textarea id="message" name="message" rows="4" required></textarea>
                <button type="submit">Send Message</button>
            </form>
        </section>
    </main>
    <footer>
        <div class="social-icons">
            <a href="#"><i class="fab fa-facebook-f"></i></a>
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
            <a href="instagram.com"><i class="fab fa-instagram"></i></a>
        </div>
        <hr>
        <div class="foot">
            <div class="foot1"> <p>India | Dubai | Bangladesh</p></div><div class="foot2">
        <p>&copy; 2024 Innowell InfoTech. All rights reserved.</p>
    </div>
</div>
    </footer>
</body>
</html>