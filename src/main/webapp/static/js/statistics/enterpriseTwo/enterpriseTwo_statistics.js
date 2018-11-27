//展示 重大人事任免会议分类汇总 饼图
function showMeeting(names,values){
	var dom = document.getElementById("container_1");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            color:['#FFBA79','#89D042','#B194E4','#54A4FD'],
	            name:'访问来源',
	            type:'pie',
	            label: {
	                normal: {
	                    formatter: '{b|{b}}\n {c|{c}}',
	                    backgroundColor: '#eee',
	                    borderColor: '#aaa',
	                    borderWidth: 1,
	                    borderRadius: 4,
	                    padding: [0, 7],
	                    rich: {
	                        b: {
	                            color: 'black',
	                            lineHeight: 22,
	                            align: 'center'
	                        },
	                        c: {
	                            color: 'black',
	                            lineHeight: 22,
	                            align: 'center'
	                        }
	                    }
	                }
	            },
	            data:[
	                {value:values[0], name:names[0]},
	                {value:values[1], name:names[1]},
	                {value:values[2], name:names[2]},
	                {value:values[3], name:names[3]}
	            ]
	        }
	    ]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
			meetingClickEvent();//重要的参数都在这里！
	    });
	}
}

function showSubject(names,values){
	var dom = document.getElementById("container_2");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '嵌套环形图';

	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius: ['40%', '55%'],
	            label: {
	                normal: {
	                    formatter: '{b|{b}}\n {c|{c}}',
	                    backgroundColor: '#eee',
	                    borderColor: '#aaa',
	                    borderWidth: 1,
	                    borderRadius: 4,
	                    // shadowBlur:3,
	                    // shadowOffsetX: 2,
	                    // shadowOffsetY: 2,
	                    // shadowColor: '#999',
	                    padding: [0, 7],
	                    rich: {
	                        b: {
	                            color: 'black',
	                            lineHeight: 22,
	                            align: 'center'
	                        },
	                        c: {
	                            color: 'black',
	                            lineHeight: 22,
	                            align: 'center'
	                        }
	                    }
	                }
	            },
	            data:[
	                {value:values[0], name:names[0]},
	                {value:values[1], name:names[1]},
	                {value:values[2], name:names[2]},
	                {value:values[3], name:names[3]},
	                {value:values[4], name:names[4]},
	                {value:values[5], name:names[5]},
	            ]
	        }
	    ]
	};;
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function (param) {
	    	var index = param.name;
	    	subjectClickEvent();
	 }); 

	}
}

function meetingClickEvent(){
	var href="/enterpriseOne/meetingJump.action"
		window.parent.addpage("企干二局会议分布",href,'enterpriseOne_MeetingDetail');
}
function subjectClickEvent(){
	var href="/enterpriseTwo/subjectJump.action"
		window.parent.addpage("企干二局议题情况",href,'enterpriseTwo_DecisionSubjectDetail');
}