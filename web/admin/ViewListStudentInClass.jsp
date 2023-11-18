<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%@page import="model.Course"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta http-equiv="x-ua-compatible" content="ie=edge" />
        <title>Student list</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
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
            }

            .action-button:hover {
                background-color: #007BFF;
                color: #fff;
                text-decoration: none;
            }

            .pagination a {
                color: #007BFF;
                text-decoration: none;
                padding: 8px 16px;
                margin: 2px;
                border: 1px solid #007BFF;
                border-radius: 4px;
            }

            .pagination .active {
                background-color: #007BFF;
                color: white;
            }

            .pagination .prev,
            .pagination .next {
                background-color: #f2f2f2;
                color: #007BFF;
                padding: 8px 16px;
                margin: 2px;
                border: 1px solid #007BFF;
                border-radius: 4px;
            }

            .pagination a:hover {
                background-color: #007BFF;
                color: white;
            }


            .button:hover  {
                text-decoration: none;
                background-color: #0056b3;
                color: #fff;
            }

            .row-header{
                display: flex;
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
                <h2>Students in class ${cl.getClass_name()}</h2>
                <div class="card-header d-flex flex-row">
                    <input type="search" class="form-control" style="height: 35px"id="input-search" placeholder="Search student.....">
                    <a href="viewStudent" class="btn btn-primary ms-3 h-25" style="width: 15rem">Assign Student</a>


                    <div class="ms-3">
                        <form action="ImportStudentController" method="post" enctype="multipart/form-data">
                            <input class="mb-1" type="file" name="file" style="width: 145px;">
                            <button class="btn btn-success"><i class="fa-regular fa-file-excel"></i> Import Excel</button>
                        </form>
                    </div>

                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <div class="searchable-container">
                            <table class="table table-bordered m-0">
                                <thead>
                                    <tr>
                                        <th class="text-center align-middle py-3 px-4" >ID</th>
                                        <th class="text-center align-middle py-3 px-4" >Email</th>
                                        <th class="text-center align-middle py-3 px-4" >Name</th>
                                        <th class="text-center align-middle py-3 px-0" >Action</th>
                                    </tr>
                                </thead>
                                <tbody>                                  
                                    <c:forEach items="${stdList}" var="s">
                                        <tr class="items">
                                            <td class="text-center align-middle p-4">${s.getId()}</td>
                                            <td class="text-left align-middle p-4">${s.getEmail()}</td>
                                            <td class="text-left align-middle p-4">${s.getName()}</td>                                       
                                            <td class="text-center align-middle p-4">
                                                <a href="modify?id=${s.getId()}&mod=1" class="action-button">Profile</a> 
                                                <a href="#" onclick="showMessageRemove(${class_id},${s.getId()})" class="action-button">Remove</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>       
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(function () {
                $('#input-search').on('keyup', function () {
                    var rex = new RegExp($(this).val(), 'i');
                    $('.searchable-container .items').hide();
                    $('.searchable-container .items').filter(function () {
                        return rex.test($(this).text());
                    }).show();
                });
            });
        </script>
        <script>
            function showMessageRemove(class_id, student_id) {
                if (confirm("Do you want to remove student with ID " + student_id + " in Class ID: " + class_id)) {
                    window.location = "deleteUpdate?class_id=" + class_id + "&student_id=" + student_id + "&mod=3";
                }
            }
        </script>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script type="text/javascript"></script>
    </body>
</html>
