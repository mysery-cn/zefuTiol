<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
	<title>制度填报</title>
	<style>
		.add_meetting{background: url(<%=SYSURL_static%>/images/meeting/btn_add.png);width: 20px;height: 20px;cursor: pointer;margin-left: 14px;margin-top: 5px}
		.del_meetting{background: url(<%=SYSURL_static%>/images/meeting/btn_del.png);width: 20px;height: 20px;float: left;cursor: pointer;margin: 11px 0;}
		.select_diy{text-align: left;border: 1px solid #dfdfdf;background: #fff;font-size: 14px;max-width: 250px;margin: 6px 0px;width: 70%;float: left;}
		.div_select_group{width: 45%;float: left;padding-top: 2px;}
		.div_select_item{width: 15%;height:26px;float:left;margin:6px 6px 6px 0;line-height: 26px;}
	</style>
</head>
<script type="text/javascript">

	var editRegulationId = jt.getParam("regulationId");
    var status = jt.getParam("status"); //1-新增
	var currEditRegulation = {};

  	//页面加载完成初始调用
	$(document).ready(function(){
		regulationId = '${regulationId}';
		$('#addMeeting_div').on('click',addSelectBox);

        // 绑定添加事项编码和表决方式按钮
        $('#zdbj_div').on('click', 'div.del_meetting', function (e) {
            e.preventDefault();
            if ($(this).parent().parent().children().length >= 2) {
                $(this).parent().remove();
            } else {
                // jt.Msg.showMsg('事项表决至少一项');
			}
        });

        initPage2();
	});

	// 页面初始化
	function initPage2(){
        if(status == 2){
            regulationId = editRegulationId;
            initRegulationEditPage();
        }
        initAfter();
	}

	function initAfter(){
    	jt._('#auditFile_ifm').src = jt.parseURL("{contextPath}/jsp/com_regulation/regulation_attachment.jsp?regulationId=" + regulationId);
    }

	// 如果是编辑制度，则初始化编辑页面
    function initRegulationEditPage(){
        $.ajax({
            url:'/com_regulation/queryRegulationById.action',
            type:"POST",
            async:false,
            data:{
            	regulationId:regulationId
            },
            success:function(editRegulation){
                currEditRegulation = eval('('+editRegulation+')');
                $('#regulationName').val(currEditRegulation.REGULATION_NAME);
                $('#approvalDate').val(currEditRegulation.APPROVAL_DATE);
                $('#effectiveDate').val(currEditRegulation.EFFECTIVE_DATE);
                $('#fileId').val(currEditRegulation.fileId);
                $('#fileName').val(currEditRegulation.fileName);
                $('input[name="auditFlag"][value="' + currEditRegulation.AUDIT_FLAG + '"]').attr('checked', 'checked');
                $('#remark').text(currEditRegulation.REMARK);
            },
            error:function(){
                jt.Msg.showMsg('获取更新制度信息失败！');
            }
        });
    }

    var selectCntFlag = 0;
    function jtAfterDropDownSelectShowData(oComp){
        selectCntFlag = selectCntFlag + 1;
	    if(status == 2) {
            if ($(oComp).attr('name') == 'typeCode') {
                $('select[name="typeCode"] option[value="' + currEditRegulation.TYPE_ID + '"]').attr('selected', 'selected');
                showRate(currEditRegulation.RATE);
            }
            if (selectCntFlag == 5) {
                var $itemVote;
                $.each(currEditRegulation.itemVoteList, function (index, itemVote) {
                    if ($('#zdbj_div').children('div:not(.del_meetting)').size() <= currEditRegulation.itemVoteList.length - 1) {
                        addSelectBox();
                    }
                    $itemVote = $('#zdbj_div').children().eq(index);
                    $itemVote.find('select[name="itemid"] option[value="'+itemVote.ITEM_ID+'"]').attr('selected', 'selected');
                    $itemVote.find('select[name="modelid"] option[value="'+itemVote.MODE_ID+'"]').attr('selected', 'selected');
                });
            }
        }
    }
	
    function addSelectBox(){
    	$('#zdbj_div').append($('#addReg').html());
    }
    
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
    function showRate(rate){
        if($('select[name="typeCode"] option:selected').text() === "议事规则"){
            $('#rateInput').attr("erremptycap", "出席人数占比不能为空！");
            $('#rateInputTr').show();
            if(rate){
                $('#rateInput').val(rate);
			}
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

        $('#regulationId').val(regulationId);

        var itemId = '';
        var modelId = '';
        var str = '';

        $.each($('#zdbj_div').children('div'), function(index, div){
            itemId = $(div).find('select[name="itemid"]').val();
            modelId = $(div).find('select[name="modelid"]').val();
            str += itemId + '/' + modelId + ',';
        });
        $('#regulationVoteStr').val(str);

        if(status == 2){
            $('#isUpdate').val('1');
        }else if(status == 1){
            $('#isUpdate').val('');
        }

        top.showLoading('正在保存...');
        $.ajax({
            url: '/com_regulation/saveRegulation.action',
            type: 'POST',
            contentType: false,
            processData: false,
            data: $('#frmMain').serialize(),
            success: function (data) {
                top.showLoading(false);
                var rs = eval('('+data+')');
                if (rs.result == 'success') {
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
    
    //上传正式文件
    function uploadFile(){
    	var sURL = '{contextPath}/com_regulation/attachmentImport.action?oneFile=1&fileType=SUMMARY';
    	showDialog('上传文件',sURL,'uploadWin',550,350);
    }
    
    //浏览附件
	function showFileView(fileId){
    	if(fileId != null && fileId !=''){
		    var href = "/common/show.jsp?fileId="+fileId;
		    //top_showDialog("附件详情",href, 'viewFileWin', 1000, 650);
		    showWindow(jt.parseURL(href),'附件详情');
    	}
	}
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/com_item/"
	  actSave="saveItem.action"
	  actUpdate="updateItem.action">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div icon="save.png" id="addButton" onclick="saveRegulation()">保存</div>
		<div></div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<input type="hidden" id="regulationId" name="regulationId">
		<input type="hidden" id="isUpdate" name="isUpdate">
		<input type="hidden" id="regulationVoteStr" name="regulationVoteStr">
		<div class=FormTableTitle>制度清单信息</div>
		<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">制度名称：</td>
				<td class="cnt">
					<input type="text" id="regulationName" name="regulationName" placeholder="请输入制度名称" style="width: 260px;" ErrLength="80" ErrEmptyCap="制度名称不能为空！">
				</td>
			</tr>
			<tr> 
				<td class="tit">制度类型：</td>
				<td class="cnt">
                    <select class="DropDown_Select" defaultvalue="请选择制度类型" style="width: 260px;"
                            name="typeCode" ErrEmptyCap="制度类型不能为空！" onchange="showRate();"
                            urldata="{contextPath}/com_regulation/queryRegulationTypeList.action"
                            optiontext="{TYPE_NAME}" optionvalue="{TYPE_ID}">
                    </select>
				</td>
			</tr>
			<tr>
                <td class="tit">审批时间：</td>
                <td class="cnt">
                    <input type="text" style="width: 260px;background-color: #fff;" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                           shownow="" showtime="true" enterindex="1" id="approvalDate"
                           name="approvalDate" readonly="" Format="YYYY-MM-DD" onchange="checkApprovalDate();"
                           errcheckcap="请输入正确的审批时间！" placeholder="请选择" />
                </td>
            </tr>
            <tr>
                <td class="tit">生效时间：</td>
                <td class="cnt">
                    <input type="text" style="width: 260px;background-color: #fff;" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                           shownow="" showtime="true" enterindex="1" id="effectiveDate"
                           name="effectiveDate" readonly="" Format="YYYY-MM-DD" onchange="checkEffectiveDate();"
                           errcheckcap="请输入正确的生效时间！" placeholder="请选择" ErrEmptyCap="生效时间不允许为空！" />
                </td>
            </tr>
			<tr>
			    <td class="tit">是否经过审查：</td>
                <td class="cnt" nowrap="nowrap">
                    <div class="radio_form">
                        <input type="radio" name="auditFlag" value="1" onclick="isAudit(this);" checked="checked"><span>是</span>
                        <input type="radio" name="auditFlag" value="0" onclick="isAudit(this);"><span>否</span>
                    </div>
                </td>
			</tr>
			<tr id="deptTitle"  HiddenField="!Id">
				<td class="tit">审查佐证材料：</td>
				<td class="cnt"><iframe id="auditFile_ifm" src="" scrolling="no" frameborder="no" border="0" marginwidth="0" marginheight="0" width="100%"></iframe></td>
			</tr>
			<tr id="rateInputTr" style="display: none;">
                <td class="tit">应出席人数占比：</td>
                <td class="cnt">
                    <input id="rateInput" type="text" name="rate" style="width: 260px;" placeholder="请输入" onblur="checkRate();" ErrCheckCap="输入不符合要求！">
                </td>
            </tr>
            <tr>
                <td class="tit">制度表决</td>
                <td class="cnt" id="regList">
					<div id="zdbj_div">
						<div>
							<div class="div_select_group">
								<div class="div_select_item">
									事项编码:
								</div>
								<div class="select_diy">
									<select style="width:100%;" class="DropDown_Select" name="itemid"
											erremptycap="事项编码不能为空！"
											urldata="{contextpath}/com_item/queryItemListByCompanyId.action"
											optiontext="[{ITEM_CODE}]{ITEM_NAME}" optionvalue="{ITEM_ID}">
									</select>
								</div>
							</div>
							<div class="div_select_group">
								<div class="div_select_item">
									表决方式:
								</div>
								<div class="select_diy">
									<select style="width: 100%;" class="DropDown_Select" name="modelid"
											erremptycap="表决方式不能为空！"
											urldata="{contextPath}/com_regulation/queryVoteModelList.action"
											optiontext="{modeName}" optionvalue="{modeId}">
									</select>
								</div>
							</div>
							<div class="del_meetting"></div>
						</div>
					</div>
                    <div id="addMeeting_div" class="add_meetting" style="float:left;margin: 5px 0 5px 75px;"></div>
                </td>
            </tr>
            <tr>
                <td class="tit">正式文件</td>
                <td class="cnt">
                	<input type="button" value="选择文件" onclick="uploadFile();" />
                	<input type="text" name="fileName" id="fileName" onclick="showFileView({fileId});" readonly="readonly" ErrEmptyCap="正式文件不能为空！" style="cursor: pointer;background-color:#fff; width: 260px;" />
                	<input type="hidden" name="fileId" id="fileId" />
                </td>
            </tr>
			<tr>
				<td class="tit">备注：</td>
				<td class="cnt">
					<textarea id="remark" rows="5" cols="7" name="remark" ErrLength="85">${remark}</textarea>
				</td>
			</tr>
		</table>
	</form>
	<div id="addReg" style="display: none;">
		<div>
			<div class="div_select_group">
				<div class="div_select_item">
					事项编码:
				</div>
				<div class="select_diy">
					<select id="addRegItemFlag" style="width: 100%" class="DropDown_Select" name="itemid" erremptycap="事项编码不能为空！"
							urldata="{contextpath}/com_item/queryItemListByCompanyId.action"
							optiontext="[{ITEM_CODE}]{ITEM_NAME}" optionvalue="{ITEM_ID}">
					</select>
				</div>
			</div>
			<div class="div_select_group">
				<div class="div_select_item">
					 表决方式:
				</div>
				<div class="select_diy">
					<select id="addRegVoteFlag" style="width: 100%" class="DropDown_Select" name="modelid" erremptycap="表决方式不能为空！"
							 urldata="{contextPath}/com_regulation/queryVoteModelList.action"
							 optiontext="{modeName}" optionvalue="{modeId}">
					</select>
				</div>
			</div>
			<div class="del_meetting"></div>
		</div>
	</div>
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>