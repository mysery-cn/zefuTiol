<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/inc_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>添加议题指派</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/meeting_form.css" type="text/css"/>
</head>
<script>
    var meetingId = jt.getParam("meetingId");
    function init(){
        jt._("#meetingId").value=meetingId;
        $('#meetingName').val(parent.$('#meetingName').val());
        $('#meetingType').val(parent.$('#meetingType').val());
    }
</script>
<body class="BodyEdit" pageType="EditPage">
<div class="form_top">
    <div class="form_top_btn">
        <a class="btn_close" onclick="closeSelf()">关闭</a>
        <a class="btn_save" onclick="submitForm()">保存</a>
    </div>
</div>
<div class="form_main" style="margin-top: 0px;">
    <div class="form_table">
        <div class="form_issue">
            <form id="frmMain" name="frmMain" class="Validate">
                <table style="margin-top: 0px">
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>议题名称</td>
                        <td colspan="3">
                            <input type="text" id="subjectName" name="subjectName" placeholder=" 请输入议题名称" ErrEmptyCap="议题名称不允许为空">
                            <input type="hidden" id="meetingId" name="meetingId">
                            <input type="hidden" id="meetingType" name="meetingType">
                            <input type="hidden" id="meetingName" name="meetingName">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>事项编码</td>
                        <td colspan="3">
                            <input type="hidden" id="itemIds" name="itemIds">
                            <input type="text" id="itemCode" name="itemCode" onblur="validateItem(this)" ErrEmptyCap="事项编码不允许为空">
                            <input type="hidden" id="catalogIds" name="catalogIds">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>指派人员</td>
                        <td colspan="3">
                            <input type="hidden" id="receiveUserId" name="receiveUserId">
                            <input type="text" onclick="selectUser()" id="receiveUserName" name="receiveUserName" placeholder=" 请选择议题填报人" ErrEmptyCap="议题填报人不允许为空">
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
        top.showLoading('正在保存...');
        var sURL = "/subjectTodo/saveSubjectTodo.action";
        postJSON(sURL, jt.Form2Json(aForm), function (json, o) {
            top.showLoading(false);
            if (json.errorCode == 0) { //保存成功
                parent.showSubject(json.data[0]);
                closeSelf();
            } else { //保存失败
                showMsg(json.errorInfo); return;
            }
        });
    }


    /**
     * 校验事项CODE是否对应ID
     */
    function validateItem(object){
        var companyId = curUser.companyId;
        var itemCodes = object.value;
        if(itemCodes == "") return;
        itemCodes = itemCodes.replace(/，/g,',').replace(/、/g,',').trim();
        var itemArr = itemCodes.split(",");
        var matchArr = new Array();
        var catalogArr = new Array();
        var sURL = "{contextPath}/item/queryItemListByCompanyId.action";
        var useNum = 0;
        postJSON(sURL, {'itemCodes':itemCodes,'companyId':companyId},function (json,o){
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
                            if(inArray(catalogArr,json[i].CATALOG_ID)) catalogArr.push(json[i].CATALOG_ID);
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
                $("#catalogIds").val(catalogArr.toString());
            }else{
                showMsg('操作失败');
            }
        });
    }

    /**
     * 判断某个元素是否在数组中
     * @param arr
     * @param item
     * @returns {boolean}
     */
    function inArray(arr, item) {
        var flag = true;
        for(var i = 0; i < arr.length; i++) {
            if(arr[i] == item) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 选择填报人员
     */
    function selectUser(){
        var sURL = "{contextPath}/jsp/meeting/register/selectUnionUser.jsp";
        showDialog("选择填报人员", sURL, 'selectUser', 400, 400);
    }
</script>
</html>
<%@ include file="/common/inc_bottom.jsp" %>