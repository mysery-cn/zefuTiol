package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import org.apache.ibatis.annotations.Param;

/**
   * @工程名 : szyd
   * @文件名 : ReformService.java
   * @工具包名：com.zefu.tiol.service
   * @功能描述: TODO
   * @创建人 ：林鹏 改革局功能模块Service
   * @创建时间：2018年10月30日 下午2:30:15
   * @版本信息：V1.0
 */
public interface ReformService {
	
	/**
	   * @功能描述: TODO 查询会议议题数据
	   * @参数: @param parameter
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午2:36:35
	 */
	List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	/**
	   * @功能描述: TODO 查询会议议题总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午2:36:50
	 */
	int querySubjectTotalCount(Map<String, Object> parameter);

	/**
	 *
	 * @功能描述 获取总记录数
	 * @time 2018年10月30日下午3:56:13
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getMeetingSubjectTotalCount(Map<String, Object> param);

}
