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
    <title>Login</title>
    <link href="https://cdn.bootcss.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-4 text-center">
                <form role="form" method="post">
                    <div class="form-group">
                        <label for="name">Username</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password"  placeholder="Paasword">
                    </div>
                    <button type="submit" class="btn btn-default">Login</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
