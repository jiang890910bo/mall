<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试首页</title>
</head>
<body>
用户列表展示：</br>
<#list  userList as user>
username : ${user.name}<br/>
password : ${user.password}
</#list>
</body>
</html>