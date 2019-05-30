package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.DeviceStatusDao;
import io.renren.modules.setting.entity.DeviceStatusEntity;
import io.renren.modules.setting.service.DeviceStatusService;


@Service("deviceStatusService")
public class DeviceStatusServiceImpl extends ServiceImpl<DeviceStatusDao, DeviceStatusEntity> implements DeviceStatusService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceStatusEntity> page = this.selectPage(
                new Query<DeviceStatusEntity>(params).getPage(),
                new EntityWrapper<DeviceStatusEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

}
