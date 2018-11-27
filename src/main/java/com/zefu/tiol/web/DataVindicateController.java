package com.zefu.tiol.web;

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.DataVindicateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @工程名 : szyd
 * @文件名 : DataVindicateController
 * @工具包名：com.zefu.tiol.web
 * @功能描述: 数据维护管理
 * @创建人 ：林鹏
 * @创建时间：2018/11/20 10:37
 * @版本信息：V1.0
 */
@Controller
@RequestMapping("/dataVindicate")
public class DataVindicateController {

    @Resource(name = "dataVindicateService")
    private DataVindicateService DataVindicateService;

    /**
       * @功能描述:  查询事项清单采集数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/20 11:53
    */
    @RequestMapping("/queryItemListByPage")
    @ResponseBody
    public Object queryItemListByPage(HttpServletRequest request) {
        //基础数据
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //分页数据
        Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = DataVindicateService.queryItemListByPage(param,page);
        //统计总数
        int totalCount = DataVindicateService.queryItemListByPageCount(param);
        page.setData(list);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
       * @功能描述:  查询规章制度采集数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/20 14:02
    */
    @RequestMapping("/queryRegulationListByPage")
    @ResponseBody
    public Object queryRegulationListByPage(HttpServletRequest request) {
        //基础数据
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //分页数据
        Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = DataVindicateService.queryRegulationListByPage(param,page);
        //统计总数
        int totalCount = DataVindicateService.queryRegulationListByPageCount(param);
        page.setData(list);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
       * @功能描述:  查询会议采集数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/20 14:20
    */
    @RequestMapping("/queryMeetingListByPage")
    @ResponseBody
    public Object queryMeetingListByPage(HttpServletRequest request) {
        //基础数据
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //分页数据
        Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = DataVindicateService.queryMeetingListByPage(param,page);
        //统计总数
        int totalCount = DataVindicateService.queryMeetingListByPageCount(param);
        page.setData(list);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
       * @功能描述:  查询议题采集列表数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/20 14:23
    */
    @RequestMapping("/querySubjectListByPage")
    @ResponseBody
    public Object querySubjectListByPage(HttpServletRequest request) {
        //基础数据
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //分页数据
        Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = DataVindicateService.querySubjectListByPage(param,page);
        //统计总数
        int totalCount = DataVindicateService.querySubjectListByPageCount(param);
        page.setData(list);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
       * @功能描述:  批量修改数据状态
       * @创建人 ：林鹏
       * @创建时间：2018/11/20 14:55
    */
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Object updateStatus(HttpServletRequest request) {
        //基础数据
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Result result = new Result();
        try {
            param.put("ids",(param.get("ids")+"").split(","));
            DataVindicateService.updateStatus(param);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorCode(-1);
            result.setErrorInfo("数据修改状态失败");
        }
        return result;
    }

    /**
       * @功能描述:  查询XMl信息
       * @创建人 ：林鹏
       * @创建时间：2018/11/20 16:32
    */
    @RequestMapping("/queryXmlDetail")
    @ResponseBody
    public Object queryXmlDetail(HttpServletRequest request) {
        //基础数据
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        String xmlDetail = DataVindicateService.queryXmlDetail(param);
        return new Result(xmlDetail);
    }

}
