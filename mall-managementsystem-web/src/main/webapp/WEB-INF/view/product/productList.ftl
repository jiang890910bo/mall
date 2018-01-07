<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品列表</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<script type="text/javascript" src="../../../js/dialogPlugin.js"></script>
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
        <li><a href="#">产品管理</a></li>
        <li><a href="#">产品列表</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="tools">

        <ul class="toolbar">
            <li ><a href="/product/toAddProduct"><span><img src="../../../images/t01.png" /></span>添加</li></a>
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
            <#--<th>商品目录</th>-->
            <th>名称</th>
            <#--<th>标题</th>-->
            <th>小图</th>
            <th>秒杀价格</th>
            <th>原价格</th>
            <th>库存</th>
            <th>产品状态</th>
            <th>数据状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list pageInfo.list as product>
        <tr>
            <#--<td><input name="" type="checkbox" value="${product.id}" /></td>-->
            <td>${product.id}</td>
            <#--<td>${product.categoryName}</td>-->
            <td>${product.name}</td>
            <#--<td>${product.subTitle}</td>-->
            <td><#if product.mainImage ??>
                <img src="${product.mainImage}" alt="${product.name}" width="100" height="50">
                <#else>
                未绑定图片
                </#if>
            </td>
            <td>${product.seckillPrice}</td>
            <td>${product.price}</td>
            <td>${product.stock}</td>
            <td>${productStatusMap["${product.productStatus}"]}</td>
            <td>
                <#list dataStatusMap ? keys as key>
                    <#if key == product.status>
                        ${dataStatusMap["${key}"]}
                    </#if>
                </#list>
            </td>
            <td>${product.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
            <td><a href="/product/productDetail/${product.id}" class="tablelink">查看</a>/
                <#if product.status == 1>
                    <a href="javascript:void(0);abandonProduct(${product.id});" class="tablelink">禁用</a>/
                <#elseif product.status == 2>
                    <a href="javascript:void(0);enableProduct(${product.id});" class="tablelink">启用</a>/
                </#if>

                <#if product.productStatus !=2 ><!--上架的商品不能编辑-->
                    <a href="/product/toEditProduct/${product.id}" class="tablelink">编辑</a>/
                </#if>
                <#if product.productStatus == 1 || product.productStatus == 4>
                    <a href="javascript:void(0);publish(${product.id});" class="tablelink">上架</a>/
                </#if>
                <#if product.productStatus == 2>
                    <a href="javascript:void(0);leftShelf(${product.id});" class="tablelink">下架</a>/
                </#if>
            </td>
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
        window.location.href="/product/productList?pageIndex="+targetPageNum+"&pageSize="+pageSize;
    });


    function abandonProduct(id){
        $.ajax({
            url: "/product/abandonProduct/" +id,
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.status == 0) {
                    zdalert("提示信息","操作成功！",function(){
                        window.location.href = "/product/productList";
                    });
                } else {
                    zdalert("错误信息",res.errorMsg);
                }

            },
            error: function (res) {
                console.log(res);
            }
        });
    }

    function enableProduct(id){
        $.ajax({
            url: "/product/enableProduct/" +id,
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.status == 0) {
                    zdalert("提示信息","操作成功！",function(){
                        window.location.href = "/product/productList";
                    });
                } else {
                    zdalert("错误信息",res.errorMsg);
                }

            },
            error: function (res) {
                console.log(res);
            }
        });
    }

    function publish(id){
        $.ajax({
            url: "/product/publish/" +id,
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.status == 0) {
                    zdalert("提示信息","操作成功！",function(){
                        window.location.href = "/product/productList";
                    });
                } else {
                    zdalert("错误信息",res.errorMsg);
                }

            },
            error: function (res) {
                console.log(res);
            }
        });
    }

    function leftShelf(id){
        $.ajax({
            url: "/product/leftShelf/" +id,
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.status == 0) {
                    zdalert("提示信息","操作成功！",function(){
                        window.location.href = "/product/productList";
                    });
                } else {
                    zdalert("错误信息",res.errorMsg);
                }

            },
            error: function (res) {
                console.log(res);
            }
        });
    }
</script>
</body>
</html>