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
	resetSearch();
	jt._("#industryId").value = id;
	queryData(id);
}

function loadingView(id){
	if (id==undefined) {
		id = '';
	}
	showSubjectView(id);
	showDecision(id);
}

function showSubjectView(id){
	var dataURL = "/statistics/item/queryCatalogSubjectFDetail.action?industryId="+id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].nameArray;
			var values = json.data[0].quantityArray;
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
				showSubjectDetail(names,values,"metcla");
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

function showSubjectDetail(names,values,code){
	var dom = document.getElementById(code);
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '嵌套环形图';

	option = {
	    tooltip: {
	        trigger: 'item',
//	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	        formatter: "{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius: ['30%', '45%'],
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
	    myChart.on('click', function (param) {
	    	catalogClickEvent(param.name);
	 }); 
	}
}

function showDecision(id){
	var dataURL = "{contextPath}/statistics/item/queryCatalogItemFDetail.action?industryId=" + id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names = json.data[0].nameArray;
			var values = json.data[0].quantityArray;
			showDecisionDetail(names,values,"methz");
		}
	},false);
}

function showDecisionDetail(names,values,code){
	var dom = document.getElementById(code);
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		xAxis : {
			type : 'category',
			data : names
		},
		yAxis : {
			type : 'value',
			splitLine : {
				show : false
			}
		},
		series : [ {
			data : values,
			color : [ '#6097DA' ],
			type : 'bar',
			barWidth : 30,
			label : {
				normal : {
					show : true,
					position : 'top',
					textStyle : {
						color : 'black'
					}
				}
			}
		} ]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
			centralClickEvent();//重要的参数都在这里！
	    });
	}
}

var catalogCode = '';
function catalogClickEvent(name){
	getCataLogByName(name);
	var href="/tiol/url/goItemSubjectList.action?levelCode=F&catalogCode="+catalogCode;
	window.parent.addpage("企业议题列表",href,"item_subject"+catalogCode);
}

function centralClickEvent(){
	var href="/jsp/financial/catalog_item_list.jsp?levelCode=F";
	window.parent.addpage("企业清单列表",href,"catalog_item");
}

function itemView(id, catalogCode) {
	var href="/tiol/url/goItemSubjectList.action?levelCode=F&companyId="+id+"&catalogCode="+catalogCode;
	window.parent.addpage("企业议题列表",href,id+catalogCode);
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
	var sURL = "{contextPath}/statistics/item/queryItemSubjectByPage.action?currentPage={page}&pageSize={pageSize}&industryId="+id+"&parameter="+encodeURIComponent(data);
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

function getCataLogByName(name){
	var dataURL = "{contextPath}/catalog/queryCatalogFLevel.action?catalogName="+encodeURIComponent(name);
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			if (json.data[0] != '') {
				catalogCode = json.data[0].CATALOG_CODE;
			} else {
				return jt.Msg.showMsg("未查询到事项目录信息！")
			}
		}
	},false);
}