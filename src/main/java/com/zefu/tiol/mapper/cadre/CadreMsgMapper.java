package com.zefu.tiol.mapper.cadre;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 干部系统领导班子信息
 * @author：陈东茂
 * @date：2018年11月23日
 */
@Repository
public interface CadreMsgMapper {
	/**
	 * 获取企业领导班子
	 * @return
	 */
	List<Map<String, Object>> getCompanyMember();
	
	/**
	 * 获取企业外部董事(非干部系统，暂不使用)
	 * @return
	 */
	List<Map<String, Object>> getCompanyOutDirector();
}
