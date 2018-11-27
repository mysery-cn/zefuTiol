<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中央企业“三重一大”决策和运行应用系统</title>
	<link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
</head>
<body>
<div class="content_main">
<table>
<tr>
	<td class="td-content_main_left">
		<input type="hidden" name="industryID" id="industryID">
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title"><p class="industry-choose">行业选择</p></div>
			<div class="industry_contents">
				<a class="industry_contents_cur" onclick="showView(this,'')">全部</a>
			</div>
		</div>
	</td>
	<td style="vertical-align:top;">
		<div class="content_main_right" style="margin-left:10px">
	<table>
	<tr>
	<td width="50%" style="padding-right: 20px;">
		<div class="list_all">
		<div class="list_title"><p>会议分类占比</p></div>
		<div class="chart_img_gai" style="height: 200px;width: 95%;margin: 0 auto;" id="metcla"></div>
		</div>
	</td>
	<td width="50%">
		<div class="list_all">
		<div class="list_title"><p>会议分类汇总</p></div>
		<div class="chart_img_gai" style="height: 200px;width: 95%;margin: 0 auto;" id="methz"></div>
		</div>
	</td>
	</tr>
	<tr>
	<td colspan="2">		
	<div class="list_all" style="width:100%;">
		<div class="list_title">
		<p style="font-size:16px;">会议分类列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">显示筛选</a>
		</div>
		<div id="search" class="list_search" style="display:none;">
		<table>
		<tr>
		<td style="width:100px"><p>企业名称：</p></td><td style="width:220px">
			<input type="text" id="companyName" placeholder="请输入企业名称"></td>
		<td style="width:100px;">
			<div class="btn_submit"><a onclick="queryData()">查询</a></div></td><td style="width:100px">
			<div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td><td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true"  id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/meeting/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}');void(0);&quot;>{COMPANY_SHORT_NAME}</a>" width="90" align="center">
				<col caption="党委会" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId0}');void(0);&quot;>{typeCode0}</a>" width="50" align="center">
				<col caption="党组会" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId1}');void(0);&quot;>{typeCode1}</a>" width="50" align="center">
				<col caption="董事会" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId2}');void(0);&quot;>{typeCode2}</a>" width="50" align="center">
				<col caption="总经理办公会" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId3}');void(0);&quot;>{typeCode3}</a>" width="50" align="center">
				<col caption="总裁办公会" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId4}');void(0);&quot;>{typeCode4}</a>" width="50" align="center">
				<col caption="股东会" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId5}');void(0);&quot;>{typeCode5}</a>" width="50" align="center">
			</table>
		</div>
		</div>
	</td>
	</tr>
	</table>
</div>
</td>
</tr>
</table>	
</div>
</body>
	<script src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/meeting_classify_list.js"></script>
	<script type="text/javascript">
		loadingIndustry();
		loadingView();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>