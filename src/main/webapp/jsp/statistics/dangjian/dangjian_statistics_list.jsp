<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<%@ include file="/common/inc_head.jsp"%>
<title>中央企业“三重一大”决策和运行应用系统</title>
<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script>
		var hostName = "${hostName}";
		var redirectToStr = "${redirectTo}";
	</script>
<link type="text/css" href="<%=SYSURL_static%>/css/index.css"
	rel="stylesheet" />
<style type="text/css">
#totalNum:hover {
	cursor: pointer;
	text-decoration: underline;
}

#exceptionNum:hover {
	cursor: pointer;
	text-decoration: underline;
}
</style>
</head>
<body>

	<div class="content_main">
		<div class="list_all" style="width:100%;padding-bottom:24px;">
			<div class="list_title">
				<p style="font-size:16px;">所有列表</p>
				<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">显示筛选</a>
			</div>
			<div id="search" class="list_search" style="display:none;">
				<table>
					<tr>
						<td width="100px"><p>企业名称：</p></td>
						<td width="220px"><input id="companyName" type="text"
							placeholder="请输入企业名称"></td>
						<td width="100px"><p>党组织前置：</p></td>
						<td width="220px">
							<div class="select_diy">
								<select id="preFlag" name="">
									<option value="">全部</option>
									<option value="1">是</option>
									<option value="2">待确认</option>
									<option value="0">否</option>
								</select>
							</div>
						</td>
						<td width="100px"><p>会议类型：</p></td>
						<td width="220px">
							<div class="select_diy">
								<select id="meetingType">
									<option value="">全部</option>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td width="100px;"><p>时间区间：</p></td>
						<td width="220px">
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
											style="width: 110px;" onchange="TimeJudge(this)" type="text"
											readonly="readonly" value="" oper=">" fieldname="opeDate"></td>
										<td>至</td>
										<td style="width: 110px"><input name="opeDate"
											id="endTime" class="Input_DateTime input"
											style="width: 110px;" onchange="" type="text"
											readonly="readonly" value="" oper="<" fieldname="opeDate"></td>
									</tr>
								</tbody>
							</table>
						</td>
						<td><div class="btn_submit" onclick="queryData();">
								<a>查询</a>
							</div></td>
						<td><div class="btn_reset" onclick="resetAll();">
								<a>重置</a>
							</div></td>
					</tr>
				</table>
			</div>
			<hr/>
			<div class="total_num" onclick="queryAllData()">
				<p>议题总数：</p>
				<span id="totalNum"></span>
			</div>
			<div class="no_front" onclick="queryExceptionData()">
				<p>未前置：</p>
				<span id="exceptionNum"></span>
			</div>
			<div class="list_main">
				<table class="Grid" style="text-align:center;" FixHead="true"
					id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
					EmptyInfo="没有记录" width="100%"
					URLData="{contextPath}/subject/queryDangjianSubjectList.action?currentPage={page}&pageSize={pageSize}">
					<col caption="序号" field="{RN}" width="50" align="center">
					<col caption="企业名称" field="{companyShortName}" width="auto"
						align="center">
					<col caption="议题名称" width="auto" align="left"
						field="<a style='color:blue' href=&quot;javascript:viewSubjectDetail('{subjectId}','{meetingId}','{companyId}');&quot;>
					{subjectName}
					</a>" >
					<col caption="会议类型" field="{meetingType}" width="auto" align="center">
					<col caption="会议日期" field="{meetingTime}" width="auto" align="center">
					<col caption="前置程序监控" width="50" align="center"
						field="[=fmtStatus('{exceptionId}','{confirmFlag}')]">
					<col caption="操作" width="auto" align="center"
						field="[=fmtFlag('{exceptionId}','{confirmFlag}')]">
				</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/statistics/dangjian/dangjian_statistics_list.js"></script>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript">
	 /**
 	  * 格式化前置程序状态
 	  */
	function fmtStatus(exceptionId,confirmFlag){
		if(exceptionId==''){
			return "<img src='<%=SYSURL_static%>/images/tiol/ico_pass.png' title='正常'>";
		}else if(confirmFlag==''||confirmFlag=='0'){
			return "<img src='<%=SYSURL_static%>/images/tiol/ico_wait.png' title='待确认'>";
		}else{
			return "<img src='<%=SYSURL_static%>/images/tiol/ico_fail.png' title='异常'>";
		}
	}
	/**
 	  * 格式化是否前置操作
 	  */
	function fmtFlag(exceptionId,confirmFlag){
		if(exceptionId != ''&&(confirmFlag==''||confirmFlag=='0')){
			return "<div class='btn_dang'><a style='font-size:12px;color:blue;margin-right:15px;' href='javascript:refuseException(\""+exceptionId+"\");'>正常</a><a style='font-size:12px;color:blue;magin-left:15px;' href='javascript:confirmException(\""+exceptionId+"\");'>异常</a></div>";
		}else{
			return "";
		}
	}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>