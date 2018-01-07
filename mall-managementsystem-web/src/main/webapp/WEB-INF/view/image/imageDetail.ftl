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
        <li><a href="#">图片列表</a></li>
        <li><a href="#">图片详情</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>图片基本信息</span></div>
    <ul class="forminfo">
        <li><label>名称</label><input type="text" value="${image.name}" class="dfinput" /></li>
        <li><label>小图片web地址</label><input type="text" value="${image.subImageUrl}" class="dfinput" /></li>
        <li><label>小图片</label><img src="${image.subImageUrl}"/></li>
        <li><label>大图片web地址</label><input type="text" value="${image.mainImageUrl}" class="dfinput" /></li>
        <li><label>大图片</label><img src="${image.mainImageUrl}"/></li>

        <li><label>&nbsp;</label><input id="backButton" type="button" class="btn" value="确认返回"/></li>
    </ul>

</div>

</body>
<script type="application/javascript">
    $("#backButton").click(function(){
        window.location.href="/image/imageList";
    });

</script>
</html>