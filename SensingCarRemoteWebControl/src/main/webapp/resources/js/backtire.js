function backtire(command, direction, speed) {
	var json = {
		"command" : command,
		"direction" : direction,
		"speed" : speed
	};

	$.ajax({
		url : "http://" + location.host + "/SensingCarRemoteWebControl/backtire",
		data : json,
		method : "post",
		success : function(data) {
			if (data.result == "success") {
				$("#backtireStatus").html("direction=" + data.direction + "; speed="	+ data.speed);
				
				$("#btnBacktireForward").attr("onclick", "backtire('change', 'forward', '" + data.speed + "')");
				$("#btnBacktireBackward").attr("onclick", "backtire('change', 'backward', '" + data.speed + "')");
				$("#btnBacktire0").attr("onclick", "backtire('change', '" + data.direction + "', '0')");
				$("#btnBacktire1").attr("onclick", "backtire('change', '" + data.direction + "', '900')");
				$("#btnBacktire2").attr("onclick", "backtire('change', '" + data.direction + "', '1200')");
				$("#btnBacktire3").attr("onclick", "backtire('change', '" + data.direction + "', '1550')");
				$("#btnBacktire4").attr("onclick", "backtire('change', '" + data.direction + "', '1900')");
				$("#btnBacktire5").attr("onclick", "backtire('change', '" + data.direction + "', '2250')");
				$("#btnBacktire6").attr("onclick", "backtire('change', '" + data.direction + "', '2600')");
				$("#btnBacktire7").attr("onclick", "backtire('change', '" + data.direction + "', '3000')");
				$("#btnBacktire8").attr("onclick", "backtire('change', '" + data.direction + "', '3400')");
				$("#btnBacktire9").attr("onclick", "backtire('change', '" + data.direction + "', '3700')");
				$("#btnBacktire10").attr("onclick", "backtire('change', '" + data.direction + "', '4095')");
			}
		}
	});
}