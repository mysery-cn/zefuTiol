package com.zefu.tiol.schedule;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.constant.CollectDataConstant;
import com.zefu.tiol.pojo.PreItem;
import com.zefu.tiol.pojo.PreMeeting;
import com.zefu.tiol.pojo.PreRegulation;
import com.zefu.tiol.pojo.PreSubject;
import com.zefu.tiol.service.BizDBService;
import com.zefu.tiol.service.ItemService;
import com.zefu.tiol.service.MeetingService;
import com.zefu.tiol.service.PreDataService;
import com.zefu.tiol.service.RegulationService;
import com.zefu.tiol.service.SubjectExceptionService;
import com.zefu.tiol.service.SubjectService;
import com.zefu.tiol.util.FileUtils;
import com.zefu.tiol.web.bussiness.CollectDataBussiness;

/**
 * 类描述：三重一大定时倒数据 创建人：haoyl 创建时间：2015-10-10 上午9:24:44
 * 
 * @version
 */
@Repository("tiolScheduleMethod")
public class TiolScheduleMethod {
	static boolean preRunning = false;// 通过该标识，防止任务重复运行

	private Logger logger = LoggerFactory.getLogger(TiolScheduleMethod.class);

	@Resource(name = "bizDBService")
	private BizDBService bizDBService;

	@Resource(name = "preDataService")
	private PreDataService preDataService;

	@Resource(name = "itemService")
	private ItemService itemService;

	@Resource(name = "regulationService")
	private RegulationService regulationService;

	@Resource(name = "meetingService")
	private MeetingService meetingService;

	@Resource(name = "subjectService")
	private SubjectService subjectService;

	private CollectDataBussiness bussiness = null;

	/**
	 * 统一http请求的调度任务 方法名称: executeHttp 方法描述: TODO(这里用一句话描述这个方法的作用) @参数：
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 * 			@返回值： String 返回类型
	 * @作者 haoyl
	 * @创建时间 2015-10-10上午9:31:35
	 */
	public String executeInit(String parameter) throws Exception {
		// JSONObject jsonObject = JSONObject.fromObject(parameter);
		if (bussiness == null) {
			bussiness = new CollectDataBussiness(bizDBService, preDataService, itemService, regulationService,
					meetingService, subjectService);
		}
		logger.info("【数据采集】缓存初始化");
		if (!bussiness.initMap()) {
			return "数据初始化失败";
		}
		return "数据初始化完成";
	}
	
	/**
	 * 议题异常统计
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 * 			@返回值： String 返回类型
	 * @作者 haoyl
	 * @创建时间 2015-10-10上午9:31:35
	 */
	public String executeExceptionCheck(String parameter) throws Exception {
		subjectService.checkSubject();
		return "数据校验完成";
	}

	public String executePreCollect(String parameter) throws Exception {
		// 1.通过标识，防止任务重复运行
		if (preRunning) {
			logger.info("【数据采集】任务运行中，请稍后再试...");
			return "任务运行中，请稍后重试";
		}
		if (bussiness == null) {
			bussiness = new CollectDataBussiness(bizDBService, preDataService, itemService, regulationService,
					meetingService, subjectService);
		}
		long startTime = System.currentTimeMillis();
		try {
			preRunning = true;
			// 信息初始化
			bussiness = new CollectDataBussiness(bizDBService, preDataService, itemService, regulationService,
					meetingService, subjectService);
			logger.info("【数据采集】开始执行数据采集任务....");

			// 1.下载文件
			List<File> downFiles = bussiness.downFile();

			// 2.遍历日期文件夹
			for (File dateFile : downFiles) {
				// 3.遍历日期文件夹下的所有zip文件
				File[] zipFiles = dateFile.listFiles();
				for (File zipFile : zipFiles) {
					String fileName = zipFile.getName();
					// 4.1如果是文件夹 或者 不是zip文件，则跳过
					if (zipFile.isDirectory() || !fileName.endsWith(".zip")) {
						continue;
					}

					// 4.2验证文件名是否正确
					fileName = fileName.replaceAll("\\.zip", "");
					if (!CollectDataBussiness.validFileName(fileName)) {
						logger.warn("【数据采集】文件【" + fileName + ".zip】未按要求命名");
						continue;
					}

					// 5.解压文件
					FileUtils.unZip(zipFile.getPath());

					// 6.进入解压后的文件夹读取文件
					String filePath = zipFile.getPath().replaceAll("\\.zip", "");
					File unZipFile = new File(filePath);
					// 6.1如果解压文件不存在，则跳过
					if (!unZipFile.exists()) {
						logger.error("【数据采集】未找到解压后的文件夹，解压失败(" + filePath + ")");
						continue;
					}
					// 6.2如果解压文件夹内存在同名文件夹，则应在最终文件夹内读取文件
					File sameFile = new File(filePath + "\\" + fileName);
					if (sameFile.exists() && sameFile.isDirectory()) {
						unZipFile = sameFile;
					}

					// 7.分业务采集
					String[] infos = fileName.split("_");
					String bussinessCode = infos[1];
					switch (bussinessCode) {
					// 事项
					case CollectDataConstant.BUSINESS_CODE_ITEM:
						if (CollectDataBussiness.collectPreItemData(unZipFile, preDataService)) {// 采集完成后，删除压缩文件和解压文件
							zipFile.delete();
							// FileUtils.delFolder(filePath);
						}
						break;
					// 制度
					case CollectDataConstant.BUSINESS_CODE_REGULATION:
						if (CollectDataBussiness.collectPreRegulationData(unZipFile, preDataService, bizDBService)) {// 采集完成后，删除压缩文件和解压文件
							zipFile.delete();
							// FileUtils.delFolder(filePath);
						}
						break;
					// 会议
					case CollectDataConstant.BUSINESS_CODE_MEETING:
						if (CollectDataBussiness.collectPreMeetingData(unZipFile, preDataService, bizDBService)) {// 采集完成后，删除压缩文件和解压文件
							zipFile.delete();
							// FileUtils.delFolder(filePath);
						}
						break;
					}
				}
			}

			// TODO 进行数据业务级清洗
			// 1.从前置表读取未处理事项清单数据，并对数据进行校验
			List<PreItem> preItemList = preDataService.getUndealItem();
			if (preItemList != null && !preItemList.isEmpty()) {
				bussiness.dealPreItem(preItemList);
			}

			// 2.从前置表读取未处理制度数据，并对数据进行校验
			List<PreRegulation> preRegulationList = preDataService.getUndealRegulation();
			if (preRegulationList != null && !preRegulationList.isEmpty()) {
				bussiness.dealPreRegulation(preRegulationList);
			}

			// 3.从前置表读取未处理会议数据，并对数据进行校验
			List<PreMeeting> preMeetingList = preDataService.getUndealMeeting();
			if (preMeetingList != null && !preMeetingList.isEmpty()) {
				bussiness.dealMeeting(preMeetingList);
			}

			// 4.从前置表读取未处理议题数据，并对数据进行校验
			List<PreSubject> preSubjectList = preDataService.getUndealSubject();
			if (preSubjectList != null && !preSubjectList.isEmpty()) {
				bussiness.dealSubject(preSubjectList);
			}
			
			logger.info("【数据采集】完成，耗时" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【数据采集】异常", e);
		} finally {
			preRunning = false;
		}

		return "前置数据采集完成";
	}
	
	

}
