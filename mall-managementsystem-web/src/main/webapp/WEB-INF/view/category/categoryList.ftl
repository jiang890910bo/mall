<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商品目录列表</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
    $(document).ready(function(){
        $(".click").click(function(){
            $(".tip").fadeIn(150);
        });

        $(".tiptop a").click(function(){
            $(".tip").fadeOut(150);
        });

        $(".sure").click(function(){
            $(".tip").fadeOut(100);
        });

        $(".cancel").click(function(){
            $(".tip").fadeOut(100);
        });

    });
</script>


</head>


<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">商品目录管理</a></li>
        <li><a href="#">商品目录列表</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="tools">

        <ul class="toolbar">
            <li ><a href="/category/toAddCategory"><span><img src="../../../images/t01.png" /></span>添加</li></a>
            <#--<li class="click"><span><img src="../../../images/t02.png" /></span>修改</li>-->
            <#--<li><span><img src="../../../images/t03.png" /></span>删除</li>-->
            <#--<li><span><img src="../../../images/t04.png" /></span>统计</li>-->
        </ul>
        <#--<ul class="toolbar1">
            <li><span><img src="../../../images/t05.png" /></span>设置</li>
        </ul>-->
    </div>

    <table class="tablelist">
        <thead>
        <tr>
            <#--<th><input name="" type="checkbox" value="" /></th>-->
            <th>编号<i class="sort"><img src="../../../images/px.gif" /></i></th>
            <th>目录名称</th>
            <th>排序值</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list pageInfo.list as category>
        <tr>
            <#--<td><input name="" type="checkbox" value="${category.id}" /></td>-->
            <td>${category.id}</td>
            <td>${category.name}</td>
            <td>${category.sortOrder}</td>
            <td>
                <#list dataStatusMap ? keys as key>
                    <#if key == category.status>
                        ${dataStatusMap["${key}"]}
                    </#if>
                </#list>
            </td>
            <td>${category.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
            <td><a href="/category/getCategory/${category.id}" class="tablelink">查看</a>/
                <a href="javascript:void(0);" onclick="deleteUser(${category.id})" >
                        <#if category.status == 1>
                            <a href="/category/abandonCategory/${category.id}" class="tablelink">禁用</a>
                        <#elseif category.status == 2>
                            <a href="/category/enableCategory/${category.id}" class="tablelink">启用</a>
                        </#if>
                </a>/
                <a href="/category/toEditCategory/${category.id}" class="tablelink">编辑</a></td>
        </tr>
        </#list>
        </tbody>
    </table>


    <div class="pagin">
        <div class="message">共<i class="blue">${pageInfo.total}</i>条记录&nbsp;&nbsp;
            当前第&nbsp;<i class="blue">${pageInfo.pageNum}&nbsp;</i>页&nbsp;&nbsp;
            共<i class="blue">${pageInfo.pages}</i>页&nbsp;&nbsp;
        ${pageInfo.pageSize}条/页
        </div>
        <ul class="paginList">
            <hidden id="pageSize" value="${pageInfo.pageSize}"/>
        <#if pageInfo.pageNum != 1>
            <li class="paginItem"  data-id="${pageInfo.pageNum-1}"><a href="javascript:;"><span class="pagepre "></span></a></li>
        </#if>
        <#if pageInfo.pageNum != pageInfo.pages>
            <li class="paginItem"  data-id="${pageInfo.pageNum+1}"><a href="javascript:;"><span class="pagenxt"></span></a></li>
        </#if>
        </ul>
    </div>


    <div class="tip">
        <div class="tiptop"><span>提示信息</span><a></a></div>

        <div class="tipinfo">
            <span><img src="../../../images/ticon.png" /></span>
            <div class="tipright">
                <p>是否确认对信息的修改 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>

        <div class="tipbtn">
            <input name="" type="button"  class="sure" value="确定" />&nbsp;
            <input name="" type="button"  class="cancel" value="取消" />
        </div>
    </div>

</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');

    $(".paginItem").click(function(){
        var targetPageNum = $(this).attr("data-id");
        var pageSize = $("#pageSize").val();
        window.location.href="/category/categoryList?pageIndex="+targetPageNum+"&pageSize="+pageSize;
    });
</script>
</body>
</html>