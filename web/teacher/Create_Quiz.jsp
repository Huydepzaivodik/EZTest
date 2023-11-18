<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="model.Account" %>
<html>

    <head>
        <title>Confirm Account</title>
        <meta charset="UTF-8">
        <link rel="shortcut icon" type="image/png" href="image/logo.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>

        <style>
            .bg-primary {
                background-color: #05386B!important;
            }
            body {
                background: #eee;
            }

            .author-card {
                position: relative;
                padding-bottom: 48px;
                background-color: #fff;
                box-shadow: 0 12px 20px 1px rgba(64, 64, 64, .09);
            }

            .author-card-cover {
                position: relative;
                width: 100%;
                height: 140px;
                background-position: center;
                background-repeat: no-repeat;
                background-size: cover;
            }

            .author-card-cover::after {
                display: block;
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                content: '';
                opacity: 0.5;
            }

            .author-card-cover>.btn {
                position: absolute;
                top: 12px;
                right: 12px;
                padding: 0 10px;
            }

            .author-card-profile {
                display: table;
                position: relative;
                margin-top: -22px;
                padding-right: 15px;
                padding-bottom: 16px;
                padding-left: 20px;
                z-index: 5;
            }

            .author-card-profile .author-card-avatar,
            .author-card-profile .author-card-details {
                display: table-cell;
                vertical-align: middle;
            }

            .author-card-profile .author-card-avatar {
                width: 85px;
                border-radius: 50%;
                box-shadow: 0 8px 20px 0 rgba(0, 0, 0, .15);
                overflow: hidden;
            }

            .author-card-profile .author-card-avatar>img {
                display: block;
                width: 100%;
            }

            .author-card-profile .author-card-details {
                padding-top: 20px;
                padding-left: 15px;
            }

            .author-card-profile .author-card-name {
                margin-bottom: 3px;
                font-size: 20px;
                font-weight: bold;
            }

            .author-card-profile .author-card-position {
                display: block;
                color: #8c8c8c;
                font-size: 14px;
                font-weight: 600;
            }

            .list-group-item {
                position: relative;
                display: block;
                padding: .75rem 1.25rem;
                margin-bottom: -1px;
                background-color: #fff;
                border: 1px solid rgba(0, 0, 0, 0.125);
            }

            .list-group-item.active:not(.disabled)::before {
                background-color: #ac32e4;
            }

            .list-group-item::before {
                display: block;
                position: absolute;
                top: 0;
                left: 0;
                width: 3px;
                height: 100%;
                background-color: transparent;
                content: '';
            }

            .error {
                color: red;
            }
        </style>
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

        <div class="container mt-5">
            <div class="row">
                <div class="col-lg-4 pb-5 mt-5">
                    <!-- Account Sidebar-->
                    <div class="author-card pb-3">
                        <div class="author-card-cover"
                             style="background-image: url(https://scontent.fhan3-2.fna.fbcdn.net/v/t39.30808-6/358426686_1618457931956948_7525238517216464040_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_ohc=-jQhXloGBlgAX8bMnPg&_nc_ht=scontent.fhan3-2.fna&oh=00_AfAgD6IpHWo3YNsDn1eGbbaDuUEkpedSgY7cIqedSHx9bQ&oe=65598D71);">
                            <a class="btn btn-style-1 btn-white btn-sm" href="#" data-toggle="tooltip"
                               title=""
                               data-original-title="You currently have 290 Reward points to spend"></a>
                        </div>
                        <div class="author-card-profile">
                            <div class="author-card-details">
                                <h5 class="author-card-name text-lg">QUIZ NAME: ${quiz_name}</h5>
                                <span class="author-card-position">Create Date: ${create_date}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Profile Settings-->

                <div class="col-lg-8 pb-5 mt-5">
                    <form action="create-quiz" method="post">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="account-fn">Start Time</label>
                                    <input class="form-control" type="datetime-local" name="start_time"
                                           value="${start_time}" required>
                                </div>
                                <c:if test="${not empty errorMessage1}">
                                    <p class="error">${errorMessage1}</p>
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="account-fn">Due Time</label>
                                    <input class="form-control" type="datetime-local" name="due_date"
                                           value="${due_date}" required>
                                </div>
                                <c:if test="${not empty errorMessage2}">
                                    <p class="error">${errorMessage2}</p>
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="account-ln">Is Visible</label>
                                    <select class="form-control" name="is_visible">
                                        <option value="no" <c:if test="${visible eq 'no'}">selected =
                                                "selected"</c:if>>No
                                            </option>
                                            <option value="yes" <c:if test="${visible eq 'yes'}">selected =
                                                "selected"</c:if>>Yes
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="account-ln">Is Display Detail</label>
                                        <select name="detail" class="form-control">
                                            <option value="no" <c:if test="${dis_detail eq 'no'}">selected =
                                                "selected"</c:if>>No
                                            </option>
                                            <option value="yes" <c:if test="${dis_detail eq 'yes'}">selected
                                                = "selected"</c:if>>Yes
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="account-phone">Time Duration</label>
                                        <div class="input-group">
                                            <select name="unit" class="form-control">
                                                <option value="minute" <c:if test="${timeUnit eq 'minute'}">
                                                        selected = "selected"
                                                </c:if>>Minute</option>
                                            <option value="second" <c:if test="${timeUnit eq 'second'}">
                                                    selected = "selected"
                                                </c:if>>Second</option>
                                        </select>
                                        <input type="number" class="form-control" placeholder="Duration"
                                               min="1" max="9000" value="${duration}" name="duration"
                                               required>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 d-flex flex-column align-items-center">
                                <div
                                    class="d-flex flex-wrap justify-content-between align-items-center">
                                    <button class="btn btn-style-1 btn-primary mt-5 mb-4">Create
                                        Quiz</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </body>

</html>