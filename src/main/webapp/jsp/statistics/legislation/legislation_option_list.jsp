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
	src="<%=SYSURL_static%>/js/statistics/legislation/legislation_option_list.js"></script>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
<script>
	var hostName = "${hostName}";
	var redirectToStr = "${redirectTo}";
</script>
<link type="text/css" href="<%=SYSURL_static%>/css/index.css"
	rel="stylesheet" />
</head>
<body>
	<div class="container">
		<div class="content_main">
			<div class="title_com">
				<h1>${companyName }</h1>
			</div>
			<table>
				<tr>
					<td class="td_content_main_left"><input type="hidden"
						name="optionType" id="optionType" value="${optionType }">
						<input type="hidden" name="companyId" id="companyId"
						value="${companyId }">
						<div id="treeDiv" class="content_main_left">
							<div class="industry_title">
								<p class="industry-choose">类型</p>
							</div>
							<div class="industry_contents">
								<a onclick="reloadOptionData('1',this);">党委（党组）会</a>
							</div>
							<div class="industry_contents">
								<a onclick="reloadOptionData('2',this);">董事会</a>
							</div>
							<div class="industry_contents">
								<a onclick="reloadOptionData('3',this);">党委（党组）会文件</a>
							</div>
							<div class="industry_contents">
								<a onclick="reloadOptionData('4',this);">报国资委事项</a>
							</div>
							<div class="industry_contents">
								<a onclick="reloadOptionData('5',this);">重大决策</a>
							</div>
							<div class="industry_contents">
								<a onclick="reloadOptionData('6',this);">境外非主业投资项目</a>
							</div>
						</div></td>
					<td><div class="w12"></div></td>
					<td>
						<div class="list_all">
							<div class="list_title">
								<p style="font-size:16px;" id="tableTitle">所有列表</p>
								<a id="showHide" class="backgroundimg1"
									onclick="showHideSearch()">隐藏筛选</a>
							</div>
							<div id="search" class="list_search">
								<table>
									<tr>
										<td name="regulationSearch" style="width:100px"><p>制度名称：</p></td>
										<td name="regulationSearch" style="width:220px"><input
											id="regulationName" type="text" placeholder="请输入制度名称"></td>
										<td name="subjectSearch" style="width:100px"><p>议题名称：</p></td>
										<td name="subjectSearch" style="width:220px"><input
											id="subjectName" type="text" placeholder="请输入议题名称"></td>
										<td name="subjectSearch" style="width:100px"><p>会议名称：</p></td>
										<td name="subjectSearch" style="width:220px"><input
											id="meetingName" type="text" placeholder="请输入会议名称"></td>
										<td name="counselSearch" style="width:220px"><p>是否总法律顾问出席：</p></td>
										<td name="counselSearch" style="width:220px">
											<div class="select_diy">
												<select id="counselFlag" name="counselFlag">
													<option value="">--全部--</option>
													<option value="1">是</option>
													<option value="0">否</option>
												</select>
											</div>
										</td>
										<td name="auditSearch" style="width:100px"><p>合法合规检查：</p></td>
										<td name="auditSearch" style="width:220px">
											<div class="select_diy">
												<select id="auditFlag" name="auditFlag">
													<option value="">--全部--</option>
													<option value="1">是</option>
													<option value="0">否</option>
												</select>
											</div>
										</td>
										<td name="opinionSearch" style="width:100px"><p>法律意见书：</p></td>
										<td name="opinionSearch" style="width:220px">
											<div class="select_diy">
												<select id="opinionFlag" name="opinionFlag">
													<option value="">--全部--</option>
													<option value="1">是</option>
													<option value="0">否</option>
												</select>
											</div>
										</td>
										<td name="auditSearch" style="width:100px;"><div class="btn_submit"
												onclick="queryData()">
												<a>查询</a>
											</div></td>
										<td name="auditSearch" style="width:100px"><div class="btn_reset"
												onclick="resetAll()">
												<a>重置</a>
											</div></td>
									</tr>
									<tr id="timeSearch">
									    <td name="subjectSearch" style="width:100px"><p>时间区间：</p></td>
										<td name="subjectSearch" style="width:220px">
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
										<td name="subjectSearch" style="width:100px;"><div class="btn_submit"
												onclick="queryData()">
												<a>查询</a>
											</div></td>
										<td name="subjectSearch" style="width:100px"><div class="btn_reset"
												onclick="resetAll()">
												<a>重置</a>
											</div></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</div>
							<hr/>
							<div id="viewNum" ><div class="total_num"><p>当前议题总数：</p><span id="all">0</span></div>
                            <div class="no_front"><p>有权限查看数：</p><span id="have">0</span></div><div class="no_front"><p>无权限查看数：</p><span id="not">0</span></div></div>
							<div id="subject1_table" class="list_main">
								<table class="Grid" style="text-align:center;" FixHead="true"
									id="subject1_List" border="0" cellspacing="0" cellpadding="0"
									EmptyInfo="没有记录" width="100%">
									<col caption="序号" field="{RN}" width="30" align="center">
									<col caption="企业名称" field="{companyName}" width="50"
										align="center">
									<col caption="议题名称" width="100" align="left"
										field="<a style='color:blue' href=&quot;javascript:viewSubjectDetail('{subjectId}','{meetingId}','{companyId}');&quot;>
									{subjectName}
									</a>" >
									<col caption="会议名称" field="{meetingName}" width="100"
										align="center">
									<col caption="会议日期" field="{meetingTime}" width="50"
										align="center">
									<col caption="是否总法律顾问列席" field="{COUNSELNUM?0|否|ELSE|是}"
										width="30" align="center">
								</table>
							</div>
							<div id="subject3_table" class="list_main">
								<table class="Grid" style="text-align:center;" FixHead="true"
									id="subject3_List" border="0" cellspacing="0" cellpadding="0"
									EmptyInfo="没有记录" width="100%">
									<col caption="序号" field="{RN}" width="30" align="center">
									<col caption="企业名称" field="{companyName}" width="50"
										align="center">
									<col caption="议题名称" width="100" align="left"
										field="<a style='color:blue' href=&quot;javascript:viewSubjectDetail('{subjectId}','{meetingId}','{companyId}');&quot;>
									{subjectName}
									</a>" >
									<col caption="会议名称" field="{meetingName}" width="100"
										align="center">
									<col caption="会议日期" field="{meetingTime}" width="50"
										align="center">
									<col caption="是否经过合法合规检查"
										field="[=fmtLegalStatus('{LEGALFLAG}','{COUNSELNUM}','{OPINIONNUM}')]"
										width="30" align="center">
								</table>
							</div>
							<div id="subject2_table" class="list_main">
								<table class="Grid" style="text-align:center;" FixHead="true"
									id="subject2_List" border="0" cellspacing="0" cellpadding="0"
									EmptyInfo="没有记录" width="100%" >
									<col caption="序号" field="{RN}" width="30" align="center">
									<col caption="企业名称" field="{companyName}" width="50"
										align="center">
									<col caption="议题名称" width="100" align="left"
										field="<a style='color:blue' href=&quot;javascript:viewSubjectDetail('{subjectId}','{meetingId}','{companyId}');&quot;>
									{subjectName}
									</a>" >
									<col caption="会议名称" field="{meetingName}" width="100"
										align="center">
									<col caption="会议日期" field="{meetingTime}" width="50"
										align="center">
									<col caption="是否出具法律意见书" field="{OPINIONNUM?0|否|ELSE|是}"
										width="30" align="center">
								</table>
							</div>
							<div id="regulation_table" class="list_main">
								<table class="Grid" style="text-align:center;" FixHead="true"
									id="regulation_List" border="0" cellspacing="0" cellpadding="0"
									EmptyInfo="没有记录" width="100%" >
									<col caption="序号" field="{RN}" width="30" align="center">
									<col caption="企业名称" field="{companyName}" width="50"
										align="center">
									<col caption="制度名称"
										field="<a style='color:blue' href=&quot;javascript:viewRegulationDetail('{regulationId}');&quot;>
									{regulationName}
									</a>" width="100" align="left" >
									<col caption="审批日期" field="{approvalDate}" width="50"
										align="center">
									<col caption="生效日期" field="{effectiveDate}" width="50"
										align="center">
									<col caption="是否经过合法合规检查" field="{auditFlag?1|是|ELSE|否}"
										width="30" align="center">
									<col caption="附件"
										field="<a style='color:blue' href=&quot;javascript:showFileView('{fileId}');&quot;>
									正式文件
									</a>" width="50" align="center">
								</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>