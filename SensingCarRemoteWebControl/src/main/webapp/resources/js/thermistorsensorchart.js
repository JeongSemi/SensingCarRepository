var thermistorSensorChart;
$(function() {
	thermistorSensorChart = new Highcharts.Chart({
		chart : {
			renderTo : "thermistorSensorChartContainer",
			type : "spline",
			events : {
				load : requestThermistorSensorData
			}
		},
		colors : [ 'cyan' ],
		title : {
			text : "ThermistorSensor(온도센서)"
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 100,
			minRange : 20 * 1000
		},
		yAxis : {
			title : {
				text : "온도",
				margin : 30
			}
		},
		series : [ {
			name : "온도",
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

function requestThermistorSensorData() {
	var ws = new WebSocket("ws://" + location.host
			+ "/SensingCarRemoteWebControl/websocket/thermistorsensor");
	ws.onmessage = function(event) {
		var data = JSON.parse(event.data);
		var series = thermistorSensorChart.series[0];
		var shift = series.data.length > 20; // 그래프의 이동을 판단하기 위한 boolean변수
		series.addPoint([data.time, data.temperature], true, shift);
	};

}