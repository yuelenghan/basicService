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
    <title>通讯录</title>
    <link rel="stylesheet" type="text/css" href="/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/css/demo.css">
    <script type="text/javascript" src="/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.easyui.min.js"></script>
    <script src="/scripts/jquery.ui.widget.js"></script>
    <script src="/scripts/jquery.iframe-transport.js"></script>
    <script src="/scripts/jquery.fileupload.js"></script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true,border:false" style="width: 200px;">
        <div id="contactsTypePanel" class="easyui-panel" title="通讯录类别" style="padding:10px;"
             data-options="fit:true,border:false,tools:'#contactsTypeTool'">
            <ul id="contactsTypeTree"></ul>
            <script type="text/javascript">
                $("#contactsTypeTree").tree({
                    url: 'contactsType/getContactsTypeTree',
                    method: 'get',
                    animate: true,
                    onAfterEdit: function (node) {
                        afterEditTreeNode(node);
                    },
                    onClick: function (node) {
                        //alert(node.text);

                        // 分页加载通讯录列表
                        $('#contactsData').datagrid({
                            url: 'contacts/getContactsByPage?contactsTypeId=' + node.id
                        });
                        // 设置分页
                        var p = $('#contactsData').datagrid('getPager');
                        $(p).pagination({
                            pageSize: 10,//每页显示的记录条数，默认为10
                            pageList: [10, 20, 30, 40, 50],//可以设置每页记录条数的列表
                            beforePageText: '第',//页数文本框前显示的汉字
                            afterPageText: '页    共 {pages} 页',
                            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
                        });
                    }
                });
            </script>
        </div>
    </div>

    <div data-options="region:'center',border:false">
        <div id="contactsPanel" class="easyui-panel" title="通讯录" style="padding:10px;"
             data-options="fit:true,border:false">

            <div id="importPanel" class="easyui-panel" title="批量导入" style="padding:10px;">
                <a href="contacts/downloadTemplate?fileName=通讯录模板.xls">下载导入模板</a>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="file" name="fileUpload" data-url="contacts/uploadFile" id="fileUpload"
                       onchange="uploadFile()"/>
                <img src="themes/default/images/loading.gif" style="display: none" id="loading">
                <input type="button" value="导入" id="importBtn" style="display: none;" onclick="startImport()">
            </div>


            <table id="contactsData">
                <thead>
                <tr>
                    <th data-options="field:'id', width:60">id</th>
                    <th field="name" width="100" editor="text">姓名</th>
                    <th field="idCard" width="180" editor="text">身份证号</th>
                    <th field="phone" width="120" editor="text">电话号码</th>
                    <th field="email" width="200" editor="text">邮箱</th>
                </tr>
                </thead>
            </table>
            <script type="text/javascript">
                // 初始化dataGrid
                $('#contactsData').datagrid({
                    title: '通讯录列表',
                    iconCls: 'icon-save',//图标
                    url: 'contacts/getContactsByPage?contactsTypeId=0',
                    rownumbers: true,//行号
                    pagination: true,
                    toolbar: '#contactsTool',
                    onAfterEdit: afterEditContacts
                });

                // 设置分页
                var p = $('#contactsData').datagrid('getPager');
                $(p).pagination({
                    pageSize: 10,//每页显示的记录条数，默认为10
                    pageList: [10, 20, 30, 40, 50],//可以设置每页记录条数的列表
                    beforePageText: '第',//页数文本框前显示的汉字
                    afterPageText: '页    共 {pages} 页',
                    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
                });
            </script>
        </div>
    </div>
</div>
<div id="contactsTypeTool">
    <a href="javascript:void(0)" class="icon-add" onclick="openContactsTypeDlg()"></a>
    <a href="javascript:void(0)" class="icon-edit" onclick="editTreeNode()"></a>
    <a href="javascript:void(0)" class="icon-remove" onclick="removeTreeNode()"></a>
</div>
<div id="contactsTypeDlg" class="easyui-dialog" title="增加通讯录类别"
     data-options="width:400,left:500,top:200,closable:true,modal:true,closed:true">
    <div style="padding:10px 0 10px 60px">
        <table>
            <tr>
                <td>类别名称:</td>
                <td><input class="easyui-validatebox" type="text" id="contactsTypeName"
                           data-options="required:true"></td>
            </tr>
        </table>
    </div>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
           onclick="addTreeNode()">保存</a>
    </div>
</div>
<div id="contactsTool">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openContactsDlg()"></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editContacts()"></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveEdit()"></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeContacts()"></a>
</div>
<div id="contactsDlg" class="easyui-dialog" title="增加通讯录人员"
     data-options="width:400,left:500,top:200,closable:true,modal:true,closed:true">
    <div style="padding:10px 0 10px 60px">
        <table>
            <tr>
                <td>姓名:</td>
                <td><input class="easyui-validatebox" type="text" id="contactsName"
                           data-options="required:true"></td>
            </tr>
            <tr>
                <td>身份证号:</td>
                <td><input class="easyui-validatebox" type="text" id="contactsIdCard"
                           data-options="required:true"></td>
            </tr>
            <tr>
                <td>手机号码:</td>
                <td><input class="easyui-validatebox" type="text" id="contactsPhone"
                           data-options="required:true"></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input class="easyui-validatebox" type="text" id="contactsEmail"></td>
            </tr>
        </table>
    </div>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
           onclick="addContacts()">保存</a>
    </div>
</div>
</body>
</html>