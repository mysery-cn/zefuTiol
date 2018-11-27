package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 会议类型
 * @time 2018年10月30日下午2:59:25
 * @author Super
 *
 */
@Repository
public interface MeetingTypeMapper {

	/**
	 * 
	 * @功能描述 获取会议类型信息
	 * @time 2018年10月30日下午3:00:10
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryList(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 新增会议类型
	   * @参数: @param param
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午9:24:01
	 */
	void saveMeetingType(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 修改会议类型
	   * @参数: @param param
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午9:24:01
	 */
	void updateMeetingType(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 删除会议类型
	   * @参数: @param param
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午9:24:01
	 */
	void deleteMeetingType(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 查询会议类型详情
	   * @参数: @param param
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午9:24:01
	 */
	Map<String, Object> queryMeetingTypeDetail(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 查询会议类型列表
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月3日 上午10:54:32
	 */
	List<Map<String, Object>> queryMeetingTypeListByPage(Map<String, Object> param);
	
}
