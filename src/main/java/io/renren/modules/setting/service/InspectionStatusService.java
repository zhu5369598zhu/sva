package io.renren.modules.setting.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.setting.entity.InspectionStatusEntity;
import io.renren.modules.setting.entity.InspectionTypeEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 11:36:34
 */
public interface InspectionStatusService extends IService<InspectionStatusEntity> {

    InspectionStatusEntity selectByName(String name);
    PageUtils queryPage(Map<String, Object> params);
}

