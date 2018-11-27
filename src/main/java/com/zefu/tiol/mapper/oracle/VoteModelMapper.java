package com.zefu.tiol.mapper.oracle;

import com.yr.gap.dal.plugin.child.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @工程名 : szyd
 * @文件名 : VoteModelMapper
 * @工具包名：com.zefu.tiol.mapper.oracle
 * @功能描述: 表决方式Dao
 * @创建人 ：林鹏
 * @创建时间：2018/11/8 11:21
 * @版本信息：V1.0
 */
@Repository
public interface VoteModelMapper {

    /**
     * @功能描述:  查询表决方式列表
     * @创建人 ：林鹏
     * @创建时间：2018/11/8 11:32
     */
    public List<Map<String, Object>> queryVoteModelList(Map<String, Object> parameter);

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
