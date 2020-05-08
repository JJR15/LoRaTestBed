<%--
  Created by IntelliJ IDEA.
  User: JJR
  Date: 2018/7/20
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="<%=basePath%>css/bootstrap-datepicker.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/style.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/style-responsive.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/retina.css" rel="stylesheet" />
    <link href="<%=basePath%>css/toastr.min.css" rel="stylesheet" />
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
                        <h2><i class="icon-map-marker"></i><span class="break"></span>Node Informations</h2>
                    </div>
                    <div class="box-content">
                        <div class="span12 height30">
                            <div class="span6"><strong>Selected Nodes:</strong><br /><span class="node-ids">none</span>&nbsp;</div>
                            <div class="span6">
                                <button class="btn btn-success orderbtn" onclick="orderNodes()">
                                    Order Selected
                                </button>
                            </div>
                        </div>
                        <table id="node-info-datatable" class="table table-striped table-bordered bootstrap-datatable">
                            <thead>
                            <tr class="center">
                                <th><input type="checkbox" onclick="changePageSelect()" /></th>
                                <th> Node ID</th>
                                <th> MAC</th>
                                <th> Node State</th>
                                <th> Position</th>
                                <th> Ordering Details</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${node_infos}" var= "node_info" varStatus="node_status">
                                <tr>
                                    <td><input type="checkbox" onclick="changeNodeSelect(${node_info.id})"/></td>
                                    <td>${node_info.id}</td>
                                    <td>${node_info.MAC}</td>
                                    <td>
                                        <c:if test="${node_info.state == 0}">
                                            <span class="label">Offline</span>
                                        </c:if>
                                        <c:if test="${node_info.state == 1}">
                                            <span class="label label-info">Usable</span>
                                        </c:if>
                                        <c:if test="${node_info.state == 2}">
                                            <span class="label label-success">Running</span>
                                        </c:if>
                                        <c:if test="${node_info.state == 3}">
                                            <span class="label label-important">Stopped</span>
                                        </c:if>
                                        <c:if test="${node_info.state == 4}">
                                            <span class="label label-warning">Burning</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <button class="btn btn-info" onclick="posBtn(${node_info.id},${node_info.longitude},${node_info.latitude})">
                                            <i class="icon-info-sign"></i>
                                            View
                                        </button>
                                    </td>
                                    <td>
                                        <button class="btn btn-info" onclick="orderInfoBtn(${node_info.id})">
                                            <i class="icon-info-sign"></i>
                                            View
                                        </button>
                                    </td>
                                    <td>
                                        <button class="btn btn-success" onclick="orderBtn(${node_info.id})">
                                            Order
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal hide fade" id="nodeOrderDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Order Node</h3>
        </div>
        <div class="modal-body">
            <p>Node ID：<span class="node-id node-ids">1</span></p>
            <div class="center">
                Start Time：
                <input type="text" class="input-small datepicker after-now" id="date01" value="2018-01-01"  readonly="readonly"/>
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="23" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> h
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="59" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> m
            </div>

            <div class="center">
                End Time：
                <input type="text" class="input-small datepicker after-now" id="date02" value="2018-01-01"  readonly="readonly"/>
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="23" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> h
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="59" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> m
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitOrder()">OK</a>
            <a href="#" class="btn" data-dismiss="modal">Cancel</a>
        </div>
    </div>

    <div class="modal hide fade" id="nodeOrderInfoDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Node <span class="node-id"></span>Ordering Details</h3>
        </div>
        <div class="modal-body" style="max-height: none">
            <table id="node-order-info-datatable" class="table table-striped table-bordered bootstrap-datatable datatable2">
                <thead>
                <tr class="center">
                    <th> User</th>
                    <th> Start Time</th>
                    <th> End Time</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="i" begin="0" end="100" step="1">
                    <tr>
                        <td>JJR</td>
                        <td>2018/01/01 8:00</td>
                        <td>2018/02/01 8:00</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </div>
        <div class="modal-footer center">
            <a href="#" class="btn btn-primary" data-dismiss="modal">OK</a>
        </div>
    </div>

    <div class="modal hide fade" id="nodePositionDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Node Position</h3>
        </div>
        <div class="modal-body">
            <div id="map" style="height: 350px; width: 100%"></div>
            <p style="line-height: 25px">
                Node ID:<span class="node-id">01</span> <br/>
                longitude:<span id="longitude">116.339041</span>,  &nbsp; latitude:<span id="latitude">40.007689</span>
            </p>

        </div>
        <div class="modal-footer center">
            <a href="#" class="btn btn-primary" data-dismiss="modal">OK</a>
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
<script src="<%=basePath%>js/main_en.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=leHYvtfyeCw2b0Cmi1sudiiju8kUzrWj"></script>

<script>
    var map;
    var icon;
    var selected_nodes = [];
    var select_node = null;
    var nodes_flag = false;
    $(document).ready(function () {
        map = new BMap.Map("map"); // 创建地图实例
        icon = new BMap.Icon("<%=basePath%>img/red.png", new BMap.Size(30, 30));
        //创建点坐标
        var centerPoint = new BMap.Point(116.339041, 40.007689); //创建点坐标
        //地图初始化
        map.centerAndZoom(centerPoint, 18); // 初始化地图，设置中心点坐标和地图级别
        map.enableScrollWheelZoom(true); //允许缩放

        var scalePos = {anchor: BMAP_ANCHOR_TOP_LEFT};  //设置偏移
        map.addControl(new BMap.NavigationControl(scalePos));  //两个控件
        map.addControl(new BMap.ScaleControl(scalePos));

        var setting = JSON.parse(JSON.stringify($.dataTableSettings));//避免改变全局设置
        setting.aoColumnDefs.push({
            aTargets:[0,4,5,6],
            bSearchable:false
        });
        $("#node-info-datatable").dataTable(setting);

        toastr.options = $.toastrSetting;
    });
    function orderBtn(id){
        nodes_flag = false;
        select_node = id;
        $(".node-id").text(id);
        $("#nodeOrderDialog").modal("show");
    }

    function orderNodes() {
        nodes_flag = true;
        if(selected_nodes.length === 0){
            return;
        }
        $(".node-ids").text(selected_nodes.join(" "));
        $("#nodeOrderDialog").modal("show");
    }

    function orderInfoBtn(id){
        var table = $("#node-order-info-datatable").DataTable();
        $(".node-id").text(id);
        table.fnClearTable();

        $.get("<%=basePath%>api/node/orderinfo",{id:id},function (response) {
            console.log(response);
            var res = JSON.parse(response);
            console.log(res);
            for(var i = 0; i < res.length; i++){
                table.fnAddData([res[i].username,new Date(res[i].starttime).format("yyyy/MM/dd hh:mm"),
                    new Date(res[i].endtime).format("yyyy/MM/dd hh:mm")]);
            }
        });

        $("#nodeOrderInfoDialog").modal("show");
    }

    function posBtn(id, longitude, latitude){
        $(".node-id").text(id);

        $("#longitude").text(longitude);
        $("#latitude").text(latitude);

        map.clearOverlays();
        var point = new BMap.Point(longitude, latitude);
        var point2 = new BMap.Point(longitude-0.002381, latitude+0.001209);
        var marker = new BMap.Marker(point);
        marker.setIcon(icon);
        map.addOverlay(marker);
        map.centerAndZoom(point2, 18);
        $("#nodePositionDialog").modal("show");
    }

    function changePageSelect() {
        var node_manage_table = $("#node-info-datatable").DataTable();
        var all_check = node_manage_table.children()[0].children[0].children[0].children[0];
        $("input:checkbox").each(function () {
            if(this.checked !== all_check.checked){
                this.click();
            }
        });
    }
    function changeNodeSelect(id) {
        var index = selected_nodes.indexOf(id);
        if(index >= 0) {
            selected_nodes.splice(index, 1);
        }
        else {
            selected_nodes.push(parseInt(id));
        }
        selected_nodes.sort($.compare);
        if(selected_nodes.length === 0){
            $(".node-ids").text("none");
        }
        else {
            $(".node-ids").text(selected_nodes.join(" "));
        }
    }
    function submitOrder() {
        var st = new Date($("#date01").val()+" "+$("#date01").next().val()+":"+$("#date01").next().next().val());
        var ed = new Date($("#date02").val()+" "+$("#date02").next().val()+":"+$("#date02").next().next().val());
        var nodes = [];
        if(nodes_flag){
            nodes = selected_nodes;
        }
        else {
            nodes.push(select_node);
        }

        $("#nodeOrderDialog").modal("hide");
        $.post("<%=basePath%>api/node/order",{"ids":nodes,"starttime":st,"endtime":ed},function (response) {
            if(response==="success"){
                toastr.success("order submit successfully");
            }
            else toastr.error(response);
        });
    }
</script>

<!-- end: JavaScript-->
</body>
</html>
