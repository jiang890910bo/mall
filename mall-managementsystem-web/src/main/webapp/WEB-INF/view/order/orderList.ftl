<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>订单列表</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<script type="text/javascript" src="../../../js/dialogPlugin.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />

</head>


<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">订单管理</a></li>
        <li><a href="#">订单列表</a></li>
    </ul>
</div>

<div class="rightinfo">
    <table class="tablelist">
        <thead>
        <tr>
            <th>编号<i class="sort"><img src="../../../images/px.gif" /></i></th>
            <th>订单号</th>
            <th>金额</th>
            <th>订单状态</th>
            <th>支付时间</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list pageInfo.list as order>
        <tr>
            <td>${order.id}</td>
            <td>${order.orderNo}</td>
            <td>￥${order.payment}</td>
            <td><#list orderStatusMap ? keys as key>
                    <#if key == order.orderStatus>
                    ${orderStatusMap["${key}"]}
                    </#if>
                </#list></td>
            <td>${order.paymentTime?string('yyyy-MM-dd HH:mm')}</td>
            <td>${order.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
            <td><a href="/order/orderDetail/${order.id}" class="tablelink">查看</a>/
                <a href="javascript:void(0);handPay(${order.id});" class="tablelink">后台付款</a>
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

</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');

    $(".paginItem").click(function(){
        var targetPageNum = $(this).attr("data-id");
        var pageSize = $("#pageSize").val();
        window.location.href="/order/orderList?pageIndex="+targetPageNum+"&pageSize="+pageSize;
    });

    /**
     * 手动支付
     */
    function handPay(var orderId){
        $.ajax({
            url: "/order/handPay",
            dataType: "json",
            type: "post",
            data: {orderId:orderId},
            success: function (res) {
                if(res.status == 0){
                    zdalert("提示信息","操作成功！",function(){
                        window.location.href="/order/orderList";
                    });
                }else{
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