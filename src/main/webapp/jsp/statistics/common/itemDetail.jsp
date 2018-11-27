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
<input type="hidden" name="companyId" id="companyId" value="${companyId}">
<div class="title_com"><h1 id="companyName"></h1></div>
<table>
<tr>
	<td style="vertical-align:top;width: 200px;" >
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
		<div id="search" class="list_search" style="display:none;">
		<table>
		<tr>
		<td style="width:100px"><p>事项清单：</p></td>
		<td style="width:220px"><input type="text" id="itemName" placeholder="请输入议题名称"></td>
		<td style="width:100px;"><div class="btn_submit" onclick="reloadItem()"><a>查询</a></div></td>
		<td style="width:100px"><div class="btn_reset"><a>重置</a></div></td>
		<td></td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="h20"></div>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/item/queryItemListByCatalog.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="事项名称" field="{itemName}" width="90" align="center">
				<col caption="事项编码" field="{itemCode}" width="50" align="center">
				<col caption="决策会议及顺序" field="{meetingDetail}" width="120" align="left">
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
		//加载表单
		reloadItem();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
