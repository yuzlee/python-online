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
            height: 80%;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <input type="button" class="btn" id="run_code" value="Run" />
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <textarea id="code_area" class="form-control editor"></textarea>
            </div>
            <div class="col-md-6">
                <textarea id="result_area" class="form-control editor"></textarea>
            </div>
        </div>
    </div>


    <script>
        var socket = new WebSocket("ws://<%=request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath()%>/python/server");
        socket.onmessage = function (m) {
            var info = m.data;
            var content = $("#result_area").val();
            if (content !== "") {
                info = content + '\n' + info;
            }
            $("#result_area").val(info);
        }
        socket.onclose = function (p1) {
            alert("Connection closed.");
        }

        var sendCode = function (code) {
            console.log(code);
            socket.send(code);
        }
        $("#run_code").click(function () {
            $("#info_text").val("");
            $("#result_area").val("");

            var code = $("#code_area").val();
            sendCode(code);
        });
    </script>
</body>
</html>
