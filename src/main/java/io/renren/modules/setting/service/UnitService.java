package io.renren.modules.setting.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.setting.entity.UnitEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:38:32
 */
public interface UnitService extends IService<UnitEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UnitEntity selectByName(String name);
}

