<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="model.Account" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <title>User List</title>
        <style>
            body {
                background-color: #edf0f0;
            }

            body * {
                font-size: 15px;
            }


            h2 {
                font-size: 25px;
                margin-bottom: 10px;
            }

            h5{
                font-size: 20px;
            }


            .btn-primary,
            .btn-primary:hover,
            .btn-primary:focus,
            .btn-primary:active,
            .btn-primary.active,
            .btn-primary.focus,
            .btn-primary:active,
            .btn-primary:focus,
            .btn-primary:hover,
            .open>.dropdown-toggle.btn-primary {
                background-color: #05386B;
                border: 1px solid #05386B;
            }

            .p-t-10 {
                padding-top: 10px !important;
            }

            .media-main a.pull-left {
                width: 100px;
            }

            .thumb-lg {
                height: 84px;
                width: 84px;
            }

            .media-main .info {
                overflow: hidden;
                color: #000;
            }

            .media-main .info h4 {
                padding-top: 10px;
                margin-bottom: 5px;
            }

            .social-links li a {
                background: #EFF0F4;
                width: 30px;
                height: 30px;
                line-height: 30px;
                text-align: center;
                display: inline-block;
                border-radius: 50%;
                -webkit-border-radius: 50%;
                color: #7A7676;
            }

        </style>
    </head>
    <body>
        <% 
            Account admin=(Account) request.getSession().getAttribute("account"); 
            String role=(String)request.getSession().getAttribute("role");
        %>

        <% 
            String exportSuccess = request.getParameter("export_success");
            String filePath = (String) request.getSession().getAttribute("filePath");
            if (exportSuccess != null && exportSuccess.equals("true")) { 
                if (filePath != null) {
        %>
        <script>
            alert("Export Excel successfully! Your file path is: <%= filePath %>");
        </script>
        <%
                } else {
        %>
        <script>
            alert("Export Excel failed! The path to the file does not exist.");
        </script>
        <%
                }
            }
        %>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <img src="image/logo.png" style="height: 40px;">&nbsp;&nbsp;
                <a class="navbar-brand mt-2 mt-lg-0" href="home">
                    <h5 >EzTest</h5>
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

        <div class="container bootstrap snippets">
            <h2><strong>Student List</strong></h2>
            <div class="d-flex align-items-center">
                <a class="btn btn-effect-ripple btn-success" href="addaccount"
                   name="export-student" style="height: 30px; width: 120px; font-size: 10px;">
                    <i class="fa fa-user-plus" aria-hidden="true"></i> New Account
                </a>
                <div class="ms-auto">
                    <form action="ImportAccountController" method="post" enctype="multipart/form-data">
                        <input class="mb-1" type="file" name="file" style="width: 145px;">
                        <button class="btn btn-success"><i class="fa-regular fa-file-excel"></i> Import Student By Excel</button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body p-t-0" style="width:90%">
                            <div class="input-group col-lg-12">
                                <input type="search" class="form-control" id="input-search" style="font-size: 15px;" placeholder="Search Student By Name">
                                <span class="input-group-btn">
                                    <div>
                                        <form method="post" action="AdminExportAccountController">
                                            <button class="btn btn-effect-ripple btn-primary"
                                                    name="export-student" style="height: 24px; width: 150px; font-size: 10px;">
                                                <i class="fa fa-download"></i> Export Student List
                                            </button>
                                        </form>
                                    </div>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="searchable-container">
                <c:forEach items="${stdList}" var="s" varStatus="loopStatus">
                    <div class="col-sm-6 items">
                        <div class="panel">
                            <div class="panel-body p-t-10">
                                <div class="media-main">
                                    <a class="pull-left" href="#">
                                        <img class="thumb-lg img-circle bx-s" src="${s.getImg()}" alt="">
                                    </a>
                                    <div class="pull-right btn-group-sm">
                                        <a href="modify?id=${s.getId()}&mod=1" class="btn btn-success tooltips" data-placement="top"
                                           data-toggle="tooltip" data-original-title="Edit">
                                            <i class="fa fa-pencil"></i>
                                        </a>
                                        <a href="#" class="btn btn-danger tooltips" onclick="showDeleteStudentConfirmation(${s.getId()})" data-placement="top"
                                           data-toggle="tooltip" data-original-title="Delete">
                                            <i class="fa fa-close"></i>
                                        </a>
                                    </div>

                                    <div class="info">
                                        <h4>${s.getName()}</h4>
                                        <p class="text-muted">ID: ${s.getId()}</p>
                                        <p class="text-muted">Email: ${s.getEmail()}</p>
                                        <p class="text-muted">DOB: ${s.getDob()}</p>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <hr>
                            </div>
                        </div>
                    </div>

                    <!-- Nếu là cột cuối cùng hoặc là cột thứ 2 trên hàng, đóng hàng -->
                    <c:if test="${loopStatus.index % 2 != 0 or loopStatus.last}">
                    </div>
                </c:if>

                <!-- Nếu là cột thứ 2 trên hàng và không phải là lần lặp cuối cùng, mở một hàng mới -->
                <c:if test="${loopStatus.index % 2 == 0 and not loopStatus.last}">
                    <div class="searchable-container">
                    </c:if>
                </c:forEach>
            </div>
        </div>

        <div class="container bootstrap snippets">
            <div class="d-flex align-items-center">
                <div>
                    <h2><strong>Teacher List</strong></h2>
                </div>
                <div class="ms-auto">
                    <form action="ImportTeacherController" method="post" enctype="multipart/form-data">
                        <input class="mb-1" type="file" name="file" style="width: 145px;">
                        <button class="btn btn-success"><i class="fa-regular fa-file-excel"></i> Import Teacher By Excel</button>
                    </form>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body p-t-0" style="width: 90%">
                            <div class="input-group col-lg-12">
                                <input type="search" class="form-control" id="input-search1" style="font-size: 15px;" placeholder="Search Teacher By Name">
                                <span class="input-group-btn">
                                    <div>
                                        <form method="post" action="AdminExportAccountController">
                                            <button class="btn btn-effect-ripple btn-primary"
                                                    name="export-teacher" style="height: 24px; width: 150px; font-size: 10px;">
                                                <i class="fa fa-download"></i> Export Teacher List
                                            </button>
                                        </form>
                                    </div>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="searchable-container1">
                <c:forEach items="${teacherList}" var="t" varStatus="loopStatus">
                    <div class="col-sm-6 items1">
                        <div class="panel">
                            <div class="panel-body p-t-10">
                                <div class="media-main">
                                    <a class="pull-left" href="#">
                                        <img class="thumb-lg img-circle bx-s" src="${t.getImg()}" alt="">
                                    </a>
                                    <div class="pull-right btn-group-sm">
                                        <a href="modify?id=${t.getId()}&mod=2" class="btn btn-success tooltips" data-placement="top"
                                           data-toggle="tooltip" data-original-title="Edit">
                                            <i class="fa fa-pencil"></i>
                                        </a>
                                        <a href="#" class="btn btn-danger tooltips" onclick="showDeleteTeacherConfirmation(${t.getId()})" data-placement="top"
                                           data-toggle="tooltip" data-original-title="Delete">
                                            <i class="fa fa-close"></i>
                                        </a>
                                    </div>

                                    <div class="info">
                                        <h4>${t.getName()}</h4>
                                        <p class="text-muted">ID: ${t.getId()}</p>
                                        <p class="text-muted">Email: ${t.getEmail()}</p>
                                        <p class="text-muted">DOB: ${t.getDob()}</p>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <hr>
                            </div>
                        </div>
                    </div>

                    <!-- Nếu là cột cuối cùng hoặc là cột thứ 2 trên hàng, đóng hàng -->
                    <c:if test="${loopStatus.index % 2 != 0 or loopStatus.last}">
                    </div>
                </c:if>

                <!-- Nếu là cột thứ 2 trên hàng và không phải là lần lặp cuối cùng, mở một hàng mới -->
                <c:if test="${loopStatus.index % 2 == 0 and not loopStatus.last}">
                    <div class="searchable-container">
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <script>
            function showDeleteStudentConfirmation(id) {
                if (confirm("Do you want to delete Student account with ID: " + id)) {
                    window.location = "deleteUpdate?id=" + id + "&mod=1";
                }
            }

            function showDeleteTeacherConfirmation(id) {
                if (confirm("Do you want to delete Teacher account with ID: " + id + "? The alter teacher will teach all classes of this Teacher.")) {
                    window.location = "delete-teacher?id=" + id;
                }
            }
        </script>
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

            $(function () {
                $('#input-search1').on('keyup', function () {
                    var rex = new RegExp($(this).val(), 'i');
                    $('.searchable-container1 .items1').hide();
                    $('.searchable-container1 .items1').filter(function () {
                        return rex.test($(this).text());
                    }).show();
                });
            });
        </script>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>