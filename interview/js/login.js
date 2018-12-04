function login() {
	$.ajax({
		type: "post",
		url: ctxPath + "/ax/login",
		async: true,
		dataType: 'json',
		data: {
			'username': $('#username').val(),
			'password': $('#password').val()
		},
		success: function(res) {
			if(res.code == 0) {
				sessionStorage.setItem("id", res.data.id);
				sessionStorage.setItem("username", res.data.username);
				window.location.href = 'index.html';
			} else {
				layerMsg(res.msg);
			}
		},
		error: function(err) {
			layerMsg(res.msg);
		}

	});

}