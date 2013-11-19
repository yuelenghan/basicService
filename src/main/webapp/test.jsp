<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-11-14
  Time: 下午4:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-panel" title="增加通讯录类别" style="width:400px">
    <div style="padding:10px 0 10px 60px">
        <form id="contactsTypeForm" action="contactsType/addChild" method="post">
            <table>
                <tr>
                    <td>类别名称:</td>
                    <td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>