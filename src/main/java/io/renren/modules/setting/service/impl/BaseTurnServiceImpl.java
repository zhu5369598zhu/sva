package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.BaseTurnDao;
import io.renren.modules.setting.entity.BaseTurnEntity;
import io.renren.modules.setting.service.BaseTurnService;


@Service("baseTurnService")
public class BaseTurnServiceImpl extends ServiceImpl<BaseTurnDao, BaseTurnEntity> implements BaseTurnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BaseTurnEntity> page = this.selectPage(
                new Query<BaseTurnEntity>(params).getPage(),
                new EntityWrapper<BaseTurnEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }

}
