<%--
  Created by IntelliJ IDEA.
  User: JJR
  Date: 2018/8/19
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>

    <!-- start: Meta -->
    <meta charset="utf-8" />
    <title>LoRa TestBed Register</title>
    <!-- end: Meta -->

    <!-- start: Mobile Specific -->
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- end: Mobile Specific -->

    <!-- start: CSS -->
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/style.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/style-responsive.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/retina.css" rel="stylesheet" />
    <link href="<%=basePath%>css/toastr.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/main.css" rel="stylesheet" />
    <!-- end: CSS -->


    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <link id="ie-style" href="<%=basePath%>css/ie.css" rel="stylesheet" />
    <![endif]-->

    <!--[if IE 9]>
    <link id="ie9style" href="<%=basePath%>css/ie9.css" rel="stylesheet" />
    <![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>

<div class="navbar">
    <div class="navbar-inner" style="min-height: 60px">
        <div class="container-fluid">
            <div class="row-fluid">
                <a class="brand span2 my-navbar noBg" href="#"><span>LORA TESTBED</span></a>
            </div>
            <div class="nav-no-collapse header-nav">
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a href="?language=en" style="padding: 0; color: white;text-shadow: none"><span> [English Version]</span></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid-full">
    <div class="row-fluid">
        <div id="content" class="span10 full" style="min-height: 678px;">
            <div class="row-fluid">
                <div class="box span6" style="margin-left: 25%;">
                    <div class="box-header" data-original-title="">
                        <h2><i class="icon-edit"></i><span class="break"></span>用户注册</h2>
                    </div>
                    <div class="box-content">
                        <form class="form-horizontal" >
                            <div class="control-group" style="margin-top: 20px;">
                                <label class="control-label" for="email">邮箱: </label>
                                <div class="controls">
                                    <input id="email" type="text"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="username">用户名: </label>
                                <div class="controls">
                                    <input id="username" type="text"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="password">密码: </label>
                                <div class="controls">
                                    <input id="password" type="password"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="confirm-password">确认密码: </label>
                                <div class="controls">
                                    <input id="confirm-password" type="password" oninput="removeError()"/>
                                    <span class="help-inline hidden">密码不一致</span>
                                </div>
                            </div>
                            <div class="m-form-actions">
                                <button type="button" onclick="register()"  class="btn btn-primary">注册</button>
                                <button type="reset" class="btn" style="margin-left: 40px">重置</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>

    </div><!--/fluid-row-->

</div><!--/.fluid-container-->

<script src="<%=basePath%>js/jquery-1.10.2.min.js"></script>
<script src="<%=basePath%>js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>js/jquery.ui.touch-punch.js"></script>
<script src="<%=basePath%>js/modernizr.js"></script>
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/jquery.cookie.js"></script>
<script src="<%=basePath%>js/fullcalendar.min.js"></script>
<script src="<%=basePath%>js/jquery.dataTables.min.js"></script>
<script src="<%=basePath%>js/excanvas.js"></script>
<script src="<%=basePath%>js/jquery.flot.js"></script>
<script src="<%=basePath%>js/jquery.flot.pie.js"></script>
<script src="<%=basePath%>js/jquery.flot.stack.js"></script>
<script src="<%=basePath%>js/jquery.flot.resize.min.js"></script>
<script src="<%=basePath%>js/jquery.flot.time.js"></script>
<script src="<%=basePath%>js/jquery.chosen.min.js"></script>
<script src="<%=basePath%>js/jquery.uniform.min.js"></script>
<script src="<%=basePath%>js/jquery.cleditor.min.js"></script>
<script src="<%=basePath%>js/jquery.noty.js"></script>
<script src="<%=basePath%>js/jquery.elfinder.min.js"></script>
<script src="<%=basePath%>js/jquery.raty.min.js"></script>
<script src="<%=basePath%>js/jquery.iphone.toggle.js"></script>
<script src="<%=basePath%>js/jquery.uploadify-3.1.min.js"></script>
<script src="<%=basePath%>js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>js/jquery.imagesloaded.js"></script>
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script src="<%=basePath%>js/jquery.knob.modified.js"></script>
<script src="<%=basePath%>js/jquery.sparkline.min.js"></script>
<script src="<%=basePath%>js/counter.min.js"></script>
<script src="<%=basePath%>js/raphael.2.1.0.min.js"></script>
<script src="<%=basePath%>js/justgage.1.0.1.min.js"></script>
<script src="<%=basePath%>js/jquery.autosize.min.js"></script>
<script src="<%=basePath%>js/retina.js"></script>
<script src="<%=basePath%>js/jquery.placeholder.min.js"></script>
<script src="<%=basePath%>js/wizard.min.js"></script>
<script src="<%=basePath%>js/core.min.js"></script>
<script src="<%=basePath%>js/charts.min.js"></script>
<script src="<%=basePath%>js/bootstrap-datepicker.js"></script>
<script src="<%=basePath%>js/bootstrap-datepicker.zh-CN.js"></script>
<script src="<%=basePath%>js/toastr.min.js"></script>

<script>
    function register() {
        var username = $("#username").val();
        var password = $("#password").val();
        var cp = $("#confirm-password");
        var confirm_password = cp.val();
        var email = $("#email").val();
        if (username === "" || password === "" || confirm_password === "" ||
            email === "") {
            return;
        }
        if(password!==confirm_password) {
            cp.parent().parent().addClass("error");
            cp.next().removeClass("hidden");
            return;
        }
        var url = "<%=basePath%>user/register";
        var requestData = {
            "username": username,
            "password": password,
            "email": email
        };
        $.ajax({
            url: url,
            type: 'post',
            data: requestData,
            dataType: 'json',
            success: function (data) {
                data = JSON.parse(data);
                if (data === "true") {
                    alert("注册成功!");
                    window.location.href = "<%=basePath%>index";
                } else {
                    alert("用户名已存在，注册失败!");
                }
            },
            error: function () {
                alert("系统繁忙，稍后再试！");
            }
        });
    }
    function removeError() {
        var cp = $("#confirm-password");
        cp.parent().parent().removeClass("error");
        cp.next().addClass("hidden");
    }
</script>
</body>
</html>
