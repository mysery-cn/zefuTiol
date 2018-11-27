/**
 * 加载会议类型列表
 */
function loadingMeetingType(){
	var dataURL = "{contextPath}/meetingType/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				$("#typeId").append("<option value='"+json.data[i].TYPE_ID+"'>"+json.data[i].TYPE_NAME+"</option>");
	        }
		}
	},false);
}

function itemView(id, code) {
	window.location.href="/meeting/meetingDetail.action?meetingId="+id+"&companyId="+code;
}

/**
 * 列表搜索
 */
function queryData(){
	var typeId = jt._("#typeId").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	var meetingName = jt._("#meetingName").value;
	var data = "{\"typeId\":\""+typeId+"\",\"meetingName\":\""+meetingName+"\",\"companyName\":\""+companyName+"\"}";
	//请求路径
	var sURL = "{contextPath}/tiol/unify/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
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
	jt._("#meetingName").value='';
	jt._("#typeId").value='';
}