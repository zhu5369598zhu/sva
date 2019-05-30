package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.BindQueueEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-03-15 11:43:31
 */
public interface BindQueueService extends IService<BindQueueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

