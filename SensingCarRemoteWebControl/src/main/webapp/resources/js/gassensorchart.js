var gasSensorChart;
$(function() {
	gasSensorChart = new Highcharts.Chart({
		chart : {
			renderTo : "gasSensorChartContainer",
			type : "spline",
			events : {
				load : requestGasSensorChartData
			}
		},
		colors : [ 'white' ],
		title : {
			text : "GasSensor(가스센서)"
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 100,
			minRange : 20 * 1000
		},
		yAxis : {
			title : {
				text : "값",
				margin : 30
			}
		},
		series : [ {
			name : "값",
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

function requestGasSensorChartData() {
	var ws = new WebSocket("ws://" + location.host
			+ "/SensingCarRemoteWebControl/websocket/gassensor");
	ws.onmessage = function(event) {
		var data = JSON.parse(event.data);
		var series = gasSensorChart.series[0];
		var shift = series.data.length > 20; // 그래프의 이동을 판단하기 위한 boolean변수
		series.addPoint([data.time, data.value], true, shift);
	};

}