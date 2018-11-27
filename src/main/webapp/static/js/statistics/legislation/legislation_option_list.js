/**
 * 页面初始化
 */
jQuery(function($) {
	var optionType = $("#optionType").val();
	$('#treeDiv').height(700);
	$("#treeDiv div a").each(function(index,element){
		if(optionType==(index+1)){
			reloadOptionData(optionType,element);
		}
	});
});

/**
 * 重新加载列表
 */
function reloadOptionData(type,obj){
	$('#tableTitle').html(obj.innerText+" — 列表");
	$("#treeDiv div a").removeClass("industry_contents_cur");
    obj.className="industry_contents_cur";
	$("#optionType").val(type);
	var companyId = $("#companyId").val();
	resetAll();
	var data;
	if(type=='1'){
		var meetingTypes = new Array("党委会","党组会");
	    data = "{\"meetingTypes\":\""+meetingTypes+"\",\"companyId\":\""+companyId+"\",\"optionType\":\""+type+"\"}";
		$('#subject1_table').show();
		$('#subject2_table').hide();
		$('#subject3_table').hide();
		$('#regulation_table').hide();
		$('#viewNum').show();
		$('#timeSearch').css("display","inline");
		$("#search tr td[name='regulationSearch']").hide();
		$("#search tr td[name='subjectSearch']").show();
		$("#search tr td[name='counselSearch']").show();
		$("#search tr td[name='auditSearch']").hide();
		$("#search tr td[name='opinionSearch']").hide();
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject1_List'),'URLData',sURL);
		_('#subject1_List').loadData();
	 }else if(type=='2'){
		 var typeName = "董事会";
	    data = "{\"typeName\":\""+typeName+"\",\"companyId\":\""+companyId+"\",\"optionType\":\""+type+"\"}";
		$('#subject1_table').show();
		$('#subject2_table').hide();
		$('#subject3_table').hide();
		$('#regulation_table').hide();
		$('#viewNum').show();
		$('#timeSearch').css("display","inline");
		$("#search tr td[name='regulationSearch']").hide();
		$("#search tr td[name='subjectSearch']").show();
		$("#search tr td[name='counselSearch']").show();
		$("#search tr td[name='auditSearch']").hide();
		$("#search tr td[name='opinionSearch']").hide();
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject1_List'),'URLData',sURL);
		_('#subject1_List').loadData();
	 }else if(type=='3'){
		data = "{\"companyId\":\""+companyId+"\",\"optionType\":\""+type+"\"}";
		//请求路径
		$('#subject2_table').hide();
		$('#subject1_table').hide();
		$('#subject3_table').hide();
		$('#viewNum').hide();
		$('#timeSearch').css("display","none");
	    $('#regulation_table').show();
	    $("#search tr td[name='regulationSearch']").show();
		$("#search tr td[name='subjectSearch']").hide();
		$("#search tr td[name='counselSearch']").hide();
		$("#search tr td[name='auditSearch']").show();
		$("#search tr td[name='opinionSearch']").hide();
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#regulation_List'),'URLData',sURL);
		_('#regulation_List').loadData();
	}else if(type=='4'){
	    data = "{\"approvalFlag\":\"1\",\"companyId\":\""+companyId+"\",\"optionType\":\""+type+"\"}";
		$('#subject2_table').show();
		$('#subject1_table').hide();
		$('#subject3_table').hide();
		$('#regulation_table').hide();
		$('#viewNum').show();
		$('#timeSearch').css("display","inline");
		$("#search tr td[name='regulationSearch']").hide();
		$("#search tr td[name='subjectSearch']").show();
		$("#search tr td[name='counselSearch']").hide();
		$("#search tr td[name='auditSearch']").hide();
		$("#search tr td[name='opinionSearch']").show();
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject2_List'),'URLData',sURL);
		_('#subject2_List').loadData();
	}else if(type=='5'){
		var catalogName="重大决策";
	    data = "{\"catalogName\":\""+catalogName+"\",\"companyId\":\""+companyId+"\",\"optionType\":\""+type+"\"}";
		$('#subject3_table').show();
		$('#subject2_table').hide();
		$('#subject1_table').hide();
		$('#regulation_table').hide();
		$('#viewNum').show();
		$('#timeSearch').css("display","inline");
		$("#search tr td[name='regulationSearch']").hide();
		$("#search tr td[name='subjectSearch']").show();
		$("#search tr td[name='counselSearch']").hide();
		$("#search tr td[name='auditSearch']").show();
		$("#search tr td[name='opinionSearch']").hide();
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject3_List'),'URLData',sURL);
		_('#subject3_List').loadData();
	}else if(type=='6'){
		var itemName="境外非主业投资项目";
	    data = "{\"itemName\":\""+itemName+"\",\"companyId\":\""+companyId+"\",\"optionType\":\""+type+"\"}";
		$('#subject1_table').show();
		$('#subject2_table').hide();
		$('#subject3_table').hide();
		$('#regulation_table').hide();
		$('#viewNum').show();
		$('#timeSearch').css("display","inline");
		$("#search tr td[name='regulationSearch']").hide();
		$("#search tr td[name='subjectSearch']").show();
		$("#search tr td[name='counselSearch']").show();
		$("#search tr td[name='auditSearch']").hide();
		$("#search tr td[name='opinionSearch']").hide();
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject1_List'),'URLData',sURL);
		_('#subject1_List').loadData();
	}
	queryAllCount(data);
}

/**
 * 查询方法
 */
function queryData(){
	//议题名称
	var subjectName = jt._("#subjectName").value;
	//会议名称
	var meetingName = jt._("#meetingName").value;
	//制度名称
	var regulationName = jt._("#regulationName").value;
	//法律意见书
	var opinionFlag=$("#opinionFlag option:selected").val();
	//法律顾问出席
	var counselFlag=$("#counselFlag option:selected").val();
	//合法合规检查
	var auditFlag=$("#auditFlag option:selected").val();
	//起始时间
	var startTime=$('#startTime').val();
	//结束时间
	var endTime=$('#endTime').val();
	//查询类型
	var type=$('#optionType').val();
	//公司ID
	var companyId = $("#companyId").val();
	var data;
	if(type=='1'){
		var meetingTypes = new Array("党委会","党组会");
	    data = "{\"companyId\":\""+companyId+"\",\"meetingName\":\""+meetingName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectName\":\""+subjectName+"\",\"counselFlag\":\""+counselFlag+"\",\"meetingTypes\":\""+meetingTypes+"\",\"optionType\":\""+type+"\"}";
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject1_List'),'URLData',sURL);
		_('#subject1_List').loadData();
	 }else if(type=='2'){
		 var typeName="董事会";
	    data = "{\"companyId\":\""+companyId+"\",\"meetingName\":\""+meetingName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectName\":\""+subjectName+"\",\"counselFlag\":\""+counselFlag+"\",\"typeName\":\""+typeName+"\",\"optionType\":\""+type+"\"}";
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject1_List'),'URLData',sURL);
		_('#subject1_List').loadData();
	 }else if(type=='3'){
	    data = "{\"companyId\":\""+companyId+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"regulationName\":\""+regulationName+"\",\"auditFlag\":\""+auditFlag+"\",\"optionType\":\""+type+"\"}";
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#regulation_List'),'URLData',sURL);
		_('#regulation_List').loadData();
	}else if(type=='4'){
	    data = "{\"companyId\":\""+companyId+"\",\"meetingName\":\""+meetingName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectName\":\""+subjectName+"\",\"opinionFlag\":\""+opinionFlag+"\",\"approvalFlag\":\"1\",\"optionType\":\""+type+"\"}";
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject2_List'),'URLData',sURL);
		_('#subject2_List').loadData();
	}else if(type=='5'){
		var catalogName="重大决策";
	    data = "{\"companyId\":\""+companyId+"\",\"meetingName\":\""+meetingName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectName\":\""+subjectName+"\",\"auditFlag\":\""+auditFlag+"\",\"catalogName\":\""+catalogName+"\",\"optionType\":\""+type+"\"}";
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject3_List'),'URLData',sURL);
		_('#subject3_List').loadData();
	}else if(type=='6'){
		var itemName="境外非主业投资项目";
	    data = "{\"companyId\":\""+companyId+"\",\"meetingName\":\""+meetingName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectName\":\""+subjectName+"\",\"counselFlag\":\""+counselFlag+"\",\"itemName\":\""+itemName+"\",\"optionType\":\""+type+"\"}";
		//请求路径
		var sURL = "{contextPath}/statistics/legislation/listLegislationOption.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
		jt.setAttr(_('#subject1_List'),'URLData',sURL);
		_('#subject1_List').loadData();
	}
	queryAllCount(data);
}
/**
 * 重置
 */
function resetAll(){
	//议题名称
	$("#subjectName").val("");
	//会议名称
	$("#meetingName").val("");
	//制度名称
	$("#regulationName").val("");
	//法律意见书
	$("#opinionFlag").val("");
	//法律顾问出席
	$("#counselFlag").val("");
	//合法合规检查
	$("#auditFlag").val("");
	//时间选择
	$('#times').val("");
	//起始时间
	$('#startTime').val("");
	//结束时间
	$('#endTime').val("");
	$("#search tr td[name='timeDiv']").hide();
}
/**
 * 查询可查看数量
 */
function queryAllCount(data){
	//请求路径
	var dataURL = "{contextPath}/statistics/legislation/getLegislationOptionCount.action?parameter="+encodeURIComponent(data);
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
 * 查看议题详情
 */
function viewSubjectDetail(subjectId,meetingId,companyId){
	var href="/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
	window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
}
/**
 * 格式化议题合法合规检查列
 */
function fmtLegalStatus(legalFlag,counselNum,opinionNum){
	if(legalFlag=='1'&&(counselNum>0||opinionNum>0)){
		return "是";
	}else{
		return "否";
	}
}
/**
 * 查看制度详情
 */
function viewRegulationDetail(regulationId){
	var href ="/leader/regulation/leaderRegulationComDetail.action?regulationId="+regulationId;
	window.parent.addpage("制度详情",href,'regulationDetail'+regulationId);
}




