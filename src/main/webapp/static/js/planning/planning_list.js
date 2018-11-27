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
	showMeetingView(id);
	showDecision(id);
	resetSearch();
	jt._("#industryId").value = id;
	queryData(id);
}

function loadingView(id){
	if (id==undefined) {
		id = '';
	}
	showMeetingView(id);
	showDecision(id);
}

function showMeetingView(id){
	var dataURL = "/statistics/directorate/queryStatisticsDirectorateVia.action?industryId="+id;
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
				$("#metcla").css("display","block");
				showcCatalogView(names,values,"metcla");
			} else {
				$("#metcla").css("display","none");
				$("#imgno").css("display","block");
				$("#imgno").html("<img id='img2' src='/static/images/tiol/no_data.png'>");
				$("#img2").css("margin-top","55px");
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

function showcCatalogView(names,values,code){
	var dom = document.getElementById(code);
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
	    tooltip: {
	        trigger: 'item',
//	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	        formatter: "{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            color:['#FFBA79','#54A4FD'],
	            name:'访问来源',
	            radius : '70%',
//	            center: ['50%', '50%'],
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
			catalogClickEvent();//重要的参数都在这里！
	    });
	}
}

function showDecision(id){
	var dataURL = "{contextPath}/statistics/subject/queryItemSubjectDetail.action?industryId=" + id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].name;
			var passs = json.data[0].pass;
			var defers = json.data[0].defer;
			var vetos = json.data[0].veto;
			showDecisionDetail(names,passs,defers,vetos,"methz");
		}
	},false);
}

function showDecisionDetail(names,passs,defers,vetos,code){
	var dom = document.getElementById(code);
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
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            axisTick : {show: false},
	            axisLabel:{  
	            	show:true,
	                interval: 0, //强制所有标签显示
	                formatter: function (params){   //标签输出形式 ---请开始你的表演
	                    var index = 5;
	                    var str = '';
	                    for(var i=0 ; i < params.length; i+=index){
	                        var tmp=params.substring(i, i+index);
	                        str+=tmp+'\n';
	                    }
	                    return str;
	                }
                },
                triggerEvent: true,
	            data: names
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
	            stack: '数量',
	            data: passs,
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
	            stack: '数量',
	            data: defers,
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
	            stack: '数量',
	            data: vetos,
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
			centralClickEvent(param.name);//重要的参数都在这里！
	    });
	}
}

function catalogClickEvent(){
	var href="/jsp/planning/catalog_subject_list.jsp"
	window.parent.addpage("企业议题列表",href,"catalog_subject");
}

var itemId ='';
function centralClickEvent(name){
	getItemByName(name);
	var href="/jsp/planning/central_subject_list.jsp?itemId="+itemId;
	window.parent.addpage("企业议题列表",href,"central_subject"+itemId);
}

function itemView(id,mId,cId) {
	var href="/subject/subjectDetail.action?subjectId="+id+"&meetingId="+mId+"&companyId="+cId;
	window.parent.addpage("议题详情",href,id+mId+cId);
}

/**
 * 列表搜索
 */
function queryData(id){
	var flag = true;
	if (id==undefined) {
		id = '';
	} else {
		flag = false;
	}
	id = jt._("#industryId").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	var meetingName = jt._("#meetingName").value;
	var subjectName = jt._("#subjectName").value;
	var year = jt._("#yearSelect").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (flag && jt._("#times").value != '') {
		if (startTime == "" || endTime == "") {
			return jt.Msg.showMsg("查询起止时间不可为空!");
		}
	}
	var subjectResult = jt._("#subjectResult").value;
	var data = "{\"companyName\":\""+companyName+"\",\"meetingName\":\""+meetingName+"\",\"subjectName\":\""+subjectName+"\",\"year\":\""+year+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/subject/queryMeetingSubjectListByPage.action?currentPage={page}&pageSize={pageSize}&industryId="+id+"&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	getAutoCount(id);
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
	jt._("#meetingName").value='';
	jt._("#subjectName").value='';
	jt._("#yearSelect").value='';
	if (jt._("#times").value != '0') {
		jt._("#times").value='';
	}
	if (jt._("#times").value == '0') {
		$("td[name='timeDiv']").hide();
		jt._("#times").value='';
	}
	jt._("#startTime").value='';
	jt._("#endTime").value='';
	jt._("#subjectResult").value='';
}

function getAutoCount(id){
	var industryId = jt._("#industryId").value;
	var companyName = jt._("#companyName").value;
	var meetingName = jt._("#meetingName").value;
	var subjectName = jt._("#subjectName").value;
	var year = jt._("#yearSelect").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (id != "" && id != undefined) {
		industryId = id;
	}
	var subjectResult = jt._("#subjectResult").value;
	var data = "{\"companyName\":\""+companyName+"\",\"meetingName\":\""+meetingName+"\",\"subjectName\":\""+subjectName+"\",\"year\":\""+year+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var dataURL = "{contextPath}/statistics/subject/getMeetingSubjectAutoCount.action?industryId="+industryId+"&parameter="+encodeURIComponent(data);
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			$("#all").html(json.data[0].all);
			$("#have").html(json.data[0].have);
			$("#not").html(json.data[0].not);
		}
	},false);
}

function getItemByName(name){
	var dataURL = "{contextPath}/item/queryItemType.action?itemName="+encodeURIComponent(name);
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			if (json.data[0] != '') {
				itemId = json.data[0].ITEM_ID;
			} else {
				return jt.Msg.showMsg("未查询到事项清单信息！")
			}
		}
	},false);
}