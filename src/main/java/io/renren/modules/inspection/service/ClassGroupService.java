package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.ClassGroupEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
public interface ClassGroupService extends IService<ClassGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public void save(ClassGroupEntity classGroupEntity);

    public void update(ClassGroupEntity classGroupEntity);

    public void deleteBatch(Long[] classGroupIds);

    public void insertClassGroup(ClassGroupEntity classGroupEntity);
}

