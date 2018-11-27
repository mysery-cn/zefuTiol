<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>	
<script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
<script>
	var hostName = "${hostName}";
	var redirectToStr = "${redirectTo}";
</script>
<link type="text/css" href="<%=SYSURL_static%>/css/index.css"
	rel="stylesheet" />
</head>
<body style="height: 100%;width:100%; margin: 0">
	<div id="container" style="height: 100%;width:60%;margin:0 auto;"></div>
</body>
<script type="text/javascript"
	src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript">
    var meetingId = jt.getParam("meetingId");
	var companyId = jt.getParam("companyId");
	var subjectId = jt.getParam("subjectId");
	jQuery(function($) {
		//展示议题关联会议流程图
		$.ajax({
			url : "/subject/querySubjectMeetingProcess.action",
			data : {
				meetingId : meetingId,
				subjectId : subjectId
			},
			dataType : "json",
			type : "POST",
			async : false,
			success : function(result) {
				showSubject(result);
			}
		});
	});
	//议题信息展示
	function showSubject(data) {
		var dom = document.getElementById("container");
		var myChart = echarts.init(dom);
		var app = {};
		option = null;
		option = {
			title : {
				text : '议题决策流程',
				left:'center'
			},
			tooltip : {
				formatter : '{c}'
			},
			animationDurationUpdate : 1500,
			animationEasingUpdate : 'quinticInOut',
			series : [
				{
					type : 'graph',
					layout : 'none',
					symbol : 'rect',
					symbolSize : [ 150, 50 ],
					color : [ '#417ac0' ],
					roam : true,
					label : {
						normal : {
							show : true,
							formatter : function(params) {
								var index = 10;
								var newStr = '';
								for (var i = 0; i < params.value.length; i += index) {
									var tmp = params.value.substring(i, i + index);
									newStr += tmp + '\n';
								}
								if (newStr.length > 5)
									return newStr.substring(0, 10) + '...';
								else
									return '\n' + newStr;
							}
						}
					},
					edgeSymbol : [ 'circle', 'arrow' ],
					edgeSymbolSize : [ 4, 10 ],
					edgeLabel : {
						normal : {
							show : false
						}
					},
					categories:[{
               		   name:'now',
           		       itemStyle:{
           		          color:'#fe9639'
           		       }
           		 	}],
					data : data.data,
					links : data.link,
					lineStyle : {
						normal : {
							opacity : 0.9,
							width : 2,
							curveness : 0
						}
					}
				}
			]
		};
		;
		if (option && typeof option === "object") {
			myChart.setOption(option, true);
			myChart.on('click', function (params) {
                viewMeetingDetail(params.data.name);
            });
		}
	}

	function viewMeetingDetail(id) {
		var href = "/meeting/meetingDetail.action?meetingId=" + id + "&companyId=" + companyId;
		window.parent.addpage("会议详情", href, 'meetingDetail' + meetingId);
	}
</script>
</html>
