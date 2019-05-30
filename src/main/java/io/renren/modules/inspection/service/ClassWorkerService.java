package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.ClassWorkerEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
public interface ClassWorkerService extends IService<ClassWorkerEntity> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 保存
     */
    void saveOrUpdate(Long classGroupId, List<Long> userIdList);

    /**
     * 根据班组D，获取用户ID列表
     */
    List<Long> queryUserIdList(Long classGroupId);

    /**
     * 根据用户ID数组，批量删除
     */
    Long deleteBatch(Long[] userIds);
}

