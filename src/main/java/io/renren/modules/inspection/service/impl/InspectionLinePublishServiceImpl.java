package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.InspectionLinePublishDao;
import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
import io.renren.modules.inspection.entity.PdaEntity;
import io.renren.modules.inspection.service.InspectionLinePublishService;
import io.renren.modules.inspection.service.PdaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("inspectionLinePublishService")
public class InspectionLinePublishServiceImpl extends ServiceImpl<InspectionLinePublishDao, InspectionLinePublishEntity> implements InspectionLinePublishService {

    @Autowired
    private PdaService pdaService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer lineId = Integer.valueOf((String)params.get("lineId"));
        Page<InspectionLinePublishEntity> page = this.selectPage(
                new Query<InspectionLinePublishEntity>(params).getPage(),
                new EntityWrapper<InspectionLinePublishEntity>()
                .eq( lineId != null, "line_id", lineId)
        );
        List<InspectionLinePublishEntity> linePublishEntities = page.getRecords();
        for(InspectionLinePublishEntity publish:linePublishEntities)
        {
            PdaEntity pda = pdaService.selectById(publish.getPdaId());
            if(pda !=null){
                publish.setPdaName(pda.getPdaName());
                publish.setPdaMac(pda.getPdaMac());
            }
        }

        return new PageUtils(page);
    }

    @Override
    public InspectionLinePublishEntity selectByParams(Integer lineId, Integer pdaId){
        return baseMapper.selectByParams(lineId, pdaId);
    }

    @Override
    public Integer insertPublishBatch(List<InspectionLinePublishEntity> publishList){
        return baseMapper.insertPublishBatch(publishList);
    }

    @Override
    public Integer updateDownload(Integer lineId, Integer isDownload){
        return baseMapper.updateDownload(lineId, isDownload);
    }

}
