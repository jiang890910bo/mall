<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录页面 </title>
</head>

<body>

<link rel="stylesheet" type="text/css" href="../../css/login_page.css" />
<link rel="stylesheet" type="text/css" href="../../css/nav_top.css">
<style type="text/css">
    .btn_tab_login{float: right; margin-top: 48px;}
    .btn_tab_login li{display: inline-block; margin-left:30px; font-size: 14px;}
    .btn_tab_login li.cur a{color:#d00;}
</style>
<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<div id="top">
    <div id="top_main">

        <div id="hello">
					<span>您好，欢迎来到商城！
			        <a href="/index/main">[首页]</a>|
                    <#if user??>
                        <a href="/login/exit">[退出]</a>
                    <#else>
                        <a href="/login/toRegister">[注册]</a>
                    </#if>
			</span>
        </div>
        <div class="topright">
            <ul>
                <li>
                    <div class="cun">
                        <a href="#">我的订单</a>
                    </div>
                </li>

            </ul>
        </div>
    </div>
</div>


<style type="text/css">
    #weixin_login_container iframe{
        width:158px;
        height:158px;
    }
</style>

<div class="login-wrap">
    <div class="wrap clearfix">

        <div class="form-box fr loginV2"  style="display:block;">
            <ul class="form-tab clearfix">
                <#--<li class="tab-li"><a href="javascript:;" tjjj="passport.login_type.wixin_qrcode">微信登录<i class="icon"></i></a></li>-->
                <li class="tab-li cur"><a href="javascript:;" tjjj="passport.login_type.login_name">账号登录</a></li>
            </ul>
            <div class="form-con">
                <div class="weixin-login" style="display:none;">
                    <div class="wx-box clearfix">
                        <a href="javascript:;" class="wx-img-box">
                            <img class="wx-qrCode" src="../../img/ewm.jpg" id="qrCodeImg">
                            <img class="wx-qrCode-logo" src="../../img/ewm.jpg" id="qrCodeLogo">
                            <img class="statusImg" src="../../img/wx-confirm.png" id="statusImg">
                            <div id="weixin_login_container" style="display:none;"></div>
                        </a>
                        <img src="../../img/wx-image.png" class="wx-image">
                    </div>
                    <p class="wx-text">微信扫一扫  快速登录</p>
                    <p class="wx-help"><a href="javascript:;" class="help-a">如何使用？</a></p>
                </div>
                <div class="login-normal" style="display:block;">
                    <form id="loginForm" method="post" autocomplete="off" action="#">
                        <div class="form-error" style=""><i></i><label class="text"></label></div>
                        <dl class="clearfix">
                            <dt>账户名：</dt>
                            <dd><input type="text" name="loginName" id="loginName" class="input-text" autocomplete="off"  onkeyup="showLoginNameTip()" value="${name? default('')}" /><span class="placeholder" id="loginNameTip">用户名/邮箱/手机号</span></dd>
                        </dl>
                        <dl class="top1 clearfix">
                            <dt>密<em></em>码：</dt>
                            <dd><input type="password" name="password" id="password" class="input-text"  autocomplete="off"  onkeyup="showPasswordTip()"  value="${password? default('')}"/><span class="placeholder" id="passwordTip">请输入密码</span></dd>
                        </dl>

                        <div class="form-remember">

                            <input name="rememberName" type="checkbox" id="remUser" class="rem-check" style="display:none;" checked="checked">
                            <#--<span class="rem-box rem-box-r memCheck"><input name="rememberMe" type="checkbox" id="remLogin" class="rem-check">三个月之内免登录</span>-->
                        </div>
                        <div class="btn-box clearfix">
                            <input id="normalSubmit" class="btn-settlement" type="button" onclick="login()" value="登    录"  tjjj="passport.button.login">

                        </div>
                        <div class="link-box clearfix">
                            <a href="/login/toRegister" class="register" tjjj="passport.login.fstreg">新用户注册</a>
                            <#--<a href="javascript:;" class="forget-pass" tjjj="passport.forget.password">忘记密码？</a>-->
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>


<div id="jia_footer">
    <div class="jia_foot_info">
        <div class="jia_foot_con">
            <p class="jia_foot_link">
                <a href="#" rel="nofollow" target="_blank">关于我们</a><span class="jia_split">|</span> <a href="#" target="_blank" rel="nofollow">联系我们</a><span class="jia_split">|</span> <a href="#" target="_blank" rel="nofollow">媒体报道</a><span class="jia_split">|</span> <a href="#" target="_blank" rel="nofollow">法律声明</a><span class="jia_split">|</span> <a href="javascript:;/help/0002.html" target="_blank" rel="nofollow">企业文化</a><span class="jia_split">|</span> <a href="javascript:;/link.html" target="_blank" rel="nofollow">友情链接</a><span class="jia_split">|</span> <a href="javascript:;/jmtg/index.html" target="_blank" rel="nofollow">加盟齐家</a><span class="jia_split">|</span> <a href="javascript:;/zhaoshang/" tjjj="sjrz.2" target="_blank" rel="nofollow">入驻齐家</a><span class="jia_split">|</span> <a href="javascript:;/help/0055.html" target="_blank" rel="nofollow">诚聘英才</a><span class="jia_split">|</span> <a href="javascript:;/help/0033.html" target="_blank">网站地图</a><span class="jia_split">|</span> <a href="javascript:;/app/" rel="nofollow" target="_blank">手机齐家</a><span class="jia_split">|</span> <a href="#" target="_blank" rel="nofollow">钱包</a><span class="jia_split">|</span> <a href="javascript:;/help/" tjjj="bottom.link.help">帮助中心</a><span class="jia_split">|</span> <a href="javascript:;" class="jia_foot_open">更多<i></i></a>
            </p>
            <p class="jia_foot_link footnone">
                <a href="#" target="_blank" tjjj="footer.bottom.1">找装修公司</a><span class="jia_split">|</span> <a href="#" target="_blank" tjjj="footer.bottom.2">买建材家居</a>
            </p>
        </div>
        <p>
            版权所有Copyright ? 2005-2016 www.17sucai.com All rights reserved
        </p>
        <p>
            沪ICP备xxxxxx号 沪B2-xxxx 组织机构代码证：xxxxx—1
        </p>
        <p>
            中国互联网协会信用评价中心网信认证 网信编码:xxxxxx1
        </p>

        <p>
            <a href="javascript:;">
                <img src="../../img/gov-inco.jpg" border="0" width="40" height="44">
            </a>
            <a href="javascript:;/315/" target="_blank" style="margin-left:15px;">
                <img src="../../img/315.gif" border="0">
            </a>
            <a style="margin-left:15px;" target="_blank" href="javascript:;">
                <img border="0" src="../../img/jb.jpg">
            </a>
        </p>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(".input-text").val("");

    function login(){
        var dataJson = $("#loginForm").serializeArray();
        var flag = true;
        $("#normalSubmit").val("正在登录...");
        $.ajax({
            url:"/login/login",
            dateType:"json",
            type:"post",
            data:dataJson,
            success:function(res){
                if(res.status == 0){
                    $("#normalSubmit").val("登    录");
                    window.location.href="/index/main";
                }else{
                    showError(res.errorMsg);
                    return false;
                }
            },
            error:function(res){
                console.log(res);
            }

        });
    }

    //开启错误提示
    function showError(error){
        $(".form-error").find("label").html(error);
        $(".form-error").show();
    }
    //关闭错误
    function closeError(){
        $(".form-error").find("label").html('');
        $(".form-error").hide();
    }


    function showLoginNameTip(){
        var loginName = $("#loginName").val();
        if(loginName.length > 0){
            $("#loginNameTip").text("");
        }else{
            $("#loginNameTip").text("用户名/邮箱/手机号");
        }
    }

    function showPasswordTip(){
        var password = $("#password").val();
        if(password.length > 0){
            $("#passwordTip").text("");
        }else{
            $("#passwordTip").text("请输入密码");
        }
    }

</script>