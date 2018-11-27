<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<script>
	var meetingId = jt.getParam("meetingId");
	var companyId = jt.getParam("companyId");
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>会议详情</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
</head>
<div class="content_main">
<div class="title_com"><h1 id="Title" style="font-size:20px;"></h1></div>
<table>
<tr>
	<td  style="vertical-align:top;" >
		<div class="list_all">
		    <div class="h20"></div>
		    <div class="list_main_content">
			  <table>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议类型名称</td>
					<td style="text-align: left;" id="meetingType"></td>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议日期</td>
					<td style="text-align: left;" id="meetingTime"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">参会人</td>
					<td colspan="3" class="table_long_txt" id="attendeeMember"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">主持人</td>
					<td colspan="3" class="table_long_txt" id="moderator"></td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议通知</td>
					<td colspan="3" class="table_long_txt" id="noticeFile">
					
					</td>
				</tr>
				<tr>
					<td class="table_tr_b" style="font-weight: bold;width:100px;">会议纪要</td>
					<td colspan="3" class="table_long_txt" id="recordFile">
					
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript">
	    function init(){
			//查询制度详情
			var dataURL = "{contextPath}/meeting/queryMeetingMessage.action";
			var jPost= {};
			jPost.meetingId = meetingId;
			jPost.companyId = companyId;
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					var data = json.data[0];
					//标题
					$("#Title").html("" + data.companyName + " - " + data.meetingName);
					//表格数据
					$("#meetingType").html(data.alias);
					$("#meetingTime").html(data.meetingTime);
					$("#attendeeMember").html(data.attendeeMember);
					$("#moderator").html(data.moderator);
					//会议通知
					if(data.recordFile != "undefind"){
						for(var i=0;i < data.recordFile.length;i++){
							var auditFile = "";
							auditFile = auditFile + "<a onclick='showFileView("+data.recordFile[i].FILE_ID+")' id='"+data.recordFile[i].FILE_ID+"'>"+data.recordFile[i].FILE_NAME+"</a>";
							$("#recordFile").append(auditFile);
				        }
					}
					//会议纪要
					if(data.noticeFile != "undefind"){
						for(var i=0;i < data.noticeFile.length;i++){
							var auditFile = "";
							auditFile = auditFile + "<a onclick='showFileView("+data.noticeFile[i].FILE_ID+")' id='"+data.noticeFile[i].FILE_ID+"'>"+data.noticeFile[i].FILE_NAME+"</a>";
							$("#noticeFile").append(auditFile);
				        }
					}
				}
			},false);
			//展示 议题简要信息
			dataURL = "{contextPath}/subject/querySubjectBrief.action";
			jPost= {};
			jPost.meetingId = meetingId;
			jPost.companyId = companyId;
			postJSON(dataURL,jPost,function(json,o){
				if(json){
					showSubject(json);
				}else{
					if (json.errorCode != "0") {
						return jt.Msg.showMsg("获取数据失败！");
					} 
				}
			},false);
		}
	    
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
	  	  		NavGoURL('{SYSURL_oa}/subject/subjectDetail.action?meetingId='+meetingId+'&subjectId='+subjectId+'&companyId='+companyId);
	  	  	}else{
		  	  	var href = "/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
	  	        window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
	  	  	}
  	    }
  	    
  	    
  	    //议题信息展示
  	    function showSubject(data){
  	        for(var i=0;i<data.length;i++){
  	            var html="<div class='table_expand'><div class='table_expand_left'>"+
  	            "<img id='expandIcon"+i+"'  style='cursor:pointer;' onclick='showHideSubject("+i+");'><a herf='javascript:void(0);' onclick='viewSubjectDetail(\""+data[i].subjectId+"\",\""+meetingId+"\",\""+companyId+"\")'>议题"+(i+1)+":"+data[i].subjectName+"</a></div>"+
  	            "<div class='table_expand_right'><img id='passIcon"+i+"''><p>是否通过：</p></div></div>"+
  	            "<div id='subject"+i+"' class='list_main_content' style='display:none;'><table>"+
  	            "<tr><td id='sourceName"+i+"' class='table_tr_b' style='font-weight: bold;width:100px;'>任务来源</td><td class='table_long_txt'>"+data[i].sourceName+"</td>"+
				"<td class='table_tr_b' style='font-weight: bold;width:100px;''>专项名称</td><td class='table_long_txt'>"+data[i].specialName+"</td> </tr>"+
				"<tr><td class='table_tr_b' style='font-weight: bold;width:100px;'>列席人员</td><td colspan='3' class='table_long_txt'>"+data[i].attendanceMember+"</td></tr>"+
				"<tr><td class='table_tr_b' style='font-weight: bold;width:100px;'>表决情况</td><td colspan='3' class='table_long_txt'>";
				for(var j=0;j<data[i].deliberationList.length;j++){
				    html += "<div class='vote_subject'><span>"+data[i].deliberationList[j].NAME+"（"+data[i].deliberationList[j].POSITION+"）：</span><span>"+data[i].deliberationList[j].RESULT+"</span></div>";
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
<%@ include file="/common/inc_bottom.jsp"%>