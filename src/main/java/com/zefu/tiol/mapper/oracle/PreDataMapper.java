package com.zefu.tiol.mapper.oracle;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.PreItem;
import com.zefu.tiol.pojo.PreMeeting;
import com.zefu.tiol.pojo.PreRegulation;
import com.zefu.tiol.pojo.PreSubject;

@Repository
public interface PreDataMapper {

	/**
	 * 保存上报事项清单到前置表
	 * 
	 * @param preItem
	 * @author linch
	 * @time 2018-11-08 11:05
	 */
	public void savePreItem(PreItem preItem);

	/**
	 * 保存上报制度到前置表
	 * 
	 * @param preRegulation
	 * @author linch
	 * @time 2018-11-08 11:06
	 */
	public void savePreRegulation(PreRegulation preRegulation);

	/**
	 * 保存上报会议到前置表
	 * 
	 * @param preMeeting
	 * @author linch
	 * @time 2018-11-08 11:06
	 */
	public void savePreMeeting(PreMeeting preMeeting);

	/**
	 * 保存上报议题到前置表
	 * 
	 * @param preSubjectList
	 * @author linch
	 * @time 2018-11-08 11:06
	 */
	public void batchInsertPreSubject(List<PreSubject> preSubjectList);

	/**
	 * 获取未处理事项
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public List<PreItem> getUndealItem();

	/**
	 * 获取未处理制度
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public List<PreRegulation> getUndealRegulation();

	/**
	 * 获取未处理会议
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public List<PreMeeting> getUndealMeeting();

	/**
	 * 获取未处理议题
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public List<PreSubject> getUndealSubject();

	/**
	 * 更新事项前置表状态
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public void updatePreItem(PreItem preItem);

	/**
	 * 更新制度前置表状态
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public void updatePreRegulation(PreRegulation preRegulation);

	/**
	 * 更新会议前置表状态
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public void updatePreMeeting(PreMeeting preMeeting);

	/**
	 * 更新议题前置表状态
	 * 
	 * @author linch
	 * @time 2018-11-08 17:36
	 */
	public void updatePreSubject(PreSubject preSubject);
	
	/**
	   * @功能描述: TODO 判断事项清单是否已存在
	   * @参数: @param itemCode
	   * @参数: @param catalogId
	   * @参数: @param companyId
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月10日 上午11:19:22
	 */
	int checkItemIsExist(@Param(value="itemCode")String itemCode, 
						 @Param(value="catalogId")String catalogId, 
						 @Param(value="companyId")String companyId);
	
	
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
	int checkRegulationExist(@Param(value="regulationId")String regulationId, 
							 @Param(value="typeId")String typeId, 
							 @Param(value="companyId")String companyId, 
							 @Param(value="regulationName")String regulationName);
}
