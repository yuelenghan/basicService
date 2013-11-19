/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-18
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
/*$(document).ready(function () {
 $('#contactsTypeTree').tree({
 onBeforeEdit : function(node) {
 alert(2);
 },
 onAfterEdit : function(node){
 alert(1);
 // alert(node.text);  // alert node text property when clicked
 }
 });
 });*/

function addTreeNode() {
    var node = $("#contactsTypeTree").tree("getSelected");
    var name = $("#contactsTypeName").val();
    if (name == "") {
        $.messager.alert("错误", "请填写通讯录类别名称！", "warning");
        return;
    }
    $("#contactsTypeDlg").dialog("close");
    //alert(name);
    if (node) {
        $.ajax({
            url: "contactsType/addChild",
            type: "post",
            data: "id=" + node.id + "&name=" + name,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data && data.code == 1) {
                    showMessage();
                } else {
                    $.messager.alert("错误", "增加通讯录类别失败！", "error");
                }
                $("#contactsTypeTree").tree("reload");
            }
        });

    }
}

function removeTreeNode() {
    var node = $("#contactsTypeTree").tree("getSelected");
    if (node.attributes.root.toUpperCase() == "Y") {
        $.messager.alert("错误", "不能删除根节点！", "warning");
        return;
    }
    $.messager.confirm('确认删除', '确定删除此通讯录类别？', function (r) {
        if (r) {
            if (node) {
                // 调用服务器端的方法，删除数据库中的数据
                $.ajax({
                    url: "contactsType/removeContactsType",
                    type: "post",
                    data: "id=" + node.id,
                    dataType: "json",
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 1) {
                            // 删除成功
                            showMessage();
                        } else {
                            $.messager.alert("错误", "删除时发生错误！", "error");
                        }
                        $("#contactsTypeTree").tree("reload");
                    }
                });

            }
        }
    });

}

function editTreeNode() {
    var node = $("#contactsTypeTree").tree("getSelected");
    if (node) {
        $("#contactsTypeTree").tree('beginEdit', node.target);
    }
}

function afterEditTreeNode(node) {
    //alert(node.text);
    $.ajax({
        url: "contactsType/saveContactsType",
        type: "post",
        data: "id=" + node.id + "&name=" + node.text,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            if (data && data.code == 1) {
                showMessage();
            } else {
                $.messager.alert("错误", "编辑通讯录类别失败！", "error");
            }
            $("#contactsTypeTree").tree("reload");
        }
    });
}

function showMessage() {
    $.messager.show({
        title: '消息',
        msg: '操作成功！',
        showType: 'slide',
        style: {
            right: '',
            top: document.body.scrollTop + document.documentElement.scrollTop,
            bottom: ''
        }
    });
}

function openDlg() {
    var node = $("#contactsTypeTree").tree("getSelected");
    if (node) {
        $("#contactsTypeName").val("");
        $("#contactsTypeDlg").dialog("open");
    } else {
        $.messager.alert("警告", "请选择父节点！", "warning");
    }
}

