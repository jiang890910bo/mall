<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加活动</title>
</head>
<link href="../../../css/style.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="../../../css/jquery.datetimepicker.css"/>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<script type="text/javascript" src="../../../js/dialogPlugin.js"></script>
<script type="application/javascript">
    var $ = $.noConflict();
</script>

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
    <div class="formtitle"><span>编辑活动基本信息</span></div>
    <form id="addActivityForm" action="#" method="post">
        <input type="hidden" name="id" value="${activity.id}"/>
        <input type="hidden" id="startTime" name="startTime" value="${activity.startTime}"/>
        <input type="hidden" id="endTime" name="endTime" value="${activity.endTime}"/>
        <ul class="forminfo">
            <li><label>名称</label><input id="name" name="name" type="text" value="${activity.name}" class="dfinput"
                                                                 maxlength="30"/></li>
            <li><label>开始时间</label><input type="text" id="startTime1"  value="${activity.startTime?string('yyyy-MM-dd HH:mm')}"
                                                                   class="dfinput"/></li>
            <li><label>结束时间</label><input type="text" id="endTime1" value="${activity.endTime?string('yyyy-MM-dd HH:mm')}"
                                                                   class="dfinput"/></li>
            <li><label>&nbsp;</label><input id="submitButton" type="button" class="btn" value="确认保存"/></li>
        </ul>
    </form>

</div>


<!--提示信息弹出框-->
<div class="warn_tip">
    <div class="warn_tiptop"><span>警告信息</span><a></a></div>
    <div class="tipinfo">
        <div class="tipright">
            <p id="message">是否确认对信息的修改 ？</p>
        </div>
    </div>

    <div class="tipbtn">
        <input name="" type="button" class="sure" value="确定"/>
    <#--<input name="" type="button"  class="cancel" value="取消" />-->
    </div>
</div>
</body>
<script src="../../../js/jquery.datetimepicker.js"></script>
<script type="application/javascript">

    $('#startTime1').datetimepicker({
        lang: 'ch',
        mask: '${activity.startTime?string('yyyy-MM-dd HH:mm')}'
    });
    $('#endTime1').datetimepicker({
        lang: 'ch',
        mask: '${activity.endTime?string('yyyy-MM-dd HH:mm')}'
    });

    var not_null_regex = /\S+/;

    //检查表单
    $("#submitButton").click(function () {
        var name = $("#name").val();
        var startTime = $("#startTime1").val();
        var endTime = $("#endTime1").val();
        var nowTime = new Date();

        if (!not_null_regex.test(name)) {
            $("#message").text("名称不能为空!");
            showTip();
            return;
        }

        if (!not_null_regex.test(startTime) || startTime.indexOf("_") != -1) {
            $("#message").text("请选择活动开始时间!");
            showTip();
            return;
        } else if (startTime < nowTime) {
            $("#message").text("活动开始时间必须大于当前时间");
            showTip();
            return;
        }
        if (!not_null_regex.test(endTime) || endTime.indexOf("_") != -1) {
            $("#message").text("请选择活动结束时间!");
            showTip();
            return;
        } else if (endTime <= startTime) {
            $("#message").text("结束时间必须大于开始时间!");
            showTip();
            return;
        }

        $("#startTime").val(new Date(startTime));
        $("#endTime").val(new Date(endTime));
        $("#submitButton").attr("disabled", "disabled");
        var data = $("#addActivityForm").serializeArray();
        $.ajax({
            url: "/activity/updateActivity",
            dataType: "json",
            type: "post",
            data: data,
            success: function (res) {
                if (res.status == 0) {
                    zdalert("提示信息", "操作成功！", function () {
                        window.location.href = "/activity/activityList";
                    });
                } else {
                    zdalert("错误信息", res.errorMsg);
                    $("#submitButton").removeAttr("disabled");
                }

            },
            error: function (res) {
                console.log(res);
            }
        });
    });

</script>
</html>