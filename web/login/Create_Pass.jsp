<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new password</title>
        <link rel="shortcut icon" type="image/png" href="image/logo.png" >
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">     
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <style>
            .container {
                max-width: 500px;
                margin: 0 auto;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            }
            .form-control{
                height: 40px;
            }
            .back-home{
                color: blue;
                margin-left: 360px;
                text-decoration: none;
            }
        </style>
    </head>
    <body> 
        <div class="container" style="margin-top: 200px;margin-left: 700px;">
            <h1 style="text-align: center">Create new password</h1>
            <div>
                <h4 style="text-align: center">Password must be:</h4>
                <div style="margin-left: 60px">- Longer than 6 characters</div>
                <div style="margin-left: 60px">- Contains at least one lowercase character</div>
                <div style="margin-left: 60px">- Contains at least one uppercase character</div>
                <div style="margin-left: 60px; margin-bottom: 25px;">- Contains at least one number</div>
            </div>
            <form action="create-pass" method="post">            
                <div class="mb-3">
                    <label for="newPassword" class="form-label">New password</label>
                    <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="Enter your new password">
                    <span style="font-size: small; color: red">${newPassErr}</span>
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">Confirmed password</label>
                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Confirm your new password">
                    <span style="font-size: small; color: red">${confirmPassErr}</span>
                    <span style="font-size: small; color: red">${error}</span>
                </div>
                <button type="submit" class="btn btn-primary" style="margin-left:200px">Change</button>
            </form>        
        </div>
    </body>
</html>
