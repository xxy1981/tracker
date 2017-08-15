<#import "/macro/header.ftl" as h>
<#import "/macro/menu.ftl" as m>
<#import "/macro/page.ftl" as p>
<#import "/macro/staticImport.ftl" as s>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<@s.staticImport />
    <style>
        html, body {
            background: #2c2c2c;
            height: 100%;
        }

        .login-logo {
            text-align: center;
            padding: 120px 0 10px 0;
        }

        .login-box {
            background: #FFF;
            margin: 0 auto;
            width: 320px;
        }

        .login-main {
            padding: 30px 20px;
        }

        .login-main input {
            width: 270px;
            height: 30px
        }

        .error-tip {
            color: #FF2048
        }
    </style>
</head>

<body>
<div class="login-logo"><#--img src="/images/bops-logo.jpg"/--></div>
<div class="login-box">
    <div class="login-main">
    	<span class="error-tip"></span>
        <input type="text" name="loginName" id="loginName" placeholder="账号"/><br/>
        <input type="password" name="password" id="password" placeholder="密码"/><br/>
        <a class="button button-primary" id="login-btn">登 录</a>
    </div>
</div>
<script>
    $(function () {
        var login_func = function () {

            var u = $("#loginName").val();
            var p = $("#password").val();
            if (u == '' || p == '') {
                $(".error-tip").html("账号、密码不能为空");
                return;
            }

            $.post("login?loginName=" + u + "&password=" + p, function (data) {
                if (data.code == 'success') {
                    location.href = '/partner';
                } else {
                    $(".error-tip").html(data.message);
                }
            }, "json");
        }

        $("#login-btn").click(login_func);

        $(document).keypress(function(event){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if(keycode == '13'){
                login_func();
            }
            event.stopPropagation();
        });
    });

</script>
</body>
</html>