package io.renren.modules.setting.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.setting.entity.OrderExceptionEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-07-02 15:22:39
 */
public interface OrderExceptionService extends IService<OrderExceptionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

