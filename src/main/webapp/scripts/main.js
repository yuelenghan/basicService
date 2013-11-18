/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-15
 * Time: 上午10:52
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function () {
    tabInit();
    setTreeListener();
});

function tabInit() {
    $("#main").tabs("add", {
            title: "制作手机报",
            href: "test.jsp",
            closable: true
        }
    );
}

function setTreeListener() {
    $('#tree').tree({
        onClick: function (node) {
//            alert(node.text);
//            alert(node.attributes.p1);
            if (node && node.attributes) {
                // alert(node.attributes.url);
                if ($("#main").tabs("exists", node.text)) {
                    $("#main").tabs("select", node.text);
                } else {
                    $("#main").tabs("add", {
                            title: node.text,
                            href: node.attributes.url,
                            closable: true
                        }
                    );
                }
            }
        }
    });
}





