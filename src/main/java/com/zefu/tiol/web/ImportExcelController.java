package com.zefu.tiol.web;

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.zefu.tiol.service.ImportExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
   * @工程名 : szyd
   * @文件名 : ImportExcelController.java
   * @工具包名：com.zefu.tiol.web
   * @功能描述: TODO Excel文件导入
   * @创建人 ：林鹏
   * @创建时间：2018年11月12日 下午8:14:08
   * @版本信息：V1.0
 */
@Controller
@RequestMapping("/import")
public class ImportExcelController {
	
	@Resource(name = "importExcelService")
	private ImportExcelService excelService;
	
	
	/**
	   * @功能描述: TODO 导入会议信息
	   * @参数: @param meetingFile
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月12日 下午8:31:57
	 */
	@RequestMapping("/importMeeting")
	public ModelAndView importMeeting(@RequestParam("meetingFile") MultipartFile meetingFile, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/meeting/importExcel/meeting_import.jsp");
		//登陆信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);

		try {
			if(meetingFile != null){
				String errMsg = excelService.importMeeting(meetingFile.getInputStream(),loginUser);
				if(StringUtils.isBlank(errMsg)){
                    mv.addObject("flag",true);
                }else{
                    mv.addObject("flag",false);
                    mv.addObject("errMsg","Excel会议数据中第：" + errMsg + "条会议已存在！");
                }
			}else{
				mv.addObject("flag",false);
                mv.addObject("errMsg","附件不能为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("flag",false);
            mv.addObject("errMsg","会议导入失败！");
		}
		return mv;
	}

	/**
	   * @功能描述:  导出数据采集异常Excel
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/25 15:26
	*/
    @RequestMapping(value="/exportExceptionExcel",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void exportExceptionExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        OutputStream os = null;
        try{
            String fileName = "采集数据异常信息.xls";
            //获取文件地址
            HSSFWorkbook wb = excelService.generateExceptionaExcel();
            //表示会下载文件
            fileName = new String(fileName.getBytes(),"ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("multipart/form-data");
//            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                os.close();
            }
        }
    }

	
}
