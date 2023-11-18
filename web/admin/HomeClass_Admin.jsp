<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%@page import="model.Course"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <style>

            #navbar {
                position: fixed;
                top: 0;
                width: 100%;
                z-index: 1000;
            }

            body{
                background-color: #edf0f0;
            }
            body {
                margin-top: 20px;
                background: #eee;
            }

            .ui-w-40 {
                width: 40px !important;
                height: auto;
            }

            .card {
                box-shadow: 0 1px 15px 1px rgba(52, 40, 104, .08);
            }

            .ui-product-color {
                display: inline-block;
                overflow: hidden;
                margin: .144em;
                width: .875rem;
                height: .875rem;
                border-radius: 10rem;
                -webkit-box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.15) inset;
                box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.15) inset;
                vertical-align: middle;
            }
            .heading-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 10px;
            }

            .action-button{
                transition: background-color 0.3s ease, color 0.3s ease;
                text-decoration: none;
                display: inline-block;
                padding: 5px 10px;
                background-color: #f2f2f2;
                border: 1px solid #007BFF;
                border-radius: 5px;
                color: #007BFF;
                cursor: pointer;
                margin:2px;
            }

            .action-button:hover {
                background-color: #007BFF;
                color: #fff;
                text-decoration: none;
            }

            .button {
                display: inline-block;
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                text-decoration: none;
                border: 1px solid #007bff;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .button:hover  {
                text-decoration: none;
                background-color: #0056b3;
                color: #fff;
            }

            .edit-button{
                border:none;
                background-color:#fff;
            }
        </style>
    </head>

    <body>
        <%
          Account admin = (Account) request.getSession().getAttribute("account");
        %>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-dark bg-primary">
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
        <div class="container px-3 clearfix" style="margin-top:70px">
            <div class="card">
                <div class="card-header">
                    <div class="heading-container">
                        <h2>Class list in course with ID: ${course_id}</h2>
                        <a href="create-class" class="button">Create class</a>
                    </div>                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered m-0">
                            <thead>
                                <tr>
                                    <th class="text-center align-middle py-3 px-4" >Class ID</th>
                                    <th class="text-center align-middle py-3 px-4" >Class Name</th>
                                    <th class="text-center align-middle py-3 px-4">Teacher ID</th>
                                    <th class="text-center align-middle py-3 px-4" >2nd Teacher ID</th>
                                    <th class="text-center align-middle py-3 px-4" >Action</th>
                                    <th class="text-center align-middle py-3 px-0" >Update</th>
                                    <th class="text-center align-middle py-3 px-0" >Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach  items="${classList}" var="cl">
                                    <tr>
                                        <td class="text-center align-middle p-4">
                                            <c:out value="${cl.getClass_id()}" />
                                        </td>
                                        <td class="text-center align-middle p-4">
                                            <a href="viewListStuInClass?class_id=${cl.getClass_id()}">
                                                <c:out value="${cl.getClass_name()}" />
                                            </a>  
                                        </td>
                                        <c:choose>
                                            <c:when test="${cl.getTeacher_id().getId() != 0}">
                                                <td class="text-center align-middle p-4">
                                                    <a href="profileTeacher?id=${cl.getTeacher_id().getId()}" style="text-decoration:none">
                                                        <c:out value="${cl.getTeacher_id().getId()}" />
                                                    </a>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="text-center align-middle p-4">${cl.getTeacher_id().getId()}. ${cl.getTeacher_id().getName()} </td> 
                                            </c:otherwise>
                                        </c:choose>
                                        <td class="text-center align-middle p-4">
                                            <c:forEach items="${alterTeacherList}" var="at">
                                                <c:if test="${cl.getClass_id() eq at.getClass_id()}">
                                                    <c:choose>
                                                        <c:when test="${at.getAlter_teacher_id().getId() != 0}">
                                                            <a href="profileTeacher?id=${at.getAlter_teacher_id().getId()}" style="text-decoration:none">
                                                                <c:out value="${at.getAlter_teacher_id().getId()}" />
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${at.getAlter_teacher_id().getId()}" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:forEach>              
                                        </td>
                                        <td class="text-center align-middle p-4">                                           
                                            <form action="assign-alter-teacher" method="get">
                                                <input type="hidden" name="class_id" value="${cl.getClass_id()}">
                                                <input type="hidden" name="class_name" value="${cl.getClass_name()}">
                                                <button type="submit" class="action-button">Update 2nd Teacher</button>
                                            </form>
                                        </td>
                                        <td class="text-center align-middle ">
                                            <form action="modifyClass" method="get">
                                                <input type="hidden" name="course_id" value="${course_id}">
                                                <input type="hidden" name="class_name" value="${cl.getClass_name()}">
                                                <button type="submit" class="edit-button"><i class="fa fa-edit" style="color:green"></i></button>
                                            </form>
                                        </td>
                                        <td class="text-center align-middle "><a href="#"  onclick="showDeleteConfirmation(${cl.getClass_id()})">
                                                <i class="fa fa-trash" style="color:red"></i></a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showConfirmation(class_id, is_visible) {
                if (is_visible) {
                    if (confirm("Do you want to deactivate Class with ID: " + class_id + " ?")) {
                        window.location = "deleteUpdate?class_id=" + class_id + "&mod=5";
                        document.getElementById(`deactivateButton-${class_id}`).innerText = 'Activate';
                    }
                } else {
                    if (confirm("Do you want to activate Class with ID: " + class_id + " ?")) {
                        window.location = "deleteUpdate?class_id=" + class_id + "&mod=7";
                        document.getElementById(`deactivateButton-${class_id}`).innerText = 'Deactivate';
                    }
                }
            }

            function showDeleteConfirmation(class_id) {
                if (confirm("Do you want to delete Class with ID: " + class_id)) {
                    window.location = "deleteUpdate?class_id=" + class_id + "&mod=9";
                }
            }
        </script>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script type="text/javascript"></script>
    </body>
</html>