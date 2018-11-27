/**
 * 加载事项类型
 */
function loadingCatelog(){
	var dataURL = "{contextPath}/catalog/queryCatalogLevel.action";
	var jPost= {};
	var typeID = $("#typeID").val();
	var companyID = $("#companyID").val();
	if( companyID !=""){
		$('#search td[name="companySearch"]').hide();
	}
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i < json.data.length;i++){
				if(json.data[i].CATALOG_CODE == typeID){
					$("#treeDiv a").removeClass();
					$("#typeName").html(json.data[i].CATALOG_NAME+'-企业议题列表');
					$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' id='"+json.data[i].CATALOG_CODE+"' onclick=reloadSubjectData('"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				}else{
					$("#treeDiv").append("<div class='industry_contents'> <a id='"+json.data[i].CATALOG_CODE+"' onclick=reloadSubjectData('"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				}
	        }
		}
	},false);
}

/**
 * 重新加载数据
 */
function reloadSubjectData(typeID,typeName){
	if(typeID == 1){
		$("#typeID").val("");
		typeID = "";
	}
	//设置选中
	$("#treeDiv a").removeClass();
	if(typeID == null || typeID == "" || typeID == "undefind"){
		document.getElementById("all").className += 'industry_contents_cur';//追加一个class
		typeID = $("#typeID").val();
		$("#typeName").html('企业议题列表');
	}else{
		$("#typeName").html(typeName+'-企业议题列表');
		document.getElementById(typeID).className += 'industry_contents_cur';//追加一个class
		$("#typeID").val(typeID);
	}
	//事项ID
	var passFlag = jt._("#passFlag").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	//企业名称
	var companyName = $("#companyName").val();
	//企业ID
	var companyID = $("#companyID").val();
    //查询时间
    var startTime = jt._("#startTime").value;
    //会议决议情况
    var subjectResult = jt._("#subjectResult").value;
    var endTime = jt._("#endTime").value;
    if (jt._("#times").value != '') {
        if (startTime == "" || endTime == "") {
            return jt.Msg.showMsg("查询起止时间不可为空!");
        }
    }
	//请求路径
    var data = "{\"subjectName\":\""+subjectName+"\",\"passFlag\":\""+passFlag+"\",\"typeID\":\""+typeID+"\",\"companyID\":\""+companyID+"\",\"companyName\":\""+companyName+"\",\"endTime\":\""+endTime+"\",\"startTime\":\""+startTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/model/reform/querySubjectPageList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
    //加载权限查看数量
    loadUserRoleMessage();
}

/**
 * 查询方法
 */
function queryData(){
    //事项ID
	var typeID = $("#typeID").val();
	//议题状态
	var passFlag = jt._("#passFlag").value;
    //会议决议情况
    var subjectResult = jt._("#subjectResult").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	//企业名称
	var companyName = $("#companyName").val();
	//企业ID
	var companyID = $("#companyID").val();
    //查询时间
    var startTime = jt._("#startTime").value;
    var endTime = jt._("#endTime").value;
    if (jt._("#times").value != '') {
        if (startTime == "" || endTime == "") {
            return jt.Msg.showMsg("查询起止时间不可为空!");
        }
    }
	//请求路径
	var data = "{\"subjectName\":\""+subjectName+"\",\"passFlag\":\""+passFlag+"\",\"typeID\":\""+typeID+"\",\"companyID\":\""+companyID+"\",\"companyName\":\""+companyName+"\",\"endTime\":\""+endTime+"\",\"startTime\":\""+startTime+"\",\"subjectResult\":\""+subjectResult+"\"}";
	//请求路径
	var sURL = "{contextPath}/model/reform/querySubjectPageList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
    //加载权限查看数量
    loadUserRoleMessage();
}

//格式状态
function formatStatus(status){
	if(status=='1'){
		return '通过';
	}
	if(status=='2'){
		return '缓议';
	}
	if(status=='0'){
		return '否决';
	}
    return '待定';
}

/**
 * 查看议题详情
 */
function showSubjectDetail(meetingID,companyID,subjectID){
	var href = "/subject/subjectDetail.action?meetingId="+meetingID+"&subjectId="+subjectID+"&companyId="+companyID;
	window.parent.addpage("议题详情",href,'subjectDetail'+subjectID);
}

/**
 * 加载权限查看数量
 */
function loadUserRoleMessage(){
    //事项ID
    var typeID = $("#typeID").val();
    //议题状态
    var passFlag = jt._("#passFlag").value;
    //查询时间
    var startTime = jt._("#startTime").value;
    var endTime = jt._("#endTime").value;
    if (jt._("#times").value != '') {
        if (startTime == "" || endTime == "") {
            return jt.Msg.showMsg("查询起止时间不可为空!");
        }
    }
    //议题名称
    var subjectName = jt._("#subjectName").value;
    //企业名称
    var companyName = $("#companyName").val();
    //企业ID
  	var companyID = $("#companyID").val();
    //会议决议情况
    var subjectResult = jt._("#subjectResult").value;
    //请求路径
    var dataURL = "{contextPath}/model/reform/getMeetingSubjectAutoCount.action";
    var jPost= {};
    jPost.typeID = typeID;
    jPost.passFlag = passFlag;
    jPost.subjectName = subjectName;
    jPost.companyName = companyName;
    jPost.companyID = companyID;
    jPost.startTime = startTime;
    jPost.subjectResult = subjectResult;
    jPost.endTime = endTime;
    postJSON(dataURL,jPost,function(json,o){
        if (!json || json.errorCode != "0") {
            return jt.Msg.showMsg("获取数据失败！");
        } else {
            document.getElementById("total").innerText = json.data[0].all;
            document.getElementById("yNumber").innerText= json.data[0].have;
            document.getElementById("nNumber").innerText= json.data[0].not;
        }
    },false);
}