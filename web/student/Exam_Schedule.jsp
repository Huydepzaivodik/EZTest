<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="DateTimeHelper" class="utils.DateTimeHelper"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="model.Account"%>
<!DOCTYPE html>
<html>

    <head>
        <title>Exam Schedule</title>
        <meta charset="UTF-8">
        <link rel="shortcut icon" type="image/png" href="image/logo.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">

        <style>
            body {
                background: #eee;
            }

            #navbar {
                position: fixed;
                top: 0;
                width: 100%;
                z-index: 1000;
            }

            .timeline {
                list-style-type: none;
                margin: 0;
                padding: 0;
                position: relative
            }

            .timeline:before {
                content: '';
                position: absolute;
                top: 5px;
                bottom: 5px;
                width: 5px;
                background: #2d353c;
                left: 20%;
                margin-left: -2.5px
            }

            .timeline>li {
                position: relative;
                min-height: 50px;
                padding: 20px 0
            }

            .timeline .timeline-time {
                position: absolute;
                left: 0;
                width: 18%;
                text-align: right;
                top: 30px
            }

            .timeline .timeline-time .date,
            .timeline .timeline-time .time {
                display: block;
                font-weight: 600
            }

            .timeline .timeline-time .date {
                line-height: 16px;
                font-size: 12px
            }

            .timeline .timeline-time .time {
                line-height: 24px;
                font-size: 20px;
                color: #242a30
            }

            .timeline .timeline-icon {
                left: 15%;
                position: absolute;
                width: 10%;
                text-align: center;
                top: 40px
            }

            .timeline .timeline-icon a {
                text-decoration: none;
                width: 20px;
                height: 20px;
                display: inline-block;
                border-radius: 20px;
                background: #d9e0e7;
                line-height: 10px;
                color: #fff;
                font-size: 14px;
                border: 5px solid #2d353c;
                transition: border-color .2s linear
            }

            .timeline .timeline-body {
                margin-left: 23%;
                margin-right: 17%;
                background: #fff;
                position: relative;
                padding: 20px 25px;
                border-radius: 6px
            }

            .timeline .timeline-body:before {
                content: '';
                display: block;
                position: absolute;
                border: 10px solid transparent;
                border-right-color: #fff;
                left: -20px;
                top: 20px
            }

            .timeline .timeline-body>div+div {
                margin-top: 15px
            }

            .timeline .timeline-body>div+div:last-child {
                margin-bottom: -20px;
                padding-bottom: 20px;
                border-radius: 0 0 6px 6px
            }

            .timeline-header {
                padding-bottom: 10px;
                border-bottom: 1px solid #e2e7eb;
                line-height: 30px
            }

            .timeline-header .userimage {
                float: left;
                width: 34px;
                height: 34px;
                border-radius: 40px;
                overflow: hidden;
                margin: -2px 10px -2px 0
            }

            .timeline-header .username {
                font-size: 16px;
                font-weight: 600
            }

            .timeline-header .username,
            .timeline-header .username a {
                color: #2d353c
            }

            .timeline img {
                max-width: 100%;
                display: block
            }

            .timeline-content {
                letter-spacing: .25px;
                line-height: 18px;
                font-size: 13px
            }

            .timeline-content:after,
            .timeline-content:before {
                content: '';
                display: table;
                clear: both
            }

            .timeline-title {
                margin-top: 0
            }

            .custom-form-control {
                width: 150px; /* Adjust the width as needed */
                height: 30px; /* Adjust the height as needed */
                font-size: 14px; /* Adjust the font size as needed */
                /* Add any other styles you want to customize */
            }
            .bg-primary {
                background-color: #05386B!important;
            }

        </style>
    </head>

    <body>
        <%
            Account student = (Account) request.getSession().getAttribute("account");
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
                        <a class="nav-link" href="exam-schedule">Exam Schedule</a>
                    </ul>
                    <div class="d-flex align-items-center justify-content-start">
                        <div class="dropdown">
                            <a class="dropdown-toggle d-flex align-items-center hidden-arrow" href="#" id="navbarDropdownMenuAvatar" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
                                <div style="color:#fff"><%=student.getName()%></div>&nbsp;
                                <img src="https://cdn-icons-png.flaticon.com/512/6596/6596121.png" class="rounded-circle" height="30" alt="Black and White Portrait of a Man" loading="lazy" />
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownMenuAvatar">
                                <li>
                                    <a class="dropdown-item" href="profileStu"><i class="icon-cog blackiconcolor fa fa-user-circle" aria-hidden="true"></i> My profile</a>
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

        <div class="container mt-5" >
            <div class="row">
                <div class="col-md-12">
                    <div class="content content-full-width">
                        <div class="profile-content">
                            <div class="tab-content p-0">
                                <div class="tab-pane fade active show" id="profile-post">
                                    <ul class="timeline">
                                        <form action="exam-schedule" method="get">
                                            <div align="center">
                                                <br/>
                                                <b>From:</b> <input type="date" class="form-control custom-form-control" name="fromdate" value="${from}"/>
                                                <input type="submit" class="btn btn-info" name="submit" value="View"/> <br/>(View 1 week after)
                                            </div>

                                        </form>
                                        <c:forEach items="${schedule}" var="s" varStatus="index"  >
                                            <c:forEach items="${dates}" var="d">
                                                <c:if test="${DateTimeHelper.compare(d,s.start_time) eq 0}">
                                                    <li>
                                                        <div class="timeline-time">
                                                            <span class="date"><fmt:formatDate value="${s.start_time}" pattern="dd-MM-yyyy 'at' HH:mm"/></span>
                                                            <span class="time">${DateTimeHelper.getDayNameofWeek(d)}</span>
                                                        </div>
                                                        <div class="timeline-icon">
                                                            <a href="javascript:;">&nbsp;</a>
                                                        </div>
                                                        <div class="timeline-body">
                                                            <div class="timeline-header">
                                                                <span class="userimage"><img
                                                                        src="image/logo.png"
                                                                        alt=""></span>
                                                                <span class="username">${s.quiz_name}</span>
                                                            </div>
                                                            <div class="timeline-content">
                                                                <p>Start Time: ${DateTimeHelper.getDayNameofWeek(d)}<fmt:formatDate value="${s.start_time}" pattern="dd-MM-yyyy 'at' HH:mm"/></p>
                                                            </div>
                                                            <div class="timeline-content">
                                                                <p>Due Time: ${DateTimeHelper.getDayNameofWeek(s.date_end)}<fmt:formatDate value="${s.date_end}" pattern="dd-MM-yyyy 'at' HH:mm"/></p>
                                                            </div>
                                                            <c:set var="today" value="${today}"> </c:set>
                                                            <c:if test="${DateTimeHelper.compare1(s.date_end, today) eq -1}">

                                                                <p style="color: olivedrab">Done</td>
                                                                </c:if>
                                                                <c:if test="${DateTimeHelper.compare1(s.start_time, today) eq 1}">

                                                                <p style="color: red">Not Yet</p>
                                                            </c:if>
                                                            <c:if test="${(DateTimeHelper.compare1(s.start_time, today) eq -1)&& (DateTimeHelper.compare1(s.date_end, today) eq 1) }">
                                                                <p style="color: orange">Ongoing</p>
                                                            </c:if>
                                                        </div>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>

                                        <li>
                                            <div class="timeline-icon">
                                                <a href="javascript:;">&nbsp;</a>
                                            </div>
                                            <div class="timeline-body">
                                                ...
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </body>

</html>