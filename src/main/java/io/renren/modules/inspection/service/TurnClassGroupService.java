package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;

import java.util.List;

/**
 *
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */

public interface TurnClassGroupService extends IService<TurnClassGroupEntity> {
    /**
     * 保存
     */
    void saveOrUpdate(Long turnId, List<Long> classGroupIdList);

    /**
     * 根据轮次ID，获取班组ID列表
     */
    List<Long> queryClassGroupIdList(Long turnId);

    /**
     * 根据轮次ID数组，批量删除
     */
    Long deleteBatch(Long[] classGroupIds);
}
