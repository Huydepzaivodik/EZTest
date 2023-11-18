<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="model.Account"%>

<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Quiz</title>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >

        <style>
            #navbar {
                position: fixed;
                top: 0;
                width: 100%;
                z-index: 1000;
            }
            body {
                background: #eee;

            }

            .main-container {
                margin-top: 40px;
            }

            .widget-author {
                margin-bottom: 58px;
            }

            .author-card {
                position: relative;
                padding-bottom: 48px;
                background-color: #fff;
                box-shadow: 0 12px 20px 1px rgba(64, 64, 64, .09);
            }

            .author-card .author-card-cover {
                position: relative;
                width: 100%;
                height: 100px;
                background-position: center;
                background-repeat: no-repeat;
                background-size: cover;
            }

            .author-card .author-card-cover::after {
                display: block;
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                content: '';
                opacity: 0.5;
            }

            .author-card .author-card-profile {
                display: table;
                position: relative;
                margin-top: -22px;
                padding-right: 15px;
                padding-bottom: 16px;
                padding-left: 20px;
                z-index: 5;
            }

            .author-card .author-card-profile .author-card-avatar,
            .author-card .author-card-profile .author-card-details {
                display: table-cell;
                vertical-align: middle;
            }

            .author-card .author-card-profile .author-card-avatar {
                width: 85px;
                border-radius: 50%;
                box-shadow: 0 8px 20px 0 rgba(0, 0, 0, .15);
                overflow: hidden;
            }

            .author-card .author-card-profile .author-card-avatar>img {
                display: block;
                width: 100%;
            }

            .author-card .author-card-profile .author-card-details {
                padding-top: 20px;
                padding-left: 15px;
            }

            .author-card .author-card-profile .author-card-name {
                margin-bottom: 2px;
                font-size: 20px;
                font-weight: bold;
                color: #05386B;
            }

            .author-card .author-card-profile .author-card-position {
                display: block;
                color: #379683;
                font-size: 14px;
                font-weight: 200;
                margin-left: 13px;
            }

            .author-card .author-card-info {
                margin-bottom: 0;
                padding: 0 25px;
                font-size: 13px;
            }

            .author-card .author-card-social-bar-wrap {
                position: absolute;
                bottom: -18px;
                left: 0;
                width: 100%;
            }

            .author-card .author-card-social-bar-wrap .author-card-social-bar {
                display: table;
                margin: auto;
                background-color: #05386B;
                box-shadow: 0 12px 20px 1px rgba(64, 64, 64, .11);
            }


            .list-group-item i {
                display: inline-block;
                margin-top: -2px;
                margin-right: 8px;
                font-size: 1.6em;
                vertical-align: middle;
            }

            .mr-1,
            .mx-1 {
                margin-right: .25rem !important;
            }

            .list-group-item.active:not(.disabled) {
                border-color: white;
                background: #fff;
                color: #05386B;
                cursor: default;
                pointer-events: none;
            }

            .list-group-flush:last-child .list-group-item:last-child {
                border-bottom: 0;
            }

            .list-group-flush .list-group-item {
                border-right: 0 !important;
                border-left: 0 !important;
            }

            .list-group-flush .list-group-item {
                border-right: 0;
                border-left: 0;
                border-radius: 0;
            }

            .list-group-item {
                z-index: 2;
                color: #fff;
                background-color: #05386B;
                border-color: #05386B;
            }

            .list-group-item:last-child {
                margin-bottom: 0;
                border-bottom-right-radius: .25rem;
                border-bottom-left-radius: .25rem;
            }

            a.list-group-item,
            .list-group-item-action {
                color: #05386B;
                font-size: 14px;
                font-weight: 200;
            }

            .list-group-item {
                padding-top: 16px;
                padding-bottom: 16px;
                -webkit-transition: all .3s;
                transition: all .3s;
                border: 1px solid #e7e7e7 !important;
                border-radius: 0 !important;
                color: #05386B;
                font-size: 12px;
                font-weight: 600;
                letter-spacing: .08em;
                text-decoration: none;
            }

            .list-group-item {
                position: relative;
                display: block;
                padding: .75rem 1.25rem;
                margin-bottom: -1px;
                background-color: #fff;
                border: 1px solid rgba(0, 0, 0, 0.125);
            }

            .form-control {
                border: hidden;
                font-size: small;
                color: #379683;
                font-weight: 300; /* Adjust the font weight as needed */
            }

            .btn {
                background-color: #05386B;
                margin: 0px;
                padding: auto;
            }

            .btn1 {
                background-color: #379683;
                margin: 0px;
                padding: auto;
            }

            .text {
                color: #EDF5E1;
            }

            .fa {
                color: #05386B;
            }

            .table th, .table td {
                max-width: 650px; /* Set your desired maximum width */
                word-wrap: break-word; /* Allow words to break and wrap onto the next line */
                overflow: hidden;
                text-overflow: ellipsis; /* Add ellipsis for overflow text */
                white-space: normal; /* Allow content to wrap onto the next line */
            }

            .table input[type="checkbox"] {
                font-size: smaller;
            }
            .error{
                color: red;
                font-size: 13px;
            }

        </style>
        <title>Your Title</title>
    </head>

    <body>
        <%
                   Account teacher = (Account) request.getSession().getAttribute("account");
                   String role=(String)request.getSession().getAttribute("role");
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
                        </li>
                    </ul>
                    <div class="d-flex align-items-center justify-content-start">
                        <div class="dropdown">
                            <a class="dropdown-toggle d-flex align-items-center hidden-arrow" href="#" id="navbarDropdownMenuAvatar" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
                                <div style="color:#fff"><%=teacher.getName()%></div>&nbsp;
                                <img src="https://cdn-icons-png.flaticon.com/512/6596/6596121.png" class="rounded-circle" height="30" alt="Black and White Portrait of a Man" loading="lazy" />
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownMenuAvatar">
                                <li>
                                    <a class="dropdown-item" href="profileTeacher"><i class="icon-cog blackiconcolor fa fa-user-circle" aria-hidden="true"></i> My profile</a>
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

        <div class="container mb-4 main-container" style="margin-top:100px">
            <div class="row">
                <form method="post" action="update-delete-quiz" class="col-lg-4 pb-5">
                    <!-- Account Sidebar-->
                    <input type="hidden" value="${quiz_id}" name="quiz_id">
                    <div class="author-card pb-3">
                        <div class="author-card-cover"
                             style="background-image: url(https://bootdey.com/img/Content/flores-amarillas-wallpaper.jpeg);">
                        </div>
                        <div class="author-card-profile">
                            <div class="author-card-details">
                                <input class="author-card-name form-control" name="quiz_name" value="${quiz_name}" required>
                                <p class="author-card-position" name="create_date" readonly>Create Date: ${create_date}</p>
                                <c:if test="${not empty errorMessage3}">
                                    <p class="error">${errorMessage3}</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="wizard">
                        <nav class="list-group list-group-flush">
                            <a class="list-group-item ">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-inline-block"><i
                                                class="fa fa-calendar mr-1 text-muted"></i> Start Time</div>
                                        <input type="datetime-local" value="${start_time}" name="start_time" required
                                               class="form-control">
                                        <c:if test="${not empty errorMessage1}">
                                            <p class="error">${errorMessage1}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </a>
                            <a class="list-group-item ">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-inline-block  "><i
                                                class="fa fa-calendar mr-1 text-muted"></i> Due Time</div>
                                        <input type="datetime-local" value="${due_date}" name="due_date" required
                                               class="form-control">
                                        <c:if test="${not empty errorMessage2}">
                                            <p class="error">${errorMessage2}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </a>
                            <a class="list-group-item ">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-inline-block  "><i
                                                class="fa fa-info-circle mr-1 text-muted"></i> Visible
                                            <input type="checkbox" name="isVisible" value="true" style="margin: 12 0 10 0" class="form-check-input"
                                                   <c:if test="${isVisible eq 'true'}">checked = "checked"</c:if>>
                                            </div>

                                        </div>
                                    </div>
                                </a>
                                <a class="list-group-item">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <div class="d-inline-block  ">
                                                <i class="fa fa-info-circle mr-1 text-muted"></i> Display Detail Answer
                                                <input type="checkbox" name="isDisplayDetail" value="true" <c:if test="${isDisplayDetail eq 'true'}">checked</c:if> class="form-check-input">
                                            </div>
                                        </div>
                                    </div>
                                </a>

                                <a class="list-group-item ">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="align-items-center">
                                            <div class="d-inline-block  ">
                                                <i class="fa fa-clock-o mr-1 text-muted"></i> Time Duration
                                            </div>
                                            <select name="unit" class="form-control ml-2">
                                                <option value="minute" <c:if test="${timeUnit eq 'minute'}">selected = "selected"</c:if>>Min</option>
                                            <option value="second" <c:if test="${timeUnit eq 'second'}">selected = "selected"</c:if>>Sec</option>
                                            </select>
                                            <input type="number" placeholder="Duration" min="1" max="9000"
                                                   class="form-control" value="${duration}" name="duration" required>
                                        <c:if test="${not empty errorMessage4}">
                                            <p class="error">${errorMessage4}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </a>
                            <a class="list-group-item ">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex align-items-right" style="margin-left: 250px;">
                                        <button class="btn justify-content-between" name="mod" value="editquiz">
                                            <p class="text" style="margin-bottom: 0px; "><i class="fa fa-floppy-o" style="color: #EDF5E1;"></i> Save</p>
                                        </button>
                                    </div>
                                </div>
                            </a>
                        </nav>
                    </div>
                </form>
                <!-- Orders Table-->
                <div class="col-lg-8 pb-8">
                    <div class="form-inline" style="display: flex;">
                        <!-- Ô nhập tìm kiếm -->
                        <input type="search" class="form-control" style="width: 300px" id="input-search" placeholder="Search By Question">

                        <!-- Nút "Add Manual" -->
                        <a class="btn justify-content-between" href="add-quiz-question?class_id=${class_id}&quiz_id=${quiz_id}&quiz_name=${quiz_name}&mod=add" onclick="submitForm()">
                            <i class="fa fa-plus" style="color: #EDF5E1;"> Add Manual</i>
                        </a>

                        <!-- Nút "Add From Bank" -->
                        <a class="btn btn1 justify-content-between" href="add-question-bank">
                            <i class="fa fa-plus" style="color: #EDF5E1;"> Add From Bank</i>
                        </a>
                    </div>


                    <br>
                    <div class="table-responsive searchable-container">

                        <c:forEach items="${listQuestion}" var="lq">
                            <table class="table table-hover items">
                                <tr>
                                    <th>
                                        <u style="color: #379683">Question ${lq.question_id}:</u>
                                    </th>
                                    <th>
                                        <a href="update-delete-quiz?class_id=${class_id}&quiz_id=${quiz_id}&quiz_name=${quiz_name}&question_id=${lq.question_id}&mod=1" class="edit-button" title="Edit Question">
                                            <i class="fa fa-edit" style="color: green;"></i>
                                        </a>
                                    </th>
                                    <th>
                                        <a href="#" class="delete-button" onclick="showAlert(${lq.question_id}, ${lq.quiz_id.quiz_id}, '${quiz_name}', ${lq.question_id})" title="Delete Question">
                                            <i class="fa fa-trash" style="color: red;"></i>
                                        </a>
                                    </th>
                                </tr>
                                <tr>
                                    <td colspan="3">${lq.question_text}</td>
                                </tr>

                                <c:forEach items="${listChoice}" var="lc">
                                    <c:if test="${lc.getQuestion_id().getQuestion_id() eq lq.getQuestion_id()}">
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="choice" <c:if test="${lc.isIs_correct()}">checked</c:if> disabled class="custom-checkbox">${lc.getChoice_text()}<br>
                                                </td>
                                            </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showAlert(id, quiz_id, quiz_name, question_id) {
                if (confirm("Do you want to delete Question with ID: " + id)) {
                    window.location = "update-delete-quiz?quiz_id=" + quiz_id + "&quiz_name=" + quiz_name + "&question_id=" + question_id + "&mod=delete";
                }
            }

            $(document).ready(function () {
                $('[data-toggle="tooltip"]').tooltip();
            });

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
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
    </body>
</html>