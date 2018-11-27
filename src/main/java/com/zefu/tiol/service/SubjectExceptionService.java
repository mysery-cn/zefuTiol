package com.zefu.tiol.service;

import com.yr.gap.dal.plugin.child.Page;

import java.util.List;
import java.util.Map;

public interface SubjectExceptionService {
	
	/**
	 * 根据异常ID修改议题异常
	 * @param parameter
	 */
	void modifySubjectException(Map<String, Object> parameter);
	
	/**
	 * 根据异常ID删除议题异常
	 * @param parameter
	 * @return
	 */
	int removeSubjectException(Map<String, Object> parameter);

    /**
       * @功能描述:  查询议题异常列表
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 14:17
    */
    List<Map<String, Object>> querySubjectExceptionByPage(Map<String, Object> param, Page<Map<String, Object>> page);

    /**
       * @功能描述:  查询议题异常总数
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 14:17
    */
    int querySubjectExceptionTotalCount(Map<String, Object> param);



}
