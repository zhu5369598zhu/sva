package io.renren.modules.setting.service.impl;

import io.renren.modules.setting.entity.InspectionStatusEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.setting.dao.ExceptionDao;
import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.service.ExceptionService;


@Service("exceptionService")
public class ExceptionServiceImpl extends ServiceImpl<ExceptionDao, ExceptionEntity> implements ExceptionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExceptionEntity> page = this.selectPage(
                new Query<ExceptionEntity>(params).getPage(),
                new EntityWrapper<ExceptionEntity>()
                        .orderBy("order_num")
        );

        return new PageUtils(page);
    }


    @Override
    public ExceptionEntity selectByName(String name) { return baseMapper.selectByName(name);}
}
