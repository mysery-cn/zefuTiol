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
			if(instFlag == null || instFlag == "" || instFlag == "undefind"){
				$("#treeDiv").append("<div class='industry_contents'><a class='industry_contents_cur' id='all' onclick='reloadRegulationData(1)'>全部</a></div>");
			}else{
				$("#treeDiv").append("<div class='industry_contents'><a id='all' onclick='reloadRegulationData(1)'>全部</a></div>");
			}
			for(var i=0;i<json.data[0].nameArray.length;i++){
				if(instFlag == json.data[0].idArray[i]){
					$("#treeDiv").append("<div class='industry_contents'> <a id='"+json.data[0].idArray[i]+"' class='industry_contents_cur' onclick=reloadRegulationData('"+json.data[0].idArray[i]+"');>"+json.data[0].nameArray[i]+"</a></div>");
				}else{
					$("#treeDiv").append("<div class='industry_contents'> <a id='"+json.data[0].idArray[i]+"' onclick=reloadRegulationData('"+json.data[0].idArray[i]+"');>"+json.data[0].nameArray[i]+"</a></div>");
				}
	        }
		}
	},false);
}


/**
 * 重新加载事项汇总列表
 */
function reloadRegulationData(id){
	if(id == 1){
		id = "";
	}
	$("#industryID").val(id);
	//设置选中
	$("#treeDiv a").removeClass();
	if(id == null || id == "" || id == "undefind"){
		document.getElementById("all").className += 'industry_contents_cur';//追加一个class
	}else{
		document.getElementById(id).className += 'industry_contents_cur';//追加一个class
	}
	//企业名称
	var companyName = jt._("#companyName").value;
	var data = "{\"companyName\":\""+companyName+"\",\"industryID\":\""+id+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/item/queryIndustryItemList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

/**
 * 查询方法
 */
function queryData(){
	//行业ID
	var industryID = jt._("#industryID").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	//请求路径
	var data = "{\"companyName\":\""+companyName+"\",\"industryID\":\""+industryID+"\"}";
	var sURL = "{contextPath}/statistics/item/queryIndustryItemList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

/**
 * 查看企业事项详情
 */
function editItem(companyID){
	var href = "/tiol/url/goLeaderItemDetail.action?companyId="+companyID;
	window.parent.addpage("事项清单列表",href,'itemDetail' + companyID,'reload');
}

/**
 * 查询企业事项清单By类型
 */
function editItemCatalogType(companyID,catalogType){
	var href = "/tiol/url/goLeaderItemDetail.action?companyId="+companyID + "&catalogType=" + catalogType;
	window.parent.addpage("事项清单列表",href,'itemDetail' + companyID,'reload');
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


