<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script>
		var hostName = "${hostName}";
		var redirectToStr = "${redirectTo}";
	</script>
	<style type="text/css">
	.measure_main_a a:hover{color: #417ac0;cursor: pointer;text-decoration: underline;}
	</style>
	<link type="text/css" href="<%=SYSURL_static%>/css/index.css" rel="stylesheet" />
</head>
<body>
<div class="content_main">
<input type="hidden" id="industryId" name="industryId" value="${industryId }">
<table>
<tr id="decisionList">
	<c:forEach items="${decision}" varStatus="i" var="item" >
		<td style="padding-left: 16px;">
		<div class="measure_main_bg">
			<table>
			<c:if test="${i.index%4==0 }">
			    <tr><td colspan="3"><div class="measure_title red"><p style="font-size:16px;">${item.statisticsName }</p></div></td></tr>
			</c:if>
		    <c:if test="${i.index%4==1 }">
			    <tr><td colspan="3"><div class="measure_title green"><p style="font-size:16px;">${item.statisticsName }</p></div></td></tr>
			</c:if>
			<c:if test="${i.index%4==2 }">
			    <tr><td colspan="3"><div class="measure_title blue"><p style="font-size:16px;">${item.statisticsName }</p></div></td></tr>
			</c:if>
			<c:if test="${i.index%4==3 }">
			    <tr><td colspan="3"><div class="measure_title purple"><p style="font-size:16px;">${item.statisticsName }</p></div></td></tr>
			</c:if>      
		    <tr>
			    <td class="measure_main"><div class="measure_one"><p style="font-size:13px;">涉及企业数量</p>
			    <div class="measure_main_a"><a style="font-size:24px;" onclick="viewDecisionCompany('${item.statisticsType }','${item.objectId }')">${item.companyQuantity }</a></div></div></td>
			    <td class="measure_main"><div class="measure_one"><p style="font-size:13px;">涉及会议数量</p>
			    <div class="measure_main_a"><a style="font-size:24px;"onclick="viewDecisionMeeting('${item.statisticsType }','${item.objectId }','','')">${item.meetingQuantity }</a></div></div></td>
			    <td class="measure_main"><div class="measure_one" style="border: none;"><p style="font-size:13px;" >涉及议题数量</p>
			    <div class="measure_main_a"><a style="font-size:24px;" onclick="viewDecisionSubject('${item.statisticsType }','${item.objectId }','','')">${item.subjectQuantity }</a></div></div></td>
		    </tr>
		    </table>
		</div>
	</td>
	</c:forEach>
</tr>
<tr>
	<td style="vertical-align:top;" colspan="6">
		<div class="list_all" style="width:99%;margin-left:16px;">
		<div class="list_title">
		<p style="font-size:16px;">贯彻执行党和国家决策部署的重大措施列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">隐藏筛选</a>
		</div>
		<div id="search" class="list_search" >
		<table>
		<tr>
		<td style="width:100px;"><p  style="font-size:16px;">企业名称：</p></td>
		<td style="width:220px;"><input type="text" id="companyName" placeholder="请输入企业名称"></td>
		<td style="width:100px;"><div class="btn_submit" onclick="queryData();"><a>查询</a></div></td>
		<td style="width:100px;"><div class="btn_reset" onclick="resetAll()"><a>重置</a></div></td>
		<td>&nbsp;</td>
		</tr>
		<tr id="danger" style="display:none;height:10px">
		<td></td>
		<td>
		<div style="font-size:10px;color:red">请输入查询内容</div>
		</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0"
                   cellpadding="0"  EmptyInfo="没有记录" width="100%"
				URLData="{contextPath}/statistics/decision/queryCompanyDecisionList.action?currentPage={page}&pageSize={pageSize}&industryId=${industryId }">
				<col caption="序号" field="{RN}" width="30" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:viewDecisionSubject('','','{companyId}','{companyName}');&quot;>{companyShortName}</a>" width="90" align="center">
				<c:forEach items="${decision}" varStatus="i" var="item" > 
				    <col caption="${item.statisticsName }" field="[=fmtCol('${item.statisticsType}','${item.objectId}','{${i.index }}','{companyId}','{companyName}')]" width="50" align="center">
				</c:forEach>
			</table>
		</div>
		</div>
	</td>
</tr>
</table>	
</div>
</body>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_decision_list.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>