/**
 * 表格企业跳转
 */
function redirectLeaderView(companyId){
	var href = "/jsp/statistics/leader/leader_inside_list.jsp?companyId="+companyId;
	window.parent.addpage("企业详情",href,'leaderInsideList'+companyId);
}
/**
 * 表格企业跳转
 */
function redirectComapnyView(companyId){
//	var href = "/tiol/url/goCompanyDetail.action?companyId="+companyId;
	var href = "/jsp/statistics/leader/leader_inside_list.jsp?companyId="+companyId;
	window.parent.addpage("企业详情",href,'companyDetail'+companyId);
}

/**
 * 表格制度跳转
 */
function redirectRegulationView(companyId,companyName){
	var href = "/leader/regulation/leaderRegulationDetail.action?companyId="+companyId+"&companyName="+companyName;
    window.parent.addpage("制度清单列表",href,'regulationView' + companyId);
}

/**
 * 表格会议跳转
 */
function redirectMeetingView(companyId) {
	var href = "/tiol/url//goMeetingList.action?companyId=" + companyId;
	window.parent.addpage("企业会议列表",href,'meetingList'+companyId);
}

/**
 * 表格事项跳转
 */
function redirectItemView(companyID){
	var href = "/tiol/url/goLeaderItemDetail.action?companyId="+companyID;
	window.parent.addpage("事项清单列表",href,'itemDetail'+companyID);
}

/**
 * 表格事项跳转
 */
function redirectSubjectView(companyId){
    var href = "/tiol/url/goLeaderCompanySubject.action?companyId="+companyId;
	window.parent.addpage("企业议题列表",href,'subjectDetail'+companyId);
}

/**
 * 搜索框重置
 */
function reset()
{
	$("#reset").val('');
}

$('#companyName').keyup(function(){
	var companyName = jt._("#companyName").value;
	if(companyName.length>0){
		$("#danger").hide()
	}
})
/**
 * 显示隐藏筛选
 */
function showHideSearch(){
	if($('#search').is(':hidden')){
	       $('#showHide').html("隐藏筛选");
           $('#search').show();
           $('#showHide').addClass('backgroundimg2') .removeClass('backgroundimg1');
    }else{
    	   $('#showHide').html("显示筛选");
           $('#search').hide();
           $('#showHide').addClass('backgroundimg1') .removeClass('backgroundimg2');
   }
}

/**
 * 在线浏览文件
 */
function showFileView(fileId){
	if(curUser.orgLevel == 0){
		NavGoURL('{SYSURL_oa}/common/show.jsp?fileId='+fileId);
	}else{
		var href = "/common/show.jsp?fileId="+fileId;
		window.parent.addpage("附件详情",href,'fileDetail');
	}
}

/**
 * 重置
 */
function resetSearchValue(){
	$("#meetingName").val("");
	$("#companyName").val("");
	$("#subjectName").val("");
	$("#passFlag").val("");
	$("#itemId").val("");
	$("#itemName").val("");
	$("#regulationName").val("");
	$("#approvalFlag").val("");
	$("#meetingType").val("");
    $("#times").val("");
	$("#startDate").val("");
	$("#endDate").val("");
    $("#startTime").val("");
    $("#endTime").val("");
    $("#subjectResult").val("");
    $("td[name='timeDiv']").hide();
}

/**
 * 回车查询事件
 */
document.onkeydown = function(e){        
	var ev = document.all ? window.event : e;
	if(ev.keyCode==13) {
		//查询事件名称固定
		queryData();
	}
}

//加载时间查询区间内容
addTimeItem();

//添加时间区间内容
function addTimeItem(){
	$("#times").append("<option value='"+1+"'>近一周</option>");
	$("#times").append("<option value='"+2+"'>近半个月</option>");
	$("#times").append("<option value='"+3+"'>近一个月</option>");
	$("#times").append("<option value='"+4+"'>近三个月</option>");
	$("#times").append("<option value='"+5+"'>近半年</option>");
	$("#times").append("<option value='"+6+"'>近一年</option>");
	$("#times").append("<option value='"+7+"'>近两年</option>");
	$("#times").append("<option value='"+0+"'>其它</option>");
}

//时间查询区间点击事件
function clickTime(){
	var val = $("#times").val();
	if (val == "0") {
		$("td[name='timeDiv']").show();
	} else if (val == "1") {
		$("td[name='timeDiv']").hide();
		$("#startTime").val(getWeek());
		$("#endTime").val(getDate());
	} else if (val == "2") {
		$("td[name='timeDiv']").hide();
		$("#startTime").val(getHalfMonth());
		$("#endTime").val(getDate());
	} else if (val == "3") {
		$("td[name='timeDiv']").hide();
		$("#startTime").val(getMonth());
		$("#endTime").val(getDate());
	} else if (val == "4") {
		$("td[name='timeDiv']").hide();
		$("#startTime").val(getQuarter());
		$("#endTime").val(getDate());
	} else if (val == "5") {
		$("td[name='timeDiv']").hide();
		$("#startTime").val(getHalfYear());
		$("#endTime").val(getDate());
	} else if (val == "6") {
		$("td[name='timeDiv']").hide();
		$("#startTime").val(getYear());
		$("#endTime").val(getDate());
	} else if (val == "7") {
		$("td[name='timeDiv']").hide();
		$("#startTime").val(getTwoYear());
		$("#endTime").val(getDate());
	} else {
		$("td[name='timeDiv']").hide();
		$("#startTime").val('');
		$("#endTime").val('');
	}
}

//当前时间日期
function getDate(){
	var d = new Date();
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

//当前时间一周前的日期
function getWeek(){
	var d = new Date();
	d.setDate(d.getDate() - 7);
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

//当前时间半个月前的日期
function getHalfMonth(){
	var d = new Date();
	d.setDate(d.getDate() - 15);
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

//当前时间一个月前的日期
function getMonth(){
	var d = new Date();
	d.setMonth(d.getMonth() - 1);
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

//当前时间一个季度前的日期
function getQuarter(){
	var d = new Date();
	d.setMonth(d.getMonth() - 3);
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

//当前时间半年前的日期
function getHalfYear(){
	var d = new Date();
	d.setMonth(d.getMonth() - 6);
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

//当前时间一年前的日期
function getYear(){
	var d = new Date();
	d.setFullYear(d.getFullYear() - 1);
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

//当前时间两年前的日期
function getTwoYear(){
	var d = new Date();
	d.setFullYear(d.getFullYear() - 2);
	var yy1 = d.getFullYear();
	var mm1 = d.getMonth()+1;
	var dd1 = d.getDate();
	if (mm1 < 10 ) {
		mm1 = '0'+ mm1;
	}
	if (dd1 < 10) {
	  dd1 = '0' + dd1;
	}
	var str = yy1 + '-' + mm1 + '-' + dd1;
	return str;
}

/**
 * 处理IE8下输入框无法编辑问题
 */
function dealInputBug(id){
	$("#" + id).focus();
}

/**
 * 同步数据
 */
function synchronous(fileName){
    var dataURL = "/Generate/Xml/parsingXmlToCompany.action";
    var jPost= {};
    jPost.fileName = fileName;
    postJSON(dataURL,jPost,function(json,o){
        if (!json || json.errorCode != "0") {
            return jt.Msg.showMsg("数据同步失败！");
        } else {
            return jt.Msg.showMsg("数据同步成功！");
        }
    },false);
}

//判断字符是否为空的方法
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}