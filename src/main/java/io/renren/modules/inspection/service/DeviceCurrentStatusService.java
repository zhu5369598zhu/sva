package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.DeviceCurrentStatusEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-12-03 10:55:02
 */
public interface DeviceCurrentStatusService extends IService<DeviceCurrentStatusEntity> {


    PageUtils queryPage(Map<String, Object> params);

    String updateDeviceStatus(DeviceCurrentStatusEntity deviceCurrentStatusEntity);

    String text(DeviceCurrentStatusEntity deviceCurrentStatusEntity);
}

