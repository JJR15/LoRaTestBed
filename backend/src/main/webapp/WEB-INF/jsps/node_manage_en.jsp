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
                        <h2><i class="icon-map-marker"></i><span class="break"></span>Node Manage</h2>
                    </div>
                    <div class="box-content">
                        <div class="span12 height30">
                            <div class="span6"><strong>Selected Nodes:</strong><br /><span class="node-ids">none</span>&nbsp;</div>
                            <div class="span6">
                                <ul class="operate-list-en">
                                    <li><strong>Batch Operations:</strong></li>
                                    <li>
                                        <button class="btn btn-danger" onclick="burnAllBtn()">
                                            <i class="icon-pencil"></i>
                                            Burn
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-success" onclick="startAllBtn()">
                                            <i class="icon-play"></i>
                                            Start
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-danger" onclick="stopAllBtn()">
                                            <i class="icon-stop"></i>
                                            Stop
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-danger" onclick="restartAllBtn()">
                                            <i class="icon-refresh"></i>
                                            Restart
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-danger" onclick="stopUseAllBtn()">
                                            <i class="icon-stop"></i>
                                            End Use
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-danger" onclick="downloadAllBtn()">
                                            <i class="icon-stop"></i>
                                            Download
                                        </button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <table id="node-manage-datatable" class="table table-striped table-bordered bootstrap-datatable datatable">
                            <thead>
                            <tr class="center">
                                <th><input type="checkbox" onclick="changePageSelect()"/></th>
                                <th> Node ID</th>
                                <th> EndTime</th>
                                <th> Node Status</th>
                                <th> Operations</th>
                            </tr>
                            </thead>
                            <tbody id="node_manage">
                            <c:forEach items="${node_manage_infos}" var = "node_manage_info" varStatus="status">
                                <tr>
                                    <td><input type="checkbox" onclick="changeNodeSelect(${node_manage_info.nodeInfo.id})"/></td>
                                    <td>${node_manage_info.nodeInfo.id}</td>
                                    <td><fmt:formatDate value="${node_manage_info.endtime}" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
                                    <td>
                                        <c:if test="${node_manage_info.nodeInfo.state == 0}">
                                            <span class="label">Offline</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 1}">
                                            <span class="label label-info">Usable</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 2}">
                                            <span class="label label-success">Running</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 3}">
                                            <span class="label label-important">Stopped</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 4}">
                                            <span class="label label-warning">Burning</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <!--,-->
                                        <button class="btn btn-success" onclick="opsBtn(${status.index},${node_manage_info.nodeInfo.id},'${node_manage_info.nodeInfo.MAC}',${node_manage_info.nodeInfo.longitude},${node_manage_info.nodeInfo.latitude},'${node_manage_info.starttime.toString()}','${node_manage_info.endtime.toString()}')">
                                            Expand
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

    <div class="modal hide fade" id="nodeBurnDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Burn Nodes</h3>
        </div>
        <div class="modal-body">
            Selected Nodes：<span class="node-id node-ids"></span><br />
            <div style="margin-top: 20px">
                <p> Upload .bin file</p>
                <form id="file"> <input id="fileUpload" type="file" accept=".bin"> </form>
            </div>
            <div>
                <p>Do you want to auto-change dev-eui for the bin file?</p>
                <button type="button" class="btn btn-success" onclick="changeBinFile()">change</button>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitBurn()">OK</a>
            <a href="#" class="btn" data-dismiss="modal">Cancel</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeStartDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Run Nodes</h3>
        </div>
        <div class="modal-body">
            Are you sure to run node <span class="node-id node-ids"></span>?
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitStart()">OK</a>
            <a href="#" class="btn" data-dismiss="modal">Cancel</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeStopDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Stop Nodes</h3>
        </div>
        <div class="modal-body">
            Are you sure to stop node <span class="node-id node-ids"></span>?
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitStop()">OK</a>
            <a href="#" class="btn" data-dismiss="modal">Cancel</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeRestartDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Restart Nodes</h3>
        </div>
        <div class="modal-body">
            Are you sure to restart node <span class="node-id node-ids"></span>?
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitRestart()">OK</a>
            <a href="#" class="btn" data-dismiss="modal">Cancel</a>
        </div>
    </div>

    <div class="modal hide fade" id="nodeLogDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Log of Node <span class="node-id"></span></h3>
        </div>
        <textarea id="real-time-log-textarea"></textarea>

        <div class="modal-body">
            <div class="dialog-btn"><button id="download-log-btn" class="btn btn-primary" onclick="searchLogBtn()"> Download</button></div>
        </div>
    </div>
    <!--
    <div class="modal hide fade" id="nodeDataDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Node <span class="node-id"></span> Outputs</h3>
        </div>
        <div class="modal-body">
            <div>
                <div class="dialog-div">Download data</div>
                <div class="dialog-div" style="text-align: right"><a href="#" id="data-url" target="_blank">View real-time outputs</a></div>
            </div>
            <div class="center">
                Start Time:
                <input type="text" class="input-small datepicker before-now" id="data-date01" value="2018-01-01"  readonly="readonly"/>
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
                End Time:
                <input type="text" class="input-small datepicker before-now" id="data-date02" value="2018-01-01"  readonly="readonly"/>
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
            <div class="dialog-btn"><button id="download-data-btn" class="btn btn-primary" onclick="searchDataBtn()"> 查询</button></div>
        </div> -->

        <!--<div class="modal-footer center">
            <a href="#" class="btn btn-primary" data-dismiss="modal">确认</a>
        </div>-->
    <!-- </div> -->

    <div class="modal hide fade" id="nodeDetailDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Detail of Node <span class="node-id"></span></h3>
        </div>
        <div class="modal-body">
            <div id="map" style="height: 350px; width: 100%"></div>
            <p style="line-height: 25px">
                Node ID:<span class="node-id">01</span> &nbsp;
                MAC:<span id="mac"></span> &nbsp;
                longitude:<span id="longitude"></span>,  &nbsp; latitude:<span id="latitude"></span><br/>
                available time:<span id="starttime"></span> to <span id="endtime"></span>
            </p>
        </div>
        <div class="modal-footer center">
            <a href="#" class="btn btn-primary" data-dismiss="modal">OK</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeStopUseDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>End Use of Nodes</h3>
        </div>
        <div class="modal-body">
            Are you sure to stop use node <span class="node-id node-ids"></span>?
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitStopUse()">OK</a>
            <a href="#" class="btn" data-dismiss="modal">Cancel</a>
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
    var map;
    var icon;
    var selected_nodes=[];
    var select_node = null;
    var nodes_flag = false;
    var socket = null;
    var last_selected_node = -1;
    var isChange = 0;
    var s_real_time_log = new Array();
    for(var i=0;i<50;i++){
        s_real_time_log[i] = "";
    }

    $(document).ready(function(){
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

        toastr.options = $.toastrSetting;

        if (window['WebSocket'])
            socket = new WebSocket('<%=wsPath%>websocket');
        else
            socket = new SockJS('<%=basePath%>socketjs');
        socket.onopen = function (event) {
            socket.send("nodes message");
        };
        socket.onclose = function (event) { console.log(event); };
        socket.onerror = function (event) { console.log(event); };
        socket.onmessage = function (event) {
            //$("#log").append("<p>"+event.data+"</p>");
            var message = event.data;
            var path, str_list, node_id;
            //console.log(event);
            if(message.startsWith("log file ")){
                path = message.substring(9);
                openDownloadDialog("<%=basePath%>file/download?path="+encodeURIComponent(path));
            }
            else if(message.startsWith("data file ")){
                path = message.substring(10);
                openDownloadDialog("<%=basePath%>file/download?path="+encodeURIComponent(path));
            }
            else if(message.startsWith("start message ")){
                str_list = m_split(message, " ", 5);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                if(str_list[3]==="succ"){
                    toastr.success("Start node "+ node_id +" successfully.");
                }
                else{
                    toastr.success("Failed to start node " + node_id + ":"+str_list[4]);
                }
            }
            else if(message.startsWith("stop message ")){
                str_list = m_split(message, " ", 5);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                if(str_list[3]==="succ"){
                    toastr.success("Stop node "+ node_id +"successfully.");
                }
                else{
                    toastr.error("Failed to stop node " + node_id + ":"+str_list[4]);
                }
            }
            else if(message.startsWith("restart message ")){
                str_list = m_split(message, " ", 5);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                if(str_list[3]==="succ"){
                    toastr.success("Restart node "+ node_id +"successfully.");
                }
                else{
                    toastr.error("Failed to restart node " + node_id + ":"+str_list[4]);
                }
            }
            else if(message.startsWith("burn message ")){
                str_list = m_split(message, " ", 5);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                if(str_list[3]==="succ"){
                    toastr.success("Burn node "+node_id+"successfully.");
                }
                else{
                    toastr.error("Failed to burn node"+node_id+":"+str_list[4]);
                }
            }
            else if(message.startsWith("burn progress ")){
                str_list = m_split(message, " ", 4);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                setNodeStatus(node_id, 4 ,str_list[3]);
            }
            else if(message.startsWith("status change ")){
                str_list = m_split(message, " ", 4);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                var status = parseInt(str_list[3]);
                if(isNaN(status)) return;
                setNodeStatus(node_id, status)
            }
            else if(message.startsWith("fail-message-data ")){
                str_list = m_split(message, " ", 3);
                toastr.error("failed to get data file of node " + str_list[1] + ":" + str_list[2]);
            }
            else if(message.startsWith("nodedata ")){
                str_list = m_split(message, " ", 3);

                var obj = document.getElementById("real-time-log-textarea");
                var isBottom = false
                //console.log(obj.scrollHeight);
                //console.log(obj.scrollTop);
                //console.log(obj.style.height);
                var h = $("#real-time-log-textarea").height();
                //console.log(h);
                if((obj.scrollHeight ) <= (h+ obj.scrollTop+ 10))
                    isBottom = true;
                var node_id = parseInt(str_list[1]);
                s_real_time_log[node_id] = s_real_time_log[node_id] + str_list[2];
                $("#real-time-log-textarea").val(s_real_time_log[select_node]);
                if(isBottom)
                    obj.scrollTop = obj.scrollHeight;
            }
        };
    });
    function changePageSelect() {
        var node_manage_table = $("#node-manage-datatable").DataTable();
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
            $(".node-ids").text("无");
        }
        else {
            $(".node-ids").text(selected_nodes.join(" "));
        }
    }
    function opsBtn(t,id,mac,longitude,latitude,starttime,endtime) {
        var text = "<tr id=\"opBtns\"> <td colspan=\"5\"> <button class=\"btn btn-danger\" onclick=\"burnBtn(" +
            id +
            ")\"> <i class=\"icon-pencil\"></i> Burn </button> <button class=\"btn btn-success\" onclick=\"startBtn(" +
            id +
            ")\"> <i class=\"icon-play\"></i> Start </button> <button class=\"btn btn-danger\" onclick=\"stopBtn(" +
            id +
            ")\"> <i class=\"icon-stop\"></i> Stop </button> <button class=\"btn btn-danger\" onclick=\"restartBtn(" +
            id +
            ")\"> <i class=\"icon-refresh\"></i> Restart </button> <button class=\"btn btn-info\" onclick=\"dataBtn(" +
            id +
            ")\"> <i class=\"icon-download\"></i> Download Data </button> <button class=\"btn btn-success\" onclick=\"detailBtn(" +
            id +",'"+mac+"',"+longitude+","+latitude+",'"+starttime+"','"+endtime+"'"+
            ")\"> <i class=\"icon-info-sign\"></i> Details </button> <button class=\"btn btn-danger\" onclick=\"stopUseBtn(" +
            id +
            ")\"> <i class=\"icon-stop\"></i> Stop Use </button> <button class=\"btn btn-info\" onclick=\" logBtn(" +
            id +
            ")\"> <i class=\"icon-info-sign\"></i> Log </button> </td> </tr> ";
        $("#opBtns").remove();
        console.log(last_selected_node);
        if(last_selected_node != id)
        {
            $(text).insertAfter($("#node-manage-datatable").DataTable().fnGetNodes(t));
            last_selected_node = id;
        }
        else
            last_selected_node = -1;

    }
    function startBtn(id) {
        nodes_flag = false;
        select_node = id;
        $(".node-id").text(id);
        $("#nodeStartDialog").modal("show");
    }
    function stopBtn(id) {
        nodes_flag = false;
        select_node = id;
        $(".node-id").text(id);
        $("#nodeStopDialog").modal("show");
    }
    function restartBtn(id) {
        nodes_flag = false;
        select_node = id;
        $(".node-id").text(id);
        $("#nodeRestartDialog").modal("show");
    }
    function startAllBtn() {
        nodes_flag = true;
        if(selected_nodes.length === 0){
            return;
        }
        $(".node-ids").text(selected_nodes.join(" "));
        $("#nodeStartDialog").modal("show");
    }
    function stopAllBtn() {
        nodes_flag = true;
        if(selected_nodes.length === 0){
            return;
        }
        $(".node-ids").text(selected_nodes.join(" "));
        $("#nodeStopDialog").modal("show");
    }
    function restartAllBtn() {
        nodes_flag = true;



        if(selected_nodes.length === 0){
            return;
        }
        $(".node-ids").text(selected_nodes.join(" "));
        $("#nodeRestartDialog").modal("show");
    }
    function logBtn(id) {
        select_node = id;
        $(".node-id").text(id);
        $("#log-url").attr('href','<%=basePath%>node/log?node='+id);
        $("#real-time-log-textarea").attr("style","overflow-y:scroll;height:400px;width:500px;resize:none;max-height:400px; max-width: 500px;");
        $("#nodeLogDialog").modal("show");
    }
    function dataBtn(id) {
        select_node = id;
        /*$(".node-id").text(id);
        $("#data-url").attr('href','<%=basePath%>node/data?node='+id);
        $("#nodeDataDialog").modal("show");*/
        searchDataBtn(id);
    }
    function detailBtn(id,mac,longitude, latitude,starttime,endtime) {
        $(".node-id").text(id);
        $("#mac").text(mac);
        $("#longitude").text(longitude);
        $("#latitude").text(latitude);
        $("#starttime").text(new Date(starttime).format("yyyy/MM/dd hh:mm"));
        $("#endtime").text(new Date(endtime).format("yyyy/MM/dd hh:mm"));

        map.clearOverlays();
        var point = new BMap.Point(longitude, latitude);
        var point2 = new BMap.Point(longitude-0.002381, latitude+0.001209);
        var marker = new BMap.Marker(point);
        marker.setIcon(icon);
        map.addOverlay(marker);
        map.centerAndZoom(point2, 18);

        $("#nodeDetailDialog").modal("show");
    }
    function stopUseBtn(id) {
        nodes_flag = false;
        select_node = id;
        $(".node-id").text(id);
        $("#nodeStopUseDialog").modal("show");
    }

    //下面是全体功能
    function stopUseAllBtn() {
        nodes_flag = true;
        if(selected_nodes.length === 0){
            return;
        }
        $(".node-ids").text(selected_nodes.join(" "));
        $("#nodeStopUseDialog").modal("show");
    }

    function burnBtn(id) {
        nodes_flag = false;
        select_node = id;
        $(".node-id").text(id);
        $("#nodeBurnDialog").modal("show");
    }

    function burnAllBtn() {
        nodes_flag = true;
        isChange = 0;
        if(selected_nodes.length === 0){
            return;
        }
        $(".node-ids").text(selected_nodes.join(" "));
        $("#nodeBurnDialog").modal("show");
    }

    function downloadAllBtn(){
        nodes_flag = true;
        if(selected_nodes.length === 0){
            return;
        }

        var str1 = "1980/01/01 0:0";
        var str2 = "2100/01/01 0:0";
        var st = new Date(str1);
        var ed = new Date(str2);

        $.post("<%=basePath%>api/node/datas",{"ids":selected_nodes, "starttime":st, "endtime":ed},function (response) {
            if(response!=="success"){
                toastr.error(response);
            }
        });
    }

    function changeBinFile(){
        isChange = 1;
    }

    function submitBurn() {
        var nodes = [];
        if(nodes_flag){
            nodes = selected_nodes;
        }
        else {
            nodes.push(select_node);
        }

        var file = $("#fileUpload")[0].files[0];

        var form = new FormData();
        form.append("ids",nodes);
        form.append("file",file);
        form.append("changebin",isChange);

        console.log("isChange" + isChange);

        $("#nodeBurnDialog").modal("hide");
        $.ajax(
            {
                url: '<%=basePath%>api/node/burn',
                type: 'POST',
                data: form,
                contentType: false, //禁止设置请求类型
                processData: false, //禁止jquery对DAta数据的处理,默认会处理
                //禁止的原因是,FormData已经帮我们做了处理
                success: function (response) {
                    if(response==="success"){
                        toastr.success("bin file has uploaded; the nodes are burning");
                    }
                    else toastr.error(response);
                }
            }
        );
    }
    function submitStart() {
        var nodes = [];
        if(nodes_flag){
            nodes = selected_nodes;
        }
        else {
            nodes.push(select_node);
        }

        $("#nodeStartDialog").modal("hide");
        $.post("<%=basePath%>api/node/operate",{"ids":nodes, "opSign":1},function (response) {
            if(response!=="success"){
                toastr.error(response);
            }
        });
    }

    function submitStop() {
        var nodes = [];
        if(nodes_flag){
            nodes = selected_nodes;
        }
        else {
            nodes.push(select_node);
        }

        $("#nodeStopDialog").modal("hide");
        $.post("<%=basePath%>api/node/operate",{"ids":nodes, "opSign":0},function (response) {
            if(response!=="success"){
                toastr.error(response);
            }
        });
    }

    function submitRestart() {
        var nodes = [];
        if(nodes_flag){
            nodes = selected_nodes;
        }
        else {
            nodes.push(select_node);
        }

        $("#nodeRestartDialog").modal("hide");
        $.post("<%=basePath%>api/node/operate",{"ids":nodes, "opSign":2},function (response) {
            if(response!=="success"){
                toastr.error(response);
            }
        });
    }

    function submitStopUse() {
        var nodes = [];
        if(nodes_flag){
            nodes = selected_nodes;
        }
        else {
            nodes.push(select_node);
        }

        $("#nodeStopUseDialog").modal("hide");
        $.post("<%=basePath%>api/node/stopUse",{"ids":nodes,},function (response) {
            if(response==="success"){
                toastr.success("Stop to use nodes "+ selected_nodes.join(" ") + " successfully.");
                setTimeout(function () {
                    window.location.reload();
                }, 1500);
            }
            else toastr.error(response);
        });
    }

    function searchLogBtn() {
        var date1 = $("#log-date01");
        var date2 = $("#log-date02");
        var st=new Date(date1.val() + " " + date1.next().val() + ":" + date1.next().next().val());
        var ed=new Date(date2.val() + " " + date2.next().val() + ":" + date2.next().next().val());
        $.post("<%=basePath%>api/node/log",{"id":select_node, "starttime":st, "endtime":ed},function (response) {
            if(response!=="success"){
                toastr.error(response);
            }
        });
    }

    function searchDataBtn() {
        //var date1 = $("#data-date01");
        //var date2 = $("#data-date02");
        //var st=new Date(date1.val() + " " + date1.next().val() + ":" + date1.next().next().val());
        //var ed=new Date(date2.val() + " " + date2.next().val() + ":" + date2.next().next().val());

        var str1 = "1980/01/01 0:0";
        var str2 = "2100/01/01 0:0";
        var st = new Date(str1);
        var ed = new Date(str2);

        $.post("<%=basePath%>api/node/data",{"id":select_node, "starttime":st, "endtime":ed},function (response) {
            if(response!=="success"){
                toastr.error(response);
            }
        });
    }

    function openDownloadDialog(url, saveName)
    {
        if(typeof url === 'object' && url instanceof Blob)
        {
            url = URL.createObjectURL(url); // 创建blob地址
        }
        var aLink = document.createElement('a');
        aLink.href = url;
        aLink.download = saveName || ''; // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
        var event;
        if(window.MouseEvent) event = new MouseEvent('click');
        else
        {
            event = document.createEvent('MouseEvents');
            event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
        }
        aLink.dispatchEvent(event);
    }

    function setNodeStatus(id, status, percent) {
        var table = $("#node-manage-datatable").DataTable();
        var total = table.fnPagingInfo().iTotal;
        for (var i = 0; i < total; i++) {
            var column_id = parseInt(table.fnGetData(i, 1));
            if (id === column_id) {
                var status_html;
                if (status === 0) {
                    status_html = "<span class=\"label\">Offline</span>";
                }
                else if (status === 1) {
                    status_html = "<span class=\"label label-info\">Usable</span>";
                }
                else if (status === 2) {
                    status_html = "<span class=\"label label-success\">Running</span>";
                }
                else if (status === 3) {
                    status_html = "<span class=\"label label-important\">Stopped</span>";
                }
                else if (status === 4) {
                    if(percent !== undefined)
                        status_html = "<span class=\"label label-warning\">Burning " + percent + "%" + "</span>";
                    else status_html = "<span class=\"label label-warning\">Burning</span>";
                }
                table.fnUpdate(status_html, i, 3);
                break;
            }
        }
    }
    function m_split(str, char, limit){
        var list=[];
        var count = 0;
        var last = -1;
        for(var i = 0; i < str.length; i++){
            if(str[i]===char){
                if(count+1<limit){
                    if(i > last + 1) {
                        list.push(str.substring(last+1, i));
                        count++;
                    }
                    last = i;
                }
                else break;
            }
        }
        list.push(str.substring(last+1));
        return list;
    }
</script>

</body>
</html>
