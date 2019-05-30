package io.renren.modules.inspection.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.DeviceCustomDao;
import io.renren.modules.inspection.entity.DeviceCustomEntity;
import io.renren.modules.inspection.service.DeviceCustomService;


@Service("deviceCustomService")
public class DeviceCustomServiceImpl extends ServiceImpl<DeviceCustomDao, DeviceCustomEntity> implements DeviceCustomService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceCustomEntity> page = this.selectPage(
                new Query<DeviceCustomEntity>(params).getPage(),
                new EntityWrapper<DeviceCustomEntity>()
        );

        return new PageUtils(page);
    }

}
