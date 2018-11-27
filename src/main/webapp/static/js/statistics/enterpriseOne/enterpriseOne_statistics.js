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

function showDecisionDetail(names,pass,defer,veto){
	var dom = document.getElementById("container_2");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '堆叠柱状图';

	option = {
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:['通过','缓议','表决']
	    },
	    color : ['#4FDCA5','#D2A340','#FF7171'],
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            axisLabel:{  
                    interval:0,//横轴信息全部显示  
                    formatter: function (params){   //标签输出形式 ---请开始你的表演
	                    var index = 5;
	                    var newstr = '';
	                    for(var i=0 ; i < params.length; i+=index){
	                        var tmp=params.substring(i, i+index);
	                        newstr+=tmp+'\n';
	                    }
	                    return newstr;
	                }
                },
	            data : names
	        }
	    ],
	    grid: {
            y2:35
        },
	    label : {
		    normal: {
		        show: true,
		        position: 'top'
		    }
		},
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'通过',
	            type:'bar',
	            stack: '结果',
	            data: pass,
	            barWidth : 30,
	            label: {
	                normal: {
	                    show: true
	                }
	            }
	        },
	        {
	            name:'缓议',
	            type:'bar',
	            stack: '结果',
	            data: defer,
	            barWidth : 30,
	            label: {
	                normal: {
	                    show: true
	                }
	            }
	        },
	        {
	            name:'表决',
	            type:'bar',
	            stack: '结果',
	            data: veto,
	            barWidth : 30,
	            label: {
	                normal: {
	                    show: true
	                }
	            }
	        }
	    ]
	};
	;
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
	    	decisionClickEvent();//重要的参数都在这里！
	    });
	}
}

function showInvestor(registerQuantity,approveQuantity,subjectQuantity){
	var dom = document.getElementById("container_3");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '环形图';

	option = {
		    backgroundColor: 'white',
		    title : {
		      text: '议题总数',
		      subtext: subjectQuantity,
		      subtextStyle: {
		    	fontSize : 20,
		    	padding: 20,
		    	color: '#84aef5'
		     },
		        x: 'center',
		        y: '40%',
		        textStyle: {
		            fontWeight: 'normal',
		            fontSize: 20
		        }
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    color : ['#3AA7F9','#DFDFDF','#FD9A36'],
		    series : [
		        {
		            name: '涉及出资人重大决策议题',
		            type: 'pie',
		            radius : ['60%','75%'],
		            center: ['50%', '50%'],
		            data:[
		            	{value:registerQuantity, name:"涉及出资人备案议题\n"+registerQuantity+"项"},
		            	{value:subjectQuantity-(registerQuantity+approveQuantity), name:"其他"},
		                {value:approveQuantity, name:"涉及出资人审批议题\n"+approveQuantity+"项"}
		            ],
		            label: {
			            normal: {
			                show: true,
			                textStyle: {
			                    fontSize: 16
			                }
			            },
			            emphasis: {
			                show: true
			            }
			        },
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
	    	investorClickEvent();//重要的参数都在这里！
	    });
	}
}

function meetingClickEvent(){
	var href="/enterpriseOne/meetingJump.action"
		window.parent.addpage("企业会议分布",href,'enterpriseOne_MeetingDetail');
}
function decisionClickEvent(){
	var href="/enterpriseOne/decisionJump.action"
		window.parent.addpage("企业议题列表",href,'enterpriseOne_DecisionSubjectDetail');
}
function investorClickEvent(){
	var href="/enterpriseOne/investorJump.action"
		window.parent.addpage("企业议题列表",href,'enterpriseOne_InvestorDetail');
}
//获取当前用户所属的企业集合
function getCurUserCompany(){
	
}