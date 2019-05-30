package io.renren.modules.setting.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.DeviceLevelDao;
import io.renren.modules.setting.entity.DeviceLevelEntity;
import io.renren.modules.setting.service.DeviceLevelService;


@Service("deviceLevelService")
public class DeviceLevelServiceImpl extends ServiceImpl<DeviceLevelDao, DeviceLevelEntity> implements DeviceLevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceLevelEntity> page = this.selectPage(
                new Query<DeviceLevelEntity>(params).getPage(),
                new EntityWrapper<DeviceLevelEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

    @Override
    public DeviceLevelEntity selectByName(String levelName){
        return baseMapper.selectByName(levelName);
    }
}
