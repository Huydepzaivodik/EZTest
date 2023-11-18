<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.concurrent.TimeUnit" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Class</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="lib/css/course.css" />
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>       
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <style>
            body{
                background-color: #edf0f0;
            }
            .list-course{
                display: flex;
            }
            #navbar {
                position: fixed;
                top: 0;
                width: 100%;
                z-index: 1000;
            }

            .heading-container {
                display: flex;
                justify-content: flex-end;
                align-items: center;
                margin-bottom: 10px;
            }

            .button {
                padding: 5px 10px;
                background-color: #007bff;
                color: #fff;
                text-decoration: none;
                border: 1px solid #007bff;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .button:hover {
                background-color: #0056b3;
                color: #fff;
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
            .take-quiz-button {
                display: inline-block;
                padding: 5px 10px;
                background-color: #007bff;
                color: #fff;
                text-decoration: none;
                border: 1px solid #007bff;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .take-quiz-button:hover {
                background-color: #0056b3;
                color: #fff;
            }
            .take-quiz-button.disabled {
                background-color: #ccc;
                cursor: not-allowed;
            }

            .take-quiz-button.disabled:hover {
                background-color: #ccc;
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


        <div class="container" style="margin-top:50px">
            <section class="py-4">
                <div class="container">
                    <h2>Class:  ${cl.getClass_name()}</h2>
                    <div class="heading-container">
                        <a href="profileTeacher?id=${cl.getTeacher_id().getId()}" class="button" style="margin-right:5px">Teacher profile</a>
                        <a href="viewListStuInClass?class_id=${cl.getClass_id()}" class="button">List student</a>
                    </div>
                    <div class="list-course row">
                        <c:set var="pageSize" value="5" />
                        <c:set var="currentPage" value="${empty param.page ? 1 : param.page}" />
                        <c:set var="startIndex" value="${(currentPage - 1) * pageSize}" />
                        <c:set var="endIndex" value="${startIndex + pageSize}" /> 
                        <c:forEach begin="${startIndex}" end="${endIndex}" items="${quizList}" var="q">
                            <div class="col-md-6 col-xl-4">
                                <div class="image-box image-box--shadowed white-bg style-2 mb-4" style="border-radius: 15px;">
                                    <div class="body">
                                        <h4>${q.getQuiz_name()}</h4>                                      
                                        <div class="row d-flex align-items-center">
                                            <div class="col-10">
                                                <ul class="bottom-course-sum none-list">
                                                    <li><b>Quiz ID:</b>&nbsp;${q.getQuiz_id()}</li>

                                                    <li><b>Time Duration:</b>&nbsp;
                                                        <c:if test="${q.getDuration() ge 3600}">
                                                            <%-- Convert to hours --%>
                                                            <c:set var="convertedDuration" value="${q.getDuration() / 3600}"/>
                                                            ${convertedDuration} hours
                                                        </c:if>
                                                        <c:if test="${q.getDuration() ge 60 and q.getDuration() lt 3600}">
                                                            <%-- Convert to minutes --%>
                                                            <c:set var="convertedDuration" value="${q.getDuration() / 60}"/>
                                                            ${convertedDuration} minutes
                                                        </c:if>
                                                        <c:if test="${q.getDuration() lt 60}">
                                                            ${q.getDuration()} seconds
                                                        </c:if>                                                      
                                                    </li>
                                                    <li><b>Created date:</b>&nbsp;${q.getCreate_date()}</li>
                                                    <li><b>Start time:</b>&nbsp;${q.getStart_time()}</li>
                                                    <li><b>End time:</b>&nbsp;${q.getDate_end()}</li> 
                                                </ul>
                                                <c:set var="currentTime" value="<%= new java.util.Date() %>" />                                 
                                                <c:choose>
                                                    <c:when test="${currentTime.before(q.getStart_time())}">
                                                        <button class="take-quiz-button disabled"><b>Take Quiz</b></button>
                                                    </c:when>  

                                                    <c:when test="${currentTime.after(q.getDate_end())}">
                                                        <a class="take-quiz-button" href="studentDoQuiz?quiz_id=${q.getQuiz_id()}&student_id=${student_id}&duration=${q.getDuration()}&mod=1"><b>View Score</b></a>
                                                    </c:when>  
                                                    <c:otherwise>
                                                        <a class="take-quiz-button" href="studentDoQuiz?quiz_id=${q.getQuiz_id()}&student_id=${student_id}&duration=${q.getDuration()}"><b>Take Quiz</b></a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="pagination" >
                            <c:if test="${currentPage > 1}">
                                <a class="prev" href="studentViewClass?page=${currentPage - 1}&course_id=${course_id}">Previous</a>
                            </c:if>
                            <c:if test="${endIndex < quizList.size()}">
                                <a class="next" href="studentViewClass?page=${currentPage + 1}&course_id=${course_id}">Next</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript"></script>    
        <script>

            function convertDuration(duration) {
                if (duration >= 3600) {
                    return Math.floor(duration / 3600) + " hours";
                } else if (duration >= 60) {
                    return Math.floor(duration / 60) + " minutes";
                } else {
                    return duration + " seconds";
                }
            }
            function preventBack() {
                window.history.forward();
            }
            setTimeout("preventBack()", 0);
            window.onunload = function () {
                null;
            };
        </script>

    </body>
</html>
