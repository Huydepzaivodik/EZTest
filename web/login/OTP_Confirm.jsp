
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Quiz Practice</title>
        <link rel="apple-touch-icon" sizes="76x76" href="lib/img/apple-icon.png">
        <link rel="icon" type="image/png" href="lib/img/favicon.png">       
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
        <link href="lib/css/nucleo-icons.css" rel="stylesheet" />
        <link href="lib/css/nucleo-svg.css" rel="stylesheet" />        
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
        <link id="pagestyle" href="lib/css/material-dashboard.css?v=3.0.5" rel="stylesheet" />
        <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
        <script defer data-site="YOUR_DOMAIN_HERE" src="https://api.nepcha.com/js/nepcha-analytics.js"></script>
    </head>
    <body class="">
        <main class="main-content  mt-0">
            <section>
                <div class="page-header min-vh-100">
                    <div class="container">
                        <div class="row">
                            <div class="col-6 d-lg-flex d-none h-100 my-auto pe-0 position-absolute top-0 start-0 text-center justify-content-center flex-column">
                                <div class="position-relative bg-gradient-primary h-100 m-3 px-7 border-radius-lg d-flex flex-column justify-content-center" style="background-image: url('image/quiz5.jpg'); background-size: cover;">
                                </div>
                            </div>
                            <div class="col-xl-4 col-lg-5 col-md-7 d-flex flex-column ms-auto me-auto ms-lg-auto me-lg-5">
                                <div class="card card-plain">
                                    <div class="card-header">
                                        <h4 class="font-weight-bolder">OTP Confirmation</h4>
                                        <p class="mb-0">Enter your OTP to continue reset password</p>
                                    </div>
                                    <div class="card-body">
                                        <form role="form" method="post" action="otp-confirm">
                                            <div class="input-group input-group-outline mb-3">
                                                <input name="otp" type="text" class="form-control" placeholder="OTP">
                                            </div>
                                            <div style="color: red">
                                                ${requestScope.msg}
                                            </div>                                           
                                            <div class="text-center">
                                                <button type="submit" class="btn btn-lg bg-gradient-primary btn-lg w-100 mt-4 mb-0">Confirm OTP</button>
                                            </div>
                                        </form>
                                    </div>                              
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <!--   Core JS Files   -->
        <script src="lib/js/core/popper.min.js"></script>
        <script src="lib/js/core/bootstrap.min.js"></script>
        <script src="lib/js/plugins/perfect-scrollbar.min.js"></script>
        <script src="lib/js/plugins/smooth-scrollbar.min.js"></script>
        <script>
            var win = navigator.platform.indexOf('Win') > -1;
            if (win && document.querySelector('#sidenav-scrollbar')) {
                var options = {
                    damping: '0.5'
                }
                Scrollbar.init(document.querySelector('#sidenav-scrollbar'), options);
            }
        </script>
        <script async defer src="https://buttons.github.io/buttons.js"></script>
        <script src="lib/js/material-dashboard.min.js?v=3.0.5"></script>
    </body>

</html>