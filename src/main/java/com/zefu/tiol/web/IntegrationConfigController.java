package com.zefu.tiol.web;

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.IntegrationConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 集成接口配置
 *
 *
 */
@Controller
@RequestMapping("/integrationCfg")
public class IntegrationConfigController {

    @Resource(name = "integrationConfigService")
    private IntegrationConfigService integrationConfigService;

    @RequestMapping("/getInerfaceCfg")
    @ResponseBody
    public Map getInterfaceConfig(){
        return integrationConfigService.getInterfaceConfig();
    }

    @RequestMapping("/saveOrUpdateInerfaceCfg")
    @ResponseBody
    public int saveOrUpdateInterfaceConfig(HttpServletRequest request){
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //登陆信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);

        param.put("update_user", loginUser.getUserName());
        param.put("update_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return integrationConfigService.saveOrUpdateInterfaceConfig(param);
    }

    @RequestMapping("/querServiceCfgPageList")
    @ResponseBody
    public Page<Map<String, Object>> querPageList(HttpServletRequest request) throws Exception {
        //提供获取接口传参进来的方法
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> list = integrationConfigService.getServiceConfigByPage(param, page);
        //总数
        int totalCount = integrationConfigService.getServiceConfigCount();
        page.setTotalCount(totalCount);
        page.setData(list);
        //封装的返回方法
        return page;
    }

    @RequestMapping("/toEditServiceConfigPage")
    public String toRegualtionRegisterJsp(HttpServletRequest request) {
        return "/config/editServiceConfig.jsp";
    }

    @RequestMapping("/saveOrUpdateServiceConfig")
    @ResponseBody
    public int saveOrUpdateServiceConfig(HttpServletRequest request){
        Map<String, Object> serviceCfg = CommonUtil.getParameterFromRequest(request);

        String update_user = CommonUtil.getLoginUser(request).getUserName();
        String update_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        serviceCfg.put("update_user", update_user);
        serviceCfg.put("update_time", update_time);

        if (StringUtils.isEmpty((String) serviceCfg.get("id"))) {
            String id = UUID.randomUUID().toString().replace("-", "");
            serviceCfg.put("id", id);
            return integrationConfigService.insertServiceConfig(serviceCfg);
        } else {
            return integrationConfigService.updateServiceConfig(serviceCfg);
        }
    }

    @RequestMapping("/deleteServiceConfig")
    @ResponseBody
    public int deleteServiceConfig(@RequestParam String ids){
        return integrationConfigService.deleteServiceConfigById(ids.split(","));
    }

    @RequestMapping("/getServiceConfig")
    @ResponseBody
    public Map<String, Object> getServiConfigById(@RequestParam String id){
        return integrationConfigService.getServiceConfigById(id);
    }

}
