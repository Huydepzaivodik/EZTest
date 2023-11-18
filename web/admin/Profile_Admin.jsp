<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="model.Account" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link rel="stylesheet" href="lib/css/mdb.min.css" />
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

            .col {
                max-width: 100vw;
            }

            .container {
                left: 40%;
                transform: translateX(-25%);
            }

            .form-inline .form-control {
                width: auto; /* Đặt chiều rộng tự động để tổng chiều dài bằng 1 dòng */
                margin-right: 5px; /* Khoảng cách giữa input và select (có thể điều chỉnh) */
            }

        </style>
        <%
                Account admin = (Account) request.getSession().getAttribute("account");
                String role=(String)request.getSession().getAttribute("role");
        %>
    </head>

    <body>
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
                                <img src="https://cdn-icons-png.flaticon.com/512/6596/6596121.png" class="rounded-circle" height="30" alt="Black and White Portrait of a Man" loading="lazy" />
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
        <div class="container" style="margin-top:35px">
            <div class="row flex-lg-nowrap">
                <div class="col">
                    <div class="row">
                        <div class="col mb-3" style="margin-left:50%">
                            <div class="card" >
                                <div class="card-body">
                                    <div class="e-profile">
                                        <div class="row">
                                            <div class="col-12 col-sm-auto mb-3">
                                                <div class="mx-auto" style="width: 150px;">
                                                    <div class="d-flex justify-content-center align-items-center rounded"
                                                         style="height: 150px;">
                                                        <img src="${img}" style="max-width:150px;max-height: 150px">
                                                    </div>
                                                </div>
                                            </div>
                                            <div
                                                class="col d-flex flex-column flex-sm-row justify-content-between mb-3">
                                                <div class="text-center text-sm-left mb-2 mb-sm-0">
                                                    <h4 class="pt-sm-2 pb-1 mb-0 text-nowrap"><b>${name}</b></h4>
                                                    <p class="mb-0">${email}</p>
                                                    <br>
                                                    <div class="mt-2">
                                                        <form action="upload" method="post" enctype="multipart/form-data" onsubmit="return isSelectedImage();">
                                                            <input type="hidden" name="userId" value="${id}">
                                                            <input type="hidden" name="role" value="${role}">
                                                            <input type="hidden" name="queryString" value="${pageContext.request.queryString}">
                                                            <div class="input-group mt-3">
                                                                <input type="file" id="fileInput" style="width: 102px" name="newAvatar" aria-label="Upload"
                                                                       accept="image/jpg, image/jpeg, image/png">
                                                                <button id="uploadButton" class="btn btn-primary">
                                                                    <i class="fa fa-picture-o" aria-hidden="true"></i>
                                                                    Upload
                                                                </button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                                <div class="text-center text-sm-right">
                                                    <span class="badge badge-secondary">${role}</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-content pt-3">
                                            <div class="tab-pane active">
                                                <form action="modify" method="post" onsubmit="return validate()">
                                                    <div class="row">
                                                        <div class="col">
                                                            <div class="row">
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label><b>Full Name</b></label>
                                                                        <input class="form-control" type="text" name="name" value="${errorMessage1 != null ? param.name : name}">
                                                                        <span style="color: red; font-size: small">${errorMessage1}</span>
                                                                    </div>
                                                                </div>
                                                                <div class="col">   
                                                                    <div class="form-group">
                                                                        <label for="source"><b>Address</b></label>
                                                                        <input type="hidden" id="selectedCity"  value="${address}">
                                                                        <select class="form-control" id="city" name="address" required>
                                                                            <option value="" selected>City</option>           
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label><b>DOB</b></label> 
                                                                        <input class="form-control" type="date" name="dob" value="${errorMessage2 != null ? param.dob : dob}" min="1952-12-31">
                                                                        <span style="color: red; font-size: small">${errorMessage2}</span>
                                                                    </div>
                                                                </div>
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label><b>Gender</b></label>
                                                                        <select class="form-control" name="gender">
                                                                            <option value="male" ${gender == 'male' ? 'selected' : ''} >Male</option>
                                                                            <option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col">
                                                                    <div class="form-group" >
                                                                        <label><b>Phone</b></label>
                                                                        <input style="width: 48%" class="form-control" type="text" name="phone" value="${errorMessage3 != null ? param.phone : phone}">
                                                                        <span style="color: red; font-size: small">${errorMessage3  }</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col">
                                                                    <div class="form-group">
                                                                        <label><b>Email</b></label>
                                                                        <div class="form-inline" style="display:flex;align-items: center">
                                                                            <input class="form-control" style="width: 300px" type="text" id="preEmail" name="preEmail" value="${errorMessage4 != null ? param.preEmail : username}">
                                                                            <br>
                                                                            <select class="form-control" name="subEmail">
                                                                                <option value="@gmail.com" ${domain=='@gmail.com' ? 'selected' : ''}>@gmail.com</option>
                                                                                <option value="@fpt.edu.vn" ${domain=='@fpt.edu.vn' ? 'selected' : ''}>@fpt.edu.vn</option>
                                                                                <option value="@fe.edu.vn" ${domain=='@fe.edu.vn' ? 'selected' : ''}>@fe.edu.vn</option>
                                                                            </select>
                                                                        </div>
                                                                        <span style="color: red;font-size: small">${errorMessage4}</span>
                                                                        <span style="font-size: small; color: red" >${errorMessage5}</span>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <input type="hidden" name="id" value="${id}">
                                                        <div class="col d-flex justify-content-end">
                                                            <button class="btn btn-primary" style="color: #EDF5E1;" type="submit" name="mod"  value="updateAdmin">Save Changes</button>
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
        <script>
            function isSelectedImage() {
                var inputBox = $("#fileInput")[0];

                if (inputBox.files.length == 0) {
                    alert('Please select any image.');
                    return false;
                }

                var selectedFile = inputBox.files[0];
                var allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

                if (!allowedExtensions.exec(selectedFile.name)) {
                    alert('Only accept .jpg, .jpeg or .png files.');
                    return false;
                }

                return true;
            }

        </script>
        <script type="text/javascript" src="lib/js/mdb.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>