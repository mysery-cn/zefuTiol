<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>选择关联的会议和议题</title>
</head>
<script type="text/javascript">
function queryData(){
	//企业名称
	var companyId = jt.getParam("companyId");

	//会议类型
	var typeID = jt._("#typeID").value;
	//会议名称
	var meetingName = jt._("#meetingName").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	//var data = "{\"companyId\":\""+companyId+"\",\"typeID\":\""+typeID+"\",\"meetingName\":\""+meetingName+"\"}";
    var data = "&companyId="+companyId+"&typeID="+typeID+"&meetingName="+encodeURIComponent(meetingName)+"&subjectName="+encodeURIComponent(subjectName);
    //查询
	var sURL = "{SYSURL.oa}/subject/model/querySubjectList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}"+data;
	//jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData(sURL);
}
function jtBeforeGridLoadData(oComp) {
    jt._("#companyId").value = jt.getParam("companyId");
    var data = "&companyId="+jt.getParam("companyId");
    var sURL = "{SYSURL.oa}/subject/model/querySubjectList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}"+data;
    jt.setAttr(_('#tabMain_List'),'URLData',sURL);
}
function init(){
	//加载制度类型
	var dataURL = "{contextPath}/meetingType/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				$("#typeID").append("<option value='"+json.data[i].TYPE_ID+"'>"+json.data[i].TYPE_NAME+"</option>");
			}
		}
	},false);
}
function viewSubjectDetail(subjectId,meetingId,companyId){
	NavGoURL("{SYSURL_oa}/jsp/subject/subject_view.jsp?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId);
}

/**
 * 选中回填事件
 */
function selectSubmit(){
    var arr = jt.getCheckBoxValue('txtID',false,true);
    if(arr.length == 0) {
        jtAlert("请选择关联的会议议题");
        return;
	}
	var relMeetingId = "";
    var relMeetingName = "";
    var relSubjectId = "";
    var relSubjectName = "";
    for (var i = 0; i < arr.length; i++) {
        var datas = arr[i].split("@");
        relMeetingId = relMeetingId + datas[0] + ",";
        relMeetingName = relMeetingName + datas[1] + ",";
        relSubjectId = relSubjectId + datas[2] + ",";
        relSubjectName = relSubjectName + datas[3] + ",";
    }
    parent.document.getElementById("relMeetingId").value = relMeetingId.substring(0,relMeetingId.length-1);
	parent.document.getElementById("relMeetingName").value = relMeetingName.substring(0,relMeetingName.length-1);
    parent.document.getElementById("relSubjectId").value = relSubjectId.substring(0,relSubjectId.length-1);
    parent.document.getElementById("relSubjectName").value = relSubjectName.substring(0,relSubjectName.length-1);
    closeSelf();
}
</script>
<body class="BodyList">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
			<div icon="close.png" id="closeButton" onclick="closeSelf()">关闭</div>
			<div icon="add.png" id="okButton" onclick="selectSubmit()">确定</div>
		</div>
		<div id="divSitebar">
			<div id="divCurPosition">当前位置 : </div>
		</div>
	</div>
	<div id="divSearch" ShowCount="0" >
	  <table>
	  	<tr>
	  		<td style="width: 5%" align="right">会议名称:</td>
	  		<td style="width: 10%">
				<input type="hidden" id="companyId" class="input" />
	  			<input type="text" id="meetingName" class="input" />
	  		</td>
	  		<td style="width: 5%" align="right">议题名称:</td>
	  		<td style="width: 10%">
	  			<input type="text" id="subjectName" class="input" />
	  		</td>
	  		<td style="width: 5%" align="right">会议类型:</td>
	  		<td style="width: 10%">
	  			<select id="typeID" style="width: 80%">
	  				<option value="">请选择</option>
	  			</select>
	  		</td>
	  		<td style="width: 5%">
	  			<input type="button" class="button" id="searchBtn" value="搜索" onclick="queryData()" />
	  		</td>
	  	</tr>
	  </table>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid"  style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/subject/model/querySubjectList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%">
			<col boxName="txtID" boxValue="{meetingId}@{meetingName}@{subjectId}@{subjectName}" boxAttr="{boxAttr}" width="15px">
			<col caption="会议名称" field="{meetingName}" align="center" width="100px">
			<col caption="议题名称" field="{subjectName}" align="center" width="100px">
			<col caption="会议类型" field="{typeName}" align="center"  width="30px">
		</table>
	</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>