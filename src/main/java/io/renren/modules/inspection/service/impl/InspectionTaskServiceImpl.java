package io.renren.modules.inspection.service.impl;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionTaskDao;
import io.renren.modules.inspection.entity.InspectionTaskEntity;
import io.renren.modules.inspection.service.InspectionTaskService;


@Service("inspectionTaskService")
public class InspectionTaskServiceImpl extends ServiceImpl<InspectionTaskDao, InspectionTaskEntity> implements InspectionTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionTaskEntity> page = this.selectPage(
                new Query<InspectionTaskEntity>(params).getPage(),
                new EntityWrapper<InspectionTaskEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> selectByDate(String inspectionDate){
        return baseMapper.selectByDate(inspectionDate);
    }

    @Override
    public List<Map<String,Object>> selectListByDate(String inspectionStartDate,String inspectionEndDate){
        return baseMapper.selectListByDate(inspectionStartDate,inspectionEndDate);
    }

}
