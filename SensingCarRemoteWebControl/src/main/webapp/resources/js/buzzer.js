function buzzer(command, status){
	var json = {
			"command" : command,
			"status" : status
		};
	
	$.ajax({
		url : "http://" + location.host + "/SensingCarRemoteWebControl/buzzer",
		data : json,
		method : "post",
		success : function(data) {
			if (data.result == "success") {
				$("#buzzerStatus").html(data.status);
			}
		}
	});
}