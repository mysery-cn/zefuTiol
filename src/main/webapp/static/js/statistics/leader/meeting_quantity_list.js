/**
 * 加载事项类型列表
 */
function loadingSource(){
	var dataURL = "{contextPath}/source/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
	        	$("#treeDiv").append("<div class='industry_contents'> <a onclick=showView(this,'"+json.data[i].SOURCE_ID+"');>"+json.data[i].SOURCE_NAME+"</a></div>");
	        }
		}
	},false);
}

function showView(obj,id){
	$("#treeDiv a").removeClass();
	$(obj).addClass("industry_contents_cur");
	resetSearch();
	jt._("#sourceId").value=id; 
	queryData(id);
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
//	id = jt._("#sourceId").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	var data = "{\"companyName\":\""+companyName+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/meeting/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}&sourceId="+id+"&parameter="+encodeURIComponent(data);
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