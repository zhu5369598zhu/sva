package io.renren.modules.setting.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.setting.entity.BaseTurnEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:38:32
 */
public interface BaseTurnService extends IService<BaseTurnEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

