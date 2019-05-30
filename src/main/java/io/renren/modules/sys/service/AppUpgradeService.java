package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.AppUpgradeEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-18 12:45:52
 */
public interface AppUpgradeService extends IService<AppUpgradeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

