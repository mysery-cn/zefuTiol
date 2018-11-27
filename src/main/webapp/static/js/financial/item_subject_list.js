/**
 * 加载大额度资金运作二级事项目录
 */
function loadingCatalogLevel(){
	var catalogCode = jt.getParam('catalogCode');
	var dataURL = "{contextPath}/catalog/queryCatalogFLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				if (json.data[i].CATALOG_CODE==catalogCode) {
					jt._("#catalogCode").value = catalogCode;
					$("#typeName").html(json.data[i].CATALOG_NAME+'-企业议题列表');
					$("#treeDiv a").removeClass();
		        	$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;' title='"+json.data[i].CATALOG_NAME+"' onclick=showView(this,'"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				} else {
		        	$("#treeDiv").append("<div class='industry_contents'> <a style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;' title='"+json.data[i].CATALOG_NAME+"' onclick=showView(this,'"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				}
	        }
		}
	},false);
}

function showView(obj,id,name){
	$("#treeDiv a").removeClass();
	$(obj).addClass("industry_contents_cur");
	resetSearch();
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
	var levelCode = jt._("#levelCode").value;
	var companyId = jt._("#companyId").value;
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (flag && jt._("#times").value != '') {
		if (startTime == "" || endTime == "") {
			return jt.Msg.showMsg("查询起止时间不可为空!");
		}
	}
	//企业名称
	var subjectName = jt._("#subjectName").value;
	var subjectResult = jt._("#subjectResult").value;
	var data = "{\"subjectName\":\""+subjectName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/subject/querySubjectByPage.action?currentPage={page}&pageSize={pageSize}&catalogCode="+id+"&levelCode="+levelCode+"&companyId="+companyId+"&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	getSubjectAllCount(id)
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
	var levelCode = jt.getParam("levelCode");
	var companyId = jt.getParam('companyId');
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	var catalogCode = jt.getParam('catalogCode');
	if (id == "") {
		catalogCode="";
	} else {
		if (id != "" && id != undefined) {
			catalogCode = id;
		}
	}
	var subjectName = jt._("#subjectName").value;
	var subjectResult = jt._("#subjectResult").value;
	var data = "{\"subjectName\":\""+subjectName+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var dataURL = "{contextPath}/subject/getSubjectAllCount.action?companyId="+companyId+"&levelCode="+levelCode+"&catalogCode="+catalogCode+"&parameter="+encodeURIComponent(data);
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