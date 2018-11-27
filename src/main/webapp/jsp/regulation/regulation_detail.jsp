<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
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
	<div>
		<div class="title_com" ><h1 id="Title" style="font-size:20px;"></h1></div>
		<table>
		<tr>
			<td  style="vertical-align:top;" >
				<div class="list_all">
				<div class="h20"></div>
				<div class="list_main_content">
					<table>
						<tr>
							<td class="table_tr_b" style="font-weight: bold;width:100px;">制度名称</td>
							<td style="text-align: left;" colspan="3" id="REGULATION_NAME"></td>
							
						</tr>
						<tr>
							<td class="table_tr_b" style="font-weight: bold;width: 100px" >制度类型</td>
							<td style="text-align: left;" id="TYPE_NAME"></td>
							<td class="table_tr_b" style="font-weight: bold;width:100px;">人数占比</td>
							<td class="table_long_txt" id="rate"></td>
						</tr>
						<tr>
							<td class="table_tr_b" style="font-weight: bold;width:100px;">审批时间</td>
							<td style="text-align: left;" id="APPROVAL_DATE"></td>
							<td class="table_tr_b" style="font-weight: bold;width: 100px">生效时间</td>
							<td style="text-align: left;" id="EFFECTIVE_DATE"></td>
						</tr>
						<tr>
							<td class="table_tr_b" style="font-weight: bold;width:100px;">是否经过审查</td>
							<td class="table_long_txt" id="auditFlag"></td>
							<td class="table_tr_b" style="font-weight: bold;width:100px;">正式文件</td>
							<td class="table_long_txt" id="fileName">
								
							</td>
						</tr>
						<tr>
							<td class="table_tr_b" style="font-weight: bold;width:100px;">审查佐证材料</td>
							<td class="table_long_txt" colspan="3" id="auditFileList">
								
							</td>
						</tr>
					</table>
				</div>
				<hr/>
				<div class="list_main_content">
					<table id="voteModelList">
						<tr>
							<td class="table_tr_b" style="font-weight: bold;width:10%">事项编码</td>
							<td class="table_tr_b" style="font-weight: bold;width:50%">事项名称</td>
							<td class="table_tr_b" style="font-weight: bold;width:40%">表决方式</td>
						</tr>
					</table>
				</div>
				</div>
			</td>
		</tr>
		</table>	
		</div>
</body>
	<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript">
		function init(){
			var regulationID = jt.getParam("regulationID");
			//查询制度详情
			var dataURL = "{contextPath}/regulation/queryRegulationDetail.action";
			var jPost= {};
			jPost.regulationID = regulationID;
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					var data = json.data[0];
					//标题
					$("#Title").html(data.title);
					//表格数据
					$("#REGULATION_NAME").html(data.REGULATION_NAME);
					$("#APPROVAL_DATE").html(data.APPROVAL_DATE);
					$("#EFFECTIVE_DATE").html(data.EFFECTIVE_DATE);
					$("#TYPE_NAME").html(data.TYPE_NAME);
					$("#rate").html(data.rate);
					$("#auditFlag").html(data.auditFlag);
					//正式文件
					$("#fileName").append("<a onclick='showFileView(\""+data.FILE_ID+"\")' id='"+data.FILE_ID+"'>"+data.fileName+"</a>");
					//佐证材料
					if(data.auditFileList.length > 0){
						for(var i=0;i < data.auditFileList.length;i++){
							var auditFile = "";
							auditFile = auditFile + "<a onclick='showFileView(\""+data.auditFileList[i].FILE_ID+"\")' id='"+data.auditFileList[i].FILE_ID+"'>"+data.auditFileList[i].ATTACHMENT_NAME+"</a>";
							$("#auditFileList").append(auditFile);
				        }
					}
					//表决方式
					for(var i=0;i < data.voteModeList.length;i++){
						var voteHtml = "";
							voteHtml += "<tr>";
							voteHtml += "<td>"+data.voteModeList[i].itemCode+"</td>";
							voteHtml += "<td>"+data.voteModeList[i].itemName+"</td>";
							voteHtml += "<td>"+data.voteModeList[i].modeName+"</td>";
							voteHtml += "</tr>";
						$("#voteModelList").append(voteHtml);
			        }
				}
			},false);
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>