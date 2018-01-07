<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户列表</title>
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
        <li><a href="#">用户管理</a></li>
        <li><a href="#">用户列表</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="tools">

        <ul class="toolbar">
            <li ><a href="/user/toAddUser"><span><img src="../../../images/t01.png" /></span>添加</a></li>
            <#--<li class="click"><span><img src="../../../images/t02.png" /></span>修改</li>-->
           <#-- <li id="batchDelete"><a href="javascript:void(0);" ><span><img src="../../../images/t03.png" /></span>批量删除</a></li>-->
            <#--<li><span><img src="../../../images/t04.png" /></span>统计</li>-->
        </ul>
        <#--<ul class="toolbar1">
            <li><span><img src="../../../images/t05.png" /></span>设置</li>
        </ul>-->
    </div>

    <table class="tablelist">
        <thead>
        <tr>
            <#--<th><input id="selectAll" type="checkbox" value="-1" /></th>-->
            <th>编号<i class="sort"><img src="../../../images/px.gif" /></i></th>
            <th>登录名</th>
            <th>邮箱</th>
            <th>电话</th>
            <th>角色</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list pageInfo.list as customer>
        <tr>
            <#--<td><input name="" type="checkbox" value="${user.id}" /></td>-->
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>
                ${customer.mobile ? substring(0,3)}******${customer.mobile ? substring(9)}
            </td>
            <td>
                <#list userRoleMap ? keys as key>
                     <#if key == customer.role>
                        ${userRoleMap["${key}"]}
                     </#if>
                </#list>
            </td>
            <td>
                <#list dataStatusMap ? keys as key>
                    <#if key == customer.status>
                        ${dataStatusMap["${key}"]}
                    </#if>
                </#list>
            </td>
            <td>${customer.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
            <td><a href="/user/userDetail/${customer.id}" class="tablelink">查看</a>
                    <#if (user.role == 0) || (user.role == 1 && customer.role == 2 )>
                        <#if customer.status == 1>
                            /<a href="/user/abandonUser/${customer.id}" class="tablelink">禁用</a>
                        <#elseif customer.status == 2>
                            / <a href="/user/enableUser/${customer.id}" class="tablelink">启用</a>
                        </#if>
                    </#if>

                    <#if (user.role == 0) || (user.role == 1 && customer.role == 2)>
                    /<a href="/user/toEditUser/${customer.id}" class="tablelink">编辑</a>
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

</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');

    $(".paginItem").click(function(){
        var targetPageNum = $(this).attr("data-id");
        var pageSize = $("#pageSize").val();
        window.location.href="/user/userList?pageIndex="+targetPageNum+"&pageSize="+pageSize;
    });

    /*$("#selectAll").click(function(){
        var checked = $(this).attr("checked");
        if(checked){
            $("[type=checkbox]").each(function(){
                this.checked = true;
            });
        }else{
            $("[type=checkbox]").each(function(){
                this.checked = false;
            });
        }
    });

    /!**
     * 批量删除
     *!/
    $("#batchDelete").click(function(){
        var flag = false;
        var ids="";

        $("[type=checkbox]:checked").each(function(){
            if(this.checked && this.value != -1){
                flag = true;
                ids +=this.value+",";
            }
        });
        if(!flag){
            zdalert("提示信息","请选中要删除的数据！");
        }else {
            zdconfirm("提示信息","删除! 确认?", function(confirmed){
                    if(confirmed) {
                        $.ajax({
                            url: "/user/batchDeleteUsers",
                            dataType: "json",
                            type: "post",
                            data: {userIds: ids},
                            success: function (res) {
                                if (res.status == 0) {
                                    zdalert("提示信息","操作成功！",function(){
                                        window.location.href = "/user/userList";
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
            });
        }
    });*/

</script>
</body>
</html>