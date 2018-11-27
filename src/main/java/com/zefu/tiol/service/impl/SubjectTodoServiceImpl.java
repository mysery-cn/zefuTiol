package com.zefu.tiol.service.impl;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.SubjectTodoMapper;
import com.zefu.tiol.pojo.SubjectTodo;
import com.zefu.tiol.service.SubjectTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("subjectTodoService")
public class SubjectTodoServiceImpl implements SubjectTodoService {
    @Autowired
    private SubjectTodoMapper subjectTodoMapper;

    @Override
    public int saveSubjectTodo(SubjectTodo todo) {
        return subjectTodoMapper.insertSelective(todo);
    }

    @Override
    public int updateSubjectTodo(SubjectTodo todo) {
        return subjectTodoMapper.updateByPrimaryKeySelective(todo);
    }

    @Override
    public List<SubjectTodo> getSubjectTo(Map<String, Object> param) {
        return subjectTodoMapper.selectByParam(param);
    }

    @Override
    public Map<String, Object> getPersonalTodoCount(String userId) {
        int todoNum = subjectTodoMapper.getPersonalTodoCount(userId);
        int doneNum = subjectTodoMapper.getPersonalDoneCount(userId);
        Map<String,Object> personalTodoCount = new HashMap<String,Object>();
        personalTodoCount.put("doneNum",doneNum);
        personalTodoCount.put("todoNum",todoNum);
        return personalTodoCount;
    }

    @Override
    public List<SubjectTodo> querySubjectListWithPage(Map<String, Object> param, Page<Map<String, Object>> page) {
        int maxRow = page.getCurrentPage()*page.getPageSize();;
        int minRow = (page.getCurrentPage()-1)*page.getPageSize();
        param.put("minRow", minRow);
        param.put("maxRow", maxRow);
        List<SubjectTodo> list = subjectTodoMapper.querySubjectPageList(param);
        return list;
    }

    @Override
    public int countSubjectTodo(Map<String, Object> param) {
        return subjectTodoMapper.countSubjectTodo(param);
    }
}
