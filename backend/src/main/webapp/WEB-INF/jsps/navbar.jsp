<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="navbar">
    <div class="navbar-inner" style="min-height: 60px">
        <div class="container-fluid">
            <a id="main-menu-toggle" class="hidden-phone my-body my-navbar open"><i class="icon-reorder"></i></a>
            <div class="row-fluid">
                <a class="brand span2 my-navbar" href="index"><span>LORA TESTBED</span></a>
            </div>
            <!-- start: Header Menu -->
            <div class="nav-no-collapse header-nav">
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a href="?language=en" style="padding: 0 40px 0 0; color: white;text-shadow: none"><span> [English Version]</span></a>
                    </li>
                    <li class="dropdown welcome">
                        <span> 欢迎，${username}</span>
                    </li>
                    <li class="dropdown">
                        <a href="<%=basePath%>login/logout" style="padding: 0; color: white;text-shadow: none">
                            <i class="icon-off"></i>
                            <span>注销</span>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- end: Header Menu -->

        </div>
    </div>
</div>