<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta http-equiv="x-ua-compatible" content="ie=edge" />
        <title>Home</title>
        <link rel ="shortcut icon" type = "image/png" href = "image/logo.png" >
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            />
        <link
            rel="stylesheet"
            href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"
            />
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <style>
            .blackiconcolor {
                color: #05386B;
            }

            .btn{
                background-color: #05386B;
            }
            .text{
                color: #05386B;
            }

            .input{
                color: #05386B;
            }

        </style>
    </head>
    <body>
        <%
    Account teacher = (Account) request.getSession().getAttribute("account");
    String role =  (String)request.getSession().getAttribute("role");
        %>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
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

        <div class="container pagination justify-content-center" style="margin-top: 40px">
            <div class="col-md-9 col-sm-8 content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info shadow">
                            <div class="panel-heading">
                                <h3 class="panel-title" style="color: #05386B">
                                    <img width="150" width="150" class="img-circle img-thumbnail" src="<%=teacher.getImg()%>">
                                    <%=teacher.getName()%>
                                </h3>
                            </div>
                            <div class="panel-body">
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <form action="updatePhone" method="post" onsubmit="return validateForm()">
                                            <table class="table">
                                                <tbody>
                                                    <tr>
                                                        <td class="col-md-6"><i class="icon-cog blackiconcolor fa fa-shopping-bag" aria-hidden="true"></i><strong class="text"> Role</strong></td>
                                                        <td class="col-md-4 text-left input"><%=role%></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="col-md-3"><i class="icon-cog blackiconcolor fa fa-home"></i><strong class="text"> Address</strong></td>
                                                        <td class="col-md-9 text-left input"><%=teacher.getAddress()%></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="col-md-3"><i class="icon-cog blackiconcolor fa fa-envelope" aria-hidden="true"></i> <strong class="text"> Email</strong></td>
                                                        <td class="col-md-9 text-left input"><%=teacher.getEmail()%></td>
                                                    </tr>
                                                    <% 
                                                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                                                    java.util.Date dobDate = teacher.getDob();
                                                    String formattedDob = sdf.format(dobDate);
                                                    %>
                                                    <tr>
                                                        <td class="col-md-3"><i class="icon-cog blackiconcolor fa fa-calendar" aria-hidden="true"></i> <strong class="text"> DOB</strong></td>
                                                        <td class="col-md-9 text-left input"><%=formattedDob%></td>
                                                    </tr>
                                                    <tr>

                                                        <% if (teacher.isGender() == true) { %>
                                                        <td class="col-md-3"><i class="icon-cog blackiconcolor fa fa-universal-access" aria-hidden="true"></i> <strong class="text"> Gender</strong></td>
                                                        <td class="col-md-9 text-left input"><%=(teacher.isGender()==true) ? "Male" : "Female" %></td>      
                                                        <% } else if (teacher.isGender() == false) { %>
                                                        <td class="col-md-3"><i class="icon-cog blackiconcolor fa fa-universal-access" aria-hidden="true"></i> <strong class="text"> Gender</strong></td>
                                                        <td class="col-md-9 text-left input"><%=(teacher.isGender()==true) ? "Male" : "Female" %></td>
                                                        <% } %>

                                                    </tr>
                                                    <tr>
                                                <div class="form-group mx-sm-3 mb-2">
                                                    <td class="col-md-3"><i class="icon-cog blackiconcolor fa fa-phone-square" aria-hidden="true"></i> <strong class="text"> Phone</strong></td>
                                                </div>
                                                <td class="col-md-9 text-left">
                                                    <input type="hidden" name="id" value="<%=teacher.getId()%>">
                                                    <input type="hidden" name="role" value="<%=role%>">
                                                    <div class="input-group">
                                                        <input type="text" name="phone" class="form-control input" value="<%=teacher.getPhone()%>" id="phone" placeholder="Phone Number">
                                                        <div class="input-group-append">
                                                            <button class="btn text" style="color: #EDF5E1" onclick="alertUpdate()">Update</button>
                                                        </div>
                                                    </div>
                                                    <span style="font-size: medium; color: red" id="phoneUpdate"></span>
                                                </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function validateForm() {
                let passed = true;
                const MUST_NOT_EMPTY = "This field must not be empty";
                //Phone: not empty and not be a string
                const PHONE_INVALID = "Phone must be a valid number";
                if (document.getElementById('phone').value === "") {
                    passed = false;
                    document.getElementById('phoneUpdate').innerHTML = MUST_NOT_EMPTY;
                } else {
                    if (!/^0\d{9}$/.test(document.getElementById('phone').value)) {
                        passed = false;
                        document.getElementById('phoneUpdate').innerHTML = PHONE_INVALID;
                    } else {
                        document.getElementById('phoneUpdate').innerHTML = '';
                    }
                }
                return passed;
            }

        </script>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script type="text/javascript"></script>
    </body>
</html>
