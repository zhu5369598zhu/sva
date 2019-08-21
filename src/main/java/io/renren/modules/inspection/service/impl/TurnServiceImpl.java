package io.renren.modules.inspection.service.impl;

import io.renren.common.utils.MapUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.ClassGroupEntity;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.PeriodTurnEntity;
import io.renren.modules.inspection.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.TurnDao;
import io.renren.modules.inspection.entity.TurnEntity;
import org.springframework.transaction.annotation.Transactional;


@Service("turnService")
public class TurnServiceImpl extends ServiceImpl<TurnDao, TurnEntity> implements TurnService {
    @Autowired
    private InspectionLineService lineService;
    @Autowired
    private ClassGroupService classGroupService;
    @Autowired
    private TurnClassGroupService turnClassGroupService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String inspectionLineId = (String)params.get("inspectionLineId");
        Page<TurnEntity> page = this.selectPage(
                new Query<TurnEntity>(params).getPage(),
                new EntityWrapper<TurnEntity>()
                        .eq(inspectionLineId != null, "inspection_line_id", inspectionLineId)
        );

        for(TurnEntity turnEntity :page.getRecords()){
            List<Long> classGroupIdList = turnClassGroupService.queryClassGroupIdList(turnEntity.getId());
            List<String> classGroupNameList = new ArrayList<>(classGroupIdList.size());

            if (classGroupIdList != null){
                for(Long classGroupId :classGroupIdList ){
                    ClassGroupEntity classGroupEntity = classGroupService.selectById(classGroupId);
                    classGroupNameList.add(classGroupEntity.getName());
                }
                turnEntity.setClassGroupList(classGroupIdList);
                turnEntity.setClassGroupNameList(classGroupNameList);
                turnEntity.setClassGroupNames(org.apache.commons.lang.StringUtils.join(classGroupNameList.toArray(), '/'));
            }
        }

        return new PageUtils(page);
    }
    @Override
    @Transactional
    public void save(TurnEntity turnEntity){
        this.insert(turnEntity);
        turnClassGroupService.saveOrUpdate(turnEntity.getId(), turnEntity.getClassGroupList());
    }

    @Override
    @Transactional
    public void update(TurnEntity turnEntity){
        this.updateById(turnEntity);
        turnClassGroupService.saveOrUpdate(turnEntity.getId(), turnEntity.getClassGroupList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] turnIds) {
        List<Long> turnIdList = Arrays.asList(turnIds);
        for(Long turnId :turnIdList){
            turnClassGroupService.deleteByMap(new MapUtils().put("turn_id", turnId));
        }

        this.deleteBatchIds(turnIdList);
    }

    @Override
    public TurnEntity selectByGuid(String guid) { return baseMapper.selectByGuid(guid); }
}
