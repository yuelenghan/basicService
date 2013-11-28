<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-11-27
  Time: 下午4:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>素材</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true,border:false" style="width: 200px;">
        <div id="materialTypePanel" class="easyui-panel" title="素材类别" style="padding:10px;"
             data-options="fit:true,border:false,tools:'#materialTypeTool'">
            <ul id="materialTypeList"></ul>
            <script type="text/javascript">
                $("#materialTypeList").tree({
                    url: 'materialType/listMaterialType',
                    method: 'get',
                    animate: true,
                    onAfterEdit: function (node) {
                        afterEditMaterialType(node);
                    },
                    onClick: function (node) {
                        // 分页加载文本素材列表
                        $('#materialTextData').datagrid({
                            url: 'material/getMaterialByPage?materialTypeId=' + node.id + '&type=文本'
                        });
                        // 设置分页
                        var p1 = $('#materialTextData').datagrid('getPager');
                        $(p1).pagination({
                            pageSize: 10,//每页显示的记录条数，默认为10
                            pageList: [10, 20, 30, 40, 50],//可以设置每页记录条数的列表
                            beforePageText: '第',//页数文本框前显示的汉字
                            afterPageText: '页    共 {pages} 页',
                            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
                        });

                        // 分页加载图片素材列表
                        $('#materialImageData').datagrid({
                            url: 'material/getMaterialByPage?materialTypeId=' + node.id + '&type=图片'
                        });
                        // 设置分页
                        var p2 = $('#materialImageData').datagrid('getPager');
                        $(p2).pagination({
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
        <div class="easyui-accordion" data-options="fit:true,border:false">
            <div title="文本素材" data-options="" style="overflow:auto;padding:10px;">
                <table id="materialTextData">
                    <thead>
                    <tr>
                        <th data-options="field:'id', width:60">id</th>
                        <th field="title" width="200" editor="text">标题</th>
                        <th field="text" width="400" editor="text">内容</th>
                    </tr>
                    </thead>
                </table>
                <script type="text/javascript">
                    // 初始化dataGrid
                    $('#materialTextData').datagrid({
                        title: '文本素材列表',
                        iconCls: 'icon-save',//图标
                        url: 'material/getMaterialByPage?materialTypeId=0&type=文本',
                        rownumbers: true,//行号
                        singleSelect: true,
                        pagination: true,
                        toolbar: '#materialTextTool'
                    });


                    // 设置分页
                    var p = $('#materialTextData').datagrid('getPager');
                    $(p).pagination({
                        pageSize: 10,//每页显示的记录条数，默认为10
                        pageList: [10, 20, 30, 40, 50],//可以设置每页记录条数的列表
                        beforePageText: '第',//页数文本框前显示的汉字
                        afterPageText: '页    共 {pages} 页',
                        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
                    });
                </script>
            </div>
            <div title="图片素材" data-options="" style="padding:10px;">
                <table id="materialImageData">
                    <thead>
                    <tr>
                        <th data-options="field:'id', width:60">id</th>
                        <th field="title" width="200" editor="text">标题</th>
                        <th field="image" width="400" editor="text">图片路径</th>
                    </tr>
                    </thead>
                </table>
                <script type="text/javascript">
                    // 初始化dataGrid
                    $('#materialImageData').datagrid({
                        title: '图片素材列表',
                        iconCls: 'icon-save',//图标
                        url: 'material/getMaterialByPage?materialTypeId=0&type=图片',
                        rownumbers: true,//行号
                        singleSelect: true,
                        pagination: true,
                        toolbar: '#materialImageTool'
                    });

                    // 设置分页
                    var p = $('#materialImageData').datagrid('getPager');
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
</div>
<div id="materialTypeTool">
    <a href="javascript:void(0)" class="icon-add" onclick="openMaterialTypeDlg()"></a>
    <a href="javascript:void(0)" class="icon-edit" onclick="editMaterialType()"></a>
    <a href="javascript:void(0)" class="icon-remove" onclick="removeMaterialType()"></a>
</div>
<div id="materialTypeDlg" class="easyui-dialog" title="增加素材类别"
     data-options="width:400,left:500,top:200,closable:true,modal:true,closed:true">
    <div style="padding:10px 0 10px 60px">
        <table>
            <tr>
                <td>类别名称:</td>
                <td><input class="easyui-validatebox" type="text" id="materialTypeName"
                           data-options="required:true"></td>
            </tr>
        </table>
    </div>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
           onclick="addMaterialType()">保存</a>
    </div>
</div>
<div id="materialTextTool">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openMaterialTextDlg()"></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="startEditMaterialText()"></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeMaterialText()"></a>
</div>

<!-- 增加文本素材的对话框 -->
<div id="materialTextAddDlg" class="easyui-dialog" title="增加文本素材"
     data-options="width:600,height:500,left:400,top:50,closable:true,modal:true,closed:true">
    <div style="padding:10px 0 10px 60px">
        <table>
            <tr>
                <td>标题:</td>
                <td><input class="easyui-validatebox" type="text" id="materialTitleAdd" style="width: 300px"
                           data-options="required:true"></td>
            </tr>
            <tr>
                <td>内容:</td>
                <td>
                    <textarea class="easyui-validatebox" id="materialTextAdd" style="width: 300px;height: 300px"
                              data-options="required:true"></textarea>
                </td>
            </tr>
            <tr>
                <td>标签:</td>
                <td><input class="easyui-validatebox" type="text" id="materialTagAdd"
                           data-options="required:false"></td>
            </tr>
        </table>
    </div>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
           onclick="addMaterialText()">保存</a>
    </div>
</div>

<!-- 编辑文本素材的对话框 -->
<div id="materialTextEditDlg" class="easyui-dialog" title="编辑文本素材"
     data-options="width:600,height:500,left:400,top:50,closable:true,modal:true,closed:true">
    <div style="padding:10px 0 10px 60px">
        <table>
            <tr>
                <td>标题:</td>
                <td><input class="easyui-validatebox" type="text" id="materialTitleEdit" style="width: 300px"
                           data-options="required:true"></td>
            </tr>
            <tr>
                <td>内容:</td>
                <td>
                    <textarea class="easyui-validatebox" id="materialTextEdit" style="width: 300px;height: 300px"
                              data-options="required:true"></textarea>
                </td>
            </tr>
            <tr>
                <td>标签:</td>
                <td><input class="easyui-validatebox" type="text" id="materialTagEdit"
                           data-options="required:false"></td>
            </tr>
        </table>
    </div>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
           onclick="editMaterialText()">保存</a>
    </div>
</div>
</body>
</html>