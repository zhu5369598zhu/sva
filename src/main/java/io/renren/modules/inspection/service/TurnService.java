package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.TurnEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
public interface TurnService extends IService<TurnEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public void save(TurnEntity turnEntity);

    public void update(TurnEntity turnEntity);

    public void deleteBatch(Long[] turnIds);

    public TurnEntity selectByGuid(String guid);
}

