<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
    <title>制度分类信息</title>
</head>
<script type="text/javascript">
    var modelId = jt.getParam("modelId");

    var status;
    if (modelId == null || modelId == ""){
        status = true;
    }else{
        status = false;
    }

    function initEditPageBefore(){
        var role = curUser.adminRoleId;
        var roleList= curUser.roleListStr;
        //配置管理员及超级管理员可以修改
        if(role != "configAdminRole" && roleList.indexOf("SYS_9999") ==-1){
            $('#addButton').hide();
            $('#addButton').removeAttr('onclick');//去掉标签中的onclick事件
            $('#frmMain input').attr('readonly','readonly');
            $('#frmMain select').attr('disabled','true');
        }
        if (modelId != null && modelId != ""){
            var dataURL = "{contextPath}/vote/model/queryVoteModelDetail.action?modelId=" + modelId;
            var jPost= {};
            postJSON(dataURL,jPost,function(json,o){
                if (!json || json.errorCode != "0") {
                    return jt.Msg.showMsg("获取数据失败！");
                } else {
                    if(json.data.length > 0){
                        jt._('#modelId').value = json.data[0].modelId;
                        jt._('#modelRadix').value = json.data[0].modelRadix;
                        jt._('#modelRate').value = json.data[0].modelRate;
                        jt._('#modelName').value = json.data[0].modelName;
                    }
                }
            },false);
        }
    }

    function saveCatalog(aForm) {
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
        if (!aForm.checkForm()) return; //表单验证

        //处理CodeEditor
        var objs = jt._('textarea.CodeEditor', aForm);
        for (var i=0;i<objs.length;i++) { try{ objs[i].getValue(); }catch(e){} }

        //disableEmptyField(aForm,'UI_ID');//将部分为空的输入框禁用

        var sURL = status ? actSave : actUpdate;

        top.showLoading('正在保存...');
        postJSON(sURL, jt.Form2Json(aForm), function (json, o) {
            top.showLoading(false);
            if (json.errorCode == 0) { //保存成功
                //[Event] EditPage保存成功后调用 EditPage_SaveSuccess(json) @Tag editPage
                if (typeof(EditPage_SaveSuccess) == 'function') {
                    EditPage_SaveSuccess(json);
                } else {
                    reloadFrameMainGrid(false,aForm);
                    //closeSelf();
                }
                if (typeof(submitResAuth) == 'function') submitResAuth(); //保存权限
            } else { //保存失败
                //[Event] EditPage保存失败后调用 EditPage_SaveError(json) @Tag editPage
                if (typeof(EditPage_SaveError) == 'function') {
                    EditPage_SaveError(json);
                } else {
                    showMsg(json.errorInfo); return;
                }
            }
        });
    }

</script>

<body class="BodyEdit" pageType="EditPage"
      actPath="{contextPath}/vote/model/"
      actSave="saveVoteModel.action"
      actUpdate="updateVoteModel.action">
<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div icon="close.png" onclick="closeSelf();">关闭</div>
        <div icon="save.png" id="addButton" onclick="saveCatalog()">保存</div>
        <div></div>
    </div>
</div>

<div id="divFixCnt" style="padding:3px;">
    <form id="frmMain" name="frmMain" class="Validate">
        <DIV class=FormTableTitle>制度分类信息</DIV>
        <table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
            <input type="hidden" class="input" name="modelId" id="modelId" maxlength="64"   />
            <tr>
				<td class="tit">表决方式名称：</td>
				<td class="cnt"><input type="text" class="input" name="modelName" ErrEmptyCap="表决方式名称不允许为空"/></td>
			</tr>
			<tr>
				<td class="tit">表决计算基数：</td>
				<td class="cnt"><input type="text" class="input" name="modelRadix" ErrEmptyCap="表决计算基数不允许为空"/></td>
			</tr>
			<tr>
				<td class="tit">表决通过比例：</td>
				<td class="cnt"><input type="text" class="input" name="modelRate" /></td>
			</tr>
        </table>
    </form>
</div>
</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>