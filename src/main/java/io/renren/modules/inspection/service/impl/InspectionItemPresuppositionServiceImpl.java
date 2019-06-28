package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.dao.InspectionItemExtraDao;
import io.renren.modules.inspection.dao.InspectionItemPresuppositionDao;
import io.renren.modules.inspection.entity.InspectionItemExtraEntity;
import io.renren.modules.inspection.entity.InspectionItemPresuppositionEntity;
import io.renren.modules.inspection.service.InspectionItemExtraService;
import io.renren.modules.inspection.service.InspectionItemPresuppositionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("inspectionItemPresuppositionService")
public class InspectionItemPresuppositionServiceImpl extends ServiceImpl<InspectionItemPresuppositionDao, InspectionItemPresuppositionEntity> implements InspectionItemPresuppositionService {

    @Override
    public void saveOrUpdate( Integer itemId, List<InspectionItemPresuppositionEntity> presuppositionList){
        this.deleteByMap(new MapUtils().put("item_id", itemId));

        if(presuppositionList.size() > 0){
            for(InspectionItemPresuppositionEntity presupposition: presuppositionList){
                presupposition.setItemId(itemId);
                presupposition.setGuid(UUID.randomUUID().toString());
            }

            this.insertBatch(presuppositionList);
        }
    }

    @Override
    public List<Integer> queryPresuppositionIdList(Integer itemId) { return baseMapper.queryPresuppositionIdList(itemId); }

    @Override
    public Integer deleteBatch(Integer[] extraIds) { return baseMapper.deleteBatch(extraIds); }

    @Override
    public InspectionItemPresuppositionEntity selectByGuid(String guid){
        return baseMapper.selectByGuid(guid);
    }
}
