package com.zefu.tiol.service;

import java.util.List;

import com.zefu.tiol.pojo.PreItem;
import com.zefu.tiol.pojo.PreMeeting;
import com.zefu.tiol.pojo.PreRegulation;
import com.zefu.tiol.pojo.PreSubject;

/**
 * 四类业务数据前置表综合操作接口
 * @author linch
 * @date	2018-11-08 
 */
public interface PreDataService {
	
	/**
	   * 保存上报事项清单到前置表
	   * @param preItem
	   * @author linch
	   * @time 2018-11-08 09:48
	 */
	void savePreItem(PreItem preItem);
	
	/**
	   * 保存上报制度到前置表
	   * @param preRegulation
	   * @author linch
	   * @time 2018-11-08 09:53
	 */
	void savePreRegulation(PreRegulation preRegulation);
	
	/**
	   * 保存上报会议到前置表
	   * @param preMeeting
	   * @author linch
	   * @time 2018-11-08 09:53
	 */
	void savePreMeeting(PreMeeting preMeeting);
	
	/**
	   * 保存上报议题到前置表
	   * @param preSubjectList
	   * @author linch
	   * @time 2018-11-08 09:54
	 */
	void batchInsertPreSubject(List<PreSubject> preSubjectList);

	List<PreItem> getUndealItem();

	List<PreRegulation> getUndealRegulation();

	List<PreMeeting> getUndealMeeting();

	List<PreSubject> getUndealSubject();
	
	void updatePreItem(PreItem preItem);
	void updatePreRegulation(PreRegulation preRegulation);
	void updatePreMeeting(PreMeeting preMeeting);
	void updatePreSubject(PreSubject preSubject);
	
	/**
	   * @功能描述: TODO 判断事项清单是否已存在
	   * @参数: @param itemCode
	   * @参数: @param catalogId
	   * @参数: @param companyId
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月10日 上午11:19:22
	 */
	int checkItemIsExist(String itemCode, String catalogId, String companyId);
	
	/**
	   * @功能描述: TODO 判断制度是否已存在
	   * @参数: @param regulationId 来源ID
	   * @参数: @param typeId       事项ID
	   * @参数: @param companyId    企业ID
	   * @参数: @param regulationName 制度名称
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月11日 上午10:19:46
	 */
	int checkRegulationExist(String regulationId, String typeId, String companyId, String regulationName);
	
}
