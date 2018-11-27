package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.RegulationVote;

/**
 * 
 * @功能描述 企业版-制度信息
 * @time 2018年10月26日下午3:10:41
 * @author Super
 *
 */
@Repository
public interface ComRegulationMapper {

	/**
	 * 查询所有制度信息
	 * @功能描述 
	 * @time 2018年10月26日下午3:12:17
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	public List<Map<String, Object>> queryRegulationList(Map<String, Object> param);
	
	public List<Map<String, Object>> queryListByPage(Map<String, Object> parameter);

	public int getTotalCount(@Param("companyId")String companyId);
	
	/**
	   * @功能描述: TODO 平台查询制度信息列表
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午3:32:58
	 */
	List<Map<String, Object>> querRegulationPageList(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 平台查询制度信息列表总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午3:33:33
	 */
	int querRegulationPageListCount(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询制度详情
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午6:43:06
	 */
	public Map<String, Object> queryRegulationDetail(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询佐证材料
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午7:46:40
	 */
	public List<Map<String, Object>> queryAuditFileList(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询制度表决数据
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午7:50:18
	 */
	public List<Map<String, Object>> queryVoteModeList(Map<String, Object> parameter);
	
	/**
	 * 
	   * @功能描述: TODO 保存制度分类
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:45:30
	 */
	public void saveRegulationType(Map<String, Object> param);
	
	/**
	 * 
	   * @功能描述: TODO 删除制度分类
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:45:30
	 */
	public void deleteRegulationType(Map<String, Object> param);
	
	/**
	 * 
	   * @功能描述: TODO 修改制度分类
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:45:30
	 */
	public void updateRegulationType(Map<String, Object> param);
	
	/**
	 * 
	   * @功能描述: TODO 查询制度分类
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:45:30
	 */
	public Map<String, Object> queryRegulationTypeDetail(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询制度分类列表
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 上午11:32:17
	 */
	public List<Map<String, Object>> queryRegulationTypeListByPage(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询制度分类总数
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 上午11:32:32
	 */
	public int queryRegulationTypeTotalCount(Map<String, Object> param);

	public void updateRegulation(Map<String, Object> param);

	/**
	 * 新增制度
	 * @param param
	 * @return
	 */
	public int insertRegulation(Map<String, Object> param);

	/**
	 * 批量插入制度表决
	 * @param list
	 * @return
	 */
	public int batchInsertRegulationVote(List<RegulationVote> list);
	
	/**
	 * 判断制度是否存在
	 * @param param regulationName 制度名称,companyId 企业id
	 * @return
	 * @author 李晓军
	 */
	public int isExistsRegulation(Map<String, Object> param);
	
	/**
	 * 判断事项是否存在
	 * @param param itemCode,companyId
	 * @return
	 * @author 李晓军
	 */
	public int isExistsItem(Map<String, Object> param);
	
	
	/**
	 * 获取制度类型 
	 * @return List:typeName,typeCode
	 * @author 李晓军
	 */
	public List<Map<String, Object>> queryRegulationTypeList();

	/**
	 * 获取企业下事项列表
	 * @param param user.企业ID
	 * @return List:itemCode,itemId,itemName
	 * @author 李晓军
	 */
	public List<Map<String, Object>> queryItemList(Map<String, Object> param);
	
	/**
	 * 获取表决方式
	 * @return List:modeId,modeName
	 * @author 李晓军
	 */
	public List<Map<String, Object>> queryVoteModeListSimple();
	
	/**
	 * 保存Excel导入的制度
	 * @param param
	 * @author 李晓军
	 */
	public void insertImportRegulation(Map<String, Object> param);
	
	/**
	 * 删除制度
	 * @param param
	 * @author 李晓军
	 */
	public void deleteRegulation(Map<String, Object> param);
	
	/**
	 * 删除制度关联数据
	 * @param param
	 * @author 李晓军
	 */
	public void deleteRegulationVote(Map<String, Object> param);

	/**
	 * 根据ID查询制度
	 * @param regulationID
	 * @return
	 * @author 刘效
	 */
	Map<String, Object> queryRegulationById(String regulationID);

	/**
	 * 根据制度id查询事项表决方式
	 * @param regulationId
	 * @return
	 */
	List<Map<String, Object>> queryItemVotesByRegulationId(String regulationId);
	
	/**
	 * 修改制度文件状态
	 * @param param
	 * @author 李晓军
	 */
	public void updateRegulationStatusByids(Map<String, Object> param);
	
	
	/**
	 * 根据Ids获取制度
	 * @param param
	 * @return
	 * @author 李晓军
	 */
	public List<Map<String, Object>> queryRegulationByIds(Map<String, Object> param);


	/**
	 * 根据制度id删除事项表决方式
	 * @param regulationId
	 * @return
	 * @author 刘效
	 */
	int deleteRegulationVote2(String regulationId);


	int updateRegulation2(Map<String, Object> param);
}
