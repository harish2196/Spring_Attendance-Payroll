<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>   
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Permission Form</title>
    </head>
    <style>
        body {
            margin: 0%;
            padding: 0;
            background-image: url("images/a20.png");
            background-size: 50% 100%;
            background-repeat: no-repeat;
            width: 100%;
            height: 106.7vh; 
        }

        .form-container {
            background-color: white;
            padding: 20px;   
            margin-left: 35%;
         
            border: none;
            border-radius: 10px;
            box-shadow: 10px 20px 15px rgba(12, 10, 10, 0.1);
            width: 300px;
        }

        .form-container label {
            margin-bottom: 20px;
        }

        .form-container input[type="text"],
        .form-container input[type="date"],
        .form-container input[type="time"],
        .form-container textarea {
            width: 100%;
            padding: 6px;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

     .form-container input[type="submit"] {
    background-color: green;
    color: white;
    padding: 10px 20px;
    border: none;
    width: 100%;
    text-align: center;
    border-radius: 5px;
    cursor: pointer;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); 
}


        .form-container input[type="submit"]:hover {
            background-color: darkolivegreen;
        }
        
          .button {
            display: inline-block;
            margin: 20px;
            margin-left:38%;
        }
        .button a {
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 4px;
            background-color:firebrick ;
            color: white;
              width: 100%;
             cursor: pointer;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); 
            font-size: 16px;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }
        .button a:hover {
            background-color: #0056b3;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .button a:active {
            background-color: #004085;
        }
    </style>

<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">


    <div class="photo">
        <div class="form-container">
            <form action="/registerPermission" method="post" onsubmit="return validateTimes()">
               <h2 style="margin-left: 70px; text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.5);">Permission Form</h2>

               
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" pattern="[A-Za-z]{2,20}" required>

                <label for="date">Date:</label>
                <input type="date" id="date" name="date" required min="2024-07-17" max="2024-07-31">
                
                <label for="start_time">Start Time:</label>
                <input type="time" id="start_time" name="start_time" required>

                <label for="end_time">End Time:</label>
                <input type="time" id="end_time" name="end_time" required>

                <label for="reason">Reason:</label>
                <textarea id="reason" name="reason" rows="4" required></textarea>
    
                <input type="submit" value="Submit">
                <div class="button"><a href="http://localhost:9000/home.jsp">Back</a></div>               
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script type="text/javascript">
        document.getElementById('start_time').addEventListener('change', function() {
            var startTime = this.value; 
            var maxEndTime = new Date('1970-01-01T' + startTime + 'Z');
            maxEndTime.setHours(maxEndTime.getHours() + 2); 
            var maxEndTimeFormatted = ('0' + maxEndTime.getHours()).slice(-2) + ':' + ('0' + maxEndTime.getMinutes()).slice(-2);
            document.getElementById('end_time').setAttribute('max', maxEndTimeFormatted);
        });

        function validateTimes() {
            var startTime = document.getElementById('start_time').value;
            var endTime = document.getElementById('end_time').value;

            var start = new Date('1970-01-01T' + startTime + 'Z');
            var end = new Date('1970-01-01T' + endTime + 'Z');
        
            var maxEndTime = new Date(start.getTime());
            maxEndTime.setHours(maxEndTime.getHours() + 2);

            if (end > maxEndTime) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'End time cannot be more than two hours after start time!',
                });
                return false; 
            }

            return true; 
        }
        
        var status = document.getElementById("status").value;
        if (status === "success") {
            Swal.fire({
                icon: 'success',
                title: 'Submitted!',
                text: 'Kindly await approval from your manager.'
            });
        }
        
    </script>
</body>
</html>
