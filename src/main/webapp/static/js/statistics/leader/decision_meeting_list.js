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
	loadingMeetingType();
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
					$('#tableTitle').html(json.data[0].nameArray[i]+" — 会议列表");
					$("#treeDiv").append("<div class='industry_contents' ><a herf='javascript:void(0);' class='industry_contents_cur' onclick='reloadTableData(\""+json.data[0].nameArray[i]+"\",\""+json.data[0].idArray[i]+"\",\""+json.data[0].typeArray[i]+"\",this);'>"+json.data[0].nameArray[i]+"</a></div>");
				}else{
					$("#treeDiv").append("<div class='industry_contents' ><a herf='javascript:void(0);' onclick='reloadTableData(\""+json.data[0].nameArray[i]+"\",\""+json.data[0].idArray[i]+"\",\""+json.data[0].typeArray[i]+"\",this);'>"+json.data[0].nameArray[i]+"</a></div>");
				}
	        	
	        }
		}
	},false);
}
/**
 * 加载会议类型
 */
function loadingMeetingType(){

	var dataURL = "{contextPath}/meetingType/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				$("#meetingType").append("<option value='"+json.data[i].TYPE_ID+"'>"+json.data[i].TYPE_NAME+"</option>");	        	
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
		$('#tableTitle').html(name+" — 会议列表");
	}else{
		$('#tableTitle').html("会议列表");
	}
	var companyId = jt._("#companyId").value;
	var industryId = jt._("#industryId").value;
	resetAll();
	$("#treeDiv div a").removeClass("industry_contents_cur");
    obj.className="industry_contents_cur";
    //请求参数
  	var data = "{\"statisticsType\":\""+type+"\",\"objectId\":\""+id+"\",\"companyId\":\""+companyId+"\",\"industryId\":\""+industryId+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/decision/queryDecisionMeetingList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	queryAllCount(data);
}

/**
 * 查询方法
 */
function queryData(){
	//会议名称
	var meetingName = jt._("#meetingName").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	//企业ID
	var companyId = jt._("#companyId").value;
	//统计类型
	var statisticsType = jt._("#statisticsType").value;
	//通缉对象ID
	var objectId = jt._("#objectId").value;
	//行业ID
	var industryId = jt._("#industryId").value;
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	var meetingType=$("#meetingType option:selected").val();
	//请求参数
	var data = "{\"companyName\":\""+companyName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"meetingType\":\""+meetingType+"\",\"meetingName\":\""+meetingName+"\",\"industryId\":\""+industryId+"\",\"companyId\":\""+companyId+"\",\"statisticsType\":\""+statisticsType+"\",\"objectId\":\""+objectId+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/decision/queryDecisionMeetingList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	queryAllCount(data);
}
/**
 * 重置
 */
function resetAll(){
	$("#companyName").val("");
	$("#meetingName").val("");
	$("#meetingType").val("");
	$("#times").val("");
	$("#startTime").val("");
	$("#endTime").val("");
	$("#search tr td[name='timeDiv']").hide();
}
/**
 * 查询可查看数量
 */
function queryAllCount(data){
	//请求路径
	var dataURL = "{contextPath}/statistics/decision/getDecisionMeetingCount.action?parameter="+encodeURIComponent(data);
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
 * 查看会议详情
 */
function viewMeetingDetail(companyId,meetingId){
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
	var href = "/tiol/url/goLeaderDecisionMeeting.action?parameter="+encodeURIComponent(data);
	window.parent.addpage(companyName+"企业会议列表",href,'decisionMeetingList'+companyId);
}
