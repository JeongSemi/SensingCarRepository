function ultrasonicsensor(command, angle) {
	var json = {
		"command" : command,
		"angle" : angle
	};

	$.ajax({
		url : "http://" + location.host + "/SensingCarRemoteWebControl/ultrasonicsensor",
		data : json,
		method : "post",
		success : function(data) {
			if (data.result == "success") {
				$("#ultrasonicsensorStatus").html("distance=" + data.distance + "; angle="	+ data.angle);
			}
		}
	});
}