package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.dao.InspectionItemExtraDao;
import io.renren.modules.inspection.entity.InspectionItemExtraEntity;
import io.renren.modules.inspection.service.InspectionItemExtraService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("inspectionItemExtraService")
public class InspectionItemExtraServiceImpl extends ServiceImpl<InspectionItemExtraDao, InspectionItemExtraEntity> implements InspectionItemExtraService {

    @Override
    public void saveOrUpdate( Integer itemId, List<InspectionItemExtraEntity> extraList){
        this.deleteByMap(new MapUtils().put("item_id", itemId));

        if(extraList.size() > 0){
            for(InspectionItemExtraEntity extra: extraList){
                extra.setItemId(itemId);
                extra.setGuid(UUID.randomUUID().toString());
            }

            this.insertBatch(extraList);
        }
    }

    @Override
    public List<Integer> queryExtraIdList(Integer itemId) { return baseMapper.queryExtraIdList(itemId); }

    @Override
    public Integer deleteBatch(Integer[] extraIds) { return baseMapper.deleteBatch(extraIds); }

    @Override
    public InspectionItemExtraEntity selectByName(String name){
        return baseMapper.selectByName(name);
    }

    @Override
    public InspectionItemExtraEntity selectByGuid(String guid){
        return baseMapper.selectByGuid(guid);
    }
}
