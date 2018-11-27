<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/sidebar-menu.css" type="text/css"/>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
</head>
<body>
<div class="content_main">
<div class="title_com"><h1 id="companyName"></h1></div>
<table>
<tr>
	<input type="hidden" name="catalogId" id="catalogId">
	<input type="hidden" name="catalogType" id="catalogType" value="${catalogType }">
	<input type="hidden" name="companyId" id="companyId" value="${companyId }">
	<td style="vertical-align:top;width: 250px;" >
		<div class="main-sidebar">
			<div  class="sidebar" id="catalogTree">
				
			</div>
		</div>
	</td>
	
	<td width="16px;"></td>
	
	<td style="vertical-align:top;" >
		<div class="list_all" style="width:100%;">
		<div class="list_title">
		<p style="font-size:16px;">事项清单列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">显示筛选</a>
		</div>
		<div id="search" class="list_search" > 
			<table>
				<tr>
					<td style="width:100px"><p>事项清单：</p></td>
					<td style="width:220px"><input type="text" id="itemName" placeholder="请输入事项名称"></td>
					<td style="width:100px;"><div class="btn_submit" onclick="queryData()"><a>查询</a></div></td>
					<td style="width:100px"><div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div></td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
		<hr/>
		<div class="list_main" style="height: 520px;">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" style="text-align:center;" border="0" cellspacing="0" cellpadding="0"  
				URLData="{contextPath}/statistics/item/queryItemListByCatalog.action?currentPage={page}&pageSize={pageSize}&companyID=${companyId}&catalogId=${catalogId}" 
				EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{RN}" width="50" align="center">
				<col caption="事项名称" field="{itemName}" width="auto" align="center">
				<col caption="事项编码" field="{itemCode}" width="70" align="center">
				<col caption="决策会议及顺序" field="[=formatStatus('{meetingDetail}')]" width="auto" align="left">
			</table>
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_item_detail.js"></script>
	<!--树状菜单开始-->
	<script src="<%=SYSURL_static%>/js/sidebar-menu.js"></script>
	<script type="text/javascript">
		var firstCatalogID;
		//加载事项目录列表
		loadMessage();
	</script>
</html>
