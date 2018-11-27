package com.zefu.tiol.service;

import com.yr.gap.dal.plugin.child.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 集成配置业务
 */
public interface IntegrationConfigService {

    /**
     * 保存或更新接口配置
     * @param param
     * @return
     */
    int saveOrUpdateInterfaceConfig(Map<String, Object> param);

    /**
     * 查找接口配置
     * @return
     */
    Map<String, Object> getInterfaceConfig();

    /**
     * 根据id查找服务配置
     * @param id
     * @return
     */
    Map<String, Object> getServiceConfigById(String id);

    /**
     * 分页查找服务配置
     * @param param
     * @return
     */
    List<Map<String, Object>> getServiceConfigByPage(Map<String, Object> param, Page page);

    /**
     * 统计服务配置数量
     * @return
     */
    int getServiceConfigCount();

    /**
     * 新增配置
     * @param param
     * @return
     */
    int insertServiceConfig(Map<String, Object> param);

    /**
     * 删除配置
     * @param ids
     * @return
     */
    int deleteServiceConfigById(String[] ids);

    /**
     * 更新配置
     * @param param
     * @return
     */
    int updateServiceConfig(Map<String, Object> param);

}
