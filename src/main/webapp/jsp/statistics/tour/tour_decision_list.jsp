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
<table>
<tr>
	<input type="hidden" id="industryId" name="industryId" value="">
	<td style="vertical-align:top;" colspan="4">
		<div class="list_all" style="width:99%;margin-left:16px;">
		<div class="list_title"><p style="font-size:16px;">贯彻执行党和国家决策部署的重大措施列表</p>
			<a id="showHide" herf="javascript:void(0);" onclick="showHideSearch();">隐藏筛选</a></div>
		<div  class="list_search">
		<table>
		<tr id="search">
		<td style="width:100px;"><p  style="font-size:16px;">企业名称：</p></td>
		<td style="width:220px;"><input type="text" id="companyName" placeholder="请输入企业名称"></td>
		<td style="width:100px;"><div class="btn_submit" onclick="queryData();"><a>查询</a></div></td>
		<td style="width:100px;"><div class="btn_reset"><a onclick="resetSearchValue()">重置</a></div></td>
		<td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="h20"></div>
		<div class="list_main_content">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/decision/queryCompanyDecisionList.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_decision_list.js"></script>
	<script type="text/javascript">
		function viewDecisionSubjects(type,id,companyId){
			var href = "/tiol/url/goDecisionDetailByCompany.action?statisticsType="+type+"&objectId="+id+"&companyId="+companyId;
			window.parent.addpage("企业议题列表",href,'tourDecisionSubject'+companyId);
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>