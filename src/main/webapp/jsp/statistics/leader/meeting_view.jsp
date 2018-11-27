<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<div class="content_main">
<input id="companyId" style="display: none;" hidden="hidden" value="${meeting.companyId }"/>
<input id="meetingId" style="display: none;" hidden="hidden" value="${meeting.meetingId }"/>
<div class="title_com"><h1>${meeting.companyName }—${meeting.meetingName }</h1></div>
<table>
<tr>
	<td  style="vertical-align:top;" >
		<div class="list_all">
		    <div class="meeting_main">
			  <table>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议类型</td>
					<td style="text-align: left;">${meeting.alias }</td>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议时间</td>
					<td style="text-align: left;">${meeting.meetingTime }</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">参会人</td>
					<td colspan="3" class="table_long_txt">${meeting.attendeeMember }</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">主持人</td>
					<td colspan="3" class="table_long_txt">${meeting.moderator }</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议通知</td>
					<td colspan="3" id="recordFile" class="table_long_txt">
					    <a onclick="showFileView('${meeting.noticeFileId}')" id="${meeting.noticeFileId}">${meeting.noticeFileName==null?"无":meeting.noticeFileName}</a>
					</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议纪要</td>
					<td colspan="3" id="noticeFile" class="table_long_txt">
						<a onclick="showFileView('${meeting.summaryFileId}')" id="${meeting.summaryFileId}">${meeting.summaryFileName==null?"无":meeting.noticeFileName}</a>
					</td>
				</tr>
			  </table>
		    </div>
		    <div id="subject_msg">
		       
		    </div>
		</div>
	</td>
</tr>
</table>	
</div>
</body>
	<script type="text/javascript">
	    jQuery(function($) {
     	    //展示 议题简要信息
			$.ajax({
				url : "/subject/querySubjectBrief.action",
				data:{
				    meetingId:$("#meetingId").val(),
				    companyId:$("#companyId").val()
				},
				dataType:"json",
				type : "POST",
				async: false, 
		        success: function (result) {
					showSubject(result);
				}
			});
  	    });
  	    //显示隐藏议题信息
  	    function showHideSubject(num){
     	    if($('#subject'+num).is(':hidden')){
    	       $('#expandIcon'+num).attr("src",'<%=SYSURL_static%>/images/tiol/ico_expand_a.png');
   	           $('#subject'+num).show();
  	        }else{
  	           $('#expandIcon'+num).attr("src",'<%=SYSURL_static%>/images/tiol/ico_expand_b.png');
  	           $('#subject'+num).hide();
  	        }
  	    }
  	    
  	    function viewSubjectDetail(subjectId,meetingId,companyId){
	  	    if(curUser.orgLevel == 0){
	  			NavGoURL('{SYSURL_oa}/subject/subjectDetail.action?meetingId='+meetingId+"&subjectId="+subjectId+"&companyId="+companyId);
	  		}else{
	  			var href = "/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
	  	        window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
	  		}
  	    }
  	    
  	    //议题信息展示
  	    function showSubject(data){
  	        for(var i=0;i<data.length;i++){
  	            var html="<div class='table_expand'><div class='table_expand_left'>"+
  	            "<img id='expandIcon"+i+"'  style='cursor:pointer;' onclick='showHideSubject("+i+");'><a herf='javascript:void(0);' onclick='viewSubjectDetail(\""+data[i].subjectId+"\",\""+$("#meetingId").val()+"\",\""+$("#companyId").val()+"\")'>议题"+(i+1)+":"+data[i].subjectName+"</a></div>"+
  	            "<div class='table_expand_right'><img id='passIcon"+i+"''><p>是否通过：</p></div></div>"+
  	            "<div id='subject"+i+"' class='list_main_content' style='display:none;'><table>"+
  	            "<tr><td id='sourceName"+i+"' class='table_tr_b' style='font-weight: bold;width:100px;'>任务来源</td><td class='table_long_txt'>"+data[i].sourceName+"</td>"+
				"<td class='table_tr_b' style='font-weight: bold;width:100px;''>专项名称</td><td class='table_long_txt'>"+data[i].specialName+"</td> </tr>"+
				"<tr><td class='table_tr_b' style='font-weight: bold;width:100px;'>列席人员</td><td colspan='3' class='table_long_txt'>"+data[i].attendanceMember+"</td></tr>"+
				"<tr><td class='table_tr_b' style='font-weight: bold;width:100px;'>表决情况</td><td colspan='3' class='table_long_txt'>";
				if(data[i].deliberationList.length>0){
				  for(var j=0;j<data[i].deliberationList.length;j++){
				    html += "<div class='vote_subject'><span>"+data[i].deliberationList[j].NAME+"（"+data[i].deliberationList[j].POSITION+"）：</span><span>"+data[i].deliberationList[j].RESULT+"</span></div>";
				  }
				}else{
				  html += "无";
				}
				html += "</td></tr></table></div>";
  	            $('#subject_msg').append(html);
  	            if(data[i].passFlag=="1"){
  	                $('#passIcon'+i).attr("src",'<%=SYSURL_static%>/images/tiol/ico_pass.png');
  	            }else{
  	                $('#passIcon'+i).attr("src",'<%=SYSURL_static%>/images/tiol/ico_no.png');
  	            }
  	            $('#expandIcon'+i).attr("src",'<%=SYSURL_static%>/images/tiol/ico_expand_b.png');
  	        }
  	    }
	</script>
</html>
