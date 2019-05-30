package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.ZoneEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
public interface ZoneService extends IService<ZoneEntity> {

    List<ZoneEntity> all(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params);

    Integer isExist(String zoneName, String zoneCode);
    ZoneEntity selectByName(String zoneName);
}

