<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Question</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <style>
            body{
                background-color: #edf0f0;
            }

            h3 {
                text-align: left;
                margin-top: 0px;
            }

            .row-header{
                display: flex;
            }

            .container_add {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                max-height: 86vh;
                overflow-y: auto;
                max-width: 800px;
                margin: auto;
            }

            .form-control {
                background-color: #f1f1f1;
                width: 100%;
            }

            .btn-primary {
                background-color: #6D8EB9;
                border: none;
            }
            .btn-primary:hover {
                background-color: #5a75a0;
            }
            .is-correct-checkbox{
                height: 15px;
                width: 15px;
            }
        </style>
    </head>
    <body>
        <%
            Account teacher = (Account) request.getSession().getAttribute("account");
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

        <div class="container_add mt-3" style="font-size: 15px">
            <h3 style="color: #20B2AA">Edit Quiz Question #${qq.question_id}</h3>
            <form action="editquizinfo" method="get" onsubmit="return validateWeights() && validateAnswers()">
                <div class="form-group">
                    <label for="questionContent">Question Content:</label>
                    <textarea class="form-control" name="questionContent" id="questionContent" required>${qq.question_text}</textarea>
                </div>
                <div class="form-group">
                    <label for="explanation">Explanation:</label>
                    <textarea class="form-control" name="explanation" id="explanation">${qq.explanation}</textarea>
                </div>
                <div class="form-group">
                    <label for="questionType">Question Type:</label>
                    <select name="questionType" id="questionType" style="width: 200px; height: 45px" class="form-control" onchange="toggleWeights(this.value === 'multiple')">
                        <option value="single" <c:if test="${qq.question_type eq 'single'}">selected = "selected"</c:if>>Single Correct Answer</option>
                        <option value="multiple" <c:if test="${qq.question_type eq 'multiple'}">selected = "selected"</c:if>>Multiple Correct Answers</option>
                        </select>
                    </div>
                    <div id="answer-container">
                        <div class="answer-template">
                        <c:forEach items="${choice}" var="c" varStatus="loop">
                            <div class="answer">
                                <div class="form-group">
                                    <label for="answerContent_${loop.index}">Answer Content:</label>
                                    <textarea type="text" name="answerContent_${loop.index}" class="form-control" required>${c.choice_text}</textarea>
                                </div>
                                <div class="form-group">
                                    <label for="isCorrect_${loop.index}">IsCorrect:</label>
                                    <input type="checkbox" name="isCorrect_${loop.index}" id="isCorrect_${loop.index}" class="is-correct-checkbox" onclick="checkSingleCorrect(this)" <c:if test="${c.is_correct}">checked</c:if>>
                                    </div>
                                    <div class="form-group">
                                        <label for="weight_${loop.index}">Weight:</label>
                                    <c:choose>
                                        <c:when test="${c.weight == -100}">
                                            <input type="number" name="weight_${loop.index}" class="form-control weight-input" disabled  min="1" max="100" style="height: 40px;width: 80px">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="number" name="weight_${loop.index}" class="form-control weight-input"  min="1" max="100" value="${c.weight}" style="height: 40px;width: 80px">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <a href="#" class="delete-button" 
                                   onclick="showAlert(${class_id}, ${quiz_id}, '${quiz_name}', ${qq.question_id}, ${c.choice_id})"
                                   title="Delete Choice" 
                                   style="display: ${loop.index < 2 ? 'none' : 'inline-block'}">
                                    <i class="fas fa-trash" style="color:red"></i>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <button type="button" class="btn btn-outline-success" style="margin-top:10px" onclick="addAnswer()">
                    <i class="fas fa-plus"></i>
                </button>
                <input name="classId" type="hidden" value="${class_id}">
                <input name="quizId" type="hidden" value="${quiz_id}">
                <input name="quizName" type="hidden" value="${quiz_name}">
                <input name="questionId" type="hidden" value="${qq.question_id}">
                <button name="mod" type="submit" class="btn btn-primary" value="updateQues" style="margin-left: 615px; padding: 10px">Save Question</button>
            </form>
        </div>
        <script type="text/javascript">
            function toggleWeights(isMultiple) {
                var weightInputs = document.querySelectorAll(".weight-input");
                var isCorrectCheckboxes = document.querySelectorAll(".is-correct-checkbox");

                for (var i = 0; i < weightInputs.length; i++) {
                    var weightInput = weightInputs[i];
                    var isCorrectCheckbox = isCorrectCheckboxes[i];

                    if (isMultiple) {
                        weightInput.disabled = !isCorrectCheckbox.checked;
                    } else {
                        if (isCorrectCheckbox.checked) {
                            weightInput.value = "100";
                        } else {
                            weightInput.value = "";
                        }
                        weightInput.disabled = !isCorrectCheckbox.checked; // Disable weight input if isCorrect is not checked
                    }
                }
            }

            var loopIndexArray = [];
            <c:forEach items="${choice}" var="c" varStatus="loop">
            loopIndexArray.push(${loop.index});
            </c:forEach>


            let index = loopIndexArray.length; // Khởi tạo biến index

            let answerIndex = loopIndexArray.length;

            function addAnswer() {

                console.log("answerIndex: " + answerIndex); // In giá trị của answerIndex ra console

                const answerContainer = document.getElementById("answer-container");
                const newAnswer = document.createElement("div");
                newAnswer.classList.add("answer");

                // HTML cho câu trả lời
                newAnswer.innerHTML = `
        <div class="form-group">
            <label for="answerContent` + answerIndex + `">Answer Content:</label>
            <textarea type="text" name="answerContent_` + answerIndex + `" class="form-control" required></textarea>
        </div>
        <div class="form-group">
            <label for="isCorrect` + answerIndex + `">IsCorrect:</label>
            <input type="checkbox" name="isCorrect_` + answerIndex + `" id="isCorrect` + answerIndex + `" class="is-correct-checkbox" onclick="checkSingleCorrect(this)">
        </div>
        <div class="form-group">
            <label for="weight` + answerIndex + `">Weight:</label>
            <input type="number" name="weight_` + answerIndex + `" class="form-control weight-input" disabled min="1" max="100" style="height: 40px;width: 80px">
        </div>
        <button type="button" class="btn btn-outline-danger" onclick="deleteAnswer(this)">
                    <i class="fas fa-trash"></i>
                </button>

    `;
                answerIndex++;
                answerContainer.appendChild(newAnswer);
            }

            function validateAnswers() {
                var answerInputs = document.querySelectorAll('textarea[name^="answerContent"]');
                var validAnswerCount = 0;

                answerInputs.forEach(function (input) {
                    if (input.value.trim() !== "") {
                        validAnswerCount++;
                    }
                });

                if (validAnswerCount < 2) {
                    alert("You must provide at least 2 answers for a question.");
                    return false;
                }

                return true;
            }

            function deleteAnswer(deleteButton) {
                const answerContainer = document.getElementById("answer-container");
                const answerDiv = deleteButton.parentElement; // Lấy phần tử cha của nút xóa

                if (answerDiv) {
                    answerContainer.removeChild(answerDiv);
                }
            }

            function checkSingleCorrect(checkbox) {
                var checkboxes = document.querySelectorAll(".is-correct-checkbox");
                if (document.getElementById("questionType").value === "single") {
                    for (var i = 0; i < checkboxes.length; i++) {
                        if (checkboxes[i] !== checkbox) {
                            checkboxes[i].checked = false;
                            var weightInput = checkboxes[i].parentElement.nextElementSibling.querySelector(".weight-input");
                            weightInput.value = "";
                        }
                    }

                    if (checkbox.checked) {
                        var weightInput = checkbox.parentElement.nextElementSibling.querySelector(".weight-input");
                        weightInput.value = "100";
                    }
                } else { // Multiple correct answer
                    var weightInput = checkbox.parentElement.nextElementSibling.querySelector(".weight-input");
                    if (!checkbox.checked) {
                        weightInput.value = ""; // Set weight to empty when isCorrect is unchecked
                    }
                }

                toggleWeights(document.getElementById("questionType").value === 'multiple');
            }


            function validateWeights() {
                var questionType = document.getElementById("questionType").value;
                var weightInputs = document.querySelectorAll(".weight-input");
                var totalWeight = 0;

                for (var i = 0; i < weightInputs.length; i++) {
                    var weightInput = weightInputs[i];
                    var isCorrectCheckbox = weightInput.parentElement.previousElementSibling.querySelector(".is-correct-checkbox");

                    if (isCorrectCheckbox.checked) {
                        var weight = parseInt(weightInput.value);
                        if (!isNaN(weight)) {
                            if (weight < 1 || (questionType === "single" && weight !== 100) || (questionType === "multiple" && weight >= 100)) {
                                alert("Weight for each answer must be between 1 and 99 for Multiple Correct Answers and exactly 100 for Single Correct Answer.");
                                return false;
                            }
                            totalWeight += weight;
                        } else {
                            alert("Please provide a valid weight for the correct answer(s).");
                            return false;
                        }
                    }
                }

                if (questionType === "single" && totalWeight !== 100) {
                    alert("Total weight for Single Correct Answer must be 100.");
                    return false;
                } else if (questionType === "multiple" && totalWeight !== 100) {
                    alert("Total weight for Multiple Correct Answers must be 100.");
                    return false;
                }

                // Additional validation as needed

                // Check for empty/whitespace in questionContent and explanation
                var questionContent = document.getElementById("questionContent").value.trim();
                var explanation = document.getElementById("explanation").value.trim();

                if (questionContent === "" || explanation === "") {
                    alert("Question content and explanation cannot be empty or contain only whitespace.");
                    return false;
                }

                // Check for empty/whitespace in answerContent fields
                var answerContents = document.querySelectorAll("textarea[name^='answerContent']");
                for (var j = 0; j < answerContents.length; j++) {
                    var answerContent = answerContents[j].value.trim();
                    if (answerContent === "") {
                        alert("Answer content cannot be empty or contain only whitespace.");
                        return false;
                    }
                }

                // Additional checks as needed

                return true;
            }

            function validateChoices() {
                var answerInputs = document.querySelectorAll('textarea[name^="answerContent"]');
                var validAnswerCount = 0;

                answerInputs.forEach(function (input) {
                    if (input.value.trim() !== "") {
                        validAnswerCount++;
                    }
                });

                if (validAnswerCount < 2) {
                    alert("You must provide at least 2 choices for a question.");
                    return false;
                }

                return true;
            }

            function validateAnswers() {
                var answerInputs = document.querySelectorAll('textarea[name^="answerContent"]');
                var validAnswerCount = 0;

                answerInputs.forEach(function (input) {
                    if (input.value.trim() !== "") {
                        validAnswerCount++;
                    }
                });

                if (validAnswerCount < 2) {
                    alert("You must provide at least 2 answers for a question.");
                    return false;
                }

                return true;
            }
            function showAlert(classId, quizId, quizName, questionId, choiceId) {
                if (confirm("Do you want to DELETE this choice?")) {
                    window.location = "editquizinfo?class_id=" + classId + "&quiz_id=" + quizId + "&quiz_name=" + quizName + "&question_id=" + questionId + "&choice_id=" + choiceId + "&mod=deleteChoice";
                }
            }
        </script>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>

    </body>
</html>


