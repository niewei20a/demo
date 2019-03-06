<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>mybatis</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.4.0/jquery.js"></script>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>

    <script src="js/artDialog.js"></script>


    <script type="text/javascript">
        function ajaxFun() {
            $.get("AjaxServlet", {
                method: "FindAll",
                provCode: 1
            }, function (data) {
                $("#tabletest tbody").empty();
                $("#tabletest thead").show();
                $.each(data, function (i, d) {
                    $("#tabletest tbody").append("<tr><td>" + d.id + "</td><td>" + d.sex + "</td><td>" + d.username + "</td><td>"
                        + d.birthday + "</td><td>" + d.address +
                        "</td><td>" + "<button cid =\"" + d.id + "\" onclick='updateUser(this)'>更新</button>" +
                        "<button cid =\"  " + d.id + "  \" onclick='deleteUser(this)'>删除</button>" + "</td></tr>");
                });
            }, 'json');
        };

        function deleteUser(obj) {
            var id = $(obj).attr('cid');
            $.get("AjaxServlet?id=" + id, {
                method: "delete",
            }, function (data) {
                alert(data);
                ajaxFun();
            }, 'json');
        };

        function ajaxSelect() {
            var provCode = $("#userId").val();
            $.get("AjaxServlet", {
                method: "findById",
                provCode: provCode
            }, function (data) {
                $("#tabletest tbody").empty()
                $("#tabletest thead").show();
                if (data.length != 0) {
                    $.each(data, function (i, d) {
                        $("#tabletest tbody").append("<tr><td>" + d.id + "</td><td>" + d.sex + "</td><td>" + d.username + "</td><td>"
                            + d.birthday + "</td><td>" + d.address +
                            "</td><td>" + "<button cid =\"" + d.id + "\" onclick='updateUser(this)'>更新</button>" +
                            "<button cid =\"  " + d.id + "  \" onclick='deleteUser(this)'>删除</button>" + "</td></tr>");
                    });
                } else {
                    $("#tabletest thead").hide();
                    $("#tabletest tbody").append("查找不到ID为" + provCode + "的人员信息");
                }
            }, 'json');
        };

        function ajaxAdd() {
            art.dialog({
                title: '添加用户',
                content: '<form id = "form1" >姓名：<input type="text" id="username" name="username" />' +
                    '性别：<input type="radio" name="sex" id="sex" value="1" /> 男\n' +
                    '<input type="radio" name="sex" id = "sex" value="2" /> 女</br>地址：<input type="text" id = "address" name ="address">' +
                    '生日：<input type="date" id="birthday" name = "birthday"></form>',
                width: 200,
                height: 200,
                cancelVal: '关闭',
                cancel: true,
                button: [{
                    name: "提交", callback: function () {
                        $.get("AjaxServlet?method=addUser", $('#form1').serialize(), function (data) {
                            alert(data);
                            ajaxFun();
                        })
                    }
                }]
            });
        }

        function updateUser(obj) {
            var id = $(obj).attr('cid');
            art.dialog({
                title: '更新用户',
                content: '<form id = "form2" >姓名：<input type="text" id="username" name="username" />' +
                    '性别：<input type="radio" name="sex" id="sex" value="1" /> 男\n' +
                    '<input type="radio" name="sex" id = "sex" value="2" /> 女</br>地址：<input type="text" id = "address" name ="address">' +
                    '生日：<input type="date" id="birthday" name = "birthday"></form>',
                width: 200,
                height: 200,
                cancelVal: '关闭',
                cancel: true,
                button: [{
                    name: "提交", callback: function () {
                        $.get("AjaxServlet?method=Update&id=" + id, $('#form2').serialize(), function (data) {
                            alert(data);
                            ajaxFun();
                        })
                    }
                }]
            });
        }

    </script>
</head>
<body>
<% request.setCharacterEncoding("UTF-8");%>
1:请输入用户id <input type="text" id="userId" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" placeholder="输入编号查询人员信息"/>
<button type="button" id="select" onclick="ajaxSelect()">查询</button>
</br>
2.
<button type="button" id="allId" onclick="ajaxFun()">查询所有用户</button>
</br>
3.
<button type="button" id="addId" onclick="ajaxAdd()">添加新用户</button>
</br>
============结果输出区============
</br>
<table border="1" id='tabletest'>
    <thead>
    <td>ID</td>
    <td>性别</td>
    <td>姓名</td>
    <td>生日</td>
    <td>地址</td>
    <td>操作</td>
    </thead>
    <tbody>

    </tbody>
</table>
</body>
</html>