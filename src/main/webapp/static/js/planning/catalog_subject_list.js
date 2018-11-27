/**
 * 加载一级事项目录
 */
function loadingCatalogLevel(){
	var dataURL = "{contextPath}/catalog/queryCatalogLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
	        	$("#treeDiv").append("<div class='industry_contents'> <a style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;' title='"+json.data[i].CATALOG_NAME+"' onclick=showView(this,'"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
	        }
		}
	},false);
}

function showView(obj,id,name){
	$("#treeDiv a").removeClass();
	$(obj).addClass("industry_contents_cur");
	resetSearch();
	if (id.length > 0) {
		id = id.substring(0,1)
	}
	jt._("#catalogCode").value=id;
	var html = name;
	if (name != '') {
		html = name + '-';
	}
	html = html+'企业议题列表';
	$("#typeName").html(html);
	queryData(id);
}

function itemView(id,mId,cId) {
	var href="/subject/subjectDetail.action?subjectId="+id+"&meetingId="+mId+"&companyId="+cId;
	window.parent.addpage("议题详情",href,id+mId+cId);
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
	id = jt._("#catalogCode").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	var subjectName = jt._("#subjectName").value;
	var viaFlag = jt._("#viaFlag").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (flag && jt._("#times").value != '') {
		if (startTime == "" || endTime == "") {
			return jt.Msg.showMsg("查询起止时间不可为空!");
		}
	}
	var subjectResult = jt._("#subjectResult").value;
	var data = "{\"companyName\":\""+companyName+"\",\"subjectName\":\""+subjectName+"\",\"viaFlag\":\""+viaFlag+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/subject/queryMeetingSubjectListByPage.action?currentPage={page}&pageSize={pageSize}&catalogCode="+id+"&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	getAutoCount(id);
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
	jt._("#subjectName").value='';
	jt._("#viaFlag").value='';
	if (jt._("#times").value != '0') {
		jt._("#times").value='';
	}
	if (jt._("#times").value == '0') {
		$("td[name='timeDiv']").hide();
		jt._("#times").value='';
	}
	jt._("#startTime").value='';
	jt._("#endTime").value='';
	jt._("#subjectResult").value='';
}

function getAutoCount(id){
	var catalogCode = jt._("#catalogCode").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	var subjectName = jt._("#subjectName").value;
	var viaFlag = jt._("#viaFlag").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (id != "" && id != undefined) {
		catalogCode = id;
	}
	var subjectResult = jt._("#subjectResult").value;
	var data = "{\"companyName\":\""+companyName+"\",\"subjectName\":\""+subjectName+"\",\"viaFlag\":\""+viaFlag+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var dataURL = "{contextPath}/statistics/subject/getMeetingSubjectAutoCount.action?catalogCode="+catalogCode+"&parameter="+encodeURIComponent(data);
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