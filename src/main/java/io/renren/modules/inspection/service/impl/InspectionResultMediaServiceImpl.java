package io.renren.modules.inspection.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionResultMediaDao;
import io.renren.modules.inspection.entity.InspectionResultMediaEntity;
import io.renren.modules.inspection.service.InspectionResultMediaService;


@Service("inspectionResultMediaService")
public class InspectionResultMediaServiceImpl extends ServiceImpl<InspectionResultMediaDao, InspectionResultMediaEntity> implements InspectionResultMediaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionResultMediaEntity> page = this.selectPage(
                new Query<InspectionResultMediaEntity>(params).getPage(),
                new EntityWrapper<InspectionResultMediaEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public InspectionResultMediaEntity selectByGuid(String guid){
        return this.baseMapper.selectByGuid(guid);
    }

    @Override
    public List<InspectionResultMediaEntity> selectListByResultId(@Param("result_id") Integer resultId){
        return this.baseMapper.selectListByResultId(resultId);
    }

}
