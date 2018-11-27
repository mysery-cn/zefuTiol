package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.oracle.AttachmentMapper;
import com.zefu.tiol.service.AttachmentService;

/**
 * 附件管理 接口实现
 *
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
	private AttachmentMapper attachmentMapper;
	
	@Override
	public int insertAttachment(Map<String, Object> param) {
		return attachmentMapper.insertAttachment(param);
	}

	@Override
	public List<Map<String, Object>> getFileById(String subjectId) {
		return attachmentMapper.getFileById(subjectId);
	}

	@Override
	public void deleteFileByFileId(Map<String, Object> param) {
		this.attachmentMapper.deleteFileByFileId(param);
	}

	@Override
	public List<Map<String, Object>> getAttachmentByType(Map<String, Object> param) {
		return attachmentMapper.getAttachmentByType(param);
	}

}
