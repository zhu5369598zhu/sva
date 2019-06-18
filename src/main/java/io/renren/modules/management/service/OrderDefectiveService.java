package io.renren.modules.management.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.management.entity.OrderDefectiveEntity;

import java.util.Map;

/**
 * 缺陷工单表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-31 15:16:09
 */
public interface OrderDefectiveService extends IService<OrderDefectiveEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

