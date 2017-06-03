<%--
  Created by IntelliJ IDEA.
  User: Rolrence
  Date: 2017/5/31
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Python Coder</title>
    <link href="https://cdn.bootcss.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>

    <style>
        .editor {
            height: 500px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                    <textarea id="code_area" class="form-control editor"></textarea>
            </div>
            <div class="col-md-6">
                <textarea id="result_area" class="form-control editor"></textarea>
            </div>
        </div>
        <div class="row">
            <div class="col-md-2">
                <input type="button" class="btn" id="run_code" value="Run" />
            </div>
            <div>
                <input type="text" id="info_text" class="form-control" />
            </div>
        </div>
    </div>


    <script>
        var socket = new WebSocket("ws://localhost:8080/python/server");
        socket.onmessage = function (m) {
            var info = m.data;
            var content = $("#result_area").val();
            $("#result_area").val(content + '\n' + info);
            $("#info_text").val(new Date().toLocaleTimeString());
        }
        $("#run_code").click(function () {
            var code = $("#code_area").val();
            console.log(code);
            socket.send(code);
        });
    </script>
</body>
</html>
