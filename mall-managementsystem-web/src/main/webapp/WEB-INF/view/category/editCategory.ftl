<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>编辑商品目录</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">商品目录列表</a></li>
        <li><a href="#">编辑商品目录</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>商品目录基本信息</span></div>
    <form id="addCategoryForm" action="#" method="post">
        <input type="hidden" name="id" value="${id}"/>
        <ul class="forminfo">
            <#assign parentId = category.parentId/>
            <li><label>父目录<b class="mustfill">*</b></label>
                <select id="parentId" name="parentId">
                    <#if parentId == 0>
                        <option value="${parentId}">无</option>
                    <#else >
                        <#list parentCategoryList as category>
                            <#if parentId == category.id>
                                <option value="${category.id}" >${category.name}</option>
                            </#if>
                        </#list>
                    </#if>
                </select><i>必选</i></li>
            <li><label>目录名<b class="mustfill">*</b></label><input id="name" name="name" type="text" value="${category.name}" class="dfinput" maxlength="15" /><i>最多15个字符</i></li>
            <li><label>排序值</label><input id="sortOrder" name="sortOrder" type="text" value="${category.sortOrder}" class="dfinput" maxlength="5"/></li>
            <li><label>&nbsp;</label><input id="submitButton" type="button" class="btn" value="确认保存"/></li>
        </ul>
    </form>


    <!--提示信息弹出框-->
    <div class="warn_tip">
        <div class="warn_tiptop"><span>警告信息</span><a></a></div>
        <div class="tipinfo">
            <div class="tipright">
                <p id="message">是否确认对信息的修改 ？</p>
            </div>
        </div>

        <div class="tipbtn">
            <input name="" type="button"  class="sure" value="确定" />
        <#--<input name="" type="button"  class="cancel" value="取消" />-->
        </div>
    </div>
</div>

</body>
<script type="application/javascript">
    var not_null_regex = /\S+/;

    //检查表单
    $("#submitButton").click(function (){
        var name = $("#name").val();
        var sortOrder = $("#sortOrder").val();

        if(!not_null_regex.test(name)){
            $("#message").text("目录名称不能为空!");
            showTip();
            return ;
        }

        if(!not_null_regex.test(sortOrder)){
            $("#message").text("排序值不能为空， 默认是999");
            showTip();
            return ;
        }

        $("#submitButton").attr("disabled", "disabled");
        var data = $("#addCategoryForm").serializeArray();
        $.ajax({
            url: "/category/updateCategory",
            dataType: "json",
            type: "post",
            data: data,
            success: function (res) {
                if(res.status == 0){
                    window.location.href="/category/categoryList";
                }else{
                    $("#message").text(res.errorMsg);
                    showTip();
                    return;
                }

            },
            error: function (res) {
                console.log(res);
            }
        });
        $("#butsubmit").removeAttr("disabled");

    });

    //显示提示
    function showTip(){
        $(".warn_tip").show();
    }

    //隐藏提示
    $(".warn_tiptop a").click(function(){
        $(".warn_tip").fadeOut(100);
    });

    //隐藏提示
    $(".sure").click(function(){
        $(".warn_tip").fadeOut(100);
    });
</script>
</html>