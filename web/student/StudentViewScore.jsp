<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%@page import="model.Course"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Score</title>
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
            .button:hover {
                background-color: #0056b3;
                color: #fff;
            }

            .row-header{
                display: flex;
            }

            .with-shadow {
                border: 1px solid black;
                border-radius: 1rem;
                padding: 20px;
                width: 520px;
                height: 300px;
                margin-top: 200px;
            }

            .container1 {
                max-width: 500px;
                margin: 0 auto;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            }

            .form-control {
                height: 40px;
            }

            .form-control:focus {
                border: 1px solid #007BFF;
                outline: none;
            }

            .btn-primary {
                background-color: #007BFF;
                color: #fff;
                border: 1px solid #007BFF;
                border-radius: 5px;
                padding: 10px 20px;
                cursor: pointer;
            }

            .btn-primary:hover {
                background-color: #0056b3;
                color: #fff;
            }

            a {
                color: blue;
            }

            a:hover {
                color: #007BFF;
            }

            .offcanvas{
                --bs-offcanvas-width: 480px;
            }

            a.back-link {
                text-decoration: none;
                cursor: pointer;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            a.back-link:hover {
                text-decoration: underline;
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
        <div class="container1" style="margin-top: 80px;">
            <h1 style="text-align: center; color: red">Score: 
                <c:choose>
                    <c:when test="${quiz_result.getScore() != null}">
                        ${quiz_result.getScore()}
                    </c:when>
                    <c:otherwise>
                        0.0
                    </c:otherwise>
                </c:choose>
            </h1>

            <h3 style="color: green">${mess}</h3> 
            <div class="mb-3">
                <label for="course_name" class="form-label">Course Name</label>
                <input type="text" class="form-control" name="course_name" id="course_name" value="${ccq.getCourse_name()}" readonly>
            </div>    

            <div class="mb-3">
                <label for="class_name" class="form-label">Class Name</label>
                <input type="text" class="form-control" name="class_name" id="class_name" value="${ccq.getClass_name()}" readonly>
            </div>      

            <div class="mb-3">
                <label for="quiz_name" class="form-label">Quiz Name</label>
                <input type="text" class="form-control" name="quiz_name" id="quiz_name" value="${ccq.getQuiz_name()}" readonly>
            </div> 

            <div class="mb-3">
                <label for="timeSpend" class="form-label">Time Taken</label>
                <input type="text" class="form-control" name="time_taken" id="time_taken" value="${time_taken}" readonly>
            </div> 

            <c:if test="${is_displaydetail == true}">
                <button style="margin-left: 285px" class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasWithBothOptions" aria-controls="offcanvasWithBothOptions">View Detail Answers</button>
            </c:if>          
        </div>
        <a href="studentViewClass?course_id=${course_id}" class="back-link btn btn-primary" style="margin-left: 710px; text-decoration: none"><b>Back</b></a>


        <div class="offcanvas offcanvas-start" data-bs-scroll="true" tabindex="-1" id="offcanvasWithBothOptions" aria-labelledby="offcanvasWithBothOptionsLabel">
            <div class="offcanvas-header">
                <h3 class="offcanvas-title" id="offcanvasWithBothOptionsLabel" style="color: green">Answer Details</h3><br>                         
                <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>

            <h5 style="color: red">Number Of Questions: ${questionNumber} </h5> 
            <c:set var="averagePoint" value="${10 / questionNumber}" />
            <h5 style="color: red">Average Point Of Question: <c:out value="${averagePoint}" /></h5>


            <hr>
            <div class="offcanvas-body">              
                <div class="m-3" id="questionsBox"></div>
            </div>
        </div>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            var questions = ${questionJson};
            var selectedChoices = ${choosenJson};
            var numberQuestion = ${questionNumber};

            // Lấy giá trị thời gian từ trường input
            var time_taken = document.getElementById("time_taken").value;

            // Chuyển đổi giá trị thời gian thành số
            var time_taken_seconds = parseInt(time_taken);

            // Tính số giờ, phút và giây
            var hours = Math.floor(time_taken_seconds / 3600);
            var minutes = Math.floor((time_taken_seconds % 3600) / 60);
            var seconds = time_taken_seconds % 60 - 1;

            // Hiển thị giá trị trong trường input
            var displayTime = "";
            if (hours > 0) {
                displayTime += hours + "h";
            }
            if (minutes > 0) {
                displayTime += minutes + "m";
            }
            if (seconds > 0) {
                displayTime += seconds + "s";
            }

            document.getElementById("time_taken").value = displayTime.trim();

            function isChosen(answerId) {
                for (let i = 0; i < selectedChoices.length; i++) {
                    if (selectedChoices[i].student_choice.choice_id === answerId) {
                        return true;
                    }
                }
                return false;
            }

            function isCorrect(answerId) {
                var correctIds = [];
                for (let i = 0; i < questions.length; i++) {
                    for (let j = 0; j < questions[i].answers.length; j++) {
                        if (questions[i].answers[j].is_correct === true) {
                            correctIds.push(questions[i].answers[j].choice_id);
                        }
                    }
                }
                return correctIds.includes(answerId);
            }

            $(function () {
                var box = $("#questionsBox");
                for (let i = 0; i < questions.length; i++) {

                    var qText = $("<strong>")
                            .css("color", isAnyChoiceChosen(questions[i].answers) ? 'black' : 'red')
                            .text(questions[i].question.question_text);

                    var qBox = $("<span>")
                            .addClass("fs-5 fw-bold")
                            .html('Q' + (i + 1) + '. ')
                            .append(qText)

                    for (let j = 0; j < questions[i].answers.length; j++) {
                        var choiceInput = $("<input>")
                                .addClass("form-check-input")
                                .attr("type", "checkbox")
                                .prop("checked", isChosen(questions[i].answers[j].choice_id))
                                .prop("disabled", true);
                        var choiceSpan = $("<span>")
                                .addClass("ms-2 ps-2 pe-2 fs-5 fst-normal")
                                .text(questions[i].answers[j].choice_text);

                        if (isCorrect(questions[i].answers[j].choice_id)) {
                            choiceSpan.css("background-color", "#99B76F");
                        }

                        if (isChosen(questions[i].answers[j].choice_id)
                                && !isCorrect(questions[i].answers[j].choice_id)
                                && !qText.hasClass('text-danger')) {
                            qText.append(
                                    document.createTextNode('\tFalse')
                                    )

                            qText.addClass("text-danger")
                        }

                        var choiceBox = $("<div>")
                                .append(choiceInput)
                                .append(choiceSpan);

                        qBox.append(choiceBox);
                    }
                    var lineBreak = $("<br>");
                    box.append(qBox, lineBreak);
                }

                // Function to check if any choice is chosen for a question
                function isAnyChoiceChosen(answers) {
                    return answers.some(answer => isChosen(answer.choice_id));
                }
            });
        </script>
    </body>
</html>

