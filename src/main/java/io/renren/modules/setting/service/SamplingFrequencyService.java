package io.renren.modules.setting.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.setting.entity.SamplingFrequencyEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 11:36:34
 */
public interface SamplingFrequencyService extends IService<SamplingFrequencyEntity> {

    SamplingFrequencyEntity selectByName(String name);
    PageUtils queryPage(Map<String, Object> params);
}

