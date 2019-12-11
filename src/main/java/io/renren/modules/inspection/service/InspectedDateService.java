package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectedDateEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-12-04 11:35:22
 */
public interface InspectedDateService extends IService<InspectedDateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

