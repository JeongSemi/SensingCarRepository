function laseremitter(command, status){
	var json = {
			"command" : command,
			"status" : status
		};
	
	$.ajax({
		url : "http://" + location.host + "/SensingCarRemoteWebControl/laseremitter",
		data : json,
		method : "post",
		success : function(data) {
			if (data.result == "success") {
				$("#laseremitterStatus").html(data.status);
			}
		}
	});
}