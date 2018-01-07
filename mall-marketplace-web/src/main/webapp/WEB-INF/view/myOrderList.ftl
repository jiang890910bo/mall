<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>我的订单列表</title>
    <link rel="stylesheet" type="text/css" href="../../css/nav_top.css">
    <link rel="stylesheet" type="text/css" href="../../css/css.css" />
    <link href="../../css/qikoo.css" type="text/css" rel="stylesheet" />
    <link href="../../css/store.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="../../js/qikoo.js"></script>

</head>

<body>
<div id="top">
    <div id="top_main">

        <div id="hello">
					<span>您好，欢迎来到商城！
			        <a href="/index/main">[首页]</a>|
                    <#if user??>
                        <a href="/login/exit">[退出]</a>
                    <#else>
                        <a href="/login/toLogin">[登陆]</a>
                        &nbsp;&nbsp;|&nbsp;&nbsp;<a href="/login/toRegister">[免费注册]</a>
                    </#if>
			</span>
        </div>

    </div>
</div>

<div class="shopping_content">
    <#if orderList??>

        <input type="hidden" name="shippingId" id="shippingId" value=""/>
        <table border="1" bordercolor="#ede8ee" cellspacing="0" cellpadding="0" style="width: 100%; text-align: center;">
            <tr>
                <th>商品图片</th>
                <th>商品名称</th>
                <th>商品价格</th>
                <th>商品数量</th>
                <th>订单总价</th>
                <th>创建时间</th>
                <th>订单状态</th>
                <th>操作</th>
            </tr>
            <#list orderList as order>
                <#assign orderItemList = order.orderItemVOList>
                <#list orderItemList as orderItem>
            <tr>
                <td>
                    <a><img src="${orderItem.productImage}" width="100" height="50"/></a>
                </td>
                <td><span>${orderItem.productName}</span></td>
                <td>
                    <span>￥${orderItem.currentUnitPrice}</span>
                </td>
                <td>
                    <span>${orderItem.quantity}</span>
                </td>
                <td>
                    <span>￥${orderItem.currentUnitPrice * orderItem.quantity}</span>
                </td>
                <td>
                    <span>${order.createTime?string('yyyy-MM-dd hh:mm:ss')}</span>
                </td>
                <td>
                    <span>${order.orderStatus}</span>
                </td>
                <td>
                    <input class="btn_closing" type="button" data-id="${order.id}" id="paySubmit"  value="付款"/>
                </td>
            </tr>
                </#list>
            </#list>
        </table>
    <#else>
        您还没有订单，<a href="/index/main">返回首页
    </a>
    </#if>
</div>


<!-- 底部开始 -->
<div id="down">
    <div class="down_top">
        <a href="#">关于我们</a>
        <a href="#">联系我们</a>
        <a href="#">商家入驻</a>
        <a href="#">营销中心</a>
        <a href="#">手机商城</a>
        <a href="#">友情链接</a>
        <a href="#">销售联盟</a>
        <a href="#">商城社区</a>
        <a href="#">商城公益</a>
        <a href="#" style="border-right:none">English Site</a>
    </div>
    <div class="down_center">
        <span>北京市公安局朝阳分局备案编号110105014669  |  京ICP证070359号  |  <a href="#">互联网药品信息服务资格证编号(京)-经营性-2014-0008</a>  |  新出发京零 字第大120007号</span>
        <span><a href="#">音像制品经营许可证苏宿批005号</a>  |  出版物经营许可证编号新出发(苏)批字第N-012号  |  互联网出版许可证编号新出网证(京)字150号</span>
        <span><a href="#">网络文化经营许可证京网文[2011]0168-061号</a>  违法和不良信息举报电话：4006561155  Copyright © 2004-2014  商城JD.com 版权所有</span>
        <span>商城旗下网站：<a href="#">360TOP</a><a href="#">拍拍网</a><a href="#">网银在线</a></span>
    </div>
    <div class="down_bot">
        <img src="../../img/bot1.gif" alt="" />
        <img src="../../img/bot2.gif" alt="" />
        <img src="../../img/bot3.png" alt="" />
        <img src="../../img/bot4.png" alt="" />
    </div>
</div>
<!-- 底部结束 -->
</body>
<script type="application/javascript">
    $("#paySubmit").click(function(){
        var orderId= $(this).attr("data-id");
        window.location.href="/payInfo/pay/" + orderId;
    });
</script>
</html>