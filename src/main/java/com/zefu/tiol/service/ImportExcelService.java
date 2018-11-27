package com.zefu.tiol.service;

import com.yr.gap.common.core.LoginUser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.InputStream;

/**
   * @工程名 : szyd
   * @文件名 : ImportExcelService.java
   * @工具包名：com.zefu.tiol.service
   * @功能描述: TODO Excel文件导入Service
   * @创建人 ：林鹏
   * @创建时间：2018年11月12日 下午8:17:02
   * @版本信息：V1.0
 */
public interface ImportExcelService {	
	
	/**
	   * @param inputStream 
	 * @param loginUser 
	 * @功能描述: TODO 导入会议信息
	   * @参数: 
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月12日 下午8:27:48
	 */
    String importMeeting(InputStream inputStream, LoginUser loginUser) throws Exception;

    /**
       * @功能描述:  生成采集数据异常信息Excel数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/25 14:54
    */
    HSSFWorkbook generateExceptionaExcel();
	
	
}
