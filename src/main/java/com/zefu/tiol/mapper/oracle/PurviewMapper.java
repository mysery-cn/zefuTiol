package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurviewMapper {

	List<Map<String, Object>> queryPurviewList(Map<String, Object> param) throws Exception ;
	
	List<String> queryPurviewByParam(@Param("purviewType") String purviewType,@Param("purviewObject") String purviewObject) throws Exception ;
	
	Map<String, Object> queryPurviewById(@Param("purviewId") String purviewId) throws Exception ;
	
	List<Map<String, Object>> queryPurviewCatalogListById(@Param("purviewId") String purviewId) throws Exception ;
	
	List<Map<String, Object>> queryPurviewMeetingTypeListById(@Param("purviewId") String purviewId) throws Exception ;
	
	List<Map<String, Object>> queryPurviewCompanyListById(@Param("purviewId") String purviewId) throws Exception ;
	
	List<Map<String, Object>> queryPurviewListByPage(Map<String, Object> param);
	
	int queryPurviewListByPageCount(Map<String, Object> param);
	
	void savePurview(Map<String, Object> param) throws Exception ;
	
	void savePurviewCatalog(Map<String, Object> param) throws Exception ;
	
	void savePurviewMeetingType(Map<String, Object> param) throws Exception ;
	
	void savePurviewCompany(Map<String, Object> param) throws Exception ;
	
	void updatePurview(Map<String, Object> param) throws Exception ;

	/**
	   * @功能描述:  删除事项类型权限记录
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/23 11:52
	*/
	void detePurviewCatalog(Map<String, Object> param) throws Exception ;

	/**
	   * @功能描述:  删除会议类型权限记录
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/23 11:52
	*/
    void detePurviewMeetingType(Map<String, Object> param) throws Exception ;

    /**
       * @功能描述:  删除企业权限记录
       * @创建人 ：林鹏
       * @创建时间：2018/11/23 11:52
    */
    void detePurviewCompany(Map<String, Object> param) throws Exception ;
	
	Boolean deletePurview(Map<String, Object> param) throws Exception ;
	
	void deletePurviewCatalog(Map<String, Object> param) throws Exception ;
	
	void deletePurviewMeetingType(Map<String, Object> param) throws Exception ;
	
	void deletePurviewCompany(Map<String, Object> param) throws Exception ;
	
	List<Map<String, Object>> queryPurviewCatalogListByIds(Map<String, Object> param) throws Exception ;
	
	List<Map<String, Object>> queryPurviewMeetingTypeListByIds(Map<String, Object> param) throws Exception ;
	
	List<Map<String, Object>> queryPurviewCompanyListByIds(Map<String, Object> param) throws Exception ;
	
	/**
	 * 查询会议权限人员、角色或部门
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> listPurviewPerson(Map<String, Object> param);
}
