package io.renren.modules.inspection.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionTaskDeviceDao;
import io.renren.modules.inspection.entity.InspectionTaskDeviceEntity;
import io.renren.modules.inspection.service.InspectionTaskDeviceService;


@Service("inspectionTaskDeviceService")
public class InspectionTaskDeviceServiceImpl extends ServiceImpl<InspectionTaskDeviceDao, InspectionTaskDeviceEntity> implements InspectionTaskDeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionTaskDeviceEntity> page = this.selectPage(
                new Query<InspectionTaskDeviceEntity>(params).getPage(),
                new EntityWrapper<InspectionTaskDeviceEntity>()
        );

        return new PageUtils(page);
    }

}
