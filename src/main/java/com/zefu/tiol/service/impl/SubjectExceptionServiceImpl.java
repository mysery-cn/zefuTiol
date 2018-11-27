package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.oracle.SubjectExceptionMapper;
import com.zefu.tiol.service.SubjectExceptionService;

@Service("subjectExceptionService")
public class SubjectExceptionServiceImpl implements SubjectExceptionService{
	
	@Autowired
	private SubjectExceptionMapper subjectExceptionMapper;

	@Override
	public void modifySubjectException(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		subjectExceptionMapper.updateSubjectException(parameter);
	}

	@Override
	public int removeSubjectException(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return subjectExceptionMapper.deleteSubjectException(parameter);
	}

	/**
	   * @功能描述:  查询议题异常列表
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/19 14:19
	*/
    @Override
    public List<Map<String, Object>> querySubjectExceptionByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
        int maxRow = page.getCurrentPage()*page.getPageSize();;
        int minRow = (page.getCurrentPage()-1)*page.getPageSize();
        param.put("minRow", minRow);
        param.put("maxRow", maxRow);
	    return subjectExceptionMapper.querySubjectExceptionByPage(param);
    }

    /**
       * @功能描述:  查询议题异常总数
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 14:19
    */
    @Override
    public int querySubjectExceptionTotalCount(Map<String, Object> param) {
        return subjectExceptionMapper.querySubjectExceptionTotalCount(param);
    }
}
