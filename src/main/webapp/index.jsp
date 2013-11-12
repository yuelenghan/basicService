<!DOCTYPE html>
<html>
<head>
    <script src="scripts/jquery-1.10.2.js" language="javascript"></script>
    <title>test</title>
</head>
<body>
test<br>
<script type="text/javascript" language="javascript">
    function test() {
        // alert(1);
        $.ajax({
            url: "/basicService/contacts/updateContacts",
            dataType: "json",
            data: "id=1&name=ztt",
            type: "post",
            success: function (data, textStatus, jqXHR) {
                alert("success");
                // alert(data[0].name);
            }
        });
    }
</script>
<input type="button" value="test" onclick="test()">

</body>
</html>