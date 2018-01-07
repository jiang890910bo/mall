<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>头部</title>
</head>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript">
    $(function(){
        //顶部导航切换
        $(".nav li a").click(function(){
            $(".nav li a.selected").removeClass("selected")
            $(this).addClass("selected");
        })
    })
</script>
<body style="background:url(../../images/topbg.gif) repeat-x;">

<div class="topleft">
    <a href="main.html" target="_parent"><img src="../../images/logo.png" title="系统首页" /></a>
</div>

<ul class="nav">
    <li><a href="/user/userList" target="rightFrame" class="selected"><img src="../../images/i07.png" title="用户管理" width="45" height="45"/><h2>用户管理</h2></a></li>
    <li><a href="/category/categoryList" target="rightFrame"><img src="../../images/icon01.png" title="目录管理"  width="45" height="45"/><h2>目录管理</h2></a></li>
    <li><a href="/product/productList"  target="rightFrame"><img src="../../images/d06.png" title="产品管理"  width="45" height="45"/><h2>产品管理</h2></a></li>
    <li><a href="/image/imageList"  target="rightFrame"><img src="../../images/img14.png" title="图片管理"  width="45" height="45"/><h2>图片管理</h2></a></li>
    <li><a href="/activity/activityList"  target="rightFrame"><img src="../../images/i09.png" title="活动管理"  width="45" height="45"/><h2>活动管理</h2></a></li>
    <li><a href="/order/statistics"  target="rightFrame"><img src="../../images/i05.png" title="订单统计"  width="45" height="45"/><h2>订单统计</h2></a></li>
</ul>

<div class="topright">
    <ul>
        <li><span><img src="../../images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
        <li><a href="/login/exit" target="_parent">退出</a></li>
    </ul>

    <#--<div class="user">
        <span>admin</span>
        <i>消息</i>
        <b>5</b>
    </div>-->

</div>
</body>
</html>