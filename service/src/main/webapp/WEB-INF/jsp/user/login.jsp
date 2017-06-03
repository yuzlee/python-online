<%--
  Created by IntelliJ IDEA.
  User: Rolrence
  Date: 2017/5/31
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
</head>
<body>
    <table>
        <tr>
            <td>Name:</td><td><input type="text" id="name" value="" /></td>
        </tr>
        <tr>
            <td>Password:</td><td><input type="password" id="password" value="" /></td>
        </tr>
        <tr>
            <td></td><td><input type="button" id="submit" value="Login" /></td>
        </tr>
    </table>

    <script>
        $("#submit").click(function () {
            $.ajax({
                type: "POST",
                url: "./login",
                data: {
                    name: $("#name").val(),
                    password: $("#password").val()
                },
                success: function (d) {
                    console.log(d);
                }
            })
        })
    </script>
</body>
</html>
