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
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css">
    <link href="https://cdn.bootcss.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
    <style type="text/css">
        #loginModal{
            margin: 0 auto;
            position: absolute;
            top: 50%;
            -webkit-transform: translateY(-50%);
            -moz-transform:  translateY(-50%);
            -ms-transform:  translateY(-50%);
            -o-transform:  translateY(-50%);
            transform:  translateY(-50%);
            background-color: #eee;
             text-align: center;
        }
        

    </style>
</head>
<body>
    <div class="container" id="loginModal">
        <div class="row">
            <form role="form" method="post" id="form">
                <div class="form-group" >
                    <label for="name" >Username</label>
                    <div class="input-group">
                        <div class="input-group-addon">
                            <i class="fa fa-user"></i>
                        </div>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Name" autocomplete="off"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="input-group">
                        <div class="input-group-addon">
                            <i class="fa fa-key"></i>
                        </div>
                        <input type="password" class="form-control" id="password" name="password"  placeholder="Paasword" autocomplete="off"/>
                    </div>
                </div>
               
                <div class="form-group">
                    <button class="btn btn-primary btn-lg col-md-6" id="btn_login" type="submit" >登录</button>
                </div>
            </form>
            
        </div>
    </div>
</body>
</html>