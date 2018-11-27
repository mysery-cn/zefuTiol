package com.zefu.tiol.mapper.oracle;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @工程名 : szyd
 * @文件名 : DataVindicateMapper
 * @工具包名：com.zefu.tiol.mapper.oracle
 * @功能描述: 数据维护Dao
 * @创建人 ：林鹏
 * @创建时间：2018/11/20 10:42
 * @版本信息：V1.0
 */
@Repository
public interface DataVindicateMapper {

    /**
     * @功能描述: TODO 查询事项清单列表
     * @参数: @param param
     * @参数: @param page
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:49:52
     */
    List<Map<String, Object>> queryItemListByPage(Map<String, Object> param);

    /**
     * @功能描述: TODO 查询事项清单列表总数
     * @参数: @param param
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:50:10
     */
    int queryItemListByPageCount(Map<String, Object> param);

    /**
     * @功能描述: TODO 查询规章制度列表
     * @参数: @param param
     * @参数: @param page
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:49:52
     */
    List<Map<String, Object>> queryRegulationListByPage(Map<String, Object> param);

    /**
     * @功能描述: TODO 查询规章制度列表总数
     * @参数: @param param
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:50:10
     */
    int queryRegulationListByPageCount(Map<String, Object> param);

    /**
     * @功能描述: TODO 查询会议列表
     * @参数: @param param
     * @参数: @param page
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:49:52
     */
    List<Map<String, Object>> queryMeetingListByPage(Map<String, Object> param);

    /**
     * @功能描述: TODO 查询会议列表总数
     * @参数: @param param
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:50:10
     */
    int queryMeetingListByPageCount(Map<String, Object> param);

    /**
     * @功能描述: TODO 查询议题列表
     * @参数: @param param
     * @参数: @param page
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:49:52
     */
    List<Map<String, Object>> querySubjectListByPage(Map<String, Object> param);

    /**
     * @功能描述: TODO 查询议题列表总数
     * @参数: @param param
     * @参数: @return
     * @创建人 ：林鹏
     * @创建时间：2018年11月2日 上午9:50:10
     */
    int querySubjectListByPageCount(Map<String, Object> param);

    /**
     * @功能描述:  修改前置表数据状态
     * @创建人 ：林鹏
     * @创建时间：2018/11/20 14:58
     */
    void updateStatus(Map<String, Object> param);

    /**
     * @功能描述:  查询前置数据XML格式
     * @创建人 ：林鹏
     * @创建时间：2018/11/20 16:33
     */
    String queryXmlDetail(Map<String, Object> param);

}
