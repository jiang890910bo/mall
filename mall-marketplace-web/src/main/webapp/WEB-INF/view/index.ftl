<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>秒杀首页</title>
</head>
<link href="../../css/index.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="../../css/nav_top.css">
<link rel="stylesheet" href="../../css/login_style.css" type="text/css"/>
<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<#--<script type="text/javascript" src="../../js/jquery.countdown.js"></script>-->
<script type="text/javascript" src="../../js/dialogPlugin.js"></script>

<script type="text/javascript" charset="utf-8">
    jQuery(document).ready(function ($) {

        /**
         * 立即抢购的跳转动作
         */
        $(".canBuy").click(function () {
            var dataId = $(this).attr("data-id");
            $("#activityProductRefId_hidden").val(dataId);
            $.ajax({
                url:"/login/checkUser",
                dateType:"json",
                success: function (res) {
                    if(res.status == 0){
                        window.location.href="/order/orderConfirm?activityProductRefId="+$("#activityProductRefId_hidden").val()+"&activityId="+$("#activityId_hidden").val();
                    }else{
                        showDialog();
                    }
                },
                error:function (res) {
                    console.error(res);
                }

            });

        });

        $(".noBuy").click(function(){
            zdalert("提示", "${hotProduct.startHour}点开抢");
        });


        /**
         * 登陆动作
         */
        $("#loginButton").click(function() {
            var not_null_regex = /\S+/;
            var loginName = $("#loginName").val();
            var password = $("#password").val();

            if(!not_null_regex.test(loginName) &&  !not_null_regex.test(password)){
                $("#errorMsg").text("请输入登录名和密码");
                return ;
            }else if(!not_null_regex.test(loginName)){
                $("#errorMsg").text("请输入登录名");
                return ;
            }else if(!not_null_regex.test(password)){
                $("#errorMsg").text("请输入密码");
                return ;
            }else{
                $("#errorMsg").text("");
                $.ajax({
                    url:"/login/checkLogin",
                    dataType:"json",
                    type:"post",
                    data:{loginName:loginName,password:password},
                    success:function(res){
                        if(res.status!= 0){
                            $("#errorMsg").text(res.errorMsg);
                        }else if(res.status == 0){
                            debugger;
                            window.location.href="/order/orderConfirm?activityProductRefId="+$("#activityProductRefId_hidden").val()+"&activityId="+$("#activityId_hidden").val();
                        }
                    },
                    error:function(res){
                        console.log(res);
                    }

                });
            }
        });
    });

</script>

</head>

<body>

<!--登陆 begin-->
<div>
   <div class="ui-mask" id="mask" onselectstart="return false"></div>
    <div class="ui-dialog" id="dialogMove" onselectstart='return false;'>

        <div class="ui-dialog-title" id="dialogDrag" onselectstart="return false;">
            登录
            <a class="ui-dialog-closebutton" href="javascript:hideDialog();"></a>
        </div>
        <div class="ui-dialog-content">
            <div class="ui-dialog-l40 ui-dialog-pt15">
                <input id="loginName" class="ui-dialog-input ui-dialog-input-username" type="input" placeholder="手机/邮箱/用户名" value=""/>
            </div>
            <div class="ui-dialog-l40 ui-dialog-pt15">
                <input id="password" class="ui-dialog-input ui-dialog-input-password" type="password" placeholder="密码" value=""/>
            </div>
            <div class="ui-dialog-l40">
                <label class="error" id="errorMsg">&nbsp;</label>
            </div>
            <div>
                <a id="loginButton" class="ui-dialog-submit" href="javascript:void(0);">登录</a>
            </div>
            <div class="ui-dialog-l40">
                <a href="#">忘记密码</a>|&nbsp;|&nbsp;<a href="/login/toRegister">立即注册</a>
            </div>
        </div>
    </div>
</div>
<!--登陆end-->

<div style="background:#f7f7f7; " class="back_1yms">
    <div id="top">
        <div id="top_main">

            <div id="hello">
					<span>您好，欢迎来到商城！
                    <#if user??>
                        Hi, ${user.name}&nbsp;&nbsp;
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
    <div class="title_1yms"></div>
    <div style="clear:both"></div>

    <input type="hidden" id="activityProductRefId_hidden" value=""/>
    <input type="hidden" id="activityId_hidden" value="${activityId}"/>
    <div class="body_1yms">
        <#if hotProduct??>
        <!--今日必抢 begin-->
        <div class="ms_floor1">
            <div class="yms_floor1_title"><img src="../../img/wenb1.png"></div>
            <div style="clear:both; height:20px"></div>

            <div class="ms_border">
                <div class="floor1_left"><img src="${hotProduct.productVO.imageVO.mainImageUrl}"></div>
                <div class="floor1_right">
                    <div class="floor1_right_text">
                        <div class="floor1_right_title">${hotProduct.productVO.name}</div>
                        <div class="floor1_right_news">${hotProduct.productVO.detail}</div>
                        <div class="floor1_time">

                            <div class="has">剩余：<img src="../../img/has.png"></div>
                            <div class="red" style="font-weight:bold;">${hotProduct.stock}</div>
                            <div class="te">瓶</div>
                        </div>
                        <div style="clear:both; height:85px;"></div>
                        <div class="floor1_three">
                            <div class="floor1_price">
                                <div class="red floor1_three_fh">￥</div>
                                <div class="red floor1_three_size">${hotProduct.seckillPrice}元</div>
                                <div class="yh">￥${hotProduct.price}元</div>
                            </div>
                            <div class="floor2_right">
                                <#if hotProduct.startHour??>
                                    <a href="javascript:void(0);">
                                        <div class="subnow noBuy" data-id="${hotProduct.id}">立即抢购(${hotProduct.startHour}点开抢) </div>
                                    </a>
                                <#else>
                                    <#if hotProduct.stock gt 0 >
                                    <a href="javascript:void(0);">
                                        <div class="subnow canBuy" data-id="${hotProduct.id}">立即抢购 </div>
                                    </a>
                                    <#else>
                                        <div class="subpassed" >已抢完 </div>
                                    </#if>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div style="clear:both; height:20px"></div>
        </div>
        <!--今日必抢 end-->

        <!--限量秒杀 begin-->
        <div class="ms_floor2">
            <div class="ms_floor2_title"><img src="../../img/wenb2.png"></div>

            <ul class="floo2_ul">
                <#list boundActivityProductVOList as product>
                    <#if product_index % 2 == 0>
                        <!--left-->
                        <li>
                            <a href="#">
                                <div><img src="${product.productVO.imageVO.mainImageUrl}"></div>
                            </a>

                            <div class="floor2_bottom_text">
                                <div class="floor2_right_title">${product.productVO.name}</div>
                                <div class="floor2_right_news">${product.productVO.detail}</div>
                                <div style="clear:both; height:15px;"></div>
                                <div class="floor2_three">
                                    <div class="floor1_price">
                                        <div class="red floor1_three_fh">￥</div>
                                        <div class="red floor1_three_size">${product.seckillPrice}元</div>
                                        <div class="yh">￥${product.price}元</div>
                                    </div>
                                    <div class="floor2_left">
                                        <div class="top">限量</div>
                                        <div class="bottom">${product.stock}件</div>
                                    </div>
                                    <div class="floor2_right">
                                        <#if product.startHour??>
                                            <a href="javascript:void(0);">
                                                <div class="subnow noBuy" data-id="${product.id}">立即抢购(${product.startHour}点开抢) </div>
                                            </a>
                                        <#else>
                                            <#if product.stock gt 0 >
                                            <a href="javascript:void(0);">
                                                <div class="subnow canBuy" data-id="${product.id}">立即抢购</div>
                                                </a>
                                            <#else>
                                                <div class="subpassed" >已抢完 </div>
                                            </#if>
                                        </#if>
                                    </div>
                                </div>
                                <div style="clear:both;"></div>
                            </div>
                        </li>
                        <div class="center"></div>
                    <#else >
                        <!--right-->
                        <li>
                            <a href="#">
                                <div><img src="${product.productVO.imageVO.mainImageUrl}"></div>
                            </a>

                            <div class="floor2_bottom_text">
                                <div class="floor2_right_title">${product.productVO.name}</div>
                                <div class="floor2_right_news">${product.productVO .detail}</div>
                                <div style="clear:both; height:15px;"></div>
                                <div class="floor2_three">
                                    <div class="floor1_price">
                                        <div class="red floor1_three_fh">￥</div>
                                        <div class="red floor1_three_size">${product.seckillPrice}元</div>
                                        <div class="yh">￥${product.price}元</div>
                                    </div>
                                    <div class="floor2_left">
                                        <div class="top">限量</div>
                                        <div class="bottom">${product.stock}件</div>
                                    </div>
                                    <div class="floor2_right">
                                        <#if product.startHour??>
                                            <a href="javascript:void(0);">
                                                <div class="subnow noBuy" data-id="${product.id}">立即抢购(${product.startHour}点开抢) </div>
                                            </a>
                                        <#else>
                                            <#if product.stock gt 0 >
                                                <a href="javascript:void(0);">
                                                    <div class="subnow canBuy" data-id="${product.id}">立即抢购</div>
                                                </a>
                                            <#else>
                                                <div class="subpassed" >已抢完 </div>
                                            </#if>
                                        </#if>
                                    </div>
                                </div>
                                <div style="clear:both;"></div>
                            </div>
                        </li>
                    </#if>
                </#list>

                <div style="clear:both"></div>
            </ul>

        </div>
        <#else>
            <div style="text-align: center; padding: 100px;">
                <span style="color: red;font-size: xx-large;">抱歉，商品暂未开抢！请耐心等待...</span>
            </div>
        </#if>
    </div>
</div>
<div style="clear:both"></div>

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
        <img src="../../img/bot1.gif" alt=""/>
        <img src="../../img/bot2.gif" alt=""/>
        <img src="../../img/bot3.png" alt=""/>
        <img src="../../img/bot4.png" alt=""/>
    </div>
</div>
<!-- 底部结束 -->
</body>
<script type="text/javascript" src="../../js/login.js"></script>
</html>