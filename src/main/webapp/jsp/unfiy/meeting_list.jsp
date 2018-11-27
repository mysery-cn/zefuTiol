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
<div class="top_add"><div class="add_main"><img src="<%=SYSURL_static%>/images/tiol/ico_add.png"><a>首页</a><p>-</p><a>会议列表</a></div></div>
<div class="content_main">
<table>
<tr>
	<td width="16px;"></td>
	<td style="vertical-align:top;">
		<div class="list_all">
		<div class="list_title"><p>企业会议列表</p>
			<a onclick="showSearch()" style="display: none;" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" id="hideSearch">隐藏筛选</a>
		</div>
		<div class="list_search" id="searchDetail">
		<table>
		<tr>
		<td width="100px;"><p>企业名称：</p></td><td width="200px;">
			<input id="companyName" type="text" placeholder="请输入企业名称"></td>
		<td style="width:100px"><p>会议名称：</p></td><td style="width:200px;">
			<input type="text" id="meetingName" placeholder="请输入会议名称">
		</td>
		<td width="100px;"><p>会议类型：</p></td>
		<td width="200px;">
			<div class="select_diy" style="width: 200px">
		       	<select name="typeId" id="typeId">
		       		<option value="">--请选择--</option>
		    	</select>
		    </div>
		</td>
		</tr>
		<tr>
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
			<div class="btn_reset"><a onclick="resetSearch()">重置</a><a onclick="login()">测试</a></div></td><td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<div class="dividing_line"></div>
		<div class="list_main_content">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/tiol/unify/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}"
                   EmptyInfo="没有记录" width="100%" >
				<col caption="序号" field="{RN}" width="10" align="center">
				<col caption="会议名称" field="<a style='color:blue' href=&quot;javascript:itemView('{MEETING_ID}','{COMPANY_ID}');void(0);&quot;>{MEETING_NAME}</a>" width="80" align="center">
				<col caption="会议日期" field="{MEETING_TIME}" width="60" align="center">
				<col caption="会议类型" field="{TYPE_NAME}" width="30" align="center">
				<col caption="企业名称" field="{COMPANY_NAME}" width="80" align="center">
				<col caption="企业简称" field="{COMPANY_SHORT_NAME}" width="30" align="center">
				<col caption="主持人" field="{MODERATOR}" width="30" align="center">
				<col caption="会议纪要" field="{SUMMARY_FILE_ID}" width="30" align="center">
				<col caption="会议记录" field="{RECORD_FILE_ID}" width="30" align="center">
				<col caption="会议通知" field="{NOTICE_FILE_ID}" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/unfiy/meeting_list.js"></script>
	<script type="text/javascript">
		loadingMeetingType();
		function login(){
			window.location.href="http://oa.sasac.gov.cn:8080/tiol/login.action";
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>