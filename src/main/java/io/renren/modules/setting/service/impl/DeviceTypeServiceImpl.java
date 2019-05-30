package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.DeviceTypeDao;
import io.renren.modules.setting.entity.DeviceTypeEntity;
import io.renren.modules.setting.service.DeviceTypeService;


@Service("deviceTypeService")
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeDao, DeviceTypeEntity> implements DeviceTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceTypeEntity> page = this.selectPage(
                new Query<DeviceTypeEntity>(params).getPage(),
                new EntityWrapper<DeviceTypeEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

}
