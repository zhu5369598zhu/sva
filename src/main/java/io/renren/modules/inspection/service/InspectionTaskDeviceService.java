package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionTaskDeviceEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-08-11 16:55:04
 */
public interface InspectionTaskDeviceService extends IService<InspectionTaskDeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

