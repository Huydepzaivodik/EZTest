<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Class"%>
<%@page import="model.Account"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta http-equiv="x-ua-compatible" content="ie=edge" />
        <title>Update secondary teacher </title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
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
        </style>
    </head>
    <body>
        <%
            Account admin = (Account) request.getSession().getAttribute("account");
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
                            <a class="nav-link" href="viewDetail">List User</a>
                        </li>
                    </ul>
                    <div class="d-flex align-items-center justify-content-start">
                        <div class="dropdown">
                            <a class="dropdown-toggle d-flex align-items-center hidden-arrow" href="#" id="navbarDropdownMenuAvatar" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
                                <div style="color:#fff"><%=admin.getName()%></div>&nbsp;
                                <img src="https://cdn-icons-png.flaticon.com/512/6596/6596121.png" class="rounded-circle" height="30" alt="Black and White Portrait of a Man" loading="lazy" />
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownMenuAvatar">
                                <li>
                                    <a class="dropdown-item" href="modify?id=<%=admin.getId()%>&mod=3"><i class="icon-cog blackiconcolor fa fa-user-circle" aria-hidden="true"></i> My profile</a>
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
            <h1 style="text-align: center">Update Secondary Teacher</h1>
            <form action="assign-alter-teacher" method="post">          
                <div class="mb-3">
                    <label for="alter_teacher_id" class="form-label"><b>Select New Secondary Teacher</b></label>
                    <select class="form-control" name="alter_teacher_id" id="alter_teacher_id" value="${alter_teacher_id}">
                        <c:forEach items="${teacherList}" var="teacher">
                            <option value="${teacher.getId()}" <c:if test="${teacher.getId() eq alter_teacher_id}">selected="selected"</c:if>>ID: ${teacher.getId()}  ( Name: ${teacher.getName()} )</option>
                        </c:forEach>
                    </select>
                    <span style="font-size: small; color: red" id="class_name_update">${error}</span>
                </div>
                <input type="hidden" name="teacher_id" value="${teacher_id}">
                <input type="hidden" name="class_id" value="${class_id}">
                <button type="submit" class="btn btn-primary" style="margin-left:180px">Update</button>
                <br>
                <a href="viewClass?course_id=${course_id}" style="color: blue; margin-left: 420px"><b>Back</b></a>
            </form>        
        </div>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script type="text/javascript"></script>
    </body>
</html>
