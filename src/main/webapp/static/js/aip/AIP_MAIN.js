/****************************************************************************************************

全局变量

httpaddr    印章服务器地址，第一个参数为印章服务器的ip+端口+路径，一般只需要修改ip+端口即可。

*****************************************************************************************************/

function OpenModel(){

var AipObj = document.getElementById("HWPostil1").LoadFile("");
}
function HcData(){
      var urlstr = 'policyNo=20120505##ownerName=上官丰分##ownerSex=男##ownerBirthYear=1999##ownerBirthMonth=08##ownerBirthDay=08##ownerAge=101##ownerIdTC=外籍护照##ownerId=130111199908080000XX##ownerNation=中国##ownerAddress=北京市海淀区东二环120号繁荣大厦2011##ownerZip=10000##ownerComAddress=北京市海淀区东二环120号繁荣大厦2011##ownerComZip=10088##ownerHomePhone=8601089897896##ownerUnitPhone=8601089897896##ownerTelPhone=8613023456789##ownerBackPhone=手机号码##ownerService=北京繁荣昌盛股份有限公司##ownerServiceAdd=北京市海淀区东二环120号繁荣大厦2011##ownerWorkContent=公司财务##ownerWork=销售总监##ownerWorkCode=CEO##beneSeq=02##beneName=上官繁荣##beneSex=男##beneBirth=1999年08月08日##beneRelation=本人##beneSharePercent=100%##beneIdTC=外籍护照##beneId=130111199908080101##&beneSeq=02##&beneName=上官昌盛##&beneSex=男##&beneBirth=1999年08月09日##&beneRelation=兄弟##&beneSharePercent=90%##&beneIdTC=外籍护照##&beneId=130111199908080101##&beneSeq1=02##&beneName1=上官昌盛##&beneSex1=男##&beneBirth1=1999年08月09日##&beneRelation1=兄弟##&beneSharePercent1=90%##&beneIdTC1=外籍护照##&beneId1=130111199908080101##coverageName=保险计划一##coveragePaymentTerm=100##coverageBenefitTerm=100##coverageCurrentAmt=100,000,000##coverageName1=保险计划二##coveragePaymentTerm1=100##coverageBenefitTerm1=100##coverageCurrentAmt1=100,000,000##paymentMethod=一次性交清##grossPremAmtITD=2,000,00元##coverageName2=保险计划3##coveragePaymentTerm2=100##coverageBenefitTerm2=100##coverageCurrentAmt2=100,000,000##&coverageName2=保险计划4##&coveragePaymentTerm2=100##&coverageBenefitTerm2=100##&coverageCurrentAmt2=100,000,000##&coverageName3=保险计划5##&coveragePaymentTerm3=100##&coverageBenefitTerm3=100##&coverageCurrentAmt3=100,000,000##settleMethod=直接领取##divType=直接领取##couponType=直接领取##prophase=POS机缴纳保险费##renewal=银行自动转账##bankName=中国招商银行##bankAccount=6225889987898789##notify1=A##notify3=A##notify4A=A##notify4B=A##notify5=A##notify6=A##notify7=A##notify8=A##notify9=A##notify10=A##notify11=A##notify12=A##notify13=A##income=1000##incomeSource=房地产炒股等。##isDebt=有##debtSum=10000##debtContent=贷款做房地产等等等##height=100##weight=100##otherInsureComp1=保险公司1##otherInsureComp2=保险公司2##lifeAssuredSum1=XXXX##lifeAssuredSum2=XXXX##pAAssuredSum1=XXXX##pAAssuredSum2=XXXX##hospAssuredSum1=XXXX##hospAssuredSum2=XXXX##smokingYears=100##smokingFreNum=200##wineyear=100##wineamount=10000##winekind=某国某某某某酒##cyesis=2##detailNo=001##describe=详细描述的内容详细描述的内容是详细描述的内容是是##detailNo1=002##describe1=详细描述的内容详细描述的内容是详细描述的内容是是1##detailNo2=003##describe2=详细描述的内容详细描述的内容是详细描述的内容是是3##liabilitiesinfo=是； 负债金额：100万元， 负债原因：贷款运营等。##getTypes=合同给付/保险费逾期未付方式： 红利领取方式：直接领取     年金/生存金领取方式：直接领取   保险费逾期未付：自动垫交##insurer1=中意人寿##insurer2=中意人寿##insurercount1=100000##insurercount2=100000##crashcount1=100000##crashcount2=100000##hospcount1=100000##hospcount2=100000';
			urlstr = urlstr.replace(/\##/g,"\r\n");
			urlstr = urlstr.replace(/\amp;/g,"");
			document.all.HWPostil1.SetValue("FORM_DATA_TXT_FORMAT", "STRDATA:"+urlstr);
			//document.all.HWPostil1.SetValue("LABEL_ALLNOTES_STATUS", "3");//设置编辑区不可编辑
			//document.all.HWPostil1.SetValue("FORM_DATA_TXT_FORMAT", "STRDATA:"+"P1=计划1计划1\r\nT1=1年\r\nM1=100,000$\r\n&P1=计划2\r\n&T1=1年\r\n&M1=100,000$");
}
var httpaddr="http://127.0.0.1:8089/inc/seal_interface/";

/****************************************************************************************************

方法名：OpenFile					打开文档
参  数：url							可以是服务器http路径：http://127.0.0.1/test.pdf
									也可以是本地文件路径：c://test.pdf
									也可以是文件流：http://127.0.0.1/GetFile.aspx

*****************************************************************************************************/
function OpenFile(url) {
	var AipObj = document.getElementById("HWPostil1");
	var IsOpen = AipObj.LoadFile(url);
	if (IsOpen != 1) {
		alert("打开文档失败！");
	}
}
/****************************************************************************************************

方法名：AddSeal						手动盖章或手写
参  数：usertype					用户类型：0 测试用户，1 本地key用户，2 服务器key用户，3 服务器口令用户
		doaction					操作类型：0 盖章，1 手写。
		other						预留参数：
											当usertype为1,2时，值为用户真实姓名，可以为空获则取证书用户名。
											当usertype为3时，值为口令内容。

说明：httpaddr中

*****************************************************************************************************/
function AddSeal(usertype,doaction,other) {
	var AipObj = document.getElementById("HWPostil1");
	if(doaction=='0'){//盖章
	  	var islogin=AipObj.Login("",1,32767,"","");//key用户登录
	    if (islogin != 0) {
		    alert("登录失败，返回值是：" + islogin);
	    } else {
		   AipObj.CurrAction = 2568;
	    }
	}else if(doaction=='1'){//手写
	    AipObj.CurrAction = 264;
	}else if(doaction=='2'){//弹出框手写	
	   // alert("aa");
	    AipObj.Login("HWSEALDEMO", 4, 65535, "DEMO", ""); 
		  AipObj.ShowFullScreen=1;
		  var id="signatureArea";//手写区域ID
		  AipObj.SetValue("SET_POPWND_MAX_WIDTH","260");//设置弹出框手写大小
		  //AipObj.SetValue("SET_POPWND_MAX_HEIGHT","700");//设置弹出框手写大小
			AipObj.SetValue(id,":PROP::LABEL:1");//
			AipObj.SetValue(id,":PROP::CLICKPOP:1");
			AipObj.SetValue(id,":PROP:BACKCOLOR:-1");	
			AipObj.SetValue(id,":PROP:BORDER:0");
			AipObj.SetValue(id,":PROP:ACTIVATE:1");
	}else if(doaction=='3'){//抄写
	    AipObj.Login("HWSEALDEMO", 4, 65535, "DEMO", ""); 
		  AipObj.ShowFullScreen=1;
		  var id="transcribeArea";//手写区域ID
			AipObj.SetValue(id,":PROP::LABEL:3");
			AipObj.SetValue(id,":PROP:ACTIVATE:1");
		
	}else if(doaction=='4'){//点击抄写区域弹出抄写框
	    AipObj.Login("HWSEALDEMO", 4, 65535, "DEMO", ""); 
		  AipObj.ShowFullScreen=1;
	}
}
/****************************************************************************************************

方法名：AutoSeal					自动盖章
参  数：usertype					用户类型：0 测试用户，1 本地key用户，2 服务器key用户，3 服务器口令用户
		doaction					操作类型：0 普通印章，1 右骑缝章，2对开骑缝
		searchtype					定位盖章位置类型：只对普通印章doaction=0时有效，0 绝对坐标，1 文字定位
		searchstring				定位信息：只对普通印章doaction=0时有效
											searchtype为0时，searchstring为x:y:page格式，即200:500:0   x为横向坐标1-50000，y为纵向坐标1-50000，page为盖章页码从0开始
											searchtype为1时，searchstring为要查找的文字字符串
		other						预留参数：
											当usertype为1,2时，值为用户真实姓名，可以为空获则取证书用户名。
											当usertype为3时，值为口令内容。

*****************************************************************************************************/
function AutoSeal(usertype,doaction,searchtype,searchstring,other){
	var AipObj = document.getElementById("HWPostil1");
	var islogin=AipObj.Login("",1,32767,"","");//key用户登录
	if (islogin != 0) {
		alert("登录失败，返回值是：" + islogin);
	} else {
		if(doaction==0){
			var num=AipObj.PageCount;
			var str=searchstring.split(":");
			var page="";
			if(searchtype==0){
				AipObj.AddQifengSeal(0,0+","+str[0]+",0,"+str[1]+",50,"+str[2],"","AUTO_ADD_SEAL_FROM_PATH");
			}else if(searchtype==1){
				AipObj.AddQifengSeal(0,"AUTO_ADD:0,"+num+",0,0,1,"+searchstring+")|(0,","","AUTO_ADD_SEAL_FROM_PATH");
			}
		}else if(doaction==1){
			var num=AipObj.PageCount;
			var page="";
			for(i=1;i<num;i++){
				page+=i+",";
			}
			var bl=100/(num-1);
			AipObj.AddQifengSeal(0,0+",25000,1,3,"+bl+","+page,"","AUTO_ADD_SEAL_FROM_PATH");
		}else if(doaction==2){
			var num=AipObj.PageCount;
			for(i=0;i<num-1;i++){
				AipObj.AddQifengSeal(0,i+",25000,2,3,50,1","","AUTO_ADD_SEAL_FROM_PATH");
			}
		}
	}
}
/****************************************************************************************************

方法名：SaveTo								保存文档
参  数：savetype							文档保存方式：0 保存本地，1 保存到服务器
		filepath							文档保存路径：
													savetype为0时为本地路径，可以为空，为空会弹出地址框，例如c:/test/1.pdf
													savetype为1时为服务器路径，例如http://127.0.0.1/getfile.php,地址为文件接收服务器地址，接收文件流FileBlod
		filecode							文档惟一标示：
													savetype为0时为文档类型，值可以为doc，pdf，aip，word，jpg，gif，bmp等
													savetype为1时为文档唯一标示，用做服务器接收的参数FileCode

*****************************************************************************************************/
function SaveTo(savetype,filepath,filecode) {
	var AipObj = document.getElementById("HWPostil1");
	if(savetype==0){
		var issave = AipObj.SaveTo(filepath,filecode,0);
		if (issave == 0) {
			alert("保存失败！");
		}else{
		  alert("保存成功！c:\\test.pdf");
		}
	}else{
		alert("SaveTo方法参数错误！")
	}
}
/****************************************************************************************************

方法名：ShowFullScreen					全屏查看
参  数：slog							1为全屏，0为普通

*****************************************************************************************************/
function ShowFullScreen(slog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowFullScreen = slog;
}
/****************************************************************************************************

方法名：FilePrint						打印文档
参  数：plog							0快速打印，1有打印对话框

*****************************************************************************************************/
function FilePrint(plog) {
	var AipObj = document.getElementById("HWPostil1");
	var isprint = AipObj.PrintDoc(1, plog);
	if (isprint == 0) {
		alert("打印失败！");
	}
}
/****************************************************************************************************

方法名：FileMerge						合并文件
参  数：filepath						要合并文件路径，如果只为空则插入一个空白页
		page							文件要插入的页数,插入到第一页值为0

*****************************************************************************************************/
function FileMerge(filepath,page) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.Login("HWSEALDEMO**",4,65535,"DEMO","");
	if(filepath==""){
		var isMerge = AipObj.InsertEmptyPage(page,0,0,0);
	}else{
		var isMerge = AipObj.MergeFile(page,filepath);
	}
	if (isMerge == 0) {
		alert("合并文档失败！");
	}
}
function merge2File(){
	var obj=document.getElementById("HWPostil1");
	if(!obj.IsLogin()){
		alert("请登录");
		return false;
	}
	if(!obj.IsOpened()){
		alert("必须打开一个PDF文件");
		return false;
	}
	obj.MergeFile(-1,"");
}
/****************************************************************************************************

方法名：SetPenwidth						设置手写笔宽
参  数：无

*****************************************************************************************************/
function SetPenwidth() {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.CurrPenWidth=-1;
}
/****************************************************************************************************

方法名：SetColor						设置手写笔颜色
参  数：无

*****************************************************************************************************/
function SetColor() {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.CurrPenColor=-1;
}
/****************************************************************************************************

方法名：SetPressurelevel				设置手写压感
参  数：无

*****************************************************************************************************/
function SetPressurelevel() {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.Pressurelevel=-1;
}
/****************************************************************************************************

方法名：SetAction						设置鼠标状态
参  数：SetLog							设置状态：1 浏览，2 文字选择

*****************************************************************************************************/
function SetAction(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.CurrAction=SetLog;
}
/****************************************************************************************************

方法名：SetPageMode						设置视图
参  数：SetLog							设置操作状态：1 原始大小，2 适应宽度，3 窗口大小，4 双页显示，5 无边框

*****************************************************************************************************/
function SetPageMode(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	if(SetLog==1){
		AipObj.SetPageMode(1,100);
	}else if(SetLog==2){
		AipObj.SetPageMode(2,100);
	}else if(SetLog==3){
		AipObj.SetPageMode(4,100);
	}else if(SetLog==4){
		AipObj.SetPageMode(8,2);
	}else if(SetLog==5){
		AipObj.SetPageMode(16,1);
	}
}
/****************************************************************************************************

方法名：ShowToolBar						设置工具栏
参  数：SetLog							设置状态：0 隐藏，1 显示

*****************************************************************************************************/
function ShowToolBar(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowToolBar=SetLog;
}
/****************************************************************************************************

方法名：ShowDefMenu						设置菜单
参  数：SetLog							设置状态：0 隐藏，1 显示

*****************************************************************************************************/
function ShowDefMenu(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowDefMenu=SetLog;
}
/****************************************************************************************************

方法名：ShowScrollBarButton				设置滚动条
参  数：SetLog							设置状态：2 隐藏滚动条，1 隐藏滚动条的工具栏，0 显示滚动条

*****************************************************************************************************/
function ShowScrollBarButton(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowScrollBarButton=SetLog;
}
/****************************************************************************************************

方法名：SetFullScreen					设置全屏
参  数：SetLog							设置状态：1全屏，0正常

*****************************************************************************************************/
function SetFullScreen(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowFullScreen =SetLog;
}
/****************************************************************************************************

方法名：SearchText						查找文字
参  数：stxt							要查找的文字
		matchcase						是否区分大小写
		findnext						查找位置。0:从头可以查找;1:查找下一个

*****************************************************************************************************/
function SearchText(stxt,matchcase,findnext) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.SearchText(stxt,matchcase,findnext);
}
/****************************************************************************************************

方法名：FileAddseal						使用文件盖章
参  数：selpath							印章路径
		doaction						操作类型：0 普通印章，1 右骑缝章，2对开骑缝
		searchtype						定位盖章位置类型：只对普通印章doaction=0时有效，0 绝对坐标，1 文字定位
		searchstring					定位信息：只对普通印章doaction=0时有效
											searchstring为x:y:page格式，即200:500:0   x为横向坐标1-50000，y为纵向坐标1-50000，page为盖章页码从0开始

*****************************************************************************************************/
function FileAddseal(selpath,doaction,searchtype,searchstring) {
	var AipObj = document.getElementById("HWPostil1");
	var islogin=AipObj.Login("",1,65535,"","");
	if (islogin != 0) {
		alert("盖章失败！错误编号：" + islogin);
	} else {
		AipObj.ShowErrMsgMode=0;
		if(doaction==0){
			var num=AipObj.PageCount;
			var str=searchstring.split(":");
			var page="";
			if(searchtype==0){
				if(AipObj.AddQifengSeal(0,0+","+str[0]+",0,"+str[1]+",50,"+str[2],"","AUTO_ADD_SEAL_FROM_PATH")!=1){
					AipObj.AddQifengSeal(0,0+","+str[0]+",8,"+str[1]+",50,"+str[2]+","+selpath,"","AUTO_ADD_SEAL_FROM_PATH");
				}
			}else if(searchtype==1){
				if(AipObj.AddQifengSeal(0,"AUTO_ADD:0,"+num+",0,0,1,"+searchstring+")|(0,","","AUTO_ADD_SEAL_FROM_PATH")!=1){
					AipObj.AddQifengSeal(0,"AUTO_ADD:0,"+num+",0,0,1,"+searchstring+")|(8,"+selpath,"","AUTO_ADD_SEAL_FROM_PATH");
				}
			}
		}else if(doaction==1){
			var num=AipObj.PageCount;
			var page="";
			for(i=1;i<num;i++){
				page+=i+",";
			}
			var bl=100/(num-1);
			AipObj.AddQifengSeal(0,0+",25000,9,3,"+bl+","+page+","+selpath,"","AUTO_ADD_SEAL_FROM_PATH");
		}else if(doaction==2){
			var num=AipObj.PageCount;
			for(i=0;i<num-1;i++){
				AipObj.AddQifengSeal(0,i+",25000,10,3,50,1,"+selpath,"","AUTO_ADD_SEAL_FROM_PATH");
			}
		}
	}
}
function UploadPDF(){
	   var AipObj = document.getElementById("HWPostil1");
		 AipObj.HttpInit(); //初始化HTTP引擎。
	   AipObj.HttpAddPostString("name","test.pdf"); //设置上传变量文件名。
	   AipObj.HttpAddPostCurrFile("FileContent");//设置上传当前文件,文件标识为FileBlod。
     var ispost=AipObj.HttpPost("http://127.0.0.1:8080/Seal/doc/saveTo.jsp");//上传数据。
     if(ispost=="kkkkk"){
	     alert("上传成功");
	   }else{
	     alert("上传失败:"+ispost);
	  }
}
function Reset(){
    var AipObj = document.getElementById("HWPostil1");
    AipObj.ShowFullScreen=1;
    var id="signatureArea";//手写区域ID
    AipObj.SetValue(id,'');//先清空手写区域
		AipObj.SetValue(id,":PROP::LABEL:1");//不可选
		AipObj.SetValue(id,":PROP::CLICKPOP:1");
		AipObj.SetValue(id,":PROP:BORDER:0");
		AipObj.SetValue(id,":PROP:ACTIVATE:1");
}
function GetWrite(){
   var AipObj = document.getElementById("HWPostil1");
   var id="signatureArea";//手写区域ID
   var writedata=AipObj.GetValueEx(id,44 ,"gif",0,"");
	 AipObj.setValueEx("signatureArea1",44,0,writedata);
}
function InsertNoteWrite(){
		  var AipObj=document.getElementById("HWPostil1");
		  AipObj.LoadFile("");//可以打开本地文件，也可以打开服务端http路径
		  var id="signatureArea";//手写区域ID
		  var keywordOne = AipObj.FindText("客户签字",0,0,0,0,2,50000,50000,1);
	   	var strArryOne = keywordOne.split(",");
		  var pageNum = strArryOne[0];
		  var xOne = new Number(strArryOne[1]);
	  	xOne = xOne+3000;
	  	var yOne = new Number(strArryOne[2]);
		  yOne = yOne-1700;
		  AipObj.Login("HWSEALDEMO", 4, 65535, "DEMO", ""); 
		  AipObj.InsertNote(id,pageNum,2,xOne,yOne,7000,3000);
		  AipObj.SetValue(id,':PROP:BORDER:0');
		  AipObj.SetValue(id,':PROP::CLICKPOP:1');	
		  AipObj.Logout();
		  setTimeout("write()",1000);//等待1秒

		 
}
function write(){
     var AipObj=document.getElementById("HWPostil1");
     AipObj.Login("HWSEALDEMO", 4, 65535, "DEMO", ""); 
	   AipObj.ShowFullScreen = 1;
	   var id="signatureArea";//手写区域ID
		 AipObj.SetValue(id,':PROP:ACTIVATE:1');
}
function settxt(){
    var AipObj=document.getElementById("HWPostil1");
	  AipObj.Login("sys_admin", 5, 32767, "", "");
		AipObj.CurrAction=1544;
}
function setsign(){
    var AipObj=document.getElementById("HWPostil1");
    AipObj.Login("sys_admin", 5, 32767, "", "");
	  AipObj.CurrAction=1288;
}
function InsertYwm(){
   var AipObj=document.getElementById("HWPostil1");
   AipObj.InsertPicture("id001","BARCODEDATA:12121212121",0,0,0,13172836);
}
function InsertPdf417(){
   var AipObj=document.getElementById("HWPostil1");
   AipObj.InsertPicture("id001","BARCODEDATA:12121212121",0,5000,5000,200);
}
function InsertQr(){
   var AipObj=document.getElementById("HWPostil1");
   AipObj.InsertPicture("id001","BARCODEDATA:12121212121",0,15000,5000,13107500);
}