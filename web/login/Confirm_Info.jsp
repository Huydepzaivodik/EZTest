<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>

    <head>
        <meta charset="UTF-8">
        <title>Confirm Account</title>
        <meta charset="UTF-8">
        <link rel="shortcut icon" type="image/png" href="image/logo.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

        <style>
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
                height: 100px;
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
                margin-bottom: 2px;
                font-size: 14px;
                font-weight: bold;
            }

            .author-card-profile .author-card-position {
                display: block;
                color: #8c8c8c;
                font-size: 12px;
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
        </style>
    </head>

    <body>


        <div class="container mt-5">
            <div class="row">

                <div class="col-lg-4 pb-5 mt-5">
                    <!-- Account Sidebar-->
                    <div class="author-card pb-3">
                        <div class="author-card-cover"
                             style="background-image: url(https://www.schudio.com/wp-content/uploads/2016/08/welcome-page-blog-header.jpg);">
                        </div>
                        <div class="author-card-profile">
                            <div class="author-card-avatar"><img
                                    src="https://icons.veryicon.com/png/o/miscellaneous/two-color-icon-library/user-286.png">
                            </div>
                            <div class="author-card-details">
                                <h5 class="author-card-name text-lg">CONFIRM ACCOUNT</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Profile Settings-->
                <div class="col-lg-8 pb-5 mt-5">
                    <form action="confirmInfo" method="post">
                        <div class="row">

                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="account-fn">User Name</label>
                                    <div class="input-group">
                                        <input class="form-control" name="pre_email" type="text" required value="${r_email}">
                                        <select class="form-control" name="sub_email">
                                            <option value="@gmail.com" <c:if test="${sub_mail eq '@gmail.com'}">selected="selected"</c:if>>@gmail.com</option>
                                            <option value="@fe.edu.vn" <c:if test="${sub_mail eq '@fe.edu.vn'}">selected="selected"</c:if>>@fe.edu.vn</option>
                                            <option value="@fpt.edu.vn" <c:if test="${sub_mail eq '@fpt.edu.vn'}">selected="selected"</c:if>>@fpt.edu.vn</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="account-ln">DOB</label>
                                        <input class="form-control" type="date" name="dob" value="${r_dob}" required="">
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="account-ln">Gender</label>
                                    <select class="form-control" name="gender" required="">
                                        <option value="male" <c:if test="${r_gender eq 'male'}">selected = "selected"</c:if>>Male</option>
                                        <option value="female" <c:if test="${r_gender eq 'female'}">selected = "selected"</c:if>>Female</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="account-phone">Phone Number</label>
                                        <div class="input-group">
                                            <input class="form-control" type="text" name="pre" value="(+84)" readonly>
                                            <input class="form-control" type="text" name="phone" value="${r_phone}" required="">
                                    </div>
                                </div>
                            </div>


                            <div class="col-md-12">
                                <div class="form-group">
                                    <div class="input-group">
                                        <c:if test="${not empty errorMessage1}">
                                            <p style="color: red;">${errorMessage1}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>


                            <div class="col-md-12 d-flex flex-column align-items-center">
                                <div class="d-flex flex-wrap justify-content-between align-items-center">
                                    <button class="btn btn-style-1 btn-primary mt-5 mb-4">Confirm Account</button>
                                </div>
                            </div>

                        </div>
                    </form>
                </div>
            </div>


            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </body>

</html>