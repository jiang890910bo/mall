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
        <li><a href="#">添加产品</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>产品基本信息</span></div>
    <form id="addProductForm" action="#" method="post">
    <ul class="forminfo">
        <li><label>所属目录<b class="mustfill">*</b></label>
            <select name="categoryId" id="categoryId" class="dfinput">
                <option value="-1">--请选择--</option>
                <#list categoryList as category>
                    <option value="${category.id}">${category.name}</option>
                </#list>
            </select>
        </li>
        <li><label>产品名称<b class="mustfill">*</b></label><input id="name" name="name" type="text" class="dfinput" maxlength="30" /><i>标题不能超过30个字符</i></li>
        <li><label>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格<b class="mustfill">*</b></label><input id="price" name="price" type="text" class="dfinput" maxlength="10" /><i>大于0元</i></li>
        <li><label>秒杀价格<b class="mustfill">*</b></label><input id="seckillPrice" name="seckillPrice" type="text" class="dfinput" maxlength="10" /><i>大于0元</i></li>
        <li><label>库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存<b class="mustfill">*</b></label><input id="stock" name="stock" type="text" value="" class="dfinput" maxlength="100"/><i>大于0</i></li>
        <li><label>副&nbsp;&nbsp;标&nbsp;&nbsp;题<b class="mustfill">*</b></label><input id="subTitle" name="subTitle" type="text" class="dfinput" maxlength="20"/></li>
        <li><label>图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片<b class="mustfill">*</b></label>
            <select name="imageId" id="imageId" class="dfinput">
                <option value="-1">--请选择--</option>
            <#list imageList as image>
                <option value="${image.id}"><a>${image.name}</a></option>
            </#list>
            </select>
        </li>
        <li><label>详情描述<b class="mustfill">*</b></label><textarea id="detail" name="detail"  style="border: 1px solid gray;" rows="5" cols="100" maxlength="200"></textarea><i>最多200个字符</i></li>

        <li><label>&nbsp;</label><input id="submitButton" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form>

</div>

</body>
<script type="application/javascript">
    var not_null_regex = /\S+/;
    var num_regex = /^[1-9]\d*(\.\d+)?$/;
    var stock_regex =  /^\+?[1-9]\d*$/;

     //检查表单
    $("#submitButton").click(function (){
        var categoryId = $("#categoryId").val();
        var name = $("#name").val();
        var price = $("#price").val();
        var seckillPrice = $("#seckillPrice").val();
        var stock = $("#stock").val();
        var subTitle = $("#subTitle").val();
        var imageId = $("#imageId").val();
        var detail = $("#detail").val();

        if(categoryId == -1){
            zdalert("错误信息","请选择所属目录");
            return;
        }
        if(!not_null_regex.test(name)){
            zdalert("错误信息","产品名称不能为空");
            return;
        }
        if(!not_null_regex.test(price)){
            zdalert("错误信息","价格不能为空");
            return ;
        }else if(!num_regex.test(price)){
            zdalert("错误信息","价格必须大于0的数字");
            return ;
        }

        if(!not_null_regex.test(seckillPrice)){
            zdalert("错误信息","秒杀价格不能为空");
            return ;
        }else if(!num_regex.test(seckillPrice)){
            zdalert("错误信息","秒杀价格必须大于0的数字");
            return ;
        }

        if(!not_null_regex.test(stock)){
            zdalert("错误信息","库存不能为空");
            return ;
        }else if(!stock_regex.test(stock)){
            zdalert("错误信息","库存必须是大于0的整数");
            return ;
        }

        if(!not_null_regex.test(subTitle)){
            zdalert("错误信息","副标题不能为空");
            return ;
        }

        if(imageId == -1){
            zdalert("错误信息","请选择绑定的图片");
            return ;
        }

        if(!not_null_regex.test(detail)){
            zdalert("错误信息","产品描述不能为空");
            return ;
        }

            $("#submitButton").attr("disabled", "disabled");
            var data = $("#addProductForm").serializeArray();
            $.ajax({
                url: "/product/addProduct",
                dataType: "json",
                type: "post",
                data: data,
                success: function (res) {
                    if(res.status == 0){
                        zdalert("提示信息","操作成功！",function(){
                            window.location.href="/product/productList";
                        });
                    }else{
                        $("#butsubmit").removeAttr("disabled");
                        zdalert("错误信息",res.errorMsg);
                        return;
                    }

                },
                error: function (res) {
                    console.log(res);
                }
            });


    });

</script>
</html>