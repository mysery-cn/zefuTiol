<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中央企业“三重一大”决策和运行应用系统</title>
	<link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/sidebar-menu.css" type="text/css"/>
</head>
<body>
<div class="content_main">
<input type="hidden" name="companyId" id="companyId">
<input type="hidden" name="catalogId" id="catalogId">
<div class="title_com"><h1 id="companyName" name="companyName"></h1></div>
<table>
<tr>
	<td class="td-content_main_left" >
		<input type="hidden" name="typeId" id="typeId">
		<div id="treeDiv" class="meetting_main_left">
		<div class="industry_title"><p class="industry-choose">会议类型</p></div>
		<div class="industry_contents"><a class="industry_contents_cur" onclick="showView(this,'','')">全部会议</a></div>
		</div>
	</td>
	<td width="16px;"></td>
	<td style="vertical-align:top;">
		<div class="list_all" style="width:100%;">
		<div class="list_title">
		<p id="typeName" style="font-size:16px;">企业会议列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">隐藏筛选</a>
		</div>
		<div id="search" class="list_search"> 
		<table>
		<tr>
		<td style="width:100px"><p>会议名称：</p></td><td style="width:200px">
			<input type="text" id="meetingName" placeholder="请输入会议名称">
		</td>
		<td width="100px;"><p>时间区间：</p></td>
		<td width="200px;">
			<div class="select_diy">
	       		<select name="times" id="times" onchange="clickTime()">
	       			<option value="">--请选择--</option>
	    		</select>
		    </div>
		</td>
		<td name="timeDiv" style="width:100px;display: none;"><p>查询时间：</p></td>
		<td name="timeDiv" style="display: none;">
			<table style="width: 220px" border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td style="width: 110px"><input name="opeDate" id="startTime"
							class="Input_DateTime input" style="width: 110px;"
							onchange="TimeJudge(this)" type="text"
							readonly="readonly" value="" oper=">"
							fieldname="opeDate"></td>
						<td>至</td>		
						<td style="width: 110px"><input name="opeDate" id="endTime"
							class="Input_DateTime input" style="width: 110px;"
							onchange="" type="text"
							readonly="readonly" value="" oper="<" fieldname="opeDate"></td>
					</tr>
				</tbody>
			</table>
		</td>
		<td style="width:100px;">
			<div class="btn_submit"><a onclick="queryData()">查询</a></div></td><td style="width:100px">
			<div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td><td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
		    <div class="total_num"><p>当前会议总数：</p><span id="all">0</span></div>
            <div class="no_front"><p>有权限查看数：</p><span id="have">0</span></div>
            <div class="no_front"><p>无权限查看数：</p><span id="not">0</span></div>
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/meeting/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}&companyId=${companyId}&catalogId=${catalogId}&typeId=${typeId}"
               EmptyInfo="没有记录" width="100%" >
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="会议名称" field="<a style='color:blue' href=&quot;javascript:itemView('{MEETING_ID}');void(0);&quot;>{MEETING_NAME}</a>" width="90" align="center">
				<col caption="会议日期" field="{MEETING_TIME}" width="50" align="center">
				<col caption="会议类型" field="{TYPE_NAME}" width="50" align="center">
				<col caption="材料是否完整" field="{flag}" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/meeting_list.js"></script>
	<script type="text/javascript">
		getCompanyNameById();
		loadingMeetingType();
		getMeetingAllCount();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>