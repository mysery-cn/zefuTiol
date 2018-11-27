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
		<div class="list_title"><p>企业议题列表</p>
			<a onclick="showSearch()" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" style="display: none;" id="hideSearch">隐藏筛选</a>
		</div>
		<div class="list_search" style="display: none;" id="searchDetail">
		<table>
		<tr>
		<td width="100px;"><p>企业名称：</p></td><td width="200px;">
			<input id="companyName" type="text" placeholder="请输入企业名称"></td>
		<td width="100px;"><p>会议名称：</p></td><td width="200px;">
			<input id="meetingName" type="text" placeholder="请输入会议名称"></td>
		<td width="100px;"><p>议题名称：</p></td><td width="200px;">
			<input id="subjectName" type="text" placeholder="请输入议题名称"></td>
		</tr>
		<tr>
		<td width="100px;"><p>事项类型：</p></td>
		<td width="200px;">
			<div class="select_diy" style="width: 270px">
		       	<select name="levelCode" id="levelCode">
		       		<option value="">--请选择--</option>
		    	</select>
		    </div>
		</td>
		<td><div class="btn_submit"><a onclick="queryData()">查询</a></div></td><td>
		<div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td>
		</tr>
		</table>
		</div>
		<div class="dividing_line"></div>
		<div class="list_main_content">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/tiol/unify/querySubjectByPage.action?currentPage={page}&pageSize={pageSize}"
                   EmptyInfo="没有记录" width="100%" >
				<col caption="序号" field="{RN}" width="10" align="center">
				<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:itemView('{SUBJECT_ID}','{MEETING_ID}','{COMPANY_ID}');void(0);&quot;>{SUBJECT_NAME}</a>" width="50" align="center">
				<col caption="议题编码" field="{SUBJECT_CODE}" width="30" align="center">
				<col caption="企业简称" field="{COMPANY_SHORT_NAME}" width="30" align="center">
				<col caption="会议名称" field="{MEETING_NAME}" width="50" align="center">
				<col caption="会议日期" field="{MEETING_TIME}" width="40" align="center">
				<col caption="事项类型" field="[=formatCatalog('{CATALOG_CODE}')]" width="30" align="center">
				<col caption="专项名称" field="{SPECIAL_NAME}" width="30" align="center">
				<col caption="事项名称" field="{ITEM_NAME}" width="30" align="center">
				<col caption="是否通过" field="[=formatPass('{PASS_FLAG}')]" width="20" align="center">
				<col caption="是否需报国资委审批" field="[=formatStatus('{APPROVAL_FLAG}')]" width="25" align="center">
				<col caption="是否听取意见" field="[=formatStatus('{ADOPT_FLAG}')]" width="20" align="center">
				<col caption="议题材料" field="{SUBJECT_FILE_ID}" width="30" align="center">
				<col caption="议题决议" field="{SUBJECT_RESULT}" width="30" align="center">
				<col caption="是否校验" field="[=formatStatus('{VERIFY_FLAG}')]" width="20" align="center">
				<col caption="是否经过董事会决策" field="[=formatStatus('{VIA_FLAG}')]" width="25" align="center">
				<col caption="备注" field="{REMARK}" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/unfiy/subject_list.js"></script>
	<script type="text/javascript">
		function formatCatalog(code){
		    code = code.substr(0, 1);
			if(code=='D'){
		    	return '重大决策';
		    }else if(code=='H'){
		    	return '重大决策';
		    }else if(code=='P'){
		    	return '重大决策';
		    }else if(code=='F'){
		    	return '大额度资金运作';
		    }return '';
		}
		function formatPass(code){
		    if(code=='1'){
		    	return '通过';
		    }else if(code=='2'){
		    	return '缓议';
		    }return '否决';
		}
		function formatStatus(code){
		    if(code=='1'){
		    	return '是';
		    }return '否';
		}
		loadingCatalogType();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>