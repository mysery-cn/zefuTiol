/**
 * 通过企业id获取企业名称
 */
function getCompanyNameById(){
	//获取页面跳转传参的单位ID
	var companyID = jt.getParam('companyId');
	//请求路径
	var dataURL = "{contextPath}/statistics/company/queryCompanyDetail.action?companyID="+companyID;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			if (json.data[0] != '') {
				jt._("#companyId").value=json.data[0].COMPANY_ID;
				$("#companyName").html(json.data[0].COMPANY_NAME);
			} else {
				return jt.Msg.showMsg("未查询到企业信息！")
			}
		}
	},false);
}

/**
 * 生成企业领导班子列表
 */
function showCompanyMemberView() {
	var companyId = jt._("#companyId").value;
	var dataURL = "/companyMember/queryCompanyMemberList.action?companyId=" + companyId;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var rowData = json.data;
			var tab = $("#cmTable");
			var tb = $(tab, "tbody");// 获取table里的tbod
			if (rowData.length > 0) {
				for (var i = 0; i < rowData.length; i++) {
					var names = rowData[i].NAME.split(",");
					var positions = rowData[i].POSITION.split(",");
					var tr = document.createElement("tr");
					$(tr).append('<td class="table_tr_b" style="width: 160px;font-weight: bold;">' + rowData[i].GROUP_TYPE + '</td>');
					var str = "";
					for (var j = 0; j < names.length; j++) {
						if (j != 0) {
							str = str + ",";
						}
						str = str + names[j]+"("+positions[j]+")"
					}
					$(tr).append('<td class="table_long_txt">' + str + '</td>');
					tb.append(tr);
				}
			} else {
				$("#cmTable").html("无记录");
			}
		}
	},false);
}

/**
 * 生成制度清单列表
 */
function showRegulationView() {
	var companyId = jt._("#companyId").value;
	var dataURL = "/regulation/queryRegulationList.action?companyId=" + companyId;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var rowData = json.data;
			var tab = $("#regTable");
			var tb = $(tab, "tbody");// 获取table里的tbod
			if (rowData.length > 0) {
				var tr = document.createElement("tr");
				var tr1 = document.createElement("tr");
				var rowlh = rowData.length;
				rowlh = 100 / rowlh;
				for (var i = 0; i < rowData.length; i++) {
					tr.className = "table_tr_b";
					$(tr).append('<td style="font-weight: bold;text-align: center;width:'+rowlh+'%">' + rowData[i].TYPE_NAME + '</td>');
					var name = rowData[i].REGULATION_NAME;
					var fileId = rowData[i].FILE_ID;
					var names = new Array();
					if (name==undefined) {
						name = '';
					} else {
						names = name.split(","); 
					}
					var fileIds = new Array();
					if (fileId==undefined) {
						fileId = '';
					} else {
						fileIds = fileId.split(","); 
					}
					var html = '';
					for (var j = 0; j < names.length; j++) {
						if (j > 0) {
							html = html+'<br>'
						}
						html = html + '<a style="color: #0000CD" onclick="showFileView('+"'"+fileIds[j]+"'"+')">' + names[j] + '</a>';

					}
					$(tr1).append('<td style="text-align: center;">'+html+'</td>');
				}
				tb.append(tr);
				tb.append(tr1);
			}
		}
	},false);
}

/**
 * 生成一级事项目录列表
 */
function showCatalogView() {
	var dataURL = "/catalog/queryCatalogLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var rowData = json.data;
			var cataDiv = $("#cataDiv");
			if (rowData.length > 0) {
				var div = document.createElement("div");
				var id = 0;
				var code = '';
				for (var i = 0; i < rowData.length; i++) {
					if (i==0) {
						id = rowData[i].CATALOG_ID;
						code = rowData[i].CATALOG_CODE;
						$(div).append('<a class="tab_top_cur" onclick="showStatSubjectViewRM(this,'+"'"+id+ "',"+"'"+code+ "'"+');">' + rowData[i].CATALOG_NAME + '</a>');
					} else {
						$(div).append('<a onclick="showStatSubjectViewRM(this,'+"'"+rowData[i].CATALOG_ID+ "',"+"'"+rowData[i].CATALOG_CODE+ "'"+');">' + rowData[i].CATALOG_NAME + '</a>');
					}
				}
				cataDiv.append(div);
				showStatSubjectView(this,id,code);
			}
		}
	},false);
}

function showStatSubjectViewRM(obj,id,code) {
	$("#cataDiv a").removeClass();
	showStatSubjectView(obj,id,code);
}

/**
 * 通过事项目录生成事项清单信息
 * @param obj
 * @param id
 */
function showStatSubjectView(obj,id,code) {
	if (id==undefined) {
		id = '';
	}
	jt._("#catalogId").value = id;
	jt._("#levelCode").value = code;
	var companyId = jt._("#companyId").value;
	$(obj).addClass("tab_top_cur");
	var dataURL = "/statistics/subject/querySubjectList.action?companyId="+companyId+"&catalogPid="+id;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var rowData = json.data;
			var tab = $("#statSubjectTable");
			tab.find("tr[class!='table_tr_b']").remove();
			var tb = $(tab, "tbody");// 获取table里的tbod
			var quantity = 0;
			if (rowData.length > 0) {
				for (var i = 0; i < rowData.length; i++) {
					var tr = document.createElement("tr");
					$(tr).append('<td>' + rowData[i].CATALOG_NAME + '</td>');
					$(tr).append('<td>' + rowData[i].SUBJECT_QUANTITY + '</td>');
					tb.append(tr);
					quantity = quantity + rowData[i].SUBJECT_QUANTITY;
				}
				$("#quantity").html(quantity);
			}
		}
	},false);
}

function showMeetingStatView(){
	var companyId = jt._("#companyId").value;
	var dataURL = "/statistics/meeting/queryMeetingDetail.action?companyId=" + companyId;
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
				$("#stat").css("display","block");
				showMeeting(names,values,"stat");
			} else {
				$("#stat").css("display","none");
				$("#imgno").css("display","block");
				$("#imgno").html("<img id='img2' src='/static/images/tiol/no_data.png'>");
				$("#img2").css("margin-top","110px");
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

/**
 * 会议数量统计图元
 */
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
	            radius : '45%',
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
			meetingClickEvent(param.name);//重要的参数都在这里！
	    });
	}
}

//展示 事项清单汇总 柱形图
function showItemView(){
	var companyId = jt._("#companyId").value;
	var dataURL = "/statistics/item/queryItemDetail.action?companyId=" + companyId;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var itemNames = json.data[0].name;
			var itemValues = json.data[0].value;
			showItem(itemNames,itemValues,"item");
		}
	},false);
}

//展示 事项清单汇总 柱形图
function showItem(names,values,code){
	var dom = document.getElementById(code);
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		grid: {
	    	y2:80
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
	    	gotoLeader_Ietm(param.name);//重要的参数都在这里！
	    });
	}
}

var catalogType = '';
/**
 * 事项清单汇总跳转
 */
function gotoLeader_Ietm(name){
    getCatalogByName(name);
    var companyId = jt._("#companyId").value;
    var href = "/tiol/url/goLeaderItemDetail.action?catalogType="+catalogType+"&companyId="+companyId;
    window.parent.addpage("事项汇总列表",href,'item'+catalogType+companyId);
}

function itemSubjectView(){
	var companyId = jt._("#companyId").value;
	var levelCode = jt._("#levelCode").value;
	var href="/tiol/url/goSubjectList.action?companyId="+companyId+"&levelCode="+levelCode;
	window.parent.addpage("企业议题列表",href,'itemSubjectList'+companyId+levelCode);
}
var typeId = '';
//会议分类图表事件处理
function meetingClickEvent(name){
	getMettingTypeByName(name);
	var companyId = jt._("#companyId").value;
	var href="/tiol/url/goMeetingList.action?companyId="+companyId+"&typeId="+typeId;
	window.parent.addpage("企业会议列表",href,'meeting'+companyId+name);
}

function getMettingTypeByName(name){
	var dataURL = "{contextPath}/meetingType/queryList.action?typeName="+encodeURIComponent(name);
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			if (json.data[0] != '') {
				typeId = json.data[0].TYPE_ID;
			} else {
				return jt.Msg.showMsg("未查询到会议类型信息！")
			}
		}
	},false);
}

function getCatalogByName(name){
	var dataURL = "{contextPath}/catalog/queryCatalogLevel.action?catalogName="+encodeURIComponent(name);
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			if (json.data[0] != '') {
				catalogType = json.data[0].CATALOG_CODE;
			} else {
				return jt.Msg.showMsg("未查询到事项目录信息！")
			}
		}
	},false);
}