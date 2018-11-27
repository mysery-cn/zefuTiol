/**
 * 页面初始化
 */
jQuery(function($) {
	var statisticsType = $("#statisticsType").val();
	var objectId = $("#objectId").val();
	loadingDecision(objectId,statisticsType);
	$('#treeDiv').height($('.list_all').height());
});
/**
 * 加载类型列表
 */
function loadingDecision(objectId,statisticsType){

	var dataURL = "{contextPath}/statistics/decision/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data[0].nameArray.length;i++){
				if(objectId == json.data[0].idArray[i] && statisticsType == json.data[0].typeArray[i]){
					$("#treeDiv div a").removeClass("industry_contents_cur");
					$('#tableTitle').html(json.data[0].nameArray[i]+" — 统计列表");
					$("#treeDiv").append("<div class='industry_contents' ><a herf='javascript:void(0);' class='industry_contents_cur' onclick='reloadTableData(\""+json.data[0].nameArray[i]+"\",\""+json.data[0].idArray[i]+"\",\""+json.data[0].typeArray[i]+"\",this);'>"+json.data[0].nameArray[i]+"</a></div>");
				}else{
					$("#treeDiv").append("<div class='industry_contents' ><a herf='javascript:void(0);' onclick='reloadTableData(\""+json.data[0].nameArray[i]+"\",\""+json.data[0].idArray[i]+"\",\""+json.data[0].typeArray[i]+"\",this);'>"+json.data[0].nameArray[i]+"</a></div>");
				}
	        	
	        }
		}
	},false);
}

/**
 * 重新加载列表
 */
function reloadTableData(name,id,type,obj){
	$("#statisticsType").val(type);
	$("#objectId").val(id);
	if(name != null && name !=""){
		$('#tableTitle').html(name+" — 统计列表");
	}else{
		$('#tableTitle').html("统计列表");
	}
	var industryId = jt._("#industryId").value;
	$("#treeDiv div a").removeClass("industry_contents_cur");
    obj.className="industry_contents_cur";
    //请求参数
  	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"industryId\":\""+industryId+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/decision/queryDecisionCompanyList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

/**
 * 查询方法
 */
function queryData(){
	//企业名称
	var companyName = jt._("#companyName").value;
	//统计类型
	var statisticsType = jt._("#statisticsType").value;
	//统计对象ID
	var objectId = jt._("#objectId").value;
	//统计对象ID
	var industryId = jt._("#industryId").value;
	//请求参数
	var data = "{\"companyName\":\""+companyName+"\",\"statisticsType\":\""+statisticsType+"\",\"objectId\":\""+objectId+"\",\"industryId\":\""+industryId+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/decision/queryDecisionCompanyList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}
/**
 * 重置
 */
function resetAll(){
	$("#companyName").val("");
}

/**
 * 查看会议列表
 */
function viewMeetingList(companyId,companyName){
	//统计类型
	var type = jt._("#statisticsType").value;
	//统计对象ID
	var id = jt._("#objectId").value;
	//统计对象ID
	var industryId = jt._("#industryId").value;
	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"industryId\":\""+industryId+"\",\"companyName\":\""+companyName+"\",\"companyId\":\""+companyId+"\"}";
	var href = "/tiol/url/goLeaderDecisionMeeting.action?parameter="+encodeURIComponent(data);
	window.parent.addpage("会议列表",href,'decisionMeetingList'+companyId);
}

/**
 * 查看会议列表
 */
function viewSubjectList(companyId,companyName){
	//统计类型
	var type = jt._("#statisticsType").value;
	//统计对象ID
	var id = jt._("#objectId").value;
	//统计对象ID
	var industryId = jt._("#industryId").value;
	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"industryId\":\""+industryId+"\",\"companyName\":\""+companyName+"\",\"companyId\":\""+companyId+"\"}";
	var href = "/tiol/url/goLeaderDecisionSubject.action?parameter="+encodeURIComponent(data);
	window.parent.addpage("议题列表",href,'decisionSubjectList'+companyId);
}