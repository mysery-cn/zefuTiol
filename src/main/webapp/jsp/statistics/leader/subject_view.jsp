<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script>
		var hostName = "${hostName}";
		var redirectToStr = "${redirectTo}";
	</script>
	<link type="text/css" href="<%=SYSURL_static%>/css/index.css" rel="stylesheet" />
</head>
<style>
.list_main table tr td {
	border: 1px solid #dfdfdf;
	height: 30px;
	padding: 4px;
	vertical-align: middle;
}

.list_main table a:hover {
	cursor: pointer;
	text-decoration: underline;
}

.list_main_content table td {
	text-align: center;
	border: 1px solid #dfdfdf;
	height: 36px;
	padding: 4px;
	vertical-align: middle !important;
}

.list_main_content table td a {
	font-size: 14px;
}

.list_main_content table tr td a:hover {
	cursor: pointer;
}
</style>
<body>
<div class="content_main">
<input id="companyId" style="display: none;" hidden="hidden" value="${companyId }"/>
<input id="meetingId" style="display: none;" hidden="hidden" value="${meetingId }"/>
<input id="subjectId" style="display: none;" hidden="hidden" value="${subjectId }"/>
<div class="title_com"><h1><span id="companyTitle"></span>—<span id="subjectTitle"></span></h1></div>
<table>
<tr>
	<td  style="vertical-align:top;" >
		<div class="list_all">
		<div class="list_main_content">
			<table>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议名称</td>
					<td id="meetingName" class="table_long_txt"></td>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议时间</td>
					<td id="meetingTime" class="table_long_txt"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">事项名称</td>
					<td id="itemName" colspan="3" class="table_long_txt"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">专项名称</td>
					<td id="specialName" colspan="3" class="table_long_txt"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">任务来源</td>
					<td id="sourceName" colspan="3" class="table_long_txt"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">参会人</td>
					<td id="attendeeMember" colspan="3" class="table_long_txt"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">列席人</td>
					<td id="attendanceMember"colspan="3" class="table_long_txt">无</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">
					  <div title="点击查看具体决策流程" style="cursor:pointer;" onclick="viewSubjectProcess();">决策流程<img width="15"src="<%=SYSURL_static%>/images/tiol/meeting_process.png"/>
					  </div>
					</td>
					<td colspan="3">
						<div id="decisionProcess" class="decision_process">
							
						</div>
					</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">是否通过</td>
					<td colspan="3" class="table_long_txt"><div id ="passFlag" class="bold"></div></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">审议结果</td>
					<td id="deliberationList" colspan="3" class="table_long_txt">
					   
					</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">议题决议</td>
					<td colspan="3" class="table_long_txt"><p id="subjectResult"></p></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">听取意见</td>
					<td colspan="3" class="table_long_txt" id="opinionFile"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">是否采纳</td>
					<td  colspan="3" class="table_long_txt"><div id ="adoptFlag" class="bold"></div></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">议题材料</td>
					<td colspan="3" class="table_long_txt" id="subjectFile"></td>
				</tr>
			</table>
		</div>
			
		</div>
	</td>
</tr>
</table>	
</div>
</body>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript">
	    jQuery(function($) {
     	     //展示 议题简要信息
			$.ajax({
				url : "/subject/querySubjectDetail.action",
				data:{
				    meetingId:$("#meetingId").val(),
				    companyId:$("#companyId").val(),
				    subjectId:$("#subjectId").val()
				},
				dataType:"json",
				type : "POST",
				async: false, 
		        success: function (result) {
					showSubject(result);
				}
			});
  	    });
  	    //议题信息展示
  	    function showSubject(data){
  	        $('#companyTitle').html(data.meeting.companyName);
  	        $('#meetingName').html(data.meeting.meetingName);
  	        $('#subjectTitle').html(data.subject.subjectName);
  	        $('#meetingTime').html(data.meeting.meetingTime);
  	        $('#sourceName').html(data.subject.sourceName);
  	        $('#specialName').html(data.subject.specialName);
  	        $('#attendeeMember').html(data.meeting.attendeeMember);
  	        $('#attendanceMember').html(data.subject.attendanceMember);
  	        $('#subjectResult').html(data.subject.subjectResult);
  	        $('#itemName').html(data.subject.itemName);
  	        if("1"==data.subject.passFlag){
  	            $('#passFlag').addClass("green");
  	            $('#passFlag').html('是');
  	        }else{
  	            $('#passFlag').addClass("red");
  	            $('#passFlag').html('否');
  	        }
  	        if("1"==data.subject.adoptFlag){
  	            $('#adoptFlag').addClass("green");
  	            $('#adoptFlag').html('是');
  	        }else{
  	            $('#adoptFlag').addClass("red");
  	            $('#adoptFlag').html('否');
  	        }
  	        //决策流程图
  	        var list = data.itemMeetingList;
  	        var html = "<table>";
  	        for(var i =0;i<data.itemMeetingList.length;i++){
  	            html += "<tr><td style='width:300px;'>"+data.itemMeetingList[i].itemName+"</td><td style='text-align: left;'>";
  	            for (var j = 0; j < data.itemMeetingList[i].typeMeeting.length; j++) {
		           html = html + '<div class="bg_meeting"><div class="bg_meet_left"></div><div class="meeting_name_bg">'+data.itemMeetingList[i].typeMeeting[j].typeName+'</div><div class="bg_meet_right"></div></div>';
		           if(j < data.itemMeetingList[i].typeMeeting.length - 1){
			           html = html + '<div class="meetting_line"></div>';
		           }
	           }
	           html += "</td></tr>";
  	        }
  	        html += "</table>";
  	        $('#decisionProcess').append(html);
  	        
  	        if(data.deliberationList.length >0){
  	            for(var i =0;i<data.deliberationList.length;i++){
  	                var html = "<div class='vote_subject'><span>"+data.deliberationList[i].NAME;
  	                if(data.deliberationList[i].POSITION != "undefined" && data.deliberationList[i].POSITION !="" && data.deliberationList[i].POSITION!=undefined){
  	                    html += "（"+data.deliberationList[i].POSITION+"）";
  	                }
  	                html += "：</span><span>"+data.deliberationList[i].RESULT+"</span></div>";
  	                $('#deliberationList').append(html);
  	            }
  	        }else{
  	            $('#deliberationList').append("无");
  	        }
  	       //听取材料
			if(data.opinionFile != "undefined" && data.opinionFile !="" && data.opinionFile!=undefined){
				for(var i=0;i < data.opinionFile.length;i++){
					var html = "";
					html = html + "<a id='"+data.opinionFile[i].FILE_ID+"' herf='javascript:void(0);' onclick='showFileView(\""+data.opinionFile[i].FILE_ID+"\")'>"+data.opinionFile[i].FILE_NAME+"</a>";
					$("#opinionFile").append(html);
				}
			}else{
				$("#opinionFile").append("无");
			}
			//议题材料
			if(data.subjectFile != "undefined" && data.subjectFile !="" && data.subjectFile!=undefined){
				for(var i=0;i < data.subjectFile.length;i++){
					var html = "";
					html = html + "<a id='"+data.subjectFile[i].FILE_ID+"' herf='javascript:void(0);' onclick='showFileView(\""+data.subjectFile[i].FILE_ID+"\")'>"+data.subjectFile[i].FILE_NAME+"</a>";
					$("#subjectFile").append(html);
				}
			}else{
				$("#subjectFile").append("无");
			}
  	    }
  	    
  	    function viewMeetingDetail(meetingId,companyId){
  	        var href = "/meeting/meetingDetail.action?meetingId="+meetingId+"&companyId="+companyId;
  			window.parent.addpage("会议详情",href,'meetingDetail' + meetingId);
  	    }
  	    
  	    //查看议题决策流程
  	    function viewSubjectProcess(){
  	        var meetingId = $("#meetingId").val();
			var companyId = $("#companyId").val();
			var subjectId = $("#subjectId").val();
  	        var href = "/subject/subjectProcess.action?meetingId="+meetingId+"&companyId="+companyId+"&subjectId="+subjectId;
  			window.parent.addpage("议题决策详情",href,'subjectProcess' + meetingId);
  	    }
	</script>
</html>
