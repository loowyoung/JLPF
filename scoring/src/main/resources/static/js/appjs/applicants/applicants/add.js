$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/applicants/applicants/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
    $('#signupForm').validate({
		rules : {
			username : {
				required : true
			},
            job : {
                required : true
            },
            birthday : {
                required : true
            },
            idnumber : {
                required : true
            },
            phone : {
                required : true
            },
            mail : {
                required : true
            },
			politics : {
                required : true
            },
			graduationdate : {
                required : true
            },
            hometown : {
                required : true
            },
            languagelevel : {
                required : true
            },
			computerlevel : {
                required : true
            },
            resumeurl : {
                required : true
            },
			englishscore : {
                required : true
            }
		},
		messages : {
            username : {
				required : icon + "请输入姓名"
			},
            job : {
                required : icon + "请输入应聘岗位"
            },
            birthday : {
                required : icon + "请输入出生年月"
            },
            idnumber : {
                required : icon + "请输入身份证号"
            },
            phone : {
                required : icon + "请输入手机号"
            },
            mail : {
                required : icon + "请输入邮箱"
            },
            politics : {
                required : icon + "请输入政治面貌"
            },
            graduationdate : {
                required : icon + "请输入毕业时间"
            },
            hometown : {
                required : icon + "请输入生源地"
            },
            languagelevel : {
                required : icon + "请输入外语等级"
            },
            computerlevel : {
                required : icon + "请输入计算机等级"
            },
            resumeurl : {
                required : icon + "请输入简历地址"
            },
            englishscore : {
                required : icon + "请输入英语分数"
            }
		}
	})
}

//显示时间组件
layui.use('laydate', function(){
    var laydate = layui.laydate;
    laydate.render({
        elem: '#birthday'
        ,type: 'date'
        // ,min: 0
    });
    laydate.render({
        elem: '#graduationdate'
        ,type: 'date'
    });
});