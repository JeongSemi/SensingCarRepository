function fronttire(command, angle) {
	var json = {
		"command" : command,
		"angle" : angle
	};

	$.ajax({
		url : "http://" + location.host + "/SensingCarRemoteWebControl/fronttire",
		data : json,
		method : "post",
		success : function(data) {
			if (data.result == "success") {
				$("#fronttireStatus").html("angle=" + data.angle);
			}
		}
	});
}