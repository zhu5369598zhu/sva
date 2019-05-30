package io.renren.modules.inspection.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.DeviceQrcodeDao;
import io.renren.modules.inspection.entity.DeviceQrcodeEntity;
import io.renren.modules.inspection.service.DeviceQrcodeService;


@Service("deviceQrcodeService")
public class DeviceQrcodeServiceImpl extends ServiceImpl<DeviceQrcodeDao, DeviceQrcodeEntity> implements DeviceQrcodeService {
    @Override
    public DeviceQrcodeEntity selectByDeviceId(Integer deviceId){
        return baseMapper.selectByDeviceId(deviceId);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceQrcodeEntity> page = this.selectPage(
                new Query<DeviceQrcodeEntity>(params).getPage(),
                new EntityWrapper<DeviceQrcodeEntity>()
        );

        return new PageUtils(page);
    }

}
