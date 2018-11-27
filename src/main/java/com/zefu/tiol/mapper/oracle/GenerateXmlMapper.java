package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
   * @工程名 : szyd
   * @文件名 : GenerateXmlMapper.java
   * @工具包名：com.zefu.tiol.mapper.oracle
   * @功能描述: TODO 生成XML控制层
   * @创建人 ：林鹏
   * @创建时间：2018年11月5日 下午3:42:25
   * @版本信息：V1.0
 */
@Repository
public interface GenerateXmlMapper {
	
	/**
	   * @功能描述: TODO 迭代查询事项目录
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月5日 下午3:59:35
	 */
	List<Map<String, Object>> queryCatalogTree(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 根据企业ID查询事项清单
	   * @参数: @param companyId 企业ID
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:08:24
	 */
	List<Map<String, Object>> getGzwItemList(String companyId);
	
	/**
	   * @功能描述: TODO 获取会议类型
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:21:53
	 */
	List<Map<String, Object>> GenerateMeetingTypeXml();
	
	/**
	   * @功能描述: TODO 获取制度类型
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:32:38
	 */
	List<String> GenerateRegulationTypeXml();
	
	/**
	   * @功能描述: TODO 查询任务来源
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:35:12
	 */
	List<String> GenerateSourceXml();
	
	/**
	   * @功能描述: TODO 查询专项任务
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:37:56
	 */
	List<String> GenerateSpecialXml();
	
	/**
	   * @param string2 
	   * @功能描述: TODO 查询企业经理班子成员
	   * @参数: @param string
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 下午2:41:45
	 */
	List<Map<String, Object>> GenerateCompanyMemberXml(@Param(value="groupType")String groupType, 
													   @Param(value="companyId")String companyId);
	
	/**
	   * @功能描述: TODO 查询表决方式
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 下午2:59:04
	 */
	List<Map<String, Object>> GenerateVoteModeXml();
	
	/**
	   * @功能描述: TODO 查询企业议题异常情况
	   * @参数: @param companyId
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 下午3:09:42
	 */
	List<Map<String, Object>> GenerateSubjectExceptionXml(String companyId);
	
	/**
	   * @功能描述: TODO 查询企业列表
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 下午3:17:17
	 */
	List<Map<String, String>> queryCompanyList();
	
	/**
	   * @功能描述: TODO 获取企业事项清单列表
	   * @参数: @param companyId
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月8日 下午4:07:17
	 */
	List<Map<String, Object>> GenerateCompanyItemXml(String companyId);
	
	/**
	   * @功能描述: TODO 查询事项清单开会顺序
	   * @参数: @param itemId
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月8日 下午4:22:43
	 */
	List<String> GenerateItemMeetingTypeXml(String itemId);
	
	/**
	   * @功能描述: TODO 读取下载路径
	   * @参数: @param data
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月11日 上午10:54:38
	 */
	String getConfigValue(Map<String, Object> data);
	
	/**
	   * @功能描述: TODO 保存事项目录
	   * @参数: @param catalog
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午5:44:08
	 */
	void saveCatalogData(Map<String, Object> catalog);
	
	/**
	   * @功能描述: TODO 保存企业领导班子成员
	   * @参数: @param groupMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午8:00:04
	 */
	void saveCompanyMember(Map<String, Object> groupMap);
	
	/**
	   * @功能描述: TODO 查询事项目录Id
	   * @参数: @param catalogCode
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午8:15:38
	 */
	String queryCatalogId(String catalogCode);
	
	/**
	   * @功能描述: TODO 查询
	   * @参数: @param itemMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午8:18:34
	 */
	void saveItem(Map<String, Object> itemMap);
	
	/**
	   * @功能描述: TODO 查询会议类型Id
	   * @参数: @param meetingTypeName
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午8:57:20
	 */
	String queryMeetingTypeId(String meetingTypeName);
	
	/**
	   * @功能描述: TODO 保存事项清单会议顺序
	   * @参数: @param meetingData
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午9:19:37
	 */
	void saveItemMeeting(Map<String, Object> meetingData);
	
	/**
	   * @功能描述: TODO 保存会议类型
	   * @参数: @param groupMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午9:33:41
	 */
	void saveMeetingType(Map<String, Object> groupMap);
	
	/**
	   * @功能描述: TODO 保存制度类型
	   * @参数: @param groupMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午10:09:27
	 */
	void saveRegulationType(Map<String, Object> groupMap);
	
	/**
	   * @功能描述: TODO 保存任务来源
	   * @参数: @param groupMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午10:19:22
	 */
	void saveSource(Map<String, Object> groupMap);
	
	/**
	   * @功能描述: TODO 保存议题异常
	   * @参数: @param subjectExceptionMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月17日 上午10:39:13
	 */
	void saveSubjectException(Map<String, Object> subjectExceptionMap);
	
	/**
	   * @功能描述: TODO 保存表决方式
	   * @参数: @param voteModeMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月17日 上午10:45:31
	 */
	void saveVoteModel(Map<String, Object> voteModeMap);
	
	/**
	   * @功能描述: TODO 保存专项任务
	   * @参数: @param specialMap
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月17日 下午3:05:18
	 */
	void saveSpecial(Map<String, Object> specialMap);

	/**
	   * @功能描述:  查询事项清单是否存在
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/19 18:53
	*/
    int checkExist(String itemName);

    /**
       * @功能描述:  查询事项目录信息
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 19:35
    */
    String queryCatalogDetail(@Param(value = "catalogName") String catalogName,
                              @Param(value = "catalogLevel")String catalogLevel);

    /**
       * @功能描述:  查询会议类型名称
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 19:52
    */
    int checkMeetingTypeExist(String meetingTypeName);

    /**
       * @功能描述:  查询制度类型名称
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 19:54
    */
    int checkRegulationTypeExist(String regulationTypeName);

    /**
       * @功能描述:  查询任务来源名称
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 19:55
    */
    int checkSourceExist(String sourceName);

    /**
       * @功能描述:  查询专项任务名称
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 19:57
    */
    int checkSprcialExist(String sprcialName);

    /**
       * @功能描述:  查询议题异常信息
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 19:58
    */
    int checkSubjectExceptionExist(String subjectId);

    /**
       * @功能描述:  查询制度表决方式信息
       * @创建人 ：林鹏
       * @创建时间：2018/11/19 20:00
    */
    int checkVoteModelExist(String modeName);

}
