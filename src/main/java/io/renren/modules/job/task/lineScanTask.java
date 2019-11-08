package io.renren.modules.job.task;

import com.qiniu.common.Zone;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component("lineTask")
public class lineScanTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    InspectionLineService lineService;
    @Autowired
    InspectionPeriodService periodService;
    @Autowired
    InspectionTaskService taskService;
    @Autowired
    InspectionTaskDeviceService taskDeviceService;
    @Autowired
    PeriodTurnService periodTurnService;
    @Autowired
    TurnService turnService;
    @Autowired
    LineZoneService lineZoneService;
    @Autowired
    ZoneDeviceService zoneDeviceService;
    @Autowired
    InspectionItemService itemService;

    public void test() {
        logger.info("test方法，正在被执行");
    }

    public void writeInspectionTask(String params) throws java.text.ParseException{
        Date dt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(params.isEmpty()){
            dt = new Date();
        }else{
            try{
                dt = simpleDateFormat.parse(params);
            }catch (Exception e){
                dt = new Date();
            }
        }

        taskGen(dt);
    }

    public void writeInspectionTask() throws java.text.ParseException{
        Date dt = new Date();
        taskGen(dt);
    }

    private void taskGen(Date dt) throws java.text.ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        HashMap lineParams = new HashMap();
        lineParams.put("is_publish", 1);
        List<InspectionLineEntity> lines = lineService.selectByMap(lineParams);
        HashSet<Integer> deviceList = new HashSet<>();
        for (InspectionLineEntity line:lines) {
            Integer deviceCount = 0;
            Integer itemCount = 0;
            HashMap lineZoneParams = new HashMap();
            lineZoneParams.put("line_id",line.getId());
            List<LineZoneEntity> lineZoneList = lineZoneService.selectByMap(lineZoneParams);
            for(LineZoneEntity lineZone:lineZoneList){
                HashMap zoneDeviceParams = new HashMap();
                zoneDeviceParams.put("zone_id",lineZone.getZoneId());
                List<ZoneDeviceEntity> zoneDeviceList = zoneDeviceService.selectByMap(zoneDeviceParams);
                zoneDeviceList.size();
                for(ZoneDeviceEntity zoneDevice:zoneDeviceList){
                    deviceList.add(zoneDevice.getDeviceId());
                }
            }
            HashMap periodParams = new HashMap();
            periodParams.put("line_id", line.getId());
            List<InspectionPeriodEntity> periods = periodService.selectByMap(periodParams);
            for (InspectionPeriodEntity period:periods) {
                InspectionTaskEntity taskEntity = new InspectionTaskEntity();
                taskEntity.setLineId(line.getId());
                taskEntity.setIsSpan(0);
                taskEntity.setIsInspected(0);
                taskEntity.setInspectedDeviceCount(0);
                taskEntity.setInspectedItemCount(0);
                HashMap periodTurnParams = new HashMap();
                periodTurnParams.put("period_id",period.getId());
                List<PeriodTurnEntity> periodTurnList = periodTurnService.selectByMap(periodTurnParams);
                for(PeriodTurnEntity periodTurn:periodTurnList){
                    itemCount = 0;
                    Date basePoint = simpleDateFormat.parse(period.getBasePoint());
                    TurnEntity turn = turnService.selectById(periodTurn.getTurnId());
                    if(turn !=null){
                        taskEntity.setTurnId(turn.getId());
                        String today = simpleDateFormat.format(dt);
                        taskEntity.setTurnStartTime(today + " " + turn.getStartTime());
                        if(turn.getEndTime().equals("00:00")){
                            turn.setEndTime("23:59");
                        }
                        taskEntity.setTurnEndTime(today + " " + turn.getEndTime());

                        if(period.getFrequency() == 1){
                            deviceCount = deviceList.size();
                            for(Integer deviceId:deviceList){
                                HashMap itemParams = new HashMap();
                                itemParams.put("device_id",deviceId);
                                itemParams.put("is_delete",0);
                                List<InspectionItemEntity> itemList = itemService.selectByMap(itemParams);
                                itemCount += itemList.size();
                                InspectionTaskDeviceEntity taskDeviceEntity = new InspectionTaskDeviceEntity();
                                taskDeviceEntity.setDeviceId(deviceId);
                                taskDeviceEntity.setLineId(line.getId().longValue());
                                taskDeviceEntity.setTurnId(turn.getId());
                                taskDeviceEntity.setInspectItemCount(itemList.size());
                                taskDeviceEntity.setInspectionDate(dt);
                                writeTaskDevice(line.getId(),turn.getId().intValue(),deviceId,simpleDateFormat.format(dt),taskDeviceEntity);
                            }
                            taskEntity.setIsSpan(0);
                            taskEntity.setInspectDeviceCount(deviceCount);
                            taskEntity.setInspectItemCount(itemCount);
                            taskEntity.setInspectionSpanEndDate(dt);
                            writeTask(line.getId(),turn.getId().intValue(),simpleDateFormat.format(dt),taskEntity);
                        } else if(period.getFrequency() == 7) {
                            if (basePoint.getTime() < dt.getTime()) {
                                cal.setTime(dt);
                                Integer week = cal.get(Calendar.DAY_OF_WEEK) - 1;
                                if (period.getSpan() == 1) {
                                    if (period.getStartPoint() == week) {
                                        deviceCount = deviceList.size();
                                        for(Integer deviceId:deviceList){
                                            HashMap itemParams = new HashMap();
                                            itemParams.put("device_id",deviceId);
                                            itemParams.put("is_delete",0);
                                            List<InspectionItemEntity> itemList = itemService.selectByMap(itemParams);
                                            itemCount += itemList.size();
                                            InspectionTaskDeviceEntity taskDeviceEntity = new InspectionTaskDeviceEntity();
                                            taskDeviceEntity.setDeviceId(deviceId);
                                            taskDeviceEntity.setLineId(line.getId().longValue());
                                            taskDeviceEntity.setTurnId(turn.getId());
                                            taskDeviceEntity.setInspectItemCount(itemList.size());
                                            taskDeviceEntity.setInspectionDate(dt);
                                            writeTaskDevice(line.getId(),turn.getId().intValue(),deviceId,simpleDateFormat.format(dt),taskDeviceEntity);
                                        }
                                        taskEntity.setIsSpan(0);
                                        taskEntity.setInspectDeviceCount(deviceCount);
                                        taskEntity.setInspectItemCount(itemCount);
                                        taskEntity.setInspectionSpanEndDate(dt);

                                        writeTask(line.getId(),turn.getId().intValue(),simpleDateFormat.format(dt),taskEntity);
                                    }
                                } else {
                                    if (period.getStartPoint() + period.getSpan() == week) {
                                        deviceCount = deviceList.size();
                                        for(Integer deviceId:deviceList){
                                            HashMap itemParams = new HashMap();
                                            itemParams.put("device_id",deviceId);
                                            itemParams.put("is_delete",0);
                                            List<InspectionItemEntity> itemList = itemService.selectByMap(itemParams);
                                            itemCount += itemList.size();
                                            InspectionTaskDeviceEntity taskDeviceEntity = new InspectionTaskDeviceEntity();
                                            taskDeviceEntity.setDeviceId(deviceId);
                                            taskDeviceEntity.setLineId(line.getId().longValue());
                                            taskDeviceEntity.setTurnId(turn.getId());
                                            taskDeviceEntity.setInspectItemCount(itemList.size());
                                            taskDeviceEntity.setInspectionDate(dt);
                                            writeTaskDevice(line.getId(),turn.getId().intValue(),deviceId,simpleDateFormat.format(dt),taskDeviceEntity);
                                        }
                                        taskEntity.setIsSpan(1);
                                        taskEntity.setInspectDeviceCount(deviceCount);
                                        taskEntity.setInspectItemCount(itemCount);
                                        taskEntity.setInspectionSpanEndDate(dt);

                                        writeTask(line.getId(),turn.getId().intValue(),simpleDateFormat.format(dt),taskEntity);
                                    }
                                }
                            }
                        } else if(period.getFrequency() == 31){
                            cal.setTime(dt);
                            Integer day = cal.get(Calendar.DATE);
                            if(basePoint.getTime()< dt.getTime()){
                                deviceCount = deviceList.size();
                                for(Integer deviceId:deviceList){
                                    HashMap itemParams = new HashMap();
                                    itemParams.put("device_id",deviceId);
                                    itemParams.put("is_delete",0);
                                    List<InspectionItemEntity> itemList = itemService.selectByMap(itemParams);
                                    itemCount += itemList.size();
                                    InspectionTaskDeviceEntity taskDeviceEntity = new InspectionTaskDeviceEntity();
                                    taskDeviceEntity.setDeviceId(deviceId);
                                    taskDeviceEntity.setLineId(line.getId().longValue());
                                    taskDeviceEntity.setTurnId(turn.getId());
                                    taskDeviceEntity.setInspectItemCount(itemList.size());
                                    taskDeviceEntity.setInspectionDate(dt);
                                    writeTaskDevice(line.getId(),turn.getId().intValue(),deviceId,simpleDateFormat.format(dt),taskDeviceEntity);
                                }
                                taskEntity.setInspectDeviceCount(deviceCount);
                                taskEntity.setInspectItemCount(itemCount);

                                if(period.getSpan() == 1){
                                    if(period.getStartPoint() == day){
                                        taskEntity.setIsSpan(0);
                                        taskEntity.setInspectionSpanEndDate(dt);
                                    }else{
                                        taskEntity.setIsSpan(1);
                                        cal.add(Calendar.DAY_OF_MONTH,1);
                                        taskEntity.setInspectionSpanEndDate(cal.getTime());
                                    }
                                }

                                writeTask(line.getId(),turn.getId().intValue(),simpleDateFormat.format(dt),taskEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeTask(Integer lineId,Integer turnId,String date,InspectionTaskEntity taskEntity){
        HashMap taskParams = new HashMap();
        taskParams.put("line_id",lineId);
        taskParams.put("turn_id",turnId);
        taskParams.put("inspection_span_end_date",date);
        List<InspectionTaskEntity> taskList = taskService.selectByMap(taskParams);
        try{
            if(taskList.size() == 0){
                taskService.insert(taskEntity);
            }else{
                InspectionTaskEntity task = taskList.get(0);
                task.setInspectDeviceCount(taskEntity.getInspectDeviceCount());
                taskService.updateById(task);
            }
        }catch(Exception e){
            logger.error(e.toString());
        }
    }

    private void writeTaskDevice(Integer lineId,Integer turnId,Integer deviceId,String date,InspectionTaskDeviceEntity taskDeviceEntity){
        HashMap taskDeviceParams = new HashMap();
        taskDeviceParams.put("device_id",deviceId);
        taskDeviceParams.put("line_id",lineId);
        taskDeviceParams.put("turn_id",turnId);
        taskDeviceParams.put("inspection_date",date);
        List<InspectionTaskDeviceEntity> taskDeviceList = taskDeviceService.selectByMap(taskDeviceParams);
        try{
            if(taskDeviceList.size() == 0){
                taskDeviceService.insert(taskDeviceEntity);
            }else if (taskDeviceList.size() > 0){
                InspectionTaskDeviceEntity tmp = taskDeviceList.get(0);
                tmp.setInspectItemCount(taskDeviceEntity.getInspectItemCount());
                taskDeviceService.updateById(tmp);
            }
        }catch (Exception e){

        }
    }

}
