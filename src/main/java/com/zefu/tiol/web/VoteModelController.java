package com.zefu.tiol.web;

import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.VoteModelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @工程名 : szyd
 * @文件名 : VoteModelController
 * @工具包名：com.zefu.tiol.web
 * @功能描述: 表决方式控制层
 * @创建人 ：林鹏
 * @创建时间：2018/11/8 11:18
 * @版本信息：V1.0
 */
@RequestMapping({ "/vote/model" })
@Controller
public class VoteModelController {

    @Resource(name = "voteModelService")
    private VoteModelService voteModelService;

    /**
       * @功能描述:  查询表决方式列表By分页
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 11:29
    */
    @RequestMapping("/queryVoteModelList")
    @ResponseBody
    public Page<Map<String, Object>> queryVoteModelList(HttpServletRequest request) throws Exception {
        //提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = voteModelService.queryVoteModelList(parameter,page);
        int totalCount = voteModelService.queryVoteModelTotalCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
        //封装的返回方法
        return page;
    }

    /**
       * @功能描述:  查询表决方式详情
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:44
    */
    @RequestMapping("/queryVoteModelDetail")
    @ResponseBody
    public Object queryVoteModelDetail(HttpServletRequest request) throws Exception{
        //提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = voteModelService.queryVoteModelDetail(parameter);
        return new Result(detail);
    }

    /**
       * @功能描述:  保存表决方式
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:47
    */
    @RequestMapping("/saveVoteModel")
    @ResponseBody
    public Object saveVoteModel(HttpServletRequest request) throws Exception{
        //提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        voteModelService.saveVoteModel(parameter);
        return new Result();
    }

    /**
       * @功能描述:  修改表决方式
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:48
    */
    @RequestMapping("/updateVoteModel")
    @ResponseBody
    public Object updateVoteModel(HttpServletRequest request) throws Exception{
        //提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        voteModelService.updateVoteModel(parameter);
        return new Result();
    }

    /**
       * @功能描述:  删除表决方式
       * @创建人 ：林鹏
       * @创建时间：2018/11/8 13:48
    */
    @RequestMapping("/deleteVoteModel")
    @ResponseBody
    public Object deleteVoteModel(HttpServletRequest request) throws Exception{
        //提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        parameter.put("ids",(parameter.get("ids")+"").split(","));
        voteModelService.deleteVoteModel(parameter);
        return new Result();
    }
}
