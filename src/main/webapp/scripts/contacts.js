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
    var name = $("#contactsTypeName").val().trim();
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
        url: "contactsType/updateContactsType",
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

function openContactsTypeDlg() {
    var node = $("#contactsTypeTree").tree("getSelected");
    if (node) {
        $("#contactsTypeName").val("");
        $("#contactsTypeDlg").dialog("open");
    } else {
        $.messager.alert("警告", "请选择父节点！", "warning");
    }
}

function openContactsDlg() {
    var treeNode = $("#contactsTypeTree").tree("getSelected");
    if (treeNode) {
        if (treeNode.attributes.leaf.toUpperCase() == "Y") {
            $("#contactsName").val("");
            $("#contactsIdCard").val("");
            $("#contactsPhone").val("");
            $("#contactsEmail").val("");
            $("#contactsDlg").dialog("open");
        } else {
            $.messager.alert("警告", "选择的通讯录类别必须为叶子节点！", "warning");
        }
    } else {
        $.messager.alert("警告", "请选择通讯录类别！", "warning");
    }

}

function addContacts() {
    //alert("add");
    var treeNode = $("#contactsTypeTree").tree("getSelected");
    var pid = treeNode.id;
    var name = $("#contactsName").val().trim();
    var idCard = $("#contactsIdCard").val().trim();
    var phone = $("#contactsPhone").val().trim();
    var email = $("#contactsEmail").val().trim();
    if (name == "") {
        $.messager.alert("错误", "请填写姓名！", "error");
        return;
    }
    if (idCard == "") {
        $.messager.alert("错误", "请填写身份证号！", "error");
        return;
    }
    if (phone == "") {
        $.messager.alert("错误", "请填写手机号！", "error");
        return;
    }
    $("#contactsDlg").dialog("close");

    $.ajax({
        url: "contacts/addContacts",
        type: "post",
        data: "pid=" + pid + "&name=" + name + "&idCard=" + idCard + "&phone=" + phone + "&email=" + email,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            if (data && data.code == 1) {
                showMessage();
            } else {
                $.messager.alert("错误", "增加通讯录人员失败！", "error");
            }
            $("#contactsData").datagrid("reload");
        }
    });
}

function editContacts() {
    var row = $("#contactsData").datagrid("getSelections");
    for (var i = 0; i < row.length; i++) {
        var index = $("#contactsData").datagrid("getRowIndex", row[i]);
        //alert(index);
        // 开启行编辑
        $("#contactsData").datagrid("beginEdit", index);
    }

}

function saveEdit() {
    //alert(1);
    var row = $("#contactsData").datagrid("getSelections");
    for (var i = 0; i < row.length; i++) {
        var index = $("#contactsData").datagrid("getRowIndex", row[i]);
        //alert(index);
        // 开启行编辑
        $("#contactsData").datagrid("endEdit", index);
    }
}

function removeContacts() {
    var row = $("#contactsData").datagrid("getSelected");
    $.messager.confirm('确认删除', '确定删除此通讯录人员？', function (r) {
        if (r) {
            if (row) {
                // 调用服务器端的方法，删除数据库中的数据
                $.ajax({
                    url: "contacts/removeContacts",
                    type: "post",
                    data: "id=" + row.id,
                    dataType: "json",
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 1) {
                            // 删除成功
                            showMessage();
                        } else {
                            $.messager.alert("错误", "删除时发生错误！", "error");
                        }
                        $("#contactsData").datagrid("reload");
                    }
                });

            }
        }
    });
}

function afterEditContacts(rowIndex, rowData) {
    //alert("rowIndex = " + rowIndex + ", rowData = " + rowData.id);
    // var row = $('#contactsData').datagrid("getSelected");
    // alert(row.id);
    //alert("id = " + rowData.id + ", name = " + rowData.name + ", idCard = " + rowData.idCard);

    var id = rowData.id;
    var name = rowData.name;
    var idCard = rowData.idCard;
    var phone = rowData.phone;
    var email = rowData.email;
    if (name == "" || idCard == "" || phone == "") {
        $.messager.alert("警告", "姓名、身份证号、手机号不能为空！", "warning");
        $("#contactsData").datagrid("reload");
    } else {
        $.ajax({
            url: "contacts/updateContacts",
            type: "post",
            data: "id=" + id + "&name=" + name + "&idCard=" + idCard + "&phone=" + phone + "&email=" + email,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data && data.code == 1) {
                    showMessage();
                } else {
                    $.messager.alert("错误", "更新通讯录人员信息失败！", "error");
                }
                $("#contactsData").datagrid("reload");
            }
        });
    }
}

function uploadFile() {
    var treeNode = $("#contactsTypeTree").tree("getSelected");
    if (treeNode) {
        if (treeNode.attributes.leaf.toUpperCase() != "Y") {
            $.messager.alert("警告", "选择的通讯录类别必须为叶子节点！", "warning");
            return;
        }
    } else {
        $.messager.alert("警告", "请选择通讯录类别！", "warning");
        return;
    }

    // 隐藏loading或状态图片
    $("#loading").attr("style", "display:none");
    // 隐藏导入按钮
    $("#importBtn").attr("style", "display:none");

    var file = document.getElementById("fileUpload").files[0];
    var fileName = file.name;
    var fileExtension = fileName.substr(fileName.lastIndexOf(".") + 1).toUpperCase();
    if (fileExtension != "XLS" && fileExtension != "XLSX") {
        $.messager.alert("警告", "请选择正确的excel文件！", "warning");
        return;
    }
    // 显示loading的gif图片
    $("#loading").attr("src", "themes/default/images/loading.gif");
    $("#loading").attr("style", "display:inline");

    // 上传文件
    /* $('#fileUpload').fileupload({
     dataType: 'json',
     done: function (e, data) {
     alert(data.result.msg);
     }
     });*/

    // html5上传文件
    var fd = new FormData();
    fd.append("fileUpload", file);
    var xhr = new XMLHttpRequest();
    //xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    //xhr.addEventListener("abort", uploadCanceled, false);
    xhr.open("POST", "contacts/uploadFile");
    xhr.send(fd);
}

function uploadComplete(evt) {
    // 隐藏loading图片
//    $("#loading").attr("style", "display:none");

    // 把返回的字符串转为json
    var json = eval("(" + evt.target.responseText + ")");

    if (json.code == 1) {
        // 上传成功
        $("#loading").attr("src", "themes/default/images/loading_success.jpg");

        // 显示导入按钮
        $("#importBtn").attr("style", "display:inline");
    } else {
        $.messager.alert("错误", "上传失败！", "error");

        //显示上传失败的图片
        $("#loading").attr("src", "themes/default/images/loading_error.jpg");
    }
}

function uploadFailed() {
    $.messager.alert("错误", "上传失败！", "error");

    //显示上传失败的图片
    $("#loading").attr("src", "themes/default/images/loading_error.jpg");
}

function startImport() {
    // alert(1);
    var treeNode = $("#contactsTypeTree").tree("getSelected");
    if (treeNode) {
        if (treeNode.attributes.leaf.toUpperCase() != "Y") {
            $.messager.alert("警告", "选择的通讯录类别必须为叶子节点！", "warning");
            return;
        }
    } else {
        $.messager.alert("警告", "请选择通讯录类别！", "warning");
        return;
    }

    // 显示loading图片
    $("#loading").attr("src", "themes/default/images/loading.gif");
    $("#loading").attr("style", "display:inline");

    $.ajax({
        url: "contacts/batchImportContacts",
        type: "post",
        data: "contactsTypeId=" + treeNode.id,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            if (data && data.code == 1) {
                showMessage();
                // 显示成功图片
                $("#loading").attr("src", "themes/default/images/loading_success.jpg");

                // 隐藏导入按钮
                $("#importBtn").attr("style", "display:none");
            } else {
                $.messager.alert("错误", "导入通讯录人员信息失败！", "error");
                //显示失败的图片
                $("#loading").attr("src", "themes/default/images/loading_error.jpg");
            }
            $("#contactsData").datagrid("reload");
        }
    });
}
