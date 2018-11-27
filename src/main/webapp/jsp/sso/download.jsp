<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
  <%@ include file="/common/inc_head.jsp"%>
  <script type="text/javascript" src="<%=SYSURL_static%>/js/ums/client.js"></script>
  <title>成都市一体化协同办公平台</title>

</head>
<style type="text/css">

  #DownloadFile ul{}
  #DownloadFile li{list-style: none;font-size:12px;padding:2px 0;}
  #DownloadFile li a{color:blue;text-decoration: underline;}
  div,p,a{font-size:16px;line-height:24px}
</style>

<script type="text/javascript">
function init(){
  var sHTML ="<div align='center'><h2>下载中心</h2></div>"
  sHTML += '尊敬的用户：<br/>';
  sHTML +='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果您是第一次登录OA系统,请您点击下面链接安装平台控件。安装完成后，请关闭当前页面重新进入系统即可使用。';
  sHTML +='<ul height="50px" style="margin-bottom: 1px;">';
  /* sHTML +='<li>1、<a target="_blak" href="'+contextPath + '/static/download/一体化协同办公组件.exe" >一体化协同办公组件</a></li>'; */
  sHTML +='<li>1、<a target="_blak" href="http://oa.cd.sc.cegn.cn/downLoadExe/downLoad.action?fileID=1" >一体化协同办公组件</a></li>';
  sHTML +='<li>2、<a target="_blak" href="http://oa.cd.sc.cegn.cn/downLoadExe/downLoad.action?fileID=7" >浏览器设置脚本</a></li>';
  sHTML +='<li>3、<a target="_blak" href="http://oa.cd.sc.cegn.cn/downLoadExe/downLoad.action?fileID=4" >OFD控件</a></li>';
  
  /* sHTML +='<li>2、<a target="_blak" href="'+contextPath + '/static/download/SCCAhelp.exe" >CA数字证书助手</a></li>'; */
  sHTML +='<li>4、<a target="_blak" href="http://oa.cd.sc.cegn.cn/downLoadExe/downLoad.action?fileID=2" >CA数字证书助手</a></li>';
  /* sHTML +='<li>3、<a target="_blak" href="'+contextPath + '/static/download/证书助手（i信）.exe" >uKey控件</a></li>'; */
  sHTML +='<li>5、<a target="_blak" href="http://oa.cd.sc.cegn.cn/downLoadExe/downLoad.action?fileID=3" >uKey控件</a></li>';
  sHTML +='</ul>';
  jt('#DownloadFile').html(sHTML);
}

</script>
<body>
<div style="padding:5px 10px;font-size:12pt" id="DownloadFile">
</div>
<div style="text-align:left;padding:0 50px;margin:1px 0; font:normal 'MicroSoft YaHei';">
  <p style="margin-bottom: 1px;margin-top: 1px;font-size:12pt">客户端软件说明：<br>
    1、适用浏览器：IE6以上版本浏览器，暂不支持非IE浏览器。<br>
    2、安装说明：不支持在线安装，请先将软件下载到本地，并关闭IE浏览器后再运行软件进行安装。<br/>
    3、如有疑问请参考：
    <a style="color:blue;text-decoration: underline;" target="_blak" href="http://oa.cd.sc.cegn.cn/downLoadExe/downLoad.action?fileID=8" >系统帮助说明</a>
    与 <a style="color:blue;text-decoration: underline;" target="_blak" href="http://oa.cd.sc.cegn.cn/downLoadExe/downLoad.action?fileID=9" >常见问题手册</a>
	<!-- 3、<a style="color:blue;text-decoration: underline;" target="_blak" href="help.htm" >帮助说明</a> -->
  </p>
</div>
<!-- <div align="center" style="text-align:center;padding:0 50px;margin-top:20px; font:normal 'MicroSoft YaHei';color:red;">使用过程中有任何问题请您联系系统管理员，联系方式：188 0280 0612</div> -->
<p/>
</body>
<script type="text/javascript">
if (typeof(init)=='function') init(); //初始化
</script>
</html>