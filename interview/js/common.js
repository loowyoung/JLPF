var ctxPath = 'http://localhost:8085';

//消息提示
function layerMsg(msg) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.msg(msg);
	});
}

//打开弹窗
function layerOpen(url) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['1000px', '550px'],
			type: 2,
			content: url
		});
	});
}