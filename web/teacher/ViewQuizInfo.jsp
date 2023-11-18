<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta http-equiv="x-ua-compatible" content="ie=edge" />
        <title>Quiz Result</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {'packages': ['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                var data = google.visualization.arrayToDataTable([
                    ['Classification', 'Quantity'],
                    ['Good (8-10)', <%= request.getAttribute("goodCount") %>],
                    ['Average (4-8)', <%= request.getAttribute("averageCount") %>],
                    ['Poor (0-4)', <%= request.getAttribute("poorCount") %>]
                ]);

                var options = {
                    title: 'QUIZ SCORE PIE CHART',
                    backgroundColor: 'transparent'
                };

                var chart = new google.visualization.PieChart(document.getElementById('pieChart'));

                chart.draw(data, options);
            }
        </script>
    </head>
    <style>
        #navbar {
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }

        .row-header{
            display: flex;
        }

        .export-button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 5px 10px;
            font-size: 16px;
            margin: 4px 2px;
            border-radius: 5px;
        }

        .heading-container {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }
    </style>
    <body>
        <%
            Account teacher = (Account) request.getSession().getAttribute("account");
            
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

        <div class="container px-3 clearfix" style="margin-top:70px">
            <div class="card">
                <div class="card-header">
                    <div class="quiz-score-chart">
                        <h4> Quiz Score Statistic </h4>
                        <div style="width: 800px;">
                            <div id="pieChart" style="width: 100%; height: 300px;"></div>
                        </div>
                    </div>           
                </div>
                <div class="card-body">
                    <form method="post" action="ExportScoreExcelController">
                        <div class="heading-container">
                            <h4>Student Score</h4>
                            <button class="export-button"> Export Report </button>
                        </div>
                        <input type="hidden" value="${class_id}" name="class_id">
                        <input type="hidden" value="${quiz_id}" name="quiz_id">
                        <input type="hidden" value="${course_name}" name="course_name">
                        <input type="hidden" value="${class_name}" name="class_name">
                        <input type="hidden" value="${quiz_name}" name="quiz_name">
                    </form>
                    <div class="table-responsive">
                        <table class="table table-bordered m-0">
                            <thead>
                                <tr>
                                    <th class="text-center align-middle py-3 px-4" >Quiz ID</th>
                                    <th class="text-center align-middle py-3 px-4" >Student ID</th>
                                    <th class="text-center align-middle py-3 px-4" >Student Name</th>
                                    <th class="text-center align-middle py-3 px-4" >Score</th>
                                    <th class="text-center align-middle py-3 px-4" >Start time</th>
                                    <th class="text-center align-middle py-3 px-4" >End time</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${quizList}" var="q">
                                    <c:if test="${q.isIs_valid()}">
                                        <tr>
                                            <td class="text-center align-middle">${q.getQuiz_id().getQuiz_id()}</td>
                                            <td class="text-center align-middle">${q.getStudent_id().getId()}</td>
                                            <td class="text-left align-middle">
                                                <c:forEach items="${studentClassList}" var="ls">
                                                    <c:if test="${q.getStudent_id().getId() eq ls.getId()}">
                                                        ${ls.getName()}
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td class="text-center align-middle" style="color:red">${q.getScore()}</td>
                                            <td class="text-center align-middle">${q.getStart_time_student()}</td>
                                            <td class="text-center align-middle">${q.getEnd_time_student()}</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>
                    <a href="teacherViewQuiz?class_id=${class_id}" style="color: blue"><b>Back</b></a>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript" src="lib/js/mdb.min.js"></script>
    <script type="text/javascript"></script>
</html>