<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/inc_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>制度填报</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/meeting_form.css" type="text/css"/>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/jquery/jquery-1.9.1.min.js" charset="utf-8"></script>
    <script type="text/javascript">
    function initEditPageBefore(){
    var regulationId = jt.getParam("regulationId");
    var status = jt.getParam("status"); //1-新增
    	if (status != null && status == 1){
    		//新增
    		jt._('#regulationId').value = regulationId;
    	}else{ 
    		//修改
			var dataURL = "{contextPath}/com_regulation/queryRegulationById.action?regulationID=" + regulationId;
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					if(json.data.length > 0){
						/*
						jt._('#catalogId').value = json.data[0].catalogId;
						jt._('#itemId').value =   json.data[0].itemId;
						jt._('#itemCode').value = json.data[0].itemCode;
						jt._('#itemName').value = json.data[0].itemName;
						jt._('#oitemCode').value = json.data[0].itemCode;
						jt._('#oitemName').value = json.data[0].itemName;
						jt._('#legalFlag').value = json.data[0].legalFlag;
						if(jt._('#legalFlag').value==1){
							$("input[name=legalFlag]:eq(0)").attr("checked",'checked'); 
						}else{
							$("input[name=legalFlag]:eq(1)").attr("checked",'checked'); 
						}
						jt._('#remark').value = json.data[0].remark;
						listMeetingType();
						listMeetingTypeSelect(json.data[0].meetingTypeList,meetingTypesJson);
						*/
					}
				}
			},false);
		}
	}
    
    </script>
</head>
<body class="BodyEdit"
      actPath="{contextPath}/com_regulation/"
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
            <form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/com_regulation/saveRegulation.action">
                <input type="hidden" id="regulationId" name="regulationId">
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
                                        urldata="{contextPath}/com_regulation/queryRegulationTypeList.action"
                                        optiontext="{TYPE_NAME}" optionvalue="{TYPE_ID}">
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name">审批时间</td>
                        <td colspan="3">
                            <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                                   shownow="" showtime="true" enterindex="1" id="approvalDate"
                                   name="approvalDate" readonly="" Format="YYYY-MM-DD" onchange="checkApprovalDate();"
                                   errcheckcap="请输入正确的审批时间！" placeholder="请选择">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>生效时间</td>
                        <td colspan="3">
                            <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                                   shownow="" showtime="true" enterindex="1" id="effectiveDate"
                                   name="effectiveDate" readonly="" Format="YYYY-MM-DD" onchange="checkEffectiveDate();"
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
                        	<!-- 
                            <input id="auditFileId" type="file" name="auditFile" ErrEmptyCap="审批佐证材料必须上传">
                             -->
                            <div id="divFixMain" class="GridOnly">
								<table class="Grid" style="text-align:center;" FixHead="true" id="auditFile_List" border="0" cellspacing="0" cellpadding="0"
									   URLData="{contextPath}/com_regulation/getFileById.action?subjectId={regulationId}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
									<col boxName="txtID" width="30" boxValue="{attachmentId}" boxAttr="{boxAttr}">
									<col caption="文件名称" field="{attachmentName}" width="50" align="center">
								</table>
							</div>
                        </td>
                    </tr>
                    <tr id="rateInputTr" style="display: none;">
                        <td class="td_name"><font style="color: RED;">*</font>应出席人数占比</td>
                        <td colspan="3">
                            <input id="rateInput" type="text" name="rate" placeholder="请输入" onblur="checkRate();" ErrCheckCap="输入不符合要求！">
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
                                        <select class="dropdown_select" name="itemid" erremptycap="事项编码不能为空！"
                                                urldata="{contextpath}/com_item/queryItemListByCompanyId.action"
                                                optiontext="{ITEM_CODE}" optionvalue="{ITEM_ID}">
                                        </select>
                                    </div>
                                </div>
                                <div style="width: 50%;float: left;padding-top: 2px;">
                                    <div style="width: 20%;height:26px;float:left;margin:6px 6px 6px 0;line-height: 26px;">
                                       	 表决方式:
                                    </div>
                                    <div class="select_diy" style="width: 77%;float: left;">
                                        <select class="dropdown_select" name="modelid" erremptycap="表决方式不能为空！"
                                                 urldata="{contextPath}/com_regulation/queryVoteModelList.action"
                       							 optiontext="{modeName}" optionvalue="{modeId}">
                                        </select>
                                    </div>
                                </div>
                                <button class="del_meetting" style="float:right;margin: 11px 0;"></button>
                            </div>
                            <button class="btn_add" style="float:left;margin: 5px 0 5px 75px;"></button>
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
                        urldata="{contextPath}/com_item/queryItemListByCompanyId.action"
                        optiontext="{ITEM_CODE}" optionvalue="{ITEM_ID}">
                </select>
            </div>
        </div>
        <div style="width: 50%;float: left;margin-left: 6px;">
            <div style="width: 20%;height:26px;float:left;margin:6px 6px 6px 0;line-height: 26px;">
            	表决方式:
            </div>
            <div class="select_diy" style="width: 77%;float: left;">
                <select class="DropDown_Select" name="modelId"
                        urldata="{contextPath}/com_regulation/queryVoteModelList.action"
                        optiontext="{modeName}" optionvalue="{modeId}">
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

    // 检查审批时间
    function checkApprovalDate(){
        var approvalDate = $('#approvalDate').val();
        var effectiveDate = $('#effectiveDate').val();
        if(approvalDate && effectiveDate){
            if(approvalDate > effectiveDate){
                $('#approvalDate').val('').focus();
                jt.Msg.showMsg('审批时间不能大于生效时间');
                return false;
            }
        }
        return true;
    }

    // 检查生效时间
    function checkEffectiveDate(){
        var approvalDate = $('#approvalDate').val();
        var effectiveDate = $('#effectiveDate').val();
        if(approvalDate && effectiveDate){
            if(approvalDate > effectiveDate){
                $('#effectiveDate').val('').focus();
                jt.Msg.showMsg('生效时间不能小于审批时间');
                return false;
            }
        }
        return true;
    }

    // 检查佐证材料是否必须上传
    function isAudit() {
        if ($('input[name="auditFlag"]:checked').val() == 1) {
            $('#auditFileId').attr("erremptycap", "审批佐证材料必须上传！");
            $('#auditFileTr').show();
        }else{
            $('#auditFileId').removeAttr("erremptycap");
            $('#auditFileTr').hide();
        }
    }

    // 根据制度类型判断是否显示占比
    function showRate(){
        if($('select[name="typeCode"] option:selected').text() === "议事规则"){
            $('#rateInput').attr("erremptycap", "出席人数占比不能为空！");
            $('#rateInputTr').show();
        }else{
            $('#rateInput').removeAttr("erremptycap");
            $('#rateInputTr').hide();
        }
    }

    // 检查输入占比
    function checkRate() {
        if($('select[name="typeCode"] option:selected').text() === "议事规则") {
            var rateStr = $('input[name="rate"]').val();
            if (rateStr) {
                var arr = rateStr.split("/");
                var a = arr[0];
                var b = arr[1];
                var flag = arr.length === 2 && Math.round(a) == a && Math.round(a) == a && a > 0 && b > 0 && a <= b;
                if (!flag) {
                    jt.Msg.showMsg('输入占比不合理，请重新输入！')
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    // 保存制度信息
    function saveRegulation(aForm) {
        if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));

        if (!aForm.checkForm()) return; //表单验证
        if(!checkEffectiveDate()) return;
        if($('select[name="typeCode"] option:selected').text() === "议事规则") {
            if (!checkRate()) return;
        }

        var itemId = '';
        var modelId = '';
        var str = '';

        $.each($('#regList').children('div'), function(index, div){
            itemId = $(div).find('select[name="itemId"]').val();
            modelId = $(div).find('select[name="modelId"]').val();
            str += itemId + '/' + modelId + ',';
        });
        $('#regulationVoteStr').val(str);
        //aForm.submit();//表单提交

        top.showLoading('正在保存...');
        $.ajax({
            url: '/com_regulation/saveRegulation.action',
            type: 'POST',
            contentType: false,
            processData: false,
            data: new FormData(document.getElementById('frmMain')),
            success: function (data) {
                top.showLoading(false);
                if (data.result === 'success') {
                    jt.Msg.alert("保存成功!", function () {
                    	reloadFrameMainGrid(false,aForm);
                        closeSelf();
                    });
                }else{
                    jt.Msg.alert("保存失败!", function () {

                    });
                }
            },
            error: function () {
                top.showLoading(false);
                jt.Msg.alert("保存失败!", function () {

                });
            }
        });
    }
</script>