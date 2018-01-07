<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商品目录信息</title>
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
    <div class="formtitle"><span>目录基本信息</span></div>
    <ul class="forminfo">
        <li><label>父目录名称</label><input type="text" value="${category.parent?default('无') }" class="dfinput" /></li>
        <li><label>目录名称</label><input type="text" value="${category.name}" class="dfinput" /></li>
        <li><label>排序值</label><input type="text" value="${category.sortOrder}" class="dfinput" /></li>
        <li><label>状&nbsp;&nbsp;&nbsp;态</label><input type="text"
                                                            <#list dataStatusMap.keySet() as status>
                                                                <#if status == category.status>
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
        window.location.href="/category/categoryList";
    });

</script>
</html>