package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.DeviceDocEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-26 12:20:16
 */
public interface DeviceDocService extends IService<DeviceDocEntity> {
    DeviceDocEntity selectByGuid(String guid);
    void deleteByGuid(String guid);
    PageUtils queryPage(Map<String, Object> params);
}

