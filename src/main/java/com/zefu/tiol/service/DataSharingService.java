package com.zefu.tiol.service;

public interface DataSharingService {
	
	/**
	 * 同步重大决策统计数据至数据共享中心
	 * @return
	 */
	boolean insertForStatisticsDecision();
	
	/**
	 * 同步制度分类统计数据至数据共享中心
	 * @return
	 */
	boolean insertForStatisticsRegulation();
	
	/**
	 * 同步事项清单汇总统计数据至数据共享中心
	 * @return
	 */
	boolean insertForStatisticsItem();
	
	/**
	 * 同步会议分类汇总统计数据至数据共享中心
	 * @return
	 */
	boolean insertForStatisticsMeeting();
	
	/**
	 * 同步决策议题汇总统计数据至数据共享中心
	 * @return
	 */
	boolean insertForStatisticsSubject();
	
	/**
	 * 同步决策议题汇总统计数据至数据共享中心
	 * @return
	 */
	boolean insertForStatisticsCpyRegulation();
	
	/**
	 * 同步决策议题汇总统计数据至数据共享中心
	 * @return
	 */
	boolean insertForStatisticsException();
}
