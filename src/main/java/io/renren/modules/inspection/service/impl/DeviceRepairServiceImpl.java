package io.renren.modules.inspection.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.DeviceRepairDao;
import io.renren.modules.inspection.entity.DeviceRepairEntity;
import io.renren.modules.inspection.service.DeviceRepairService;


@Service("deviceRepairService")
public class DeviceRepairServiceImpl extends ServiceImpl<DeviceRepairDao, DeviceRepairEntity> implements DeviceRepairService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceRepairEntity> page = this.selectPage(
                new Query<DeviceRepairEntity>(params).getPage(),
                new EntityWrapper<DeviceRepairEntity>()
        );

        return new PageUtils(page);
    }

}
