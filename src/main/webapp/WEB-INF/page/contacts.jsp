<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-11-15
  Time: 下午2:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>基础服务</title>
    <link rel="stylesheet" type="text/css" href="/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/css/demo.css">
    <script type="text/javascript" src="/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.easyui.min.js"></script>

</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true,border:false" style="width: 200px;">
        <div id="p" class="easyui-panel" title="通讯录类别" style="padding:10px;"
             data-options="fit:true,border:false,tools:'#contactsTypeTool'">
            <ul id="contactsTypeTree" class="easyui-tree" data-options="
                            url:'contactsType/getContactsTypeTree'
                            ,method:'get',
                            animate:true"></ul>
        </div>

    </div>

    <div data-options="region:'center',border:false"></div>
</div>
<div id="contactsTypeTool">
    <a href="javascript:void(0)" class="icon-add" onclick="addTreeNode()"></a>
    <a href="javascript:void(0)" class="icon-edit" onclick="editTreeNode()"></a>
    <a href="javascript:void(0)" class="icon-cancel" onclick="removeTreeNode()"></a>
</div>
</body>
</html>