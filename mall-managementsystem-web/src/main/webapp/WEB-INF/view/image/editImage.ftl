<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户信息</title>
</head>
<script type="text/javascript" src="../../../js/jquery.js"></script>
<link href="../../../css/style.css" rel="stylesheet" type="text/css" />
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">图片列表</a></li>
        <li><a href="#">编辑图片</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>图片基本信息</span></div>
    <ul class="forminfo">
        <form action="/image/editImage" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${image.id}"/>
        <li><label>名称</label>${image.name}</li>
        <li><label>图片</label><input id="file" type="file" name="imageFile" onchange="selectImage()" />    </li>
        <li><label>&nbsp;</label><img src="${image.subImageUrl}" id="showSmall" width="86" height="53">
        </li>
        <li><label>&nbsp;</label><img src="${image.mainImageUrl}" id="showBig" width="568" height="348">
        </li>
        <li><label>&nbsp;</label><input type="submit" id="saveButton" onclick="return checkSubmit();" class="btn" value="确认保存"/></li>
        </form>
    </ul>

</div>

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

</body>
<script type="application/javascript">
    function selectImage () {
        var r= new FileReader();
        var f=document.getElementById('file').files[0];

        r.readAsDataURL(f);
        r.onload=function (e) {
            document.getElementById('showSmall').src=this.result;
            document.getElementById('showBig').src=this.result;
        };
    }

    function checkSubmit(){
        var not_null_regex = /\S+/;
        var imageFile = $("#imageFile").val();

        if(!not_null_regex.test(imageFile)){
            $("#message").text("请选择图片");
            showTip();
            return false;
        }else{

            var filepath = $("#file").val();
            var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart, filepath.length).toUpperCase();
            if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
                $("#message").text("图片限于bmp,png,gif,jpeg,jpg格式");
                showTip();
                return false;
            }

            var file_size = $("#file")[0].files[0].size;
            var size = file_size / 1024;
            if (size > 1024) {
                $("#message").text("上传的图片大小不能超过1M！");
                showTip();
                return false;
            }
        }

    }

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