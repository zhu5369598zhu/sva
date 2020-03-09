package io.renren.modules.inspection.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.DateUtils;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@RestController
@RequestMapping("inspection/inspectionline")
public class InspectionLineController {
    @Autowired
    private InspectionLineService inspectionLineService;
    @Autowired
    private LineZoneService lineZoneService;
    @Autowired
    private ZoneDeviceService zoneDeviceService;
    @Autowired
    private InspectionPeriodService periodService;
    @Autowired
    private PeriodTurnService periodTurnService;
    @Autowired
    private TurnService turnService;
    @Autowired
    private ClassGroupService classGroupService;
    @Autowired
    private ClassWorkerService workerService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private TurnClassGroupService turnClassGroupService;
    @Autowired
    private InspectionLinePublishService publishService;
    @Autowired
    private InspectionLineService lineService;
    @Autowired
    private InspectionTaskService taskService;
    @Autowired
    private InspectionTaskDeviceService taskDeviceService;
    @Autowired
    private SysDeptService sysDeptService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectionline:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionLineService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectionline:info")
    public R info(@PathVariable("id") Integer id){
		InspectionLineEntity inspectionLine = inspectionLineService.selectById(id);
        SysDeptEntity sysDeptEntity = sysDeptService.selectById(inspectionLine.getDeptId());
        inspectionLine.setDeptName(sysDeptEntity.getName());
        if(inspectionLine.getPeriodType()== 1){
            inspectionLine.setPeriodTypeName("天");
        }else if(inspectionLine.getPeriodType()== 2){
            inspectionLine.setPeriodTypeName("周");
        }else if(inspectionLine.getPeriodType()== 3){
            inspectionLine.setPeriodTypeName("月");
        }

        return R.ok().put("inspectionLine", inspectionLine);
    }

    /**
     * 发布
     */
    @RequestMapping("/publish/{id}")
    @RequiresPermissions("inspection:inspectionline:publish")
    public R publish(@PathVariable("id") Integer id){
        InspectionLineEntity inspectionLine = inspectionLineService.selectById(id);

        HashMap<String, Object> linePublishParams = new HashMap<String, Object>();
        linePublishParams.put("line_id", inspectionLine.getId());
        List<InspectionLinePublishEntity> linePublishEntities = publishService.selectByMap(linePublishParams);
        if (linePublishEntities.size() == 0 ){
            return R.error(400, "线路没有设置下载PDA。");
        }

        HashMap<String, Object> lineZoneParams = new HashMap<String, Object>();
        lineZoneParams.put("line_id", inspectionLine.getId());
        List<LineZoneEntity> lineZoneEntities = lineZoneService.selectByMap(lineZoneParams);
        if (lineZoneEntities.size() == 0 ){
            return R.error(400, "线路没有绑定巡区。");
        }

        for (LineZoneEntity lineZoneEntity:lineZoneEntities){
            HashMap<String, Object> zoneDeviceParams = new HashMap<String, Object>();
            zoneDeviceParams.put("zone_id", lineZoneEntity.getZoneId());
            List<ZoneDeviceEntity> zoneDeviceEntities = zoneDeviceService.selectByMap(zoneDeviceParams);
            if (zoneDeviceEntities.size() == 0 ){
                ZoneEntity zone = zoneService.selectById(lineZoneEntity.getZoneId());
                return R.error(400, "巡区\"" + zone.getZoneName() + "\"下没有绑定设备。" );
            }
        }

        HashMap<String, Object> periodParams = new HashMap<String, Object>();
        periodParams.put("line_id", inspectionLine.getId());
        List<InspectionPeriodEntity> inspectionPeriodEntities = periodService.selectByMap(periodParams);
        if (inspectionPeriodEntities.size() == 0 ){
            return R.error(400, "线路没有设置周期。");
        }

        for (InspectionPeriodEntity periodEntity:inspectionPeriodEntities){
            HashMap<String, Object> periodTurnParams = new HashMap<String, Object>();
            periodTurnParams.put("period_id", periodEntity.getId());
            List<PeriodTurnEntity> periodTurnEntities = periodTurnService.selectByMap(periodTurnParams);
            if (periodTurnEntities.size() == 0 ){
                return R.error(400, "周期\"" + periodEntity.getName() + "\"没有设置轮次。" );
            }

            for (PeriodTurnEntity periodTurnEntity:periodTurnEntities){
                HashMap<String, Object> turnClassParams = new HashMap<String, Object>();
                turnClassParams.put("turn_id", periodTurnEntity.getTurnId());
                List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassParams);
                if (turnClassGroupEntities.size() == 0 ){
                    TurnEntity turnEntity = turnService.selectById(periodTurnEntity.getTurnId());
                    if(turnEntity !=null){
                        return R.error(400, "轮次\"" + turnEntity.getName() + "\"没有设置班组。" );
                    }
                }

                for (TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                    HashMap<String, Object> classWorkerParams = new HashMap<String, Object>();
                    classWorkerParams.put("class_group_id", turnClassGroupEntity.getClassGroupId());
                    List<ClassWorkerEntity> classWorkerEntities = workerService.selectByMap(classWorkerParams);
                    if (classWorkerEntities.size() == 0){
                        ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                        return R.error(400, "班组\"" + classGroupEntity.getName() + "\"没有设置人员。" );
                    }
                }
            }
        }
        HashMap<String, Object> taskMap = new HashMap<>();
        taskMap.put("line_id",inspectionLine.getId());
        taskMap.put("inspection_span_end_date", DateUtils.format(new Date(), DateUtils.DATE_PATTERN));
        taskService.deleteByMap(taskMap);
        HashMap<String, Object> devicetaskMap = new HashMap<>();
        devicetaskMap.put("line_id",inspectionLine.getId());
        devicetaskMap.put("inspection_date", DateUtils.format(new Date(), DateUtils.DATE_PATTERN));
        taskDeviceService.deleteByMap(devicetaskMap);
        HashMap<String, Object> publishParams = new HashMap<String, Object>();
        publishParams.put("line_id", inspectionLine.getId());
        publishService.updateDownload(inspectionLine.getId(), 0);
        inspectionLine.setIsPublish(1);
        inspectionLineService.updateById(inspectionLine);

        return R.ok();
    }


    /**
     * 保存
     */
    @SysLog("保存巡检线路")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectionline:save")
    public R save(@RequestBody InspectionLineEntity inspectionLine){
            inspectionLine.setCreateTime(new Date());
            inspectionLine.setUpdateTime(new Date());
            inspectionLine.setGuid(UUID.randomUUID().toString());
			inspectionLineService.insert(inspectionLine);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改巡检线路")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectionline:update")
    public R update(@RequestBody InspectionLineEntity inspectionLine){
            inspectionLine.setUpdateTime(new Date());
			inspectionLineService.updateById(inspectionLine);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除巡检线路")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectionline:delete")
    public R delete(@RequestBody Integer[] ids){
        boolean isOk = false;
        for(Integer lineId:ids){
            InspectionLineEntity line = lineService.selectById(lineId);
            if(line != null){
                if(line.getIsPublish().equals(1)){
                    return R.error(400,"线路已发布，不能删除。");
                } else {
                    line.setIsDelete(1);
                    isOk = inspectionLineService.updateById(line);
                    if(!isOk){
                        break;
                    }
                }
            }
        }

        if(isOk){
            return R.ok();
        }else{
            return R.error(500,"服务器错误");
        }
    }

}
