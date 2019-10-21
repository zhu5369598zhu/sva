package io.renren.modules.management.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.management.dao.DeviceMaintainDao;
import io.renren.modules.management.entity.DeviceMaintainEntity;
import io.renren.modules.management.service.DeviceMaintainService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("deviceMaintainService")
public class DeviceMaintainServiceImpl extends ServiceImpl<DeviceMaintainDao, DeviceMaintainEntity> implements DeviceMaintainService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String deviceId = params.get("deviceId").toString();
        Page<DeviceMaintainEntity> page = this.selectPage(
                new Query<DeviceMaintainEntity>(params).getPage(),
                new EntityWrapper<DeviceMaintainEntity>()
                .eq("device_id", deviceId)
        );

        return new PageUtils(page);
    }

}
