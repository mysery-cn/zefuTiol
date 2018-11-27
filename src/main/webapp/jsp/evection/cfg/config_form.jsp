<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>参数配置</title>
</head>
<style type="text/css">
	.cfgTitle {font-family:黑体,楷体_GB2312;line-height:30px;font-size:20px;text-align:center;padding:10px 0px;}
</style>
<script type="text/javascript">
 	var appId = jt.getParam('appId');
	
	function jtParseURL_Page(sURL){
		sURL = jtReplaceVAR(sURL, 'sID,appId');
		return sURL;
	}
	
	//表单检测
	function EditPage_CheckForm(aForm){ 
		return true;
	}
	
	//页面初始化后
	function initFormValueAfter(json){
		jt._('#appId').value = jt._('#appId').value==""?appId:"";
	}
	
	//初始化系统配置数据
	function initEditFormValue(){
		var appId = jt._('#appId')==null?"":jt._('#appId').value;
		var rangeId = jt._('#RANGE_ID')==null?"":jt._('#RANGE_ID').value;
		var sURL = "{SYSURL.bam}/bam/bizSystemConfig/initConfig.action?t={JSTime}&appId="+appId+"&rangeId="+rangeId;
		getHttp(sURL, function(str){
			if(str=="true"){
				location.reload();
			}
		}, true);
	}
</script>
	
<body class="BodyEdit" pageType="EditPage" 
			actPath="{SYSURL.bam}/bam/bizSystemConfig/"
			actSave="saveOrUpdate.action"
			actUpdate="saveOrUpdate.action"
			actGetData="listByRange.action?appId={appId}"
			>
	
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div id="btnSave" icon="save.png" onclick="EditPage_Save()">保存</div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
<form id="frmMain" name="frmMain" class="Validate">
	<input type="hidden" name="appId" value="" />
	<input type="hidden" name="RANGE_ID" value="{RANGE_ID}" />
	<div style="font-size:16px;font-weight:bold;text-align:center;padding:15px">参数配置</div>
	<div class="TabBase" cssTab="tabItem" cssTabSel="tabItemSel" cssTabOver="tabItemOver" cssCnt="tabCnt" style="padding-left:0px;padding-top:0px;">
		<div class="tabItem">参数设置</div>
		<div class="tabCnt">
			<table width11="100%" id="tabbasic" class="TableEdit" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit">职　　务：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_evection_jobtitle" value=",经理,总师,主任,副主任,处长,副处长"/>
					</td>
				</tr>
				<tr>
					<td class="tit">出差类型：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_evection_type" value="公务出差，学习培训,会议培训,请假,报告,年休假"/>
					</td>
				</tr>
				<tr>
					<td class="tit">人员级别：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_evection_peoplelevel" value="公司领导,部门领导,普通员工"/>
					</td>
				</tr>
				<tr>
					<td class="tit">出差地点：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_evection_place" value="北京 ,上海,南京,深圳,厄瓜多尔,武汉"/>
					</td>
				</tr>
				<tr>
					<td class="tit">交通工具：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_evection_traffic" value="火车 ,长途汽车,飞机,轮船,其他"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	
</form>	
</div>
	
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
