<%--
  Created by IntelliJ IDEA.
  User: JJR
  Date: 2018/7/20
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String wsPath = "ws://" + request.getServerName() + ":" + 80 + path + "/";
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
    <link href="<%=basePath%>css/bootstrap-datepicker.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/style.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/style-responsive.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/retina.css" rel="stylesheet" />
    <link href="<%=basePath%>css/toastr.min.css" rel="stylesheet" />
    <!--<link href="<%=basePath%>css/fileinput.min.css" rel="stylesheet" />-->
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
<jsp:include page="navbar_en.jsp" />

<div class="container-fluid-full">
    <div class="row-fluid">

        <jsp:include page="sidebar_en.jsp" />

        <div id="content" class="span10" style="min-height: 678px;">
            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header" data-original-title="">
                        <h2><i class="icon-map-marker"></i><span class="break"></span>Gateway Manage</h2>
                    </div>
                    <div class="box-content">
                        <div class="span12 height30">
                            <div class="span6"><strong>Selected Gateway:</strong><br /><span class="node-ids">none</span>&nbsp;</div>
                        </div>
                        <table id="node-manage-datatable" class="table table-striped table-bordered bootstrap-datatable gatewaydatatable">
                            <thead>
                            <tr class="center">
                                <th><input type="checkbox" onclick="changePageSelect()"/></th>
                                <th> Gateway ID</th>
                                <th> Channel</th>
                                <th> Gateway Status</th>
                                <th> Operations</th>
                            </tr>
                            </thead>
                            <tbody id="node_manage">
                                <tr>
                                    <td><input type="checkbox" onclick="changeNodeSelect(${node_manage_info.nodeInfo.id})"/></td>
                                    <td>1</td>
                                    <td><select class="selectpicker">
                                        <option value="1">470.3-471.7MHz</option>
                                        <option value="2">471.9-473.3MHz</option>
                                        <option value="3">473.5-474.9MHz</option>
                                        <option value="4">475.1-476.5MHz</option>
                                        <option value="5">476.7-478.1MHz</option>
                                        <option value="6">478.3-479.7MHz</option>
                                        <option value="7">479.9-481.3MHz</option>
                                        <option value="8">481.5-482.9MHz</option>
                                        <option value="9">483.1-484.5MHz</option>
                                        <option value="10">484.7-486.1MHz</option>
                                        <option value="11">486.3-487.7MHz</option>
                                        <option value="12">487.9-489.3MHz</option>
                                    </select>
                                    </td>
                                    <td><span class="label label-importan" id="spe1">stopped</span></td>
                                    <td>
                                        <button class="btn btn-success" onclick="changeState(1,1)">Start</button>
                                        <button class="btn btn-warning" onclick="changeState(1,0)">Stop</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeNodeSelect(${node_manage_info.nodeInfo.id})"/></td>
                                    <td>2</td>
                                    <td><select class="selectpicker">
                                        <option value="1">470.3-471.7MHz</option>
                                        <option value="2">471.9-473.3MHz</option>
                                        <option value="3">473.5-474.9MHz</option>
                                        <option value="4">475.1-476.5MHz</option>
                                        <option value="5">476.7-478.1MHz</option>
                                        <option value="6">478.3-479.7MHz</option>
                                        <option value="7">479.9-481.3MHz</option>
                                        <option value="8">481.5-482.9MHz</option>
                                        <option value="9">483.1-484.5MHz</option>
                                        <option value="10">484.7-486.1MHz</option>
                                        <option value="11">486.3-487.7MHz</option>
                                        <option value="12">487.9-489.3MHz</option>
                                    </select>
                                    </td>
                                    <td><span class="label label-importan" id="spe2">stopped</span></td>
                                    <td>
                                        <button class="btn btn-success" onclick="changeState(2,1)">Start</button>
                                        <button class="btn btn-warning" onclick="changeState(2,0)">Stop</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeNodeSelect(${node_manage_info.nodeInfo.id})"/></td>
                                    <td>3</td>
                                    <td><select class="selectpicker">
                                        <option value="1">470.3-471.7MHz</option>
                                        <option value="2">471.9-473.3MHz</option>
                                        <option value="3">473.5-474.9MHz</option>
                                        <option value="4">475.1-476.5MHz</option>
                                        <option value="5">476.7-478.1MHz</option>
                                        <option value="6">478.3-479.7MHz</option>
                                        <option value="7">479.9-481.3MHz</option>
                                        <option value="8">481.5-482.9MHz</option>
                                        <option value="9">483.1-484.5MHz</option>
                                        <option value="10">484.7-486.1MHz</option>
                                        <option value="11">486.3-487.7MHz</option>
                                        <option value="12">487.9-489.3MHz</option>
                                    </select>
                                    </td>
                                    <td><span class="label label-importan" id="spe3">stopped</span></td>
                                    <td>
                                        <button class="btn btn-success" onclick="changeState(3,1)">Start</button>
                                        <button class="btn btn-warning" onclick="changeState(3,0)">Stop</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
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
<script src="<%=basePath%>js/bootstrap-datepicker.js"></script>
<script src="<%=basePath%>js/toastr.min.js"></script>
<script src="<%=basePath%>js/sockjs.min.js"></script>
<script src="<%=basePath%>js/main_en.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=leHYvtfyeCw2b0Cmi1sudiiju8kUzrWj"></script>
<!--<script src="<%=basePath%>js/custom.min.js"></script>-->
<!-- end: JavaScript-->
<script>
    function changeState(n,s){
        nm = "#spe" + n;
        //console.log(nm);
        if(s == 1){
            $(nm).text("running");
            $(nm).addClass("label-success");
            $(nm).removeClass("label-important");
        }
        else{
            $(nm).text("stop");
            $(nm).addClass("label-important");
            $(nm).removeClass("label-success");
        }
    }
</script>

</body>
</html>
