/**
 * 通过企业id获取企业名称
 */
function getCompanyNameById(){
	//获取页面跳转传参的单位ID
	var companyID = jt.getParam('companyId');
	if(companyID == null || companyID == "" || companyID == "undefind"){
		companyID = jt._("#companyId").value;
	}
	//请求路径
	var dataURL = "{contextPath}/statistics/company/queryCompanyDetail.action?companyID="+companyID;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			if (json.data[0] != '') {
				jt._("#companyId").value=json.data[0].COMPANY_ID;
				$("#companyName").html(json.data[0].COMPANY_NAME);
			} else {
				return jt.Msg.showMsg("未查询到企业信息！")
			}
		}
	},false);
}

/**
 * 加载会议类型列表
 */
function loadingMeetingType(){
	var typeId = jt.getParam('typeId');
	if(typeId == null || typeId == "" || typeId == "undefind"){
		typeId = jt._("#typeId").value;
	}
	var dataURL = "{contextPath}/meetingType/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				if (json.data[i].TYPE_ID==typeId) {
					jt._("#typeId").value=typeId;
					$("#typeName").html(json.data[i].TYPE_NAME+'-企业会议列表');
					$("#treeDiv a").removeClass();
					$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' onclick=showView(this,'"+json.data[i].TYPE_ID+"','"+json.data[i].TYPE_NAME+"');>"+json.data[i].TYPE_NAME+"</a></div>");
				} else {
		        	$("#treeDiv").append("<div class='industry_contents'> <a onclick=showView(this,'"+json.data[i].TYPE_ID+"','"+json.data[i].TYPE_NAME+"');>"+json.data[i].TYPE_NAME+"</a></div>");
				}
	        }

		}
	},false);
}

function itemView(id) {
	var companyId = jt._("#companyId").value;
    var href = "/meeting/meetingDetail.action?meetingId="+id+"&companyId="+companyId;
    window.parent.addpage("会议详情",href,'meetingDetail'+id);
}

/**
 * 通过会议类型获取会议汇总信息
 * @param obj
 * @param id
 */
function showView(obj,id,name){
	$("#treeDiv a").removeClass();
	$(obj).addClass("industry_contents_cur");
	resetSearch();
	jt._("#typeId").value=id;
	var html = name;
	if (name != '') {
		html = name + '-';
	}
	html = html+'企业会议列表';
	$("#typeName").html(html);
	queryData(id);
}

/**
 * 列表搜索
 */
function queryData(id){
	var flag = true;
	if (id==undefined) {
		id = '';
	} else {
		flag = false;
	}
	id = jt._("#typeId").value;
	var companyId = jt._("#companyId").value;
	var catalogId = jt._("#catalogId").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (flag && jt._("#times").value != '') {
		if (startTime == "" || endTime == "") {
			return jt.Msg.showMsg("查询起止时间不可为空!");
		}
	}
	
	//企业名称
	var meetingName = jt._("#meetingName").value;
	var data = "{\"meetingName\":\""+meetingName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
	//请求路径
	var sURL = "{contextPath}/meeting/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}&companyId="+companyId+"&catalogId="+catalogId+"&typeId="+id+"&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	getMeetingAllCount(id);
}

/**
 * 显示查询条件
 */
function showSearch(){
	$("#hideSearch").css('display','block');
	$("#searchDetail").css('display','block');
	$("#showSearch").css('display','none');
	autodivheight();
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
	jt._("#meetingName").value='';
	if (jt._("#times").value != '0') {
		jt._("#times").value='';
	}
	if (jt._("#times").value == '0') {
		$("td[name='timeDiv']").hide();
		jt._("#times").value='';
	}
	jt._("#startTime").value='';
	jt._("#endTime").value='';
}

function getMeetingAllCount(id){
	var companyId = jt._("#companyId").value;
	var catalogId = jt._("#catalogId").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	var typeId = jt.getParam('typeId');
	if (id == "") {
		typeId = "";
	} else {
		if (id != "" && id != undefined) {
			typeId = id;
		}
	}
	var meetingName = jt._("#meetingName").value;
	var data = "{\"meetingName\":\""+meetingName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
	//请求路径
	var dataURL = "{contextPath}/meeting/getMeetingAllCount.action?companyId="+companyId+"&catalogId="+catalogId+"&typeId="+typeId+"&parameter="+encodeURIComponent(data);
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