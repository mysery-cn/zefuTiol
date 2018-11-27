package com.zefu.tiol.service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.SubjectTodo;

import java.util.List;
import java.util.Map;

/**
 * 会议填报待办业务接口类
 *
 * @author：李缝兴
 * @date：2018年11月14日
 */
public interface SubjectTodoService {
    /**
     * 保存待办信息
     * @param todo
     * @return
     */
    int saveSubjectTodo(SubjectTodo todo);

    /**
     * 根据更新待办
     * @param todo
     * @return
     */
    int updateSubjectTodo(SubjectTodo todo);


    /**
     * 获取当前登陆人待办
     * @param param
     * @return
     */
    List<SubjectTodo> getSubjectTo(Map<String, Object> param);

    /**
     * 获取当前登陆待办已办数量
     * @param
     * @return
     */
    Map<String,Object> getPersonalTodoCount(String userId);


    /**
     * 分页查询待办信息
     * @param param
     * @param page
     * @return
     */
    List<SubjectTodo> querySubjectListWithPage(Map<String, Object> param,Page<Map<String, Object>> page);

    /**
     * 统计数量
     * @param param
     * @return
     */
    int countSubjectTodo(Map<String, Object> param);

}
