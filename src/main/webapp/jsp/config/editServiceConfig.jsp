<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <title>新增服务配置</title>
</head>
<script type="text/javascript">

    var serviceConfigId = jt.getParam("serviceConfigId");

    function init(){
        if(serviceConfigId != null && serviceConfigId != ''){
            var sURL = '{contextPath}/integrationCfg/getServiceConfig.action';
            postJSON(sURL, {id: serviceConfigId}, function (json, o) {
                $('input[name="id"]').val(json.id);
                $('input[name="system_name"]').val(json.system_name);
                $('input[name="organization_name"]').val(json.organization_name);
                $('input[name="user_name"]').val(json.user_name);
                $('input[name="password"]').val(json.password);
            });
        }
    }

    function saveServiceConfig(){
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
        if (!aForm.checkForm()) return; //表单验证

        showLoading('正在保存...');
        var sURL = '{contextPath}/integrationCfg/saveOrUpdateServiceConfig.action';
        postJSON(sURL, jt.Form2Json(aForm), function (json, o) {
            showLoading(false);
            if (json == 1) { //保存成功
                jt.Msg.alert("保存成功!", function () {
                    reloadFrameMainGrid(false, aForm);
                    closeSelf();
                });
            } else { //保存失败
                jt.Msg.showMsg('保存失败');
            }
        });
    }

</script>

<body class="BodyEdit">

<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div id="btnClose" icon="close.png" onclick="closeSelf();" style="display:none;">关闭</div>
        <div icon="save.png" onclick="saveServiceConfig();">保存</div>
    </div>
</div>

<div id="divFixCnt" style="padding:3px;">
    <form id="frmMain" name="frmMain" class="Validate">
        <input type="hidden" name="id"/>
        <table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
            <tr>
                <td class="tit">系统名称：</td>
                <td class="cnt">
                    <input type="text" class="input" name="system_name" maxlength="64" ErrEmptyCap=""/>
                </td>
            </tr>
            <tr>
                <td class="tit">对接单位：</td>
                <td class="cnt">
                    <input type="text" class="input" name="organization_name" maxlength="64" ErrEmptyCap=""/>
                </td>
            </tr>
            <tr>
                <td class="tit">用户名：</td>
                <td class="cnt">
                    <input type="text" class="input" name="user_name" maxlength="64" ErrEmptyCap=""/>
                </td>
            </tr>
            <tr>
                <td class="tit">密码：</td>
                <td class="cnt">
                    <input type="text" class="input" name="password" maxlength="64" ErrEmptyCap=""/>
                </td>
            </tr>
        </table>
    </form>
</div>

</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
