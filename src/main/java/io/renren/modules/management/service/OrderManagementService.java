package io.renren.modules.management.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.management.entity.OrderManagementEntity;

import java.util.List;
import java.util.Map;

/**
 * 工单管理表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
public interface OrderManagementService extends IService<OrderManagementEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<OrderManagementEntity> all(Map<String, Object> params);
}

