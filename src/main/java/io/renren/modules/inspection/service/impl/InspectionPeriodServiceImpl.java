package io.renren.modules.inspection.service.impl;

import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.entity.TurnEntity;
import io.renren.modules.inspection.service.PeriodTurnService;
import io.renren.modules.inspection.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionPeriodDao;
import io.renren.modules.inspection.entity.InspectionPeriodEntity;
import io.renren.modules.inspection.service.InspectionPeriodService;
import org.springframework.transaction.annotation.Transactional;


@Service("inspectionPeriodService")
public class InspectionPeriodServiceImpl extends ServiceImpl<InspectionPeriodDao, InspectionPeriodEntity> implements InspectionPeriodService {

    @Autowired
    private PeriodTurnService periodTurnService;
    @Autowired
    private TurnService turnService;
    @Autowired
    private InspectionPeriodService inspectionPeriodService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String lineId = (String)params.get("lineId");
        Page<InspectionPeriodEntity> page = this.selectPage(
                new Query<InspectionPeriodEntity>(params).getPage(),
                new EntityWrapper<InspectionPeriodEntity>()
                    .eq(lineId != null, "line_id", lineId)
        );

        for(InspectionPeriodEntity inspectionPeriodEntity: page.getRecords()){
            List<Long> turnIdList = periodTurnService.queryTurnIdList(inspectionPeriodEntity.getId());
            List<String> turnNameList = new ArrayList<>(turnIdList.size());

            if (turnIdList != null){
                for(Long turnId :turnIdList){
                    TurnEntity turnEntity = turnService.selectById(turnId);
                    if(turnEntity !=null ){
                        turnNameList.add(turnEntity.getName());
                    }
                }

                if (inspectionPeriodEntity.getTurnFinishMod().equals(1l)){
                    inspectionPeriodEntity.setTurnFinishModName("整体完成");
                } else if (inspectionPeriodEntity.getTurnFinishMod().equals(2l)) {
                    inspectionPeriodEntity.setTurnFinishModName("班组完成");
                } else if (inspectionPeriodEntity.getTurnFinishMod().equals(3l)) {
                    inspectionPeriodEntity.setTurnFinishModName("个人完成");
                }

                inspectionPeriodEntity.setTurnList(turnIdList);
                inspectionPeriodEntity.setTurnNameList(turnNameList);
                inspectionPeriodEntity.setTurnNames(org.apache.commons.lang.StringUtils.join(turnNameList.toArray(), '/'));
            }
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(InspectionPeriodEntity inspectionPeriodEntity){
        this.insert(inspectionPeriodEntity);
        periodTurnService.saveOrUpdate(inspectionPeriodEntity.getId(), inspectionPeriodEntity.getTurnList());
    }

    @Override
    @Transactional
    public void update(InspectionPeriodEntity inspectionPeriodEntity){
        this.updateById(inspectionPeriodEntity);
        periodTurnService.saveOrUpdate(inspectionPeriodEntity.getId(), inspectionPeriodEntity.getTurnList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] periodIds) {
        List<Long> periodIdList = Arrays.asList(periodIds);
        for(Long periodId :periodIdList){
            periodTurnService.deleteByMap(new MapUtils().put("period_id", periodId));
        }

        this.deleteBatchIds(periodIdList);
    }

    @Override
    public void insertPeroid(InspectionPeriodEntity inspectionPeriodEntity){
        baseMapper.insertPeriod(inspectionPeriodEntity);
    }

}
