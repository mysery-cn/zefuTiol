<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <%@ include file="/common/inc_head.jsp"%>
    <title>中央企业“三重一大”决策和运行应用系统</title>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script>
		var hostName = "${hostName}";
		var redirectToStr = "${redirectTo}";
	</script>
	<link type="text/css" href="<%=SYSURL_static%>/css/index.css" rel="stylesheet" />
<style>
.p-detail{
position:relative;
text-align: center;
}
</style>
</head>
<body>
<div class="content_main">
	<div class="list_all">
	<div class="charts_legal">
		<table>
			<tr style="height:100px;">
				<td style="vertical-align: middle;">
				    <input name="legalType" type="hidden" value="1"/>
				    <div class="charts_legal">
				    <div class="charts_legal_img">
				        <div id="container_1"class="charts_legal_div"></div>
				    </div>
				    </div>
				</td>
				<td style="vertical-align: middle;">
				    <input name="legalType" type="hidden" value="2"/>
				    <div class="charts_legal">
				    <div class="charts_legal_img">
				        <div id="container_2" class="charts_legal_div"></div>
				    </div>
				    </div>
				</td>
				<td style="vertical-align: middle;">
				    <input name="legalType" type="hidden" value="3"/>
				    <div class="charts_legal">
				    <div class="charts_legal_img">
				        <div id="container_3" class="charts_legal_div"></div>
				    </div>
				    </div>
				</td>
				<td style="vertical-align: middle;">
				    <input name="legalType" type="hidden" value="4"/>
				    <div class="charts_legal">
				    <div class="charts_legal_img">
				        <div id="container_4" class="charts_legal_div"></div>
				    </div>
				    </div>
				</td>
				<td style="vertical-align: middle;">
				    <input name="legalType" type="hidden" value="5"/>
				    <div class="charts_legal">
				    <div class="charts_legal_img">
				        <div id="container_5" class="charts_legal_div"></div>
				    </div>
				    </div>
				</td>
				<td style="vertical-align: middle;">
				    <input name="legalType" type="hidden" value="6"/>
				    <div class="charts_legal">
				    <div class="charts_legal_img">
				        <div id="container_6" class="charts_legal_div" ></div>
				    </div>
				    </div>
				</td>
			</tr>
			<tr>
				<td class="p-detail"><p>党委（党组）会</p><p>总法律顾问列席占比</p></td>
				<td class="p-detail"><p>董事会</p><p>总法律顾问列席情况</p></td>
				<td class="p-detail"><p>党委（党组）会文件</p><p>合法合规审查情况</p></td>
				<td class="p-detail"><p>报国资委事项</p><p>法律意见书出具情况</p></td>
				<td class="p-detail"><p>重大决策</p><p>合法合规性审查情况</p></td>
				<td class="p-detail"><p>境外非主业投资项目</p><p>法律顾问参与情况</p></td>
			</tr>
		</table>
	</div>
	</div>
	<div class="list_all">
		<div class="list_title"><p>所有列表</p><a id="showHide" class="backgroundimg1" onclick="showHideSearch()">显示筛选</a></div>
		<div id="search"  class="list_search" style="display:none;">
		<table>
		<tr>
		<td width="100px;"><p>企业名称：</p></td>
		<td width="200px;"><input id="companyName" type="text" placeholder="请输入企业名称"></td>
		<td width="100px;"><p>年度：</p></td>
		<td width="200px;"><div class="select_diy">
       		<select id="year" name="year">
       			<option value="">--请选择--</option>
    		</select>
    		</div></td>
		<td width="100px;"><div class="btn_submit" onclick="queryData();"><a>查询</a></div></td><td width="100px;"><div class="btn_reset" onclick="resetAll();"><a>重置</a></div></td>
		<td><div class="w12"></div></td>
		</tr>
		
		</table>
		</div>
		
		<hr/>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
                   EmptyInfo="没有记录" width="100%"
					URLData="{contextPath}/statistics/legislation/listLegislationStatistics.action?currentPage={page}&pageSize={pageSize}">
					<col caption="序号" field="{RN}" width="30" align="center">
					<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:viewLegislationOption('1','{companyId}','{companyName}');&quot;>{companyShortName}</a>" width="50" align="center">
					<col caption="党委（党组）会" field="<a style='color:blue' href=&quot;javascript:viewLegislationOption('1','{companyId}','{companyName}');&quot;>{cc}</a>" width="100" align="center" >
					<col caption="董事会" field="<a style='color:blue' href=&quot;javascript:viewLegislationOption('2','{companyId}','{companyName}');&quot;>{dc}</a>" width="100" align="center">
					<col caption="党委（党组）会文件" field="<a style='color:blue' href=&quot;javascript:viewLegislationOption('3','{companyId}','{companyName}');&quot;>{cf}</a>" width="100" align="center">
					<col caption="报国资委事项" field="<a style='color:blue' href=&quot;javascript:viewLegislationOption('4','{companyId}','{companyName}');&quot;>{ro}</a>" width="100" align="center"  >
					<col caption="重大决策事项" field="<a style='color:blue' href=&quot;javascript:viewLegislationOption('5','{companyId}','{companyName}');&quot;>{er}</a>" width="100" align="center"  >
					<col caption="境外非主业投资项目" field="<a style='color:blue' href=&quot;javascript:viewLegislationOption('6','{companyId}','{companyName}');&quot;>{op}</a>" width="100" align="center"  >
				</table>
		</div>	
	</div>
	
</div>
</body>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/legislation/legislation_statistics_list.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript">
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>