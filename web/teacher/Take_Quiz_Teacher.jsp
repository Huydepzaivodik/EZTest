<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take quiz</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >       
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <style>
            #timer {
                text-align: center;
                font-size: 24px;
                margin-top: 20px;
            }

            #question-order {
                font-size: 18px;
            }

            #question-index {
                font-size: 30px;
            }

            #question-text {
                font-size: 24px;
                margin-left: 10px;
            }

            #answers {
                font-size: 20px;
            }

            .nav-button, .submit-button {
                background-color: #007BFF;
                color: white;
                border: none;
                padding: 10px 20px;
                margin: 5px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            .nav-button:hover, .submit-button:hover {
                background-color: #0056b3;
            }

            #buttons {
                text-align: center;
                margin-top: 20px;
            }

            #buttons .submit-button {
                background-color: #28a745;
            }

            #buttons .submit-button:hover {
                background-color: #1e7e34;
            }

            #buttons .nav-button:hover {
                background-color: #5a6268;
            }
            #question-buttons1 {
                margin-top: 20px;
            }

            #question-buttons1 button {
                height: 40px;
                margin: 2px;
                background-color: #007bff;
                color: #fff;
                border: #007bff;
                margin-bottom: 10px;
                border-radius: 8px;
            }
            body {
                height: 100vh;
                background: linear-gradient(184deg,#05386B,black);
            }
            .start-screen,
            .score-container {
                position: absolute;
                top: 0;
                width: 100%;
                height: 100%;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }


            #display-container {
                background-color: whitesmoke;
                padding: 3.1em 1.8em;
                width: 80%;
                max-width: 37.5em;
                margin: 0 auto;
                position: absolute;
                transform: translate(-50%, -50%);
                top: 50%;
                left: 50%;
                border-radius: 0.6em;
            }
            .header {
                margin-bottom: 1.8em;
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 0.6em;
                border-bottom: 0.1em solid #c0bfd2;
            }

            .timer-div {
                background-color: #dc3545;
                width: 15.5em;
                border-radius: 1.8em;
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 0.7em 1.8em;
            }

            .col-3{
                width: 20%;
            }

            #question-buttons1 button.selected {
                font-weight: bold;
            }

            .custom-checkbox {
                /* Tùy chỉnh kiểu checkbox/radio nếu cần thiết */
            }

            .checkbox-container {
                margin-bottom: 10px;
            }

            .custom-checkbox-label {
                margin-left: 5px;
                font-size: 16px;
            }

            .checkbox-container:hover {
                background-color: #f4f4f4;
                cursor: pointer;
            }

            .checkbox-container input:checked + .custom-checkbox-label {
                font-weight: bold;
            }

            p {
                min-height: 100vh;
                display: flex;
                color: wheat;
                justify-content: center;
                font-family: 'Work Sans', sans-serif;
                font-weight: 900;
                font-size: 6vw;
                text-transform: uppercase;
                line-height: 1;
            }

        </style>
    </head>
    <body >
        <p>EzTest</p>
        <div id="display-container" style="max-width: 81.5em" class="left">
            <div class="header">
                <div class="number-of-count">
                    <h3 style="color: green"><span class="number-of-question ">Number Of Question: ${totalQuestion}</span>                     
                </div>
                <div class="timer-div">                  
                    <b><span style="font-size: 20px; color: #fff"><i class="fa fa-clock" aria-hidden="true"></i> Time Left: <span id="countdown"></span></span></b> 
                </div>
            </div>
            <div class="father" style="display: flex">
                <div id="container" style="width: 100%">
                    <span class="fs-3">
                        <b><span class="fs-3" id="question-index" style="font-size: 30px"></span></b> 
                        <span class="fs-3" id="question-text"></span><br>
                        <span class="fs-5" id="explanation"></span>
                    </span>
                    <div id="answers" style="font-size: 20px"></div>

                </div>
                <div class="right">
                    <div class="right-border">
                        <div id="question-buttons1" style="width: 10rem">
                        </div>
                    </div>
                </div>
            </div>
            <button class="btn btn-primary" onclick="prevQuestion()"><i class="fa fa-arrow-left" aria-hidden="true"></i></button>
            <button class="btn btn-primary" onclick="nextQuestion()"><i class="fa fa-arrow-right" aria-hidden="true"></i></button>
            <button id="red-flag-button" class="btn btn-danger" onclick="toggleRedFlag()"><i class="fa fa-flag" aria-hidden="true"></i> Red flag</button>


            <button class="submit-button" onclick="submitExam()" style="margin-left: 800px">Submit</button>
        </div>
        <script>
            var questionIndex = 1;
            var quizId = ${quiz_id};
            var questionsSize = ${totalQuestion};
            var questionData = null;
            var teacherId = ${teacher_id};
            var courseId = ${course_id};
            var currentNum = 1;
            var totalSeconds = ${duration};

            // Tính số phút và số giây
            var minutes = Math.floor(totalSeconds / 60);
            var seconds = totalSeconds % 60;

            var countdownMinutes = minutes;
            var countdownSeconds = seconds;

            var remainingMinutes = countdownMinutes;
            var remainingSeconds = countdownSeconds;

            var countdownInterval;
            var isSubmitted = false;







            function preventBack() {
                window.history.forward();
            }
            setTimeout("preventBack()", 0);
            window.onunload = function () {
                null;
            };

            $(document).ready(function () {

                localStorage.removeItem('countdownMinutes');
                localStorage.removeItem('countdownSeconds');

                // Kiểm tra xem đã có startTime trong localStorage chưa
                var startTime = localStorage.getItem('startTime');

                if (!startTime) {
                    // Nếu chưa có, lưu thời điểm bắt đầu vào localStorage
                    startTime = new Date().getTime();
                    localStorage.setItem('startTime', startTime);
                }


                if (localStorage.getItem('countdownMinutes') && localStorage.getItem('countdownSeconds')) {
                    countdownMinutes = parseInt(localStorage.getItem('countdownMinutes'));
                    countdownSeconds = parseInt(localStorage.getItem('countdownSeconds'));
                    updateCountdown(); // Cập nhật hiển thị thời gian
                }

                showQuestionAtCurrentIndex();
                startCountdown();
                preventBack();
            });

            var questionButtons = $("#question-buttons1");
            questionButtons.addClass("row");
            for (var i = 1; i <= questionsSize; i++) {

                var button = $("<button>")
                        .attr("id", "q" + i)
                        .addClass("col-3")
                        .text(i)
                        .on("click", function () {
                            submitCurrentChoices();
                            var questionId = $(this).attr("id"); // Lấy id của nút 
                            questionIndex = $(this).index() + 1;
                            showQuestionAtCurrentIndex();
                        });
                questionButtons.append(button);
            }



            function updateQuestionText() {
                $('#question-order').text(questionIndex + '/' + questionsSize);
                $('#question-index').text('Question ' + questionIndex + ":");
                $('#question-text').text(questionData.question_text);
                $('#explanation').text('(' + questionData.explanation + ')');

            }

            function startCountdown() {
                if (isSubmitted) {
                    return;
                }
                updateCountdown(); // Cập nhật hiển thị đếm ngược ban đầu
                countdownInterval = setInterval(function () {
                    if (countdownMinutes === 0 && countdownSeconds === 0) {
                        clearInterval(countdownInterval);
                        submitCurrentChoices();
                        disableButtons();
                        localStorage.removeItem('countdownMinutes');
                        localStorage.removeItem('countdownSeconds');
                        localStorage.removeItem('isSubmitted');

                        // Lấy thời gian bắt đầu từ localStorage
                        var startTime = localStorage.getItem('startTime');

                        // Lấy thời gian kết thúc 
                        var endTime = new Date(parseInt(startTime) + totalSeconds * 1000).getTime();

                        alert("Time end!");
                        // Xóa localStorage 'startTime'
                        localStorage.removeItem('startTime');
                        window.location.href = "calScore?teacher_id=" + teacherId + "&quiz_id=" + quizId + "&course_id=" + courseId + "&endTime=" + endTime + "&startTime=" + startTime;
                    } else {
                        if (countdownSeconds === 0) {
                            countdownMinutes--;
                            countdownSeconds = 59;
                        } else {
                            countdownSeconds--;
                        }
                        updateCountdown(); // Cập nhật hiển thị đếm ngược sau mỗi giây
                    }
                }, 1000);
            }

            function updateCountdown() {
                var countdownDisplay = $('#countdown');
                var minutes = countdownMinutes < 10 ? '0' + countdownMinutes : countdownMinutes;
                var seconds = countdownSeconds < 10 ? '0' + countdownSeconds : countdownSeconds;
                countdownDisplay.text(minutes + ':' + seconds);

                // Lưu trạng thái thời gian còn lại vào localStorage
                localStorage.setItem('countdownMinutes', countdownMinutes);
                localStorage.setItem('countdownSeconds', countdownSeconds);
            }

            function updateQuestionAnswers() {
                var answersDiv = $('#answers');
                answersDiv.empty();

                for (var i = 0; i < questionData.choiceListOfQuestion.length; i++) {
                    var choice = questionData.choiceListOfQuestion[i];

                    var input = $('<input>')
                            .attr('type', questionData.question_type === true ? "radio" : "checkbox")
                            .attr('id', "choice" + choice.choice_id)
                            .attr('name', 'choice')
                            .attr('value', choice.choice_id)
                            .addClass('custom-checkbox');

                    var label = $('<label>')
                            .attr('for', "choice" + choice.choice_id)
                            .text(choice.choice_text)
                            .addClass('custom-checkbox-label');

                    var checkboxContainer = $('<div>')
                            .addClass('checkbox-container')
                            .append(input)
                            .append(label);

                    answersDiv.append(checkboxContainer);
                }
            }



            function toggleRedFlag() {
                var btn = $('#q' + questionIndex);

                var currentBackgroundColor = btn.css("background-color");
                if (currentBackgroundColor === 'rgb(255, 0, 0)') { // Kiểm tra xem màu nền có phải là màu đỏ (RGB)
                    btn.css("background-color", ""); // Bỏ màu đỏ

                } else {
                    btn.css("background-color", "red"); // Thêm màu đỏ
                }
            }

            function toggleSelectedBox() {
                var btn = $('#q' + questionIndex);
                btn.css("background-color", "green");
            }
            function toggleUnSelectedBox() {
                var btn = $('#q' + questionIndex);
                var currentBackgroundColor = btn.css("background-color");
                if (currentBackgroundColor === 'rgb(255, 0, 0)') {
                    return true;
                } else {

                    btn.css("background-color", ""); // Bỏ màu xanh
                }
            }
            function isSelectedChoice() {
                var selected = false;
                $(".custom-checkbox").each(function () {
                    if ($(this).is(":checked")) {
                        selected = true;
                    }
                });
                return selected;
            }

            function nextQuestion() {
                if (isSelectedChoice()) {
                    toggleSelectedBox();
                } else {
                    toggleUnSelectedBox();
                }
                submitCurrentChoices();
                if (questionIndex === questionsSize) {
                    questionIndex = 1;
                } else {
                    questionIndex++;
                }
                showQuestionAtCurrentIndex();


            }

            function prevQuestion() {
                if (isSelectedChoice()) {
                    toggleSelectedBox();
                } else {
                    toggleUnSelectedBox();
                }
                submitCurrentChoices();
                if (questionIndex === 1) {
                    questionIndex = questionsSize;
                } else {
                    questionIndex--;
                }
                showQuestionAtCurrentIndex();
            }

            function disableButtons() {
                $(".nav-button").prop("disabled", true);
                $(".submit-button").prop("disabled", true);
            }

            function submitExam() {
                submitCurrentChoices();


                // Lấy thời điểm bắt đầu từ localStorage
                var startTime = localStorage.getItem('startTime');

                // Tính thời gian còn lại từ đồng hồ đếm ngược
                var remainingTimeInSeconds = countdownMinutes * 60 + countdownSeconds;

                // Tính thời gian đã làm bài bằng cách trừ thời gian còn lại từ thời gian tối đa
                var timeTakenInSeconds = totalSeconds - remainingTimeInSeconds;

                var endTime = new Date(parseInt(startTime) + timeTakenInSeconds * 1000).getTime();
 
                var confirmed = confirm("Bạn có chắc chắn muốn nộp bài?");
                if (confirmed) {
                    isSubmitted = true;
                    disableButtons();
                    clearInterval(countdownInterval);

                    // Đặt thời gian đếm ngược thành 0 và cập nhật hiển thị
                    countdownMinutes = 0;
                    countdownSeconds = 0;
                    updateCountdown();

                    // Xóa dữ liệu từ localStorage
                    localStorage.removeItem('startTime');
                    localStorage.removeItem('countdownMinutes');
                    localStorage.removeItem('countdownSeconds');
                    localStorage.removeItem('isSubmitted');

                    var teacher_id = teacherId;  // Thay thế bằng giá trị thực tế
                    var quiz_id = quizId;    // Thay thế bằng giá trị thực tế

                    window.location.href = "calScore?teacher_id=" + teacher_id + "&quiz_id=" + quiz_id + "&course_id=" + courseId + "&endTime=" + endTime + "&startTime=" + startTime ;

                } else {
                    submitCurrentChoices();
                    alert('Bài của bạn chưa được nộp.');
                }

            }

            function submitCurrentChoices() {
                var selectedAnswers = [];
                $('input:checked').each(function () {
                    var value = $(this).val();
                    selectedAnswers.push(parseInt(value, 10));
                });
                if (selectedAnswers.length === 0) {
                    //Nếu câu nào chưa được trả lời thì thêm
                    addNewChoice();
                } else {
                    // Nếu có câu trả lời được chọn, cập nhật câu trả lời
                    updateChoice(selectedAnswers);
                }
            }

            function addNewChoice() {
                var selectedAnswers = [];
                $('input:checked').each(function () {
                    var value = $(this).val();
                    selectedAnswers.push(parseInt(value, 10));
                });
                var selectedChoicesJSON = {
                    teacherId: teacherId,
                    questionId: questionData.question_id,
                    selectedAnswerIds: selectedAnswers,
                    quizId: quizId
                };

                $.post("attempt", {
                    "action": "teacher",
                    "selectedChoicesJSON": JSON.stringify(selectedChoicesJSON)
                }, function (data) {
                    // Xử lý kết quả (nếu cần)
                }, "json");
            }

            function updateChoice(selectedAnswers) {
                var selectedChoicesJSON = {
                    teacherId: teacherId,
                    questionId: questionData.question_id,
                    selectedAnswerIds: selectedAnswers,
                    quizId: quizId
                };

                $.post("attempt", {
                    "action": "teacher",
                    "selectedChoicesJSON": JSON.stringify(selectedChoicesJSON)
                }, function (data) {
                    // Xử lý kết quả (nếu cần)
                }, "json");
            }

            function showQuestionAtCurrentIndex() {
                $.get("attempt", {
                    "questionIndex": questionIndex,
                    "quizId": quizId
                }, function (data) {
                    questionData = JSON.parse(data);
                    updateQuestionText();
                    updateQuestionAnswers();
                    markSubmittedChoices();
                });
            }


            function markSubmittedChoices() {
                $.get("submittedChoice", {
                    "action": "teacher",
                    "teacherId": teacherId,
                    "questionId": questionData.question_id,
                    "quizId": quizId
                }, function (data) {
                    if (questionData.question_type === true) {
                        var selectedAnswerId = parseInt(data);
                        $('input[value="' + selectedAnswerId + '"]').prop("checked", true);
                    } else if (questionData.question_type === false) {
                        var selectedAnswerIds = String(data).split(",");
                        for (var i = 0; i < selectedAnswerIds.length; i++) {
                            var answerId = selectedAnswerIds[i];
                            $('input[value="' + answerId + '"]').prop("checked", true);
                        }
                    }
                });
            }
        </script>
    </body>
</html> 