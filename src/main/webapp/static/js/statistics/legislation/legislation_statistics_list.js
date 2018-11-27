/**
 * 页面初始化
 */
jQuery(function($) {
	var width=$('.charts_legal table').width();
	loadStatisticsYear();
	//展示统计环图
	 $.ajax({
		url : "/statistics/legislation/queryLegislationChart.action",
		type : "POST",
		async: false, 
		dataType:"json",
        success: function (result) {
	        var data = result.data[0];
            $("input[name='legalType']").each(function(index,element){
            	if(data.type!=undefined){
            		var i = data.type.indexOf(element.value);
            		if(i !=-1 && data.total[i]>0){
                		showLegislation(width,data.quantity[i],data.subtraction[i],data.total[i],element.value);
            		}else{
                		$("#container_"+(index+1)).html("<img id='img"+(index+1)+"' src='"+SYSURL.static+"/images/tiol/no_data.png'>");
                	}
            	}else{
            		$("#container_"+(index+1)).html("<img id='img"+(index+1)+"' src='"+SYSURL.static+"/images/tiol/no_data.png'>");
            	}
            });
		}
	});
});

/**
 * 查询统计年份列表
 */
function loadStatisticsYear(){
	var dataURL = "{contextPath}/statistics/legislation/listStatisticsYear.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				$("#year").append("<option value='"+json.data[i].value+"'>"+json.data[i].text+"</option>");
	        }
		}
	},false);
}
/**
 * 展示法规环形图
 */
function showLegislation(width,quantity,subtraction,total,type){
	var dom = document.getElementById("container_"+type);
	dom.style.width = width/8+'px';
	dom.style.height = width/8+'px';
	var myChart = echarts.init(dom);
	var app = {};
	option = null;

	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{c} ({d}%)"
	    },

	    grid:{
	                         top:"50px",
	                         left:"50px",
	                         right:"15px",
	                         bottom:"50px"
	    },
	    series: [
	        {
	        	color:['#FF4800','#DBE1E7'],
	            name:'访问来源',
	            type:'pie',
	            radius: ['50%', '70%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: true,
	                    position: 'center',
	                    textStyle:{
	                        color:'#081018'
	                    },
	                    formatter:function(param){
	                        return quantity+"/"+total;
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	                {value:quantity, name:'quantity'},
	                {value:subtraction, name:'subtraction'}
	            ]
	        }
	    ]
	};
	;
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	    myChart.on('click', function (param) {
	    	viewLegislationOption(type,'','');
	    }); 
	}
}
/**
 * 重置方法
 */
function resetAll(){
	//议题名称
	$("#companyName").val("");
	//制度名称
	$("#year").val("");
}
/**
 * 显示隐藏筛选
 */
function showHideSearch(){
	if($('#search').is(':hidden')){
	       $('#showHide').html("隐藏筛选");
           $('#search').show();
    }else{
    	$('#showHide').html("显示筛选");
          $('#search').hide();
   }
}
/**
 * 查看议题详情
 */
function viewSubjectDetail(subjectId,meetingId,companyId){
	var href=contextPath+"/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
	window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
}
/**
 * 查看法规统计列表
 */
function viewLegislationOption(optionType,companyId,companyName){
	var href=contextPath+"/statistics/legislation/legislationOption.action?optionType="+optionType+"&companyId="+companyId+"&companyName="+companyName;
	window.parent.addpage("法规统计详情",href,'legislationOption'+companyId+optionType);
}

/**
 * 查询方法
 */
function queryData(){
	var companyName=$('#companyName').val();
	var year=$("#year option:selected").val();
	//请求参数
	var data = "{\"companyName\":\""+companyName+"\",\"year\":\""+year+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/legislation/listLegislationStatistics.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}


