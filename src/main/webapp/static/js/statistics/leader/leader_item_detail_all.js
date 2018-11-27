/**
 * 读取页面信息数据
 */
function loadMessage(){
	var catalogType = jt._("#catalogType").value;
	var catalogId = "";
	//查询事项目录列表
	var dataURL = "{contextPath}/catalog/queryCatalogTree.action";
	var jPost= {};
	jPost.companyID = "";
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			var catalogTreeTop = json.data[0].catalogTreeTop;
			var catalogTree = "";
			catalogTree = catalogTree + '<ul class="sidebar-menu">';
			catalogTree = catalogTree + '<li class="treeview active">';
			catalogTree = catalogTree + '<a href="#" onclick=reloadItem("")>';
			catalogTree = catalogTree + '<i class="icon iconfont">&#xe706;</i><span>全部('+json.data[0].allNumber+')</span>';
			catalogTree = catalogTree + '</a>';
			catalogTree = catalogTree + '<ul class="treeview-menu">';
			for(var i = 0; i < catalogTreeTop.length; i++){
				//获取二级数据
				var secondary = catalogTreeTop[i].catalogTreeSecondary;
				if(catalogType != null && catalogType != ""){
					if(catalogTreeTop[i].catalogCode == catalogType){
						catalogTree = catalogTree + '<li class="active">';
						catalogId = catalogTreeTop[i].catalogId;
					}else{
						catalogTree = catalogTree + '<li>';
					}
				}else{
					catalogTree = catalogTree + '<li>';
				}
				catalogTree = catalogTree + '<a href="#" onclick=reloadItem("'+catalogTreeTop[i].catalogId+'")><i class="icon iconfont">&#xe638;</i>'+catalogTreeTop[i].catalogName+'('+catalogTreeTop[i].totalCount+')</a>';
				//二级菜单
				catalogTree = catalogTree + '<ul class="treeview-menu">';
				if(secondary != null && secondary.length > 0){
					for(var s = 0; s < secondary.length; s++){
						//获取三级数据
						var threeary = secondary[s].catalogTreeThreeary;
						catalogTree = catalogTree + '<li>';
						if(threeary != null && threeary.length > 0){
							catalogTree = catalogTree + '<a href="#" onclick=reloadItem("'+secondary[s].catalogId+'")><i class="icon iconfont">&#xe638;</i>'+secondary[s].catalogName+'('+secondary[s].totalCount+')</a>';
							catalogTree = catalogTree + '<ul class="treeview-menu">';
							for (var t = 0; t < threeary.length; t++) {
								catalogTree = catalogTree + '<li><a href="#" onclick=reloadItem("'+threeary[t].catalogId+'")><i class="icon iconfont">&#xe6e3;</i>'+threeary[t].catalogName+'('+threeary[t].totalCount+')</a></li>';
							}
							catalogTree = catalogTree + '</ul>';
						}else{
							catalogTree = catalogTree + '<a href="#" onclick=reloadItem("'+secondary[s].catalogId+'")><i class="icon iconfont">&#xe638;</i>'+secondary[s].catalogName+'('+secondary[s].totalCount+')</a>';
						}
						catalogTree = catalogTree + '</li>';
					}
				}
				catalogTree = catalogTree + '</ul>';
				catalogTree = catalogTree + '</li>';
	        }
			catalogTree = catalogTree + '</ul>';
			catalogTree = catalogTree + '</li>';
			catalogTree = catalogTree + '</ul>';
			$("#catalogTree").append(catalogTree);
			$.sidebarMenu($('.sidebar-menu'));
			if(catalogType != null && catalogType != ""){
				reloadItem(catalogId);
			}
		}
	},false);
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
 * 解析排序顺序
 */
function formatStatus(meetingDetail){
	if(meetingDetail == "" || meetingDetail == null || meetingDetail == "undefind"){
		return "";
	}
	var order = meetingDetail.split(",");
	var detail = '';
	for (var i = 0; i < order.length; i++) {
		detail = detail + '<td style="text-align: left;">';
		detail = detail + '<div class="bg_meeting"><div class="bg_meet_left"></div><div class="meeting_name_bg">'+order[i]+'</div><div class="bg_meet_right"></div></div>';
		if(i < order.length - 1){
			detail = detail + '<div class="meetting_line"></div>';
		}
		detail = detail + '</td>';
	}
	return detail;
}

/**
 * 重新加载数据
 */
function reloadItem(catalogId){
	$("#catalogId").val(catalogId);
	//获取企业名称
	var companyName = jt._("#companyName").value;
	//查询事项清单名称
	var itemName = jt._("#itemName").value;
	//请求路径
	var data = "{\"companyName\":\""+companyName+"\",\"itemName\":\""+itemName+"\",\"catalogId\":\""+catalogId+"\"}";
	var sURL = "{contextPath}/statistics/item/queryItemList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

/**
 * 查询方法
 */
function queryData(){
	var catalogId = jt._("#catalogId").value;
    //获取企业名称
    var companyName = jt._("#companyName").value;
	//查询事项清单名称
	var itemName = jt._("#itemName").value;
	//请求路径
	var data = "{\"companyName\":\""+companyName+"\",\"itemName\":\""+itemName+"\",\"catalogId\":\""+catalogId+"\"}";
	var sURL = "{contextPath}/statistics/item/queryItemList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

