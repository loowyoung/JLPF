//初始化页面，查询岗位导航栏
initHTML();
// 绘制候选人table
var table = layui.table;
//岗位id
var jobid = null;
//table返回值
var list = null;

function initHTML() {
	$.ajax({
		type: "get",
		url: ctxPath + "/ax/getJobs",
		async: true,
		data: {
			userid: sessionStorage.getItem('id'),
			username: sessionStorage.getItem('username')
		},
		success: function(res) {
			if(res.code == 0) {
				$.each(res.data, function(index, item) {
					$('#postList').append('<dd><a onclick="drawTable(\'' + item.id + '\')">' + item.jobname + '</a></dd>');
				});
			} else {
				window.location.href = "login.html";
			}
		},
		error: function(err) {
			layerMsg(err.msg);
		}
	});
}

drawTable('ceshi');
//绘制候选人打分table表格
function drawTable(id) {
	jobid = id;
	table.render({
		elem: '#applicantTable',
		url: ctxPath + '/ax/getAppicants',
		where: {
			jobid: id,
			userid: sessionStorage.getItem('id'),
			username: sessionStorage.getItem('username')
		},
		parseData: function(res) {
			list = res.data;
			if(res.data) {
				$.each(list, function(index, item) {
					item.index = index + 1;
				});
			}
			return {
				code: res.code,
				msg: res.msg,
				data: list,
				count: res.count
			};
		},
		cols: [
			[ //表头
				{
					field: 'index',
					title: '序号',
					width: '58',
				}, {
					field: 'ranking',
					title: '排名',
					width: '58',
					edit: 'ranking'
				}, {
					field: 'score',
					title: '打分',
					width: '58',
					edit: 'score'
				}, {
					field: 'username',
					title: '姓名',
					width: '72',
					templet: function(row) {
						return '<a href=\'#\' onclick=layerOpen(\'' + row.resumeurl + '\')>' + row.username + '</a>';
					}
				}, {
					field: 'sex',
					title: '性别',
					width: '58',
					templet: function(row) {
						if(1 == row.sex) {
							return '男';
						} else {
							return '女';
						}
					}
				}, {
					field: 'birthday',
					title: '出生日期',
					width: '102',
					templet: function(row) {
						return row.birthday.split(' ')[0];
					}
				}, {
					field: 'hometown',
					title: '生源地',
					width: '72'
				}, {
					field: 'education',
					title: '学位',
					width: '80',
				}, {
					field: 'doctor',
					title: '博士',
					width: '150',
					templet: function(row) {
						if(row.doctorschool && row.doctormajor) {
							return row.doctorschool + '<br/>' + row.doctormajor;
						} else {
							return '';
						}
					}
				}, {
					field: 'master',
					title: '硕士',
					width: '150',
					templet: function(row) {
						if(row.masterschool && row.mastermajor) {
							return row.masterschool + '<br/>' + row.mastermajor;
						} else {
							return '';
						}
					}
				}, {
					field: 'bachelor',
					title: '本科',
					width: '150',
					templet: function(row) {
						if(row.bachelorschool && row.bachelormajor) {
							return row.bachelorschool + '<br/>' + row.bachelormajor;
						} else {
							return '';
						}
					}
				}, {
					field: 'languagelevel',
					title: '外语等级',
					width: '168',
				}, {
					field: 'englishscore',
					title: '英语笔试',
					width: '86',
				}
			]
		]
	});
}

//监听单元格编辑事件,打分和排名
table.on('edit(tableFilter)', function(obj) {
	if('score' == obj.field) {
		saveScore(obj);
	}
	
	if('ranking' == obj.field) {
		saveRanking(obj);
	}
	
	console.log(obj.value); //得到修改后的值
	console.log(obj.field); //当前编辑的字段名
	console.log(obj.data); //所在行的所有相关数据  
});
//保存排名
function saveRanking(obj) {
	if(Number(obj.value) >= 1 && Number(obj.value) <= 100) {
		layerMsg('调用接口，保存排名');
		editRanking(obj);
	} else {
		obj.tr.children().eq(1).children().eq(1).val(''); //去掉编辑框内的值
		$.each(table.cache.applicantTable, function(property, value) {
			if(value.id == obj.data.id) {
				value.ranking = '';
				obj.value = '';
			}
		});
		layerMsg('请输入数字');
	}
}
//保存打分
function saveScore(obj) {
	if(Number(obj.value) >= 50 && Number(obj.value) <= 100) {
		layerMsg('调用接口，保存打分');
	} else {
		obj.tr.children().eq(2).children().eq(1).val(''); //去掉编辑框内的值
		$.each(table.cache.applicantTable, function(property, value) {
			if(value.id == obj.data.id) {
				value.score = '';
				obj.value = '';
			}
		});
		layerMsg('请输入50-100之间的数字');
	}
}

var rankArr = [];
var unRankArr = [];
var data = {
	userid: sessionStorage.getItem('id'),
	scorelist: []
};
// 修改排名
function editRanking(obj) {
	// 把修改的数据插到指定位置
	if(obj.value) {
		$.each(list, function(index, item) {
			if(obj.data.id == item.id) {
				list.splice(index, 1);
				list.splice(obj.value - 1, 0, obj.data);
			}
		});
		// 分离排名
		separationRanking();
		var _rankOrder = rankArr.sort(function(a, b) {
			return(a.ranking - b.ranking);
		});
		// 往参数中存值
		generateParameterData(_rankOrder);
	} else {
		// 分离排名
		separationRanking();
		// 往参数中存值
		generateParameterData(rankArr);
		// 如果排名改为了空，放到数组的最后
		data.scorelist.push({
			tempuserId: sessionStorage.getItem('id'),
			applicantId: obj.data.id,
			ranking: ""
		});
	}
	// 提交数据
	$.ajax({
		type: "post",
		contentType: 'application/json;charset=utf-8',
		url: ctxPath + "/ax/saveRank",
		async: true,
		data: JSON.stringify(data),
		success: function(res) {
			drawTable(jobid);
			rankArr = [];
			unRankArr = [];
			data.scorelist = [];
		}

	});
}
// 分离排名
function separationRanking() {
	$.each(list, function(index, item) {
		if(item.ranking) {
			rankArr.push(item);
		} else {
			unRankArr.push(item);
		}
	});
}
// 往参数中存值
function generateParameterData(jsonData) {
	$.each(jsonData, function(index, item) {
		if(item.ranking) {
			data.scorelist.push({
				tempuserId: sessionStorage.getItem('id'),
				applicantId: item.id,
				ranking: index + 1,
				username: item.username
			});
		}
	});
}

//设置登陆用户名或跳转登陆界面
userInfo();
//标题栏设置登陆用户名
function userInfo() {
	if(sessionStorage.getItem('username')) {
		$('#username').text(sessionStorage.getItem('username'))
	} else {
		window.location.href = 'login.html';
	}
}

//退出登陆
function logout() {
	sessionStorage.clear();
	window.location.href = "login.html";
}