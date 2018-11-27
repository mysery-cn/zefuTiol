<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>添加关联关系</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/address_book.js"></script>
<script>
	var sID = jt.getParam('id');
	var type = jt.getParam('type');
	function init(){
		if(type == "insert"){
			var sURL = "{SYSURL.oa}/address_book/address/getOrgNameById.action?orgId="+sID;
			//alert(sURL);
			var json = getJSON(sURL,null,false);
			if(json.data[0] != '' && json.data[0] != null){
				var orgName = json.data[0].ORG_NAME;
				var orgId = json.data[0].ORG_ID;
				jt._("#USERDEPT").value = orgName;
				jt._("#USERDEPTID").value = orgId;
			}
		}else{
			var sURL = "{SYSURL.oa}/address_book/address/listDataByIds.action?FID="+sID;
			var json = getJSON(sURL,null,false);
			if(json.data[0] != '' && json.data[0] != null){
				var obj = json.data[0];
				jt._("#USERNAME").value = obj.USERNAME;
				jt._("#USERID").value = obj.USERID;
				jt._("#USERDEPT").value = obj.USERDEPT;
				jt._("#USERDEPTID").value = obj.USERDEPTID;
				jt._("#USERTITLE").value = obj.USERTITLE;
				
				
				jt._("#OFFICETEL_CODE").value = obj.OFFICETEL_CODE ;
				jt._("#OFFICETEL_NUM").value = obj.OFFICETEL_NUM ;
				jt._("#HOUSETEL_CODE").value = obj.HOUSETEL_CODE ;
				jt._("#HOUSETEL_NUM").value = obj.HOUSETEL_NUM ;
				jt._("#MOBILETEL").value = obj.MOBILETEL ;
				jt._("#EMAIL").value = obj.EMAIL ;
				jt._("#OFFICETEL_CODE_IN").value = obj.OFFICETEL_CODE_IN ;
				jt._("#OFFICETEL_NUM_IN").value = obj.OFFICETEL_NUM_IN ;
				jt._("#REMARK").value = obj.REMARK ;
				jt._("#VIEWNUM").value = obj.VIEWNUM ;
			}
		}
	}
	
	function getpersonthing(){
		var userid = jt._("#USERID").value
		var sURL = "{SYSURL.oa}/address_book/address/getThingById.action?USERID="+userid;
		var json = getJSON(sURL,null,false);
		if(json.data[0] != '' && json.data[0] != null){
			var obj = json.data[0];
			jt._("#USERID").value = obj.USERID;
			jt._("#USERNAME").value = obj.USERNAME;
			jt._("#USERTITLE").value = obj.USERTITLE;
			
			jt._("#OFFICETEL_CODE").value = obj.OFFICETEL_CODE ;
			jt._("#OFFICETEL_NUM").value = obj.OFFICETEL_NUM ;
			jt._("#HOUSETEL_CODE").value = obj.HOUSETEL_CODE ;
			jt._("#HOUSETEL_NUM").value = obj.HOUSETEL_NUM ;
			jt._("#MOBILETEL").value = obj.MOBILETEL ;
			jt._("#EMAIL").value = obj.EMAIL ;
			jt._("#OFFICETEL_CODE_IN").value = obj.OFFICETEL_CODE_IN ;
			jt._("#OFFICETEL_NUM_IN").value = obj.OFFICETEL_NUM_IN ;
			jt._("#REMARK").value = obj.REMARK ;
			jt._("#VIEWNUM").value = obj.VIEWNUM ;
			jt._("#FID").value = obj.FID;
		}
	}
	 //保存
	function localSave(){
		if (typeof(aForm)=='undefined') aForm=jt._('#'+ jt.getAttr(document.body,'mainForm','frmMain'));
		var postData = {};
		postData.FID = jt._("#FID").value;
		postData.USERNAME = jt._("#USERNAME").value;
		postData.USERID=jt._("#USERID").value;
		postData.USERDEPT=jt._("#USERDEPT").value;
		postData.USERDEPTID=jt._("#USERDEPTID").value;
		postData.USERTITLE=jt._("#USERTITLE").value;
		
		postData.OFFICETEL_CODE=jt._("#OFFICETEL_CODE").value;
		postData.OFFICETEL_NUM=jt._("#OFFICETEL_NUM").value;
		postData.HOUSETEL_CODE=jt._("#HOUSETEL_CODE").value;
		postData.HOUSETEL_NUM=jt._("#HOUSETEL_NUM").value;
		postData.MOBILETEL=jt._("#MOBILETEL").value;
		postData.EMAIL=jt._("#EMAIL").value;
		postData.OFFICETEL_CODE_IN=jt._("#OFFICETEL_CODE_IN").value;
		postData.OFFICETEL_NUM_IN=jt._("#OFFICETEL_NUM_IN").value;
		postData.REMARK=jt._("#REMARK").value;
		postData.VIEWNUM=jt._("#VIEWNUM").value;
		//alert(jt.Obj2Str(postData));return;
		var sURL = "{SYSURL.oa}/address_book/address/saveData.action";
		/* 	postJSON(sURL,postData,function (json,o){
	
		}); */
		//保存后弹出提醒 20170723 bailu
		  postJSON(sURL, postData,function(json, o) {
			    if (!json || json.errorCode != "0") {
			      parent.showMsg("添加关联关系失败,请您确认数据是否填写正确!");
			    }else{
			    //parent.showMsg("添加关联关系成功!");
			    reloadFrameMainGrid();
			    closeSelf();
			    }
			  },false);
		
		parent.reloadFrameMainGrid();
		closeSelf();
	}

	
</script>
</head>
<body class="BodyEdit">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
			<div icon="close.png" onclick="closeSelf();">关闭</div>
			<div icon="save.png" onclick="localSave();">保存</div>
		</div>
	</div>
	<div id="divFixCnt" style="padding:3px;">
		<form id="frmMain" name="frmMain" class="Validate">
			<input type="hidden" name="FID" value="${FID }" />
			<table width="100%" class="TableEdit" border="0" cellspacing="0"
				cellpadding="3">
				<COLGROUP>
					<COL width=150>
					<COL>
					<COL width=130>
					<COL>
				</COLGROUP>
				<tr>
					<td class="tit must">人员：</td>
					<td class="cnt">
						<table border=0 cellSpacing=0 cellPadding=0 width=100% style="table-layout:fixed;">
							<COL><COL width=75>
							<tr>
								<td><input type="text" name="USERNAME"	
						value="${USERNAME}" class="input" maxlength=100 style="width:100%"/></td>
								<td><button class="button" onclick="funSelectPerson('USERID','USERNAME')" >选择</button></td>
							</tr>
						</table>	
						</td>
					
					</td>
					<input type="hidden" name="USERID"
						value="${USERID}" class="input" maxlength=30  onchange="getpersonthing()"/>
					<td class="tit">部门：</td>
					<td class="cnt"><input type="text" name="USERDEPT"
						value="${USERDEPT}" class="input" maxlength=200 disabled="disabled"/></td>
					</td>
					<input type="hidden" name="USERDEPTID"
						value="${USERDEPTID}" class="input" maxlength=30 />
				</tr>
				<tr>
					<td class="tit">职务：</td>
					<td class="cnt" colspan="3"><input type="text" name="USERTITLE"
						value="${USERTITLE}" class="input" maxlength=10 /></td>
					</td>

				</tr>
				<tr>
					<td class="tit">办公电话区号：</td>
					<td class="cnt"><input type="text" name="OFFICETEL_CODE"
						value="${OFFICETEL_CODE}" class="input" maxlength=10 /></td>
					</td>
					<td class="tit">办公电话号码：</td>
					<td class="cnt"><input type="text" name="OFFICETEL_NUM"
						value="${OFFICETEL_NUM}" class="input" maxlength=10 /></td>
					</td>
				</tr>
				<tr>
					<td class="tit">住宅电话区号：</td>
					<td class="cnt"><input type="text" name="HOUSETEL_CODE"
						value="${HOUSETEL_CODE}" class="input" maxlength=10 /></td>
					</td>
					<td class="tit">住宅电话号码：</td>
					<td class="cnt"><input type="text" name="HOUSETEL_NUM"
						value="${HOUSETEL_NUM}" class="input" maxlength=10 /></td>

					</td>
				</tr>
				<tr>
					<td class="tit">手机号码：</td>
					<td class="cnt"><input type="text" name="MOBILETEL"
						value="${MOBILETEL}" class="input" maxlength=15 /></td>
					</td>
					<td class="tit">电子邮箱：</td>
					<td class="cnt"><input type="text" name="EMAIL"
						value="${EMAIL}" class="input" maxlength=50 /></td>
					</td>
				</tr>
				<tr>
					<td class="tit">办公电话区号_内部：</td>
					<td class="cnt"><input type="text" name="OFFICETEL_CODE_IN"
						value="${OFFICETEL_CODE_IN}" class="input" maxlength=10 /></td>
					</td>
					<td class="tit">办公电话号码_内部：</td>
					<td class="cnt"><input type="text" name="OFFICETEL_NUM_IN"
						value="${OFFICETEL_NUM_IN}" class="input" maxlength=10 /></td>

					</td>
				</tr>
				<tr>
					<td class="tit">备注：</td>
					<td class="cnt" colspan="3"><input type="text" name="REMARK"
						value="${REMARK}" class="input" maxlength=10 /></td>
					</td>

				</tr>
				<tr>
					<td class="tit">序号：</td>
					<td class="cnt" colspan="3"><input type="text" name="VIEWNUM"
						value="${VIEWNUM}" class="input" maxlength=10 /></td>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>