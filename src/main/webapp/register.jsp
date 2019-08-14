<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/font-awesome.min.css" type="text/javascript" rel="stylesheet">
    <link href="/css/bootsnav.css" type="text/css" rel="stylesheet">
    <link href="/css/normalize.css" type="text/css" rel="stylesheet">
    <link href="/css/css.css" rel="stylesheet" type="text/css">
    <script src="/js/jquery-1.11.0.min.js" type="text/javascript"></script>
    <script src="/js/jquery.step.js"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/js/bootsnav.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="/css/style.css" type="text/css"/>
    <script src="/js/jquery.js" type="text/javascript"></script>
    <!--[if IE]>
    <script src="/js/html5.js"></script><![endif]-->
    <title>注册</title>

    <style>
        .error {
            color: red
        }

    </style>

<%--    <script type="text/javascript">

        //自定义校验规则
        $.validator.addMethod(
            //规则的名称
            "checkUsername",

            //校验函数
            function (value, element, params) {

                var flag = false;

                //value:表单输入的内容
                //element：被校验的元素对象
                //params:规则对应的参数值

                //目的：对输入的username进行ajax校验
                $.ajax({
                    "async": false,
                    "url": "${pageContext.request.contextPath}/user/checkUsername",
                    "data": {"username": value},
                    "type": "POST",
                    "dataType": "json",
                    "success": function (data) {
                        flag = data;
                    }
                });
                //校验规则在false的情况下才会生效
                return !flag;
            }
        );



        $(function () {
            $("#").validate({
                rules: {
                    "username": {
                        "required": true,
                        "checkUsername": true,
                        "isLetter": true
                    },
                    "Verification": {
                        "required": true,
                        "checkcode": true
                    }
                },
                messages: {
                    "username": {
                        "required": "用户名不能为空",
                        "checkUsername": "用户名已经存在",
                        "isLetter": "只能使用字母和数字"
                    },
                    "Verification": {
                        "required": "",
                        "checkcode": ""
                    }
                }
            });
        });

    </script>--%>

<script type="text/javascript">





</script>

</head>

<body class="logobg_style">
<jsp:include page="/header.jsp"></jsp:include>
<div id="large-header" class="large-header login-page">
    <canvas id="demo-canvas" width="1590" height="711"></canvas>
    <div class="Retrieve_style">
        <div class="Retrieve-content step-body" id="myStep">
            <h1 class="title_name">用户注册</h1>
            <div class="step-header">
                <ul>
                    <li><p>输入电话</p></li>
                    <li><p>确认密码</p></li>
                    <li><p>成功</p></li>
                </ul>
            </div>
            <div class="step-content">
                <div class="step-list login_padding">
                    <form role="form" id="form_login" class="">
                        <div class="form-group clearfix">
                            <div class="input-group col-lg-8 col-md-8 col-xs-8">
                                <div class="input-group-addon"><i class="icon_phone"></i></div>
                                <input type="text" class="form-control text_phone" name="phone" id="phone"
                                       placeholder="输入手机号" autocomplete="off">
                            </div>
                            <div class="col-lg-4 col-md-4 col-xs-4 fl">
                                <input type="button" id="btn" class="btn_mfyzm" value="获取验证码" onclick="Sendpwd(this)"/>
                            </div>
                        </div>
                        <div class="form-group clearfix">
                            <div class="input-group">
                                <div class="input-group-addon"><i class="icon_yanzhen"></i></div>
                                <input type="text" class="form-control" name="Verification" id="Verification"
                                       placeholder="短信验证码" autocomplete="off">
                            </div>
                        </div>
                        <div class="tishi"></div>
                    </form>
                    <div class="form-group">
                        <button class="btn btn-danger btn-block btn-login" id="applyBtn">下一步</button>
                    </div>
                </div>


                <div class="step-list">
                    <form method="post" role="form" id="registForm" class="login_padding" action="${pageContext.request.contextPath}/user/register" >
                        <input type="hidden" name="telephone" id="telephone" value="">
                        <div class="form-group clearfix">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="icon_user"></i>
                                </div>

                                <input type="text" class="form-control" name="username" id="username" placeholder="用户名"
                                       autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group clearfix">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="icon_password"></i>
                                </div>
                                <input type="password" class="form-control" name="password" id="password"
                                       placeholder="输入密码" autocomplete="off">
                            </div>

                        </div>
                        <div class="form-group clearfix">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="icon_password"></i>
                                </div>
                                <input type="password" class="form-control" name="confirmpwd" id="confirmpwd"
                                       placeholder="再次密码" autocomplete="off">
                            </div>
                        </div>
                        <div class="tishis"></div>
                        <div class="form-group">
                            <a href="javascript:void(0);" onclick="regist()" type="submit" class="btn btn-danger btn-block btn-login"
                               id="submitBtn">下一步</a>
                        </div>
                    </form>
                </div>


                <div class="step-list">
                    <div class="ok_style center">
                        <h2><img src="images/ok.png"></h2>
                        <a href="${pageContext.request.contextPath}/default.jsp" class="btn btn-danger">首页</a>
                    </div>
                </div>



            </div>
        </div>
    </div>
</div>
<script src="/js/TweenLite/TweenLite.min.js"></script>
<script src="/js/TweenLite/EasePack.min.js"></script>
<script src="/js/TweenLite/rAF.js"></script>
<script src="/js/TweenLite/demo-1.js"></script>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
<script type="text/javascript">

</script>
