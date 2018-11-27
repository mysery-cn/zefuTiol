package com.zefu.tiol.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.zefu.tiol.service.CompanyMemberService;
import com.zefu.tiol.service.DataSharingService;

@Repository("dataSharingScheduleMethod")
public class DataSharingScheduleMethod {

	
	
	@Resource(name = "dataSharingService")
	private DataSharingService dataSharingService;
	
	@Resource(name = "companyMemberService")
	private CompanyMemberService companyMemberService;
	
	/**
	 * 同步大屏展示数据
	 * @param parameter
	 */
	public void insertStatisticsData(String parameter) {
		//重大决策统计
		dataSharingService.insertForStatisticsDecision();
		//制度分类汇总
		dataSharingService.insertForStatisticsRegulation();
		//事项清单汇总
		dataSharingService.insertForStatisticsItem();
		//会议分类汇总
		dataSharingService.insertForStatisticsMeeting();
		//议题汇总
		dataSharingService.insertForStatisticsSubject();
		//企业制度汇总
		dataSharingService.insertForStatisticsCpyRegulation();
		//异常统计
		dataSharingService.insertForStatisticsException();
	}
	
	/**
	 * 同步企业领导班子成员
	 * @param parameter
	 * @throws Exception
	 */
	public void saveCompanyMember(String parameter) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//获取干部系统数据
		List<Map<String, Object>> list = companyMemberService.getCompanyMember();
		//获取干部系统外部董事数据
		List<Map<String, Object>> outDirectorlist = companyMemberService.getCompanyOutDirector();
		if(list.size()>0) {
			companyMemberService.deleteCompanyMember(param);
			param.put("tempList", list);
			companyMemberService.saveCompanyMember(list);
			companyMemberService.saveCompanyMember(outDirectorlist);
		}
	}
}
