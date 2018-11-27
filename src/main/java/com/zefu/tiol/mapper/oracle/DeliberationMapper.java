package com.zefu.tiol.mapper.oracle;

import com.zefu.tiol.pojo.Deliberation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeliberationMapper {
    /**
     * 保存审议结果
     * @param deliberationList 审议结果
     * @return
     * @by 李缝兴
     */
    int batchInsertDeliberation(List<Deliberation> deliberationList);

    List<Map<String,Object>> getDeliberationList(Map<String, Object> param);

    int deleteDeliberationBySubejctId(@Param("subjectId") String subjectId);
}
