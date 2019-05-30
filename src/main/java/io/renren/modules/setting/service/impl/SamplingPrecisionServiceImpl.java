package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.SamplingPrecisionDao;
import io.renren.modules.setting.entity.SamplingPrecisionEntity;
import io.renren.modules.setting.service.SamplingPrecisionService;


@Service("samplingPrecisionService")
public class SamplingPrecisionServiceImpl extends ServiceImpl<SamplingPrecisionDao, SamplingPrecisionEntity> implements SamplingPrecisionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SamplingPrecisionEntity> page = this.selectPage(
                new Query<SamplingPrecisionEntity>(params).getPage(),
                new EntityWrapper<SamplingPrecisionEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

    @Override
    public SamplingPrecisionEntity selectByName(String name) { return baseMapper.selectByName(name);}
}
