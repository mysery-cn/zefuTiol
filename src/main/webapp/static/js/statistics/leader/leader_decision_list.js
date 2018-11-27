/**
 * 页面初始化
 */
jQuery(function($) {
	
	
});

/**
 * 查询方法
 */
function queryData(){
	//行业ID
	var industryId =$('#industryId').val();
	//企业名称
	var companyName = jt._("#companyName").value;
	//请求参数
	var data = "{\"companyName\":\""+companyName+"\",\"industryId\":\""+industryId+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/decision/queryCompanyDecisionList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}
/**
 * 查询企业议题列表
 */
function viewDecisionSubject(type,id,companyId,companyName){
	var industryId =$('#industryId').val();
	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"industryId\":\""+industryId+"\",\"companyId\":\""+companyId+"\",\"companyName\":\""+companyName+"\"}";
	var href = "/tiol/url/goLeaderDecisionSubject.action?parameter="+encodeURIComponent(data);
	window.parent.addpage("议题列表",href,'decisionSubjectList'+type+id+companyId);
}
/**
 * 重置
 */
function resetAll(){
	$("#companyName").val("");
}

/**
 * 查询企业会议列表
 */
function viewDecisionMeeting(type,id,companyId,companyName){
	var industryId =$('#industryId').val();
	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"industryId\":\""+industryId+"\",\"companyId\":\""+companyId+"\",\"companyName\":\""+companyName+"\"}";
	var href = "/tiol/url/goLeaderDecisionMeeting.action?parameter="+encodeURIComponent(data);
	window.parent.addpage("会议列表",href,'decisionMeetingList'+type+id+companyId);
}

/**
 * 查询企业列表
 */
function viewDecisionCompany(type,id){
	var industryId =$('#industryId').val();
	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"industryId\":\""+industryId+"\"}";
	var href = "/tiol/url/goLeaderDecisionCompany.action?parameter="+encodeURIComponent(data);
	window.parent.addpage("统计列表",href,'decisionCompanyList'+type+id);
}
/**
 * 格式化列
 */
function fmtCol(type,id,value,companyId,companyName){
	var html="<a style='color:blue' href=\"javascript:viewDecisionSubject('"+type+"','"+id+"','"+companyId+"','"+companyName+"');\">"+value+"</a>";
	return html;
}


