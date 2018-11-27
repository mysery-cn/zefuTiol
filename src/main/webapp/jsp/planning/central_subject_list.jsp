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
<table>
<tr>
	<td class="td-content_main_left">
		<input type="hidden" name="itemId" id="itemId">
		<div class="meetting_main_left" id="treeDiv">
		<div class="industry_title"><p class="industry-choose">类型</p></div>
		<div class="industry_contents"><a class="industry_contents_cur" onclick="showView(this,'','')">全部类型</a></div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td style="vertical-align:top;" >
		<div class="list_all">
		<div class="list_title"><p id="typeName">企业议题列表</p>
			<a onclick="showSearch()" class="backgroundimg1" style="display: none;" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" class="backgroundimg2" id="hideSearch">隐藏筛选</a>
		</div>
		<div  class="list_search" id="searchDetail">
		<table>
		<tr>
		<td style="width:80px"><p>企业名称：</p></td><td style="width:180px">
			<input id="companyName" type="text" placeholder="请输入企业名称"></td>
		<td style="width:80px"><p>议题名称：</p></td><td style="width:180px">
			<input id="subjectName" type="text" placeholder="请输入议题名称"></td>
		<td style="width:80px"><p>决议内容：</p></td>
        <td style="width:180px;">
            <input style="width:180px" type="text" id="subjectResult" placeholder="请输入决议情况内容">
        </td>
		<td style="width:100px;"><div class="btn_submit"><a onclick="queryData()">查询</a></div>
		</td><td style="width:100px"><div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td>
        </tr>
    	<tr>
		<td style="width:80px"><p>是否通过：</p></td>
		<td style="width:180px">
      		<div class="select_diy" style="width: 180px">
       		<select name="passFlag" id="passFlag">
       			<option value="">--请选择--</option>
        		<option value="1">通过</option>
        		<option value="2">缓议</option>
        		<option value="0">否决</option>
    		</select>
    		</div>
    	</td>
    	<td width="80px"><p>时间区间：</p></td>
		<td width="180px;">
			<div class="select_diy" style="width: 180px">
	       		<select name="times" id="times" onchange="clickTime()">
	       			<option value="">--请选择--</option>
	    		</select>
		    </div>
		</td>
		<td name="timeDiv" style="width:80px;display: none;"><p>查询时间：</p></td>
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
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main_content">
		<div class="total_num"><p>当前会议总数：</p><span id="all">0</span></div><div class="no_front"><p>有权限查看数：</p><span id="have">0</span></div><div class="no_front"><p>无权限查看数：</p><span id="not">0</span></div>
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/subject/queryMeetingSubjectListByPage.action?currentPage={page}&pageSize={pageSize}"
                   EmptyInfo="没有记录" width="100%" >
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="企业名称" field="{COMPANY_SHORT_NAME}" width="30" align="center">
				<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:itemView('{SUBJECT_ID}','{MEETING_ID}','{COMPANY_ID}');void(0);&quot;>{SUBJECT_NAME}</a>" width="50" align="center">
				<col caption="会议名称" field="{MEETING_NAME}" width="50" align="center">
				<col caption="会议时间" field="{MEETING_TIME}" width="30" align="center">
				<col caption="是否通过" field="[=formatStatus('{PASS_FLAG}')]" width="20" align="center">
				<col caption="会议决议结果" field="<a style='color:blue' title='{subjectResult}' href='#'>会议决议结果</a>" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/planning/central_subject_list.js"></script>
	<script type="text/javascript">
		function formatStatus(code){
		    if(code=='1'){
		    	return '通过';
		    }else if(code=='2'){
		    	return '缓议';
		    }return '否决';
		}
		loadingItemType();
		getAutoCount();
	</script>
</html>