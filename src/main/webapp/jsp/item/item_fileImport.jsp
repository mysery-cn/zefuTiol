<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<script type="text/javascript" src="<%=SYSURL_static%>/js/jquery/jquery-1.9.1.min.js" charset="utf-8"></script>
</head>
<script type="text/javascript">
/**
 * 保存表单
 */
function submitForm(){
    if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
    if (!aForm.checkForm()) return; //表单验证
    aForm.submit();//表单提交
}

function submitForm2(){
	var formData = new FormData();
	formData.append("importFile", document.getElementById("importFile").files[0]);   
	
	
	
	file = document.getElementById("importFile").files[0];

	if(file == null){
		showMsg("请选择文件。")
		return;
	}
	filename = file.name;
	if(checkFileExt(filename) == false){
		showMsg("您上传的文件类型不正确。")
		return;
	}
	
	top.showLoading('正在保存...');
	$.ajax({
	    url: "/item/importTiolItem.action",
	    type: "POST",
	    data: formData,
	    contentType: false,
	    processData: false,
	    success: function (data) {
	    	top.showLoading(false);
	        if (data.result=='success') {
	        	jt.Msg.alert("导入成功!",function(){
	        		var aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
	        		reloadFrameMainGrid(false,aForm);
					closeSelf();
	        	});
	        }else{
	        	jt.Msg.alert("导入失败！失败原因：<br>"+data.result);
	        }
	    },
	    error: function () {
	    	jt.Msg.alert("上传失败！");
	    }
	 
	}); 
}

//判断文件扩展名
function checkFileExt(filename){
	var flag = false; //状态
 	var arr = ["xls","xlsx"];
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
			<div icon="save.png" id="addButton" onclick="submitForm2()">保存</div>
			<div></div>
		</div>
	</div>
	<div id="divFixCnt" style="padding: 3px;">
		<form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/item/importTiolItem.action">
			<DIV class=FormTableTitle>导入事项信息</DIV>
			<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">上传导入文件：</td>
				<td class="cnt">
					 <input type="file" id="importFile" name="importFile">
				</td>
			</tr>
			</table>
			<blockquote  style="margin-top:20px;height:20px;">只允许导入xls或xlsx类型的文件。</blockquote>  
		</form>
	</div>
</body>
</html>