<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登陆页</title>
</head>
<title>欢迎登录后台管理系统</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script src="../../js/cloud.js" type="text/javascript"></script>

<script language="javascript">
    $(function(){
        $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
        $(window).resize(function(){
            $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
        })
    });
</script>

</head>

<body style="background-color:#1c77ac; background-image:url(../../images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>


<div class="logintop">
    <span>欢迎登录后台管理界面平台</span>
    <ul>
        <li><a href="#">回首页</a></li>
        <li><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
    </ul>
</div>

<div class="loginbody">

    <span class="systemlogo"></span>

    <div class="loginbox">
        <form id="checkForm" action="#" method="post">
        <ul>
            <li><input id="loginName" type="text" class="loginuser" value="" title="登陆账号" /></li>
            <li><input id="password" type="password" class="loginpwd" value="" title="登陆密码"/></li>
            <li><b id="errorMsg" style="color: red;"></b></li>
            <li><input id="submitButton" type="button" class="loginbtn" value="登录"   /><label><input name="" type="checkbox" value="" checked="checked" />记住密码</label><label><a href="#">忘记密码？</a></label></li>
        </ul>
        </form>

    </div>

</div>



<div class="loginbm">版权所有  2013  .com 仅供学习交流，勿用于任何商业用途</div>
</body>
<script type="application/javascript">
    var not_null_regex = /\S+/;

    $("#submitButton").click(function(){
        var loginName = $("#loginName").val();
        var password = $("#password").val();


        if(!not_null_regex.test(loginName) &&  !not_null_regex.test(password)){
            $("#errorMsg").text("请输入登录名和密码");
            return ;
        }else if(!not_null_regex.test(loginName)){
            $("#errorMsg").text("请输入登录名");
            return ;
        }else if(!not_null_regex.test(password)){
            $("#errorMsg").text("请输入密码");
            return ;
        }else{
            $("#errorMsg").text("");
            $.ajax({
                url:"/login/checkLogin",
                dateType:"json",
                type:"post",
                data:{loginName:loginName,password:password},
                success:function(res){
                    if(res.status!= 0){
                        $("#errorMsg").text(res.errorMsg);
                    }else if(res.status == 0){
                        window.location.href="/index/main";
                    }

                },
                error:function(res){
                    console.log(res);
                }

            });
        }

    });
</script>
</html>