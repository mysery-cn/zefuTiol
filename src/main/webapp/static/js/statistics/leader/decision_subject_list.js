/**
 * 页面初始化
 */
jQuery(function($) {
	var statisticsType = $("#statisticsType").val();
	var objectId = $("#objectId").val();
	var companyId = $("#companyId").val();
	if( companyId !=""){
		$('#search td[name="companySearch"]').hide();
	}
	var industryId = $("#industryId").val();
	//请求参数
  	var data = "{\"statisticsType\":\""+statisticsType+"\",\"objectId\":\""+objectId+"\",\"companyId\":\""+companyId+"\",\"industryId\":\""+industryId+"\"}";
  	queryAllCount(data);
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
					$('#tableTitle').html(json.data[0].nameArray[i]+" — 议题列表");
					$("#treeDiv").append("<div class='industry_contents' ><a herf='javascript:void(0);' class='industry_contents_cur' onclick='reloadSubjectData(\""+json.data[0].nameArray[i]+"\",\""+json.data[0].idArray[i]+"\",\""+json.data[0].typeArray[i]+"\",this);'>"+json.data[0].nameArray[i]+"</a></div>");
				}else{
					$("#treeDiv").append("<div class='industry_contents' ><a herf='javascript:void(0);' onclick='reloadSubjectData(\""+json.data[0].nameArray[i]+"\",\""+json.data[0].idArray[i]+"\",\""+json.data[0].typeArray[i]+"\",this);'>"+json.data[0].nameArray[i]+"</a></div>");
				}
	        	
	        }
		}
	},false);
}

/**
 * 重新加载列表
 */
function reloadSubjectData(name,id,type,obj){
	$("#statisticsType").val(type);
	$("#objectId").val(id);
	if(name != null && name !=""){
		$('#tableTitle').html(name+" — 议题列表");
	}else{
		$('#tableTitle').html("议题列表");
	}
	var companyId = jt._("#companyId").value;
	var industryId = jt._("#industryId").value;
	resetAll();
	$("#treeDiv div a").removeClass("industry_contents_cur");
    obj.className="industry_contents_cur";
    //请求参数
  	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"companyId\":\""+companyId+"\",\"industryId\":\""+industryId+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/decision/queryDecisionSubjectList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	queryAllCount(data);
}

/**
 * 查询方法
 */
function queryData(){
	//议题名称
	var subjectName = jt._("#subjectName").value;
    //决议内容
    var subjectResult = jt._("#subjectResult").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	//企业ID
	var companyId = jt._("#companyId").value;
	//统计类型
	var statisticsType = jt._("#statisticsType").value;
	//对象ID
	var objectId = jt._("#objectId").value;
	//会议名称
	var meetingName = jt._("#meetingName").value;
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	//请求参数
	var data = "{\"companyName\":\""+companyName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"meetingName\":\""+meetingName+"\",\"subjectName\":\""+subjectName+"\",\"companyId\":\""+companyId+"\",\"statisticsType\":\""+statisticsType+"\",\"objectId\":\""+objectId+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/decision/queryDecisionSubjectList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	queryAllCount(data);
}
/**
 * 查询可查看数量
 */
function queryAllCount(data){
	//请求路径
	var dataURL = "{contextPath}/statistics/decision/getDecisionSubjectCount.action?parameter="+encodeURIComponent(data);
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			$("#all").html(json.data[0].all);
			$("#have").html(json.data[0].have);
			$("#not").html(json.data[0].not);
		}
	},false);
}
/**
 * 重置
 */
function resetAll(){
	$("#companyName").val("");
	$("#subjectName").val("");
	$("#meetingName").val("");
	$("#times").val("");
	$("#startTime").val("");
	$("#subjectResult").val("");
    $("#endTime").val("");
	$("#search tr td[name='timeDiv']").hide();
}
/**
 * 查看公司议题列表
 */
function viewSubjectList(companyId,companyName){
	var industryId =$('#industryId').val();
	var data = "{\"industryId\":\""+industryId+"\",\"companyId\":\""+companyId+"\"}";
	var href = "/tiol/url/goLeaderDecisionMeeting.action?parameter="+encodeURIComponent(data);
	window.parent.addpage(companyName+"企业会议列表",href,'decisionSubjectList'+companyId);
}
/**
 * 查看议题详情
 */
function viewSubjectDetail(subjectId,meetingId,companyId){
    var href = "/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
  	window.parent.addpage("议题详情",href,'subjectDetail' + subjectId);
}

/**
 * 查看会议详情
 */
function viewMeetingDetail(meetingId,companyId){
	var href = "/meeting/meetingDetail.action?meetingId="+meetingId+"&companyId="+companyId;
	window.parent.addpage("会议详情",href,'meetingDetail'+meetingId);
}
/**
 * 格式化公司列
 */
function fmtCompanyCol(companyId,companyName){
	var html="<a style='color:blue' href=\"javascript:reloadList('"+companyId+"','"+companyName+"');\">"+companyName+"</a>";
	return html;
}
/**
 * 打开公司列表页
 */
function reloadList(companyId,companyName){
	var industryId =$('#industryId').val();
	var data = "{\"industryId\":\""+industryId+"\",\"companyId\":\""+companyId+"\"}";
	var href = "/tiol/url/goLeaderDecisionSubject.action?parameter="+encodeURIComponent(data);
	window.parent.addpage("企业议题列表",href,'decisionSubjectList'+companyId);
}

