<%@ page import="net.sonclub.shiro.User; net.sonclub.Player" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'auth.css')}" type="text/css">
    </head>
    <body>
        <div class="row">
            <div class="col-xs-12 col-sm-offset-3 col-sm-6 col-md-offset-4 col-md-4">
                <div class="card">
                    <form role="form" class="auth-form">
                        <h3>队员信息完善</h3>
                        <div class="first-group">
                            <input type="text" class="form-control" name="nickName" value="${fieldValue(bean:Player, field:'user.nickName')}" placeholder="昵称">
                        </div>
                        <div class="auth-group">
                            <input type="number" class="form-control" id="exampleInputEmail1" placeholder="球衣号码">
                        </div>
                        <div class="auth-group">
                            <input type="text" class="form-control" id="exampleInputEmail1" placeholder="队名">
                        </div>
                        <div class="submit-btn">
                            <button type="submit" style="font-size: 20px" class="btn btn-primary btn-block">确认</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>