package io.renren.modules.inspection.service.impl;

import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.entity.DeviceEntity;
import io.renren.modules.inspection.entity.InspectionItemExtraEntity;
import io.renren.modules.inspection.entity.InspectionItemPresuppositionEntity;
import io.renren.modules.inspection.service.DeviceService;
import io.renren.modules.inspection.service.InspectionItemExtraService;
import io.renren.modules.inspection.service.InspectionItemPresuppositionService;
import io.renren.modules.setting.entity.*;
import io.renren.modules.setting.service.*;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionItemDao;
import io.renren.modules.inspection.entity.InspectionItemEntity;
import io.renren.modules.inspection.service.InspectionItemService;
import org.springframework.transaction.annotation.Transactional;


@Service("inspectionItemService")
public class InspectionItemServiceImpl extends ServiceImpl<InspectionItemDao, InspectionItemEntity> implements InspectionItemService {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private InspectionItemExtraService extraService;
    @Autowired
    private InspectionStatusService inspectionStatusService;
    @Autowired
    private InspectionTypeService inspectionTypeService;
    @Autowired
    private SamplingFrequencyService samplingFrequencyService;
    @Autowired
    private SamplingPrecisionService samplingPrecisionService;
    @Autowired
    private InspectionItemPresuppositionService presuppositionService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private ExceptionService exceptionService;

    private Integer OBSERVE = 8;
    private Integer PRESUPPOSITION = 9;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        String deviceId = (String)params.get("deviceId");
        String isCheck = (String)params.get("isCheck");
        if (isCheck == null){
            isCheck = "1";
        }

        Page<InspectionItemEntity> page = this.selectPage(
                new Query<InspectionItemEntity>(params).getPage(),
                new EntityWrapper<InspectionItemEntity>()
                    .eq(deviceId!= null, "device_id", deviceId)
                    .eq(isCheck!= null, "is_check", isCheck)
                    .eq("is_delete",0)
                    .like(StringUtils.isNotBlank(name), "name", name)
                    .orderBy("order_num")
        );

        for (InspectionItemEntity inspectionItemEntity :page.getRecords()){
            DeviceEntity deviceEntity = deviceService.selectById(inspectionItemEntity.getDeviceId());
            inspectionItemEntity.setDeviceName(deviceEntity.getDeviceName());
            inspectionItemEntity.setDeviceCode(deviceEntity.getDeviceCode());

            InspectionTypeEntity inspectionTypeEntity = inspectionTypeService.selectById(inspectionItemEntity.getInspectionType());
            inspectionItemEntity.setInspectionTypeName(inspectionTypeEntity.getName());
            inspectionItemEntity.setInspectionTypeUnit(inspectionTypeEntity.getUnit());

            InspectionStatusEntity inspectionStatusEntity = inspectionStatusService.selectById(inspectionItemEntity.getInspectionStatus());
            if (inspectionStatusEntity != null){
                inspectionItemEntity.setInspectionStatusName(inspectionStatusEntity.getName());
            }

            //如果是抄表，单位重新读取
            if (inspectionItemEntity.getInspectionType().equals(3)) {
                UnitEntity unit = unitService.selectById(inspectionItemEntity.getUnit());
                if(unit != null){
                    inspectionItemEntity.setUnit(unit.getName());
                }
            }

            SamplingFrequencyEntity samplingFrequencyEntity = samplingFrequencyService.selectById(inspectionItemEntity.getFrequency());
            inspectionItemEntity.setFrequencyName(samplingFrequencyEntity.getName());

            SamplingPrecisionEntity samplingPrecisionEntity = samplingPrecisionService.selectById(inspectionItemEntity.getPrecision());
            inspectionItemEntity.setPrecisionName(samplingPrecisionEntity.getName());

            Integer itemId = inspectionItemEntity.getId();

            List<InspectionItemExtraEntity> extras = extraService.selectList(
                    new EntityWrapper<InspectionItemExtraEntity>()
                        .eq(itemId != null, "item_id", itemId)
            );
            inspectionItemEntity.setExtraList(extras);
            if(inspectionItemEntity.getInspectionType().equals(OBSERVE)){
                List<String> extraNameList = new ArrayList<>(extras.size());
                for(InspectionItemExtraEntity extra :extras){
                    extraNameList.add(extra.getName());
                }
                inspectionItemEntity.setExtras(org.apache.commons.lang.StringUtils.join(extraNameList.toArray(), '/'));

            }

            List<InspectionItemPresuppositionEntity> presuppositions = presuppositionService.selectList(
                    new EntityWrapper<InspectionItemPresuppositionEntity>()
                            .eq(itemId != null, "item_id", itemId)
            );
            inspectionItemEntity.setPresuppositionList(presuppositions);
            if(inspectionItemEntity.getInspectionType().equals(PRESUPPOSITION)){
                List<String> presuppositionsNameList = new ArrayList<>(presuppositions.size());
                for(InspectionItemPresuppositionEntity presupposition :presuppositions){
                    presuppositionsNameList.add(presupposition.getName());
                }
                inspectionItemEntity.setExtras(org.apache.commons.lang.StringUtils.join(presuppositionsNameList.toArray(), '/'));
            }
        }

        return new PageUtils(page);
    }

    @Override
    public List<InspectionItemEntity> all(Map<String, Object> params) {
        String name = (String)params.get("name");
        String deviceId = (String)params.get("deviceId");
        String isCheck = (String)params.get("isCheck");
        if (isCheck == null){
            isCheck = "1";
        }

        List<InspectionItemEntity> list = this.selectList(
                new EntityWrapper<InspectionItemEntity>()
                        .eq(deviceId!= null, "device_id", deviceId)
                        .eq(isCheck!= null, "is_check", isCheck)
                        .eq("is_delete",0)
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .orderBy("order_num")
        );

        for (InspectionItemEntity inspectionItemEntity :list){
            DeviceEntity deviceEntity = deviceService.selectById(inspectionItemEntity.getDeviceId());
            inspectionItemEntity.setDeviceName(deviceEntity.getDeviceName());
            inspectionItemEntity.setDeviceCode(deviceEntity.getDeviceCode());

            InspectionTypeEntity inspectionTypeEntity = inspectionTypeService.selectById(inspectionItemEntity.getInspectionType());
            inspectionItemEntity.setInspectionTypeName(inspectionTypeEntity.getName());
            inspectionItemEntity.setInspectionTypeUnit(inspectionTypeEntity.getUnit());

            InspectionStatusEntity inspectionStatusEntity = inspectionStatusService.selectById(inspectionItemEntity.getInspectionStatus());
            if (inspectionStatusEntity != null){
                inspectionItemEntity.setInspectionStatusName(inspectionStatusEntity.getName());
            }

            //如果是抄表，单位重新读取
            if (inspectionItemEntity.getInspectionType().equals(3)) {
                UnitEntity unit = unitService.selectById(inspectionItemEntity.getUnit());
                if(unit != null){
                    inspectionItemEntity.setUnit(unit.getName());
                }
            }

            SamplingFrequencyEntity samplingFrequencyEntity = samplingFrequencyService.selectById(inspectionItemEntity.getFrequency());
            inspectionItemEntity.setFrequencyName(samplingFrequencyEntity.getName());

            SamplingPrecisionEntity samplingPrecisionEntity = samplingPrecisionService.selectById(inspectionItemEntity.getPrecision());
            inspectionItemEntity.setPrecisionName(samplingPrecisionEntity.getName());

            Integer itemId = inspectionItemEntity.getId();

            List<InspectionItemExtraEntity> extras = extraService.selectList(
                    new EntityWrapper<InspectionItemExtraEntity>()
                            .eq(itemId != null, "item_id", itemId)
            );
            inspectionItemEntity.setExtraList(extras);
            if(inspectionItemEntity.getInspectionType().equals(OBSERVE)){
                List<String> extraNameList = new ArrayList<>(extras.size());
                List<String> extraExceptionList = new ArrayList<>(extras.size());
                for(InspectionItemExtraEntity extra :extras){
                    extraNameList.add(extra.getName());
                    ExceptionEntity exception = exceptionService.selectById(extra.getExceptionId());
                    if(exception != null){
                        extraExceptionList.add(exception.getName());
                    }
                }
                inspectionItemEntity.setExtras(org.apache.commons.lang.StringUtils.join(extraNameList.toArray(), '/'));
                inspectionItemEntity.setExtraExceptions(org.apache.commons.lang.StringUtils.join(extraExceptionList.toArray(), '/'));

            }

            List<InspectionItemPresuppositionEntity> presuppositions = presuppositionService.selectList(
                    new EntityWrapper<InspectionItemPresuppositionEntity>()
                            .eq(itemId != null, "item_id", itemId)
            );
            inspectionItemEntity.setPresuppositionList(presuppositions);
            if(inspectionItemEntity.getInspectionType().equals(PRESUPPOSITION)){
                List<String> presuppositionsNameList = new ArrayList<>(presuppositions.size());
                for(InspectionItemPresuppositionEntity presupposition :presuppositions){
                    presuppositionsNameList.add(presupposition.getName());
                }
                inspectionItemEntity.setExtras(org.apache.commons.lang.StringUtils.join(presuppositionsNameList.toArray(), '/'));
            }
        }

        return list;
    }

    @Override
    public InspectionItemEntity selectByGuid(String guid){
        return baseMapper.selectByGuid(guid);
    }


    @Override
    public List<Map<String,Object>> selectByDevice(Long deviceId, Integer inspectionType){
        return baseMapper.selectByDevice(deviceId, inspectionType);
    }


    @Override
    @Transactional
    public void save(InspectionItemEntity inspectionItemEntity){
        this.insert(inspectionItemEntity);
        if(inspectionItemEntity.getExtraList() != null){
            extraService.saveOrUpdate(inspectionItemEntity.getId(), inspectionItemEntity.getExtraList());
        }
        if(inspectionItemEntity.getPresuppositionList() != null) {
            presuppositionService.saveOrUpdate(inspectionItemEntity.getId(), inspectionItemEntity.getPresuppositionList());
        }    }

    @Override
    @Transactional
    public void update(InspectionItemEntity inspectionItemEntity){
        inspectionItemEntity.setIsCheck(1);
        this.updateAllColumnById(inspectionItemEntity);
        if(inspectionItemEntity.getExtraList() != null){
            extraService.saveOrUpdate(inspectionItemEntity.getId(), inspectionItemEntity.getExtraList());
        }
        if(inspectionItemEntity.getPresuppositionList() != null) {
            presuppositionService.saveOrUpdate(inspectionItemEntity.getId(), inspectionItemEntity.getPresuppositionList());
        }
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] itemIds){
        List<Long> itemIdList = Arrays.asList(itemIds);
        for(Long itemId: itemIdList){
            extraService.deleteByMap(new MapUtils().put("item_id", itemId));
            presuppositionService.deleteByMap(new MapUtils().put("item_id", itemId));
        }

        this.deleteBatchIds(itemIdList);
    }

    @Override
    public boolean updateBatchIds(Collection<? extends Serializable> Ids){
        List<InspectionItemEntity> itemEntityList = this.selectBatchIds(Ids);
        return this.updateBatchById(itemEntityList);
    }

    @Override
    public Integer isExist(String itemName, String deviceCode){
        return baseMapper.isExist(itemName, deviceCode);


    }
}
