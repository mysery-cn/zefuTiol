package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import com.zefu.tiol.pojo.Subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.Subject;
/**
 * 议题信息数据操作类
 * 
 * @author：陈东茂
 * @date：2018年10月26日
 */
@Repository
public interface SubjectMapper {

	
	/**
	 * 查询会议议题信息列表
	 * @param parameter 参数
	 * @return
	 */
	List<Map<String, Object>> listSubjectByMeeting(Map<String, Object> parameter);
	
	/**
	 * 查询议题审议结果列表
	 * @param companyId 企业ID
	 * @param subjectId 议题ID
	 * @param meetingId 会议ID
	 * @return
	 */
	List<Map<String, Object>> listDeliberationBySubject(@Param("companyId")String companyId,@Param("subjectId")String subjectId,@Param("meetingId")String meetingId);
	
	/**
	 * 获取议题详情
	 * @param parameter
	 * @return
	 */
	Map<String, Object> getSubjectDetail(Map<String, Object> parameter);
	
	/**
	 * 获取议题相关会议列表
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> getSubjectMeetingList(Map<String, Object> parameter);
	
	/**
	 * 查询议题关联会议关联层数统计
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> countSubjectMeetingLevel(Map<String, Object> parameter);
	
	/**
	 * 获取议题所需会议类型列表
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> getRelSubjectItem(Map<String, Object> parameter);
	
	/**
	 * 查询党建局议题列表
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> listDangjianSubject(Map<String, Object> parameter);
	
	/**
	 * 查询党建局议题总数
	 * @param parameter
	 * @return
	 */
	int countDangjianSubject(Map<String, Object> parameter);
	
	/**
	 * 
	 * @功能描述 分页查询会议议题信息
	 * @time 2018年11月1日下午5:57:10
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> querySubjectByPage(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 查询会议议题信息总记录数
	 * @time 2018年11月1日下午5:57:34
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
	List<Map<String, Object>> querySubjectList(Map<String, Object> parameter) throws Exception;
	
	/**
	   * @功能描述: TODO 平台获取议题总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 上午10:46:31
	 */
	int querySubjectTotalCount(Map<String, Object> parameter) throws Exception;

	void updateSubject(Map<String, Object> param);

	void updateSubjectByMeetingSid(@Param("meetingSid")String meetingSid);

	/**
	 * 保存议题
	 * @param subject 议题实体
	 * @return
	 * @by 李缝兴
	 */
	int insertSubject(Subject subject);

	/**
	 * 保存关联事项
	 * @param itemRelevanceList 关联事项
	 * @return
	 * @by 李缝兴
	 */
	int batchInsertItemRelevance(@Param("list")List<Map<String,Object>> itemRelevanceList);

	/**
	 * 删除关联事项
	 * @param subjectId 议题ID
	 * @return
	 * @by 李缝兴
	 */
	int removeItemRelevanceBySubejctId(@Param("subjectId") String subjectId);

	/**
	 * 关联事项查询
	 * @param parameter
	 * @return
	 * @by 李缝兴
	 */
	List<Map<String,Object>> getItemRelevanceList(Map<String,Object> parameter);

	/**
	 * 保存关联议题
	 * @param subjectRelevanceList 关联议题
	 * @return
	 * @by 李缝兴
	 */
	int batchInsertSubjectRelevance(@Param("list")List<Map<String,Object>> subjectRelevanceList);

	/**
	 * 删除关联议题
	 * @param subjectId 议题ID
	 * @return
	 * @by 李缝兴
	 */
	int removeSubjectRelevanceBySubejctId(@Param("subjectId") String subjectId);

	/**
	 * 查询关联议题
	 * @param parameter
	 * @return
	 * @by 李缝兴
	 */
	List<Map<String,Object>> getSubjectRelevance(Map<String,Object>  parameter);

	/**
	 * 根据会议查询议题信息
	 * @param param 查询信息
	 * @return
	 * @by 李缝兴
	 */
	List<Map<String,Object>> getSubjectByMeetingId(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 保存会议议题
	   * @参数: @param subject
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午2:54:31
	 */
	void saveSubject(Subject subject);

	/**
	 * @功能描述: 根据主键查询议题
	 * @参数: @param subject
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	Map<String,Object> getSubjectById(@Param("subjectId") String subjectId);

	/**
	 * @功能描述: 插入会议信息
	 * @参数: @param subject
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	int insertAttachment(Map<String,Object> param);

	/**
	 * @功能描述: 查询附件信息
	 * @参数: @param subjectId
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	List<Map<String,Object>> getSubjectFileById(@Param("subjectId") String subjectId);

	/**
	 * @功能描述: 删除会议附件
	 * @参数: @param fileId
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	int deleteSubjectFileByFileId(@Param("fileId") String fileId);
	
	/**
	 * 查询附件表附件信息（非平台）
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> getAttachmentByType(Map<String, Object> param);

	List<Map<String, Object>> getAttendanceList(Map<String, Object> conParam);

	List<Map<String, Object>> getDeliberationList(Map<String, Object> conParam);

	List<Map<String, Object>> getRelSubjectList(Map<String, Object> conParam);

	List<Map<String, Object>> getSubjectAfter(Map<String, Object> parameter);
}
