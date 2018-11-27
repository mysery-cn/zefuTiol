package com.zefu.tiol.mapper.oracle;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 集成配置下接口配置和服务配置表操作
 * biz_cfg_interface 接口配置表
 * biz_cfg_service 服务配置表
 *
 * @author 刘效
 */
@Repository
public interface IntegrationConfigMapper {

    /**
     * 保存接口配置
     * @param param
     * @return
     */
    int saveInterfaceConfig(Map<String, Object> param);

    /**
     * 更新接口配置
     * @param param
     * @return
     */
    int updateInterfaceConfig(Map<String, Object> param);

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
    List<Map<String, Object>> getServiceConfigByPage(Map<String, Object> param);

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
    int deleteServiceConfigById(@Param("ids") String[] ids);

    /**
     * 更新配置
     * @param param
     * @return
     */
    int updateServiceConfig(Map<String, Object> param);
}
