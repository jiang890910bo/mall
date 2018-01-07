<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>订单确认首页</title>
    <link rel="stylesheet" type="text/css" href="../../css/nav_top.css">
    <link rel="stylesheet" type="text/css" href="../../css/css.css" />
    <link href="../../css/qikoo.css" type="text/css" rel="stylesheet" />
    <link href="../../css/store.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="../../js/qikoo.js"></script>


    <script type="text/javascript">
        $(function() {
            var not_null_regex = /\S+/;
            var region = $("#region");
            var number_this = $("#number_this");
            var phone = $("#phone");

            //添加收货地址
            $("#sub_setID").click(function() {
                var input_out = $(".input_style");
                for (var i = 0; i <= input_out.length; i++) {
                    if (!not_null_regex.test($(input_out[i]).val())) {
                        $(input_out[i]).css("border", "1px solid red");
                        return false;
                    } else {
                        $(input_out[i]).css("border", "1px solid #cccccc");
                    }
                }

                var dataJson = $("#addShipping_form").serializeArray();
                $.ajax({
                    url:'/shipping/addShipping',
                    dataType:'json',
                    type:'post',
                    data:dataJson,
                    success:function(res){
                        if(res.status == 0){
                            window.location.href="/order/orderConfirm?activityProductRefId="+$("#productId_hidden").val()+"&activityId="+$("#activityId").val();
                        }else{
                            $("#errorMsg").text(res.errorMsg);
                        }
                    },
                    error:function(res){
                        console.log(res);
                    }
                });
            });

            //选择收货地址
            $('.Caddress .add_mi').click(function() {
                $(this).css('background', 'url("../../img/mail_1.jpg") no-repeat').siblings('.add_mi').css('background', 'url("../../img/mail.jpg") no-repeat');
                var addressList = $(".add_mi");
                for(var i=0;i<addressList.length;i++){
                    $(addressList[i]).removeAttr("selected");
                }
                $(this).attr("selected", "true");


            });


        });
        var x = Array();

        function func(a, b) {
            x[b] = a.html();
            alert(x)
            a.css('border', '2px solid #f00').siblings('.min_mx').css('border', '2px solid #ccc');
        }

        function onclick_close() {
            var shade_content = $(".shade_content");
            var shade = $(".shade");
            shade_content.hide();
            shade.hide();

        }

        function onclick_open() {
            $(".shade_content").show();
            $(".shade").show();
            var input_out = $(".clear");
            for (var i = 0; i <= input_out.length; i++) {
                if ($(input_out[i]).val() != "") {
                    $(input_out[i]).val("");
                }
            }
        }


        function checkShipping(){
            var shippingId = $("#shippingId").val();
            if(shippingId.length == 0){
                onclick_open();
                return false;
            }
        }
    </script>
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
        <div class="topright">
            <ul>
                <li>
                    <div class="cun">
                        <a href="/order/myOrderList">我的订单</a>
                    </div>
                </li>

            </ul>
        </div>
    </div>
</div>

<div class="Caddress">
    <#if shippingList??>
        <#if (shippingList? size < 4) >
        <div class="open_new">
            <a href="javascript:void(0);"  class="open_btn" onclick="javascript:onclick_open();">使用新地址</a>
        </div>
        </#if>
        <#list shippingList as shipping>
            <div data-id=${shipping.id} class="add_mi" <#if shipping_index==0>selected="selected"</#if>>
                <p style="border-bottom:1px dashed #ccc;line-height:28px;">${shipping.receiverName}</p>
                <p>${shipping.receiverProvince}&nbsp;&nbsp;${shipping.receiverCity}&nbsp;&nbsp;${shipping.receiverDistrict}&nbsp;&nbsp;${shipping.receiverAddress}&nbsp;&nbsp;${shippingreceiverMobile}</p>
            </div>
        </#list>
    <#else>
        您还没有添加收获地址，请<a href="javascript:void(0);"  class="open_btn" onclick="javascript:onclick_open();">添加收货地址</a>！
    </#if>
</div>

<div class="shopping_content">
    <#if activityProductRef??>

        <form action="/order/order" method="post" onclick="return checkShipping();">
            <input type="hidden" name="activityId" id="activityId" value="${activityId}"/>
            <input type="hidden" name="shippingId" id="shippingId" value=""/>
        <table border="1" bordercolor="#ede8ee" cellspacing="0" cellpadding="0" style="width: 100%; text-align: center;">
            <tr>
                <th>商品图片</th>
                <th>商品名称</th>
                <th>商品价格</th>
                <th>商品数量</th>
                <#--<th>商品操作</th>-->
            </tr>
            <tr>
                <td>
                    <input type="hidden" id="productId_hidden" name="activityProductRefId" value="${activityProductRef.id}"/>
                    <a><img src="${activityProductRef.imageUrl}" width="100" height="50"/></a>
                </td>
                <td><span>${activityProductRef.productName}</span></td>
                <td>
                    <span class="span_momey">￥${activityProductRef.seckillPrice}</span>
                </td>
                <td>
                    <#--<button class="btn_reduce" onclick="javascript:onclick_reduce(this);">-</button>-->
                    <input class="momey_input" type="" name="" id="" value="1" disabled="disabled" />
                    <#--<button class="btn_add" onclick="javascript:onclick_btnAdd(this);">+</button>-->
                </td>
               <#-- <td>
                    <button class="btn_r" onclick="javascript:onclick_remove(this);">删除</button>
                </td>-->
            </tr>
        </table>
        <div class="" style="width: 100%; text-align: right; margin-top: 200px;margin-bottom: 50px;">
            <div class="div_outMumey" style="float: left;">
                总价：<span class="out_momey">${activityProductRef.seckillPrice * 1} </span>
            </div>
            <input type="button" class="btn_closing" onclick="window.location.href='/index/main';" value="返回"/>
            <input class="btn_closing" type="submit" id="submitButtonId"  value="结算"/>
        </div>
        </form>
    <#else>
        您还没有选择购买的商品，<a href="/index/main">返回首页购买</a>
    </#if>
</div>

<!--
    描述：shade 遮罩层
-->
<div class="shade" style="display: none;">
</div>
<!--
    作者：z@163.com
    时间：2016-03-02
    描述：shade_content
-->
<div class="shade_content" style="display: none;">
    <div class="col-xs-12 shade_colse">
        <a href="javascript:void(0);onclick_close();">x</a>
    </div>
    <div class="nav shade_content_div">
        <div class="col-xs-12 shade_title">
            新增收货地址
    </div>
        <form id="addShipping_form">
        <div class="col-xs-12 shade_from">
                <input type="hidden" name="userId" id="userId" value="${user.id}"/>
                <div class="col-xs-12">
                    <span class="span_style" id="">省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</span>
                    <input class="input_style clear" type="input" name="receiverProvince" id="receiverProvince" value="" placeholder="&nbsp;&nbsp;请输入您的所在省份" />
                </div>
                <div class="col-xs-12">
                    <span class="span_style" id="">城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市</span>
                    <input class="input_style clear" type="input" name="receiverCity" id="receiverCity" value="" placeholder="&nbsp;&nbsp;请输入您的所在城市" />
                </div>
                <div class="col-xs-12">
                    <span class="span_style" id="">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区</span>
                    <input class="input_style clear" type="input" name="receiverDistrict" id="receiverDistrict" value="" placeholder="&nbsp;&nbsp;请输入您的所在地区" />
                </div>
                <div class="col-xs-12">
                    <span class="span_style" id="">详细地址</span>
                    <input class="input_style clear" type="input" name="receiverAddress" id="receiverAddress" value="" placeholder="&nbsp;&nbsp;请输入您的详细地址" />
                </div>
                <div class="col-xs-12">
                    <span class="span_style" id="">邮政编号</span>
                    <input class="input_style clear" type="input" name="receiverZip" id="receiverZip" value="" placeholder="&nbsp;&nbsp;请输入您的邮政编号" />
                </div>
                <div class="col-xs-12">
                    <span class="span_style" class="span_sty" id="">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                    <input class="input_style " type="input" name="receiverName" id="receiverName" value="${user.name}" placeholder="&nbsp;&nbsp;请输入您的姓名" />
                </div>
                <div class="col-xs-12">
                    <span class="span_style" id="">手机号码</span>
                    <input class="input_style" type="input" name="receiverMobile" id="receiverMobile" value="${user.phone}" placeholder="&nbsp;&nbsp;请输入您的手机号码" />
                </div>
                <div class="col-xs-12">
                    <span class="errorMsg" id="errorMsg"></span>
                </div>
                <div class="col-xs-12">
                    <input class="btn_remove" type="button" id="" onclick="javascript:onclick_close();" value="取消" />
                    <input type="button" class="sub_set" id="sub_setID" value="提交" />
                </div>
        </div>
        </form>
    </div>
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
<script>
    //默认指定第一个后货地址
    $("[selected='selected']").css('background', 'url("../../img/mail_1.jpg") no-repeat').siblings('.add_mi').css('background', 'url("../../img/mail.jpg") no-repeat');
    var shippingId = $("div[class='add_mi'][selected='selected']").attr("data-id");
    $("#shippingId").val(shippingId);
</script>
</html>