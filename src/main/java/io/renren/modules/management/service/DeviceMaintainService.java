package io.renren.modules.management.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.management.entity.DeviceMaintainEntity;

import java.util.Map;

/**
 * 设备日常维护记录表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-10-09 15:39:27
 */
public interface DeviceMaintainService extends IService<DeviceMaintainEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

