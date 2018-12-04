
var prefix = "/operationlog/operationlog"
$(function() {
	load();
});

//显示时间组件
layui.use('laydate', function(){
    var laydate = layui.laydate;

    laydate.render({
        elem: '#searchstarttime'
        ,type: 'date'
    });
    laydate.render({
        elem: '#searchendtime'
        ,type: 'date'
    });

});

function selectLoad() {
    var html = "";
    $.ajax({
        url : prefix+'/listUser',
        success : function(data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].tempuserId + '">' + data[i].tempuserId + '</option>'
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
                        tempuserId : params.selected,
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
                                searchstarttime:$('#searchstarttime').val(),
                                searchendtime:$('#searchendtime').val(),
                                tempuserId:$('#tempuserId').val()
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
                                    align : 'center',
									width:'60',
                                    formatter: function (value, row, index) {
                                        var options = $('#exampleTable').bootstrapTable('getOptions');
                                        return options.pageSize * (options.pageNumber - 1) + index + 1;
                                    }
								},
																{
									field : 'tempuserId', 
									title : '用户名',
                                    align : 'center',
                                    width:'100',
								},
																{
									field : 'applicantId', 
									title : '候选人' ,
									visible : false
                                    // formatter : function(value,row,index){
                                    //     return '给'+value;
                                    // }
								},
																{
									field : 'operate',
									title : '操作信息',
                                    align : 'center',

								},
																{
									field : 'date',
									title : '操作时间',
                                    align : 'center',
								},
																{
									field : 'createBy', 
									title : '创建者' ,
                                    visible : false
								},
								// 								{
								// 	field : 'createDate',
								// 	title : '创建时间' ,
                                 //    visible : false
								// },
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
    var starttime = $('#searchstarttime').val(),
        endtime = $('#searchendtime').val();
    if (starttime.length!=0 && endtime.length!=0 && endtime<starttime) {
        layer.msg("[结束时间]要晚于[开始时间]");
        return;
    }
	$('#exampleTable').bootstrapTable('refresh');
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

function exportLogList() {
	window.location.href=prefix+"/exportLogList";
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


    // function exportLogList() {
    //     layer.open({
    //         title : '请选择导出的列',
    //         type: 1,
    //         shadeClose : false, // 点击遮罩关闭层
    //         area : [ '220px', '450px' ],
    //         content : '<div class="col-sm-8"><div id="columnTree"></div></div>'+
    //         '<div class="columns pull-right"><button class="btn btn-success" onclick="getAllSelectNodes();">确定</button></div>'
    //     });
    //     loadcolumnTree();
    // }
    // function loadcolumnTree() {
    //     $('#columnTree').jstree({
    //         "plugins" : [ "wholerow", "checkbox",'types' ],
    //         'core' : {
    //             'data' : {
    //                 "id":"-1","text":"全选","state":{"opened":true},"checked":true,"attributes":null,
    //                 "children":[
    //                     {"id":"tempuserId","text":"用户名"},
    //                     {"id":"operate","text":"操作信息"},
    //                     {"id":"createDate","text":"操作时间"}
    //                 ]}
    //         },
    //         "checkbox" : {
    //             "three_state" : true
    //         },
    //         'types': {
    //             'default': {
    //                 'icon': false  // 删除默认图标
    //             }
    //         }
    //
    //     });
    // }
    //
    //
    // function getAllSelectNodes() {
    //     var ref = $('#columnTree').jstree(true); // 获得整个树
    //     // 获得所有选中节点的id，返回值为数组
    //     var ids=ref.get_selected();
    //     var texts='';
    //     $.each(ids,function (i, data) {
    //         texts +=ref.get_text(data)+",";
    //     });
    //     var ids=ids.join(',');
    //     if (ids.length == 0 ||texts.length == 0) {
    //         layer.msg("请选择要导出的列",{icon: 2});
    //         return;
    //     }
    //     layer.closeAll('page');
    //     // exportList(ids,texts);
    //     $.ajax({
    //         url : prefix+"/exportLogList",
    //         type : "post",
    //         data : {
    //             'ids' : ids,
    //             'texts':texts
    //         },
    //         traditional: true,//这里设置为true
    //         success : function(r) {
    //             var form = $("<form>");
    //             form.attr('style', 'display:none');
    //             form.attr('target', '');
    //             form.attr('method', 'post');
    //             form.attr('action', prefix+"/exportLogList");
    //
    //             var input1 = $('<input>');
    //             input1.attr('type', 'hidden');
    //             input1.attr('name', 'ids');
    //             input1.attr('value', ids);
    //
    //             var input2 = $('<input>');
    //             input2.attr('type', 'hidden');
    //             input2.attr('name', 'texts');
    //             input2.attr('value', texts);
    //
    //             $('body').append(form);
    //             form.append(input1);
    //             form.append(input2);
    //
    //             form.submit();
    //             form.remove();
    //         }
    //     });
    // }

}