package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.UserPicEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-10-17 16:00:30
 */
public interface UserPicService extends IService<UserPicEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteByGuid(String guid);

    List<Map<String, Object>> selectByUserId(Integer userId);

    UserPicEntity selectByGuid(String uuid);
}

