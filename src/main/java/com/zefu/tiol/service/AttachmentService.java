package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 附件管理 业务层
 *
 */
public interface AttachmentService {
	/**
	 * @功能描述: 插入会议信息
	 * @参数: @param subject
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	int insertAttachment(Map<String, Object> param);

	/**
	 * @功能描述: 查询附件信息
	 * @参数: @param subjectId
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	List<Map<String, Object>> getFileById(@Param("subjectId") String subjectId);

	/**
	 * 查询附件表附件信息（非平台）
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getAttachmentByType(Map<String, Object> param);

	/**
	 * 删除会议附件
	 * 
	 * @param param
	 */
	void deleteFileByFileId(Map<String, Object> param);
}
