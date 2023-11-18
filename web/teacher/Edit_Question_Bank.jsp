<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Question Bank</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <style>
            a
            .detail-bank {
                width: 60%;
                overflow: auto;
                text-align: center; /* Căn giữa nội dung trong bảng */
            }

            h3 {
                text-align: center;
                margin: 20px 0;
            }

            .row-header {
                display: flex;
            }

            div.row {
                width: 1100px;
            }

            .container_add {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                max-height: 80vh;
                overflow-y: auto;
                margin-left: 500px;
            }

            .form-control {
                background-color: #f1f1f1;
            }

            .btn-primary {
                background-color: #6D8EB9;
                border: none;
            }

            .btn-primary:hover {
                background-color: #5a75a0;
            }

            .form-control-short {
                width: 837px;
                padding: 4px 4px;
                font-size: 12px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            .detail-bank {
                margin: 0 auto;
                width: 60%;
                font-family: Arial, sans-serif;
                overflow: auto;
            }

            .detail-bank h2 {
                text-align: center;
                margin-bottom: 20px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            table, th, td {
                border: 1px solid #ddd;
                background: #ffffff
            }

            th, td {
                padding: 12px;
                text-align: left;
            }

            th {
                background-color: #05386B;
            }

            tr:nth-child(even) {
                background-color: white;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            .question-bank-header {
                background-color: #0000ff;
                color: #EDF5E1;
            }

            .question-bank-row {
                background-color: #ffffff;
                color: black;
            }
            .question-bank-header:hover {
                background-color: white;
            }
            .question-bank-row:hover {
                background-color: #ccffff;
            }

            .question-bank-checkbox {
                background-color: #ffffff;
            }

            /* Thay đổi màu của nút checkbox khi chưa được chọn */
            input[type="checkbox"] {
                border: 2px solid red; /* Màu đường viền khi chưa được chọn */
                background-color: #fff; /* Màu nền khi chưa được chọn */
            }

            /* Thay đổi màu của nút checkbox khi đã được chọn */
            input[type="checkbox"]:checked {
                border: 2px solid red; /* Màu đường viền khi đã được chọn */
                background-color: #00ff00; /* Màu nền khi đã được chọn */
            }
            /*
            */                body{
                background-color: #edf0f0;
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

        <div class="detail-bank" style="margin: 0 auto; width: 60%; overflow: auto;">
            <h2>Question Bank Data</h2>
            <form action="add-question-bank" method="post"> <!-- Thay đổi action thành địa chỉ URL xử lý form -->
                <table border="1">
                    <c:forEach items="${qbList}" var="qb">
                        <tr class="question-bank-header">
                            <th>Question Bank ID</th>
                            <!--                            <th>Course ID</th>-->
                            <th>Question Text</th>
                            <th>Explanation</th>
                            <th>Question Type</th>
                            <th>Add to Quiz</th> <!-- Thêm cột mới cho checkbox -->
                        </tr>
                        <tr class="question-bank-row">
                            <td>${qb.getQuestion_bank_id()}</td>
<!--                            <td>${qb.getCourse_id().getCourse_id()}</td>-->
                            <td>${qb.getQuestion_text()}</td>
                            <td>${qb.getExplanation()}</td>
                            <td> <c:choose>
                                    <c:when test="${qb.isQuestion_type()}">
                                        <span style="color: #379683
                                              ; ">Single Choice</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #379683
                                              ; ">Multiple Choice</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
<!--                            <td>${qb.isQuestion_type()}</td>-->
                            <td class="question-bank-checkbox">
                                <input type="checkbox" name="questionBankIds" value="${qb.getQuestion_bank_id()}">
                            </td>
                        </tr>
                        <td style="background: #ffffff; color: green"  colspan="4"> Answer:</td>
                        <!--                        <tr class="question-bank-header" >
                                                    <th>Choice Bank ID</th>
                                                    <th>Question Bank ID</th>
                                                    <th>Choice Text</th>
                                                    <th>Is Correct</th>
                                                    <th>Weight</th>
                                                </tr>-->
                        <c:forEach items="${cbList}" var="cb">
                            <c:if test="${cb.getQuestion_bank_id().getQuestion_bank_id() == qb.getQuestion_bank_id()}">
                                <tr class="question-bank-row">
                                    <td>${cb.getChoice_bank_id()}</td>
<!--                                    <td>${cb.getQuestion_bank_id().getQuestion_bank_id()}</td>-->
                                    <td>${cb.getChoice_bank_text()}</td>
                                    <td> <c:choose>
                                            <c:when test="${cb.isIs_correct()}">
                                                <span style="color: green; font-size: 25px">✓</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: red; font-size: 25px">✗</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${cb.getWeight()}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <!--                                <tr class="questionBankRow">
                                                            
                                                        </tr>-->
                        <tr>
                            <td colspan="5">&nbsp;</td>
                        </tr>
                    </c:forEach>
                </table>
                <button style="margin-left: 90% "class="btn btn-info" type="submit">Add</button> <!-- Thêm nút submit để thêm vào quiz -->
            </form>
        </div>
        <!--bên trong để đây-->

        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script type="text/javascript"></script>       
    </body>
</html>
