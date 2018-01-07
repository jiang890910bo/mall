<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加活动</title>
</head>
<link href="../../../css/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../../../js/jquery.js"></script>

<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">活动列表</a></li>
        <li><a href="#">活动信息</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>活动基本信息</span></div>
    <form id="addActivityForm" action="#" method="post">
        <ul class="forminfo">
            <li><label>名称</label><input id="name" name="name" type="text" value="${activity.name}" class="dfinput"
                                                                 maxlength="30"/></li>
            <li><label>开始时间</label><input type="text" id="startTime" name="startTime" value="${activity.startTime?string('yyyy-MM-dd hh:mm')}"
                                                                   class="dfinput"/></li>
            <li><label>结束时间</label><input type="text" id="endTime" name="endTime" value="${activity.endTime?string('yyyy-MM-dd hh:mm')}"
                                                                   class="dfinput"/></li>
            <li><label>活动状态</label>
                <input type="text"
                       value="${activityStatusMap["${activity.activityStatus}"]}"
                       class="dfinput"/>
            </li>
            <li><label>状&nbsp;&nbsp;&nbsp;态</label><input type="text"
                       value="${dataStatusMap["${activity.status}"]}"
                  class="dfinput"/>
            <li><label>创建时间</label><input type="text" id="endTime" name="endTime" value="${activity.createTime?string('yyyy-MM-dd hh:mm:ss')}"
                                          class="dfinput"/></li>
            <li><label>&nbsp;</label><input id="submitButton" onclick="javascript:window.location.href='/activity/activityList';" type="button" class="btn" value="确认返回"/></li>
        </ul>
    </form>

</div>

</body>

</html>