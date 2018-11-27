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

<body style="height:800px;">
<div class="content_main">
<table>
	<tr>
	<td class="td-content_main_left" >
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title"><p class="industry-choose" >行业选择</p></div>
			<div class="industry_contents">
				<a class="industry_contents_cur" onclick="reloadChart(this,'')">全部</a>
			</div>
		</div>
	</td>
	<td>
		<div class="w12">
		</div>
	</td>
	<td style="vertical-align:top;">
		<div class="content_main_right">
			<div class="list_all">
			<table>
			<tr>
			<td style="border-right:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf;width: 33%;">
			<div class="charts_wei_a">
				<div class="list_title" ><p>贯彻执行党和国家决策部署的重大措施</p></div>
				<div class="chart_img_wei">
					<!-- 柱形图 -->
					<div id="container_1" style="height: 220px;width: 95%;margin: 0 auto;text-align:center;"></div>
				</div>
			</div>
			</td>
			<td style="border-right:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf; width: 33%;">
			<div class="charts_wei_a">
				<div class="list_title"><p>制度分类汇总</p></div>
				<div class="chart_img_wei">
					<!-- 饼图 -->
					<div id="container_2" style="height: 220px;width: 95%;margin: 0 auto;"></div>
					<div id="container_2_2" style="height: 220px;width: 95%;margin: 0 auto;display: none;text-align:center;"></div>
				</div>
			</div>
			</td>
			<td style="border-bottom:1px solid #dfdfdf;width: 33%;">
			<div class="charts_wei_a"> 
				<div class="list_title"><p>事项清单汇总</p></div>
				<div class="chart_img_wei">
					<div id="container_3" style="height: 220px;width: 95%;margin: 0 auto;text-align:center;"></div>
				</div>
			</div>
			</td>
			</tr>
			<tr>
			<td style="border-right:1px solid #dfdfdf;">
			<div class="charts_wei_a">
				<div class="list_title"><p>会议分类汇总</p></div>
				<div class="chart_img_wei">
					<div id="container_4" style="height: 220px;width: 95%;margin: 0 auto;"></div>
					<div id="container_4_4" style="height: 220px;width: 95%;margin: 0 auto;display: none;text-align:center;"></div>
				</div>
			</div>
			</td>
			<td style="border-right:1px solid #dfdfdf;">
			<div class="charts_wei_a"> 
				<div class="list_title"><p>决策议题情况</p></div>
				<div class="chart_img_wei">
					<div id="container_5" style="height: 220px;width: 95%;margin: 0 auto;text-align:center;"></div>
				</div>
			</div>
			</td>
			<td style="border-right:1px solid #dfdfdf;">
			<div class="charts_wei_a"> 
				<div class="list_title"><p>异常情况汇总</p></div>
				<div class="chart_img_wei">
					<div id="container_6" style="height: 220px;width: 95%;margin: 0 auto;text-align:center;"></div>
				</div>
			</div>
			</td>
			</tr>
			</table>
			</div>
		<div class="list_all" style="width:100%;">
		<div class="list_title">
		<p style="font-size:16px;">所有列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">显示筛选</a>
		</div>
		<div id="search" class="list_search" style="display:none;"> 
		<table style="width:540px;">
		<tr>
		<td width="110px;"><p>企业名称：</p></td><td width="240px;">
		<input type="text" id="companyName" placeholder="请输入企业名称"></td>
		<td width="90px;">
			<div class="btn_submit" ><a onclick="queryData()">查询</a></div>
		</td>
		<td width="90px;">
			<div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div>
		</td>
		<td></td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/statistics/company/querPageList.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{rn}" width="10" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:redirectComapnyView('{companyId}');void(0);&quot;>{companyName}</a>" width="90" align="center">
				<col caption="规章制度" field="<a style='color:blue' href=&quot;javascript:redirectRegulationView('{companyId}');void(0);&quot;>{regulationQuantity}</a>" width="50" align="center">
				<col caption="决策事项" field="<a style='color:blue' href=&quot;javascript:redirectItemView('{companyId}');void(0);&quot;>{itemQuantity}</a>" width="50" align="center">
				<col caption="决策议题" field="<a style='color:blue' href=&quot;javascript:redirectSubjectView('{companyId}');void(0);&quot;>{subjectQuantity}</a>" width="50" align="center">
				<col caption="决策会议" field="<a style='color:blue' href=&quot;javascript:redirectMeetingView('{companyId}');void(0);&quot;>{meetingQuantity}</a>" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_statistics.js"></script>
	<script type="text/javascript">
	//定义全局变量：行业ID
		var instFlag = "";
		var decisionTypeArray;
		var decisionIdArray;
	</script>
	<script type="text/javascript">
		jQuery(function($) {
			//列表偏移问题
			$("#rn").width(60);
			$("#companyId").width(180);
			$("#regulationQuantity").width(118);
			$("#itemQuantity").width(120);
			$("#meetingQuantity").width(118);
			$("#subjectQuantity").width(116);
			//生成行业树形
			$.ajax({
				url : "/statistics/industry/queryList.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					showTree(result);
				}
			});
			//展示 贯彻执行党和国家决策部署的重大措施 柱形图
			 $.ajax({
				url : "/statistics/decision/queryList.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					statisticsNames = result.data[0].nameArray;
					meeting_quantities = result.data[0].meetingArray;
					subject_quantities = result.data[0].subjectArray;
					decisionTypeArray = result.data[0].typeArray;
					decisionIdArray = result.data[0].idArray;
					showDecision(statisticsNames,meeting_quantities,subject_quantities);
				}
			});
			//展示 制度分类汇总 饼图
			$.ajax({
				url : "/statistics/regulation/queryRegulationList.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					names = result.data[0].nameArray;
					values = result.data[0].quantityArray;
					if(values==""){
						$("#container_2").html("<img id='img2' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img2").css("margin-top","55px");
					}else{
						showRegulation(names,values);
					}
				}
			});
			//展示 事项清单汇总 柱形图
			$.ajax({
				url : "/statistics/item/queryItemDetail.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					var names = result.data[0].name;
					var values = result.data[0].value;
					var number = 0;
                    for (var i = 0; i < values.length; i++) {
                        number = number + parseInt(values[i]);
                    }
                    if(number == 0){
                        $("#container_3").html("<img id='img3' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
                        $("#img3").css("margin-top","55px");
                    }else{
                        showItem(names,values);
                    }
				}
			});
			//展示 会议分类汇总 饼图
			$.ajax({
				url : "/statistics/meeting/queryMeetingDetail.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					names = result.data[0].name;
					values = result.data[0].value;
					if(values==""){
						$("#container_4").html("<img id='img4' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img4").css("margin-top","55px");
					}else{
						showMeeting(names,values);
					}
				}
			});
			//展示决策议题
			 $.ajax({
				url : "/statistics/subject/getStatisticsSubjectChartData.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					var series = result.data[0].series;
					var xAxisData = result.data[0].xAxisData;
					var legendData = result.data[0].legendData;
					if(legendData==undefined){
					    $("#container_5").html("<img id='img5' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img5").css("margin-top","55px");
					}else{
					    showSubject(legendData,xAxisData,series);
					}
				}
			});
			function showTree(result){
		        for(var i=0;i<result.data[0].nameArray.length;i++){
		        	$("#treeDiv").append("<div class='industry_contents' style=''> <a onclick=reloadChart(this,'"+result.data[0].idArray[i]+"');>"+result.data[0].nameArray[i]+"</a></div>");
		        }
			}
			//展示制度议题异常图表
			showItem_6("");
		});
		function reloadChart(obj,id){
			$("#treeDiv a").removeClass();
			$(obj).addClass("industry_contents_cur");
			//展示 贯彻执行党和国家决策部署的重大措施 柱形图
			 $.ajax({
				url : "/statistics/decision/queryList.action?industryId="+id,
				type : "POST",
				async: false, 
		        success: function (result) {
					statisticsNames = result.data[0].nameArray;
					meeting_quantities = result.data[0].meetingArray;
					subject_quantities = result.data[0].subjectArray;
					showDecision(statisticsNames,meeting_quantities,subject_quantities);
				}
			});
			//展示 制度分类汇总 饼图
			$.ajax({
				url : "/statistics/regulation/queryRegulationList.action?industryId="+id,
				type : "POST",
				async: false, 
		        success: function (result) {
					var names1 = result.data[0].nameArray;
					var values1 = result.data[0].quantityArray;
					if(values1==""){
						$("#container_2").css("display","none");
						$("#container_2_2").css("display","block");
						$("#container_2_2").html("<img id='img2' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img2").css("margin-top","55px");
					}else{
						$("#container_2_2").css("display","none");
						$("#container_2").css("display","block");
						showRegulation(names1,values1);
					}
				}
			});
			//展示 事项清单汇总 柱形图
			$.ajax({
				url : "/statistics/item/queryItemDetail.action?industryID="+id,
				type : "POST",
				async: false, 
		        success: function (result) {
					var itemNames = result.data[0].name;
					var itemValues = result.data[0].value;
					showItem(itemNames,itemValues);
				}
			});
			//展示 会议分类汇总 饼图
			$.ajax({
				url : "/statistics/meeting/queryMeetingDetail.action?industryID="+id,
				type : "POST",
				async: false, 
		        success: function (result) {
					var meetingNames = result.data[0].name;
					var meetingValues = result.data[0].value;
					if(meetingValues==""){
						$("#container_4").css("display","none");
						$("#container_4_4").css("display","block");
						$("#container_4_4").html("<img id='img4' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img4").css("margin-top","55px");
					}else{
						$("#container_4_4").css("display","none");
						$("#container_4").css("display","block");
						showMeeting(meetingNames,meetingValues);
					}
				}
			});
			//展示决策议题
			 $.ajax({
				url : "/statistics/subject/getStatisticsSubjectChartData.action?industryId="+id,
				type : "POST",
				async: false, 
		        success: function (result) {
					var series = result.data[0].series;
					var xAxisData = result.data[0].xAxisData;
					var legendData = result.data[0].legendData;
					if(legendData==undefined){
					    $("#container_5").html("<img id='img5' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img5").css("margin-top","55px");
					}else{
					    showSubject(legendData,xAxisData,series);
					}
				}
			});
			//重载制度议题异常图表
			showItem_6(id);
			//列表重载
			var sURL = "{contextPath}/statistics/company/querPageList.action?currentPage={page}&pageSize={pageSize}&industryId="+id;
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
			//赋值全局变量行业ID
			instFlag = id;
		}
		
		function queryData(){
			var companyName = jt._("#companyName").value;
			$("#danger").hide();
			var data = "{\"companyName\":\""+companyName+"\"}";
			var sURL = "{contextPath}/statistics/company/querPageList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>