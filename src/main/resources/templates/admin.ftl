<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<#include "head.html">

<body class="style-2">

<div class="container">
    <!--登陆框-->
    <div class="row">
        <div class="col-md-4">

            <!-- Start Sign In Form -->
            <form action="/admin/index" class="fh5co-form animate-box" data-animate-effect="fadeInLeft" method="post" role="form">
                <h5>欢迎登陆</h5>
                <div class="form-group">
                    <label for="username" class="sr-only">Username</label>
                    <input type="text" class="form-control" id="username" placeholder="用户名" autocomplete="off" name="username">
                </div>
                <div class="form-group">
                    <label for="password" class="sr-only">Password</label>
                    <input type="password" class="form-control" id="password" placeholder="用户密码" autocomplete="off" name="password">
                </div>
                <div class="form-group">
                    <label for="remember"><input type="checkbox" id="remember" name="rememberMe"> 记住密码</label>
                    <span class="alert-warning">${message?if_exists }</span>
                </div>
                <#--<div class="form-group">-->
                    <#--<input type="submit" value="Sign In" class="btn btn-primary">-->
                <#--</div>-->
                <button type="submit" class="btn btn-primary" >Sign In</button>
            </form>

        </div>
    </div>
    <div class="row" style="padding-top: 60px; clear: both;">
        <div class="col-md-12 text-center">
            <p>
                <small>&copy; All Rights Reserved <a href="http://www.cssmoban.com/" target="_blank" title="趣萌宠物后台管理系统">趣萌宠物后台管理系统</a>
                    - Collect from <a href="http://www.cssmoban.com/" title="趣萌宠物后台管理系统" target="_blank">chinesszz</a>
                </small>
            </p>
        </div>
    </div>
</div>



<#include "font.html">
</body>

</html>

