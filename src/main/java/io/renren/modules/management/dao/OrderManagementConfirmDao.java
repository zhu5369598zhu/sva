package io.renren.modules.management.dao;

import io.renren.modules.management.entity.OrderManagementEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 工单管理表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
@Mapper
public interface OrderManagementConfirmDao extends BaseMapper<OrderManagementEntity> {

	List<OrderManagementEntity> selectOrderManagement(HashMap<String, Object> entityMap);
	
}
