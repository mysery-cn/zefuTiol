package com.zefu.tiol.service;

import com.yr.gap.dal.plugin.child.Page;

import java.util.List;
import java.util.Map;

/**
 * @工程名 : szyd
 * @文件名 : VoteModelService
 * @工具包名：com.zefu.tiol.service
 * @功能描述: 表决方式Service
 * @创建人 ：林鹏
 * @创建时间：2018/11/8 11:19
 * @版本信息：V1.0
 */
public interface VoteModelService {
    
    /**
       * @功能描述:  查询表决方式列表
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 11:32
    */
    public List<Map<String, Object>> queryVoteModelList(Map<String, Object> parameter, Page<Map<String, Object>> page);

    /**
       * @功能描述:  查询表决方式总数
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 11:37
    */
    public int queryVoteModelTotalCount(Map<String, Object> parameter);

    /**
       * @功能描述:  查询表决方式详情
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:45
    */
    Map<String, Object> queryVoteModelDetail(Map<String, Object> parameter);

    /**
       * @功能描述:  保存表决方式
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:45
    */
    void saveVoteModel(Map<String, Object> param)  throws Exception;
    
    /**
       * @功能描述:  修改表决方式
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:46
    */
    void updateVoteModel(Map<String, Object> param)  throws Exception;
    
    /**
       * @功能描述:  删除表决方式
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:46
    */
    void deleteVoteModel(Map<String, Object> param)  throws Exception;
}
