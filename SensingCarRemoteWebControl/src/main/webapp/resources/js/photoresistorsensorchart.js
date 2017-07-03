var photoresistorSensorChart;
$(function() {
	photoresistorSensorChart = new Highcharts.Chart({
		chart : {
			renderTo : "photoresistorSensorChartContainer",
			type : "spline",
			events : {
				load : requestPhotoresistorSensorData
			}
		},
		colors : [ 'yellow' ],
		title : {
			text : "PhotoresistorSensor(조도센서)"
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 100,
			minRange : 20 * 1000
		},
		yAxis : {
			title : {
				text : "조도",
				margin : 30
			}
		},
		series : [ {
			name : "조도",
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

function requestPhotoresistorSensorData() {
	var ws = new WebSocket("ws://" + location.host
			+ "/SensingCarRemoteWebControl/websocket/photoresistorsensor");
	ws.onmessage = function(event) {
		var data = JSON.parse(event.data);
		var series = photoresistorSensorChart.series[0];
		var shift = series.data.length > 20; // 그래프의 이동을 판단하기 위한 boolean변수
		series.addPoint([data.time, data.value], true, shift);
	};

}