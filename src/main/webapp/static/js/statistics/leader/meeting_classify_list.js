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
	jt._("#industryId").value=id;
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
	var dataURL = "/statistics/meeting/queryMeetingDetail.action?industryID=" + id;
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
				showMeeting(names,values,"metcla");
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

function showMeeting(names,values,code){
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
	            color:['#FFBA79','#89D042','#B194E4','#54A4FD','#FF7171','#8B0000','#EEEE00','#CD6600','#8B8B00','#848484','#68228B','#00E5EE'],
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
			meetingClickEvent();//重要的参数都在这里！
	    });
	}
}

function showDecision(id){
	var dataURL = "{contextPath}/statistics/meeting/queryMeetingClassDetail.action?industryId=" + id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var types = json.data[0].type;
			var names = json.data[0].name;
			var values = json.data[0].value;
			showDecisionDetail(types,names,values,"methz");
		}
	},false);
}

/**
 * 动态添加叠层图信息
 * @param types
 * @param values
 * @returns {Array}
 */
function addItems(types,values) {
	var items=[];
	for( var i=0;i < types.length;i++){
		var item={
				name:types[i],
		        type:'bar',
		        stack: '数量',
		        data: values[i],
		        barWidth : 30,
		        abel: {
		            normal: {
		            	show: true
		            }
		        }
			}
		items.push(item);
	};
	return items;
}

function showDecisionDetail(types,names,values,code){
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
	        },
			textStyle:{ 
				fontSize:10
	        }
	    },
	    legend: {
	    	data:types
	    },
        color:['#FFBA79','#89D042','#B194E4','#54A4FD','#FF7171','#8B0000','#EEEE00','#CD6600','#8B8B00','#848484','#68228B','#00E5EE'],
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
                    rotate:0//-15度角倾斜显示
                },
	            data : names
	        }
	    ],
	    grid: {
            y2:20
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
	    series : addItems(types,values)
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
			meetingClickEvent();//重要的参数都在这里！
	    });
	}
}

function meetingClickEvent(){
	var href = "/tiol/url/goMeetingQuantity.action"
	window.parent.addpage("企业会议列表",href,'meetingQuantityList');
}

function itemView(id, typeId) {
	var href = "/tiol/url/goMeetingList.action?companyId=" + id + "&typeId=" + typeId;
	window.parent.addpage("企业会议列表",href,'meetingList'+id+typeId);
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
	var sURL = "{contextPath}/statistics/meeting/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}&industryId="+id+"&parameter="+encodeURIComponent(data);
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