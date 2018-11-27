<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>议题异常列表</title>
</head>
<script type="text/javascript">
function queryData(){
	//异常类型
	var exceptionType = jt._("#exceptionType").value;
	//议题名称
	var subjectName = jt._("#subjectName").value;
	var data = "{\"exceptionType\":\""+exceptionType+"\",\"subjectName\":\""+subjectName+"\"}";
	//查询
	var sURL = "{SYSURL.oa}/subjectException/querySubjectExceptionByPage.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);;
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

    //判断同步是否隐藏
    if(curUser.companyId == "GZW"){
        $("#exportButton").hide();
    }
}

function viewSubjectDetail(subjectId,meetingId,companyId){
   	if(curUser.orgLevel == 0){
   		NavGoURL("{SYSURL_oa}/jsp/subject/subject_view.jsp?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId);
   	}else{
   		var href = "{SYSURL_oa}/jsp/subject/subject_view.jsp?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
   		window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
   	}
}

</script>
<body class="BodyList">
    <div id="divFixTop">
        <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
            <!-- <div icon="export.png" id="exportButton"  onclick="synchronous('subject_exception.xml')">同步</div> -->
        </div>
    </div>
	<div id="divSearch" ShowCount="0" >
	  <table>
	  	<tr>
	  		<td style="width: 5%" align="right">议题名称:</td>
	  		<td style="width: 10%">
	  			<input type="text" id="subjectName" class="input" />
	  		</td>
	  		<td style="width: 5%" align="right">异常类型:</td>
	  		<td style="width: 10%">
	  			<select id="exceptionType" style="width: 80%">
	  				<option value="">请选择</option>
                    <option value="1">合法合规审查</option>
                    <option value="2">决策会议顺序</option>
                    <option value="3">党委（党组）会前置</option>
                    <option value="4">会议召开条件</option>
                    <option value="5">表决结果</option>
                    <option value="6">采集校验异常</option>
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
			URLData="{SYSURL.oa}/subjectException/querySubjectExceptionByPage.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%">
			<col caption="序号" field="{RN}" width="20" align="center">
			<col caption="议题名称" field="{subjectName}" align="center" width="70px">
			<col caption="异常原因" field="{exceptionCause}" align="center" width="100px">
		</table>
	</div>

</body>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>