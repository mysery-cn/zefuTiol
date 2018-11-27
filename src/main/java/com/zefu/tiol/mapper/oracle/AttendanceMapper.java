package com.zefu.tiol.mapper.oracle;

import com.zefu.tiol.pojo.Attendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AttendanceMapper {
    /**
     * 保存列席人员
     * @param attendanceList 列席人员
     * @return
     * @by 李缝兴
     */
    int batchInsertAttendance(List<Attendance> attendanceList);

    /**
     * 查询参会人员信息
     * @param param
     * @return
     * @by 李缝兴
     */
    List<Map<String,Object>> getAttendanceList(Map<String,Object> param);

    /**
     * 删除参会人员信息
     * @param subjectId
     * @return
     * @by 李缝兴
     */
    int deleteAttendanceBySubejctId(@Param("subjectId") String subjectId);
}
