function queryDecisionDetail(){
	var dataURL = "/statistics/decision/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var statisticsNames = json.data[0].nameArray;
			var meeting_quantities = json.data[0].meetingArray;
			var subject_quantities = json.data[0].subjectArray;
            if(isEmpty(statisticsNames) || isEmpty(meeting_quantities) || isEmpty(subject_quantities)){
                $("#decision").html("<img id='img1' src='/static/images/tiol/no_data.png'>");
                $("#img1").css("padding-top","45px");
                //$("#img1").css("padding-left","45px");
            }else{
                showDecision(statisticsNames,meeting_quantities,subject_quantities);
            }
		}
	},false);
}
//展示 贯彻执行党和国家决策部署的重大措施 柱形图
function showDecision(statisticsNames,meeting_quantities,subject_quantities,names){
	var dom = document.getElementById("decision");
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
		        data: ['议题数量', '会议数量']
		    },
		    grid: {
		    	y2:45
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
		                    var index = 4;
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
	    myChart.on('click', function(param) {
    		var href = "/tiol/url/goDecisionDetail.action";
	    	window.parent.addpage("重大措施列表",href,'tourDecisionList');
	    });
	}
}

function queryItemDetail(){
	var dataURL = "/statistics/item/queryItemDetail.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var itemNames = json.data[0].name;
			var itemValues = json.data[0].value;
			var num = 0;
            for (var i = 0; i < itemValues.length; i++) {
                num = num + parseInt(itemValues[i]);
            }
            if(isEmpty(itemNames) || isEmpty(itemValues) || num == 0){
                $("#item").html("<img id='img2' src='/static/images/tiol/no_data.png'>");
                $("#img2").css("padding-top","45px");
                //$("#img2").css("padding-left","45px");
            }else{
                showItem(itemNames,itemValues);
            }
		}
	},false);
}

//展示 事项清单汇总 柱形图
function showItem(names,values){
	var dom = document.getElementById("item");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
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
	    grid: {
            y2:35
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
	;
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
	    	gotoLeader_Ietm();//重要的参数都在这里！
	    });
	}
}

function queryRegulationDetail(){
	var dataURL = "/statistics/regulation/queryRegulationList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var names1 = json.data[0].nameArray;
			var values1 = json.data[0].quantityArray;
			if(names1 == "undefind" || values1 == "undefind" || names1 == "" || values1 == ""){
				$("#regulation").html("<img id='img2' src='/static/images/tiol/no_data.png'>");
				$("#img2").css("padding-top","45px");
				//$("#img2").css("padding-left","45px");
			}else{
				showRegulation(names1,values1);
			}
		}
	},false);
}

//展示 制度分类汇总 饼图
function showRegulation(names,values){
	var dom = document.getElementById("regulation");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '嵌套环形图';

	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{b}: {c} ({d}%)"
	    },
	    //color : ['#FFB978','#42C9D1','#42E2A4','#89D142','#B195E4','#55A4FD'],
	    series: [
	        {
	            name:'',
	            type:'pie',
	            radius: ['30%', '45%'],
//	            label: {
//	                normal: {
//	                	formatter:get,
//	                	textStyle:{       //这只是为了让文字居中而已
//	                		　　align:"center",            //水平对齐方式可选left，right，center
//	                		　　baseline:"top",　　　　//垂直对齐方式可选top，bottom，middle
//	                		},
//	                    backgroundColor: '#eee',
//	                    borderColor: '#aaa',
//	                    borderWidth: 1,
//	                    borderRadius: 4,
//	                    lineHeight: 33
//	                }
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
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function(param) {
	    	regulationClickEvent();//重要的参数都在这里！
	    });
	}
}


/**
 * 显示查询条件
 */
function showSearch(){
	$("#hideSearch").css('display','block'); 
	$("#searchDetail").css('display','block'); 
	$("#showSearch").css('display','none'); 
	$("#hideH").css('display','none'); 
}

/**
 * 隐藏查询条件
 */
function hideSearch(){
	$("#hideSearch").css('display','none'); 
	$("#searchDetail").css('display','none'); 
	$("#showSearch").css('display','block'); 
	$("#hideH").css('display','block'); 
}

/**
 * 查询方法
 */
function queryData(){
	//企业名称
	var companyName = jt._("#companyName").value;
	var data = "{\"companyName\":\""+companyName+"\"}";
	var sURL = "{contextPath}/statistics/company/querPageList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	
}

/**
 * 事项清单汇总跳转
 */
function gotoLeader_Ietm(){
//	var href = "/tiol/url/goTourItem.action";
//	window.parent.addpage("事项清单",href,'tourItem');
	
	var href = "/tiol/url/goLeaderItem.action";
    window.parent.addpage("事项汇总列表",href,'item');
}

/**
 * 制度分类
 */
function regulationClickEvent(){
//    var href = "/tiol/url/goTourRegulation.action";
//	window.parent.addpage("制度分类",href,'tourRegulation');
	
	var href = "/leader/regulation/regulationDetail.action"
	window.parent.addpage("制度清单列表",href,'leaderRegulation');
}

/**
 * 显示不合规议题数据
 */
function showNonstandard(companyId){
	var href = "/tiol/url/goTourSubjectNon.action?companyId="+companyId+"&search=1";
	window.parent.addpage("企业议题列表",href,'tourSubjectNon'+companyId);
}

var get = function(e) {
	var newStr = " ";
	var start, end;
	var name_len = e.name.length;// 每个内容名称的长度
	var max_name = 5;// 每行最多显示的字数
	var new_row = Math.ceil(name_len / max_name); // 最多能显示几行，向上取整比如2.1就是3行
	if (name_len > max_name) {// 如果长度大于每行最多显示的字数
		for (var i = 0; i < new_row; i++) {// 循环次数就是行数
			var old = '';// 每次截取的字符
			start = i * max_name;// 截取的起点
			end = start + max_name;// 截取的终点
			if (i == new_row - 1) {// 最后一行就不换行了
				old = e.name.substring(start);
			} else {
				old = e.name.substring(start, end) + "\n";
			}
			newStr += old; // 拼接字符串
		}
	} else {// 如果小于每行最多显示的字数就返回原来的字符串
		newStr = e.name;
	}
	return newStr;
}