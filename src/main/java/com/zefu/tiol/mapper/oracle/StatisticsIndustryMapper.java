package com.zefu.tiol.mapper.oracle;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.StatisticsIndustry;

@Repository
public interface StatisticsIndustryMapper {

	List<StatisticsIndustry> queryIndustryList();
}
