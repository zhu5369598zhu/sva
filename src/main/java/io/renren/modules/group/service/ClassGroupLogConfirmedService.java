package io.renren.modules.group.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.group.entity.ClassGroupLogEntity;
/**
 * 班组日志(待确认)
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-05 16:24:44
 */
public interface ClassGroupLogConfirmedService extends IService<ClassGroupLogEntity>{

	PageUtils queryPage(Map<String, Object> params);
}
