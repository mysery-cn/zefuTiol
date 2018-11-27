package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.StatisticsCompany;

@Repository
public interface StatisticsCompanyMapper {

	public List<StatisticsCompany> queryCompanyList(@Param("industryId") String industryId);

	public List<StatisticsCompany> queryListByPage(Map<String, Object> parameter);

	public int getTotalCount(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询企业信息
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午4:34:08
	 */
	public Map<String, Object> queryCompanyDetail(Map<String, Object> parameter);
	
	
}
