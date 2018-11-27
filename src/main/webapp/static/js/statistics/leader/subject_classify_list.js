/**
 * 加载行业列表
 */
function loadingIndustry(){
	var dataURL = "{contextPath}/statistics/industry/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data[0].nameArray.length;i++){
	        	$("#treeDiv").append("<div class='industry_contents'> <a onclick=showView(this,'"+json.data[0].idArray[i]+"');>"+json.data[0].nameArray[i]+"</a></div>");
	        }
		}
	},false);
}

function showView(obj,id) {
	$("#treeDiv a").removeClass();
	$(obj).addClass("industry_contents_cur");
	if (id==undefined) {
		id = '';
	}
	showSubjectView(id);
	showDecision(id);
	showSubjectDecision(id);
	resetSearch();
	jt._("#industryId").value=id;
	queryData(id);
}

function loadingView(id){
	if (id==undefined) {
		id = '';
	}
	showSubjectView(id);
	showDecision(id);
	showSubjectDecision(id);
}

function showSubjectView(id){
	var dataURL = "/statistics/subject/getSubjectChartData.action?industryId=" + id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].name;
			var values = json.data[0].value;
			var flag = false;
			if (values != undefined) {
				for (var i = 0; i < values.length; i++) {
					if (values[i] != 0) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				$("#imgno").css("display","none");
				$("#sbjcla").css("display","block");
				showSubjectClass(names,values,"sbjcla");
			} else {
				$("#sbjcla").css("display","none");
				$("#imgno").css("display","block");
				$("#imgno").html("<img id='img2' src='/static/images/tiol/no_data.png'>");
				$("#img2").css("padding-top","80px");
				$("#img2").css("padding-left","50px");
			}
		}
	},false);
}

/**
 * 动态添加饼图信息
 * @param names
 * @param values
 * @returns {Array}
 */
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

function showSubjectClass(names,values,code){
	var dom = document.getElementById(code);
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
	            color:['#FFBA79','#89D042','#B194E4','#54A4FD','#FF7171','#8B0000'],
	            name:'访问来源',
	            radius : '70%',
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
	            data:add(names,values)
	        }
	    ]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
	    	var href = "/tiol/url/goReformSubject.action";
	    	window.parent.addpage("企业议题列表",href,'reformSubject');
	    });;
	}
}

function showDecision(id){
	//展示决策议题
	var dataURL = "{contextPath}/statistics/subject/getStatisticsSubjectChartData.action?industryId=" + id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var series = json.data[0].series;
			var xAxisData = json.data[0].xAxisData;
			var legendData = json.data[0].legendData;
			showSubject(legendData,xAxisData,series);
		}
	},false);
}

//展示决策议题
function showSubject(legendData,xAxisData,series){
	var dom = document.getElementById("sbjhz");
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
	}
}

/**
 * 显示三重一大决策情况
 */
function showSubjectDecision(id){
	var dataURL = "{contextPath}/statistics/meeting/queryDecisionSubjectDetail.action?industryId=" + id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].name;
			var pass = json.data[0].pass;
			var defer = json.data[0].defer;
			var veto = json.data[0].veto;
			showSubjectDecisionDetail(names,pass,defer,veto);
		}
	},false);
}


function showSubjectDecisionDetail(names,pass,defer,veto){
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

function subjectView(id, typeId,companyName) {
	var href = "/tiol/url/goReformSubject.action?companyID=" + id + "&typeID=" + typeId+"&companyName="+companyName;
	window.parent.addpage("企业议题列表",href,'reformSubject'+id+typeId);
}

/**
 * 列表搜索
 */
function queryData(id){
	if (id==undefined) {
		id = '';
	}
	id = jt._("#industryId").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	var data = "{\"companyName\":\""+companyName+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/subject/querySubjectClassList.action?currentPage={page}&pageSize={pageSize}&industryId="+id+"&parameter="+encodeURIComponent(data);
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

/**
 * 重置查询条件
 */
function resetSearch(){
	jt._("#companyName").value=''; 
}