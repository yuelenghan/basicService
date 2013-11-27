<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="scripts/jquery.min.js"></script>
    <script src="scripts/jquery.ui.widget.js"></script>
    <script src="scripts/jquery.iframe-transport.js"></script>
    <script src="scripts/jquery.fileupload.js"></script>
    <title>文件上传</title>
</head>
<body>
<%--<form action="user/fileUpload" method="post" enctype="multipart/form-data">--%>
<%--<form action="contacts/uploadFile" method="post" enctype="multipart/form-data">
    <input type="file" name="fileUpload" />
    <input type="submit" value="上传" />
</form>--%>
<%--<input id="fileupload" type="file" name="fileUpload" data-url="contacts/uploadFile">--%>
<script type="text/javascript">
    $(function () {
        $('#fileupload').fileupload({
            dataType: 'json',
            done: function (e, data) {
                alert(data.result.msg);
            }
        });
    });
</script>
</body>
</html>  