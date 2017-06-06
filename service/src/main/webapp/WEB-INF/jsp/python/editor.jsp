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
      <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
      <style type="text/css">
        textarea{
          resize: none;
      }
      body{
          min-height:300px;
          padding-top:90px;
          background: #f6f6f6;
      }
      .panel-body{
        height: 80%;
    }
    .container{
      width:98%;
      padding-right:15px;
      padding-left:15px;
      margin-right:auto;
      margin-left:auto
  }
</style>
</head>
<body>
    <nav class="navbar navbar-default navbar-fixed-top" style="background: #96b97d;">
      <div class="container">
        <div class="navbar-header ">
            <h1>
               <a style="display: block;color: white" href="http://59.64.78.31:8080/rolrence/user/login">Python-online</a>
           </h1>
       </div>
   </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default"> 
              <div class="panel-heading">
                <div class="row">
                  <div class="col-xs-6">
                    <button type="button" class="btn btn-default">源代码</button>
                </div>
                <div class="col-xs-6 text-right">
                    <button type="button" class="btn btn-success" id="run_code">
                      <span class="glyphicon glyphicon-send"></span> 
                      点击运行
                  </button>
              </div>
          </div>
      </div>
      <div class="panel-body">
         <textarea class="form-control "  id="code_area" style="height: 100%"></textarea>
     </div>
 </div>
</div>
<div class="col-md-6">
    <div class="panel panel-default"> 
      <div class="panel-heading">
          <div class="row">
            <div class="col-xs-6">
                <button type="button" class="btn btn-default">运行结果</button>
            </div>
            <div class="col-xs-6 text-right">
                <button type="button" class="btn btn-success" id="run_code" 
                onclick="{ if(confirm('确定跳转？'))location.href='http://59.64.78.31:8080/rolrence/user/login'}">登陆</button>
            </div>
        </div>
    </div>
    <div class="panel-body">
        <textarea class="form-control " id="result_area" style="height: 100%"></textarea>
    </div>
</div>
</div>
</div>
<footer>
  <div class="row">
     <div class="col--12">
        <hr>
        <p style="text-align: right;">Copyright @<a target="_blank" href="//www.rolrence.cn/tag/note//">Rolrence</a>
        </p>
    </div>
</div>
</footer>
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