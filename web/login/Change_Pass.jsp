<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta http-equiv="x-ua-compatible" content="ie=edge" />
        <title>Change password</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <style>
            body{
                background-color: #edf0f0;
            }
            .container {
                background-color:#fff;
                max-width: 500px;
                margin: auto;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }

            .form-control{
                height: 40px;
            }

            .form-label{
                color:#05386B;
            }
            .back-home{
                color: blue;
                margin-left: 360px;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <%
            Account user = (Account) request.getSession().getAttribute("account");
            String role=(String)request.getSession().getAttribute("role");
        %>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <img src="image/logo.png" style="height: 40px;">&nbsp;&nbsp;
                <a class="navbar-brand mt-2 mt-lg-0" href="home">
                    <h5 class="pt-1">EzTest</h5>
                </a>
                <button class="navbar-toggler" type="button" data-mdb-toggle="collapse" data-mdb-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <% if(role.equals("Admin")){%>
                            <a class="nav-link" href="viewDetail">List User</a>
                            <%}%>
                        </li>
                    </ul>
                    <div class="d-flex align-items-center justify-content-start">
                        <div class="dropdown">
                            <a class="dropdown-toggle d-flex align-items-center hidden-arrow" href="#" id="navbarDropdownMenuAvatar" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
                                <div style="color:#fff"><%=user.getName()%></div>&nbsp;
                                <img src="https://cdn-icons-png.flaticon.com/512/6596/6596121.png" class="rounded-circle" height="30" alt="Black and White Portrait of a Man" loading="lazy" />
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownMenuAvatar">
                                <li>
                                    <% if(role.equals("Admin")){%>
                                    <a class="dropdown-item" href="profileAdmin"><i class="icon-cog blackiconcolor fa fa-user-circle" aria-hidden="true"></i> My profile</a>
                                    <%} else if (role.equals("Teacher")){%>
                                    <a class="dropdown-item" href="profileTeacher"><i class="icon-cog blackiconcolor fa fa-user-circle" aria-hidden="true"></i> My profile</a>
                                    <%} else if (role.equals("Student")){%>
                                    <a class="dropdown-item" href="profileStudent"><i class="icon-cog blackiconcolor fa fa-user-circle" aria-hidden="true"></i> My profile</a>
                                    <%}%>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="change-pass"><i class="icon-cog blackiconcolor fa fa-key" aria-hidden="true"></i> Change password</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="logout"><i class="icon-cog blackiconcolor fa fa-sign-out" aria-hidden="true"></i> Logout</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container">
            <h1 style="text-align: center">Change password</h1>
            <div>
                <h4 style="text-align: center">Password must be:</h4    >
                <div style="margin-left: 60px">- Longer than 6 characters</div>
                <div style="margin-left: 60px">- Contains at least one lowercase character</div>
                <div style="margin-left: 60px">- Contains at least one uppercase character</div>
                <div style="margin-left: 60px; margin-bottom: 25px;">- Contains at least one number</div>
            </div>
            <form action="change-pass" method="post">            
                <div class="mb-3">
                    <label for="oldPassword" class="form-label"><b>Old password</b></label>
                    <input type="password" class="form-control" name="oldPassword" id="oldPassword" placeholder="Enter old password">
                    <span style="font-size: small; color: red">${oldPassErr}</span>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label"><b>New password</b></label>
                    <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="Enter new password">
                    <span style="font-size: small; color: red">${newPassErr}</span>
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label"><b>Confirm password</b></label>
                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Confirm new password">
                    <span style="font-size: small; color: red">${confirmPassErr}</span>
                    <span style="font-size: small; color: red">${error}</span>
                </div>
                <button type="submit" class="btn btn-primary" style="margin-left:200px">Change</button>
                <a href="home" class="back-home"><b>Back to home</b></a>
            </form>        
        </div>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script type="text/javascript"></script>
    </body>
</html>
