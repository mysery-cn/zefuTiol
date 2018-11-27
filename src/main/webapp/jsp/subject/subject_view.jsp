<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<script>
	var meetingId = jt.getParam("meetingId");
	var companyId = jt.getParam("companyId");
	var subjectId = jt.getParam("subjectId");
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>议题详情</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
</head>
<body>
<div class="content_main">
<div class="title_com"><span id="companyTitle"></span>—<span id="subjectTitle"></span></div>
<table>
<tr>
	<td  style="vertical-align:top;" >
		<div class="list_all">
		<div class="h20"></div>
		<div class="list_main_content">
			<table>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议名称</td>
					<td id="meetingName" class="table_long_txt"></td>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议日期</td>
					<td id="meetingTime" class="table_long_txt"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">事项名称</td>
					<td id="itemName" colspan="3" class="table_long_txt"></td>
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
					<td class="table_tr_b" style="font-weight: bold;width:100px;">决策流程</td>
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
					<td colspan="3" class="table_long_txt"><a href="#">《财务部关于增设境外公司监管组的请示的征求意见反馈》 </a><a href="#">《法律意见书》</a></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">是否采纳</td>
					<td  colspan="3" class="table_long_txt"><div id ="adoptFlag" class="bold"></div></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">议题材料</td>
					<td colspan="3" class="table_long_txt"><a href="#">《财务部关于增设境外公司监管组的请示》 </a></td>
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
		showDetail();
		function showDetail(){
			//展示 议题简要信息
			$.ajax({
				url : "/subject/querySubjectDetail.action",
				data:{
				    meetingId:meetingId,
				    companyId:companyId,
				    subjectId:subjectId
				},
				dataType:"json",
				type : "POST",
				async: false, 
		        success: function (result) {
					showSubject(result);
				}
			});
		}
  	    //议题信息展示
  	    function showSubject(data){
  	        $('#companyTitle').html(data.meeting.companyName);
  	        $('#meetingName').html(data.meeting.meetingName);
  	        $('#subjectTitle').html(data.subject.subjectName);
  	        $('#meetingTime').html(data.meeting.meetingTime);
  	        $('#attendeeMember').html(data.meeting.attendeeMember);
  	        $('#attendanceMember').html(data.subject.attendanceMember);
  	        $('#subjectResult').html(data.subject.subjectResult);
  	        $('#itemName').html(data.subject.meetingTime);
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
  	        for(var i =0;i<data.meetingList.length;i++){
  	            var imgBg="bg_a";
  	            if(i>0 && i< data.meetingList.length-1){
  	                imgBg="bg_b";
  	            }else if(i == data.meetingList.length-1){
  	                imgBg="bg_c";
  	            }
  	            if(data.meetingList[i].typeMeetingList.length==0){
  	                var html="<div class='meeting_blue_a'><div class='meeting_name_gray'><a>"+data.meetingList[i].typeName+"</a><div class='gray_"+imgBg+"'></div><p></p></div></div>";
  	                $('#decisionProcess').append(html);
  	            }else{
  	                for(var j=0;j<data.meetingList[i].typeMeetingList.length;j++){
  	                    if(meetingId==data.meetingList[i].typeMeetingList[j].meetingId){
  	                        var html="<div class='meeting_blue_a'><div class='meeting_name_yellow'><a herf='javascript:void(0);' onclick='viewMeetingDetail(\""+data.meetingList[i].typeMeetingList[j].meetingId+"\",\""+companyId+"\")'>"+data.meetingList[i].typeMeetingList[j].meetingName+"</a><div class='yellow_"+imgBg+"'></div><p>"+data.meetingList[i].typeMeetingList[j].meetingTime+"</p></div></div>";
  	                        $('#decisionProcess').append(html);
  	                    }else{
  	                        var html="<div class='meeting_blue_a'><div class='meeting_name_blue'><a herf='javascript:void(0);' onclick='viewMeetingDetail(\""+data.meetingList[i].typeMeetingList[j].meetingId+"\",\""+companyId+"\")'>"+data.meetingList[i].typeMeetingList[j].meetingName+"</a><div class='bule_"+imgBg+"'></div><p>"+data.meetingList[i].typeMeetingList[j].meetingTime+"</p></div></div>";
  	                        $('#decisionProcess').append(html);
  	                    }
  	                
  	                }
  	            }
  	        }
  	        if(data.deliberationList.length >0){
  	            for(var i =0;i<data.deliberationList.length;i++){
  	                var html = "<div class='vote_subject'><span>"+data.deliberationList[i].NAME+"（"+data.deliberationList[i].POSITION+"）：</span><span>"+data.deliberationList[i].RESULT+"</span></div>";
  	                $('#deliberationList').append(html);
  	            }
  	        }else{
  	            $('#deliberationList').append("无");
  	        }
  	    }
  	    
  	    function viewMeetingDetail(meetingId,companyId){
  	      NavGoURL('{SYSURL_oa}/jsp/meeting/meeting_view.jsp?meetingId='+meetingId+'&companyId='+companyId);
  	    }
	</script>
</html>
