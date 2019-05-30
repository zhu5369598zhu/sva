package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionPeriodEntity;

import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
public interface InspectionPeriodService extends IService<InspectionPeriodEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public void save(InspectionPeriodEntity inspectionPeriodEntity);

    public void update(InspectionPeriodEntity inspectionPeriodEntity);

    public void deleteBatch(Long[] periodIds);

    public void insertPeroid(InspectionPeriodEntity inspectionPeriodEntity);
}

