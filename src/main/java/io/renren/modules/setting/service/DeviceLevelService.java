package io.renren.modules.setting.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.setting.entity.DeviceLevelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 19:32:48
 */
public interface DeviceLevelService extends IService<DeviceLevelEntity> {

    DeviceLevelEntity selectByName(String levelName);
    PageUtils queryPage(Map<String, Object> params);
}

