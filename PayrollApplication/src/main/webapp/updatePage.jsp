<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 300px;
            transition: box-shadow 0.3s ease;
        }

        form:hover {
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button[type="submit"] {
            background-color: lightslategrey;
            color: #fff;
              width: 100%;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
   <form action="/update" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
    <div class="form-group">
        <label for="name">Name</label>
        <input type="text" name="name" id="name" value="<%= request.getParameter("name") %>" required>
    </div>
    <div class="form-group">
        <label for="role">Designation</label>
        <input type="text" name="role" id="role" value="<%= request.getParameter("designation") %>" required>
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" name="email" id="email" value="<%= request.getParameter("email") %>" required>
    </div>
    <div class="form-group">
        <label for="contact">Mobile</label>
        <input type="text" name="contact" id="contact" value="<%= request.getParameter("mobile") %>" required>
    </div>
    <div class="form-group">
        <label for="salary">Salary</label>
        <input type="text" name="salary" id="salary" value="<%= request.getParameter("salary") %>" required>
    </div>
    <button type="submit">Update</button>
</form>

</body>
</html>
