<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/inc_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>议题编辑</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/meeting_form.css" type="text/css"/>
</head>
<script>
    function init(){
        initFieldValue();//回填信息
        initFileData();//初始化附件
    }
    /**
     * 回填初始化
     */
    function initFieldValue(){
        $("#subjectResult").val("${subject.subjectResult}");
        $("#remark").val("${subject.remark}");
        $("#subjectUser").val("${subject.attendanceList}");
        $("#deliberations").val("${subject.deliberationList}");
        $("#itemCode").val("${subject.itemRelevanceCodes}");
        $("#itemIds").val("${subject.itemRelevanceIds}");
        $("#relMeetingId").val("${subject.meetingRelevanceIds}");
        $("#relMeetingName").val("${subject.meetingRelevanceNames}");
        $("#relSubjectId").val("${subject.subjectRelevanceIds}");
        $("#relSubjectName").val("${subject.subjectRelevanceNames}");
        var meetingType = parent.$("#meetingType option:selected").text();
        if(meetingType !="" && (meetingType.indexOf('董事长')>-1 || meetingType.indexOf('其他会议')>-1)){
            $("#subjectUserRed").hide();
            $("#subjectUser").removeAttr("ErrEmptyCap");
        }else{
            $("#subjectUserRed").show();
            $("#subjectUser").attr("ErrEmptyCap","会议通知不允许为空");
        }
    }
</script>
<body class="BodyEdit" pageType="EditPage">
<div class="form_top">
    <div class="form_top_btn">
        <a class="btn_close" href="javascript:void(0)" onclick="closeSelf()">关闭</a>
        <a class="btn_save" href="javascript:void(0)" onclick="saveForm()">保存</a>
        <a class="btn_upload" href="javascript:void(0)" id="uploadAttachmentBt" onclick="uploadAttachment()">上传附件</a>
    </div>
</div>
<div class="form_main" style="height:420px;margin-top: 0px;overflow: auto">
    <div class="form_table">
        <div class="form_issue">
            <form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/subject/saveSubject.action">
            <table id="subjectTable" style="margin-top: 0px">
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>议题名称</td>
                    <td colspan="3">
                        <input type="text" id="subjectName" name="subjectName" placeholder=" 请输入议题名称" ErrEmptyCap="议题名称不允许为空" value="${subject.subjectName}">
                        <input type="hidden" id="subjectId" name="subjectId" value="${subject.subjectId}">
                        <input type="hidden" id="meetingId" name="meetingId" value="${subject.meetingId}">
                        <input type="hidden" id="companyId" name="companyId" value="${subject.companyId}">
                        <input type="hidden" id="isUpdate" name="isUpdate" value="1">
                    </td>
                </tr>
                <tr>
                    <td class="td_name">任务来源</td>
                    <td>
                        <select class="DropDown_Select" id="sourceId" name="sourceId" style="border:1px solid #dfdfdf;width: 100%;" defaultValue="${subject.sourceId}"
                                urldata="{contextPath}/source/querySourceList.action" optiontext="{SOURCE_NAME}" optionvalue="{SOURCE_ID}">
                        </select>
                    </td>
                    <td class="td_name">专项名称</td>
                    <td>
                        <select class="DropDown_Select" id="specialId" name="specialId" style="border:1px solid #dfdfdf;width: 100%;" defaultValue="${subject.specialId}"
                                urldata="{contextPath}/special/querySpecialList.action" optiontext="{specialName}" optionvalue="{specialId}">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>事项编码</td>
                    <td>
                        <input type="hidden" id="itemIds" name="itemIds">
                        <input type="text" id="itemCode" name="itemCode" onblur="validateItem(this)" ErrEmptyCap="关联事项编码不允许为空">
                    </td>
                    <td class="td_name">是否通过</td>
                    <td>
                        <div class="radio_form">
                            <input type="radio" name="passFlag" value="1" checked="checked"><span>通过</span>
                            <input type="radio" name="passFlag" value="0"><span>否决</span>
                            <input type="radio" name="passFlag" value="2"><span>缓议</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_name">关联会议</td>
                    <td>
                        <input type="hidden" id="relMeetingId" name="relMeetingId">
                        <input type="text" id="relMeetingName" name="relMeetingName" style="width: 92%;float: left" readonly="readonly">
                        <button type="button" class="btn_add" onclick="selectLinkSubject()" style="margin-top: 5px;"></button>
                    </td>
                    <td class="td_name">关联议题</td>
                    <td>
                        <input type="hidden" id="relSubjectId" name="relSubjectId">
                        <input type="text" id="relSubjectName" name="relSubjectName" style="width: 92%;float: left" readonly="readonly">
                        <button type="button" class="btn_add" onclick="selectLinkSubject()" style="margin-top: 5px;"></button>
                    </td>
                </tr>
                <tr>

                    <td class="td_name">是否听取意见</td>
                    <td>
                        <div class="radio_form">
                            <input type="radio" name="adoptFlag" value="1" checked="checked"><span>是</span>
                            <input type="radio" name="adoptFlag" value="0"><span>否</span>
                        </div>
                    </td>
                    <td class="td_name">是否上报</td>
                    <td>
                        <div class="radio_form">
                            <input type="radio" name="approvalFlag" value="1" checked="checked"><span>是</span>
                            <input type="radio" name="approvalFlag" value="0"><span>否</span>
                        </div>
                    </td>
                </tr>
                <td class="td_name"><font id="subjectUserRed" style="color: RED;">*</font>列席人员</td>
                <td colspan="3">
                    <input type="text" id="subjectUser" name="subjectUser" placeholder=" 请输入列席人员" style="margin-top: 5px;" maxlength="80" ErrEmptyCap="列席人员不允许为空">
                    <span style="color: RED;margin-bottom: 6px;float: right;">填报格式：人员（特殊职务说明），多个人意见以逗号分隔。例如：张XX（总法律顾问）,王XX,李XX</span>
                </td>
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>议题决议</td>
                    <td colspan="3"><textarea id="subjectResult" name="subjectResult" placeholder="请输入议题决议" style="width: 100%" maxlength="300" ErrEmptyCap="议题决议不允许为空"></textarea></td>
                </tr>
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>审议结果</td>
                    <td colspan="3">
                        <textarea id="deliberations" name="deliberations" placeholder="请输入审议结果" maxlength="80" ErrEmptyCap="审议结果不允许为空" style="padding-bottom: 0px;margin-bottom: 0px;width: 97%;"></textarea>
                        <button type="button" id="selectAttendeeBt" class="btn_add" onclick="selectDeliberations()" style="margin-top: 25px"></button>
                        <span style="color: RED;margin-bottom: 6px;float: right;">填报格式：人员（是否同意），多个人意见以逗号分隔。例如：张XX（同意）,王XX（同意）,李XX（同意）</span>
                    </td>
                </tr>
                <tr>
                    <td class="td_name">备注</td>
                    <td colspan="3"><textarea id="remark" name="remark" placeholder="请输入备注" maxlength="80" style="width: 100%" value="${subject.remark}"></textarea></td>
                </tr>
            </table>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript">

    /**
     * 保存表单
     */
    function saveForm(){
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
        if (!aForm.checkForm()) return; //表单验证
        var sURL = "/subject/saveSubject.action";
        postJSON(sURL, jt.Form2Json(aForm), function (json, o) {
            if (json.errorCode == 0) { //保存成功
                parent.showSubejctData();
                $("#isUpdate").val("1");
                $("#uploadAttachmentBt").show();
                showMsg("保存成功");
            } else { //保存失败
                showMsg("保存失败");
            }
        });
    }

    /**
     * 校验事项CODE是否对应ID
     */
    function validateItem(object){
        var itemCodes = object.value;
        if(itemCodes == "") return;
        itemCodes = itemCodes.replace(/，/g,',').replace(/、/g,',').trim();
        var itemArr = itemCodes.split(",");
        var matchArr = new Array();
        var sURL = "{contextPath}/item/queryItemListByCompanyId.action?companyId=${subject.companyId}";
        var useNum = 0;
        postJSON(sURL, {'itemCodes':itemCodes},function (json,o){
            if(json || json.length == 0){
                for (var n = 0; n < itemArr.length; n++) {
                    if(itemArr[n] == "") {
                        continue;
                    }else{
                        useNum++;
                    }
                    for (var i = 0; i < json.length; i++) {
                        if(itemArr[n] == json[i].ITEM_CODE){
                            matchArr.push(json[i].ITEM_ID);
                        }
                    }
                    //判断是否匹配
                    if(matchArr.length != useNum){
                        showMsg("编码："+itemArr[n] + ' 未找到匹配的事项');
                        object.focus();
                        return;
                    }
                }
                $("#itemIds").val(matchArr.toString());
            }else{
                showMsg('操作失败');
            }
        });
    }

    /**
     * 上传附件方法
     */
    function uploadAttachment(){
        var subjectId = $('#subjectId').val();
        var sURL = "{contextPath}/jsp/meeting/register/upload_file.jsp?subjectId="+subjectId;
        showDialog("上传附件", sURL, 'uploadAttachmentWin', 400, 200);
    }

    var fileIndex = 0;
    /**
     * 上传成功回调方法
     * 操作：添加附件信息行
     */
    function afterUploadAttachment(fileType,fileName,fileId){
        var html = "<tr id=\"fileTd"+ fileIndex +"\">\n" +
            "<td class=\"td_name\">" + fileType + "</td>\n" +
            "<td colspan=\"3\">\n" +
            "<a herf='javascript:void(0);' onclick='showFileView(\""+fileId+"\")'>"+ fileName +"</a>" +
            "<button type='button' class='btn_del' onclick='deleteFile(\""+fileId+"\",\""+fileIndex+"\")' style='margin-top: 5px;'></button>"+
            "</td>\n" +
            "</tr>";
        $("#subjectTable").append(html);
        fileIndex++;
    }
    /**
     * 删除附件
     */
    function deleteFile(fileId,index){
        var sURL = "/subject/deleteSubjectFileById.action";
        postJSON(sURL, {"fileId":fileId}, function (json, o) {
            if (json.errorCode == 0) {
                //删除本条附件
                $("#fileTd" + index).remove();
                showMsg("附件删除成功");
            } else {
                showMsg("操作失败");
            }
        });
    }
    /**
     * 在线浏览文件
     */
    function showFileView(fileId){
        var href = "/common/show.jsp?fileId="+fileId;
        //top_showDialog("附件详情",href, 'viewFileWin', 1000, 650);
        showWindow(jt.parseURL(href),'附件详情');
    }

    /**
     * 初始化文件
     */
    function initFileData(){
        fileIndex = 0;
        var sURL = "/subject/getSubjectFileById.action";
        postJSON(sURL, {"subjectId":$("#subjectId").val()}, function (json, o) {
            for (var i = 0; i < json.length; i++) {
                afterUploadAttachment(json[i].attachmentType,json[i].attachmentName,json[i].fileId)
            }
        });
    }

    /**
     * 填写审议结果操作
     */
    function selectDeliberations(){
        var meetingId = $('#meetingId').val();
        var sURL = "{contextPath}/jsp/meeting/register/select_deliberation.jsp?meetingId="+meetingId;
        showDialog("审议结果选择", sURL, 'selectDeliberation', 500, 400);
    }
    /**
     * 选择关联会议页面
     */
    function selectLinkSubject(){
        var sURL = "{contextPath}/jsp/meeting/register/subject_link.jsp?companyId=${subject.companyId}";
        showDialog("选择关联会议议题", sURL, 'subjectLinkWin', 650, 420);
    }
</script>
</html>
<%@ include file="/common/inc_bottom.jsp" %>