package io.renren.modules.inspection.service.impl;

import io.renren.modules.inspection.entity.InspectionRandomResultEntity;
import io.renren.modules.inspection.entity.InspectionRandomResultMediaEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionRandomResultMediaDao;
import io.renren.modules.inspection.service.InspectionRandomResultMediaService;


@Service("inspectionRandomResultMediaService")
public class InspectionRandomResultMediaServiceImpl extends ServiceImpl<InspectionRandomResultMediaDao, InspectionRandomResultMediaEntity> implements InspectionRandomResultMediaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionRandomResultMediaEntity> page = this.selectPage(
                new Query<InspectionRandomResultMediaEntity>(params).getPage(),
                new EntityWrapper<InspectionRandomResultMediaEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public InspectionRandomResultMediaEntity selectByGuid(String guid){
        return this.baseMapper.selectByGuid(guid);
    }

    @Override
    public List<InspectionRandomResultMediaEntity> selectListByResultId(@Param("result_id") Integer resultId){
        return this.baseMapper.selectListByResultId(resultId);
    }

}
