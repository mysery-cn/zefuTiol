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
<input type="hidden" name="companyId" id="companyId" value="${companyId}">
<input type="hidden" name="search" id="search" value="${search}">
<div class="title_com"><h1 id="companyName"></h1></div>
<table>
<tr>
	<td class="td-content_main_left">
		<input type="hidden" name="catalogCode" id="catalogCode">
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title"><p class="industry-choose">事项类型</p></div>
			<div class="industry_contents">
				<a class="industry_contents_cur" id="all" onclick="reloadSubjectData('')">全部</a>
			</div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td  style="vertical-align:top;" >
	<div class="content_main_right">
		<div class="list_all">
			<div class="list_title"><p>企业议题列表</p>
			</div>
			<div  class="list_search">
				<table>
				<tr>
                    <td style="width:100px"><p>议题名称：</p></td>
                    <td style="width:220px">
                        <input type="text" id="subjectName" placeholder="请输入议题名称">
                    </td>
                    <td style="width:100px"><p>会议决议内容：</p></td>
                    <td style="width:220px">
                        <input type="text" id="subjectResult" placeholder="请输入决议情况内容">
                    </td>
                    <td style="width:100px;">
                        <div class="btn_submit" onclick="queryData()"><a>查询</a></div>
                    </td>
                    <td style="width:100px">
                        <div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div>
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
				</tr>
				</table>
			</div>
			<hr/>
			<div class="list_main">
                <div class="total_num"><p>当前议题总数：</p><span id="total"></span></div>
                <div class="no_front"><p>有权限查看数：</p><span id="yNumber"></span></div>
                <div class="no_front"><p>无权限查看数：</p><span id="nNumber"></span></div>
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
					URLData="{contextPath}/statistics/subject/queryMeetingTypeSubjectListByPage.action?currentPage={page}&pageSize={pageSize}&companyID=${companyId}&search=${search}"
                       EmptyInfo="没有记录" width="100%" >
					<col caption="序号" field="{ROWNUM}" width="50" align="center">
					<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:subjectDetail('{subjectId}','{meetingId}','{companyId}');void(0);&quot;>{subjectName}</a>" width="auto" align="center">
					<col caption="会议名称" field="{meetingName}" width="auto" align="center">
					<col caption="会议日期" field="{createDate}" width="auto" align="center">
					<col caption="事项类型" field="{catalogName}" width="auto" align="center">
                    <col caption="会议决议结果" field="<a style='color:blue' title='{subjectResult}' href='#'>会议决议结果</a>" width="auto" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/tour/tour_subject_non.js"></script>
	<script type="text/javascript">
		//加载权限查看数量
		loadUserRoleMessage();
		//加载事项类型
		loadingCatalogType();
		//获取企业名称
		queryCompanyName();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>