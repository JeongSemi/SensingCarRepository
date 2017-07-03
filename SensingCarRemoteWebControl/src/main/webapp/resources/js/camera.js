function camera(command, leftright, updown) {
	var json = {
		"command" : command,
		"leftright" : leftright,
		"updown" : updown
	};

	$.ajax({
		url : "http://" + location.host + "/SensingCarRemoteWebControl/camera",
		data : json,
		method : "post",
		success : function(data) {
			if (data.result == "success") {
				$("#cameraStatus").html("leftright=" + data.leftright + "; updown="	+ data.updown);
				$("#btnLeftRight180").attr("onclick", "camera('change', '180', '" + data.updown + "')");
				$("#btnLeftRight135").attr("onclick", "camera('change', '135', '" + data.updown + "')");
				$("#btnLeftRight90").attr("onclick", "camera('change', '90', '" + data.updown + "')");
				$("#btnLeftRight45").attr("onclick", "camera('change', '45', '" + data.updown + "')");
				$("#btnLeftRight0").attr("onclick", "camera('change', '0', '" + data.updown + "')");
				$("#btnUpDown10").attr("onclick", "camera('change', '" + data.leftright + "', '10')");
				$("#btnUpDown45").attr("onclick", "camera('change', '" + data.leftright + "', '45')");
				$("#btnUpDown75").attr("onclick", "camera('change', '" + data.leftright + "', '75')");
			}
		}
	});
}