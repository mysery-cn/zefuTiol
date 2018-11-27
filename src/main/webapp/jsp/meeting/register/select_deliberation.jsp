<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/inc_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>议题决议选择</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
</head>
<script type="text/javascript">
    var meetingId = jt.getParam("meetingId");
    var attendeeMembers = null;
    function init(){
        var sURL = "/meeting/queryMeetingAttendee.action";
        //查询参会人员信息
        postJSON(sURL, {'meetingId':meetingId,'attendFlag':1},function (json,o){
            if(json && json.attendeeMember != ""){
                attendeeMembers = json.attendeeMember.split(',');
                showUserInfo(attendeeMembers);
            }
        });
    }

    /**
     * 加载选择表单
     * @param json 参会人信息
     */
    function showUserInfo(json){
        if(json.length == 0) $("#tableId").append("<tr>" + "<td class=\"cnt\" style=\"text-align: center\" colspan='2'>" + "未查询到参会人员，请查看配置或手动输入"+ "</td>" +"</tr>");
        for (var i = 0; i < json.length; i++) {
            var html = "<tr>" +
                        "<td class=\"tit\" style=\"text-align: center\">"+json[i]+"</td>" +
                        "<td class=\"cnt\">" +
                        "<select id=\"result"+i+"\" >\n" +
                            "<option value=\"同意\">同意</option>\n" +
                            "<option value=\"不同意\">不同意</option>\n" +
                            "<option value=\"原则同意\">原则同意</option>\n" +
                            "<option value=\"保留意见\">保留意见</option>\n" +
                        "</select>"+
                        "</td>" +
                        "</tr>";
            $("#tableId").append(html);
        }
    }

    /**
     * 保存选择结果
     */
    function saveCatalog(){
        if(attendeeMembers == null || attendeeMembers.length == 0) closeSelf();
        var results = "";
        for (var i = 0; i < attendeeMembers.length; i++) {
            var name = attendeeMembers[i];
            var result = $('#result'+i).val();
            results = results + name;
            results = results+"("+result+"),";
        }
        //父页面决议回填
        parent.jt._("#deliberations").value = results.substring(0,results.length-1);
        closeSelf();
    }
</script>
<body class="BodyEdit" pageType="EditPage" onload="init()">
<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div icon="close.png" onclick="closeSelf();">关闭</div>
        <div icon="save.png" id="addButton" onclick="saveCatalog()">保存</div>
        <div></div>
    </div>
</div>

<div id="divFixCnt" style="padding:3px;height: 340px;overflow: auto">
    <form id="frmMain" name="frmMain" class="Validate">
        <table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3" id="tableId">
            <td class="tit" style="width: 150px;text-align: center;border-right: 1px solid #f9f9f9">姓名</td>
            <td class="cnt" style="text-align: center">
                <span style="color: RED">缺席会议说明，未缺席不填写</span>
            </td>
        </table>
    </form>
</div>
</body>
</html>
