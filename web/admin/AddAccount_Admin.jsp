<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="model.Account" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Profile</title>
        <link rel="shortcut icon" type="image/png" href="image/logo.png">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
              rel="stylesheet">
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <style>
            body {
                background: #edf0f0;
            }

            .e-navlist {
                border-radius: 10px;
            }

            .nav-link.active {
                background-color: #05386B;
                color: #fff !important;
            }

            .e-profile {
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                justify-content: center;
            }

            .btn-primary {
                background-color: #05386B;
                border-color: #05386B;
            }

            .btn-primary:hover {
                background-color: #052755;
                border-color: #052755;
            }

            .container {
                left: 70%;
            }

            .form-inline .form-control {
                width: auto;
                /* Đặt chiều rộng tự động để tổng chiều dài bằng 1 dòng */
                margin-right: 5px;
                /* Khoảng cách giữa input và select (có thể điều chỉnh) */
            }
            .col {
                max-width: 100vw; /* Thay đổi giá trị max-width tại đây */
            }

            .container {
                left: 40%; /* Thay đổi giá trị left tại đây */
                transform: translateX(-27%); /* Thêm dòng này để canh giữa container */
            }
        </style>
        <% Account admin=(Account) request.getSession().getAttribute("account"); String
                        role=(String)request.getSession().getAttribute("role"); %>
    </head>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <img src="image/logo.png" style="height: 40px;">&nbsp;&nbsp;
                <a class="navbar-brand mt-2 mt-lg-0" href="home">
                    <h5 class="pt-1">EzTest</h5>
                </a>
                <button class="navbar-toggler" type="button" data-mdb-toggle="collapse"
                        data-mdb-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
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
                            <a class="dropdown-toggle d-flex align-items-center hidden-arrow" href="#"
                               id="navbarDropdownMenuAvatar" role="button" data-mdb-toggle="dropdown"
                               aria-expanded="false">
                                <div style="color:#fff">
                                    <%=admin.getName()%>
                                </div>&nbsp;
                                <img src="https://cdn-icons-png.flaticon.com/512/6596/6596121.png"
                                     class="rounded-circle" height="30"
                                     alt="Black and White Portrait of a Man" loading="lazy" />
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end"
                                aria-labelledby="navbarDropdownMenuAvatar">
                                <li>
                                    <a class="dropdown-item" href="modify?id=<%=admin.getId()%>&mod=3"><i
                                            class="icon-cog blackiconcolor fa fa-user-circle"
                                            aria-hidden="true"></i> My profile</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="change-pass"><i
                                            class="icon-cog blackiconcolor fa fa-key"
                                            aria-hidden="true"></i> Change password</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="logout"><i
                                            class="icon-cog blackiconcolor fa fa-sign-out"
                                            aria-hidden="true"></i> Logout</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container" style="margin-top:5px;">
            <div class="row flex-lg-nowrap">
                <div class="col">
                    <div class="row">
                        <div class="col mb-3" style="margin-left:50%">
                            <div class="card">
                                <div class="card-body">
                                    <div class="e-profile">
                                        <h4 style="color: #05386B"><i class="fa fa-user" aria-hidden="true"></i> User Info </h4>
                                        <div class="tab-content pt-3">

                                            <div class="tab-pane active">
                                                <form action="addaccount" method="post">
                                                    <div class="row">
                                                        <div class="col">
                                                            <div class="row">
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label style="color: #379683"> Full Name</b></label>
                                                                        <input class="form-control"
                                                                               type="text" required
                                                                               name="name"
                                                                               value="${r_name}">
                                                                    </div>
                                                                    <span style="color: red; font-size: small;">${errorMessage1}</span>
                                                                </div>
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label
                                                                            for="source" style="color: #379683"> Address</b></label>
                                                                        <input type="hidden"
                                                                               id="selectedCity"
                                                                               value="${r_address}">
                                                                        <select class="form-control"
                                                                                id="city" name="address"
                                                                                required>
                                                                            <option value="" selected>
                                                                                City
                                                                            </option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label style="color: #379683"> DOB</b></label>
                                                                        <input class="form-control"
                                                                               type="date" value="${r_dob}"
                                                                               name="dob" required
                                                                               min="1952-12-31">
                                                                        <span
                                                                            style="color: red; font-size: small">${errorMessage2}</span>
                                                                    </div>
                                                                </div>
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label style="color: #379683"> Gender</b></label>
                                                                        <select class="form-control"
                                                                                name="gender">
                                                                            <option value="male" <c:if
                                                                                        test="${r_gender eq 'male'}">
                                                                                        selected = "selected"
                                                                                    </c:if>
                                                                                    >Male
                                                                            </option>
                                                                            <option value="female" <c:if
                                                                                        test="${r_gender eq 'female'}">
                                                                                        selected = "selected"
                                                                                    </c:if>
                                                                                    >Female
                                                                            </option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col">
                                                                    <label style="color: #379683"> Phone</b></label>
                                                                    <div class="form-group" style="display: flex; align-items: center;">

                                                                        <div class="form-inline" style="display: flex;">
                                                                            <input name="pre" style="width: 53px;" class="form-control" value="+84" readonly>
                                                                            <input style="width: 75%;" class="form-control" type="text" value="${r_phone}" name="phone" required>
                                                                        </div>
                                                                    </div>
                                                                    <span style="color: red; font-size: small;">${errorMessage3}</span>
                                                                </div>
                                                            </div>
                                                            <br>

                                                            <div class="row">
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label style="color: #379683">Role</label>
                                                                        <select class="form-control"
                                                                                name="role">
                                                                            <option value="3" <c:if
                                                                                        test="${r_role eq '3'}">
                                                                                        selected = "selected"
                                                                                    </c:if>
                                                                                    >Student</option>
                                                                            <option value="2" <c:if
                                                                                        test="${r_role eq '2'}">
                                                                                        selected = "selected"
                                                                                    </c:if>
                                                                                    >Teacher</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label style="color: #379683"> Password</b></label>
                                                                        <input class="form-control"
                                                                               type="text" name="password"
                                                                               value="${r_pass}"
                                                                               readonly ${r_pass eq
                                                                                          password ? 'selected'
                                                                                          : '' }>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col">
                                                                    <label style="color: #379683"> Email</b></label>
                                                                    <div class="form-group" style="display: flex; align-items: center;">

                                                                        <div class="form-inline" style="display: flex; align-items: center;">
                                                                            <input class="form-control" style="width: 420px;" type="text" name="pre_email" value="${r_email}">
                                                                            <select class="form-control" name="subEmail" style="width: 120px;">
                                                                                <option value="@gmail.com" <c:if test="${sub_mail  eq '@gmail.com'}">selected="selected"</c:if>>@gmail.com</option>
                                                                                <option value="@fe.edu.vn" <c:if test="${sub_mail  eq '@fe.edu.vn'}">selected="selected"</c:if>>@fe.edu.vn</option>
                                                                                <option value="@fpt.edu.vn" <c:if test="${sub_mail  eq '@fpt.edu.vn'}">selected="selected"</c:if>>@fpt.edu.vn</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                        <span style="color: red; font-size: small;">${errorMessage4}</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="row">
                                                        <input type="hidden" name="id" value="${id}">
                                                        <div class="col d-flex justify-content-end">
                                                            <button class="btn btn-primary"
                                                                    style="color: #EDF5E1;">Add
                                                                New</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
            <script>
                var citis = document.getElementById("city");
                var selectedCity = document.getElementById("selectedCity").value;
                var Parameter = {
                    url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
                    method: "GET",
                    responseType: "application/json",
                };
                var promise = axios(Parameter);
                promise.then(function (result) {
                    renderCity(result.data);
                });
                function renderCity(data) {
                    for (const x of data) {
                        citis.options[citis.options.length] = new Option(x.Name, x.Name);
                    }
                    if (selectedCity) {
                        citis.value = selectedCity;

                    }
                }
            </script>
        </div>

    </body>

</html>