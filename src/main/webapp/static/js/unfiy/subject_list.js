/**
 * 生成一级事项目录列表
 */
function loadingCatalogType(){
	var dataURL = "{contextPath}/catalog/queryCatalogLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				$("#levelCode").append("<option value='"+json.data[i].CATALOG_CODE+"'>"+json.data[i].CATALOG_NAME+"</option>");
	        }
		}
	},false);
}

function itemView(id,mId,cId) {
	window.location.href="/subject/subjectDetail.action?subjectId="+id+"&meetingId="+mId+"&companyId="+cId;
}

/**
 * 列表搜索
 */
function queryData(){
	var id = jt._("#levelCode").value;
	var companyName = jt._("#companyName").value;
	var meetingName = jt._("#meetingName").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	var data = "{\"levelCode\":\""+id+"\",\"companyName\":\""+companyName+"\",\"meetingName\":\""+meetingName+"\",\"subjectName\":\""+subjectName+"\"}";
	//请求路径
	var sURL = "{contextPath}/tiol/unify/querySubjectByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
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
	jt._("#subjectName").value='';
	jt._("#levelCode").value='';
}