//展示 贯彻执行党和国家决策部署的重大措施 柱形图
function showDecision(statisticsNames,meeting_quantities,subject_quantities,names){
	
	var dom = document.getElementById("container_1");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.config = {
	    align: 'middle',
	    verticalAlign: 'middle',
	    show: true,
	    position: 'top',
	    distance: 15,
	    onChange: function () {
	        var labelOption = {
	            normal: {
	                rotate: app.config.rotate,
	                align: app.config.align,
	                verticalAlign: app.config.verticalAlign,
	                position: app.config.position,
	                distance: app.config.distance
	                
	            }
	        };
	        myChart.setOption({
	            series: [{
	                label: labelOption
	            }, {
	                label: labelOption
	            }, {
	                label: labelOption
	            }, {
	                label: labelOption
	            }]
	        })
	    }
	};


	var labelOption = {
	    normal: {
	        show: true,
	        position: app.config.position,
	        distance: app.config.distance,
	        align: app.config.align,
	        verticalAlign: app.config.verticalAlign,
	        rotate: app.config.rotate,
	        fontSize: 16,
	        rich: {
	            name: {
	                textBorderColor: '#fff'
	            }
	        }
	    }
	};

	option = {
	    color: ['#F8D770', '#6097DA'],
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	        data: ['议题数量', '会议数量'],
	        bottom:-5
	    },
	    toolbox: {
	        show: false,
	        orient: 'vertical',
	        left: 'right',
	        top: 'center',
	        feature: {
	            mark: {show: true},
	            dataView: {show: true, readOnly: false},
	            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    calculable: true,
	    xAxis: [
	    	{
	            type : 'category',
	            axisTick : {show: false},
	            axisLabel: {
	                show:true,
	                interval: 0, //强制所有标签显示
	                formatter: function (params){   //标签输出形式 ---请开始你的表演
	                    var index = 6;
	                    var newstr = '';
	                    for(var i=0 ; i < params.length; i+=index){
	                        var tmp=params.substring(i, i+index);
	                        newstr+=tmp+'\n';
	                    }
	                    return newstr;
	                }
	            },
	            triggerEvent: true,
	            data: statisticsNames
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value'
	        }
	    ],
	    series: [
	        {
	            name: '议题数量',
	            type: 'bar',
	            barGap: 0,
	            label: labelOption,
	            barWidth : 30,
	            data: subject_quantities
	        },
	        {
	            name: '会议数量',
	            type: 'bar',
	            label: labelOption,
	            barWidth : 30,
	            data: meeting_quantities
	        }
	    ]
	}
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function (param) {
	    	var index = param.name;
	    	decisionClickEvent(param);
	    });
	}
}
//展示 制度分类汇总 饼图
function showRegulation(names,values){
	var dom = document.getElementById("container_2");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '嵌套环形图';

	option = {
	    tooltip: {//鼠标悬浮事件tooltip的数据自定义
	        trigger: 'item',
	        formatter: "{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            name:'',
	            type:'pie',
	            radius: ['30%', '45%'],
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
	            data:add(names,values)
	        }
	    ]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function (param) {
	    	var index = param.name;
	    	regulationClickEvent();
	    });
	}
}
//展示 事项清单汇总 柱形图
function showItem(names,values){
	var dom = document.getElementById("container_3");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		grid: {
	    	y2:40
	    },
	    xAxis: {
	        type: 'category',
	        axisLabel: {
                show:true,
                interval: 0, //强制所有标签显示
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
	        data: names
	    },
	    yAxis: {
	        type: 'value',
				splitLine:{
				   show:false
				}
	    },
	    series: [{
	        data: values,
	        color:['#6097DA'],
	        type: 'bar',
	        barWidth : 30,
	        label: {
		       normal: {
		          show: true,
		          position: 'top',
		          textStyle: {
		            color: 'black'
		          }
		       }
	        }
	    }]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
	    	gotoLeader_Ietm(param);//重要的参数都在这里！
	    });
	}
}
//展示 会议分类汇总 饼图
function showMeeting(names,values){
	var dom = document.getElementById("container_4");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            color:['#FFBA79','#89D042','#B194E4','#54A4FD','#FF7171','#8B0000','#EEEE00','#CD6600','#8B8B00','#848484','#68228B','#00E5EE'],
	            name:'访问来源',
	            type:'pie',
	            radius: ['0', '65%'],
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
	            data:add(names,values)
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
//展示决策议题
function showSubject(legendData,xAxisData,series){
	var dom = document.getElementById("container_5");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:legendData,
	        top:5
	    },
	    color:['#66CD98','#FEBA4B','#FC6862','#43acef'],
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        triggerEvent:true,
	        data: xAxisData
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: series
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
			subjectClickEvent();//重要的参数都在这里！
	    });
	}
}

function add(names,values){
	var items = [];
	for (var i = 0; i < names.length; i++) {
		var item = {
				value:values[i], 
				name:names[i]
			};
		items.push(item);
	}
	return items;
}

//会议分类图表事件处理
function meetingClickEvent(){
	var href = "/tiol/url/goMeetingByPage.action"
	window.parent.addpage("会议分类列表",href,'meetingClassifyList');
}

/**
 * 查看重大措施详情页面
 */
function decisionClickEvent(param){
	var index = param.dataIndex;
	var dataType = param.seriesIndex;
	var type= decisionTypeArray[index];
	var objectId= decisionTypeArray[index];
	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+objectId+"\",\"industryId\":\""+instFlag+"\"}";
	if(dataType == '0'){
		var href = "/tiol/url/goLeaderDecisionSubject.action?parameter="+encodeURIComponent(data);
		window.parent.addpage("议题列表",href,'decisionSubjectList'+type+objectId);
	}else if(dataType == '1'){
		var href = "/tiol/url/goLeaderDecisionMeeting.action?parameter="+encodeURIComponent(data);
		window.parent.addpage("会议列表",href,'decisionMeetingList'+type+objectId);
	}
}

function showALlDecision(){
	var data = "{\"instFlag\":\""+instFlag+"\"}";
	var href = "/tiol/url/goLeaderDecisionDetail.action?parameter="+encodeURIComponent(data);
    window.parent.addpage("事项汇总列表",href,'item');
}

//制度分类图表事件处理
function regulationClickEvent(){
	var href = "/leader/regulation/regulationDetail.action?industryId=0"
	window.parent.addpage("制度清单列表",href,'leaderRegulation');
}
/**
 * 事项清单汇总跳转
 */
function gotoLeader_Ietm(param){
	var catalogType = "D";
	if(param.name == "重大决策"){
        catalogType = "D";
	}
    if(param.name == "重要人事任免"){
        catalogType = "H";
    }
    if(param.name == "重大项目安排"){
        catalogType = "P";
    }
    if(param.name == "大额度资金运作"){
        catalogType = "F";
    }
    var href = "/tiol/url/goItemListByAlone.action?catalogType="+catalogType;
    window.parent.addpage("事项汇总列表",href,'item','reload');
}

function showALlItem() {
    var data = "{\"instFlag\":\""+instFlag+"\"}";
	var href = "/tiol/url/goLeaderItem.action?parameter="+encodeURIComponent(data);
    window.parent.addpage("事项汇总列表",href,'item');
}

function showItem_6(){
	var dataURL = "{contextPath}/exception/queryException.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].name;
			var values = json.data[0].value;
			if(values==""){
				$("#container_6").html("<img id='img6' src='"+SYSURL.static+"/images/tiol/no_data.png'>");
				$("#img6").css("padding-top","45px");
			}else{
				var plantCap = new Array();
				for(var i=0;i < names.length;i++){
					var data = new Object();
					data.name = names[i];
					data.value = values[i];
					plantCap.push(data);
				}
				var datalist = [{
				    offset: [56, 48],
				    symbolSize: 85,
				    opacity: .95,
				    color: '#f467ce'
				}, {
				    offset: [35, 80],
				    symbolSize: 80,
				    opacity: .88,
				    color: '#7aabe2'
				}, {
				    offset: [20, 43],
				    symbolSize: 80,
				    opacity: .84,
				    color: '#ff7123'
				}, {
				    offset: [83, 30],
				    symbolSize: 80,
				    opacity: .8,
				    color: '#ffc400'
				}, {
				    offset: [36, 17],
				    symbolSize: 85,
				    opacity: .75,
				    color: '#5e333f'
				},  {
				    offset: [75, 75],
				    symbolSize: 85,
				    opacity: .68,
				    color: '#8a3647'
				}];
				var datas = [];
				for (var i = 0; i < plantCap.length; i++) {
				    var item = plantCap[i];
				    var itemToStyle = datalist[i];
				    datas.push({
				        name: item.name + '\n' + item.value,
				        value: itemToStyle.offset,
				        symbolSize: itemToStyle.symbolSize,
				        label: {
				            normal: {
				                textStyle: {
				                    fontSize: 11
				                }
				            }
				        },
				        itemStyle: {
				            normal: {
				                color: itemToStyle.color,
				                opacity: itemToStyle.opacity
				            }
				        }
				    })
				}
				showItemDetail(datas,datalist);
			}
		}
	},false);
}

//展示 事项清单汇总 柱形图
function showItemDetail(datas){
	var dom = document.getElementById("container_6");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		grid: {
	        show: false,
	        top: 10,
	        bottom: 10
	    },
	    xAxis: [{
	        gridIndex: 0,
	        type: 'value',
	        show: false,
	        min: 0,
	        max: 100,
	        nameLocation: 'middle',
	        nameGap: 5
	    }],
	    yAxis: [{
	        gridIndex: 0,
	        min: 0,
	        show: false,
	        max: 100,
	        nameLocation: 'middle',
	        nameGap: 30
	    }],
	    series: [{
	        type: 'scatter',
	        symbol: 'circle',
	        symbolSize: 120,
	        label: {
	            normal: {
	                show: true,
	                formatter: '{b}',
	                color: '#fff',
	                textStyle: {
	                    fontSize: '20'
	                }
	            }
	        },
	        itemStyle: {
	            normal: {
	                color: '#00acea'
	            }
	        },
	        data: datas
	    }]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
    	var href = "/exception/exceptionJump.action"
		window.parent.addpage("异常列表",href,'exceptionList');
	    });
	}
}
/**
 * 议题分类列表
 */
function subjectClickEvent(){
	var href = "/tiol/url/goLeaderSubjectClass.action"
	window.parent.addpage("议题分类列表",href,'subjectClassifyList');
}

