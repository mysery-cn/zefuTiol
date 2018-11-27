<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
    <title>上传议题附件</title>
</head>
<script type="text/javascript">
    function init(){
        var flag = "${flag}";
        if(flag){
            afterUpload();
            closeSelf();
        }else{
            var subjectId = jt.getParam("subjectId");
            jt._("#subjectId").value = subjectId;
        }
    }
    /**
     * 上传
     */
    function upload(){
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
        if (!aForm.checkForm()) return; //表单验证
        aForm.submit();//表单提交
    }

    /**
     * 刷新父页面
     */
    function afterUpload(){
        var fileType = "${file.attachmentType}";
        var fileName = "${file.attachmentName}";
        var fileId = "${file.fileId}";
        parent.afterUploadAttachment(fileType, fileName, fileId);
    }

    /**
     * 获取文件名称
     * @param Obj
     */
    function getFileName(Obj){
        var filePath = Obj.value;
        var arr = filePath.split('\\');
        $("#attachmentName").val(arr[arr.length-1]);
        if(arr[arr.length-1].indexOf("法律") > -1){
            $("#attachmentType").val("法律意见书");
        }
    }
</script>

<body class="BodyEdit" pageType="EditPage">
<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div icon="close.png" onclick="closeSelf();">关闭</div>
        <div icon="save.png" id="addButton" onclick="upload()">上传</div>
        <div></div>
    </div>
</div>

<div id="divFixCnt" style="padding:3px;">
    <form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data" action="/subject/uploadSubjectFile.action">
        <table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
            <tr>
                <td class="tit"><font style="color: RED;">*</font>选择文件：</td>
                <td class="cnt">
                    <input type="file" class="input" name="uploadfile" onchange="getFileName(this)" accept=".ofd,.pdf"/>
                    <input type="hidden" id="attachmentName" name="attachmentName">
                </td>
            </tr>
            <tr>
                <td class="tit"><font style="color: RED;">*</font>附件类型：</td>
                <td class="cnt">
                    <%--<input type="text" class="input" name="attachmentType" ErrEmptyCap="附件类型不允许为空"/>--%>
                    <select id="attachmentType" name="attachmentType">
                        <option value="议题材料">议题材料</option>
                        <option value="听取意见">听取意见</option>
                        <option value="法律意见书">法律意见书</option>
                    </select>
                    <input type="hidden" id="subjectId" name="subjectId">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>