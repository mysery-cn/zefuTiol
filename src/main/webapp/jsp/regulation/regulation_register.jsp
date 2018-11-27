<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/inc_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>制度填报</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/meeting_form.css" type="text/css"/>
</head>
<body class="BodyEdit"
      actPath="{contextPath}/regulation/"
      actSave="saveRegulation.action"
      actUpdate="">
<div class="form_top">
    <div class="form_top_btn">
        <a class="btn_save" onclick="saveRegulation();">保存</a>
        <a class="btn_upload" onclick="saveRegulation();">提交</a>
    </div>
</div>
<div class="form_main">
    <div class="form_tittle"><p>制度填报</p></div>
    <div class="form_table">
        <div class="form_meetting">
            <form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/regulation/saveRegulation.action">
                <input type="hidden" id="regulationVoteStr" name="regulationVoteStr">
                <table>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>制度名称</td>
                        <td colspan="3">
                            <input type="text" id="" name="regulationName" placeholder="请输入制度名称" ErrLength="80" ErrEmptyCap="制度名称不能为空！">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>制度类型</td>
                        <td>
                            <div class="select_diy">
                                <select class="DropDown_Select" defaultvalue="请选择制度类型"
                                        name="typeCode" ErrEmptyCap="制度类型不能为空！" onchange="showRate();"
                                        urldata="{contextPath}/regulation/queryRegulationTypeList.action"
                                        optiontext="{TYPE_NAME}" optionvalue="{TYPE_CODE}">
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name">审批时间</td>
                        <td colspan="3">
                            <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                                   shownow="" showtime="true" enterindex="1" id="approvalDate"
                                   name="approvalDate" readonly="" Format="YYYY-MM-DD"
                                   errcheckcap="请输入正确的审批时间！" placeholder="请选择">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>生效时间</td>
                        <td colspan="3">
                            <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                                   shownow="" showtime="true" enterindex="1" id="effectiveDate"
                                   name="effectiveDate" readonly="" Format="YYYY-MM-DD"
                                   errcheckcap="请输入正确的生效时间！" placeholder="请选择" ErrEmptyCap="生效时间不允许为空！">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>是否经过审查</td>
                        <td>
                            <div class="radio_form">
                                <input type="radio" name="auditFlag" value="1" onclick="isAudit(this);" checked="checked"><span>是</span>
                                <input type="radio" name="auditFlag" value="0" onclick="isAudit(this);"><span>否</span>
                            </div>
                        </td>
                    </tr>
                    <tr id="auditFileTr">
                        <td class="td_name"><font style="color: RED;">*</font>审查佐证材料</td>
                        <td colspan="3">
                            <input id="auditFileId" type="file" name="auditFile" ErrEmptyCap="审批佐证材料必须上传">
                        </td>
                    </tr>
                    <tr id="rateInputTr" style="display: none;">
                        <td class="td_name"><font style="color: RED;">*</font>应出席人数占比</td>
                        <td colspan="3">
                            <input id="rateInput" type="text" name="rate" placeholder="请输入" onblur="checkRate();" ErrEmptyCap="出席人数占比不能为空！" ErrCheckCap="输入不符合要求！">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>制度表决</td>
                        <td colspan="3" id="regList">
                            <div>
                                <div style="width: 45%;float: left;padding-top: 2px;">
                                    <div style="width: 25%;height:26px;float:left;margin:6px 6px 6px 0;line-height: 26px;">
                                        事项编码:
                                    </div>
                                    <div class="select_diy" style="width: 70%;float: left;">
                                        <select class="DropDown_Select" name="itemId" ErrEmptyCap="事项编码不能为空！"
                                                urldata="{contextPath}/item/queryItemListByPage.action?navLevel=0&amp;catalogCode=ROOT&amp;catalogId=ROOT"
                                                optiontext="{itemName}" optionvalue="{itemCode}">
                                        </select>
                                    </div>
                                </div>
                                <div style="width: 50%;float: left;padding-top: 2px;">
                                    <div style="width: 20%;height:26px;float:left;margin:6px 6px 6px 0;line-height: 26px;">
                                        表决方式:
                                    </div>
                                    <div class="select_diy" style="width: 77%;float: left;">
                                        <select class="DropDown_Select" name="modelId" ErrEmptyCap="表决方式不能为空！"
                                                urldata="{contextPath}/vote/model/queryVoteModelList.action?navLevel=0&amp;catalogCode=ROOT&amp;catalogId=ROOT"
                                                optiontext="{modelName}" optionvalue="{modelId}">
                                        </select>
                                    </div>
                                </div>
                                <button id="addRegBtn" class="del_meetting" style="float:right;margin: 11px 0;"></button>
                            </div>
                            <button id="addRegBtn" class="btn_add" style="float:left;margin: 5px 0 5px 75px;"></button>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>正式文件</td>
                        <td colspan="3">
                            <input type="file" name="file" ErrEmptyCap="正式文件不能为空！">
                        </td>
                    </tr>
                    <tr>
                    <tr>
                        <td class="td_name">备注</td>
                        <td colspan="3"><textarea name="remark" placeholder="请输入备注" ErrLength="85"></textarea></td>
                    </tr>
                    </tr>
                </table>
            </form>
        </div>
        <div class="meeting_line" id="meeting_line" style="display: none;"></div>
    </div>
</div>
<div id="addReg" style="display: none;">
    <div>
        <div style="width: 45%;">
            <div style="width: 25%;height:26px;float:left;margin:6px 6px 6px 0;line-height: 26px;">
                事项编码:
            </div>
            <div class="select_diy" style="width: 70%;float: left;">
                <select class="DropDown_Select" name="itemId" ErrEmptyCap="事项编码不能为空！"
                        urldata="{contextPath}/item/queryItemListByPage.action?navLevel=0&amp;catalogCode=ROOT&amp;catalogId=ROOT"
                        optiontext="{itemName}" optionvalue="{itemCode}">
                </select>
            </div>
        </div>
        <div style="width: 50%;float: left;margin-left: 6px;">
            <div style="width: 20%;height:26px;float:left;margin:6px 6px 6px 0;line-height: 26px;">
                表决方式:
            </div>
            <div class="select_diy" style="width: 77%;float: left;">
                <select class="DropDown_Select" name="modelId"
                        urldata="{contextPath}/vote/model/queryVoteModelList.action?navLevel=0&amp;catalogCode=ROOT&amp;catalogId=ROOT"
                        optiontext="{modelName}" optionvalue="{modelId}">
                </select>
            </div>
        </div>
        <button class="del_meetting" style="float:right;margin: 8px 0;"></button>
    </div>
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp" %>
<script type="text/javascript">
    $(function () {
        // 绑定添加事项编码和表决方式按钮
        $('#regList').on('click', 'button', function (e) {
            e.preventDefault();
            if ($(this).hasClass('btn_add')) {
                $(this).remove();
                var $regList = $('#regList');
                $regList.append($('#addReg').html());
                $regList.append('<button class="btn_add" style="float:left;margin: 5px 0 5px 75px;"></button>');
            } else if ($(this).hasClass('del_meetting')) {
                if ($(this).parent().parent().children().length > 2) {
                    $(this).parent().remove();
                }
            }
        });
    });

    // 检查佐证材料是否必须上传
    function isAudit() {
        if ($('input[name="auditFlag"]:checked').val() == 1) {
            $('#auditFileTr').show();
        }else{
            $('#auditFileTr').hide();
        }
    }

    // 根据制度类型判断是否显示占比
    function showRate(){
        if($('select[name="typeCode"] option:selected').text() === "议事规则"){
            $('#rateInputTr').show();
        }else{
            $('#rateInputTr').hide();
        }
    }

    // 检查输入占比
    function checkRate() {
        var rateStr = $('input[name="rate"]').val();
        if (rateStr) {
            var arr = rateStr.split("/");
            var a = arr[0];
            var b = arr[1];
            var flag = arr.length === 2 && Math.round(a) == a && Math.round(a) == a && a > 0 && b > 0 && a <= b;
            if(!flag){
                jt.Msg.showMsg('输入占比不合理，请重新输入！')
            }
            return flag;
        }
        return false;
    }

    // 保存制度信息
    function saveRegulation(aForm) {
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
        if (!aForm.checkForm()) return; //表单验证
        var itemId = '';
        var modelId = '';
        var str = '';
        $.each($('#regList').children('div'), function(index, div){
            itemId = $(div).find('select[name="itemId"]').val();
            modelId = $(div).find('select[name="modelId"]').val();
            str += itemId + '/' + modelId + ',';
        });
        $('#regulationVoteStr').val(str);
        aForm.submit();//表单提交
    }
</script>