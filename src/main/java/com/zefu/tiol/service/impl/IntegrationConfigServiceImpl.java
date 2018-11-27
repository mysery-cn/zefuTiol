package com.zefu.tiol.service.impl;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.IntegrationConfigMapper;
import com.zefu.tiol.service.IntegrationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("integrationConfigService")
public class IntegrationConfigServiceImpl implements IntegrationConfigService {

    @Autowired
    private IntegrationConfigMapper integrationConfigMapper;

    /**
     * 保存或更新接口配置
     *
     * @param param
     * @return
     */
    @Override
    public int saveOrUpdateInterfaceConfig(Map<String, Object> param) {
        Map DBInterfaceConfig = integrationConfigMapper.getInterfaceConfig();
        if(DBInterfaceConfig == null || DBInterfaceConfig.size() == 0){
            String id = UUID.randomUUID().toString().replace("-", "");
            param.put("id", id);
            return integrationConfigMapper.saveInterfaceConfig(param);
        }else{
            return integrationConfigMapper.updateInterfaceConfig(param);
        }
    }

    /**
     * 查找接口配置
     *
     * @return
     */
    @Override
    public Map<String, Object> getInterfaceConfig() {
        return integrationConfigMapper.getInterfaceConfig();
    }

    /**
     * 根据id查找服务配置
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getServiceConfigById(String id) {
        return integrationConfigMapper.getServiceConfigById(id);
    }

    /**
     * 统计服务配置数量
     *
     * @return
     */
    @Override
    public int getServiceConfigCount() {
        return integrationConfigMapper.getServiceConfigCount();
    }

    /**
     * 分页查找服务配置
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getServiceConfigByPage(Map<String, Object> param, Page page) {
        int maxRow = page.getCurrentPage() * page.getPageSize();
        int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
        param.put("minRow", minRow);
        param.put("maxRow", maxRow);
        return integrationConfigMapper.getServiceConfigByPage(param);
    }

    /**
     * 新增配置
     *
     * @param param
     * @return
     */
    @Override
    public int insertServiceConfig(Map<String, Object> param) {
        return integrationConfigMapper.insertServiceConfig(param);
    }

    /**
     * 删除配置
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteServiceConfigById(String[] ids) {
        return integrationConfigMapper.deleteServiceConfigById(ids);
    }

    /**
     * 更新配置
     *
     * @param param
     * @return
     */
    @Override
    public int updateServiceConfig(Map<String, Object> param) {
        return integrationConfigMapper.updateServiceConfig(param);
    }
}
