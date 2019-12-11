package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.InspectedDateDao;
import io.renren.modules.inspection.entity.InspectedDateEntity;
import io.renren.modules.inspection.service.InspectedDateService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("inspectedDateService")
public class InspectedDateServiceImpl extends ServiceImpl<InspectedDateDao, InspectedDateEntity> implements InspectedDateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectedDateEntity> page = this.selectPage(
                new Query<InspectedDateEntity>(params).getPage(),
                new EntityWrapper<InspectedDateEntity>()
        );

        return new PageUtils(page);
    }

}
