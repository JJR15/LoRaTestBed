<%--
  Created by IntelliJ IDEA.
  User: JJR
  Date: 2018/7/20
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
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
<jsp:include page="navbar.jsp" />

<div class="container-fluid-full">
    <div class="row-fluid">

        <jsp:include page="sidebar.jsp" />

        <div id="content" class="span10" style="min-height: 678px;">
            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header" data-original-title="">
                        <h2><i class="icon-map-marker"></i><span class="break"></span>我的预约</h2>
                    </div>
                    <div class="box-content">
                        <div class="span12 height30">
                            <div class="span6"><strong>已选择节点:</strong><br /><span class="node-ids">无</span>&nbsp;</div>
                            <div class="span6">
                                <button class="btn btn-danger orderbtn" onclick="cancelAllBtn()">
                                    全部取消
                                </button>
                            </div>
                        </div>
                        <table id="node-order-datatable" class="table table-striped table-bordered bootstrap-datatable datatable">
                            <thead>
                            <tr>
                                <th><input type="checkbox" onclick="changePageSelect()"/></th>
                                <th> 节点ID</th>
                                <th> 开始时间</th>
                                <th> 结束时间</th>
                                <th> 操作</th>
                            </tr>
                            </thead>
                            <tbody id="node_manage">
                            <c:forEach items="${node_orders}" var = "node_order" varStatus="status">
                                <tr>
                                    <td><input type="checkbox" onclick="changeNodeSelect(${status.index},${node_order.id})"/></td>
                                    <td>${node_order.id}</td>
                                    <td><fmt:formatDate value="${node_order.starttime}" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
                                    <td><fmt:formatDate value="${node_order.endtime}" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
                                    <td>
                                        <button class="btn btn-danger" onclick="cancelBtn(${status.index},${node_order.id})">
                                            取消预约
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

    <div class="modal hide fade" id="nodeOrderCancelDialog">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>取消预约</h3>
        </div>
        <div class="modal-body">
            确定要取消节点<span class="node-id node-ids"></span>的预约吗？
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-primary" onclick="submitCancel()">确认</a>
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
<script src="<%=basePath%>js/toastr.min.js"></script>
<script src="<%=basePath%>js/main.js"></script>
<!--<script src="<%=basePath%>js/custom.min.js"></script>-->
<script>
    var selected_nodes = [];
    var select_node = null;
    var nodes_flag = false;
    $(document).ready(function(){
        toastr.options = $.toastrSetting;
    });
    function cancelBtn(index, id){
        select_node = {index:index, id: id};
        nodes_flag = false;
        $(".node-id").text(id);
        $("#nodeOrderCancelDialog").modal("show");
    }
    function cancelAllBtn() {
        if(selected_nodes.length === 0){
            return;
        }
        nodes_flag = true;
        var t = selected_nodes[0].id.toString();
        for(var i = 1; i < selected_nodes.length; i++){
            t = t + " " + selected_nodes[i].id.toString();
        }
        $(".node-ids").text(t);
        $("#nodeOrderCancelDialog").modal("show");
    }
    function changePageSelect() {
        var table = $("#node-order-datatable").DataTable();
        var all_check = table.children()[0].children[0].children[0].children[0];
        $("input:checkbox").each(function () {
            if(this.checked !== all_check.checked){
                this.click();
            }
        });
    }
    function changeNodeSelect(index, id) {
        var i = -1, j = 0;
        for(j = 0;j<selected_nodes.length;j++){
            if(selected_nodes[j].id === id &&selected_nodes[j].index === index){
                i = j;
                break;
            }
        }
        if(i >= 0) {
            selected_nodes.splice(i, 1);
        }
        else {
            selected_nodes.push({index:index ,id:id });
        }
        selected_nodes.sort(function (x, y) {
            if(x.id < y.id) return -1;
            else if(x.id > y.id) return 1
            else if(x.index < y.index) return -1;
            else if(x.index > y.index) return 1;
            else return 0;
        });
        if(selected_nodes.length === 0){
            $(".node-ids").text("无");
        }
        else {
            var t = selected_nodes[0].id.toString();
            for(j = 1;j<selected_nodes.length;j++){
                t = t + " " + selected_nodes[j].id.toString();
            }
            $(".node-ids").text(t);
        }
    }
    function submitCancel() {
        var cancels = [];
        var table = $("#node-order-datatable").DataTable();
        if(nodes_flag){
            for(var i=0; i<selected_nodes.length; i++) {
                cancels.push({id:selected_nodes[i].id, starttime:new Date(table.fnGetData(selected_nodes[i].index,2)).toUTCString(),
                    endtime: new Date(table.fnGetData(selected_nodes[i].index,3)).toUTCString()});
            }
        }
        else {
            cancels.push({id:select_node.id, starttime:new Date(table.fnGetData(select_node.index,2)).toUTCString(),
                endtime: new Date(table.fnGetData(select_node.index,3)).toUTCString()});
        }

        $("#nodeOrderCancelDialog").modal("hide");
        $.ajax({
            type:"POST",
            url:"<%=basePath%>api/node/order/cancel",
            data:JSON.stringify(cancels),
            dataType:"json",
            contentType:"application/json",
            error:function (status, error) {
                
            },
            success:function (data, status) {
                if(data==="success"){
                    toastr.success("取消预定成功！");
                    window.location.reload();
                }
                else toastr.error(data);
            }
        });
    }
</script>
<!-- end: JavaScript-->

</body>
</html>
