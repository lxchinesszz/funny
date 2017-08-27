<!DOCTYPE html>
<!-- release v4.3.6, copyright 2014 - 2017 Kartik Visweswaran -->
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <title>趣萌宠物后台管理</title>
    <link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/default.css">
    <link href="/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
    <script>
        window.jQuery || document.write('<script src="/js/jquery.min.js"><\/script>')
    </script>
    <script src="/js/fileinput.js" type="text/javascript"></script>
    <!--简体中文-->
    <script src="/js/locales/zh.js" type="text/javascript"></script>
    <!--繁体中文-->
    <script src="/js/locales/zh-TW.js" type="text/javascript"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <!--引入七牛图片sdk-->
    <script src="/js/qiniu.min.js" type="text/javascript"></script>

    <style>
        body {
            padding-top: 70px;
        }
    </style>
</head>

<body>
<!--导航栏目-->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <!-- 导航头不，当浏览器像素太小，显示 -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">趣萌宠物后台管理</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="#"><a href="#"> 图片上传 <span class="sr-only">(current)</span></a></li>
                <li><a href="#">图库</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">关于 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">扩展中...</a></li>
                        <li><a href="#">扩展中...</a></li>
                        <li><a href="#">扩展中...</a></li>
                    </ul>
                </li>
            </ul>


        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>


<!--日期组件-->

<div class="htmleaf-container">

    <div class="container">
        <div class="row" style="min-height: 80px;margin-top: 10px;">

            <div class="col-md-5">
                <h3>请输入一段话:</h3>
                <div class="mytext">
                    <input type="text" id="text" class="form-control" placeholder="您输入的字符不能超过20个汉字">
                </div>
            </div>

            <div class="col-md-2">
                <h3>作者:</h3>
                <div class="author">
                    <input type="text" id="author" class="form-control" placeholder="作者">

                </div>
            </div>

            <div class="col-md-4">
                <h3>请选择一个展示图片的日期：</h3>
                <div class="input-group date datepicker">
                    <input type="text" id="mydate" class="form-control">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th "></span>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<!--图片上传组件-->
<div class="htmleaf-container">
    <div class="container kv-main">
        <div class="page-header">
            <h3>请选择您要上传的图片</h3>
        </div>
        <hr>
        <form enctype="multipart/form-data">
            <label>支持多种</label>
            <input id="file-zh" name="file-zh[]" type="file" multiple>
        </form>
        <hr>
        <br>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-4"> </div>
        <div class="col-md-4"><p><button id="fat-btn" data-loading-text="loading..." class="btn btn-primary">提交</button></p></div>
        <div class="col-md-4"> </div>
    </div>

</div>

</body>
<script>
    $('#file-zh').fileinput({
        language: 'zh',
        uploadUrl: '#',
        allowedFileExtensions: ['jpg', 'png', 'gif'],
    });


    $(".btn-warning").on('click', function () {
        if ($('#file-4').attr('disabled')) {
            $('#file-4').fileinput('enable');
        } else {
            $('#file-4').fileinput('disable');
        }
    });
    $(".btn-info").on('click', function () {
        $('#file-4').fileinput('refresh', {previewClass: 'bg-info'});
    });
    /*
    $('#file-4').on('fileselectnone', function() {
        alert('Huh! You selected no files.');
    });
    $('#file-4').on('filebrowse', function() {
        alert('File browse clicked for #file-4');
    });
    */
    $(document).ready(function () {
        $("#test-upload").fileinput({
            'showPreview': false,
            'allowedFileExtensions': ['jpg', 'png', 'gif'],
            'elErrorContainer': '#errorBlock'
        });
        /*
        $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
            alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
        });
        */
    });

</script>


<script type="text/javascript" src="/dist/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="/dist/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script type="text/javascript">
    $(function () {
        $('.datepicker').datepicker({
            language: 'zh-CN'
        });
        //获取token
        alert(111)
        var tokenJson;

        $('#fat-btn').click(function(){
            //获取token
            $.ajax({
                type:"GET",
                url:"http://127.0.0.1:8081/funnyanimal/v1/image/token",
                dataType:"json",
                async: false,
                success:function(data){
                    var str = JSON.stringify(data);
                    tokenJson=data['data']['token'];
                }
            });

            //获取图片 作者 文本 日期
            var author=$('#author').val()
            var text=$('#text').val()
//            var fileName=

                    //发送到七牛服务器


                    //发送到后台
                    $.ajax({
                        type: 'POST',
                        url:'http://127.0.0.1:8081/admin/add/image',
                        async: false, // 使用同步方式
                        // 1 需要使用JSON.stringify 否则格式为 a=2&b=3&now=14...
                        // 2 需要强制类型转换，否则格式为 {"a":"2","b":"3"}
                        data: JSON.stringify({
                            "fileName":'test',
                            "date":$('#mydate').val(),
                            "author":$('#author').val(),
                            "text":$('#text').val()
                        }),
                        crossDomain:true,
                        dataType:"json",
                        contentType: "application/json; charset=utf-8",
                        success: function(data) {
                            var str = JSON.stringify(data);
                            var msg=data['message'];
                            alert(msg)
                        } // 注意不要在此行增加逗号
                    });


            var imgageFoam =$(".file-footer-caption")

            alert(imgageFoam[0].text())

        })



    });

</script>

</html>