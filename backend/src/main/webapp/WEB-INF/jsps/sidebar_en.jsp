<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div id="sidebar-left" class="span2">
    <div class="nav-collapse sidebar-nav">
        <ul class="nav nav-tabs nav-stacked main-menu">
            <li><a href="<%=basePath%>index?language=en"><span class="hidden-tablet sidebar-mainmenu"> DashBoard</span></a></li>
            <li>
                <a class="dropmenu" href="#"><span class="hidden-tablet sidebar-mainmenu"> Node Info</span> <span class="label" style="margin-top: 4px">2</span></a>
                <ul>
                    <li><a class="submenu" href="<%=basePath%>node/info?language=en"><span class="hidden-tablet sidebar-submenu"> Node Ordering</span></a></li>
                    <li><a class="submenu" href="<%=basePath%>node/order?language=en"><span class="hidden-tablet sidebar-submenu"> My Orders</span></a></li>
                </ul>
            </li>
            <li><a href="<%=basePath%>node_manage?language=en"><span class="hidden-tablet sidebar-mainmenu"> Node Manage</span></a></li>
            <li><a href="<%=basePath%>gateway?language=en"><span class="hidden-tablet sidebar-mainmenu"> GateWay</span></a></li>
            <li><a href="https://loraserver.lpwan-thu.top"><span class="hidden-tablet sidebar-mainmenu"> LoraServer</span></a></li>
            <li><a href="<%=basePath%>node_map?language=en"><span class="hidden-tablet sidebar-mainmenu"> Airscape</span></a></li>
            <li><a href="<%=basePath%>manual?language=en"><span class="hidden-tablet sidebar-mainmenu"> User Guide</span></a></li>
            <li><a href="<%=basePath%>about_us?language=en"><span class="hidden-tablet sidebar-mainmenu"> About Us</span></a></li>
        </ul>
    </div>
</div>