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
	<td class="td-content_main_left">
		<input type="hidden" name="industryID" id="industryID">
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title"><p class="industry-choose">行业选择</p></div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td  style="vertical-align:top;" >
	<div class="content_main_right">
	<div class="list_all">
		<div class="list_title"><p>事项汇总列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch()">显示筛选</a></div>
		<div id="search" class="list_search">
		<table>
		<tr>
		<td width="110px;"><p>企业名称：</p></td><td width="240px;">
		<input type="text" id="companyName" placeholder="请输入企业名称"></td>
		<td width="90px;">
			<div class="btn_submit" ><a onclick="queryData()">查询</a></div>
		</td>
		<td width="90px;">
			<div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div>
		</td>
		<td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<hr/>
			<div class="list_main_content">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" 
					URLData="{contextPath}/statistics/item/queryIndustryItemList.action?currentPage={page}&pageSize={pageSize}&industryID=${instFlag}" EmptyInfo="没有记录" width="100%">
					<col caption="序号" field="{RN}" width="50" align="center">
					<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:editItem('{COMPANYID}');void(0);&quot;>{COMPANYNAME}</a>" width="auto" align="center">
					<col caption="总数" field="<a style='color:blue' href=&quot;javascript:editItem('{COMPANYID}');void(0);&quot;>{all}</a>" width="auto" align="center">
					<col caption="重大决策" field="<a style='color:blue' href=&quot;javascript:editItemCatalogType('{COMPANYID}','D');void(0);&quot;>{oz}</a>" width="auto" align="center">
					<col caption="重要人事任免" field="<a style='color:blue' href=&quot;javascript:editItemCatalogType('{COMPANYID}','H');void(0);&quot;>{tz}</a>" width="auto" align="center">
					<col caption="重大项目安排" field="<a style='color:blue' href=&quot;javascript:editItemCatalogType('{COMPANYID}','P');void(0);&quot;>{trz}</a>" width="auto" align="center">
					<col caption="大额度资金运作" field="<a style='color:blue' href=&quot;javascript:editItemCatalogType('{COMPANYID}','F');void(0);&quot;>{od}</a>" width="auto" align="center">
				</table>
			</div>
		</div>
	</div>
	</td>
</tr>
</table>	
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_item.js"></script>
<script type="text/javascript">
	var instFlag = "${instFlag}";
	//加载行业列表
	loadingIndustry();
</script>