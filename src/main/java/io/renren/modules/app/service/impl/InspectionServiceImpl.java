package io.renren.modules.app.service.impl;

import io.renren.modules.app.service.InspectionService;
import io.renren.modules.inspection.dao.InspectionItemDao;
import io.renren.modules.inspection.dao.InspectionLineDao;
import io.renren.modules.inspection.dao.PeriodTurnDao;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import io.renren.modules.setting.entity.*;
import io.renren.modules.setting.service.*;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;



@Service("InspectionService")
public class InspectionServiceImpl extends ServiceImpl<InspectionLineDao, InspectionLineEntity> implements InspectionService {

    @Autowired
    private SysDeptService deptService;
    @Autowired
    private InspectionLineService lineService;
    @Autowired
    private LineZoneService lineZoneService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneDeviceService zoneDeviceService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private InspectionItemService itemService;
    @Autowired
    private TurnService turnService;
    @Autowired
    private ClassGroupService classGroupService;
    @Autowired
    private ClassWorkerService workerService;
    @Autowired
    private TurnClassGroupService turnClassGroupService;
    @Autowired
    private InspectionPeriodService periodService;
    @Autowired
    private PeriodTurnService periodTurnService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private InspectionTypeService inspectionTypeService;
    @Autowired
    private InspectionItemExtraService extraService;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private InspectionStatusService statusService;
    @Autowired
    private InspectionItemPresuppositionService presuppositionService;
    @Autowired
    private InspectionLinePublishService publishService;
    @Autowired
    private PdaService pdaService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private SamplingPrecisionService precisionService;
    @Autowired
    private SamplingFrequencyService frequencyService;

    @Override
    public HashMap<String, Object> download(SysUserEntity userEntity, String pdaMac) {
        List<Object> lineList = new ArrayList<Object>();
        List<Object> zoneList = new ArrayList<Object>();
        List<Object> lineZoneList = new ArrayList<Object>();
        List<Object> deviceList = new ArrayList<Object>();
        List<Object> zoneDeviceList = new ArrayList<Object>();
        List<Object> itemList = new ArrayList<Object>();
        List<Object> extraList = new ArrayList<>();
        List<Object> periodList = new ArrayList<Object>();
        List<Object> turnList = new ArrayList<Object>();
        List<Object> periodTurnList = new ArrayList<>();
        List<Object> classGroupList = new ArrayList<Object>();
        List<Object> turnClassGroupList = new ArrayList<Object>();
        List<Object> workerList = new ArrayList<Object>();
        List<Object> classGroupWorkerList = new ArrayList<Object>();
        List<Object> lineTypeList = new ArrayList<Object>();
        List<Object> presuppositionList = new ArrayList<Object>();

        HashMap<String, Object> downloadList = new HashMap<String, Object>();

        List<InspectionLineEntity> lineEntities = lineService.selectByMac(pdaMac);
        for(InspectionLineEntity lineEntity :lineEntities) {
            Map<String, Object> lineJson = new HashMap<>();
            lineJson.put("line_guid", lineEntity.getGuid());
            lineJson.put("name", lineEntity.getName());
            lineJson.put("type", lineEntity.getType());
            lineJson.put("periodType", lineEntity.getPeriodType());
            lineJson.put("remark", lineEntity.getRemark());
            lineJson.put("orderNum", lineEntity.getOrderNum());
            lineList.add(lineJson);

            PdaEntity pda = pdaService.selectByMac(pdaMac);
            HashMap<String, Object> publishParams = new HashMap<String, Object>();
            publishParams.put("line_id", lineEntity.getId());
            publishParams.put("pda_id", pda.getPdaId());
            InspectionLinePublishEntity publishEntity = publishService.selectByParams(lineEntity.getId(), pda.getPdaId());
            if (publishEntity != null){
                publishEntity.setUpdateTime(new Date());
                publishEntity.setIsDownload(1);
                publishService.updateById(publishEntity);
            }

            //获取巡区
            Map<String, Object> zoneParams = new HashMap<>();
            zoneParams.put("line_id", lineEntity.getId());
            List<LineZoneEntity> lineZoneEntities = lineZoneService.selectByMap(zoneParams);
            for(LineZoneEntity lineZoneEntity : lineZoneEntities) {
                Map<String, Object> lineZoneJson = new HashMap<>();
                Map<String, Object> zoneJson = new HashMap<>();
                ZoneEntity zone = zoneService.selectById(lineZoneEntity.getZoneId());
                if (zone != null && zone.getIsDelete() != 1){
                    lineZoneJson.put("line_guid", lineEntity.getGuid());
                    lineZoneJson.put("zone_guid", zone.getGuid());
                    lineZoneJson.put("orderNum", lineZoneEntity.getOrderNum());
                    lineZoneList.add(lineZoneJson);

                    zoneJson.put("zone_guid", zone.getGuid());
                    zoneJson.put("zoneName", zone.getZoneName());
                    zoneJson.put("zoneCode", zone.getZoneCode());
                    zoneJson.put("order_num", zone.getOrderNum());
                    zoneList.add(zoneJson);
                }

                //获取设备
                Map<String, Object> zoneDeviceParams = new HashMap<>();
                zoneDeviceParams.put("zone_id", zone.getZoneId());
                List<ZoneDeviceEntity> zoneDeviceEntities = zoneDeviceService.selectByMap(zoneDeviceParams);
                for(ZoneDeviceEntity zoneDeviceEntity : zoneDeviceEntities) {
                    DeviceEntity deviceEntity = deviceService.selectById(zoneDeviceEntity.getDeviceId());
                    if(deviceEntity != null && deviceEntity.getIsDelete() != 1){
                        Map<String, Object> zoneDeviceJson = new HashMap<>();
                        zoneDeviceJson.put("zone_guid",zone.getGuid());
                        zoneDeviceJson.put("device_guid",deviceEntity.getGuid());
                        zoneDeviceJson.put("orderNum",zoneDeviceEntity.getOrderNum());
                        zoneDeviceList.add(zoneDeviceJson);

                        Map<String, Object> deviceJson = new HashMap<>();
                        deviceJson.put("device_guid", deviceEntity.getGuid());
                        deviceJson.put("deviceName", deviceEntity.getDeviceName());
                        deviceJson.put("deviceCode", deviceEntity.getDeviceCode());
                        deviceJson.put("deviceNum", deviceEntity.getDeviceNum());
                        deviceList.add(deviceJson);
                    }


                    //获取巡检项
                    Map<String, Object> itemParams = new HashMap<>();
                    itemParams.put("device_id", deviceEntity.getDeviceId());
                    List<InspectionItemEntity> itemEntities = itemService.selectByMap(itemParams);
                    for(InspectionItemEntity itemEntity : itemEntities){
                        if(itemEntity.getIsDelete() != 1){
                            Map<String,Object> itemJson = new HashMap<>();
                            itemJson.put("item_guid", itemEntity.getGuid());
                            itemJson.put("name", itemEntity.getName());
                            itemJson.put("defaultRpm", itemEntity.getDefaultRpm());
                            itemJson.put("emissivity", itemEntity.getEmissivity());
                            if(itemEntity.getFrequency() != null){
                                SamplingFrequencyEntity frequencyEntity = frequencyService.selectById(itemEntity.getFrequency());
                                itemJson.put("frequency", frequencyEntity.getName());
                            }else{
                                itemJson.put("frequency", itemEntity.getFrequency());
                            }
                            if(itemEntity.getFrequency() != null){
                                SamplingPrecisionEntity precisionEntity = precisionService.selectById(itemEntity.getPrecision());
                                itemJson.put("precision", precisionEntity.getName());
                            }else{
                                itemJson.put("precision", itemEntity.getPrecision());
                            }

                            itemJson.put("remark", itemEntity.getRemark());
                            itemJson.put("InspectionType", itemEntity.getInspectionType());
                            itemJson.put("order_num", itemEntity.getOrderNum());

                            itemJson.put("upup_used",itemEntity.getUpupUsed());
                            itemJson.put("upup_limit", itemEntity.getUpLimit());
                            itemJson.put("up_limit", itemEntity.getUpupLimit());
                            itemJson.put("up_used",itemEntity.getUpUsed());
                            itemJson.put("down_limit", itemEntity.getDownLimit());
                            itemJson.put("down_used",itemEntity.getDownUsed());
                            itemJson.put("downdown_limit", itemEntity.getDowndownLimit());
                            itemJson.put("downdown_used",itemEntity.getDownUsed());
                            itemJson.put("device_guid", deviceEntity.getGuid());

                            //抄表的单位特殊处理
                            if (itemEntity.getInspectionType() == 3){
                                UnitEntity unitEntity = unitService.selectById(itemEntity.getUnit());
                                if(unitEntity != null){
                                    itemJson.put("unit", unitEntity.getName());
                                }
                            }else{
                                itemJson.put("unit", itemEntity.getUnit());
                            }

                            itemList.add(itemJson);


                            //获取观察扩展
                            Map<String, Object> extraParams = new HashMap<>();
                            extraParams.put("item_id", itemEntity.getId());
                            List<InspectionItemExtraEntity> extras = extraService.selectByMap(extraParams);
                            for (InspectionItemExtraEntity extra : extras) {
                                Map<String, Object> extraJson = new HashMap<>();
                                extraJson.put("extra_guid", extra.getGuid());
                                extraJson.put("name", extra.getName());
                                extraJson.put("exceptionId", extra.getExceptionId());
                                extraJson.put("item_guid", itemEntity.getGuid());
                                extraList.add(extraJson);
                            }

                            //获取预设扩展
                            Map<String, Object> prosuppositionParams = new HashMap<>();
                            prosuppositionParams.put("item_id", itemEntity.getId());
                            List<InspectionItemPresuppositionEntity> presuppositions = presuppositionService.selectByMap(prosuppositionParams);
                            for (InspectionItemPresuppositionEntity presupposition : presuppositions) {
                                Map<String, Object> presuppositionJson = new HashMap<>();
                                presuppositionJson.put("presupposition_guid", presupposition.getGuid());
                                presuppositionJson.put("name", presupposition.getName());
                                presuppositionJson.put("exceptionId", presupposition.getExceptionId());
                                presuppositionJson.put("item_guid", itemEntity.getGuid());
                                presuppositionList.add(presuppositionJson);
                            }
                        }
                    }
                }
            }

            //获取周期
            Map<String,Object> periodParams = new HashMap<>();
            periodParams.put("line_id", lineEntity.getId());
            List<InspectionPeriodEntity> periodEntities = periodService.selectByMap(periodParams);
            for(InspectionPeriodEntity periodEntity:periodEntities){
                Map<String, Object> periodJson = new HashMap<>();
                periodJson.put("Guid", periodEntity.getGuid());
                periodJson.put("name", periodEntity.getName());
                periodJson.put("basePoint", periodEntity.getBasePoint());
                periodJson.put("name", periodEntity.getName());
                periodJson.put("turnFinishMod", periodEntity.getTurnFinishMod());
                periodJson.put("frequency", periodEntity.getFrequency());
                periodJson.put("startPoint", periodEntity.getStartPoint());
                periodJson.put("span", periodEntity.getSpan());
                periodJson.put("line_guid", lineEntity.getGuid());
                periodList.add(periodJson);

                Map<String,Object> periodTurnParams = new HashMap<>();
                periodTurnParams.put("period_id", periodEntity.getId());
                List<PeriodTurnEntity> periodTurnEntities = periodTurnService.selectByMap(periodTurnParams);
                for(PeriodTurnEntity periodTurnEntity: periodTurnEntities){
                    TurnEntity turnEntity = turnService.selectById(periodTurnEntity.getTurnId());
                    Map<String,Object> periodTurnJson = new HashMap<>();
                    periodTurnJson.put("turn_guid",turnEntity.getGuid());
                    periodTurnJson.put("period_guid", periodEntity.getGuid());
                    periodTurnList.add(periodTurnJson);
                }
            }

            //获取轮次
            Map<String,Object> turnParams = new HashMap<>();
            turnParams.put("inspection_line_id", lineEntity.getId());
            List<TurnEntity> turnEntities = turnService.selectByMap(turnParams);
            for(TurnEntity turnEntity: turnEntities){
                Map<String,Object> turnJson = new HashMap<>();
                turnJson.put("turn_guid",turnEntity.getGuid());
                turnJson.put("name",turnEntity.getName());
                turnJson.put("startTime",turnEntity.getStartTime());
                if(turnEntity.getEndTime().equals("00:00")){
                    turnEntity.setEndTime("23:59");
                }
                turnJson.put("endTime",turnEntity.getEndTime());
                turnJson.put("line_guid", lineEntity.getGuid());
                turnList.add(turnJson);

                //获取轮次下班组
                Map<String,Object> turnClassGroupParams = new HashMap<>();
                turnClassGroupParams.put("turn_id", turnEntity.getId());
                List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassGroupParams);
                for(TurnClassGroupEntity turnClassGroupEntity :turnClassGroupEntities){
                    Map<String,Object> turnClassGroupJson = new HashMap<>();
                    ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                    turnClassGroupJson.put("class_group_guid", classGroupEntity.getGuid());
                    turnClassGroupJson.put("turn_guid", turnEntity.getGuid());
                    turnClassGroupList.add(turnClassGroupJson);
                }
            }

            //获取班组
            Map<String, Object> classParams = new HashMap<>();
            classParams.put("inspection_line_id", lineEntity.getId());
            List<ClassGroupEntity> classGroupEntities = classGroupService.selectByMap(classParams);
            for(ClassGroupEntity classGroupEntity : classGroupEntities){
                Map<String,Object> classGroupJson = new HashMap<>();
                classGroupJson.put("class_group_guid", classGroupEntity.getGuid());
                classGroupJson.put("name", classGroupEntity.getName());
                classGroupJson.put("line_guid", lineEntity.getGuid());
                classGroupList.add(classGroupJson);

                //获取班组成员
                List<Object> userList = new ArrayList<Object>();
                Map<String,Object> workerParams = new HashMap<>();
                workerParams.put("class_group_id", classGroupEntity.getId());
                List<ClassWorkerEntity> workerEntities = workerService.selectByMap(workerParams);
                for(ClassWorkerEntity worker : workerEntities) {
                    SysUserEntity user = userService.selectById(worker.getUserId());
                    if(user != null && user.getIsDelete() != 1){
                        Map<String,Object> classGroupWorkerJson = new HashMap<>();
                        classGroupWorkerJson.put("worker_guid",user.getGuid());
                        classGroupWorkerJson.put("class_group_guid", classGroupEntity.getGuid());
                        classGroupWorkerList.add(classGroupWorkerJson);
                        Map<String,Object> workerJson = new HashMap<>();
                        workerJson.put("worker_guid", user.getGuid());
                        workerJson.put("userName", user.getUsername());
                        workerJson.put("userCode", user.getUserCode());
                        workerJson.put("password", user.getPassword());
                        workerJson.put("salt", user.getSalt());
                        workerList.add(workerJson);
                    }
                }
            }
        }

        //获取巡检类型
        List<InspectionTypeEntity> inspectionTypeEntityList = inspectionTypeService.selectList(
                new EntityWrapper<InspectionTypeEntity>()
        );
        //获取异常等级
        List<ExceptionEntity> exceptionEntityList = exceptionService.selectList(
                new EntityWrapper<ExceptionEntity>()
        );
        //获取巡检状态
        List<InspectionStatusEntity> inspectionStatusEntityList = statusService.selectList(
                new EntityWrapper<InspectionStatusEntity>()
        );
        //获取轮次完成模式
        List<Object> turnFinishModList = new ArrayList<Object>();
        for(Integer i = 1; i <4; i++){
            Map<String,Object> turnFinishModJson = new HashMap<>();
            turnFinishModJson.put("id", i);
            if( i.equals(1) ) {
                turnFinishModJson.put("name", "全体完成");
            }
            if( i.equals(2) ) {
                turnFinishModJson.put("name", "班组完成");
            }
            if( i.equals(3) ) {
                turnFinishModJson.put("name", "个人完成");
            }
            turnFinishModList.add(turnFinishModJson);
        }

        //获取周期类型
        List<Object> periodTypeList = new ArrayList<Object>();
        for(Integer i = 1; i < 4; i++){
            Map<String,Object> periodTypeJson = new HashMap<>();
            periodTypeJson.put("id", i);
            if( i.equals(1) ) {
                periodTypeJson.put("name", "天");
            }
            if( i.equals(2) ) {
                periodTypeJson.put("name", "周");
            }
            if( i.equals(3) ) {
                periodTypeJson.put("name", "月");
            }
            periodTypeList.add(periodTypeJson);
        }

        //获取线路类型
        for(Integer i = 0; i < 2; i++){
            Map<String,Object> lineTypeJson = new HashMap<>();
            lineTypeJson.put("id", i);
            if( i.equals(0) ) {
                lineTypeJson.put("name", "扫码");
            }
            if( i.equals(1) ) {
                lineTypeJson.put("name", "RFID");
            }

            lineTypeList.add(lineTypeJson);
        }

        downloadList.put("t_line", lineList);
        downloadList.put("t_line_type", lineTypeList);
        downloadList.put("t_class_group", classGroupList);
        downloadList.put("t_class_group_worker", classGroupWorkerList);
        downloadList.put("t_turn_class_group", turnClassGroupList);
        downloadList.put("t_period", periodList);
        downloadList.put("t_period_turn", periodTurnList);
        downloadList.put("t_turn", turnList);
        downloadList.put("t_inspectionType",inspectionTypeEntityList);
        downloadList.put("t_exception",exceptionEntityList);
        downloadList.put("t_inspection_status",inspectionStatusEntityList);
        downloadList.put("t_turnfinishmode",turnFinishModList);
        downloadList.put("t_period_type",periodTypeList);

        //人员去重
        HashSet worker = new HashSet(workerList);
        workerList.clear();
        workerList.addAll(worker);
        downloadList.put("t_worker", workerList);

        //设备去重
        HashSet device = new HashSet(deviceList);
        deviceList.clear();
        deviceList.addAll(device);
        downloadList.put("t_device", deviceList);

        //巡检设备去重
        HashSet zoneDevice = new HashSet(zoneDeviceList);
        zoneDeviceList.clear();
        zoneDeviceList.addAll(zoneDevice);
        downloadList.put("t_zone_device", zoneDeviceList);

        //观察扩展去重
        HashSet extra = new HashSet(extraList);
        extraList.clear();
        extraList.addAll(extra);
        downloadList.put("t_extra", extraList);

        //预设扩展去重
        HashSet presupposition = new HashSet(presuppositionList);
        presuppositionList.clear();
        presuppositionList.addAll(presupposition);
        downloadList.put("t_presupposition", presuppositionList);

        //预设扩展去重
        HashSet item = new HashSet(itemList);
        itemList.clear();
        itemList.addAll(item);
        downloadList.put("t_item", itemList);

        //巡区去重
        HashSet zone = new HashSet(zoneList);
        zoneList.clear();
        zoneList.addAll(zone);
        downloadList.put("t_zone", zoneList);

        //巡线巡区去重
        HashSet lineZone = new HashSet(lineZoneList);
        lineZoneList.clear();
        lineZoneList.addAll(lineZone);
        downloadList.put("t_line_zone", lineZoneList);


        return downloadList;
    }
}
