<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录页面 </title>
</head>

<body>
<link rel="stylesheet" type="text/css" href="../../css/nav_top.css">
<link rel="stylesheet" type="text/css" href="../../css/register_page.css" />
<style type="text/css">
    .btn_tab_login{float: right; margin-top: 48px;}
    .btn_tab_login li{display: inline-block; margin-left:30px; font-size: 14px;}
    .btn_tab_login li.cur a{color:#d00;}
</style>
<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<style type="text/css">
    #weixin_login_container iframe{
        width:158px;
        height:158px;
    }
</style>
<div id="top">
    <div id="top_main">

        <div id="hello">
					<span>您好，欢迎来到商城！
			        <a href="/index/main">[首页]</a>|
                    <#if user??>
                        <a href="/login/exit">[退出]</a>
                    <#else>
                        <a href="/login/toLogin">[登陆]</a>
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


<div class="login-wrap">
    <div class="wrap clearfix">
        <!-- -快捷登录 -->
        <div class="register form-box fr shortLogin" style="display:block;">
            <h5 class="title">注册登录</h5>
            <div class="form-con">
                <form id="mobileLoginForm" method="post" action="#">
                    <div class="form-error" style=""><i></i><label class="text"></label></div>
                    <dl class="clearfix">
                        <dt>用&nbsp;&nbsp;户&nbsp;&nbsp;名：</dt>
                        <dd><input name="name" type="text" id="name" autocomplete="off" class="input-text mobile" maxlength="50" onblur="nameCheck();" ><span class="placeholder" ></span></dd>
                    </dl>
                    <dl class="clearfix">
                        <dt>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</dt>
                        <dd><input name="password" type="password" id="password" autocomplete="off" class="input-text mobile" maxlength="30" onblur="passwordCheck();"><span class="placeholder"></span></dd>
                    </dl>
                    <dl class="clearfix">
                        <dt>确认密码：</dt>
                        <dd><input name="affirmPassword" type="password" id="affirmPassword" autocomplete="off" class="input-text mobile" maxlength="30" onblur="passwordAgainCheck();"><span class="placeholder"></span></dd>
                    </dl>
                    <dl class="clearfix">
                        <dt>手&nbsp;&nbsp;机&nbsp;&nbsp;号：</dt>
                        <dd><input name="mobile" type="text" id="mobile" autocomplete="off" class="input-text mobile" maxlength="11" onblur="mobileCheck();"><span class="placeholder"></span></dd>
                    </dl>
                    <dl class="clearfix">
                        <dt>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</dt>
                        <dd><input name="email" type="text" id="email" autocomplete="off" class="input-text mobile" maxlength="100" onblur="emailCheck();"><span class="placeholder"></span></dd>
                    </dl>
                    <dl class="clearfix">
                        <dt>找回密码的问题：</dt>
                        <dd><input name="question" type="text" id="question" autocomplete="off" class="input-text mobile" maxlength="200"><span class="placeholder"></span></dd>
                    </dl>
                    <dl class="clearfix">
                        <dt>找回密码的答案：</dt>
                        <dd><input name="answer" type="text" id="answer" autocomplete="off" class="input-text mobile" maxlength="200"><span class="placeholder"></span></dd>
                    </dl>
                    <div class="btn-box clearfix">
                        <input id="partnerSubmit" class="btn-settlement" type="button" value="注    册" tjjj="passport.quick.button.login">
                    </div>
                    <div class="link-box clearfix">
                        <a href="/login/toLogin" class="backLogin">返回账号登录>></a>
                    </div>
                </form>
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
    var phone_reg = /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/;
    var email_reg = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/;
    var num_reg =/\d+/;
    var word_reg = /[a-z]+/;//密码由6-21字母和数字组成

    var not_null_regex = /\S+/;

    function nameCheck(){
        if(!not_null_regex.test($("#name").val())){
            showError("姓名不能为空");
            return false;
        }else{
            checkNameExist();
        }
        closeError();
        return true;
    }

    function passwordCheck(){
        var paasword = $("#password").val();
        if(!not_null_regex.test(paasword)){
            showError("密码不能为空");
            return false;
        }else if(paasword.length < 6 || paasword.length > 30){
            showError("密码至少6位,最多30位");
            return false;
        }else if(!num_reg.test(paasword) || !word_reg.test(paasword)){
            showError("密码由6-21字母和数字组成");
            return false;
        }
        closeError();
        return true;
    }

    function passwordAgainCheck(){
        if(!not_null_regex.test($("#paasword").val())){
            showError("确认密码不能为空");
            return false;
        }else if($("#affirmPassword").val() != $("#password").val()){
            showError("确认密码必须与密码一致");
            return false;
        }
        closeError();
        return true;
    }

    function mobileCheck(){
        if(!not_null_regex.test($("#mobile").val())){
            showError("手机号不能为空");
            return false;
        }else if(!phone_reg.test($("#mobile").val())){
            showError("请填写正确的手机号");
            return false;
        }else{
            checkMobileExist();
        }
        closeError();
        return true;
    }

    function emailCheck(){
        if(!not_null_regex.test($("#email").val())){
            showError("邮箱不能为空");
            return false;
        }else if(!email_reg.test($("#email").val())){
            showError("请填写正确的邮箱");
            return false;
        }else{
            checkEmailExist();
        }
        closeError();
        return true;
    }

    //检查用户名是否存在
    function checkNameExist(){
        var loginName = $("#name").val();
            $.ajax({
                url:"/login/checkLoginNameOrMobileOrEmailExist",
                dateType:"json",
                type:"post",
                data:{userName:encodeURI(loginName)},
                success:function(res){
                    if(res.status != 0){
                        showError("抱歉，该注册账号已存在!");
                        return false;
                    }else{
                        return true;
                    }
                },
                error:function(res){
                    console.log(res);
                }

            });
    }

    //检查邮箱是否存在
    function checkEmailExist(){
        var email = $("#email").val();
            $.ajax({
                url:"/login/checkLoginNameOrMobileOrEmailExist",
                dateType:"json",
                type:"post",
                data:{email:email},
                success:function(res){
                    if(res.status != 0){
                        showError("抱歉，该邮箱已存在!");
                        return false;
                    }else{
                        return true;
                    }
                },
                error:function(res){
                    console.log(res);
                }

            });
    }

    //检查手机号是否存在
    function checkMobileExist(){
        var mobile = $("#mobile").val();
            $.ajax({
                url:"/login/checkLoginNameOrMobileOrEmailExist",
                dateType:"json",
                type:"post",
                data:{mobile:mobile},
                success:function(res){
                    if(res.status != 0){
                        showError("抱歉，该手机号已存在!");
                        return false;
                    }else{
                        return true;
                    }
                },
                error:function(res){
                    console.log(res);
                }

            });
    }

    $("#partnerSubmit").click(function(){
        var flag = true;
        if(!nameCheck()){
            flag = false;
        }
        if(!passwordCheck()){
            flag = false;
        }
        if(!passwordAgainCheck()){
            flag = false;
        }
        if(!mobileCheck()){
            flag = false;
        }
        if(!emailCheck()){
            flag = false;
        }

        if(flag){
            $.ajax({
                url:'/login/register',
                dataType:'json',
                type:'post',
                data:$("#mobileLoginForm").serializeArray(),
                success:function(res){
                    if(res.status == 0){
                        window.location.href="/login/toLogin";
                    }else{
                        showError(res.errorMsg);
                        return;
                    }
                },
                error:function(res){
                    console.log(res);
                }
            });
        }
    });

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
</script>