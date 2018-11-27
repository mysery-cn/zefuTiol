package com.zefu.tiol.service.impl;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.VoteModelMapper;
import com.zefu.tiol.service.VoteModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @工程名 : szyd
 * @文件名 : VoteModelServiceImpl
 * @工具包名：com.zefu.tiol.service.impl
 * @功能描述:
 * @创建人 ：林鹏
 * @创建时间：2018/11/8 11:42
 * @版本信息：V1.0
 */
@Service("voteModelService")
public class VoteModelServiceImpl implements VoteModelService {

    @Autowired
    private VoteModelMapper voteModelMapper;


    /**
     * @param parameter
     * @param page
     * @功能描述: 查询表决方式列表
     * @创建人 ：林鹏
     * @创建时间：2018/11/8 11:32
     */
    @Override
    public List<Map<String, Object>> queryVoteModelList(Map<String, Object> parameter, Page<Map<String, Object>> page) {
        int maxRow = page.getCurrentPage()*page.getPageSize();;
        int minRow = (page.getCurrentPage()-1)*page.getPageSize();
        parameter.put("minRow", minRow);
        parameter.put("maxRow", maxRow);
        return voteModelMapper.queryVoteModelList(parameter);
    }

    /**
     * @param parameter
     * @功能描述: 查询表决方式总数
     * @创建人 ：林鹏
     * @创建时间：2018/11/8 11:37
     */
    @Override
    public int queryVoteModelTotalCount(Map<String, Object> parameter) {
        return voteModelMapper.queryVoteModelTotalCount(parameter);
    }

    /**
     * @param parameter
     * @功能描述: 查询表决方式详情
     * @创建人 ：林鹏
     * @创建时间：2018/11/8 13:45
     */
    @Override
    public Map<String, Object> queryVoteModelDetail(Map<String, Object> parameter) {
        return voteModelMapper.queryVoteModelDetail(parameter);
    }

    /**
     * @param param
     * @功能描述: 保存表决方式
     * @创建人 ：林鹏
     * @创建时间：2018/11/8 13:45
     */
    @Override
    public void saveVoteModel(Map<String, Object> param) throws Exception {
        param.put("modelId",CommonUtil.getUUID());
        voteModelMapper.saveVoteModel(param);
    }

    /**
     * @param param
     * @功能描述: 修改表决方式
     * @创建人 ：林鹏
     * @创建时间：2018/11/8 13:46
     */
    @Override
    public void updateVoteModel(Map<String, Object> param) throws Exception {
        voteModelMapper.updateVoteModel(param);
    }

    /**
     * @param param
     * @功能描述: 删除表决方式
     * @创建人 ：林鹏
     * @创建时间：2018/11/8 13:46
     */
    @Override
    public void deleteVoteModel(Map<String, Object> param) throws Exception {
        voteModelMapper.deleteVoteModel(param);
    }
}
