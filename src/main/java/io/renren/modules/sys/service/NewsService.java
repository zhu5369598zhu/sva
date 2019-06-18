package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.NewsEntity;

import java.util.Map;

/**
 * 消息表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-10 08:42:48
 */
public interface NewsService extends IService<NewsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

