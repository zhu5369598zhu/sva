package io.renren.modules.setting.service.impl;

import io.renren.modules.setting.entity.SamplingFrequencyEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.InspectionTypeDao;
import io.renren.modules.setting.entity.InspectionTypeEntity;
import io.renren.modules.setting.service.InspectionTypeService;


@Service("inspectionTypeService")
public class InspectionTypeServiceImpl extends ServiceImpl<InspectionTypeDao, InspectionTypeEntity> implements InspectionTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionTypeEntity> page = this.selectPage(
                new Query<InspectionTypeEntity>(params).getPage(),
                new EntityWrapper<InspectionTypeEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

    @Override
    public InspectionTypeEntity selectByName(String name) { return baseMapper.selectByName(name);}
}
