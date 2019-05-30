package io.renren.modules.inspection.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.BindQueueDao;
import io.renren.modules.inspection.entity.BindQueueEntity;
import io.renren.modules.inspection.service.BindQueueService;


@Service("bindQueueService")
public class BindQueueServiceImpl extends ServiceImpl<BindQueueDao, BindQueueEntity> implements BindQueueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BindQueueEntity> page = this.selectPage(
                new Query<BindQueueEntity>(params).getPage(),
                new EntityWrapper<BindQueueEntity>()
        );

        return new PageUtils(page);
    }

}
