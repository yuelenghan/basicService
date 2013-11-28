/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-27
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
function openMaterialTypeDlg() {
    $("#materialTypeName").val("");
    $("#materialTypeDlg").dialog("open");
}

function addMaterialType() {
    var name = $("#materialTypeName").val().trim();
    if (name == "") {
        $.messager.alert("错误", "请填写素材类别名称！", "error");
        return;
    }
    $("#materialTypeDlg").dialog("close");

    $.ajax({
        url: "materialType/addMaterialType",
        type: "post",
        data: "name=" + name,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            if (data && data.code == 1) {
                showMessage();
            } else {
                $.messager.alert("错误", "增加素材类别失败！", "error");
            }
            $("#materialTypeList").tree("reload");
        }
    });
}

function removeMaterialType() {
    var node = $("#materialTypeList").tree("getSelected");
    //alert(node.id);
    if (node) {
        $.messager.confirm('确认删除', '确定删除此素材类别？', function (r) {
            if (r) {
                // 调用服务器端的方法，删除数据库中的数据
                $.ajax({
                    url: "materialType/removeMaterialType",
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
                        $("#materialTypeList").tree("reload");
                    }
                });

            }
        });
    } else {
        $.messager.alert("警告", "请选择素材类别！", "warning");
    }
}

function editMaterialType() {
    var node = $("#materialTypeList").tree("getSelected");
    if (node) {
        $("#materialTypeList").tree('beginEdit', node.target);
    }
}

function afterEditMaterialType(node) {
    $.ajax({
        url: "materialType/updateMaterialType",
        type: "post",
        data: "id=" + node.id + "&name=" + node.text,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            if (data && data.code == 1) {
                showMessage();
            } else {
                $.messager.alert("错误", "编辑素材类别失败！", "error");
            }
            $("#materialTypeList").tree("reload");
        }
    });
}

function openMaterialTextDlg() {
    var node = $("#materialTypeList").tree("getSelected");
    if (node) {
        $("#materialTitleAdd").val("");
        $("#materialTextAdd").val("");
        $("#materialTagAdd").val("");
        $("#materialTextAddDlg").dialog("open");
    } else {
        $.messager.alert("错误", "请选择素材类别！", "error");
    }
}

function addMaterialText() {
    var node = $("#materialTypeList").tree("getSelected");
    var materialTypeId = node.id;
    var title = $("#materialTitleAdd").val().trim();
    var text = $("#materialTextAdd").val().trim();
    var tag = $("#materialTagAdd").val().trim();

    if ($("#materialTitleAdd").validatebox("isValid") && $("#materialTextAdd").validatebox("isValid")) {
        $("#materialTextAddDlg").dialog("close");
        $.ajax({
            url: "material/addMaterial",
            type: "post",
            data: "materialTypeId=" + materialTypeId + "&title=" + title + "&text=" + text + "&type=文本",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data && data.code == 1) {
                    showMessage();
                } else {
                    $.messager.alert("错误", "增加文本素材失败！", "error");
                }
                $("#materialTextData").datagrid("reload");
            }
        });
    } else {
        $.messager.alert("错误", "校验失败！", "error");
    }
}

function removeMaterialText() {
    var row = $("#materialTextData").datagrid("getSelected");
    if (row) {
        $.messager.confirm('确认删除', '确定删除此文本素材？', function (r) {
            if (r) {
                // 调用服务器端的方法，删除数据库中的数据
                $.ajax({
                    url: "material/removeMaterial",
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
                        $("#materialTextData").datagrid("reload");
                    }
                });
            }
        });
    } else {
        $.messager.alert("警告", "请选择要删除的文本素材！", "warning");
    }
}

function startEditMaterialText() {
    var row = $("#materialTextData").datagrid("getSelected");
    if (row) {
        $.ajax({
            url: "material/getMaterial",
            type: "post",
            data: "id=" + row.id,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                // alert(data.id)
                $("#materialTitleEdit").val(data.title);
                $("#materialTextEdit").val(data.text);
                $("#materialTagEdit").val(data.tags);

                $("#materialTextEditDlg").dialog("open");
            },
            error: function (data, textStatus, jqXHR) {
                $.messager.alert("错误", "加载文本素材错误！", "error");
            }
        });
    } else {
        $.messager.alert("警告", "请选择要编辑的文本素材！", "warning");
    }
}

function editMaterialText() {
    var row = $("#materialTextData").datagrid("getSelected");

    var id = row.id;
    var title = $("#materialTitleEdit").val().trim();
    var text = $("#materialTextEdit").val().trim();
    var tag = $("#materialTagEdit").val().trim();

    if ($("#materialTitleEdit").validatebox("isValid") && $("#materialTextEdit").validatebox("isValid")) {
        $("#materialTextEditDlg").dialog("close");
        $.ajax({
            url: "material/updateMaterial",
            type: "post",
            data: "id=" + id + "&title=" + title + "&text=" + text,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data && data.code == 1) {
                    showMessage();
                } else {
                    $.messager.alert("错误", "编辑文本素材失败！", "error");
                }
                $("#materialTextData").datagrid("reload");
            }
        });
    } else {
        $.messager.alert("错误", "校验失败！", "error");
    }
}