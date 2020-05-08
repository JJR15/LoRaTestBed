<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div id="sidebar-left" class="span2">
    <div class="nav-collapse sidebar-nav">
        <ul class="nav nav-tabs nav-stacked main-menu">
            <li><a href="<%=basePath%>index"><span class="hidden-tablet sidebar-mainmenu"> 系统概况</span></a></li>
            <li>
                <a class="dropmenu" href="#"><span class="hidden-tablet sidebar-mainmenu"> 节点信息</span> <span class="label" style="margin-top: 4px">2</span></a>
                <ul>
                    <li><a class="submenu" href="<%=basePath%>node/info"><span class="hidden-tablet sidebar-submenu"> 预定节点</span></a></li>
                    <li><a class="submenu" href="<%=basePath%>node/order"><span class="hidden-tablet sidebar-submenu"> 我的预定</span></a></li>
                </ul>
            </li>
            <li><a href="<%=basePath%>node_manage"><span class="hidden-tablet sidebar-mainmenu"> 节点管理</span></a></li>
            <li><a href="<%=basePath%>manual"><span class="hidden-tablet sidebar-mainmenu"> 使用教程</span></a></li>
            <li><a href="<%=basePath%>about_us"><span class="hidden-tablet sidebar-mainmenu"> 关于我们</span></a></li>
        </ul>
    </div>
</div>