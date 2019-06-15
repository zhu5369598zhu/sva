package io.renren.modules.group.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.group.entity.ClassGroupLogEntity;

import java.util.Map;

/**
 * 班组日志表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-05 16:24:44
 */
public interface ClassGroupLogService extends IService<ClassGroupLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

