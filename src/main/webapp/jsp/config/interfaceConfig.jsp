<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <title>参数配置</title>
</head>
<style type="text/css">
    .cfgTitle {font-family:黑体,楷体_GB2312;line-height:30px;font-size:20px;text-align:center;padding:10px 0px;}
</style>
<script type="text/javascript">

    function init(){
        postJSON('{contextPath}/integrationCfg/getInerfaceCfg.action', {} ,function(json, o){
            if(json){
                if(json.ADDRESS){
                    $('input[name="address"]').val(json.ADDRESS);
                }
                if(json.USER_NAME){
                    $('input[name="user_name"]').val(json.USER_NAME);
                }
                if(json.PASSWORD){
                    $('input[name="password"]').val(json.PASSWORD);
                }
            }
        });
    }

    function saveOrUpdate(){
        postJSON(
            '{contextPath}/integrationCfg/saveOrUpdateInerfaceCfg.action',
            {
                address:$('input[name="address"]').val(),
                user_name:$('input[name="user_name"]').val(),
                password:$('input[name="password"]').val()
            },
            function(json, o){
                if(json == 1){
                    jt.Msg.showMsg('保存成功');
                }else{
                    jt.Msg.showMsg('保存失败');
                }
            }
        );
    }

</script>

<body class="BodyEdit">

<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div id="btnSave" icon="save.png" onclick="saveOrUpdate();">保存</div>
    </div>
</div>

<div id="divFixCnt" style="padding:3px;">
    <form id="frmMain" name="frmMain" class="Validate">
        <div style="font-size:16px;font-weight:bold;text-align:center;padding:15px">参数配置</div>
        <div class="TabBase" cssTab="tabItem" cssTabSel="tabItemSel" cssTabOver="tabItemOver" cssCnt="tabCnt" style="padding-left:0px;padding-top:0px;">
            <div class="tabItem">参数设置</div>
            <div class="tabCnt">
                <table width="100%" id="tabbasic" class="TableEdit" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tit">地址：</td>
                        <td class="cnt">
                            <input type="text" class="input" name="address" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tit">用户名：</td>
                        <td class="cnt">
                            <input type="text" class="input" name="user_name" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tit">密码：</td>
                        <td class="cnt">
                            <input type="text" class="input" name="password" value=""/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </form>
</div>

</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
