package com.zefu.tiol.mapper.oracle;

import com.zefu.tiol.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
   * @工程名 : szyd
   * @文件名 : ImportExcelMapper.java
   * @工具包名：com.zefu.tiol.mapper.oracle
   * @功能描述: TODO Excel文件导入Dao
   * @创建人 ：林鹏
   * @创建时间：2018年11月12日 下午8:19:12
   * @版本信息：V1.0
 */
@Repository
public interface ImportExcelMapper {
	
	/**
	   * @功能描述: TODO 查询会议类型ID
	   * @参数: @param alias
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:10:26
	 */
	String queryMeetingType(String alias);
	
	/**
	   * @功能描述: TODO 查询匹配会议别名数据
	   * @参数: @param alias
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:17:48
	 */
	List<Map<String, String>> queryMeetingTypeList(String alias);
	
	/**
	   * @功能描述: TODO 保存会议参会人信息
	   * @参数: @param attendee
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:25:26
	 */
	void saveAttendess(Attendee attendee);
	
	/**
	   * @功能描述: TODO 查询任务来源Id
	   * @参数: @param sourceName
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:39:00
	 */
	String querySourceId(String sourceName);
	
	/**
	   * @功能描述: TODO 查询专项任务Id
	   * @参数: @param specialName
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:41:13
	 */
	String querySpecialId(String specialName);
	
	/**
	   * @功能描述: TODO 查询事项Id
	   * @参数: @param itemCode
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:42:49
	 */
	String queryItemId(String itemCode);
	
	/**
	   * @功能描述: TODO 查询会议Id
	   * @param companyId 
	   * @参数: @param relMeetingName
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:44:59
	 */
	String queryMeetingId(@Param(value="relMeetingName")String relMeetingName, 
						  @Param(value="companyId")String companyId);
	
	/**
	   * @功能描述: TODO 查询议题通过状态数据
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午2:44:47
	 */
	List<Map<String, String>> queryPassFlagValue();
	
	/**
	   * @功能描述: TODO 保存议题事项编码
	   * @参数: @param subjectItem
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午3:09:55
	 */
	void saveSubjectItem(SubjectItem subjectItem);
	
	/**
	   * @功能描述: TODO 查询议题Id
	   * @参数: @param relSubjectName
	   * @参数: @param relMeetingId
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午3:15:00
	 */
	String querySubjectId(@Param(value="relSubjectName")String relSubjectName, 
						  @Param(value="relMeetingId")String relMeetingId);
	
	/**
	   * @功能描述: TODO 保存议题关联会议议题信息
	   * @参数: @param subjectRelevance
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午3:16:34
	 */
	void saveSubjectRelevance(SubjectRelevance subjectRelevance);
	
	/**
	   * @功能描述: TODO 保存议题列席人员
	   * @参数: @param attendance
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午3:22:48
	 */
	void saveAttendance(Attendance attendance);
	
	/**
	   * @功能描述: TODO 保存议题审议结果
	   * @参数: @param deliberation
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午3:26:09
	 */
	void saveDeliberation(Deliberation deliberation);

    /**
       * @功能描述:  查询事项清单异常数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/25 15:02
    */
    List<Map<String,Object>> queryPreItemList();

    /**
       * @功能描述:  查询会议异常数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/25 15:13
    */
    List<Map<String,Object>> queryPreMeetingList();

    /**
       * @功能描述:  查询议题异常数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/25 15:17
    */
    List<Map<String,Object>> queryPreSubjectList();

    /**
       * @功能描述:  查询制度异常数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/25 15:23
    */
    List<Map<String,Object>> queryPreRegulationtList();

    /**
       * @功能描述:  判断会议是否存在-根据会议名称and议题ID
       * @创建人 ：林鹏
       * @创建时间：2018/11/26 11:04
    */
    int checkMeetingNameExist(@Param(value = "meetingName") String meetingName,
                              @Param(value = "companyId") String companyId);
}
