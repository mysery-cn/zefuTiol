/**
 * 页面初始化
 */
jQuery(function($) {
	loadingMeetingType();
	loadCountNum();
	
});
/**
 * 查询前置议题数与总数
 */
function loadCountNum(){
	var dataURL = "{contextPath}/subject/getDangjianSubjectCount.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			$('#totalNum').html(json.data[0].totalNum);
			$('#exceptionNum').html(json.data[0].exceptionNum);
		}
	},false);
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
 * 重置
 */
function resetAll(){
	$('#companyName').val("");
	$('#startTime').val("");
	$('#endTime').val("");
	$('#meetingType').val("");
	$('#preFlag').val("");
	$("#times").val("");
	$("#search tr td[name='timeDiv']").hide();
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
 * 查看议题详情
 */
function viewSubjectDetail(subjectId,meetingId,companyId){
	var href = "/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
	window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
}
/**
 * 议题前置异常确认
 */
function confirmException(id){
	jtConfirm("是否确认前置异常？",function(r){
		if(!r) return;
		var sURL= contextPath+'/subjectException/editSubjectException.action?';
		postJSON(sURL, {'exceptionId':id,'confirmFlag':1},function (json,o){
			if (json.errorCode==0){
				_('#tabMain_List').loadData();
			}else{
				showMsg(json.errorInfo,'操作失败');
			}
		});
	});
	
}
/**
 * 议题前置异常否决
 */
function refuseException(id){
	jtConfirm("是否确认前置正常？",function(r){
		if(!r) return;
		var sURL= contextPath+'/subjectException/deleteSubjectException.action?';
		postJSON(sURL, {'exceptionId':id},function (json,o){
			if (json.errorCode==0){
				_('#tabMain_List').loadData();
			}else{
				showMsg(json.errorInfo,'操作失败');
			}
		});
	});
}
/**
 * 查询方法
 */
function queryData(){
	var companyName=$('#companyName').val();
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	var meetingType=$("#meetingType option:selected").val();
	var preFlag=$("#preFlag option:selected").val();
	//请求参数
	var data = "{\"companyName\":\""+companyName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"meetingType\":\""+meetingType+"\",\"preFlag\":\""+preFlag+"\"}";
	//请求路径
	var sURL = "{contextPath}/subject/queryDangjianSubjectList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

/**
 * 查询全部数据
 */
function queryAllData(){
	//请求路径
		var sURL = "{contextPath}/subject/queryDangjianSubjectList.action?currentPage={page}&pageSize={pageSize}";
		jt.setAttr(_('#tabMain_List'),'URLData',sURL);
		_('#tabMain_List').loadData();
}

/**
 * 查询未前置数据
 */
function queryExceptionData(){
	//请求参数
		var data = "{\"preFlag\":\"1\"}";
		//请求路径
		var sURL = "{contextPath}/subject/queryDangjianSubjectList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#tabMain_List'),'URLData',sURL);
		_('#tabMain_List').loadData();
}


