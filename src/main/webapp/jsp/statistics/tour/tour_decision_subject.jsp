<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <%@ include file="/common/inc_head.jsp"%>
    <title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/decision-subject-list.css" type="text/css"/>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
	<script>
		var hostName = "${hostName}";
		var redirectToStr = "${redirectTo}";
	</script>
	<link type="text/css" href="<%=SYSURL_static%>/css/index.css" rel="stylesheet" />
</head>
<body>
<div class="content_main">
<table>
<tr>
	<td class="td-content_main_left" >
	    <input type="hidden" name="statisticsType" id="statisticsType" value="${statisticsType }">
	    <input type="hidden" name="objectId" id="objectId" value="${objectId }">
	    <input type="hidden" name="companyId" id="companyId" value="${companyId }">
	    <input type="hidden" name="industryId" id="industryId" value="${industryId }">
		<div id="treeDiv" class="meetting_main_left">
		<div class="industry_title" ><p class="industry-choose">类型</p></div>
		<div class="industry_contents" ><a class="industry_contents_cur" onclick="reloadSubjectData('','',this);">全部类型</a></div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td style="vertical-align:top;" >
		<div class="list_all">
		<div class="list_title"><p>企业议题列表</p><a id="showHide" onclick="showHideSearch()">隐藏筛选</a></div>
		<div id="search" class="list_search">
		<table>
		<tr>
		<td style="width:100px"><p>企业名称：</p></td><td style="width:220px"><input id="companyName" type="text" placeholder="请输入企业名称" value="${companyName }"></td>
		<td style="width:100px"><p>议题名称：</p></td><td style="width:220px"><input id="subjectName" type="text" placeholder="请输入议题名称"></td>
		<td style="width:100px;"><div class="btn_submit" onclick="queryData()"><a>查询</a></div></td>
		<td style="width:100px"><div class="btn_reset" ><a onclick="resetSearchValue()">重置</a></div></td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main_content">
				<table class="Grid" style="text-align:center;"  FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
					URLData="{contextPath}/subject/queryDecisionSubjectList.action?currentPage={page}&pageSize={pageSize}&companyId=${companyId }&statisticsType=${statisticsType}&objectId=${objectId}" EmptyInfo="没有记录" width="100%">
					<col caption="序号" field="{RN}" width="10" align="center">
					<col caption="企业名称" field="{companyName}" width="30" align="center">
					<col caption="议题名称" width="50" align="center" field="<a style='color:blue' href=&quot;javascript:SubjectDetail('{subjectId}','{meetingId}','{companyId}');&quot;>{subjectName}</a>" width="90" align="center">
					<col caption="会议名称" field="{meetingName}" width="70" align="center">
					<col caption="会议日期" field="{meetingTime}" width="30" align="center">
					<col caption="会议类型" field="{meetingType}" width="30" align="center">
				</table>
			</div>
		</div>
	</td>
</tr>
</table>	
</div>
</div>
</body>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/decision_subject_list.js"></script>
	<script type="text/javascript">
		function SubjectDetail(subjectId,meetingId,companyId){
			var href = "/subject/subjectDetail.action?type=tour&meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
			window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>