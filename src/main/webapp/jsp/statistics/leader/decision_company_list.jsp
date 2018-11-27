<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <%@ include file="/common/inc_head.jsp"%>
    <title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/decision-subject-list.css" type="text/css"/>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
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
	<td class="td_content_main_left" >
	    <input type="hidden" name="statisticsType" id="statisticsType" value="${statisticsType }">
	    <input type="hidden" name="objectId" id="objectId" value="${objectId }">
	    <input type="hidden" name="industryId" id="industryId" value="${industryId }">
		<div id="treeDiv" class="content_main_left" >
		<div class="industry_title" ><p class="industry-choose">类型</p></div>
		<div class="industry_contents">
		<a class="industry_contents_cur" style="font-size:15px;" onclick="reloadTableData('','','',this);">全部类型</a>
		</div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td >
		<div class="list_all" >
		<div class="list_title">
		<p style="font-size:16px;" id="tableTitle">企业公司列表</p>
		<a id="showHide" class="backgroundimg1"  onclick="showHideSearch()">隐藏筛选</a></div>
		<div id="search" class="list_search" >
		<table>
		<tr>
		<td style="width:100px"><p>企业名称：</p></td><td style="width:220px"><input id="companyName" type="text" placeholder="请输入企业名称" ></td>
		<td style="width:100px;"><div class="btn_submit" onclick="queryData()"><a>查询</a></div></td><td style="width:100px"><div class="btn_reset" onclick="resetAll()"><a>重置</a></div></td>
		<td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
                       EmptyInfo="没有记录" width="100%"
					URLData="{contextPath}/statistics/decision/queryDecisionCompanyList.action?currentPage={page}&pageSize={pageSize}&statisticsType=${statisticsType}&objectId=${objectId}&industryId=${industryId}" >
					<col caption="序号" field="{RN}" width="50" align="center">
				    <col caption="企业名称" field="{companyShortName}" width="auto" align="center">
				    <col caption="会议数量" field="<a style='color:blue' href=&quot;javascript:viewMeetingList('{companyId}','{companyName}');&quot;>{meetingQuantity}</a>" width="260" align="center">
					<col caption="议题数量" field="<a style='color:blue' href=&quot;javascript:viewSubjectList('{companyId}','{companyName}');&quot;>{subjectQuantity}</a>" width="260" align="center">
				</table>
			</div>
		</div>
	</td>
</tr>
</table>	
</div>
</body>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/decision_company_list.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>