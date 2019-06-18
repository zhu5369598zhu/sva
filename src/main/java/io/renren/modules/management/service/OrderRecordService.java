package io.renren.modules.management.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.management.entity.OrderRecordEntity;

import java.util.Map;

/**
 * 工单操作记录
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-27 15:40:37
 */
public interface OrderRecordService extends IService<OrderRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

