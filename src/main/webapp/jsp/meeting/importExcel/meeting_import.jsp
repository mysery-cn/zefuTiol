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
        validateSuccess();//校验是否保存成功
    }
    function validateSuccess(){
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
        var flag = "${flag}";
        var errMsg = "${errMsg}";
        if(flag && flag == 'true') {
            showMsg("会议信息导入成功,附件请手动上传",3000);
            reloadFrameMainGrid(false,aForm);
            closeSelf();
        }else if(flag == 'false'){
            showMsg(errMsg,5000);
            reloadFrameMainGrid(false,aForm);
            closeSelf();
        }
    }

</script>
<body class="BodyEdit" pageType="EditPage">
<div class="form_top">
    <div class="form_top_btn">
        <a class="btn_close" onclick="closeSelf()">关闭</a>
        <a class="btn_save" onclick="submitForm()">导入</a>
    </div>
</div>
<div class="form_main" style="margin-top: 0px;">
    <div class="form_table">
        <div class="form_issue">
            <form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/import/importMeeting.action?t={JSTime}">
            <table style="margin-top: 0px">
                <tr>
                    <td class="td_name">选择模板</td>
                    <td colspan="3">
                        <input type="file" name="meetingFile" placeholder="请选择模板excel" accept=".xls,.xlsx">
                    </td>
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
    function submitForm(){
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
        if (!aForm.checkForm()) return; //表单验证
        top.showLoading('正在上传数据...');
        aForm.submit();//表单提交
    }

</script>
</html>
<%@ include file="/common/inc_bottom.jsp" %>