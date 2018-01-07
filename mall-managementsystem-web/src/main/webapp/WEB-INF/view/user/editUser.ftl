<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加用户</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">用户列表</a></li>
        <li><a href="#">添加用户</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>用户基本信息</span></div>
    <form id="addUserForm" action="#" method="post">
        <input type="hidden" name="id" value="${user.id}"/>
    <ul class="forminfo">
        <li><label>登录名</label><b>${user.name}</b></li>
        <li><label>密&nbsp;&nbsp;&nbsp;码<b class="mustfill">*</b></label><input id="password" name="password" type="password" value="${user.password}" class="dfinput" maxlength="10" /><i>最少六个字符</i></li>
        <li><label>邮&nbsp;&nbsp;&nbsp;箱</label><input id="email" name="email" type="text" value="${user.email}" class="dfinput" maxlength="100"/></li>
        <li><label>电&nbsp;&nbsp;&nbsp;话<b class="mustfill">*</b></label><input id="mobile" name="mobile" type="text" value="${user.mobile}" class="dfinput" maxlength="20"/></li>
        <li><label>角&nbsp;&nbsp;&nbsp;色</label>
        <select id="role" name="role" class="dfinput" >
        <#list roleMap ? keys as key>
            <option value="${key}"    <#if key == user.role> selected="selected"</#if>  >${roleMap["${key}"]}</option>
        </#list>
        </select>
        </li>
        <li><label>找回密码的问题<b class="mustfill">*</b></label><input id="question" name="question" type="text" value="${user.question}" class="dfinput" maxlength="50"/></li>
        <li><label>找回密码的答案<b class="mustfill">*</b></label><input id="answer" name="answer" type="text" value="${user.answer}"  class="dfinput" maxlength="50"/></li>
        <li><label>&nbsp;</label><input id="submitButton" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form>

    <!--提示信息弹出框-->
    <div class="warn_tip">
        <div class="warn_tiptop"><span>警告信息</span><a></a></div>
        <div class="tipinfo">
            <div class="tipright">
                <p id="message">是否确认对信息的修改 ？</p>
            </div>
        </div>

        <div class="tipbtn">
            <input name="" type="button"  class="sure" value="确定" />
            <#--<input name="" type="button"  class="cancel" value="取消" />-->
        </div>
    </div>
</div>

</body>
<script type="application/javascript">
    var not_null_regex = /\S+/;

     //检查表单
    $("#submitButton").click(function (){
        var flag = true;
        var password = $("#password").val();
        var mobile = $("#mobile").val();
        var role = $("#role").val();
        var question = $("#question").val();
        var answer = $("#answer").val();

        var mobile_regex = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;

        if(!not_null_regex.test(password)){
            $("#message").text("登录名或密码不能为空!");
            showTip();
            return;
        }else if(password.length < 6){
            $("#message").text("密码不能少于6位字符!");
            showTip();
            return;
        }
        if(!not_null_regex.test(mobile)){
            $("#message").text("电话号码不能为空!");
            showTip();
            return ;
        }else if(!mobile_regex.test(mobile)){
            $("#message").text("电话号码格式错误!");
            showTip();
            return ;
        }

        if(!not_null_regex.test(question)){
            $("#message").text("找回密码的问题不能为空!");
            showTip();
            return ;
        }
        if(!not_null_regex.test(answer)){
            $("#message").text("找回密码的答案不能为空!");
            showTip();
            return ;
        }

        $("#submitButton").attr("disabled", "disabled");
        if (flag) {
            var data = $("#addUserForm").serializeArray();
            $.ajax({
                url: "/user/editUser",
                dataType: "json",
                type: "post",
                data: data,
                success: function (res) {
                    if(res.status == 0){
                        window.location.href="/user/userList";
                    }else{
                        $("#message").text(res.errorMsg);
                        showTip();
                        return;
                    }

                },
                error: function (res) {
                    console.log(res);
                }
            });
            $("#butsubmit").removeAttr("disabled");
        }

    });

    //显示提示
    function showTip(){
        $(".warn_tip").show();
    }

    //隐藏提示
    $(".warn_tiptop a").click(function(){
        $(".warn_tip").fadeOut(100);
    });

    //隐藏提示
    $(".sure").click(function(){
        $(".warn_tip").fadeOut(100);
    });
</script>
</html>