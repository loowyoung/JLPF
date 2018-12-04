$(function(){
    var validate1 = $("#forgetForm").validate({
        //debug: true, //调试模式取消submit的默认提交功能
        //errorClass: "label.error", //默认为错误的样式类为：error
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应
        onkeyup: false,
        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form
            changePassword();
        },
        rules:{
            username:{
                required:true
            },
            email:{
                required:true,
                email:true
            },
        },
        messages:{
            password:{
                required:"必填"
            },
            email:{
                required:"必填",
                email:"E-Mail格式不正确"
            },
        }

    });

    var validate2 = $("#newAdminForm").validate({
        //debug: true, //调试模式取消submit的默认提交功能
        //errorClass: "label.error", //默认为错误的样式类为：error
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应
        onkeyup: false,
        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form
            changeAdmin();
        },

        rules:{
            username:{
                required:true
            },
            newemail:{
                required:true,
                email:true
            },
        },
        messages:{
            username:{
                required:"必填"
            },
            newemail:{
                required:"必填",
                email:"E-Mail格式不正确"
            },
        }

    });

});

function changePassword() {
    $.ajax({
        type : "post",
        url: "/sendPassword",
        data: $('#forgetForm').serialize(),
        success: function (r) {
            if (r.code == 0) {
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                layer.msg(r.msg,{
                    icon: 1,
                    time:3000
                });
                parent.location.href = '/login';
            } else {
                layer.msg(r.msg);
            }
        },
    });
}

function changeAdmin() {
    $.ajax({
        type : "post",
        url: "/changeEmail",
        data: $('#newAdminForm').serialize(),
        success: function (r) {
            if (r.code == 0) {
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                layer.msg(r.msg,{
                    icon: 1,
                    time:3000
                });
                parent.location.href = '/login';
            } else {
                layer.msg(r.msg);
            }
        },
    });
}