<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/inc_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>会议协同填报</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/meeting_form.css" type="text/css"/>
</head>
<script type="text/javascript">
    var companyId = curUser.companyId;
    jQuery(function($) {
        $("#reCompanyId").val(companyId);
        $("#companyId").val(companyId);
        //$("#reMeetingId").val("7a3c7216aba14e70a9c5d7a35e46724c");
        //$("#reCompanyId").val("91310000132200821H");
        //$("#companyId").val(curUser.companyId);
        var meetingId = $("#reMeetingId").val();
        if(meetingId != "") {
            $("#addSubjectsBtn").show();
            if("${flag}") {
                showMsg("会议保存成功");
                parent._('#tabMain_List').loadData();
            }
            reInitForm();//回填原数据
        }
    });
</script>
<body class="BodyEdit" pageType="EditPage">
<div class="form_top">
    <div class="form_top_btn">
        <a class="btn_close" href="javascript:void(0)" onclick="closeSelf()">关闭</a>
        <a class="btn_save" id="saveBt" onclick="submitForm()">保存</a>
        <a class="btn_save" onclick="addSubjects()" id="addSubjectsBtn" style="display: none">新增议题</a>
    </div>
</div>
<div class="form_main">
    <input type="hidden" id="reMeetingId" value="${meeting.MEETINGID}">
    <input type="hidden" id="reCompanyId" value="${meeting.COMPANYID}">
    <div class="form_tittle"><p>会议协同填报</p></div>
    <div class="form_table">
        <div class="form_meetting">
            <form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/meeting/saveMeeting.action">
            <table id="meetingTable">
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>会议名称</td>
                    <td colspan="3">
                        <input type="text" id="meetingName" name="meetingName"  placeholder="请输入会议名称" ErrEmptyCap="会议名称不允许为空" >
                        <input type="hidden" id="meetingId" name="meetingId" >
                        <%--录入类型 0批量导入 1集中填报 2协同填报--%>
                        <input type="hidden" id="registerType" name="registerType" value="2">
                        <%--上传状态 0 待上传、1 已上传--%>
                        <input type="hidden" id="uploadStatus" name="uploadStatus" value="${meeting.UPLOADSTATUS}">
                        <%--删除状态 0删除  1新增  2修改--%>
                        <input type="hidden" id="status" name="status" value="${meeting.STATUS}">
                        <input type="hidden" id="companyId" name="companyId">
                    </td>
                </tr>
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>会议类型</td>
                    <td>
                            <select class="DropDown_Select" onchange="fillTypeName(this);" name="meetingType" id="meetingType"
                                    urldata="{contextPath}/meetingType/queryMeetingTypeListByPage.action" DefaultValue="${meeting.TYPEID}"
                                    optiontext="{typeName}" optionvalue="{typeId}" style="border:1px solid #dfdfdf;width: 100%;">
                            </select>
                            <input type="hidden" name="typeName" id="typeName">
                    </td>
                    <td class="td_name"><font style="color: RED;">*</font>会议时间</td>
                    <td>
                        <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                               shownow="" showtime="true" enterindex="1" id="meetingTime"
                               name="meetingTime" readonly="" onblur="sourceReceiveTimeBlur(this)"
                               errcheckcap="请输入正确的会议日期！" placeholder="请选择" ErrEmptyCap="会议时间不允许为空">
                    </td>
                </tr>
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>主持人</td>
                    <td colspan="3">
                        <input type="text" id="moderator" name="moderator" ErrEmptyCap="主持人不允许为空">
                    </td>
                </tr>
                <tr>
                    <td class="td_name"><font style="color: RED;">*</font>参会人</td>
                    <td colspan="3">
                        <textarea id="attendeeMember" name="attendeeMember" type="text" placeholder="请输入" style="padding-bottom: 0px;margin-bottom: 0px;width: 100%"></textarea>
                        <span style="color: RED;margin-bottom: 6px;float: right">多个参会人员请以逗号分开，缺席原因用括号说明。例如：张XX,王XX(因公出国),李XX</span>
                    </td>
                </tr>
                <tr>
                    <td class="td_name"><font id="noticeFileRed" style="color: RED;">*</font>会议通知</td>
                    <td id="noticeFileTd">
                        <input type="file" name="noticeFile" id="noticeFile" ErrEmptyCap="会议通知不允许为空" accept=".ofd,.pdf">
                    </td>
                    <td class="td_name"><font style="color: RED;">*</font>会议纪要</td>
                    <td id="summaryFileTd">
                        <input type="file" name="summaryFile" id="summaryFile" ErrEmptyCap="会议纪要不允许为空" accept=".ofd,.pdf">
                        <input type="hidden" name="page" value="meeting_union_register">
                    </td>
                </tr>
            </table>
            </form>
        </div>
        <div class="meeting_line" id="meeting_line" style="display: none;"></div>
    </div>
    <div id="subject_msg" style="overflow: auto;max-height: 200px;">

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
        aForm.submit();//表单提交
    }

    /**
     * 添加议题弹窗
     */
    function addSubjects(){
        var meetingId = $("#reMeetingId").val();//会议ID
        if(meetingId == ""){
            top.showMsg("请先保存会议信息！");
        }else{
            var sURL = "{contextPath}/jsp/meeting/register/subject_union_add.jsp?meetingId="+meetingId;
            showDialog("新增指派", sURL, 'subjectEditWin', 600, 500);
        }
    }
    /**
     * 查询指派议题信息
     */
    function showSubejctData(){
        subjectIndex = 0;
        $('#subject_msg').empty();
        $.ajax({
            url : "/subjectTodo/getSubjectTo.action",
            data:{
                meetingId:$("#meetingId").val()
            },
            dataType:"json",
            type : "POST",
            async: false,
            success: function (result) {
                for (var i = 0; i < result.length; i++) {
                    showSubject(result[i]);
                }
            }
        });
    }
    /**
     * 显示指派议题信息
     */
    var subjectIndex = 0;
    function showSubject(data){
        var html="<div class='table_expand' style='margin-top: 10px'><div class='table_expand_left'>"+
            "<img id='expandIcon"+subjectIndex+"'><a herf='javascript:void(0);' onclick='doEditSubject(\""+data.subjectId+"\",\""+data.meetingId+"\",\""+$("#companyId").val()+"\",\""+data.subjectName+"\",\""+data.status+"\",\""+data.receiveUserId +"\",\""+data.itemCode+"\",\""+data.itemId+"\")'>议题"+(subjectIndex+1)+":"+data.subjectName +"</a></div>"+
            "<div class='table_expand_right'><span style='margin-right: 10px'>填报人："+ data.receiveUserName +"</span><img id='passIcon"+subjectIndex+"''><p>是否填报：</p></div></div>";
        $('#subject_msg').append(html);
        if(data.status == "2"){
            $('#passIcon'+subjectIndex).attr("src",'<%=SYSURL_static%>/images/tiol/ico_pass.png');
        }else{
            $('#passIcon'+subjectIndex).attr("src",'<%=SYSURL_static%>/images/tiol/ico_no.png');
        }
        $('#expandIcon'+subjectIndex).attr("src",'<%=SYSURL_static%>/images/tiol/ico_expand_b.png');
        subjectIndex++;
    }


    /**
     * 选择会议类型后记录会议类型名称
     * @param obj
     */
    function fillTypeName(obj){
        var index = obj.selectedIndex;
        var selectText = obj.options[index].text;
        $('#typeName').val(selectText);
        loadCompanyMembers();//根据会议类型加载最近会议的主持人与参会人

        //董事长与其他会议类型
        if(selectText !="" && (selectText.indexOf('董事长')>-1 || selectText.indexOf('其他会议')>-1)){
            $("#noticeFileRed").hide();
            $("#noticeFile").removeAttr("ErrEmptyCap");
        }else{
            $("#noticeFileRed").show();
            $("#noticeFile").attr("ErrEmptyCap","会议通知不允许为空");
        }
    }

    /**
     * 加载主持人与参会人员
     */
    function loadCompanyMembers(){
        $("#moderator").val("");
        $("#attendeeMember").val("");
        var typeId = $('#meetingType').val();
        if(typeId == "") {
            return;
        }
        var sURL = "/meeting/queryMeetingAttendee.action";
        postJSON(sURL, {'typeId':typeId},function (json,o){
            if(json){
                $("#moderator").val(json.moderator);
                $("#attendeeMember").val(json.attendeeMember);
            }
        });
    }

    /**
     * 会议信息回填
     */
    function reInitForm(){
        $("#meetingName").val("${meeting.MEETINGNAME}");
        $("#meetingId").val("${meeting.MEETINGID}");
        $("#meetingTime").val("${meeting.MEETINGTIME}");
        $("#companyId").val("${meeting.COMPANYID}");
        $("#companyName").val("${meeting.COMPANYNAME}");
        loadCompanyMembers();//加载主持人下拉菜单
        $("#attendeeMember").val("${meeting.ATTENDEEMEMBER}");

        var noticeFile_html = "";
        var summaryFile_html = "";

        //是否为待办跳转过来
        if("${meeting.isTodo}" == 1) {
            noticeFile_html = "<a>"+"${meeting.NOTICEFILE}"+"</a>";
            summaryFile_html = "<a>"+"${meeting.SUMMARYFILE}"+"</a>";
            disableTable();//待办跳转过来的表单不可编辑
        }else{
            noticeFile_html = "<a onclick='showFileView(\""+"${meeting.NOTICEFILEID}"+"\")'>"+"${meeting.NOTICEFILE}"+"</a>";
            summaryFile_html = "<a onclick='showFileView(\""+"${meeting.SUMMARYFILEID}"+"\")'>"+"${meeting.SUMMARYFILE}"+"</a>";
        }

        if("${meeting.NOTICEFILEID}"){
            $("#noticeFile").hide();
            $("#noticeFile").removeAttr("ErrEmptyCap");
            $("#noticeFileTd").append(noticeFile_html);
        }
        if("${meeting.SUMMARYFILEID}"){
            $("#summaryFile").hide();
            $("#summaryFile").removeAttr("ErrEmptyCap");
            $("#summaryFileTd").append(summaryFile_html);
        }
        showSubejctData();//加载指派议题信息
    }
    /**
     * 会议表单不可编辑
     */
    function disableTable(){
        $("#saveBt").hide();
        $("#meetingName").attr("disabled", "disabled");
        $("#meetingId").attr("disabled", "disabled");
        $("#meetingTime").attr("disabled", "disabled");
        $("#meetingType").attr("disabled", "disabled");
        $("#moderator").attr("disabled", "disabled");
        $("#companyId").attr("disabled", "disabled");
        $("#companyName").attr("disabled", "disabled");
        $("#attendeeMember").attr("disabled", "disabled");
        $("#saveBt").hide();
        $("#selectAttendeeBt").attr("disabled", "disabled");
        $("#addSubjectsBtn").hide();
        $("#noticeFile").hide();
        $("#summaryFile").hide();
    }

    /**
     * 刷新当前表单
     */
    function reload(){
        var meetingId = $("#reMeetingId").val();
        window.location.href = "/meeting/reloadMeeting.action?meetingId="+meetingId+"&page=meeting_union_register";
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
     * 显示隐藏议题信息
     */
    function showHideSubject(num){
        if($('#subject'+num).is(':hidden')){
            $('#expandIcon'+num).attr("src",'<%=SYSURL_static%>/images/tiol/ico_expand_a.png');
            $('#subject'+num).show();
        }else{
            $('#expandIcon'+num).attr("src",'<%=SYSURL_static%>/images/tiol/ico_expand_b.png');
            $('#subject'+num).hide();
        }
    }

    /**
     * 编辑议题 带入待办中保存的会议名称和ID、事项编码与ID
     */
    function doEditSubject(subjectId,meetingId,companyId,subjectName,status,receiveUserId,itemCode,itemId){
        //限制编辑权限
        if(curUser.userId != receiveUserId){
            showMsg("非填报人无权编辑");
            return;
        }
        var sURL = "";
        if(status == '1'){
            sURL = "{contextPath}/jsp/meeting/register/subject_add.jsp?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId+"&subjectName="+encodeURI(subjectName)+"&itemCode="+itemCode+"&itemId="+itemId;
        }else{
            sURL = "{contextPath}/subject/toSubjectEdit.action?companyId="+companyId+"&subjectId="+subjectId;
        }
        showDialog("议题编辑", sURL, 'subjectEditWin', 1000, 600, true);
    }
</script>
</html>
<%@ include file="/common/inc_bottom.jsp" %>