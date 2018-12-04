$().ready(function() {
    validateRule();
    var rows = parent.$('#exampleTable').bootstrapTable('getSelections'); // 返回父窗口所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要修改的数据");
        return;
    }
    var ids = new Array(),names = new Array();
    // 遍历所有选择的行数据，取每条数据对应的ID
    $.each(rows, function(i, row) {
        ids[i] = row['id'];
        names[i] = row['username'];
    });
    $('#ids').val(ids);
    $('#usernames').val(names);
});

$.validator.setDefaults({
    submitHandler : function() {
        update();
    }
});
function update() {
    $.ajax({
        cache : true,
        type : "POST",
        url : "/tempuser/tempuser/batchUpdate",
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
    $("#signupForm").validate({
        rules : {
            password : {
                required : true
            },
            jobsid : {
                required : true
            },
        },
        messages : {
            password : {
                required : icon + "请输入密码"
            },
            password : {
                required : icon + "请选择岗位"
            }
        }
    })
}