/**
 * 展示规则制图柱形图
 */
function showRegulation(){
	var dataURL = "{contextPath}/statistics/regulation/queryRegulationConstructionDetail.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].name;
			var Y = json.data[0].Y;
			var F = json.data[0].F;
            if(isEmpty(names)){
                $("#regulation").html("<img id='img1' src='/static/images/tiol/no_data.png'>");
                $("#img1").css("padding-top","45px");
                $("#img1").css("padding-left","45px");
            }else {
                showRegulationDetail(names, Y, F);
            }
		}
	},false);
}

function showRegulationDetail(names,Y,F){
	var dom = document.getElementById("regulation");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '正负条形图';

	option = {
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    grid: {
	    	x:130,
	    	y2:20
	    },
	    color : ['#6694CC','#D3D3D3'],
	    legend: {
	        data:['已制定企业数', '未制定企业数']
	    },
	    xAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    yAxis : [
	        {
	            type : 'category',
	            axisTick : {show: false},
	            axisLabel: {
	                show:true,
	                interval: 0, //强制所有标签显示
	                formatter: function (params){   //标签输出形式 ---请开始你的表演
	                    var index = 10;
	                    var newstr = '';
	                    for(var i=0 ; i < params.length; i+=index){
	                        var tmp=params.substring(i, i+index);
	                        newstr+=tmp+'\n';
	                    }
	                    return newstr;
	                }
	            },
	            triggerEvent: true,
	            data: names
	        }
	    ],
	    series : [
	        {
	            name:'已制定企业数',
	            type:'bar',
	            barWidth : 20,
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true
	                }
	            },
	            data: Y
	        },
	        {
	            name:'未制定企业数',
	            type:'bar',
	            barWidth : 20,
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true
	                }
	            },
	            data:F
	        }
	    ]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
//    		var href = "/tiol/url/goTourRegulation.action";
//	    	window.parent.addpage("制度分页",href,'tourRegulation');
	    	
	    	var href = "/leader/regulation/regulationDetail.action"
    		window.parent.addpage("制度清单列表",href,'leaderRegulation');
	    });
	}
}

/**
 * 获取事项清单汇总数据
 */
function showItem(){
	var dataURL = "{contextPath}/statistics/meeting/queryDecisionMeetingDetail.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].name;
			var values = json.data[0].value;
            if(isEmpty(names) || isEmpty(values)){
                $("#item").html("<img id='img1' src='/static/images/tiol/no_data.png'>");
                $("#img1").css("padding-top","45px");
                $("#img1").css("padding-left","45px");
            }else{
                var plantCap = new Array();
                for(var i=0;i < names.length;i++){
                    var data = new Object();
                    data.name = names[i];
                    data.value = values[i];
                    plantCap.push(data);
                }
                var datalist = [{
                    offset: [50, 50],
                    symbolSize: 80,
                    opacity: .95,
                    color: '#f467ce'
                }, {
                    offset: [15, 85],
                    symbolSize: 80,
                    opacity: .88,
                    color: '#7aabe2'
                }, {
                    offset: [15, 53],
                    symbolSize: 50,
                    opacity: .84,
                    color: '#ff7123'
                }, {
                    offset: [73, 20],
                    symbolSize: 80,
                    opacity: .95,
                    color: '#ffc400'
                }, {
                    offset: [20, 20],
                    symbolSize: 70,
                    opacity: .75,
                    color: '#5e333f'
                }, {
                    offset: [44, 15],
                    symbolSize: 50,
                    opacity: .92,
                    color: '#B452CD'
                }, {
                    offset: [75, 75],
                    symbolSize: 70,
                    opacity: .68,
                    color: '#8a3647'
                }, {
                    offset: [95, 42],
                    symbolSize: 70,
                    opacity: .6,
                    color: '#68333f'
                }, {
                    offset: [50, 85],
                    symbolSize: 50,
                    opacity: .6,
                    color: '#68553f'
                }];
                var datas = [];
                for (var i = 0; i < plantCap.length; i++) {
                    var item = plantCap[i];
                    var itemToStyle = datalist[i];
                    datas.push({
                        name: item.value + '\n' + item.name,
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
	var dom = document.getElementById("item");
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
	    	var href = "/tiol/url/goMeetingQuantity.action?year=2018"
    		window.parent.addpage("企业会议列表",href,'reformMeeting');
	    });
	}
}

/**
 * 显示三重一大决策情况
 */
function showDecision(){
	var dataURL = "{contextPath}/statistics/meeting/queryDecisionSubjectDetail.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].name;
			var pass = json.data[0].pass;
			var defer = json.data[0].defer;
			var veto = json.data[0].veto;
            if(isEmpty(names)){
                $("#decision").html("<img id='img1' src='/static/images/tiol/no_data.png'>");
                $("#img1").css("padding-top","45px");
                $("#img1").css("padding-left","45px");
            }else{
                showDecisionDetail(names,pass,defer,veto);
            }
		}
	},false);
}


function showDecisionDetail(names,pass,defer,veto){
	var dom = document.getElementById("decision");
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
	        data:['通过','缓议','否决']
	    },
	    color : ['#4FDCA5','#D2A340','#FF7171'],
	    xAxis : [
	        {
	            type : 'category',
	            axisLabel:{  
                    interval:0,//横轴信息全部显示  
                   // rotate:-30,//-15度角倾斜显示
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
            y2:40
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
	            name:'否决',
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
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
	    	var href = "/tiol/url/goReformSubject.action";
	    	window.parent.addpage("企业议题列表",href,'reformSubject');
	    });
	}
}

/**
 * 列表搜索
 */

function queryData(){
	//企业名称
	var companyName = jt._("#companyName").value;
	//请求路径
	var data = "{\"companyName\":\""+companyName+"\"}";
	var sURL = "{contextPath}/statistics/company/querPageList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

/**
 * 显示查询条件
 */
function showSearch(){
	$("#hideSearch").css('display','block'); 
	$("#searchDetail").css('display','block'); 
	$("#showSearch").css('display','none'); 
}

/**
 * 隐藏查询条件
 */
function hideSearch(){
	$("#hideSearch").css('display','none'); 
	$("#searchDetail").css('display','none'); 
	$("#showSearch").css('display','block'); 
}


