<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户信息</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">用户列表</a></li>
        <li><a href="#">用户详情</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>用户基本信息</span></div>
    <ul class="forminfo">
        <li><label>登录名</label><input type="text" value="${user.name}" class="dfinput" /></li>
        <li><label>密&nbsp;&nbsp;&nbsp;码</label><input type="text" value="*********" class="dfinput" /></li>
        <li><label>邮&nbsp;&nbsp;&nbsp;箱</label><input type="text" value="${user.email}" class="dfinput" /></li>
        <li><label>电&nbsp;&nbsp;&nbsp;话</label><input type="text" value="${user.mobile}" class="dfinput" /></li>
        <li><label>角&nbsp;&nbsp;&nbsp;色</label><input type="text"

                                                            <#list roleMap? keys as role>
                                                                <#if role == user.role>
                                                                        value="${roleMap["${role}"]}"
                                                                </#if>
                                                            </#list>
                                                             class="dfinput"/>
        </li>
        <li><label>找回密码的问题</label><input type="text"value="${user.question}" class="dfinput" /></li>
        <li><label>找回密码的答案</label><input type="text"value="******" class="dfinput" /></li>
        <li><label>状&nbsp;&nbsp;&nbsp;态</label><input type="text"
                                                            <#list dataStatusMap.keySet() as status>
                                                                <#if status == user.status>
                                                                    value="${dataStatusMap.get(status)}"
                                                                </#if>
                                                            </#list>
                                                            class="dfinput"/>
        </li>
        <li><label>&nbsp;</label><input id="backButton" type="button" class="btn" value="确认返回"/></li>
    </ul>

</div>

</body>
<script type="application/javascript">
    $("#backButton").click(function(){
        window.location.href="/user/userList";
    });

</script>
</html>