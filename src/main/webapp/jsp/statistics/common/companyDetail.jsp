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
<input type="hidden" name="companyId" id="companyId" value="${companyId}">
<input type="hidden" name="type" id="type" value="${type}">
<div class="title_com"><h1 id="companyName"></h1></div>
<table>
<tr>
<td style="vertical-align:top;"  colspan="3">
	<div>
		<div class="list_all">
		<div class="list_title"><p>企业领导班子</p></div>
		<div class="h20"></div>
		<div class="list_main_com">
			<table id="cmTable">
			
			</table>
		</div>
		<hr/>
		<div class="list_title"><p>制度清单</p></div>
		<div class="h20"></div>
		<div class="list_main">
			<table id="regTable">
				
			</table>
		</div>
	</div>
</div>
</td>
</tr>
<tr>
	<td  style="vertical-align:top;" width="55%" height="380px;">
		<div class="list_all">
		<div class="tab_titl">
		<input type="hidden" name="catalogId" id="catalogId">
		<input type="hidden" name="levelCode" id="levelCode">
		<div class="tab_top_left" id="cataDiv">
		</div>
		<div class="tab_top_right">
			<span>议题总数：</span>
			<a id="quantity" onclick="itemSubjectView()">0</a>
		</div>
		</div>
		<div class="list_main_content">
			<table id="statSubjectTable">
				<tr class="table_tr_b">
					<td style="font-weight: bold;">事项清单</td>
					<td style="font-weight: bold;">议题数</td>
				</tr>
			</table>
		</div>
		</div>
	</td>
	<td width="20px;"></td>
	<td style="vertical-align:top;" height="343px;">
		<div class="list_all">
		<div class="list_title"><p>会议数量统计</p></div>	
			<div class="chart_img_com" id="stat" style="height: 343px;width: 95%;margin: 0 auto;"></div>
			<div id="imgno" style="height: 373px;width: 95%;margin: 0 auto;display: none"></div>
		</div>
	</td>
</tr>
</table>	
</div>
</body>
	<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_inside_list.js"></script>
	<script type="text/javascript">
		getCompanyNameById();
		showCompanyMemberView();
		showRegulationView();
		showCatalogView();
		showMeetingStatView();
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>