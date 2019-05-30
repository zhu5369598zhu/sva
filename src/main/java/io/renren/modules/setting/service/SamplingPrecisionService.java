package io.renren.modules.setting.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.setting.entity.SamplingPrecisionEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 11:36:34
 */
public interface SamplingPrecisionService extends IService<SamplingPrecisionEntity> {

    SamplingPrecisionEntity selectByName(String name);
    PageUtils queryPage(Map<String, Object> params);
}

