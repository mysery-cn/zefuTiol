<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/inc_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>会议填报</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/meeting_form.css" type="text/css"/>
</head>
<script type="text/javascript">

    jQuery(function($) {
        var companyId = curUser.companyId;
        $("#reCompanyId").val(companyId);
        $("#companyId").val(companyId);

        var meetingId = $("#reMeetingId").val();
        if(meetingId) {
            $("#addSubjectsBtn").show();
            if("${flag}") {
                showMsg("会议保存成功");
                parent._('#tabMain_List').loadData();//刷新父页面列表
            }
            reInitForm();//回填原数据
        }
    });
</script>
<body class="BodyEdit" pageType="EditPage">
<div class="form_top">
    <div class="form_top_btn">
        <a class="btn_close" href="javascript:void(0)" onclick="closeSelf()">关闭</a>
        <a class="btn_save" onclick="submitForm()">保存</a>
        <a class="btn_add1" onclick="addSubjects()" id="addSubjectsBtn" style="display: none">新增议题</a>
    </div>
</div>
<div class="form_main">
    <input type="hidden" id="reMeetingId" value="${meeting.MEETINGID}">
    <input type="hidden" id="reCompanyId" value="${meeting.COMPANYID}">
    <div class="form_tittle"><p>会议填报</p></div>
    <div class="form_table">
        <div class="form_meetting">
            <form id="frmMain" name="frmMain" class="Validate" method="post" enctype="multipart/form-data"  action="/meeting/saveMeeting.action">
                <table>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>会议名称</td>
                        <td colspan="3">
                            <input type="text" id="meetingName" name="meetingName"  placeholder="请输入会议名称" ErrEmptyCap="会议名称不允许为空" >
                            <input type="hidden" id="meetingId" name="meetingId" >
                            <%--录入类型 0批量导入 1集中填报 2协同填报--%>
                            <input type="hidden" id="registerType" name="registerType" value="1">
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
                            <input type="hidden" name="typeName" id="typeName"/>
                        </td>
                        <td class="td_name"><font style="color: RED;">*</font>会议时间</td>
                        <td>
                            <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                                   shownow="" showtime="true" enterindex="1" id="meetingTime"
                                   name="meetingTime" readonly=""
                                   errcheckcap="请选择正确的会议日期！" placeholder="请选择" ErrEmptyCap="会议时间不允许为空">
                        </td>
                    </tr>
                    <tr>
                        <%--<td class="td_name"><font style="color: RED;">*</font>单位</td>
                        <td>
                            <input type="hidden" id="companyId" name="companyId">
                            <input type="text" class="input" id="companyName" name="companyName" placeholder="请选择"
                                   onclick="companyTree('companyId','companyName',false)" ErrEmptyCap="单位不允许为空">
                        </td>--%>
                        <td class="td_name"><font style="color: RED;">*</font>主持人</td>
                        <td colspan="3">
                            <input type="text" id="moderator" name="moderator" ErrEmptyCap="主持人不允许为空">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_name"><font style="color: RED;">*</font>参会人</td>
                        <td colspan="3">
                            <textarea id="attendeeMember" name="attendeeMember" type="text" placeholder="请输入" ErrEmptyCap="参会人不允许为空" style="padding-bottom: 0px;margin-bottom: 0px;width: 100%"></textarea>
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
                            <input type="hidden" name="page" value="meeting_register">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="meeting_line" id="meeting_line" style="display: none;"></div>
    </div>
    <div id="subject_msg" style="max-height: 150px;overflow: auto">

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
        var companyId = $("#reCompanyId").val();//单位ID
        if(meetingId == ""){
            top.showMsg("请先保存会议信息！");
        }else{
            var sURL = "{contextPath}/jsp/meeting/register/subject_add.jsp?meetingId="+meetingId+"&companyId="+companyId;
            showDialog("议题编辑", sURL, 'subjectEditWin', 1000, 600, true);
        }
    }

    /**
     * 添加议题信息
     */
    function showSubject(data){
        for(var i=0;i<data.length;i++){
            var attendanceInfo = "";
            var attendances = (data[i].attendance && data[i].attendance != "") ? data[i].attendance.split(",") : "";
            for (var n = 0; n < attendances.length; n++) {
                var name = attendances[n].split(":")[0];
                var positon = attendances[n].split(":").length > 1 ? attendances[n].split(":")[1] : "";
                attendanceInfo = attendanceInfo+name;
                if(positon != "") attendanceInfo = attendanceInfo+"("+positon+")";
                attendanceInfo = attendanceInfo+",";
            }
            if(attendanceInfo.length > 1) attendanceInfo = attendanceInfo.substring(0,attendanceInfo.length-1);

            var html="<div class='table_expand' style='margin-top: 10px'><div class='table_expand_left'>"+
                "<img id='expandIcon"+i+"'  style='cursor:pointer;' onclick='showHideSubject("+i+");'><a herf='javascript:void(0);' onclick='doEditSubject(\""+data[i].subjectId+"\",\""+$("#meetingId").val()+"\",\""+$("#companyId").val()+"\")'>议题"+(i+1)+":"+data[i].subjectName+"</a></div>"+
                "<div class='table_expand_right'><img id='passIcon"+i+"''><p>是否通过：</p></div></div>"+
                "<div id='subject"+i+"' class='list_main_content' style='display:none;height: 110px'><table>"+
                "<tr><td id='sourceName"+i+"' class='table_tr_b' style='font-weight: bold;width:100px;'>任务来源</td><td class='table_long_txt'>"+data[i].sourceName+"</td>"+
                "<td class='table_tr_b' style='font-weight: bold;width:100px;''>专项名称</td><td class='table_long_txt'>"+data[i].specialName+"</td> </tr>"+
                "<tr><td class='table_tr_b' style='font-weight: bold;width:100px;'>列席人员</td><td colspan='3' class='table_long_txt'>"+attendanceInfo+"</td></tr>"+
                "<tr><td class='table_tr_b' style='font-weight: bold;width:100px;'>表决情况</td><td colspan='3' class='table_long_txt'>";
            html += "<div class='vote_subject'><span>"+data[i].deliberation+"</span></div>";
            html += "</td></tr></table></div>";
            $('#subject_msg').append(html);
            if(data[i].passFlag == "1"){
                $('#passIcon'+i).attr("src",'<%=SYSURL_static%>/images/tiol/ico_pass.png');
            }else{
                $('#passIcon'+i).attr("src",'<%=SYSURL_static%>/images/tiol/ico_no.png');
            }
            $('#expandIcon'+i).attr("src",'<%=SYSURL_static%>/images/tiol/ico_expand_b.png');
        }
    }

    /**
     * 选择会议类型后记录会议类型名称
     * @param obj
     */
    function fillTypeName(obj){
        var index = obj.selectedIndex;
        var selectText = obj.options[index].text;
        $('#typeName').val(selectText);
        loadCompanyMembers();//根据会议类型 加载最新会议的主持人与参会人

        //董事长与其他会议类型
        if(selectText !="" && (selectText.indexOf('董事长')>-1 || selectText.indexOf('其他会议')>-1)){
            $("#noticeFileRed").hide();
            $("#noticeFile").removeAttr("ErrEmptyCap");
        }else{
            $("#noticeFileRed").show()
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

        if("${meeting.NOTICEFILEID}"){
            $("#noticeFile").hide();
            $("#noticeFile").removeAttr("ErrEmptyCap");
            var html = "<a onclick='showFileView(\""+"${meeting.NOTICEFILEID}"+"\")'>"+"${meeting.NOTICEFILE}"+"</a>";
            $("#noticeFileTd").append(html);
        }
        if("${meeting.SUMMARYFILEID}"){
            $("#summaryFile").hide();
            $("#summaryFile").removeAttr("ErrEmptyCap");
            var html = "<a onclick='showFileView(\""+"${meeting.SUMMARYFILEID}"+"\")'>"+"${meeting.SUMMARYFILE}"+"</a>";
            $("#summaryFileTd").append(html);
        }
        showSubejctData();
    }
    /**
     * 查询显示会议议题信息
     */
    function showSubejctData(){
        $('#subject_msg').empty();
        $.ajax({
            url : "/subject/getSubjectByMeetingId.action",
            data:{
                meetingId:$("#meetingId").val(),
                companyId:$("#companyId").val()
            },
            dataType:"json",
            type : "POST",
            async: false,
            success: function (result) {
                showSubject(result);//加载会议信息
            }
        });
    }

    /**
     * 刷新当前表单
     */
    function reload(){
        var meetingId = $("#reMeetingId").val();
        window.location.href = "/meeting/reloadMeeting.action?meetingId="+meetingId+"&page=meeting_register";
    }

    /**
     * 在线浏览文件
     */
    function showFileView(fileId){
        var href = "/common/show.jsp?fileId="+fileId;
        //top_showDialog("附件详情",href, 'viewFileWin', 1000, 650);
        showWindow(jt.parseURL(href),'附件详情');
    }

    //显示隐藏议题信息
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
     * 编辑议题
     * @param subjectId
     * @param meetingId
     */
    function doEditSubject(subjectId,meetingId,companyId){
        var sURL = "{contextPath}/subject/toSubjectEdit.action?companyId="+companyId+"&subjectId="+subjectId;
        showDialog("议题编辑", sURL, 'subjectEditWin', 1000, 600, true);
    }
</script>
</html>
<%@ include file="/common/inc_bottom.jsp" %>