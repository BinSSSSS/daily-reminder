$(function() {
    // var header = $("meta[name='_csrf.parameterName']").attr("content");
    // var token = $("meta[name='_csrf.token']").attr("content");
    Auth.init({
        login_url : 'login',
        forgot_url : 'forgot'
    });

    var timer = null;
    var seconds;
    $("#send-vcode").click(function() {

        var validator = $('.register').data("bootstrapValidator"); // 获取validator对象

        // 触发邮箱的验证
        validator.validateField("email");

        // 未通过验证直接返回
        if (!validator.isValidField("email"))
            return;

        // 表示用户更改了前台代码并点击了按钮，此时只需要将按钮设置为不可用即可
        if (timer != null) {
            $(this).attr("disabled",true);
            return;
        }
        // 设置为不可再次点击
        $(this).attr("disabled",true);
        $(this).text("后台验证中");

        $.ajax({
            url : '/send-email-vcode',
            method : 'post',
            data : {
                email : $(".register").find("input[name=email]").val()
            },
            dataType : 'json',
            // beforeSend : function(xhr) {
            // xhr.setRequestHeader('X-CSRF-TOKEN', token); // POST,
            // header
            // 'X-CSRF-TOKEN'
            // },
            success : function(data) {   
            	
//            	console.log("返回的数据为: " + data);
//            	console.log(data);
                // 当发送之后，拿到下次发送需要等待的时间
                if (data.seconds > 0) {

                    // 设置定时器并进行等待
                    seconds = data.seconds;
                    $("#send-vcode").text(--seconds + "秒后重发");
                    timer = setInterval(function() {
                        if (seconds == 0) {
                            clearInterval(timer);
                            timer = null;
                            $("#send-vcode").removeAttr("disabled");
                            $("#send-vcode").text("发送验证码");
                        } else {
                            $("#send-vcode").text(--seconds + "秒后重发");
                        }
                    }, 1000);
                }
                document.getElementById("tips-modal-btn").click();
                $(".modal-tips").text(data.msg);
            }
        });
    });

    // 表单验证相关
    $(".register").bootstrapValidator({
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            // invalid: 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        fields : {
            username : {
                validators : {
                    notEmpty : {
                        message : '用户名不能为空'
                    },
                    stringLength : {
                        min : 4,
                        max : 12,
                        message : '用户名长度必须在4到12位之间'
                    },
                    regexp : {
                        regexp : /^[a-zA-Z0-9_]+$/,
                        message : '用户名只能包含大写、小写、数字和下划线'
                    },
                    remote : {
                        message : '用户名已经被注册',
                        url : 'verify-username',
                        // 输入的延时验证,单位毫秒
                        delay : 1500,
                        type : 'post'

                    }
                }
            },
            password : {
                validators : {
                    notEmpty : {
                        message : '密码不能为空'
                    },
                    stringLength : {
                        min : 6,
                        max : 16,
                        message : '密码长度在6-16位之间'
                    }
                }
            },
            email : {
                message : '邮箱格式不合法',
                validators : {
                    notEmpty : {
                        message : '邮箱不能为空'
                    },
                    remote : {
                        message : '当前邮箱已经注册!',
                        url : 'verify-email',
                        delay : 1000,
                        type : 'post'
                    }

                }
            },
            code : {
                message : '验证码验证失败',
                validators : {
                    notEmpty : {
                        message : '验证码不能为空'
                    },
                    regexp : {
                        regexp : /^[0-9]+$/,
                        message : '验证码只能为数字'
                    },
                    stringLength : {
                        min : 4,
                        message : '验证码最少为4位'
                    }
                }
            }
        },
        submitHandler : function(validator, form, submitButton) {
            // alert("submit");
        }
    });

    // 在注册按钮提交的时候。 进行表单的验证
    $(".register")
        .submit(
            function() {

                var validator = $('.register').data(
                    "bootstrapValidator"); // 获取validator对象

                // 激活手动验证
                // validator.validate();//手动触发验证

                // 如果验证通过的话，则提交注册信息到后台
                if (validator.isValid()) {
                    $.ajax({
                        url : 'register',
                        method : 'post',
                        data : $(".register").serialize(),
                        dataType : 'json',
                        success : function(data) {
                            document.getElementById(
                                "tips-modal-btn").click();
                            $(".modal-tips").text(data.msg);
                            // 如果注册成功
                            if (data.success) {
                                console.log("成功");
                                // 清空所有表单字段
                                $(".register").find("input")
                                    .val("");
                                // 跳转到登陆页面
                                document
                                    .getElementsByClassName("login-link")[0]
                                    .click();
                            }
                        },
                        error : function(data) {
                            document.getElementById(
                                "tips-modal-btn").click();
                            $(".modal-tips").text(data.msg);

                        }
                    })
                }
            })
})