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
<tr>
	<td class="td-content_main_left" >
		<input type="hidden" name="industryID" id="industryID">
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title" ><p class="industry-choose">行业选择</p></div>
		</div>
	</td>
	<td  style="vertical-align:top;" >
	<div class="content_main_right">
		<div class="list_all">
			<div class="list_title"><p>事项汇总列表</p>
				<a onclick="showSearch()" id="showSearch">显示筛选</a>
				<a onclick="hideSearch()" style="display: none;" id="hideSearch">隐藏筛选</a>
			</div>
			<div  class="list_search" id="searchDetail">
				<table>
					<tr>
					<td style="width:100px"><p>企业名称：</p></td>
					<td style="width:220px">
						<input type="text" id="companyName" placeholder="请输入企业名称">
					</td>
					<td style="width:100px;">
						<div class="btn_submit" onclick="queryData()"><a>查询</a></div>
					</td>
					<td style="width:100px">
						<div class="btn_reset"><a onclick="resetSearchValue()">重置</a></div>
					</td>
					</tr>
				</table>
			</div>
			<hr/>
			<div class="list_main">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
					URLData="{contextPath}/statistics/item/queryIndustryItemList.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
					<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:editItem('{COMPANYID}');void(0);&quot;>{COMPANYNAME}</a>" width="90" align="center">
					<col caption="重大决策" field="{oz}" width="50" align="center">
					<col caption="重要人事任免" field="{tz}" width="50" align="center">
					<col caption="重大项目安排" field="{trz}" width="50" align="center">
					<col caption="大额度资金运作" field="{od}" width="50" align="center">
				</table>
			</div>
		</div>
	</div>
	</td>
</tr>
</table>	
</div>
</body>
	<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_item.js"></script>
	<script type="text/javascript">
		var instFlag = "${instFlag}";
		//加载行业列表
		loadingIndustry();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>