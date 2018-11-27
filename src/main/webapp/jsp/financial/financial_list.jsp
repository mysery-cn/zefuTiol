<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<table>
	<tr>
	<td style="vertical-align:top;width: 150px;" >
		<input type="hidden" name="industryId" id="industryId">
		<div class="meetting_main_left" style="width: 150px;" id="treeDiv">
		<div class="industry_title"><p class="industry-choose">行业选择</p></div>
		<div class="industry_contents"><a class="industry_contents_cur" onclick="showView(this,'')">全部</a></div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td style="vertical-align:top;">
		<div class="content_main_right">
	<table>
	<tr>
	<td width="50%" style="padding-right: 12px;">
		<div class="list_all">
		<div class="list_title"><p>年度大额度资金运作事项数量</p></div>
		<div class="chart_img_gai" style="height: 200px;width: 95%;margin: 0 auto;" id="methz"></div>
		</div>
	</td>
	<td width="50%">
		<div class="list_all">
		<div class="list_title"><p>大额度资金运作议题情况</p></div>
		<div class="chart_img_gai" style="height: 200px;width: 95%;margin: 0 auto;" id="metcla"></div>
		<div id="imgno" style="height: 258px;width: 95%;margin: 0 auto;display: none;text-align:center;"></div>
		</div>
	</td>
	</tr>
	<tr>
	<td colspan="2">		
	<div class="list_all">
		<div class="list_title"><p>所有列表</p>
			<a onclick="showSearch()" class="backgroundimg1" id="showSearch">显示筛选</a>
			<a onclick="hideSearch()" class="backgroundimg2" style="display: none;" id="hideSearch">隐藏筛选</a>
		</div>
		<div class="list_search" style="display: none;" id="searchDetail">
		<table>
			<tr>
			<td style="width:100px"><p>企业名称：</p></td><td style="width:220px">
			<input id="companyName" type="text" placeholder="请输入企业名称"></td>
			<td style="width:100px;"><div class="btn_submit"><a onclick="queryData()">查询</a></div></td><td style="width:100px">
			<div class="btn_reset"><a onclick="resetSearch()">重置</a></div></td><td>&nbsp;</td>
		</table>
		</div>
		<hr/>
		<div class="list_main_content">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/item/queryItemSubjectByPage.action?currentPage={page}&pageSize={pageSize}"
                   EmptyInfo="没有记录" width="100%" >
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','');void(0);&quot;>{COMPANY_SHORT_NAME}</a>"" width="30" align="center">
				<col caption="总数" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','');void(0);&quot;>{SUMS}</a>" width="30" align="center">
				<col caption="年度预算内大额度资金调度和使用" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId0}');void(0);&quot;>{TYPECODE0}</a>" width="50" align="center">
				<col caption="超预算的资金调动和使用" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId1}');void(0);&quot;>{TYPECODE1}</a>" width="50" align="center">
				<col caption="对外大额捐赠、赞助" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId2}');void(0);&quot;>{TYPECODE2}</a>" width="30" align="center">
				<col caption="其他大额度资金运作事项" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{typeId3}');void(0);&quot;>{TYPECODE3}</a>" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/financial/financial_list.js"></script>
	<script type="text/javascript">
		loadingIndustry();
		loadingView();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>