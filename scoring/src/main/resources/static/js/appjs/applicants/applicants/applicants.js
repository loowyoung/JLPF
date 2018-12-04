
var prefix = "/applicants/applicants"
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
        ,type: 'date',
        done:function(value,date){
            // date.hours=date.hours+23;
            // date.minutes=date.minutes+59;
            // date.seconds=date.seconds+59;
            // alert(date.hours+"  ：hours");
            // alert(date.minutes+"  ：minutes");
            // alert(date.seconds+"  ：seconds");
            return date.date+1;
        }
    });

});

function selectLoad() {
    var html = "";
    $.ajax({
        url : '/preadmission/applicants/type',
        success : function(data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].job + '">' + data[i].job + '</option>'
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
                        job : params.selected,
                    }
                }
                $('#exampleTable').bootstrapTable('refresh', opt);
            });
        }
    });
}

function load() {
    selectLoad();//查询所有候选人岗位，下拉框显示
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
                        height: 500,
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
						showColumns : true, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
					           // name:$('#searchName').val(),
					           	username:$('#searchName').val(),
                                job:$('#jobname').val(),
                                // searchstarttime:$('#searchstarttime').val(),
                                // searchendtime:$('#searchendtime').val()
							};
						},
                        rowStyle: function (row, index) {
                            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                            var strclass = "";
                            if (row.flag == "0") {
                                strclass = 'success';//还有一个active
                            }
                            else {
                                return {};
                            }
                            return { classes: strclass }
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
								}, {
									field : 'id',
									title : '序号',
                                    width: 40,
                                    align : 'center',
                                    width: 50,
                                    switchable: false,
                                    formatter: function (value, row, index) {
                                          var options = $('#exampleTable').bootstrapTable('getOptions');
                                        return options.pageSize * (options.pageNumber - 1) + index + 1;
									}
								}, {
									field : 'username', 
									title : '姓名' ,
                                    align : 'center',
                                    width: 65,
                                    switchable: false,
                                    width: 60,
									formatter : function(value,row,index){
										var url = '<a href="#" onclick="showResume(\''+row.resumeurl+'\')"><font color="blue">'+value+'</font></a>';
										return url;
									}
								}, {
									field : 'sex', 
									title : '性别',
                                    align : 'center',
                                    width: 40,
                                    switchable: false,
                                    formatter : function(value,row,index){
                                       if (value=='1'){
											return '男';
									   }else {
                                           return '女';
									   }
									}
								}, {
									field : 'job', 
									title : '应聘岗位',
                                    align : 'center',
                                    width: 200,
                                    switchable: false,
								}, {
									field : 'birthday', 
									title : '出生年月',
                                    align : 'center',
                                    width: 90,
                                    formatter : function(value,row,index){
                                        return value.substring(0,10);
                                    }
								}, {
									field : 'idnumber', 
									title : '身份证号',
                                    align : 'center',
                                    width: 160,
								}, {
									field : 'phone', 
									title : '手机号' ,
                                    align : 'center',
                                    width: 100,
								}, {
									field : 'mail', 
									title : '邮箱' ,
                                    align : 'center',
                                    width: 200,
								}, {
									field : 'politics', 
									title : '政治面貌' ,
                                    align : 'center',
                                    width: 100,
								}, {
                                    field : 'education',
                                    title : '学历' ,
                                    align : 'center',
                                    width: 90,
                                }, {
									field : 'type', 
									title : '是否优才' ,
                                    align : 'center',
                                    width: 60,
                                    visible : false,
                                    formatter : function(value, row, index) {
                                        if(value=='优才'){
                                            return '是';
                                        }else {
                                            return '';
                                        }
                                        // if(value=='优才'){
                                        //     var e = '<input type="checkbox" checked onchange="updateTypeIsNot(\''+row.id+'\')">'
                                        // }else{
                                        //     var e = '<input type="checkbox" onchange="updateTypeIs(\''+row.id+'\')">'
                                        // }
                                        // return e;
									}
								},
                                {
                                    field : 'exemptionreason',
                                    title : '免试原因',
                                    align : 'center',
                                    width: 120,
                                },
								{
									field : 'doctorschool',
									title : '博士',
                                    align : 'center',
                                    width: 160,
									formatter : function(value,row,index){
										// if (value != null) {
										// 	return value + "<br/>" + row.doctormajor;
										// }
                                        if (value && row.doctormajor) {
                                            var school = value.split('/');
                                            var major = row.doctormajor.split('/');
                                            if (school.length > 1) {
                                                var str = school[0] +'<br>'+ major[0] +'<br>' + school[1] +'<br>'+ major[1];
                                                return str;
                                            } else {
                                                if (major.length > 1) {
                                                    var str = school[0] +'<br>'+ major[0] +'<br>' + school[0] +'<br>'+ major[1];
                                                    return str;
                                                } else {
                                                    var str = school[0] +'<br>'+ major[0];
                                                    return str;
                                                }
                                            }
                                        } else {
                                            return '';
                                        }
									}
								},
								{
									field : 'masterschool',
									title : '硕士',
                                    align : 'center',
                                    width: 160,
									formatter : function(value,row,index){
										// if (value != null) {
										// 	return value + "<br/>" + row.mastermajor;
										// }
                                        if (value && row.mastermajor) {
                                            var school = value.split('/');
                                            var major = row.mastermajor.split('/');
                                            if (school.length > 1) {
                                                var str = school[0] +'<br>'+ major[0] +'<br>' + school[1] +'<br>'+ major[1];
                                                return str;
                                            } else {
                                                if (major.length > 1) {
                                                    var str = school[0] +'<br>'+ major[0] +'<br>' + school[0] +'<br>'+ major[1];
                                                    return str;
                                                } else {
                                                    var str = school[0] +'<br>'+ major[0];
                                                    return str;
                                                }
                                            }
                                        } else {
                                            return '';
                                        }
									}
								}, {
									field : 'bachelorschool', 
									title : '本科',
                                    align : 'center',
                                    width: 160,
									formatter : function(value,row,index){
                                        // if (value != null) {
                                        //     return value+"<br/>"+row.bachelormajor;
                                        // }
                                        if (value && row.bachelormajor) {
                                            var school = value.split('/');
                                            var major = row.bachelormajor.split('/');
                                            if (school.length > 1) {
                                                var str = school[0] +'<br>'+ major[0] +'<br>' + school[1] +'<br>'+ major[1];
                                                return str;
                                            } else {
                                                if (major.length > 1) {
                                                    var str = school[0] +'<br>'+ major[0] +'<br>' + school[0] +'<br>'+ major[1];
                                                    return str;
                                                } else {
                                                    var str = school[0] +'<br>'+ major[0];
                                                    return str;
                                                }
                                            }
                                        } else {
                                            return '';
                                        }
                                    }
								}, {
									field : 'bachelormajor', 
									title : '本科专业',
                                    align : 'center',
                                    width: 160,
                                    visible : false
								}, {
									field : 'mastermajor', 
									title : '硕士专业',
                                    visible : false
								}, {
									field : 'doctormajor', 
									title : '博士专业',
                                    visible : false
								}, {
									field : 'graduationdate', 
									title : '毕业时间' ,
                                    align : 'center',
                                    width: 100,
                                    formatter : function(value,row,index){
                                        return value.substring(0,10);
                                    }
								}, {
									field : 'hometown', 
									title : '生源地',
                                    align : 'center',
                                    width: 110
								}, {
									field : 'languagelevel', 
									title : '外语等级',
                                    align : 'center',
                                    width: 160
								}, {
									field : 'computerlevel', 
									title : '计算机等级',
                                    align : 'center',
                                    width: 150,
								}, {
									field : 'resumenum', 
									title : '简历识别码',
                                    align : 'center',
                                    width: 270
								}, {
									field : 'resumeurl', 
									title : '简历地址' ,
                                    align : 'center',
                                    width: 650,
									visible : false
								}, {
									field : 'interviewresult', 
									title : '面试成绩',
                                    align : 'center',
                                    width: 70,
								}, {
									field : 'englishscore', 
									title : '英语分数',
                                    align : 'center',
                                    width: 70,
								}, {
									field : 'status', 
									title : '状态',
                                    align : 'center',
                                    width: 70,
								}, {
									field : 'interviewdate', 
									title : '面试场次' ,
                                    align : 'center',
                                    width: 160,
                                    visible:false
								}, {
									field : 'jobone', 
									title : '岗位一',
                                    align : 'center',
                                    width: 300
								}, {
									field : 'jobtwo', 
									title : '岗位二',
                                    align : 'center',
                                    width: 300
								}, {
									field : 'jobthree', 
									title : '岗位三',
                                    align : 'center',
                                    width: 300
								}, {
									field : 'createBy', 
									title : '创建者',
                                    align : 'center',
                                    width: 70,
                                    visible : false
								}, {
									field : 'createDate', 
									title : '创建时间',
                                    align : 'center',
                                    width: 150,
                                    visible : false
								}, {
									field : 'updateBy', 
									title : '修改者',
                                    align : 'center',
                                    width: 70,
                                    visible : false
								}, {
									field : 'updateDate', 
									title : '修改时间',
                                    align : 'center',
                                    width: 150
								}, {
									field : 'remarks', 
									title : '备注',
                                    align : 'center',
                                    width: 160,
								}
						],
                        // fixedColumns: true, // 固定列
                        // fixedNumber: 5, // 固定前5列
					});
}


//修改为是优才
function updateTypeIs(id) {
    $.ajax({
        url : prefix+"/update",
        type : "post",
        data : {
            'id' : id,
            'type':'优才',
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
}
//修改为不是优才
function updateTypeIsNot(id) {
    $.ajax({
        url : prefix+"/update",
        type : "post",
        data : {
            'id' : id,
            'type':''
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
}

function reLoad() {
    // var starttime = $('#searchstarttime').val(),
    //     endtime = $('#searchendtime').val();
    // if (starttime.length!=0 && endtime.length!=0 && endtime<starttime) {
    //     layer.msg("[结束时间]要晚于[开始时间]");
    //     return;
    // }
	$('#exampleTable').bootstrapTable('refresh');
}

	//导入概要信息
    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        upload.render({
            elem: '#importExcel',
			url: prefix + "/excelImport",
            accept: 'file',
            before: function(obj){
                layer.load("导入中..."); //上传loading
			},
			//设置文件最大可允许上传的大小，单位 KB
			size:102400,
			done: function(r){
                layer.closeAll("loading");
                if (r.code==0) {
                    reLoad();
                    layer.msg(r.msg,{
                        icon: 1,
                        time:5000
                    });
                }else{
                    layer.msg(r.msg,{
                        icon: 2,
                        time:5000
                    });
                }

            },
			error: function(){
                layer.closeAll("loading");
            }
        });
    });

//导入英语成绩
layui.use('upload', function(){
    var upload = layui.upload;
    upload.render({
        elem: '#importEnglish',
        url: prefix + "/importEnglish",
        accept: 'file',
        before: function(obj){
            layer.load("导入中..."); //上传loading
        },
        //设置文件最大可允许上传的大小，单位 KB
        size:102400,
        done: function(r){
            layer.closeAll("loading");
            if (r.code==0) {
                reLoad();
                layer.msg(r.msg,{
                    icon: 1,
                    time:5000
                });
            }else{
                layer.msg(r.msg,{
                    icon: 2,
                    time:5000
                });
            }

        },
        error: function(){
            layer.closeAll("loading");
        }
    });
});


function exportList() {
    layer.open({
        title : '请选择导出的列',
        type: 1,
        btn: ['确定', '取消'],
        shadeClose : false, // 点击遮罩关闭层
        area : [ '600px', '300px' ],
        content : '<div class="col-sm-8"><div id="columnTree"></div></div>'+
		'<div class="columns pull-right"></div>',
        yes: function (index, layero) {
            var rows = $('#exampleTable').bootstrapTable('getSelections');
            var rowids = [];
            // 遍历所有选择的行数据，取每条数据对应的ID
            $.each(rows, function(i, row) {
                rowids[i] = row['id'];
                console.log(typeof (row['id']));
            });

            getAllSelectNodes(rowids.join(","));
        }
    });
    loadcolumnTree();
}
function loadcolumnTree() {
    $('#columnTree').jstree({
        "plugins" : [ "wholerow", "checkbox",'types' ],
        'core' : {
            'dblclick_toggle': false,//双击全选是否折叠子项
            'data' : {
            	"id":"-1","text":"全选","state":{"opened":true},"checked":true,"attributes":null,
				"children":[
					{"id":"username","text":"姓名"},
                    {"id":"sex","text":"性别"},
                    {"id":"job","text":"应聘岗位"},
                    {"id":"birthday","text":"出生年月"},
                    {"id":"idnumber","text":"身份证号"},
                    {"id":"phone","text":"手机号"},
                    {"id":"mail","text":"邮箱"},
                    {"id":"politics","text":"政治面貌"},
                    {"id":"education","text":"学历"},
                    // {"id":"type","text":"是否优才"},
                    {"id":"doctorschool","text":"博士"},
                    {"id":"masterschool","text":"硕士"},
                    {"id":"bachelorschool","text":"本科"},
                    {"id":"graduationdate","text":"毕业时间"},
                    {"id":"hometown","text":"生源地"},
                    {"id":"languagelevel","text":"外语等级"},
                    {"id":"computerlevel","text":"计算机等级"},
                    {"id":"resumenum","text":"简历识别码"},
                    {"id":"resumeurl","text":"简历地址"},
                    {"id":"interviewresult","text":"面试成绩"},
                    {"id":"interviewdate","text":"面试场次"},
                    {"id":"englishscore","text":"英语分数"},
                    {"id":"status","text":"状态"},
                    {"id":"jobone","text":"岗位一"},
                    {"id":"jobtwo","text":"岗位二"},
                    {"id":"jobthree","text":"岗位三"},
                    {"id":"exemptionreason","text":"免试原因"},
                    {"id":"remarks","text":"备注"},
					]}
        },
        "checkbox" : {
            "three_state" : true
        },
        'types': {
            'default': {
                'icon': false  // 删除默认图标
            }
        }

    });
}
function getAllSelectNodes(rowids) {
    var ref = $('#columnTree').jstree(true); // 获得整个树
    // 获得所有选中节点的id，返回值为数组
    var ids=ref.get_selected();
	var texts='';
    $.each(ids,function (i, data) {
         texts +=ref.get_text(data)+",";
    });
    var ids=ids.join(',');
    if (ids.length == 0 ||texts.length == 0) {
        layer.msg("请选择要导出的列",{icon: 2});
        return;
    }
    layer.closeAll('page');
    // exportList(ids,texts);
    $.ajax({
        url : prefix+"/dtexport",
        type : "post",
        data : {
            'ids' : ids,
			'texts':texts,
            'rowids':rowids
        },
        traditional: true,//这里设置为true
        success : function(r) {
            var form = $("<form>");
            form.attr('style', 'display:none');
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', prefix+"/dtexport");

            var input1 = $('<input>');
            input1.attr('type', 'hidden');
            input1.attr('name', 'ids');
            input1.attr('value', ids);

            var input2 = $('<input>');
            input2.attr('type', 'hidden');
            input2.attr('name', 'texts');
            input2.attr('value', texts);

            var input3 = $('<input>');
            input3.attr('type', 'hidden');
            input3.attr('name', 'rowids');
            input3.attr('value', rowids);

            $('body').append(form);
            form.append(input1);
            form.append(input2);
            form.append(input3);

            form.submit();
            form.remove();
        }
    });
}


//弹出个人简历
function showResume(url) {
    layer.open({
        type : 2,
        title : '个人简历',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
		scrollbar : false,
        moveOut: true,
        area : [ '900px', '520px' ],
        content : url
    });
}
function add() {
	layer.open({
		type : 2,
		title : '新增',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}

//点击修改按钮
function editApplicant() {
    var rows = $('#exampleTable').bootstrapTable('getSelections');
    if (rows.length == 0) {
        layer.msg("请选择要修改的数据");
        return;
    }
    if (rows.length > 1) {
        layer.msg("只能修改单条数据");
        return;
    }
    var ids = new Array();
    $.each(rows, function(i, row) {
        ids[i] = row['id'];
    });
    var id = ids[0];

    edit(id);
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


//搜索框回车搜索
$("#searchName").keydown(function(e) {
    if (e.keyCode == 13) {
        reLoad();
    }
});