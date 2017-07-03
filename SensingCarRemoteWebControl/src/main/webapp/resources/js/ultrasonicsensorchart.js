var ultrasonicSensorChart;
$(function() {
	ultrasonicSensorChart = new Highcharts.Chart({
		chart : {
			renderTo : "ultrasonicSensorChartContainer",
			type : "spline",
			events : {
				load : requestUltrasonicSensorData
			}
		},
		colors : [ 'grey' ],
		title : {
			text : "UltrasonicSensor(거리센서)"
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 100,
			minRange : 20 * 1000
		},
		yAxis : {
			minPadding : 0.2,
			maxPadding : 0.2,
			title : {
				text : "거리",
				margin : 30
			}
		},
		series : [ {
			name : "거리",
			data : []
		} ],
		plotOptions: {
			series: {
				marker: {
					enabled: true
				}
			}
		}
	});

}); // 자동실행

function requestUltrasonicSensorData() {
	var ws = new WebSocket("ws://" + location.host
			+ "/SensingCarRemoteWebControl/websocket/ultrasonicsensor");
	ws.onmessage = function(event) {
		var data = JSON.parse(event.data);
		var series = ultrasonicSensorChart.series[0];
		series.name = "각도(" + data.angle + ")";
		var shift = series.data.length > 20; // 그래프의 이동을 판단하기 위한 boolean변수
		
		series.addPoint([ data.time, data.distance ], true, shift);
		
		var length = series.points.length;
		if(data.distance < 10){
			series.points[length-1].color = 'red';
		} else {
			series.points[length-1].color = 'grey';
		}
					
	};

}