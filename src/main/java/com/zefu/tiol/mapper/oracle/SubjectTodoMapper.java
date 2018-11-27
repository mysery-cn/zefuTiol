package com.zefu.tiol.mapper.oracle;

import com.zefu.tiol.pojo.SubjectTodo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SubjectTodoMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(SubjectTodo record);

    int insertSelective(SubjectTodo record);

    SubjectTodo selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(SubjectTodo record);

    int updateByPrimaryKey(SubjectTodo record);

    List<SubjectTodo> selectByParam(Map<String, Object> param);

    int updateSubjectTodo(Map<String, Object> param);

    int getPersonalTodoCount(@Param("userId") String userId);

    int getPersonalDoneCount(@Param("userId") String userId);

    List<SubjectTodo> querySubjectPageList(Map<String, Object> param);

    int countSubjectTodo(Map<String, Object> param);
}