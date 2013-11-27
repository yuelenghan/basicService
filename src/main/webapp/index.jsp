<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>基础服务</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <script type="text/javascript" src="scripts/jquery.min.js"></script>
    <script type="text/javascript" src="scripts/jquery.easyui.min.js"></script>

    <script src="scripts/jquery.ui.widget.js"></script>
    <script src="scripts/jquery.iframe-transport.js"></script>
    <script src="scripts/jquery.fileupload.js"></script>

    <script type="text/javascript" src="scripts/main.js"></script>
    <script type="text/javascript" src="scripts/contacts.js"></script>
</head>
<body>
<div class="easyui-layout" style="width: auto;height: 600px">
    <div data-options="region:'north'" style="height:50px">
        <h2>手机报系统</h2>
    </div>
    <div data-options="region:'east',split:true" title="说明" style="width:180px;">
    </div>
    <div data-options="region:'west',split:true" title="功能区" style="width:150px;">
        <ul id="tree" class="easyui-tree" data-options="url:'json/tree.json',method:'get',animate:true"></ul>
    </div>
    <div data-options="region:'center',title:'主页面'">
        <div id="main" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        </div>
    </div>
</div>
</body>
</html>