package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.DeviceQrcodeEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-26 12:20:16
 */
public interface DeviceQrcodeService extends IService<DeviceQrcodeEntity> {
    DeviceQrcodeEntity selectByDeviceId(Integer deviceId);
    PageUtils queryPage(Map<String, Object> params);
}

