package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.StatisticsRegulation;
import com.zefu.tiol.pojo.StatisticsRegulationVo;

/**
 * @功能描述: TODO 重大决策统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午9:33:01 @版本信息：V1.0
 */
@Repository
public interface StatisticsRegulationMapper {
	
	/**
	 * 根据任务来源，汇总“上级重要决定-习总书记的批示和指示”的会议数和议题数
	 * 
	 * @param industryId
	 *            行业Id
	 * @return List
	 */
	List<StatisticsRegulation> queryListByType(@Param("industryId") String industryId);
	
	/**
	 * 根据公司查询列表
	 * @param companyId
	 * @return
	 */
	List<StatisticsRegulation> queryListByCompanyId(@Param("companyId") String companyId);
	
	/**
	   * @功能描述: TODO 查询当前年份决策议题情况
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午8:10:48
	 */
	List<Map<String, Object>> queryRegulationConstructionDetail(Map<String, Object> parameter);
	
	/**
	 * 查询制度分类统计表所有数据
	 * @return
	 */
	List<StatisticsRegulationVo> listStatisticsRegulation();
	
	/**
	 * 查询企业制度统计表所有数据
	 * @return
	 */
	List<Map<String, Object>> listStatisticsCpyRegulation();

}
