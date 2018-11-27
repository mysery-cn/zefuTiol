package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.StatisticsDecision;
import com.zefu.tiol.pojo.StatisticsDecisionVo;

/**
 * @功能描述: TODO 重大决策统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午9:33:01 @版本信息：V1.0
 */
@Repository
public interface StatisticsDecisionMapper {
	
	/**
	 * 查询所有重大决策统计数据
	 * @return
	 */
	List<StatisticsDecisionVo> listStatisticsDecision();
	
	/**
	 * 根据任务来源，汇总“上级重要决定-习总书记的批示和指示”的会议数和议题数
	 * 
	 * @param industryId
	 *            行业Id
	 * @param type
	 *            统计类型(1:任务来源，2:事项目录)
	 * @return List
	 */
	List<StatisticsDecisionVo> queryListByType(@Param("industryId") String industryId,@Param("type") String type);

	/**
	 * 查询公司重大措施统计列表
	 * @param parameter
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> listCompanyDecision(Map<String, Object> parameter);
	
	/**
	 * 公司重大措施统计列表总数
	 * @param parameter
	 * @return
	 */
	int countCompanyDecision(Map<String, Object> parameter);

	/**
	 * 查询重大措施会议分类列表
	 * @param parameter
	 * @return
	 */
    List<Map<String, Object>> listDecisionMeeting(Map<String, Object> parameter);
	
	/**
	 * 查询重大措施会议分类总数
	 * @param parameter
	 * @return
	 */
	int countDecisionMeeting(Map<String, Object> parameter);
	
	/**
	 * 查询重大措施议题列表
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> listDecisionSubject(Map<String, Object> parameter);
	
	/**
	 * 查询重大措施议题总数
	 * @param parameter
	 * @return
	 */
	int countDecisionSubject(Map<String, Object> parameter);
	
	/**
	 * 查询重大措施会议分类列表
	 * @param parameter
	 * @return
	 */
    List<Map<String, Object>> listDecisionCompany(Map<String, Object> parameter);
	
	/**
	 * 查询重大措施会议分类总数
	 * @param parameter
	 * @return
	 */
	int countDecisionCompany(Map<String, Object> parameter);
}
