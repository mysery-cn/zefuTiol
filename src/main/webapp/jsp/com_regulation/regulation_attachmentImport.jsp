<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<script type="text/javascript" src="<%=SYSURL_static%>/js/ums/ums.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/jquery/jquery-1.9.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/jquery.form.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/json2.js" charset="utf-8"></script>
</head>
<script type="text/javascript">
var paraHash = {"regulationId": "regulationId"};

var regulationId = jt.getParam("regulationId");
var oneFile = jt.getParam("oneFile");
$(document).ready(function(){

});


function submitForm(){
	var files = document.getElementById("importFile");
	var file;
	var filename;
	if(files.files){
		file = files.files[0];
		if(file){
			filename = file.name;	
		}		
	}else{
		file = files;
		filename = file.value;
	}
	if(file == null || filename == '' || filename == null){
		showMsg("请选择文件。")
		return;
	}
	
	if(checkFileExt(filename) == false){
		showMsg("您上传的文件类型不正确。")
		return;
	}
	
	var url = "/com_regulation/uploadFile.action?regulationId=" + regulationId;
	if(oneFile == 1){
		url = "/com_regulation/uploadOneFile.action";
	}
	
	top.showLoading('正在保存...');
	//$('#frmMain').attr('action',url);
	$('#frmMain').ajaxSubmit({
			type : "POST",
			url : url,
			contentType : false,
			processData : false,
			success : function(jsonstr) {
				top.showLoading(false);
				var data = jsonstr;
		    	if(typeof(jsonstr) == 'string'){
			    	data = JSON.parse(jsonstr);
		    	}
				if (data.result=='success') {
		        	jt.Msg.alert("上传成功!",function(){
		        		if(oneFile == 1){
		        			var fileName = data.fileName;
		        			var fileId = data.fileId;
		    				$("#fileName",window.parent.document).val(fileName);
		    				$("#fileId",window.parent.document).val(fileId);
		        		}else{
		        			top.jt.Window.findWindow("WinEdit").getIFrame()[0]._("#attachmentList").loadData();
		        		}
						closeSelf();
		        	});
		        }else{
		        	jt.Msg.alert("上传失败！失败原因：<br>"+data.result);
		        }
			}
	});
}

//判断文件扩展名
function checkFileExt(filename){
	var flag = false; //状态
 	var arr = ["pdf","ofd"];
 	//取出上传文件的扩展名
 	var index = filename.lastIndexOf(".");
 	var ext = filename.substr(index+1);
 	//循环比较
 	for(var i=0;i<arr.length;i++){
  		if(ext == arr[i]){
   			flag = true; //一旦找到合适的，立即退出循环
   			break;
  		}
	}
 	return flag;
}
</script>
<body class="BodyEdit" pageType="EditPage">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
			<div icon="close.png" onclick="closeSelf();">关闭</div>
			<div icon="save.png" id="addButton" onclick="submitForm()">保存</div>
			<div></div>
		</div>
	</div>
	<div id="divFixCnt" style="padding: 3px;">
		<form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/com_regulation/uploadFile.action">
			<DIV class=FormTableTitle>请选择要上传的文件</DIV>
			<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">上传文件：</td>
				<td class="cnt">
					 <input type="file" id="importFile" name="importFile">
				</td>
			</tr>
			</table>
			<blockquote  style="margin-top:20px;height:20px;">只允许导入pdf或odf类型的文件。</blockquote>  
		</form>
	</div>
</body>
</html>