var trackingSensorChart;
$(function() {
	trackingSensorChart = new Highcharts.Chart({
		chart : {
			renderTo : "trackingSensorChartContainer",
			type : "spline",
			events : {
				load : requestTrackingSensorChartData
			}
		},
		colors : [ 'green' ],
		title : {
			text : "TrackingSensor(라인감지센서)"
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 100,
			minRange : 20 * 1000
		},
		yAxis : {
			title : {
				text : "감지",
				margin : 30
			}
		},
		series : [ {
			name : "감지",
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

function requestTrackingSensorChartData() {
	var ws = new WebSocket("ws://" + location.host
			+ "/SensingCarRemoteWebControl/websocket/trackingsensor");
	ws.onmessage = function(event) {
		var data = JSON.parse(event.data);
		var series = trackingSensorChart.series[0];
		var shift = series.data.length > 20; // 그래프의 이동을 판단하기 위한 boolean변수
		series.addPoint([data.time, data.tracking], true, shift);
		
		var length = series.points.length;
		if(data.tracking == 1){
			series.points[length-1].color = 'red';
		} else {
			series.points[length-1].color = 'green';
		}
		
	};

}