<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/inc_head.jsp"%>
<script type="text/javascript" src="<%=SYSURL_static%>/js/ums/ums.js"></script>
<html>
<head>
    <title>附件上传</title>
</head>
<script type="text/javascript">
    //var paraHash = {"userId": "userId", "userName": "userName", "companyId": "companyId", "companyName": "companyName"};
    var paraHash = {"regulationId": "regulationId"};
	var regulationId  = jt.getParam("regulationId");
	//页面加载完成初始调用
    $(document).ready(function(){
		regulationId = jt.getParam("regulationId");
		//viewInit();
	});
    
    function viewInit() {
        jt._("#attachmentList").loadData()
    }
    
    function setMasterFlag(type) {
        var arr = delItem_GetIDs('', 'txtID', '请选择要设置的数据!', '确实要修改选中的数据吗？');
        if (arr.length != 1){
            showMsg("请选择一条记录进行操作");
            return;
        }
        var sURL = SYSURL.ums + "/ums/orguser/updateMasterFlag.action?";
        postJSON(sURL, {'id': arr.toString(),type:type}, function (json, o) {
            if (json.errorCode != 0) {
                showMsg('操作失败');
                return;
            }
            var gridId = "tabMain_List";
            if ($('body').attr("ForGrid"))  gridId = $('body').attr("ForGrid");
            _('#' + gridId).loadData();
        });
    }
    
    //上传附件
    function importAttachment(){
    	var sURL = '{contextPath}/com_regulation/attachmentImport.action?fileType=SUMMARY';
    	top_showDialog('上传文件',sURL,'WinEdit',550,350);
    }
    
    //删除
	function delAttachment(itemId){
		var arr=delItem_GetIDs(itemId,'txtID','请选择要删除的数据!','确定要删除吗？');
        if (arr.length==0) return;

        var sURL='{contextPath}/com_regulation/deleteFileById.action';
        postJSON(sURL, {'ids':arr.toString()},function (json,o){
            if (json.errorCode!=0){ 
            	showMsg(json.errorInfo,'操作失败',2000); 
            	return; 
            }else{
            	showMsg('删除成功！');
            	_('#attachmentList').loadData();
            }
        });
	}
    
    //浏览附件
	function showFileView(fileId){
	    var href = "/common/show.jsp?fileId="+fileId;
	    //top_showDialog("附件详情",href, 'viewFileWin', 1000, 650);
	    showWindow(jt.parseURL(href),'附件详情');
	}
   
	
</script>
<body class="BodyTreeList" type="view" actionPath="/com_regulation" jspPath="/jsp/com_regulation/" formName="regulation_attachmentImport"
      deleteAction="deleteOrgUser" formWidth="700" formHeight="500" ForGrid="attachmentList">
<!----------------------------------------------操作条------------------------------------------------ -->
<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div icon="add.png" onclick="addItem('&regulationId={regulationId}','addAttachmentWin')">
                      上传
        </div>
        <div icon="del.png" onclick="delAttachment()">删除</div>
    </div>
</div>

<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
    <tr>
        <!----------------------------------------------分隔线-------------------------------------------------- -->
        <td class="SplitBar" width="3"><span></span></td>
        <!----------------------------------------------视图列表------------------------------------------------ -->
        <td>
            <div id="divFixMain" class="GridOnly">
                <table class="Grid" FixHead="false" id="attachmentList" border="0" cellspacing="0" cellpadding="0"
                       AutoLoadData=false Action="showFileView('{fileId}')"
                       URLData="{contextPath}/com_regulation/getFileById.action?regulationId={regulationId}"
                       EmptyInfo="没有记录" width="100%">
                    <col boxName="txtID" width="10" boxValue="{attachmentId}">
                    <col caption="文件名称" field="{attachmentName}" width="120px" style="text-align:center;" align="center">
                    <col caption="上传时间" field="{uploadTime}" width="120px" style="text-align:center;" align="center">
                    
                </table>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp" %>