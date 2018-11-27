package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.Subject;

/**
 * 议题信息接口类
 * 
 * @author：陈东茂
 * @date：2018年10月26日
 */
public interface SubjectService {
	
	/**
	 * 获取议题总数及议题前置总数
	 * @param parameter
	 * @return
	 */
	Map<String, Object> getDangjianSubjectCount(Map<String, Object> parameter);
	
	/**
	 * 查询会议议题信息列表
	 * @param parameter 参数
	 * @return
	 */
	List<Map<String, Object>> listSubjectByMeeting(Map<String, Object> parameter);

	/**
	 * 获取议题信息详情
	 * @param parameter 参数
	 * @return
	 */
	Map<String, Object> getSubjectDetail(Map<String, Object> parameter);
	
	/**
	 * 获取议题会议关联列表
	 * @param parameter
	 * @return
	 */
	Map<String, Object> querySubjectMeetingProcess(Map<String, Object> parameter);
	
	/**
	 * 查询党建局议题列表
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> listDangjianSubject(Map<String, Object> parameter,Page<Map<String, Object>> page);
	
	/**
	 * 查询党建局议题总数
	 * @param parameter
	 * @return
	 */
	int countDangjianSubject(Map<String, Object> parameter);
	
	/**
	 * 
	 * @功能描述 分页查询会议议题信息
	 * @time 2018年11月1日下午6:27:16
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> querySubjectByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
	/**
	 * 
	 * @功能描述 查询会议议题信息总记录数
	 * @time 2018年11月1日下午6:27:02
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getSubjectTotalCount(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 平台获取议题列表
	   * @参数: @param parameter
	   * @参数: @param page
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 上午10:46:20
	 */
	List<Map<String, Object>> querySubjectList(Map<String, Object> parameter, Page<Map<String, Object>> page) throws Exception;
	
	/**
	   * @功能描述: TODO 平台获取议题总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 上午10:46:31
	 */
	int querySubjectTotalCount(Map<String, Object> parameter) throws Exception;

	/**
	   * @功能描述: 更新议题
	 */
	void updateSubject(Map<String, Object> pojo2Map);

	/**
	   * @功能描述: 根据会议来源ID，将议题状态标为‘删除’
	 */
	void updateSubjectByMeetingSid(String meetingSid);

	/**
	 * @功能描述: 保存议题信息
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	Subject saveSubject(Map<String, Object> parameter) throws Exception;

	/**
	 * @功能描述: 根据会议查询议题信息
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	List<Map<String,Object>> getSubjectByMeetingId(Map<String, Object> parameter);

	/**
	 * @功能描述: 根据主键查询议题信息
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	Map<String,Object> getSubjectById(Map<String, Object> parameter);

	/**
	 * @功能描述: 保存议题附件
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	int insertAttachment(Map<String, Object> parameter);

	/**
	 * @功能描述: 查询会议关联附件
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	List<Map<String,Object>> getSubjectFileById(Map<String, Object> parameter);

	/**
	 * @功能描述: 根据file_id更新附件表状态
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	int deleteSubjectFileByFileId(Map<String, Object> parameter);

	/**
	 * 校验议题异常情况
	 */
	void checkSubject();
}
