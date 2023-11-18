<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%@page import="model.Course"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Home</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="lib/css/course.css" />
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        <style>
            body{
                background-color: #edf0f0;
            }

            .heading-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 10px;
            }

            .button {
                padding: 10px 20px;
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

            .edit-button {
                background-color: #f2f2f2;
                color: #007BFF;
                padding: 8px 12px;
                margin: 2px;
                border: 1px solid #007BFF;
                border-radius: 4px;
                text-decoration: none;
                display: inline-block;
                cursor: pointer;
            }

            .edit-button:hover {
                background-color: #007BFF;
                color: white;
            }
        </style>
    </head>
    <body>
        <%
            Account admin = (Account) request.getSession().getAttribute("account");
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
                            <a class="nav-link" href="viewDetail">List User</a>
                        </li>
                    </ul>
                    <div class="d-flex align-items-center justify-content-start">
                        <div class="dropdown">
                            <a class="dropdown-toggle d-flex align-items-center hidden-arrow" href="#" id="navbarDropdownMenuAvatar" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
                                <div style="color:#fff"><%=admin.getName()%></div>&nbsp;
                                <img src="https://cdn-icons-png.flaticon.com/512/6596/6596121.png" class="rounded-circle" height="30" loading="lazy" />
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

        <div class="container">
            <section class="py-4">
                <div class="container">
                    <div class="heading-container">
                        <h2>Course List</h2>
                        <a href="create-course" class="button">Create course</a>
                    </div>
                    <div class="list-course row">
                        <c:set var="pageSize" value="5" />
                        <c:set var="currentPage" value="${empty param.page ? 1 : param.page}" />
                        <c:set var="startIndex" value="${(currentPage - 1) * pageSize}" />
                        <c:set var="endIndex" value="${startIndex + pageSize}" /> 
                        <c:forEach begin="${startIndex}" end="${endIndex}" items="${courseList}" var="c">
                            <div class="col-md-6 col-xl-4">
                                <div class="image-box image-box--shadowed white-bg style-2 mb-4" style="border-radius: 15px;">
                                    <div class="body">
                                        <h4><a href="viewClass?course_id=${c.getCourse_id()}">${c.getCourse_name()}</a></h4>                                      
                                        <div class="row d-flex align-items-center">
                                            <div class="col-10">
                                                <ul class="bottom-course-sum none-list">
                                                    <li><b>Course ID:</b>&nbsp;${c.getCourse_id()}</li>
                                                    <li><b>Semester:</b>&nbsp;${c.getSemester_id().getSemester_id()}</li>
                                                    <li><b>Major:</b>&nbsp;
                                                        <c:choose>
                                                            <c:when test="${c.getMajor_id().getMajor_id() == 1}">
                                                                Software Engineering
                                                            </c:when>
                                                            <c:when test="${c.getMajor_id().getMajor_id() == 2}">
                                                                Marketing
                                                            </c:when>
                                                            <c:otherwise>
                                                                Unknown Major
                                                            </c:otherwise>
                                                        </c:choose> 
                                                    </li>
                                                    <li><b>IsActive:</b>&nbsp;
                                                        <c:choose>
                                                            <c:when test="${c.isIs_visible()}">
                                                                <span style="color: green; font-size: 20px">✓</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span style="color: red; font-size: 20px">✗</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="col-10 text-right" style="display:flex">
                                                <form action="modifyCourse" method="get">
                                                    <input type="hidden" name="course_id" value="${c.getCourse_id()}">
                                                    <button type="submit" class="edit-button">Update</button>
                                                </form>
                                                <a href="#" onclick="showDeleteConfirmation(${c.getCourse_id()})" class="edit-button">Delete</a>
                                                <a href="#" onclick="showConfirmation(${c.getCourse_id()}, ${c.isIs_visible()})" class="edit-button" id="deactivateButton-${c.getCourse_id()}">                                            
                                                    ${c.isIs_visible() ? 'Deactivate' : 'Activate'}
                                                </a> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="pagination" >
                            <c:if test="${currentPage > 1}">
                                <a class="prev" href="home?page=${currentPage - 1}">Previous</a>
                            </c:if>
                            <c:if test="${endIndex < courseList.size()}">
                                <a class="next" href="home?page=${currentPage + 1}">Next</a>
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
                                                    function showConfirmation(course_id, is_visible) {
                                                        if (is_visible) {
                                                            if (confirm("Do you want to deactivate Course with ID: " + course_id + " ?")) {
                                                                window.location = "deleteUpdate?course_id=" + course_id + "&mod=4";
                                                                document.getElementById(`deactivateButton-${course_id}`).innerText = 'Activate';
                                                            }
                                                        } else {
                                                            if (confirm("Do you want to activate Course with ID: " + course_id + " ?")) {
                                                                window.location = "deleteUpdate?course_id=" + course_id + "&mod=6";
                                                                document.getElementById(`deactivateButton-${course_id}`).innerText = 'Deactivate';
                                                            }
                                                        }
                                                    }

                                                    function showDeleteConfirmation(course_id) {
                                                        if (confirm("Do you want to delete Course with ID: " + course_id)) {
                                                            window.location = "deleteUpdate?course_id=" + course_id + "&mod=8";
                                                        }
                                                    }

        </script>
    </body>
</html>
