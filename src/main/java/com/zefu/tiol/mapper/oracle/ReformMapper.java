package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
   * @工程名 : szyd
   * @文件名 : ReformMapper.java
   * @工具包名：com.zefu.tiol.mapper.oracle
   * @功能描述: TODO
   * @创建人 ：林鹏  改革局功能模块Dao
   * @创建时间：2018年10月30日 下午2:31:36
   * @版本信息：V1.0
 */
@Repository
public interface ReformMapper {
	
	/**
	   * @功能描述: TODO 查询会议议题数据
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午2:38:44
	 */
	List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询会议议题总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午2:38:50
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
