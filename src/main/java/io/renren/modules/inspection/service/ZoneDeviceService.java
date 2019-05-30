package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.ZoneDeviceEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
public interface ZoneDeviceService extends IService<ZoneDeviceEntity> {

    List<Map<String,Object>> findDeviceByzoneId(long zoneId);

    Integer insertDeviceBatch(List<ZoneDeviceEntity> zoneDeviceEntityList);

    PageUtils queryPage(Map<String, Object> params);
}

