/**
 * 加载事项目录
 */
function loadingCatalogType(){
//	var dataURL = "{contextPath}/meetingType/queryList.action";
//	var jPost= {};
//	postJSON(dataURL,jPost,function(json,o){
//		if (!json || json.errorCode != "0") {
//			return jt.Msg.showMsg("获取数据失败！");
//		} else {
//			for(var i=0;i<json.data.length;i++){
//	        	$("#treeDiv").append("<div class='industry_contents'> <a id='"+json.data[i].TYPE_ID+"' onclick=reloadSubjectData('"+json.data[i].TYPE_ID+"');>"+json.data[i].TYPE_NAME+"</a></div>");
//	        }
//		}
//	},false);
	var dataURL = "{contextPath}/catalog/queryCatalogLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i < json.data.length;i++){
	        	$("#treeDiv").append("<div class='industry_contents'> <a id='"+json.data[i].CATALOG_CODE+"' onclick=reloadSubjectData('"+json.data[i].CATALOG_CODE+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
	        }
		}
	},false);
}

/**
 * 重新加载数据
 */
function reloadSubjectData(catalogCode){
	//设置选中
	$("#treeDiv a").removeClass();
	if(catalogCode == null || catalogCode == "" || catalogCode == "undefind"){
		catalogCode = "";
		document.getElementById("all").className += 'industry_contents_cur';//追加一个class
	}else{
		document.getElementById(catalogCode).className += 'industry_contents_cur';//追加一个class
	}
	$("#catalogCode").val(catalogCode);
	//获取页面跳转传参的单位ID
	var companyID = jt._("#companyId").value;
	//查询类型
	var search = jt._("#search").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	//查询时间
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (jt._("#times").value != '') {
		if (startTime == "" || endTime == "") {
			return jt.Msg.showMsg("查询起止时间不可为空!");
		}
	}
    //会议决议情况
    var subjectResult = jt._("#subjectResult").value;
    //请求路径
    var data = "{\"subjectName\":\""+subjectName+"\",\"itemId\":\""+catalogCode+"\",\"companyID\":\""+companyID+"\",\"search\":\""+search+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
    //请求路径
	var sURL = "{contextPath}/statistics/subject/queryMeetingTypeSubjectListByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
    //计算数量
    loadUserRoleMessage();
}

/**
 * 查询数据
 */
function queryData(){
	//获取页面跳转传参的单位ID
	var companyID = jt._("#companyId").value;
	//查询类型
	var search = jt._("#search").value;
	//事项ID
	var itemId = jt._("#catalogCode").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	//查询时间
	var startTime = jt._("#startTime").value;
	var endTime = jt._("#endTime").value;
	if (jt._("#times").value != '') {
		if (startTime == "" || endTime == "") {
			return jt.Msg.showMsg("查询起止时间不可为空!");
		}
	}
    //会议决议情况
    var subjectResult = jt._("#subjectResult").value;
	//请求路径
	var data = "{\"subjectName\":\""+subjectName+"\",\"itemId\":\""+itemId+"\",\"companyID\":\""+companyID+"\",\"search\":\""+search+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/subject/queryMeetingTypeSubjectListByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
	//计算数量
    loadUserRoleMessage();
}

/**
 * 查询企业名称
 */
function queryCompanyName(){
	//获取页面跳转传参的单位ID
	var companyID = jt._("#companyId").value;
	//请求路径
	var dataURL = "{contextPath}/statistics/company/queryCompanyDetail.action?companyID="+companyID;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			$("#companyName").val(json.data[0].COMPANY_NAME);
		}
	},false);
}

/**
 * 查询议题详情
 */
function subjectDetail(subjectId,meetingId,companyId){
	var href = "/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
	window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
}

/**
 * 加载权限查看数量
 */
function loadUserRoleMessage(){
	//获取页面跳转传参的单位ID
    var companyID = jt._("#companyId").value;
    //查询类型
    var search = jt._("#search").value;
    //事项ID
    var itemId = jt._("#catalogCode").value;
    //议题名称
    var subjectName = jt._("#subjectName").value;
    //查询时间
    var startTime = jt._("#startTime").value;
    var endTime = jt._("#endTime").value;
    if (jt._("#times").value != '') {
        if (startTime == "" || endTime == "") {
            return jt.Msg.showMsg("查询起止时间不可为空!");
        }
    }
    //会议决议情况
    var subjectResult = jt._("#subjectResult").value;
    //请求路径
    var dataURL = "{contextPath}/statistics/subject/querySubjectRoleNumber.action";
    var jPost= {};
    jPost.startTime = startTime;
    jPost.endTime = endTime;
    jPost.subjectName = subjectName;
    jPost.itemId = itemId;
    jPost.search = search;
    jPost.companyID = companyID;
    jPost.subjectResult = subjectResult;
    postJSON(dataURL,jPost,function(json,o){
        if (!json || json.errorCode != "0") {
            return jt.Msg.showMsg("获取数据失败！");
        } else {
            document.getElementById("total").innerText = json.data[0].total;
            document.getElementById("yNumber").innerText= json.data[0].number;
            document.getElementById("nNumber").innerText= parseInt(json.data[0].total) - parseInt(json.data[0].number);
        }
    },false);
}




