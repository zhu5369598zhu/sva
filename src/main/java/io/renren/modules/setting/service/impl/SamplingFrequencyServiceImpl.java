package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.SamplingFrequencyDao;
import io.renren.modules.setting.entity.SamplingFrequencyEntity;
import io.renren.modules.setting.service.SamplingFrequencyService;


@Service("samplingFrequencyService")
public class SamplingFrequencyServiceImpl extends ServiceImpl<SamplingFrequencyDao, SamplingFrequencyEntity> implements SamplingFrequencyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SamplingFrequencyEntity> page = this.selectPage(
                new Query<SamplingFrequencyEntity>(params).getPage(),
                new EntityWrapper<SamplingFrequencyEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

    @Override
    public SamplingFrequencyEntity selectByName(String name) { return baseMapper.selectByName(name);}

}
