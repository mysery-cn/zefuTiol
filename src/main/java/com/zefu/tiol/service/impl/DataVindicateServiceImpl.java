package com.zefu.tiol.service.impl;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.DataVindicateMapper;
import com.zefu.tiol.service.DataVindicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @工程名 : szyd
 * @文件名 : DataVindicateServiceImpl
 * @工具包名：com.zefu.tiol.service.impl
 * @功能描述: 数据维护业务逻辑层
 * @创建人 ：林鹏
 * @创建时间：2018/11/20 10:38
 * @版本信息：V1.0
 */
@Service("dataVindicateService")
public class DataVindicateServiceImpl implements DataVindicateService {

    @Autowired
    private DataVindicateMapper DataVindicateMapper;


    @Override
    public List<Map<String, Object>> queryItemListByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
        // 封装分页
        int maxRow = page.getCurrentPage() * page.getPageSize();
        int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
        param.put("minRow", minRow);
        param.put("maxRow", maxRow);
        List<Map<String, Object>> itemList = DataVindicateMapper.queryItemListByPage(param);
        return itemList;
    }

    @Override
    public int queryItemListByPageCount(Map<String, Object> param) {
        return DataVindicateMapper.queryItemListByPageCount(param);
    }

    @Override
    public List<Map<String, Object>> queryRegulationListByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
        // 封装分页
        int maxRow = page.getCurrentPage() * page.getPageSize();
        int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
        param.put("minRow", minRow);
        param.put("maxRow", maxRow);
        return DataVindicateMapper.queryRegulationListByPage(param);
    }

    @Override
    public int queryRegulationListByPageCount(Map<String, Object> param) {
        return DataVindicateMapper.queryRegulationListByPageCount(param);
    }

    @Override
    public List<Map<String, Object>> queryMeetingListByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
        // 封装分页
        int maxRow = page.getCurrentPage() * page.getPageSize();
        int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
        param.put("minRow", minRow);
        param.put("maxRow", maxRow);
        return DataVindicateMapper.queryMeetingListByPage(param);
    }

    @Override
    public int queryMeetingListByPageCount(Map<String, Object> param) {
        return DataVindicateMapper.queryMeetingListByPageCount(param);
    }

    @Override
    public List<Map<String, Object>> querySubjectListByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
        // 封装分页
        int maxRow = page.getCurrentPage() * page.getPageSize();
        int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
        param.put("minRow", minRow);
        param.put("maxRow", maxRow);
        return DataVindicateMapper.querySubjectListByPage(param);
    }

    @Override
    public int querySubjectListByPageCount(Map<String, Object> param) {
        return DataVindicateMapper.querySubjectListByPageCount(param);
    }

    @Override
    public void updateStatus(Map<String, Object> param) {
        DataVindicateMapper.updateStatus(param);
    }

    @Override
    public String queryXmlDetail(Map<String, Object> param) {
        return DataVindicateMapper.queryXmlDetail(param);
    }
}
