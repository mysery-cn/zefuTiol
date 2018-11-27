<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中央企业“三重一大”决策和运行应用系统</title>
	<link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
</head>
<body>
<div class="content_main">
<input type="hidden" name="companyId" id="companyId">
<div class="title_com"><h1 id="companyName" name="companyName"></h1></div>
<table>
<tr>
	<td class="td-content_main_left">
		<input type="hidden" name="catalogId" id="catalogId">
		<input type="hidden" name="levelCode" id="levelCode" value="F">
		<div class="meetting_main_left" id="treeDiv">
		<div class="industry_title"><p class="industry-choose">类型</p></div>
		<div class="industry_contents"><a class="industry_contents_cur" onclick="showView(this,'','')">全部类型</a></div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td style="vertical-align:top;">
		<div class="list_all">
		<div class="list_title"><p id="typeName">事项清单列表</p>
			<a onclick="showSearch()" class="backgroundimg1" style="display: none;" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" class="backgroundimg2" id="hideSearch">隐藏筛选</a>
		</div>
		<div class="list_search" id="searchDetail">
		<table>
		<tr>
		<td style="width:100px"><p>事项清单：</p></td><td style="width:220px">
			<input type="text" id="itemName" placeholder="请输入事项清单">
		</td>
		<td style="width:100px;">
			<div class="btn_submit"><a onclick="queryData()">查询</a></div></td><td style="width:100px">
			<div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td><td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main_content">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"  
				URLData="{contextPath}/item/queryCatalogItemByPage.action?currentPage={page}&pageSize={pageSize}&companyId=${companyId}&catalogId=${catalogId}&levelCode=${levelCode}"
                   EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{RN}" width="30" align="center">
				<col caption="事项名称" field="{itemName}" width="50" align="center">
				<col caption="事项编码" field="{itemCode}" width="50" align="center">
				<col caption="决策会议及顺序" field="[=formatStatus('{meetingDetail}')]" width="150" align="left">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/financial/item_list.js"></script>
	<script type="text/javascript">
		jt._("#levelCode").value = jt.getParam('levelCode')
		getCompanyNameById();
		loadingCatalogLevel();
		/**
		 * 解析排序顺序
		 */
		function formatStatus(meetingDetail){
			if(meetingDetail == "" || meetingDetail == null || meetingDetail == "undefind"){
				return "";
			}
			var order = meetingDetail.split(",");
			var detail = '';
			for (var i = 0; i < order.length; i++) {
				detail = detail + '<td style="text-align: left;">';
				detail = detail + '<div class="bg_meeting"><div class="bg_meet_left"></div><div class="meeting_name_bg">'+order[i]+'</div><div class="bg_meet_right"></div></div>';
				if(i < order.length - 1){
					detail = detail + '<div class="meetting_line"></div>';
				}
				detail = detail + '</td>';
			}
			return detail;
		}
		//queryData();
	</script>
</html>