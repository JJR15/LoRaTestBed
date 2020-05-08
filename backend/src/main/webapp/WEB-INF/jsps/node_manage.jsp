<%--
  Created by IntelliJ IDEA.
  User: JJR
  Date: 2018/7/20
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
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
<jsp:include page="navbar.jsp" />

<div class="container-fluid-full">
    <div class="row-fluid">

        <jsp:include page="sidebar.jsp" />

        <div id="content" class="span10" style="min-height: 678px;">
            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header" data-original-title="">
                        <h2><i class="icon-map-marker"></i><span class="break"></span>节点管理</h2>
                    </div>
                    <div class="box-content">
                        <div class="span12 height30">
                            <div class="span6"><strong>已选择的节点:</strong><br /><span class="node-ids">无</span>&nbsp;</div>
                            <div class="span6">
                                <ul class="operate-list">
                                    <li><strong>批量操作:</strong></li>
                                    <li>
                                        <button class="btn btn-danger" onclick="burnAllBtn()">
                                            <i class="icon-pencil"></i>
                                            烧录
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-success" onclick="startAllBtn()">
                                            <i class="icon-play"></i>
                                            开启
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-danger" onclick="stopAllBtn()">
                                            <i class="icon-stop"></i>
                                            停止
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-danger" onclick="restartAllBtn()">
                                            <i class="icon-refresh"></i>
                                            重启
                                        </button>
                                    </li>
                                    <li>
                                        <button class="btn btn-danger" onclick="stopUseAllBtn()">
                                            <i class="icon-stop"></i>
                                            停止使用
                                        </button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <table id="node-manage-datatable" class="table table-striped table-bordered bootstrap-datatable datatable">
                            <thead>
                            <tr class="center">
                                <th><input type="checkbox" onclick="changePageSelect()"/></th>
                                <th> 节点ID</th>
                                <th> 到期时间</th>
                                <th> 节点状态</th>
                                <th> 操作</th>
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
                                            <span class="label">离线</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 1}">
                                            <span class="label label-info">可用</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 2}">
                                            <span class="label label-success">运行</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 3}">
                                            <span class="label label-important">停止</span>
                                        </c:if>
                                        <c:if test="${node_manage_info.nodeInfo.state == 4}">
                                            <span class="label label-warning">烧录</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <!--,-->
                                        <button class="btn btn-success" onclick="opsBtn(${status.index},${node_manage_info.nodeInfo.id},'${node_manage_info.nodeInfo.MAC}',${node_manage_info.nodeInfo.longitude},${node_manage_info.nodeInfo.latitude},'${node_manage_info.starttime.toString()}','${node_manage_info.endtime.toString()}')">
                                            展开
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
            <h3>烧录节点</h3>
        </div>
        <div class="modal-body">
            已选择的节点：<span class="node-id node-ids"></span><br />
            <div style="margin-top: 20px">
                <p> 上传文件</p>
                <form id="file"> <input id="fileUpload" type="file" accept=".bin"> </form>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitBurn()">确认</a>
            <a href="#" class="btn" data-dismiss="modal">取消</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeStartDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>运行节点</h3>
        </div>
        <div class="modal-body">
            确定要开始运行节点<span class="node-id node-ids"></span>吗？
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitStart()">确认</a>
            <a href="#" class="btn" data-dismiss="modal">取消</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeStopDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>停止节点</h3>
        </div>
        <div class="modal-body">
            确定要停止节点<span class="node-id node-ids"></span>吗？
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitStop()">确认</a>
            <a href="#" class="btn" data-dismiss="modal">取消</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeRestartDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>重启节点</h3>
        </div>
        <div class="modal-body">
            确定要重启节点<span class="node-id node-ids"></span>吗？
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitRestart()">确认</a>
            <a href="#" class="btn" data-dismiss="modal">取消</a>
        </div>
    </div>

    <!--<div class="modal hide fade" id="nodeLogDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>节点<span class="node-id"></span>日志</h3>
        </div>
        <div class="modal-body">
            <div>
                <div class="dialog-div">下载日志：</div>
                <div class="dialog-div" style="text-align: right"><a href="#" id="log-url" target="_blank">查看实时日志</a></div>
            </div>

            <div class="center">
                开始时间：
                <input type="text" class="input-small datepicker before-now" id="log-date01" value="2018-01-01"  readonly="readonly"/>
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="23" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 时
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="59" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 分
            </div>

            <div class="center">
                结束时间：
                <input type="text" class="input-small datepicker before-now" id="log-date02" value="2018-01-01"  readonly="readonly"/>
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="23" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 时
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="59" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 分
            </div>
            <div class="dialog-btn"><button id="download-log-btn" class="btn btn-primary" onclick="searchLogBtn()"> 查询</button></div>
        </div>
    </div>-->

    <div class="modal hide fade" id="nodeDataDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>节点<span class="node-id"></span>数据</h3>
        </div>
        <div class="modal-body">
            <div>
                <div class="dialog-div">下载数据：</div>
                <div class="dialog-div" style="text-align: right"><a href="#" id="data-url" target="_blank">查看实时输出</a></div>
            </div>
            <div class="center">
                开始时间：
                <input type="text" class="input-small datepicker before-now" id="data-date01" value="2018-01-01"  readonly="readonly"/>
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="23" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 时
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="59" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 分
            </div>

            <div class="center">
                结束时间：
                <input type="text" class="input-small datepicker before-now" id="data-date02" value="2018-01-01"  readonly="readonly"/>
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="23" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 时
                <select class="input-mini">
                    <c:forEach var="i" begin="0" end="59" step="1">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select> 分
            </div>
            <div class="dialog-btn"><button id="download-data-btn" class="btn btn-primary" onclick="searchDataBtn()"> 查询</button></div>
        </div>
        <!--<div class="modal-footer center">
            <a href="#" class="btn btn-primary" data-dismiss="modal">确认</a>
        </div>-->
    </div>

    <div class="modal hide fade" id="nodeDetailDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>节点<span class="node-id"></span>详情</h3>
        </div>
        <div class="modal-body">
            <div id="map" style="height: 350px; width: 100%"></div>
            <p style="line-height: 25px">
                节点ID：<span class="node-id">01</span> &nbsp;
                MAC：<span id="mac"></span> &nbsp;
                经度：<span id="longitude"></span>,  &nbsp; 纬度：<span id="latitude"></span><br/>
                可使用时间：<span id="starttime"></span>至<span id="endtime"></span>
            </p>
        </div>
        <div class="modal-footer center">
            <a href="#" class="btn btn-primary" data-dismiss="modal">确认</a>
        </div>
    </div>
    <div class="modal hide fade" id="nodeStopUseDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>放弃使用节点</h3>
        </div>
        <div class="modal-body">
            确定要放弃使用节点<span class="node-id node-ids"></span>吗？
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitStopUse()">确认</a>
            <a href="#" class="btn" data-dismiss="modal">取消</a>
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
    <script src="<%=basePath%>js/bootstrap-datepicker.zh-CN.js"></script>
    <script src="<%=basePath%>js/toastr.min.js"></script>
    <script src="<%=basePath%>js/sockjs.min.js"></script>
    <script src="<%=basePath%>js/main.js"></script>
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
                    toastr.success("节点"+node_id+"开启成功");
                }
                else{
                    toastr.error("节点"+node_id+"开启失败：" + str_list[4]);
                }
            }
            else if(message.startsWith("stop message ")){
                str_list = m_split(message, " ", 5);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                if(str_list[3]==="succ"){
                    toastr.success("节点"+node_id+"停止成功");
                }
                else{
                    toastr.error("节点"+node_id+"停止失败："+str_list[4]);
                }
            }
            else if(message.startsWith("restart message ")){
                str_list = m_split(message, " ", 5);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                if(str_list[3]==="succ"){
                    toastr.success("节点"+node_id+"重启成功");
                }
                else{
                    toastr.error("节点"+node_id+"重启失败："+str_list[4]);
                }
            }
            else if(message.startsWith("burn message ")){
                str_list = m_split(message, " ", 5);
                node_id = parseInt(str_list[2]);
                if(isNaN(node_id)) return;
                if(str_list[3]==="succ"){
                    toastr.success("节点"+node_id+"烧写成功");
                }
                else{
                    toastr.error("节点"+node_id+"烧写失败："+str_list[4]);
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
                console.log(message, str_list);
                toastr.error("获取节点" + str_list[1] + "data文件失败：" + str_list[2]);
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
            ")\"> <i class=\"icon-pencil\"></i> 烧录 </button> <button class=\"btn btn-success\" onclick=\"startBtn(" +
            id +
            ")\"> <i class=\"icon-play\"></i> 开启 </button> <button class=\"btn btn-danger\" onclick=\"stopBtn(" +
            id +
            ")\"> <i class=\"icon-stop\"></i> 停止 </button> <button class=\"btn btn-danger\" onclick=\"restartBtn(" +
            id +
            ")\"> <i class=\"icon-refresh\"></i> 重启 </button> <button class=\"btn btn-info\" onclick=\"dataBtn(" +
            id +
            ")\"> <i class=\"icon-download\"></i> 下载数据 </button> <button class=\"btn btn-success\" onclick=\"detailBtn(" +
            id +",'"+mac+"',"+longitude+","+latitude+",'"+starttime+"','"+endtime+"'"+
            ")\"> <i class=\"icon-info-sign\"></i> 详情 </button> <button class=\"btn btn-danger\" onclick=\"stopUseBtn(" +
            id +
            ")\"> <i class=\"icon-stop\"></i> 停止使用 </button> </td> </tr>";
        $("#opBtns").remove();
        $(text).insertAfter($("#node-manage-datatable").DataTable().fnGetNodes(t));
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
    /*function logBtn(id) {
        select_node = id;
        $(".node-id").text(id);
        $("#log-url").attr('href','/lora/node/log?node='+id);
        $("#nodeLogDialog").modal("show");
    }*/
    function dataBtn(id) {
        select_node = id;
        $(".node-id").text(id);
        $("#data-url").attr('href','/lora/node/data?node='+id);
        $("#nodeDataDialog").modal("show");
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
        if(selected_nodes.length === 0){
            return;
        }
        $(".node-ids").text(selected_nodes.join(" "));
        $("#nodeBurnDialog").modal("show");
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

        $("#nodeBurnDialog").modal("hide");
        $.ajax(
            {
                url: '/api/node/burn',
                type: 'POST',
                data: form,
                contentType: false, //禁止设置请求类型
                processData: false, //禁止jquery对DAta数据的处理,默认会处理
                //禁止的原因是,FormData已经帮我们做了处理
                success: function (response) {
                    if(response==="success"){
                        toastr.success("文件已上传，正在烧录！");
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
                toastr.success("放弃使用节点成功！");
                setTimeout(function () {
                    window.location.reload();
                }, 1500);
            }
            else toastr.error(response);
        });
    }

    /*function searchLogBtn() {
        var date1 = $("#log-date01");
        var date2 = $("#log-date02");
        var st=new Date(date1.val() + " " + date1.next().val() + ":" + date1.next().next().val());
        var ed=new Date(date2.val() + " " + date2.next().val() + ":" + date2.next().next().val());
        $.post("<%=basePath%>api/node/log",{"id":select_node, "starttime":st, "endtime":ed},function (response) {
            if(response!=="success"){
                toastr.error(response);
            }
        });
    }*/

    function searchDataBtn() {
        var date1 = $("#data-date01");
        var date2 = $("#data-date02");
        var st=new Date(date1.val() + " " + date1.next().val() + ":" + date1.next().next().val());
        var ed=new Date(date2.val() + " " + date2.next().val() + ":" + date2.next().next().val());
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
        for(var i = 0; i < total; i++){
            var column_id = parseInt(table.fnGetData(i, 1));
            if(id === column_id) {
                var status_html;
                if(status === 0) {
                    status_html = "<span class=\"label\">离线</span>";
                }
                else if(status === 1){
                    status_html = "<span class=\"label label-info\">可用</span>";
                }
                else if(status === 2){
                    status_html = "<span class=\"label label-success\">运行</span>";
                }
                else if(status === 3){
                    status_html = "<span class=\"label label-important\">停止</span>";
                }
                else if(status === 4){
                    if(percent !== undefined)
                        status_html = "<span class=\"label label-warning\">烧录 " + percent + "%" + "</span>";
                    else status_html = "<span class=\"label label-warning\">烧录</span>";
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
