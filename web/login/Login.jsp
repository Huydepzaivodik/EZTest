<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EzTest</title>
        <link rel="shortcut icon" type="image/png" href="image/logo.png">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <%
            String notice= "";
            if(request.getAttribute("notice")!=null)
                notice = (String)request.getAttribute("notice");
        %>



        <style>
            body {
                color: #05386B;
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh; /* Chiều cao 100% của viewport */
                margin: 0;
            }

            .card0 {
                box-shadow: 0px 4px 8px 0px #757575;
                border-radius: 0px;
            }

            .card2 {
                margin: 0px 40px;
            }

            .logo {
                width: 100px;
                height: 50px;
                margin-top: 20px;
                margin-left: 35px;
            }

            .image {
                width: 340px;
                height: 310px;
            }

            .border-line {
                border-right: 1px solid #EEEEEE;
            }

            .google {
                background-color: #db4a39;
                color: white;
                font-size: 18px;
                padding-top: 5px;
                border-radius: 50%;
                width: 35px;
                height: 35px;
                cursor: pointer;
            }

            .line {
                height: 1px;
                width: 45%;
                background-color: #E0E0E0;
                margin-top: 10px;
            }

            .or {
                width: 10%;
                font-weight: bold;
            }

            .text-sm {
                font-size: 14px !important;
            }

            ::placeholder {
                color: #BDBDBD;
                opacity: 1;
                font-weight: 300
            }

            :-ms-input-placeholder {
                color: #BDBDBD;
                font-weight: 300
            }

            ::-ms-input-placeholder {
                color: #BDBDBD;
                font-weight: 300
            }

            input,
            textarea {
                padding: 10px 12px 10px 12px;
                border: 1px solid lightgrey;
                border-radius: 2px;
                margin-bottom: 5px;
                margin-top: 2px;
                width: 100%;
                box-sizing: border-box;
                color: #05386B;
                font-size: 14px;
                letter-spacing: 1px;
            }

            input:focus,
            textarea:focus {
                -moz-box-shadow: none !important;
                -webkit-box-shadow: none !important;
                box-shadow: none !important;
                border: 1px solid #05386B;
                outline-width: 0;
            }

            button:focus {
                -moz-box-shadow: none !important;
                -webkit-box-shadow: none !important;
                box-shadow: none !important;
                outline-width: 0;
            }

            a {
                color: inherit;
                cursor: pointer;
            }

            .btn-blue {
                background-color: #05386B;
                width: 150px;
                color: #fff;
                border-radius: 2px;
            }

            .btn-blue:hover {
                background-color: #05386B;
            }

            .bg-blue {
                color: #EDF5E1;
                background-color: #05386B;
            }
        </style>
        <title>Your Title</title>
    </head>


    <body>

        <div class="container-fluid px-1 px-md-5 px-lg-1 px-xl-5 py-5 mx-auto">
            <div class="card card0 border-0">
                <div class="row d-flex">
                    <div class="col-lg-6">
                        <div class="card1 pb-5">
                            <div class="row">
                                <img src="https://cdn.haitrieu.com/wp-content/uploads/2021/10/Logo-Dai-hoc-FPT.png"
                                     class="logo">
                            </div>
                            <div class="row px-3 justify-content-center mt-4 mb-5 border-line">
                                <img src="image/logo.png" class="image">
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <c:set var="cookie" value="${pageContext.request.cookies}"></c:set>
                            <form action="login" method="post">
                                <div class="card2 card border-0 px-4 py-5">
                                    <div class="row mb-4 px-3">
                                        <h6 class="mb-0 mr-4 mt-2">Log in with</h6>
                                        <a class="google text-center mr-3"
                                           href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile%20openid&redirect_uri=http://localhost:9999/Eztest_isp/login-google&response_type=code&client_id=973064291796-k2ku986aouen6s68a6b0bimviub65ao8.apps.googleusercontent.com&approval_prompt=force">
                                            <div class="fa fa-google"></div>
                                        </a>
                                    </div>
                                    <div class="row px-3 mb-4">
                                        <div class="line"></div>
                                        <small class="or text-center">Or</small>
                                        <div class="line"></div>
                                    </div>
                                    <div class="row px-3">
                                        <label class="mb-1">
                                            <h6 class="mb-0 text-sm">Username</h6>
                                        </label>
                                        <input class="mb-4" type="text" name="username" required value="${cookie.cusername.value}"
                                           placeholder="Enter a valid email address">
                                </div>
                                <div class="row px-3">
                                    <label class="mb-1">
                                        <h6 class="mb-0 text-sm">Password</h6>
                                    </label>
                                    <input type="password" name="password" required placeholder="Enter password">
                                </div>
                                <div class="row px-3 mb-4">
                                    <div class="custom-control custom-checkbox custom-control-inline">
                                        <input id="chk1" type="checkbox" name="rem" ${(cookie.crem!=null?'checked':'')} value="ON" class="custom-control-input">
                                        <label for="chk1" class="custom-control-label text-sm">Remember me</label>
                                    </div>
                                    <a href="forgot-pass" class="ml-auto mb-0 text-sm">Forgot Password?</a>
                                </div>
                                <p style="color: red; font-size: 17px; font-weight: lighter"><b><%=notice%></b></p>
                                <div class="row mb-3 px-3">
                                    <button type="submit" class="btn btn-blue text-center">Login</button>
                                </div>
                            </div>
                        </form>
                    </div>

                </div>
                <div class="bg-blue py-4">
                    <div class="row px-3">
                        <small class="ml-4 ml-sm-5 mb-2"> &copy; 2023. EzTest.</small>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>