function rgbled(command, red, green, blue) {
	var json = {
		"command" : command,
		"red" : red,
		"green" : green,
		"blue" : blue
	};

	$.ajax({
		url : "http://" + location.host + "/SensingCarRemoteWebControl/rgbled",
		data : json,
		method : "post",
		success : function(data) {
			if (data.result == "success") {
				$("#rgbledStatus").css(
						"background-color",
						"rgb(" + data.red + "," + data.green + "," + data.blue + ")");
			}
		}
	});
}