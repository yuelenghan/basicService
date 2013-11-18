/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-18
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
function addTreeNode() {

}

function removeTreeNode() {
    $.messager.confirm('确认删除', '确定删除此通讯录类别？', function (r) {
        if (r) {
            var node = $("#contactsTypeTree").tree("getSelected");
            if (node) {
                //alert(node.id);

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
    alert("edit");
}

function showMessage() {
    $.messager.show({
        title: '消息',
        msg: '删除成功！',
        showType: 'slide',
        style: {
            right: '',
            top: document.body.scrollTop + document.documentElement.scrollTop,
            bottom: ''
        }
    });
}

