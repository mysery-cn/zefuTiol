/**
 * 通过企业id获取企业名称
 */
function getCompanyNameById(){
	//获取页面跳转传参的单位ID
	var companyID = jt.getParam('companyId');
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
 * 生成一级事项目录列表
 */
function loadingCatalogType(){
	var code = jt.getParam('levelCode');
	var dataURL = "{contextPath}/catalog/queryCatalogLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				if (json.data[i].CATALOG_CODE == code) {
					$("#treeDiv a").removeClass();
					jt._("#levelCode").value=code;
					$("#typeName").html(json.data[i].CATALOG_NAME+'-企业议题列表');
		        	$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' id="+json.data[i].CATALOG_CODE+" onclick=showView(this,'"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				} else {
		        	$("#treeDiv").append("<div class='industry_contents'> <a id="+json.data[i].CATALOG_CODE+" onclick=showView(this,'"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				}
	        }
		}
	},false);
}

function itemView(id,mId,cId) {
	var href="/subject/subjectDetail.action?subjectId="+id+"&meetingId="+mId+"&companyId="+cId;
	window.parent.addpage("议题详情",href,'subjectDetail'+id+mId+cId);
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
	jt._("#levelCode").value=id;
	var html = name;
	if (name != '') {
		html = name + '-';
	}
	html = html+'企业议题列表';
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
	id = jt._("#levelCode").value;
	var companyId = jt._("#companyId").value;
	var catalogId = jt._("#catalogId").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (flag && jt._("#times").value != '') {
		if (startTime == "" || endTime == "") {
			return jt.Msg.showMsg("查询起止时间不可为空!");
		}
	}
	//议题名称
	var subjectName = jt._("#subjectName").value;
	//会议决议情况
	var subjectResult = jt._("#subjectResult").value;
	var data = "{\"subjectName\":\""+subjectName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/subject/querySubjectByPage.action?currentPage={page}&pageSize={pageSize}&companyId="+companyId+"&levelCode="+id+"&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	getSubjectAllCount(id);
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
	jt._("#subjectName").value='';
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

function getSubjectAllCount(id){
	var companyId = jt.getParam('companyId');
	var catalogId = jt.getParam('catalogId');
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	var levelCode = jt.getParam('levelCode');
	if (id == "") {
		levelCode="";
	} else {
		if (id != "" && id != undefined) {
			levelCode = id;
		}
	}
	var subjectName = jt._("#subjectName").value;
    //会议决议情况
    var subjectResult = jt._("#subjectResult").value;
	var data = "{\"subjectName\":\""+subjectName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var dataURL = "{contextPath}/subject/getSubjectAllCount.action?companyId="+companyId+"&catalogId="+catalogId+"&levelCode="+levelCode+"&parameter="+encodeURIComponent(data);
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