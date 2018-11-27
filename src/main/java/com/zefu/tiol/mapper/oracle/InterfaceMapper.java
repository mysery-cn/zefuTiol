package com.zefu.tiol.mapper.oracle;

import com.zefu.tiol.pojo.Attendee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InterfaceMapper {


	/**
	 *
	 * @功能描述 分页查询服务接口
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryInterfaceByPage(Map<String, Object> param);

	/**
	 *
	 * @功能描述 获取服务接口总记录数
	 * @param param
	 * @return
	 *
	 */
	int getInterfaceTotalCount(Map<String, Object> param);

	/**
	 * 新增服务接口
	 * @param parameter
	 */
    void saveInterface(Map<String, Object> parameter);
}
