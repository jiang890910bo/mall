<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加用户</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<script type="text/javascript" src="../../../js/dialogPlugin.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">用户列表</a></li>
        <li><a href="#">查看产品</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>产品基本信息</span></div>
        <ul class="forminfo">
            <li><label>所属目录<b class="mustfill">*</b></label>
                <input id="name" name="name" type="text" class="dfinput" maxlength="30" value="${category.name}"/>
            </li>
            <li><label>产品名称<b class="mustfill">*</b></label><input id="name" name="name" type="text" value="${product.name}" class="dfinput" maxlength="30" /></li>
            <li><label>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格<b class="mustfill">*</b></label><input id="price" name="price" value="${product.price}" type="text" class="dfinput" maxlength="10" /></li>
            <li><label>秒杀价格<b class="mustfill">*</b></label><input id="seckillPrice" name="seckillPrice" value="${product.seckillPrice}" type="text" class="dfinput" maxlength="10" /></li>
            <li><label>库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存<b class="mustfill">*</b></label><input id="stock" name="stock" type="text" value="${product.stock}" class="dfinput" maxlength="100"/></li>
            <li><label>副&nbsp;&nbsp;标&nbsp;&nbsp;题<b class="mustfill">*</b></label><input id="subTitle" name="subTitle" type="text" value="${product.subTitle}" class="dfinput" maxlength="20"/></li>
            <li><label>图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片<b class="mustfill">*</b></label>
                <input id="name" name="name" type="text" class="dfinput" maxlength="30" value="${image.name}"/>
            </li>
            <li>
                <label>预&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;览</label>
                <img src="${image.subImageUrl}"/>
            </li>
            <li><label>详情描述<b class="mustfill">*</b></label><textarea id="detail" name="detail"  style="border: 1px solid gray;" rows="5" cols="100"  maxlength="200">${product.detail}</textarea></li>

            <li><label>&nbsp;</label><input id="submitButton" onclick="javascript:window.location.href='/product/productList';" type="button" class="btn" value="确认返回"/></li>
        </ul>

</div>

</body>
</html>