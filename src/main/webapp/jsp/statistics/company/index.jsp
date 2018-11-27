<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中央企业“三重一大”决策和运行应用系统</title>
<link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css"
	type="text/css" />
<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
</head>

<body style="height:800px;">
	<div class="content_main">
		<div class="list_all">
			<table style="margin-top:20px;">
				<tr>
					<td style="border-right:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf;width: 33%;">
			<div class="charts_wei_a">
				<div class="list_title" ><p>贯彻执行党和国家决策部署的重大措施</p></div>
				<div class="chart_img_wei">
					<!-- 柱形图 -->
					<div id="container_1" style="height: 220px;width: 95%;margin: 0 auto;"></div>
				</div>
			</div>
			</td>
			<td style="border-right:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf; width: 33%;">
			<div class="charts_wei_a">
				<div class="list_title"><p>制度分类汇总</p></div>
				<div class="chart_img_wei">
					<!-- 饼图 -->
					<div id="container_2" style="height: 220px;width: 95%;margin: 0 auto;"></div>
					<div id="container_2_2" style="height: 220px;width: 95%;margin: 0 auto;display: none"></div>
				</div>
			</div>
			</td>
			<td style="border-bottom:1px solid #dfdfdf;width: 33%;">
			<div class="charts_wei_a"> 
				<div class="list_title"><p>事项清单汇总</p></div>
				<div class="chart_img_wei">
					<div id="container_3" style="height: 220px;width: 95%;margin: 0 auto;"></div>
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
					<div id="container_4_4" style="height: 220px;width: 95%;margin: 0 auto;display: none"></div>
				</div>
			</div>
			</td>
			<td style="border-right:1px solid #dfdfdf;">
			<div class="charts_wei_a"> 
				<div class="list_title"><p>决策议题情况</p></div>
				<div class="chart_img_wei">
					<div id="container_5" style="height: 220px;width: 95%;margin: 0 auto;"></div>
				</div>
			</div>
			</td>
			<td style="border-right:1px solid #dfdfdf;">
			<div class="charts_wei_a"> 
				<div class="list_title"><p>异常情况汇总</p></div>
				<div class="chart_img_wei">
					<div id="container_6" style="height: 220px;width: 95%;margin: 0 auto;"></div>
				</div>
			</div>
			</td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/statistics/company/index.js"></script>
<script type="text/javascript">
	//定义全局变量：行业ID
		var instFlag = "";
		var decisionTypeArray;
		var decisionIdArray;
	</script>
<script type="text/javascript">
		jQuery(function($) {
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
						$("#img2").css("padding-top","45px");
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
                        $("#img3").css("padding-top","45px");
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
						$("#img4").css("padding-top","45px");
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
					if(legendData==""||legendData ==undefined){
						$("#container_5").html("<img id='img5' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img5").css("padding-top","45px");
					}else{
					    showSubject(legendData,xAxisData,series);
					}
				}
			});
			showItem_6();
		});
		
		function queryCompanyByCompanyName(){
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