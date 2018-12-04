
var prefix = "/plan/plan"
$(function() {
	load();
});

//显示时间组件
layui.use('laydate', function(){
    var laydate = layui.laydate;

    laydate.render({
        elem: '#starttime'
        ,type: 'date'
        ,min: 0
    });
    laydate.render({
        elem: '#endtime'
        ,type: 'date'
        ,min: 0
    });

});

function selectLoad() {
    var html = "";
    $.ajax({
        url : prefix+'/listJob',
        success : function(data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].jobname + '">' + data[i].jobname + '</option>'
            }
            $(".chosen-select").append(html);
            $(".chosen-select").chosen({
                maxHeight : 200
            });
            //点击事件
            $('.chosen-select').on('change', function(e, params) {
                console.log(params.selected);
                var opt = {
                    query : {
                        jobname : params.selected,
                    }
                }
                $('#exampleTable').bootstrapTable('refresh', opt);
            });
        }
    });
}

function load() {
    selectLoad();
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
                        height: 500,
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
                                jobname:$('#jobname').val(),
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								{
									checkbox : true
								},
																{
									field : 'id', 
									title : '序号',
                                    visible : false
								},
                           		{
                           		    field : 'username',
                           		    title : '候选人姓名',
                                    align : 'center',
                           		},
                           		{
                           		    field : 'sex',
                           		    title : '性别',
                                    align : 'center',
                                    formatter : function(value,row,index){
                                        if (row.sex=='1'){
                                            return '男';
                                        }else {
                                            return '女';
                                        }
                                    }
                           		},
                           		{
                           		    field : 'idnumber',
                           		    title : '身份证号',
                                    align : 'center',
                           		},
                           		{
                           		    field : 'phone',
                           		    title : '手机号',
                                    align : 'center',
                           		},
                           		{
                           		    field : 'jobname',
                           		    title : '<div style=\"text-align: center\">面试岗位</div>',
									width : '160px'
                           		},
                           		{
                           		    field : 'tempuser',
                                    width : '220px',
                           		    title : '<div style=\"text-align: center\">所属用户</div>',
                           		},
                           		{
                           		    field : 'notscore',
                           		    width : '100px',
                           		    title : '<div style=\"text-align: center\">未打分用户</div>',
                           		},
																{
									field : 'starttime', 
									title : '开始时间' ,
                                    align : 'center',
								},
																{
									field : 'endtime', 
									title : '结束时间' ,
                                    align : 'center',
								},
																{
									field : 'createBy', 
									title : '创建者',
									visible : false
								},
																{
									field : 'createDate', 
									title : '创建时间' ,
                                    visible : false
								},
																{
									field : 'updateBy', 
									title : '修改者' ,
                                    visible : false
								},
																{
									field : 'updateDate', 
									title : '修改时间' ,
                                    visible : false
								},
																{
									field : 'remarks', 
									title : '备注' ,
                                    visible : false
								},
								// 								{
								// 	title : '操作',
								// 	field : 'id',
								// 	align : 'center',
								// 	formatter : function(value, row, index) {
								// 		var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
								// 				+ row.id
								// 				+ '\')"><i class="fa fa-edit"></i></a> ';
								// 		var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
								// 				+ row.id
								// 				+ '\')"><i class="fa fa-remove"></i></a> ';
								// 		var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
								// 				+ row.id
								// 				+ '\')"><i class="fa fa-key"></i></a> ';
								// 		return e + d ;
								// 	}
								// }
						]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function setTime() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'),
		starttime = $('#starttime').val(),
		endtime = $('#endtime').val();
    if (rows.length == 0) {
        layer.msg("请选择要修改的候选人");
        return;
    }
    if (endtime<starttime) {
        layer.msg("[结束时间]要晚于[开始时间]");
        return;
    }
    var ids = new Array();
    $.each(rows, function(i, row) {
        ids[i] = row['id'];
    });
    $.ajax({
        type : 'POST',
        data : {
            "ids" : ids,
			"starttime":starttime,
			"endtime":endtime

        },
        url : prefix + '/batchSetTime',
        success : function(r) {
            if (r.code == 0) {
                layer.msg(r.msg);
                reLoad();
            } else {
                layer.msg(r.msg);
            }
        }
    });
}

function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function resetPwd(id) {
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}