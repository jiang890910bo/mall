<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>左边菜单页</title>
</head>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery.js"></script>

<script type="text/javascript">
    $(function(){
        //导航切换
        $(".menuson li").click(function(){
            $(".menuson li.active").removeClass("active")
            $(this).addClass("active");
        });

        $('.title').click(function(){
            var $ul = $(this).next('ul');
            $('dd').find('ul').slideUp();
            if($ul.is(':visible')){
                $(this).next('ul').slideUp();
            }else{
                $(this).next('ul').slideDown();
            }
        });
    })
</script>


</head>

<body style="background:#f0f9fd;">
<div class="lefttop"><span></span>菜单栏</div>

<dl class="leftmenu">

    <dd>
        <div class="title">
            <span><img src="../../images/leftico01.png" /></span>用户管理
        </div>
        <ul class="menuson">
            <li><cite></cite><a href="/user/userList" target="rightFrame">用户列表</a><i></i></li>
            <#--<li><cite></cite><a href="index.html" target="rightFrame">首页模版</a><i></i></li>
            <li class="active"><cite></cite><a href="right.html" target="rightFrame">数据列表</a><i></i></li>
            <li><cite></cite><a href="imgtable.html" target="rightFrame">图片数据表</a><i></i></li>
            <li><cite></cite><a href="form.html" target="rightFrame">添加编辑</a><i></i></li>
            <li><cite></cite><a href="imglist.html" target="rightFrame">图片列表</a><i></i></li>
            <li><cite></cite><a href="imglist1.html" target="rightFrame">自定义</a><i></i></li>
            <li><cite></cite><a href="tools.html" target="rightFrame">常用工具</a><i></i></li>
            <li><cite></cite><a href="filelist.html" target="rightFrame">信息管理</a><i></i></li>
            <li><cite></cite><a href="tab.html" target="rightFrame">Tab页</a><i></i></li>
            <li><cite></cite><a href="error.html" target="rightFrame">404页面</a><i></i></li>-->
        </ul>
    </dd>


    <dd>
        <div class="title">
            <span><img src="../../images/leftico02.png" /></span>商品目录管理
        </div>
        <ul class="menuson">
            <li><cite></cite><a href="/category/categoryList" target="rightFrame">商品目录列表</a><i></i></li>
            <#--<li><cite></cite><a href="#">发布信息</a><i></i></li>
            <li><cite></cite><a href="#">档案列表显示</a><i></i></li>
            <li><cite></cite><a href="#">编辑内容</a><i></i></li>
            <li><cite></cite><a href="#">发布信息</a><i></i></li>
            <li><cite></cite><a href="#">档案列表显示</a><i></i></li>-->
        </ul>
    </dd>


    <dd><div class="title"><span><img src="../../images/leftico03.png" /></span>产品管理</div>
        <ul class="menuson">
            <li><cite></cite><a href="/product/productList" target="rightFrame">产品列表</a><i></i></li>
            <#--<li><cite></cite><a href="#">常用资料</a><i></i></li>
            <li><cite></cite><a href="#">信息列表</a><i></i></li>
            <li><cite></cite><a href="#">其他</a><i></i></li>-->
        </ul>
    </dd>

    <dd><div class="title"><span><img src="../../images/leftico03.png" /></span>图片管理</div>
        <ul class="menuson">
            <li><cite></cite><a href="/image/imageList" target="rightFrame">图片列表</a><i></i></li>
        <#--<li><cite></cite><a href="#">常用资料</a><i></i></li>
        <li><cite></cite><a href="#">信息列表</a><i></i></li>
        <li><cite></cite><a href="#">其他</a><i></i></li>-->
        </ul>
    </dd>


    <dd><div class="title"><span><img src="../../images/leftico04.png" /></span>活动管理</div>
        <ul class="menuson">
            <li><cite></cite><a href="/activity/activityList" target="rightFrame">活动列表</a><i></i></li>
            <#--<li><cite></cite><a href="#">常用资料</a><i></i></li>
            <li><cite></cite><a href="#">信息列表</a><i></i></li>
            <li><cite></cite><a href="#">其他</a><i></i></li>-->
        </ul>

    </dd>

    <dd><div class="title"><span><img src="../../images/leftico04.png" /></span>订单管理</div>
        <ul class="menuson">
            <li><cite></cite><a href="/order/orderList" target="rightFrame">订单列表</a><i></i></li>
        <#--<li><cite></cite><a href="#">常用资料</a><i></i></li>
        <li><cite></cite><a href="#">信息列表</a><i></i></li>
        <li><cite></cite><a href="#">其他</a><i></i></li>-->
        </ul>

    </dd>

</dl>
</body>
</html>