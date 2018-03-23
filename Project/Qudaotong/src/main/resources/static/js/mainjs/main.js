$(document).ready(function(){
	
	
	page9_jContainer1_obj=$('#page9_jContainer1_container').layout({
		onresize:function(){},
		center__paneSelector:'.page9_jContainer1_center'
		,west__paneSelector:'.page9_jContainer1_west'
		,west__size:	220
		,west__spacing_open:	0
		,maskIframesOnResize: true
	});
	
	var page9_jChart1_chart = echarts.init(document.getElementById('page9_jChart1'));
	page9_jChart1_chart.setOption({
		tooltip : {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			x: 'left',
			y: 'center',
			orient: 'vertical',
			data:['2010', '2011', '2012', '2013', '2014']
		},
		series : [
			{
				type:'pie',
				radius: ['50%', '70%'],
					itemStyle: {
						normal: {
							label: {
								show: false,
							formatter: '{b} \n {c} ({d}%)'
								},
							labelLine: {
								show: false
							}
						},
						emphasis: {
							label: {
								show: true,
								position: 'center',
								textStyle: {
									fontSize: '30',
									fontWeight: 'bold'
								}
							}
						}
					},
				data:[{value:267, name:'2010'},
					{value:306, name:'2011'},
					{value:220, name:'2012'},
					{value:330, name:'2013'},
					{value:360, name:'2014'}
				]
			}
		]
	});
	
	var page9_jChart2_chart = echarts.init(document.getElementById('page9_jChart2'),themeinfographic);
	page9_jChart2_chart.setOption({
		tooltip : {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			show: false,
			data:['2010', '2011', '2012', '2013', '2014']
		},
		series : [
			{
				type:'pie',
				radius: ['50%', '70%'],
					itemStyle: {
						normal: {
							label: {
								show: true,
							formatter: '{b} \n {c} ({d}%)'
								},
							labelLine: {
								show: true
							}
						},
						emphasis: {
							label: {
								show: true,
								position: 'center',
								textStyle: {
									fontSize: '30',
									fontWeight: 'bold'
								}
							}
						}
					},
				data:[{value:267, name:'2010'},
					{value:306, name:'2011'},
					{value:220, name:'2012'},
					{value:330, name:'2013'},
					{value:360, name:'2014'}
				]
			}
		]
	});
	
	var page9_jChart3_chart = echarts.init(document.getElementById('page9_jChart3'));
	page9_jChart3_chart.setOption({
		tooltip : {
			trigger: 'axis'
		},
		legend: {
			data:['收入（万元）','利润（万元）']
		},
		xAxis: [
			{
				type: 'category',
				data: ['2010','2011','2012','2013','2014']
			}
		],
		yAxis: [
			{
				type: 'value'
			}
		],
		series : [
			{
				name:'收入（万元）',
				type:'line',
				data:[267, 306, 220, 330, 360]
			},
			{
				name:'利润（万元）',
				type:'line',
				data:[123, 231, 144, 122, 211]
			}
		]
	});
});

$(document).ready(function(){
	page9_jContainer1_obj.resizeAll();
});
