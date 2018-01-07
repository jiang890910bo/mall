<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加活动</title>
</head>
<link href="../../../css/style.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="../../../css/jquery.datetimepicker.css"/>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<script type="application/javascript">
    var $ = $.noConflict();
</script>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">活动列表</a></li>
        <li><a href="#">添加活动</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>活动基本信息</span></div>
    <form id="addActivityForm" action="#" method="post">
        <ul class="forminfo">
            <li><label>名称<b class="mustfill">*</b></label><input id="name" name="name" type="text" class="dfinput"
                                                                 maxlength="30"/><i>标题不能超过30个字符</i></li>
            <li><label>开始时间<b class="mustfill">*</b></label><input type="text" id="startTime" name="startTime"
                                                                   class="dfinput"/></li>
            <li><label>结束时间<b class="mustfill">*</b></label><input type="text" id="endTime" name="endTime"
                                                                   class="dfinput"/></li>
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
            <input name="" type="button" class="sure" value="确定"/>
        <#--<input name="" type="button"  class="cancel" value="取消" />-->
        </div>
    </div>
</div>

</body>
<script src="../../../js/jquery.datetimepicker.js"></script>
<script type="application/javascript">

    $('#startTime').datetimepicker({
        lang: 'ch',
        mask: '9999/19/39 29:59'
    });
    $('#endTime').datetimepicker({
        lang: 'ch',
        mask: '9999/19/39 29:59'
    });

    var not_null_regex = /\S+/;

    //检查表单
    $("#submitButton").click(function () {
        var flag = true;
        var name = $("#name").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
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

        $("#submitButton").attr("disabled", "disabled");
        var data = $("#addActivityForm").serializeArray();
        $.ajax({
            url: "/activity/addActivity",
            dataType: "json",
            type: "post",
            data: data,
            success: function (res) {
                if (res.status == 0) {
                    window.location.href = "/activity/activityList";
                } else {
                    $("#message").text(res.errorMsg);
                    showTip();
                    $("#submitButton").removeAttr("disabled");
                }

            },
            error: function (res) {
                console.log(res);
            }
        });
    });

    //显示提示
    function showTip() {
        $(".warn_tip").show();
    }

    //隐藏提示
    $(".warn_tiptop a").click(function () {
        $(".warn_tip").fadeOut(100);
    });

    //隐藏提示
    $(".sure").click(function () {
        $(".warn_tip").fadeOut(100);
    });
</script>
</html>