package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.InspectionStatusDao;
import io.renren.modules.setting.entity.InspectionStatusEntity;
import io.renren.modules.setting.service.InspectionStatusService;


@Service("inspectionStatusService")
public class InspectionStatusServiceImpl extends ServiceImpl<InspectionStatusDao, InspectionStatusEntity> implements InspectionStatusService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionStatusEntity> page = this.selectPage(
                new Query<InspectionStatusEntity>(params).getPage(),
                new EntityWrapper<InspectionStatusEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }


    @Override
    public InspectionStatusEntity selectByName(String name) { return baseMapper.selectByName(name);}
}
