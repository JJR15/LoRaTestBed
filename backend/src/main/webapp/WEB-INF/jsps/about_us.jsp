<%--
  Created by IntelliJ IDEA.
  User: JJR
  Date: 2018/7/20
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>

    <!-- start: Meta -->
    <meta charset="utf-8" />
    <title>LoRa TestBed Platform</title>
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
    <link href="<%=basePath%>css/main.css" rel="stylesheet" />
    <!-- end: CSS -->


    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <link id="ie-style" href="css/ie.css" rel="stylesheet">
    <![endif]-->

    <!--[if IE 9]>
    <link id="ie9style" href="css/ie9.css" rel="stylesheet">
    <![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>

<body>
<jsp:include page="navbar.jsp" />

<div class="container-fluid-full">
    <div class="row-fluid">

        <jsp:include page="sidebar.jsp" />

        <div id="content" class="span10" style="min-height: 678px;">
            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-content">
                        <h4 class="mbs">
                            powered by 东配楼</h4>
                        <p><%@ include file="about_node.html" %></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="clearfix"></div>

</div><!--/.fluid-container-->

<!-- start: JavaScript-->
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
<!-- end: JavaScript-->

</div>
</body>
</html>
