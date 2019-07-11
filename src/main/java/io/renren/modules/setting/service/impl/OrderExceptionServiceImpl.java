package io.renren.modules.setting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.OrderExceptionDao;
import io.renren.modules.setting.entity.OrderExceptionEntity;
import io.renren.modules.setting.service.OrderExceptionService;


@Service("orderExceptionService")
public class OrderExceptionServiceImpl extends ServiceImpl<OrderExceptionDao, OrderExceptionEntity> implements OrderExceptionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderExceptionEntity> page = this.selectPage(
                new Query<OrderExceptionEntity>(params).getPage(),
                new EntityWrapper<OrderExceptionEntity>()
        );

        return new PageUtils(page);
    }

}
