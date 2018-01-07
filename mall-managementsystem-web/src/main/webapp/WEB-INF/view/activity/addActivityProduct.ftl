<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>活动添加商品</title>
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
        <li><a href="#">活动添加商品</a></li>
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
                    <th>未参加活动的商品名称</th>
                    <td></td>
                    <th>已参加活动的商品名称</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td width="45%">
                        <#if unBoundActivityProductVOList?size != 0>
                        <table class="halfTablelist">
                            <#list unBoundActivityProductVOList as prodcut>
                            <tr>
                                <td width="20%">
                                    <a href="/product/productDetail/${prodcut.id}" class="tablelink">${prodcut.name}</a>
                                </td>
                                <td width="12%">
                                    秒杀价格:￥${prodcut.seckillPrice}
                                </td>
                                <td width="12%">
                                    原价格:￥${prodcut.price}
                                </td>
                                <td width="8%">
                                    库存:${prodcut.stock}
                                </td>
                                <td width="8%"><a href="javascript:void(0);joinActivity(${prodcut.id},${activity.id})" class="tablelink">参加活动</a></td>
                            </tr>
                            </#list>
                        </table>
                        <#else>
                            无商品
                        </#if>
                    </td>
                    <td  width="5%">
                    </td>
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
                                    活动价格:￥${activityProductRef.seckillPrice}
                                </td>
                                <td>
                                    原价格:￥${activityProductRef.price}
                                </td>
                                <td>
                                    剩余库存:${activityProductRef.stock}
                                </td>
                                <td><a href="javascript:void(0);exitActivity(${activityProductRef.id}, ${activity.id})" class="tablelink">退出活动
                                </a>

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

<script type="text/javascript">
    /**
     * 添加活动
     * @param id
     */
    function joinActivity(productId, activityId){
        zdconfirm("提示信息", "参加活动的商品价格和商品库存直接copy原商品的信息设置！",function(confirmed) {
                    if(confirmed){

                        $.ajax({
                            url: "/activity/joinActivity/" + activityId,
                            dataType: "json",
                            type: "post",
                            data: {productId: productId},
                            success: function (res) {
                                if (res.status == 0) {
                                    zdalert("提示信息", "操作成功！", function () {
                                        window.location.href = "/activity/addActivityProduct/" + activityId;
                                    });
                                } else {
                                    zdalert("错误信息", res.errorMsg);
                                }

                            },
                            error: function (res) {
                                console.log(res);
                            }
                        });
                    }
                }
        );
    }

    /**
     * 退出活动
     * @param id
     */
    function exitActivity(activityProductRefId, activityId){
        zdconfirm("提示信息", "退出活动！确认？",function() {
            $.ajax({
                url: "/activity/exitActivity/",
                dataType: "json",
                type: "post",
                data: {activityProductRefId: activityProductRefId},
                success: function (res) {
                    if (res.status == 0) {
                        zdalert("提示信息", "操作成功！", function () {
                            window.location.href = "/activity/addActivityProduct/" + activityId;
                        });
                    } else {
                        zdalert("错误信息", res.errorMsg);
                    }

                },
                error: function (res) {
                    console.log(res);
                }
            });
        }
        );
    }
</script>
</body>
</html>