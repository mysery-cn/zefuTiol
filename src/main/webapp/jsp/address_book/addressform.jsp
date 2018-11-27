<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>新增人员</title>
<script>
	var newNum=0;
	var sID = jt.getParam('id');
	var sOrgID = jt.getParam('orgid');
		function init(){
			var sURL = "{SYSURL.oa}/address_book/address/getOrgNameById.action?orgId="+sOrgID;
			getJSON(sURL,function(json){
				if(json.data[0] != '' && json.data[0] != null){
					var orgName = json.data[0].ORG_NAME;
					var orgId = json.data[0].ORG_ID;
					jt._("#USERDEPT").value = orgName;
					jt._("#USERDEPTID").value = orgId;
				}
			});
			

				
			if(sID != ""){
				var sURL = "{SYSURL.oa}/address_book/address/listDataByIds.action?FID="+sID;
				var json = getJSON(sURL,null,false);
				//alert(jt.Obj2Str(json));
				if(json.data[0] != '' && json.data[0] != null){
					var obj = json.data[0];
					jt._("#USERNAME").value = obj.USERNAME;
					//jt._("#USERID").value = newNum;
					try{jt("[name='USER_SEX'][value='"+obj.USER_SEX+"']")[0].checked = 'true';}catch (e) {}
					//jt._("#USER_SEX").value = obj.USER_SEX;
					jt._("#USERDEPT").value = obj.USERDEPT;
					jt._("#USERTITLE").value = obj.USERTITLE;
					
					jt._("#USERDEPTID").value = obj.USERDEPTID;
					jt._("#OFFICETEL_CODE").value = obj.OFFICETEL_CODE;
					jt._("#OFFICETEL_NUM").value = obj.OFFICETEL_NUM;
					//jt._("#HOUSETEL_CODE").value = obj.HOUSETEL_CODE; 
					//jt._("#HOUSETEL_NUM").value = obj.HOUSETEL_NUM;
					jt._("#MOBILETEL").value = obj.MOBILETEL;
					jt._("#EMAIL").value = obj.EMAIL;
					//jt._("#OFFICETEL_CODE_IN").value = obj.OFFICETEL_CODE_IN;
					//jt._("#OFFICETEL_NUM_IN").value = obj.OFFICETEL_NUM_IN;
					jt._("#REMARK").value = obj.REMARK;
					jt._("#VIEWNUM").value = obj.VIEWNUM;
				}
			}
			 
		}
	
	//人员保存
		function EditPage_Save(){
				//获取otheruser_1234的最大值
				debugger;
				var sURL1 = "{SYSURL.oa}/address_book/address/getMaxNum.action?";
				var json1 = getJSON(sURL1,null,false);
				if(json1.length>0){
					var array=new Array();
					for ( var i = 0; i < json1.length; i++) {
						if (json1[i].USERID.indexOf('otheruser_')!=-1) {
							var num = json1[i].USERID.split("_")[1];
							array.push(num);
						}
					}
					//alert("数组="+array)
					newNum = Math.max.apply(null,array)+1;
					
				}
		
		var USERNAME=jt._('#USERNAME').value;
		var USERID=newNum;
		var USER_SEX = jt("[name='USER_SEX']:checked").val();
		var USERDEPT=jt._('#USERDEPT').value;
		var USERDEPTID=jt._('#USERDEPTID').value;
		var USERTITLE=jt._('#USERTITLE').value;
		var OFFICETEL_CODE=jt._('#OFFICETEL_CODE').value;
		var OFFICETEL_NUM=jt._('#OFFICETEL_NUM').value;
		//var HOUSETEL_CODE=jt._('#HOUSETEL_CODE').value;
		//var HOUSETEL_NUM=jt._('#HOUSETEL_NUM').value;
		var MOBILETEL=jt._('#MOBILETEL').value;
		var EMAIL=jt._('#EMAIL').value;
		//var OFFICETEL_CODE_IN=jt._('#OFFICETEL_CODE_IN').value;
		//var OFFICETEL_NUM_IN=jt._('#OFFICETEL_NUM_IN').value;
		var REMARK=jt._('#REMARK').value;
		var VIEWNUM=jt._('#VIEWNUM').value;

		//是否授权
		var USE = jt._('#USE').value;

		var sURL = "{SYSURL_oa}/address_book/address/saveConfigPeople.action";
		  var data = {};
		  data.fid='';
		  if(sID) data.fid=sID;
		  data.USERNAME = USERNAME;
		  data.USERID = "otheruser_"+USERID;
		  data.USER_SEX = USER_SEX;
		  data.USERDEPT = USERDEPT;
		  data.USERDEPTID = USERDEPTID;
		  data.USERTITLE = USERTITLE;
		  data.USE = USE;
		  
		  data.OFFICETEL_CODE = OFFICETEL_CODE;
		  data.OFFICETEL_NUM = OFFICETEL_NUM;
		  //data.HOUSETEL_CODE = HOUSETEL_CODE;
		 // data.HOUSETEL_NUM = HOUSETEL_NUM;
		  data.MOBILETEL = MOBILETEL;
		 //data.OFFICETEL_CODE_IN = OFFICETEL_CODE_IN;
		 // data.OFFICETEL_NUM_IN = OFFICETEL_NUM_IN;
		  data.REMARK = REMARK;
		  data.VIEWNUM = VIEWNUM;
		  data.EMAIL = EMAIL;
		  
		  if(USERNAME == ''){
			  showMsg('人员不允许为空');
			  return false;
		  }
		  if(USERDEPT == ''){
			 showMsg('部门不允许为空');
			 return false;
		  }
		  
		  /*if(USER_SEX == ''){
			  showMsg('性别不允许为空');
			  return false;
		  }*/
		  postJSON(sURL, data,function(json, e) {
		    if (!json || json.errorCode != "0") {
		      parent.showMsg("保存失败,请您确认数据是否填写正确!");
		    }else{
		    parent.showMsg("保存成功!");
		    reloadFrameMainGrid();
		    closeSelf();
		    }
		  },false);
		  reloadFrameMainGrid();
		  
	}
		//bailu 20170722
		function getpersonbyid(){
			var FID= jt._("#FID").value
			var sURL = "{SYSURL.oa}/address_book/address/getPersonById.action?FID="+FID;
			var json = getJSON(sURL,null,false);
			if(json.data[0] != '' && json.data[0] != null){
				var obj = json.data[0];
				jt._("#USERID").value = obj.USERID;
			    jt._("#USER_SEX").value = obj.USER_SEX;
				jt._("#USERNAME").value = obj.USERNAME;
				jt._("#USERTITLE").value = obj.USERTITLE;
				
				
				jt._("#OFFICETEL_CODE").value = obj.OFFICETEL_CODE ;
				jt._("#OFFICETEL_NUM").value = obj.OFFICETEL_NUM ;
				//jt._("#HOUSETEL_CODE").value = obj.HOUSETEL_CODE ;
				//jt._("#HOUSETEL_NUM").value = obj.HOUSETEL_NUM ;
				jt._("#MOBILETEL").value = obj.MOBILETEL ;
				jt._("#EMAIL").value = obj.EMAIL ;
				//jt._("#OFFICETEL_CODE_IN").value = obj.OFFICETEL_CODE_IN ;
				//jt._("#OFFICETEL_NUM_IN").value = obj.OFFICETEL_NUM_IN ;
				jt._("#REMARK").value = obj.REMARK ;
			}
		}

		//选择部门
		


</script>
</head>
<style type="text/css">
	body.BodyEdit .cnt .input{width:100%;}
</style>

<body class="BodyEdit" >
	
	<input type="hidden" name="USE" value="0" /> <!--是否授权标识-lin-->

	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
			<div icon="close.png" onclick="closeSelf();">关闭</div>
			<div icon="save.png" onclick="EditPage_Save();">保存</div>
		</div>
	</div>
	<div id="divFixCnt" style="padding:3px;">
		<form id="frmMain" name="frmMain" class="Validate">
			<input type="hidden" name="FID" value="${FID }" />
			<table width="100%" class="TableEdit FormTable" border="0" cellspacing="0"
				cellpadding="3">
				<COLGROUP>
					<COL width=120>
					<COL>
					<COL width=120>
					<COL>
				</COLGROUP>
				<tr>
					<td class="tit must">人员：</td>
					<td class="cnt"><input type="text" name="USERNAME"
						value="${USERNAME}" class="input" maxlength=10
						ErrEmptyCap="人员不允许为空" /></td>
					</td>
					

					<td class="tit must">部门：</td>
					<td class="cnt">
						<input type="text" name="USERDEPT" value="${USERDEPT}" class="input" maxlength=200 disabled="disabled"/>
						<input type="hidden" name="USERDEPTID" value="${USERDEPTID}" class="input" maxlength=30 />
					</td>
				</tr>			
				<tr>
					<td class="tit">职务：</td>
					<td class="cnt"><input type="text" name="USERTITLE"
						value="${USERTITLE}" class="input" maxlength=10 /></td>
					<td class="tit">性别：</td>
					<td class="cnt">
						<input type="radio" name="USER_SEX" maxlength=10
						ErrEmptyCap="性别不允许为空" value="男" />男
						<input type="radio" name="USER_SEX" maxlength=10
						ErrEmptyCap="性别不允许为空" value="女" />女
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
				<!-- <tr>
					<td class="tit">住宅电话区号：</td>
					<td class="cnt"><input type="text" name="HOUSETEL_CODE"
						value="${HOUSETEL_CODE}" class="input" maxlength=10 /></td>
					</td>
					<td class="tit">住宅电话号码：</td>
					<td class="cnt"><input type="text" name="HOUSETEL_NUM"
						value="${HOUSETEL_NUM}" class="input" maxlength=10 /></td>
					</td>
				</tr> -->
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
				<!-- <tr>
					<td class="tit">办公电话区号_内部：</td>
					<td class="cnt"><input type="text" name="OFFICETEL_CODE_IN"
						value="${OFFICETEL_CODE_IN}" class="input" maxlength=10 /></td>
					</td>
					<td class="tit">办公电话号码_内部：</td>
					<td class="cnt"><input type="text" name="OFFICETEL_NUM_IN"
						value="${OFFICETEL_NUM_IN}" class="input" maxlength=10 /></td>
					</td>
				</tr> -->
				<tr>
					<td class="tit">备注：</td>
					<td class="cnt" colspan="3"><input type="text" name="REMARK"
						value="${REMARK}" class="input" maxlength=10 /></td>
					</td>
				</tr>
				<tr>
					<td class="tit">序号：</td>
					<td class="cnt" colspan="3"><input type="text" name="VIEWNUM"
						value="99" class="input" maxlength=10 /></td>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>