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
		<input type="hidden" name="catalogCode" id="catalogCode">
		<input type="hidden" name="levelCode" id="levelCode">
		<div class="meetting_main_left" id="treeDiv">
		<div class="industry_title"><p class="industry-choose">类型</p></div>
		<div class="industry_contents"><a class="industry_contents_cur" onclick="showView(this,'','')">全部类型</a></div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td style="vertical-align:top;">
		<div class="list_all">
		<div class="list_title"><p id="typeName">企业议题列表</p>
			<a onclick="showSearch()" class="backgroundimg1" style="display: none;" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" class="backgroundimg2" id="hideSearch">隐藏筛选</a>
		</div>
		<div class="list_search" id="searchDetail">
		<table>
		<tr>
		<td style="width:100px"><p>议题名称：</p></td><td style="width:235px">
			<input style="width:235px" type="text" id="subjectName" placeholder="请输入议题名称">
		</td>
		 <td style="width:100px"><p>会议决议内容：</p></td>
        <td style="width:235px;">
            <input style="width:235px" type="text" id="subjectResult" placeholder="请输入决议情况内容">
        </td>
        <td style="width:100px;">
			<div class="btn_submit"><a onclick="queryData()">查询</a></div></td><td style="width:100px">
			<div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td><td>&nbsp;</td>
		</tr>
		<tr>
		<td width="100px"><p>时间区间：</p></td>
		<td width="240px">
			<div class="select_diy" style="width:240px">
	       		<select name="times" id="times" onchange="clickTime()">
	       			<option value="">--请选择--</option>
	    		</select>
		    </div>
		</td>
		<td name="timeDiv" style="width:100px;display: none;"><p>查询时间：</p></td>
		<td name="timeDiv" style="display: none;">
			<table style="width: 240px" border="0" cellspacing="0" cellpadding="0">
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
		<div class="total_num"><p>当前议题总数：</p><span id="all">0</span></div><div class="no_front"><p>有权限查看数：</p><span id="have">0</span></div><div class="no_front"><p>无权限查看数：</p><span id="not">0</span></div>
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/subject/querySubjectByPage.action?currentPage={page}&pageSize={pageSize}&companyId=${companyId}&catalogCode=${catalogCode}&levelCode=${levelCode}"
                   EmptyInfo="没有记录" width="100%" >
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="企业名称" field="{COMPANY_SHORT_NAME}" width="30" align="center">
				<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:itemView('{SUBJECT_ID}','{MEETING_ID}','{COMPANY_ID}');void(0);&quot;>{SUBJECT_NAME}</a>" width="50" align="center">
				<col caption="会议名称" field="{MEETING_NAME}" width="50" align="center">
				<col caption="会议日期" field="{MEETING_TIME}" width="50" align="center">
				<col caption="事项类型" field="[=formatStatus('{CATALOG_CODE}')]" width="50" align="center">
				<col caption="材料是否完整" field="{flag}" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/financial/item_subject_list.js"></script>
	<script type="text/javascript">
		function formatStatus(code){
		    code = code.split(",");
		    var html = '';
		    for (var i = 0; i < code.length; i++) {
		    	var flag = true;
		    	var name = formatType(code[i]);
		    	var names = html.split("<br>");
		    	for (var j = 0; j < names.length; j++) {
					if (names[j]==name) {
						flag = false;
					}
				}
		    	if (flag) {
		    		if(i==0){
				    	html = name;
				    } else {
				    	html = html+'<br>'+name;
				    }
				}
			}
			return html;
		}
		function formatType(code){
			code = code.substr(0, 1);
			if(code=='D'){
		    	return '重大决策';
		    }else if(code=='H'){
		    	return '重要人事任免';
		    }else if(code=='P'){
		    	return '重大项目安排';
		    }else if(code=='F'){
		    	return '大额度资金运作';
		    }return '';
		}
		jt._("#levelCode").value = jt.getParam('levelCode')
		loadingCatalogLevel();
		getSubjectAllCount();
		//queryData();
	</script>
</html>