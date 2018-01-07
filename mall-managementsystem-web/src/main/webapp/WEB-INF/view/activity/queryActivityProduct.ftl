<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看活动商品</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<script type="text/javascript" src="../../../js/dialogPlugin.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="place">
    <span>位置:</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">活动管理</a></li>
        <li><a href="#">查看活动商品</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="tools">
        <ul class="toolbar">
            <li >活动名称:${activity.name}</li>
            <li >开始时间:${activity.startTime?string('yyyy-MM-dd hh:mm:ss')}</li>
            <li >结束时间:${activity.endTime?string('yyyy-MM-dd hh:mm:ss')}</li>
            <li >绑定商品个数:${boundActivityProductVOList?size}</li>
        </ul>
    </div>

    <div class="tools" >
        <table class="tablelist">

                <thead>
                <tr>
                    <th>已参加活动的商品名称</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <#if boundActivityProductVOList?size != 0>
                        <table class="halfTablelist">
                            <#list boundActivityProductVOList as activityProductRef>
                            <tr>
                                <td>
                                    <a href="/product/productDetail/${activityProductRef.productVO.id}" class="tablelink">${activityProductRef.productVO.name}</a>
                                    <#if activityProductRef_index == 0><label style="color: red">(必抢)</label></#if>
                                </td>
                                <td>
                                    活动价格:￥${activityProductRef.price}
                                </td>
                                <td>
                                    剩余库存:${activityProductRef.stock}
                                </td>
                            </tr>
                            </#list>
                        </table>
                        <#else>
                            无商品
                        </#if>
                    </td>
                </tr>
                </tbody>
        </table>
    </div>

</div>

</body>
</html>