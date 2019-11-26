<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>HighChartsTest</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<!-- <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/highcharts.js"></script> 
	<script type="text/javascript" src="js/exporting.js"></script>-->	
	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/highcharts.js"></script>
	<script>
		
		//图表属性，不包含数据
		var options = {
			chart: {
				renderTo:'container',
				type:'spline',
			},
			title: {
				text: '实时呼吸监测曲线',
				x: -20 //center
			},
		
			xAxis:{
				title: {
					text: 'Phase (radian)'
				},
			},
			yAxis: {
				title: {
					text: 'Time (s)'
				},
				plotLines: [{
					value: 0,
					width: 1,
					color: '#808080'
				}]
			},
			series: [{
				name:'时间',
			}],
			tooltip: {
				valueSuffix: 's'
			},
			// 优化性能损耗
	        // tooltip：禁用鼠标移上去显示的提示框
	       // tooltip: {
	      //  	enabled: false
	      //  },
	        // animation：禁止动画
	        // enableMouseTracking：禁止鼠标移到线上时，线条变粗
		
			plotOptions: {
				spline:{
					dataLabels: {
						enabled: true
					},
					animation:false,
				},
				 turboThreshold:0, //不限制数据点个数  
				 marker: {
	                    enabled: false,
	                    states: {
	                        hover: {
	                            enabled: false
	                        }
	                    }
	                },
	                enableMouseTracking: false

			},
		};

		//初始函数，设置定时器，定时取数据
		$(function () {
			queryData(0);
		
			var i=0;
			var timer = setInterval(function(){
				i++;
				if(i>=3) {i=0;}
				queryData(i);
			},3000);
		
			//停止刷新
			$("button").click(function(){
				clearInterval(timer);
			});
		});
		
		var categories = [];
		var datas = [];
		
		//Ajax 获取数据并解析创建Highcharts图表
		function queryData(index) {
			$.ajax({
				url:'HighCharts',
				type:'get',
				dataType:"json",
				success:function(data) {
						
					$.each(data,function(i,n){
						categories[i] = n["time"];
						datas[i] = n["data1"];
					});
					
					options.xAxis.categories = categories;
					options.series[0].data = datas;
					
					chart = new Highcharts.Chart(options);
				}
			});
		}
		
	
	</script>
</head>

<body>
	<div id="container" style="min-width:400px;height:400px;"></div>
	<p align=center><button>停止刷新</button></p>

</body>

</html>
