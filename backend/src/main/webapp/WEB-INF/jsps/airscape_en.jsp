<%--
  Created by IntelliJ IDEA.
  User: baishun
  Date: 2018/9/17
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>LoRa TestBed platform</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- start: CSS -->
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet"/>
    <link href="<%=basePath%>css/bootstrap-responsive.min.css" rel="stylesheet"/>
    <link href="<%=basePath%>css/bootstrap-datepicker.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/style.min.css" rel="stylesheet"/>
    <link href="<%=basePath%>css/style-responsive.min.css" rel="stylesheet"/>
    <link href="<%=basePath%>css/retina.css" rel="stylesheet"/>
    <link href="<%=basePath%>css/toastr.min.css" rel="stylesheet" />
    <link href="<%=basePath%>css/main.css" rel="stylesheet"/>
    <!-- end: CSS -->

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>

    <!--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>-->
    <link id="ie-style" href="css/ie.css" rel="stylesheet">
    <![endif]-->

    <!--[if IE 9]>
    <link id="ie9style" href="css/ie9.css" rel="stylesheet">
    <![endif]-->

    <script src="<%=basePath%>js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/getscript?v=2.0&ak=fjQgfPruozIPlj7pMrVRn7s5MCmp4eWr"></script>
</head>

<body>
<jsp:include page="navbar_en.jsp"/>
<%--<div id="allmap"></div>--%>

<div class="container-fluid-full">
    <div class="row-fluid">

        <jsp:include page="sidebar_en.jsp"/>

        <div id="content" class="span10" style="min-height: 678px;">
            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-content">
                        <div id="map" style="height: 680px; width: 100%">map is here</div>
                    </div>
                </div>
            </div>
            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-content">
                        <div><img src="<%=basePath%>img/green.png"/>可选
                            <img src="<%=basePath%>img/blue.png"/>预订
                            <img src="<%=basePath%>img/running.png"/>正在运行
                            <img src="<%=basePath%>img/suspend.png"/>暂停
                            <%--<img src="<%=basePath%>img/pink.png"/>别人占用--%>
                            <%--<img src="<%=basePath%>img/order.png">预定的 --%>
                            <img src="<%=basePath%>img/offline.png"/>离线
                            <img src="<%=basePath%>img/gateway.png"/>网关
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="clearfix"></div>

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
<script src="<%=basePath%>js/toastr.min.js"></script>
<script src="<%=basePath%>js/sockjs.min.js"></script>
<script src="<%=basePath%>js/main_en.js"></script>
<script type="text/javascript">

    // 创建标注对象并添加到地图
    var points = new Array(100);

    var point = function () {
        this.icon = 0;
        this.l = 116.340164;
        this.b = 40.007576;
        this.pos = "东主楼";
        this.state = 0;
        this.marker = "";
    };

    for (i = 0; i < 100; i++) {
        points[i] = new point();
    }

    var iconsPath = ["<%=basePath%>img/green.png", "<%=basePath%>img/blue.png",
        "<%=basePath%>img/running.png", "<%=basePath%>img/suspend.png",
        "<%=basePath%>img/offline.png", "<%=basePath%>img/gateway.png"];

    var icon1 = new BMap.Icon(iconsPath[0], new BMap.Size(30, 30)); //显示图标大小
    var icon2 = new BMap.Icon(iconsPath[1], new BMap.Size(30, 30)); //显示图标大小
    var icon3 = new BMap.Icon(iconsPath[2], new BMap.Size(30, 30)); //显示图标大小
    var icon4 = new BMap.Icon(iconsPath[3], new BMap.Size(30, 30)); //显示图标大小
    var icon5 = new BMap.Icon(iconsPath[4], new BMap.Size(30, 30)); //显示图标大小
    var icon6 = new BMap.Icon(iconsPath[5], new BMap.Size(30, 30)); //显示图标大小
    // var icon8_order = new BMap.Icon(iconsPath[7], new BMap.Size(30, 30)); //显示图标大小
    var icons = [icon1, icon2, icon3, icon4, icon5, icon6];

    $(document).ready(function () {
        var map = new BMap.Map("map"); // 创建地图实例
        //创建点坐标
        var centerPoint = new BMap.Point(116.339041, 40.007689); //创建点坐标
        //地图初始化
        map.centerAndZoom(centerPoint, 18); // 初始化地图，设置中心点坐标和地图级别
        map.enableScrollWheelZoom(true); //允许缩放

        var scalePos = {anchor: BMAP_ANCHOR_TOP_LEFT};  //设置偏移
        map.addControl(new BMap.NavigationControl(scalePos));  //两个控件
        map.addControl(new BMap.ScaleControl(scalePos));

        initMapFromDB(map);  //points的icon 调用initmap
    });

    function initMapFromDB(map) {
        initMap(map);
    }

    function initMap(map) {
        map.clearOverlays();  //清除地图上所有障碍物

        // points[0].l = 116.3413;
        // points[0].b = 40.0066;
        // points[0].icon = 1;
        /*points[1].l = 116.3453;
        points[1].b = 40.0076;
        points[1].icon = 1;
        points[2].l = 116.3455;
        points[2].b = 40.0096;
        points[2].icon = 1;
        points[3].l = 116.3454;
        points[3].b = 40.0070;
        points[3].icon = 3;
        points[4].l = 116.3336;
        points[4].b = 40.0068;
        points[4].icon = 1;
        points[5].l = 116.3368;
        points[5].b = 40.0070;
        points[5].icon = 3;
        points[6].l = 116.3356;
        points[6].b = 40.0086;
        points[6].icon = 1;
        points[7].l = 116.3363;
        points[7].b = 40.0071;
        points[7].icon = 3;
        points[8].l = 116.3353;
        points[8].b = 40.0075;
        points[8].icon = 1;
        points[9].l = 116.3343;
        points[9].b = 40.0072;
        points[9].icon = 1;
        points[10].l = 116.3333;
        points[10].b = 40.0076;
        points[10].icon = 3;
        points[11].l = 116.3323;
        points[11].b = 40.0088;
        points[11].icon = 1;*/

        //foo(map);
        $.ajax({
            url: "<%=basePath%>map/nodeinfo",
            type: 'post',
            dataType: 'json',
            success: function (data) {
                data = JSON.parse(data);
                for (var node_id = 0; node_id <= data.length; node_id++) {
                    if (data[node_id] == null || data[node_id] == "null" || data[node_id] == "") {
                        continue;
                    }
                    points[node_id].l = data[node_id][0][0];
                    points[node_id].b = data[node_id][0][1];
                }
                foo(map);
            },
            error: function () {
                console.log("no nodes.");
            }
        });
    }

    function foo(map) {
        var point, marker, bpoints = [];
        for (var i = 0, pointsLen = points.length; i < pointsLen; i++) {
            if (points[i].icon <= 0) {
                points[i].icon = 1;
            }
            point = new BMap.Point(points[i].l, points[i].b);
            bpoints.push(point);
            marker = new BMap.Marker(point);
            marker.setIcon(icons[points[i].icon - 1]);
            map.addOverlay(marker);
        }
        //按照添加的坐标计算合适的zoom及中心坐标并更新map
        map.setViewport(bpoints);
        initGateway(map);
    }

    function initGateway(map) {
        $.ajax({
            url: "<%=basePath%>map/initGateway",
            type: 'post',
            dataType: 'json',
            success: function (data) {
                data = JSON.parse(data);
                for (p = 0, q = data.length; p < q; p++) {
                    var point = new BMap.Point(data[p][0], data[p][1]);
                    var marker = new BMap.Marker(point);
                    marker.setIcon(icons[5]);
                    //给标注点添加点击事件。使用立即执行函数和闭包
                    (function () {
                        marker.addEventListener("click", function () {
                            firm();
                        });
                    })();
                    map.addOverlay(marker);
                }
            },
            error: function () {
                console.log("no gateway.");
            }
        });
    }

    //弹出一个询问框，有确定和取消按钮
    function firm() {
        //利用对话框返回的值 （true 或者 false）
        if (confirm("你确定暂停网关吗？")) {
            alert("点击了确定");
        }
        else {
            alert("点击了取消");
        }
    }

</script>
</body>
</html>