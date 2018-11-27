<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
</head>

<body>
<div class="content_main">
<table>
<tr><td width="33%" style="padding-right: 20px;">
	<div class="list_all">
	<div class="list_title"><p>规章制度建设情况</p></div>
	<div class="chart_img_gai">
		<div id="regulation" style="height: 250px;width: 95%;margin: 0 auto;"></div>
	</div>
	</div>
</td>
<td width="33%" style="padding-right: 20px;">
	<div class="list_all">
	<div class="list_title"><p>2018年决策会议情况</p></div>
	<div class="chart_img_gai">
		<div id="item" style="height: 250px;width: 95%;margin: 0 auto;"></div>
	</div>
	</div>
</td>	
<td width="33%">
	<div class="list_all">
	<div class="list_title"><p>2018年“三重一大”决策议题情况</p></div>
	<div class="chart_img_gai">
		<div id="decision" style="height: 250px;width: 95%;margin: 0 auto;"></div>
	</div>
	</div>
</td>
</tr>
</table>
	<div class="list_all">
		<div class="list_title"><p>所有列表</p>
			<a onclick="showSearch()" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" style="display: none;" id="hideSearch">隐藏筛选</a>
		</div>
		<div  class="list_search" id="searchDetail" style="display: none;">
		<table>
			<tr>
				<td width="110px;"><p>企业名称：</p></td>
				<td width="240px;">
					<input type="text" id="companyName" placeholder="请输入企业名称">
				</td>
				<td width="90px;">
					<div class="btn_submit"><a onclick="queryData()">查询</a></div>
				</td>
				<td width="90px;">
					<div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/company/querPageList.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{rn}" width="50" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:redirectComapnyView('{companyId}');void(0);&quot;>{companyName}</a>" width="auto" align="center">
				<col caption="规章制度" field="<a style='color:blue' href=&quot;javascript:redirectRegulationView('{companyId}');void(0);&quot;>{regulationQuantity}</a>" width="auto" align="center">
				<col caption="决策事项" field="<a style='color:blue' href=&quot;javascript:redirectItemView('{companyId}');void(0);&quot;>{itemQuantity}</a>" width="auto" align="center">
				<col caption="决策议题" field="<a style='color:blue' href=&quot;javascript:redirectSubjectView('{companyId}');void(0);&quot;>{subjectQuantity}</a>" width="auto" align="center">
				<col caption="决策会议" field="<a style='color:blue' href=&quot;javascript:redirectMeetingView('{companyId}');void(0);&quot;>{meetingQuantity}</a>" width="auto" align="center">
			</table>
		</div>
	</div>
</div>
</body>
	<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/reform/reform.js"></script>
	<script type="text/javascript">
		showRegulation();
		showItem();
		showDecision();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>