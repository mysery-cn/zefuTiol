<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
    <title>XML详情</title>
</head>
<script type="text/javascript">
    var id = jt.getParam("id");
    var type = jt.getParam("type");
    var status = jt.getParam("status");

    function init(){
        var dataURL = "{contextPath}/dataVindicate/queryXmlDetail.action";
        var jPost= {};
        jPost.id = id;
        jPost.type = type;
        jPost.status = status;
        postJSON(dataURL,jPost,function(json,o){
            if (!json || json.errorCode != "0") {
                return jt.Msg.showMsg("获取数据失败！");
            } else {
                if(json.data.length > 0){
                    showLoading(false);
                    jt._('#xmlDetail').value = json.data[0];
                }
            }
        },false);
    }
</script>

<body class="BodyEdit" pageType="EditPage">
<div id="divFixCnt" style="padding:3px;">
    <form id="frmMain" name="frmMain" class="Validate">
        <table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
            <tr style="height: 400px;">
                <td class="tit">XML内容：</td>
                <td class="cnt" style="height: 400px;">
                    <textarea style="height: 100%;" name="xmlDetail" id="xmlDetail" readonly="readonly" ></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>