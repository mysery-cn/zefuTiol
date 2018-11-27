<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/UserSelect.js"></script>
    <title>协同填报选择人员</title>
</head>
<script type="text/javascript">
    function init(){
        loadUsers();
    }
</script>
<body class="BodySendToSelectUser">
<div id="divFixCnt" style="padding:0px 8px;">
    <table id="tabMain1" width="100%" border="0" cellspacing="0" cellpadding="4">
        <tr>
            <td width="100" height="24">指派类型：</td>
            <td>
                <select style="width: 70%;" id="transition" onchange="transitionSelectChange_page(this.value)">
                    <option value="1">按人员选择</option><%--//0部门 1人员 2角色--%>
                    <option value="0">按组织机构</option>N
                    <option value="2">按角色</option>
                </select>
            </td>
        </tr>
    </table>
    <div id="divUserSelect" class="EngineUserSelect" style="height:250px;" cellspacing="0" cellpadding="4"></div>
</div>

<div id="divFixBottom" class="FixBottomButton">
    <input type="button" class="btnOK" value="确定" onclick="funOK()">
    <input type="button" class="btnCancel" value="取消" onclick="closeSelf()">
</div>
</body>
<script type="text/javascript">
    var showObj = jt._("#divUserSelect");
    var meetingTypeId = parent.$("#meetingType").val();
    var catalogIds = parent.$("#catalogIds").val();
    var companyId = curUser.companyId;
    var sURL = "/purview/queryPurviewListByMeeting.action";
    var postData = {};
    postData.typeId = meetingTypeId;
    postData.catalogId = catalogIds;
    postData.companyId = companyId;

    function funOK(){
        var selectUsr = showObj.getStateUser();
        if(selectUsr.length == 0 || selectUsr[0].user.length == 0) {
            showMsg("请选择指派人员");
            return;
        }
        var userName = selectUsr[0].user[0].text;
        var userId = selectUsr[0].user[0].id;
        parent.$("#receiveUserId").val(userId);
        parent.$("#receiveUserName").val(userName);
        closeSelf();
    }
    /**
     * 0部门 1人员 2角色
     */
    function transitionSelectChange_page(type){
        showObj.delAllUser();
        if(type == "0"){
            loadDeptUser();
        }else if(type == "1"){
            loadUsers();
        }else{
            loadRoleUser();
        }
    }

    /**
     * 加载权限用户
     */
    function loadUsers(){
        postData.purviewType = 1;
        postJSON(sURL,postData,function(json,o){
            var initData =  [{text: '按人员指派', id: 'orgType', nodeType: 'org_org', defaultfield: '', defaultfieldtype: '',_ORG_ID:''}];
            if (!json || json.errorCode != "0") {
                return jt.Msg.showMsg("数据同步失败！");
            } else {
                var userArr = new Array();
                var users = json.data;
                for (var i = 0; i < users.length; i++) {
                    var user = {};
                    user.id = users[i].id;
                    user.nodeType = 'org_user';
                    user.text = users[i].name
                    userArr.push(user);
                }
                initData[0].children = userArr;
                showObj.loadUsers(initData);
            }
        },false);
    }

    /**
     * 加载部门用户
     */
    function loadDeptUser(){
        postData.purviewType = 0;
        postJSON(sURL,postData,function(json,o){
            var initData =  [{text: '按部门指派', id: 'orgType', nodeType: 'org_company', defaultfield: '', defaultfieldtype: '',_ORG_ID:''}];
            if (json && json.errorCode == "0") {
                var deptArray = new Array();
                var depts = json.data;
                for (var i = 0; i < depts.length; i++) {
                    var dept = {};
                    dept.id = depts[i].id;
                    dept.nodeType = 'org_org';
                    dept.text = depts[i].name;
                    dept.children = getUsersByDept(depts[i].id);
                    deptArray.push(dept);

                }
                initData[0].children = deptArray;
                showObj.loadUsers(initData);
            }
        },false);
    }
    /**
     * 获取部门下的用户
     */
    function getUsersByDept(deptId){
        var sURL = "/ums/orguser/orgUserListByOrgId.action";
        var data = {"Id":deptId,"mode":'',"companyId":curUser.companyId,"currentPage":1,"pageSize":200};
        var userArray = new Array();
        postJSON(sURL,data,function(json,o){
            if (json && json.errorCode == "0") {
                var users = json.data;
                for (var i = 0; i < users.length; i++) {
                    var user = {};
                    user.id = users[i].USER_ID;
                    user.nodeType = 'org_user';
                    user.text = users[i].USER_NAME;
                    userArray.push(user);
                }
            }
        },false);
        return userArray;
    }

    /**
     * 加载角色用户 --0部门 1人员 2角色
     */
    function loadRoleUser(){
        postData.purviewType = 2;
        postJSON(sURL,postData,function(json,o){
            var initData =  [{text: '按角色指派', id: 'orgType', nodeType: 'org_company', defaultfield: '', defaultfieldtype: '',_ORG_ID:''}];
            if (json && json.errorCode == "0") {
                var roleArray = new Array();
                var roles = json.data;
                for (var i = 0; i < roles.length; i++) {
                    var role = {};
                    role.id = roles[i].id;
                    role.nodeType = 'org_role';
                    role.text = roles[i].name;
                    role.children = getUsersByRole(roles[i].id);
                    roleArray.push(role);

                }
                initData[0].children = roleArray;
                showObj.loadUsers(initData);
            }
        },false);
    }

    /**
     * 根据角色查询人员
     */
    function getUsersByRole(roleId){
        var sURL = "/ums/roleuser/getRoleUserListById.action";
        var data = {"Id":roleId,"rangeId":curUser.companyId,"rangeType":"COMPANY","currentPage":1,"pageSize":100};
        var userArray = new Array();
        postJSON(sURL,data,function(json,o){
            if (json && json.errorCode == "0") {
                var users = json.data;
                for (var i = 0; i < users.length; i++) {
                    var user = {};
                    user.id = users[i].USER_ID;
                    user.nodeType = 'org_user';
                    user.text = users[i].USER_NAME;
                    userArray.push(user);
                }
            }
        },false);
        return userArray;
    }
</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
