package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
   * @工程名 : szyd
   * @文件名 : SpecialMapper.java
   * @工具包名：com.zefu.tiol.mapper.oracle
   * @功能描述: TODO 专项任务Dao
   * @创建人 ：林鹏
   * @创建时间：2018年11月4日 下午3:24:44
   * @版本信息：V1.0
 */
@Repository
public interface SpecialMapper {
	
	
	
	/**
	   * @功能描述: TODO
	   * @参数: @param param
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午3:21:21
	 */
	List<Map<String, Object>> querySpecialList(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 保存任务来源
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:01
	 */
	void saveSpecial(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 修改任务来源
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:01
	 */
	void updateSpecial(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 删除任务来源
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:01
	 */
	void deleteSpecial(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 查询任务来源详情
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:12
	 */
	Map<String, Object> querySpecialDetail(Map<String, Object> param) throws Exception;
}
