package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.UnitDao;
import io.renren.modules.setting.entity.UnitEntity;
import io.renren.modules.setting.service.UnitService;


@Service("unitService")
public class UnitServiceImpl extends ServiceImpl<UnitDao, UnitEntity> implements UnitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UnitEntity> page = this.selectPage(
                new Query<UnitEntity>(params).getPage(),
                new EntityWrapper<UnitEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

    @Override
    public UnitEntity selectByName(String name) { return baseMapper.selectByName(name);}

}
