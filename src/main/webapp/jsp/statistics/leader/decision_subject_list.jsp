<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<%@ include file="/common/inc_head.jsp"%>
<title>中央企业“三重一大”决策和运行应用系统</title>
<link rel="stylesheet"
	href="<%=SYSURL_static%>/css/decision-subject-list.css" type="text/css" />
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script>
	var hostName = "${hostName}";
	var redirectToStr = "${redirectTo}";
</script>
<link type="text/css" href="<%=SYSURL_static%>/css/index.css"
	rel="stylesheet" />
</head>
<body>
	<div class="content_main">
		<div class="title_com">
			<h1>${companyName }</h1>
		</div>
		<table>
			<tr>
				<td class="td-content_main_left"><input type="hidden"
					name="statisticsType" id="statisticsType"
					value="${statisticsType }"> <input type="hidden"
					name="objectId" id="objectId" value="${objectId }"> <input
					type="hidden" name="companyId" id="companyId" value="${companyId }">
					<input type="hidden" name="industryId" id="industryId"
					value="${industryId }">
					<div id="treeDiv" class="content_main_left">
						<div class="industry_title">
							<p class="industry-choose">类型</p>
						</div>
						<div class="industry_contents">
							<a class="industry_contents_cur" style="font-size:15px;"
								onclick="reloadSubjectData('','','',this);">全部类型</a>
						</div>
					</div></td>
				<td><div class="w12"></div></td>
				<td style="vertical-align:top;">
					<div class="list_all" style="width:100%;">
						<div class="list_title">
							<p style="font-size:16px;" id="tableTitle">企业议题列表</p>
							<a id="showHide" class="backgroundimg1"
								onclick="showHideSearch();">隐藏筛选</a>
						</div>
						<div id="search" class="list_search">
							<table>
								<tr>
									
									<td style="width:100px"><p>议题名称：</p></td>
									<td style="width:220px"><input id="subjectName"
										type="text" placeholder="请输入议题名称"></td>
									<td style="width:100px"><p>会议名称：</p></td>
									<td style="width:220px"><input id="meetingName"
										type="text" placeholder="请输入会议名称"></td>
                                    <td style="width:100px"><p>会议决议内容：</p></td>
                                    <td style="width:220px">
                                        <input type="text" id="subjectResult" placeholder="请输入决议情况内容">
                                    </td>
                                    <td style="width:100px" name="companySearch"><p>企业名称：</p></td>
                                    <td style="width:220px" name="companySearch"><input
                                            id="companyName" type="text" placeholder="请输入企业名称">
                                    </td>
								</tr>
								<tr>
                                    <td style="width:100px"><p>时间区间：</p></td>
                                    <td style="width:220px">
                                        <div class="select_diy">
                                            <select name="times" id="times" onchange="clickTime()">
                                                <option value="">--请选择--</option>
                                            </select>
                                        </div>
                                    </td>
									<td name="timeDiv" style="width:100px;display: none;"><p>查询时间：</p></td>
									<td name="timeDiv" style="width:220px;display: none;">
										<table style="width: 220px" border="0" cellspacing="0"
											cellpadding="0">
											<tbody>
												<tr>
													<td style="width: 110px"><input name="opeDate"
														id="startTime" class="Input_DateTime input"
														style="width: 110px;" onchange="TimeJudge(this)"
														type="text" readonly="readonly" value="" oper=">"
														fieldname="opeDate"></td>
													<td>至</td>
													<td style="width: 110px"><input name="opeDate"
														id="endTime" class="Input_DateTime input"
														style="width: 110px;" onchange="" type="text"
														readonly="readonly" value="" oper="<" fieldname="opeDate"></td>
												</tr>
											</tbody>
										</table>
									</td>
                                    <td style="width:100px;">
                                        <div class="btn_submit" onclick="queryData()">
                                            <a>查询</a>
                                        </div></td>
                                    <td style="width:100px">
                                        <div class="btn_reset" onclick="resetAll()">
                                            <a>重置</a>
                                        </div>
                                    </td>
								</tr>
							</table>
						</div>
						<hr/>
						<div class="list_main">
						    <div class="total_num"><p>当前会议总数：</p><span id="all">0</span></div><div class="no_front"><p>有权限查看数：</p><span id="have">0</span></div><div class="no_front"><p>无权限查看数：</p><span id="not">0</span></div>
							<table class="Grid" style="text-align:center;" FixHead="true"
								id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
								EmptyInfo="没有记录" width="100%"
								URLData="{contextPath}/statistics/decision/queryDecisionSubjectList.action?currentPage={page}&pageSize={pageSize}&companyId=${companyId }&statisticsType=${statisticsType}&objectId=${objectId}&industryId=${industryId}">
								<col caption="序号" field="{RN}" width="50" align="center">
								<col caption="企业名称" field="{companyName}" width="220"
									align="center">
								<col caption="议题名称" width="" align="left"
									field="<a style='color:blue' href=&quot;javascript:viewSubjectDetail('{subjectId}','{meetingId}','{companyId}');&quot;>
								{subjectName}
								</a>" >
								<col caption="会议名称" width="auto" align="left"
									field="<a style='color:blue' href=&quot;javascript:viewMeetingDetail('{meetingId}','{companyId}');&quot;>
								{meetingName}
								</a>" >
								<col caption="会议日期" field="{meetingTime}" width="200" align="center">
								<col caption="会议类型" field="{meetingType}" width="200" align="center">
                                <col caption="会议决议结果" field="<a style='color:blue' title='{subjectResult}' href='#'>会议决议结果</a>" width="150" align="center">
                            </table>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/statistics/leader/decision_subject_list.js">
	</script>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>