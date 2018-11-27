<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>议题列表</title>
</head>
<script type="text/javascript">
function queryData(){
	//企业名称
	var companyName = jt._("#companyName").value;
	//会议类型
	var typeID = jt._("#typeID").value;
	//会议名称
	var meetingName = jt._("#meetingName").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	var data = "{\"companyName\":\""+companyName+"\",\"typeID\":\""+typeID+"\",\"meetingName\":\""+meetingName+"\",\"subjectName\":\""+subjectName+"\"}";
	//查询
	var sURL = "{SYSURL.oa}/subject/model/querySubjectList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);;
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
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
   	if(curUser.orgLevel == 0){
   		NavGoURL("{SYSURL_oa}/subject/subjectDetail.action?subjectId="+subjectId+"&meetingId="+meetingId+"&companyId="+companyId);
   	}else{
        var href="/subject/subjectDetail.action?subjectId="+subjectId+"&meetingId="+meetingId+"&companyId="+companyId;
        window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
   	}
}

</script>
<body class="BodyList">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		</div>
		<div id="divSitebar">
			<div id="divCurPosition">当前位置 : </div>
		</div>
	</div>
	<div id="divSearch" ShowCount="0" >
	  <table>
	  	<tr>
	  		<td style="width: 5%" align="right">企业名称:</td>
	  		<td style="width: 10%">
	  			<input type="text" id="companyName" class="input" />
	  		</td>
	  		<td style="width: 5%" align="right">会议名称:</td>
	  		<td style="width: 10%">
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
	  		<td style="width: 20%">
	  			<input type="button" class="button" id="searchBtn" value="搜索" onclick="queryData()" />
	  		</td>
	  	</tr>
	  </table>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/subject/model/querySubjectList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%">
			<col caption="序号" field="{RN}" width="20" align="center">
			<col caption="企业名称" field="{companyName}" align="center" width="70px">
			<col caption="会议名称" field="{meetingName}" align="center" width="100px">
			<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:viewSubjectDetail('{subjectId}','{meetingId}','{companyId}');void(0);&quot;>{subjectName}</a>" align="center" width="100px">
			<col caption="会议类型" field="{typeName}" align="center"  width="30px">
			<col caption="会议日期" field="{meetingTime}" align="center" width="30px">
		</table>
	</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>