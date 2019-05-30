package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.DevicePicEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-26 12:20:16
 */
public interface DevicePicService extends IService<DevicePicEntity> {
    DevicePicEntity selectByGuid(String guid);
    List<Map<String, Object>> selectByDeviceId (Integer deviceId);
    void deleteByGuid(String guid);
    PageUtils queryPage(Map<String, Object> params);
}

