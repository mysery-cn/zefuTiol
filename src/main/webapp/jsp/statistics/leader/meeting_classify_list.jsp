<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
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
	<tr>
	<td style="vertical-align:top;width: 150px;" >
		<input type="hidden" name="industryId" id="industryId">
		<div class="meetting_main_left" style="width: 150px;" id="treeDiv">
		<div class="industry_title"><p class="industry-choose">行业选择</p></div>
		<div class="industry_contents"><a class="industry_contents_cur" onclick="showView(this,'')">全部</a></div>
		</div>
	</td>
	<td style="vertical-align:top;">
		<div class="content_main_right" style="margin-left:10px">
	<table>
	<tr>
	<td width="45%" style="padding-right: 20px;">
		<div class="list_all">
		<div class="list_title"><p>会议分类占比</p></div>
		<div class="chart_img_gai" style="height: 200px;width: 95%;margin: 0 auto;" id="metcla"></div>
		<div id="imgno" style="height: 258px;width: 95%;margin: 0 auto;display: none;text-align:center;"></div>
		</div>
	</td>
	<td width="55%">
		<div class="list_all">
		<div class="list_title"><p>会议分类汇总</p></div>
		<div class="chart_img_gai" style="height: 200px;width: 95%;margin: 0 auto;" id="methz"></div>
		</div>
	</td>
	</tr>
	<tr>
	<td colspan="2">
	<div class="list_all">
		<div class="list_title"><p>会议分类列表</p>
			<a onclick="showSearch()" class="backgroundimg1" style="display: none;" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" class="backgroundimg2" id="hideSearch">隐藏筛选</a>
		</div>
		<div class="list_search" id="searchDetail">
		<table>
		<tr>
            <td style="width:100px"><p>企业名称：</p></td><td style="width:220px">
                <input type="text" id="companyName" placeholder="请输入企业名称"></td>
            <td style="width:100px;">
                <div class="btn_submit"><a onclick="queryData()">查询</a></div>
            </td>
            <td style="width:100px">
                <div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td><td>&nbsp;
            </td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/meeting/queryMeetingByPage.action?currentPage={page}&pageSize={pageSize}"
                   EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','');void(0);&quot;>{COMPANY_SHORT_NAME}</a>" width="90" align="center">
				<col caption="总数" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','');void(0);&quot;>{SUMS}</a>" width="30" align="center">
				<c:forEach items="${meetingType}" varStatus="i" var="item" > 
					<col caption="${item.TYPE_NAME}" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','${item.TYPE_ID}');void(0);&quot;>{TYPECODE${i.index}}</a>" width="50" align="center">
				</c:forEach>
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/meeting_classify_list.js"></script>
	<script type="text/javascript">
		loadingIndustry();
		loadingView();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>